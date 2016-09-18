package br.com.restaurante.faces;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;

import org.primefaces.context.RequestContext;

import br.com.restaurante.business.ComboBS;
import br.com.restaurante.business.ExecucaoPLSQLBS;
import br.com.restaurante.business.PermissaoFuncaoOrigemBS;
import br.com.restaurante.business.QuizGrupoBS;
import br.com.restaurante.business.QuizTemplateBSAb;
import br.com.restaurante.business.TipoQuizBS;
import br.com.restaurante.business.ValidadorBS;
import br.com.restaurante.model.CamposRespostaAb;
import br.com.restaurante.model.QuizGrupoModel;
import br.com.restaurante.model.QuizModel;
import br.com.restaurante.model.QuizTemplateArquetipoRespostaAb;
import br.com.restaurante.model.QuizTemplateIf;
import br.com.restaurante.model.QuizTemplateRespostaIf;
import br.com.restaurante.model.TipoQuizModel;
import br.com.restaurante.model.UsuarioModel;
import br.com.restaurante.util.Constantes;
import br.com.restaurante.util.JasperUtil;
import br.com.restaurante.util.QuizTemplateUtil;
import br.com.restaurante.util.QuizUtil;
import br.com.restaurante.util.Utilitario;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.exception.TSSystemException;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.faces.TSMainFaces;

@SuppressWarnings("serial")
public abstract class QuizTemplateFacesAb<T extends QuizTemplateIf<R>, R extends QuizTemplateRespostaIf<T, R, A>, A extends QuizTemplateArquetipoRespostaAb<T, R, A>> extends TSMainFaces implements QuizQuestionarioFacesIF, QuestionarioFacesIF {

	@EJB
	private QuizGrupoBS quizGrupoBS;

	@EJB
	protected TipoQuizBS tipoQuizBS;

	@EJB
	protected ValidadorBS validadorBS;

	@EJB
	protected PermissaoFuncaoOrigemBS permissaoFuncaoOrigemBS;

	@EJB
	protected ComboBS comboBS;

	@EJB
	protected ExecucaoPLSQLBS execucaoPLSQLBS;

	protected T quizQuestionarioModel;

	protected List<T> questionarios;

	protected boolean flagCriarQuestionarioAutomatico = true;

	protected abstract Long getGrupoQuizId();

	protected abstract T getInstanciaQuiz();

	protected abstract R getInstanciaResposta();

	protected abstract QuizTemplateBSAb<T, R, A> getQuizQuestionarioBS();

	protected QuizTemplateUtil<T, R, A> quizTemplateUtil;
	protected QuizUtil quizUtil;

	protected QuizModel quizCidSelecionado;
	protected QuizModel quizProcedimentoSelecionado;

	protected String getNomeRelatorio() {
		return null;
	}

	protected void instanciarObjetos() {

		this.quizTemplateUtil = new QuizTemplateUtil<T, R, A>();
		this.quizUtil = new QuizUtil();
		this.quizQuestionarioModel = this.instanciarQuestionario();
		this.questionarios = new ArrayList<T>();

	}

	protected boolean isCarregamentoAutomatico() {
		return false;
	}

	public void init() {

		Utilitario.addMapaVariaveisSessao(Utilitario.getFuncionarioSessao());

		this.instanciarObjetos();

		this.pesquisar();

		T questionario = getQuizQuestionarioBS().obterExistente(this.quizQuestionarioModel);

		boolean flagQuestionarioIniciado = false;

		if (TSUtil.isEmpty(questionario)) {

			this.iniciarQuestionario();

			flagQuestionarioIniciado = true;

			if ((!TSUtil.isEmpty(this.quizQuestionarioModel.getQuizGrupos()) || this.isCarregamentoAutomatico()) && flagCriarQuestionarioAutomatico) {

				this.questionarios.add(0, this.quizQuestionarioModel);
				return;

			}

		}

		if (TSUtil.isEmpty(getQuestionarios()) || (this.isQuestionarioComMovimentacao() && !getQuestionarios().get(0).getAtendimentoModel().equals(Utilitario.getAtendimentoSessao()))) {

			if (!flagQuestionarioIniciado) {
				this.iniciarQuestionario();
			}

			if (!TSUtil.isEmpty(this.quizQuestionarioModel.getQuizGrupos()) && flagCriarQuestionarioAutomatico) {
				this.questionarios.add(0, this.quizQuestionarioModel);
			}

		}

		if (!TSUtil.isEmpty(this.questionarios)) {

			this.quizQuestionarioModel = this.questionarios.get(0);

			if (TSUtil.isEmpty(this.quizQuestionarioModel.getId())) {

				this.iniciarQuestionario();

			}

			this.carregarRespostas();

		}

	}

	public void pesquisar() {
		this.questionarios = this.getQuizQuestionarioBS().pesquisarQuiz(this.quizQuestionarioModel, 50);
	}

	public String carregarCompleto() {
		this.questionarios = this.getQuizQuestionarioBS().pesquisarQuiz(this.quizQuestionarioModel, 999999);
		return null;
	}

	public String processarCampo(QuizModel quiz) {
		this.processarCampo(quiz, this.quizQuestionarioModel);
		return null;
	}

	protected void processarCampo(QuizModel quiz, T quizQuestionarioModel) {

		Map<String, String> mensagensValidacao = this.quizUtil.processarCampo(quiz, quizQuestionarioModel.getQuizGrupos(), false);

		Utilitario.tratarMensagensValidacaoQuiz(mensagensValidacao, null);

	}

	protected T instanciarQuestionario() {

		T quizQuestionarioModel = getInstanciaQuiz();
		quizQuestionarioModel.setTipoQuizModel(new TipoQuizModel());
		quizQuestionarioModel.getTipoQuizModel().setTabModel(this.tipoQuizBS.obterTab(this.getGrupoQuizId()));
		quizQuestionarioModel.getTipoQuizModel().setFuncaoModel(Utilitario.getUsuarioLogado().getFuncaoLogada());
		quizQuestionarioModel.setAtendimentoModel(Utilitario.getAtendimentoSessao());
		quizQuestionarioModel.setFlagConcluido(Boolean.FALSE);
		quizQuestionarioModel.setUsuarioCadastroModel(Utilitario.getUsuarioLogado());
		quizQuestionarioModel.setFuncaoModel(Utilitario.getUsuarioLogado().getFuncaoLogada());

		this.iniciarExtensaoQuestionario(quizQuestionarioModel);

		return quizQuestionarioModel;
	}

	protected void iniciarQuestionario() {

		this.quizQuestionarioModel = this.instanciarQuestionario();

		this.getQuizQuestionarioBS().iniciarQuiz(this.quizQuestionarioModel);

		if (TSUtil.isEmpty(this.quizQuestionarioModel.getQuizGrupos())) {

			this.quizQuestionarioModel.setQuizGrupos(new ArrayList<QuizGrupoModel>());

		}

	}

	protected void iniciarExtensaoQuestionario(T questionario) {

	}

	protected void carregarExtensaoRespostas() {

	}

	public void carregarRespostas() {

		if (!TSUtil.isEmpty(this.quizQuestionarioModel.getId())) {

			this.quizQuestionarioModel = this.getQuizQuestionarioBS().obterQuiz(this.quizQuestionarioModel);
			this.carregarExtensaoRespostas();

		}

	}

	protected boolean isQuestionarioComMovimentacao() {
		return true;
	}

	protected void preInsertQuiz() {
	}

	protected void prePersistQuiz(boolean flagConcluir) {
	}

	protected void preUpdateQuiz() {
	}

	protected void posInsertQuiz() {
	}

	protected void posPersistQuiz() {
	}

	protected boolean validaCampos(T quizQuestionarioModel) {

		Map<String, String> mensagensValidacao = this.quizUtil.validaCampos(quizQuestionarioModel, quizQuestionarioModel.getQuizGrupos());

		return Utilitario.tratarMensagensValidacaoQuiz(mensagensValidacao, null);

	}

	public String insertQuiz(boolean flagConcluir) {

		if (!this.validaCampos(this.quizQuestionarioModel)) {
			return null;
		}

		this.preInsertQuiz();

		this.prePersistQuiz(flagConcluir);

		this.quizQuestionarioModel.setDataCadastro(new Date());
		this.quizQuestionarioModel.setUsuarioCadastroModel(Utilitario.getUsuarioLogado());
		this.quizQuestionarioModel.setFuncaoModel(Utilitario.getUsuarioLogado().getFuncaoLogada());

		if (flagConcluir) {

			this.quizQuestionarioModel.setFlagConcluido(true);

			int index = this.questionarios.indexOf(this.quizQuestionarioModel);

			if (index > -1) {

				this.questionarios.get(index).setFlagConcluido(true);

			}

		}

		this.quizTemplateUtil.atualizarValoresCamposCalculaveis(this.quizQuestionarioModel);

		boolean sucesso = false;

		try {

			this.executeInsert();

			this.posInsertQuiz();

			this.posPersistQuiz();

			super.addInfoMessageKey(Constantes.OPERACAO_SUCESSO);

			sucesso = true;

		} catch (TSApplicationException e) {

			super.throwException(e);

		} catch (TSSystemException e) {

			e.printStackTrace();
			super.addErrorMessageKey("ERRO_INESPERADO");

		} finally {

			RequestContext.getCurrentInstance().addCallbackParam("valido", sucesso);

			if (!sucesso) {
				this.quizQuestionarioModel.setId(null);
				this.quizQuestionarioModel.setFlagConcluido(false);
			}

		}

		return null;

	}

	protected void executeInsert() throws TSApplicationException {
		this.getQuizQuestionarioBS().inserirQuiz(this.quizQuestionarioModel);
	}

	public String insertQuiz() {
		return this.insertQuiz(false);
	}

	public String concluir() {

		if (TSUtil.isEmpty(this.quizQuestionarioModel.getId())) {

			this.insertQuiz(true);

		} else {

			this.updateQuiz(true);

		}

		Boolean valido = (Boolean) RequestContext.getCurrentInstance().getCallbackParams().get("valido");

		if (this.isQuestionarioComMovimentacao() && (TSUtil.isEmpty(valido) || valido)) {

			if (Utilitario.getTabTemplateSessao().getFlagImpressaoAutomatica()) {

				this.imprimir();

			}

		}

		return null;
	}

	public String reabrir() {

		this.quizQuestionarioModel.setDataCadastro(new Date());
		this.quizQuestionarioModel.setUsuarioCadastroModel(Utilitario.getUsuarioLogado());

		this.quizQuestionarioModel.setFlagConcluido(false);

		int index = this.questionarios.indexOf(this.quizQuestionarioModel);

		if (index > -1) {
			this.questionarios.get(index).setFlagConcluido(false);
		}

		boolean sucesso = false;

		try {

			this.getQuizQuestionarioBS().reabrirQuiz(this.quizQuestionarioModel);

			super.addInfoMessageKey(Constantes.OPERACAO_SUCESSO);

			sucesso = true;

		} catch (TSApplicationException e) {

			super.throwException(e);

		} catch (TSSystemException e) {

			e.printStackTrace();
			super.addErrorMessageKey("ERRO_INESPERADO");

		} finally {

			RequestContext.getCurrentInstance().addCallbackParam("valido", sucesso);

			if (!sucesso) {
				this.quizQuestionarioModel.setFlagConcluido(true);
			}

		}

		return null;
	}

	public String updateQuiz() {
		return this.updateQuiz(false);
	}

	public String updateQuiz(boolean flagConcluir) {

		if (!this.validaCampos(this.quizQuestionarioModel)) {
			return null;
		}

		this.preUpdateQuiz();

		this.prePersistQuiz(flagConcluir);

		this.quizQuestionarioModel.setDataCadastro(new Date());
		this.quizQuestionarioModel.setUsuarioCadastroModel(Utilitario.getUsuarioLogado());
		this.quizQuestionarioModel.setFuncaoModel(Utilitario.getUsuarioLogado().getFuncaoLogada());

		if (flagConcluir) {

			this.quizQuestionarioModel.setFlagConcluido(true);

			int index = this.questionarios.indexOf(this.quizQuestionarioModel);

			if (index > -1) {

				this.questionarios.get(index).setFlagConcluido(true);

			}

		}

		this.quizTemplateUtil.atualizarValoresCamposCalculaveis(this.quizQuestionarioModel);

		boolean sucesso = false;

		try {

			this.executeUpdate();

			this.posPersistQuiz();

			super.addInfoMessageKey(Constantes.OPERACAO_SUCESSO);

			sucesso = true;

		} catch (TSApplicationException e) {

			super.throwException(e);

		} catch (TSSystemException e) {

			e.printStackTrace();
			super.addErrorMessageKey("ERRO_INESPERADO");

		} finally {

			RequestContext.getCurrentInstance().addCallbackParam("valido", sucesso);

			if (!sucesso) {
				this.quizQuestionarioModel.setFlagConcluido(false);
			}

		}

		return null;

	}

	protected void executeUpdate() throws TSApplicationException {
		this.getQuizQuestionarioBS().alterarQuiz(this.quizQuestionarioModel);
	}

	public String gravar(boolean concluir) {

		this.quizQuestionarioModel.setUsuarioCadastroModel(Utilitario.getUsuarioLogado());
		this.quizQuestionarioModel.setDataCadastro(new Date());

		if (TSUtil.isEmpty(this.quizQuestionarioModel.getId())) {

			this.insertQuiz();

		} else {
			this.updateQuiz(concluir);
		}

		return null;

	}
	
	protected boolean validarCriacaoQuestionario() {

		boolean valida = true;

		UsuarioModel usuario = Utilitario.getUsuarioLogado();

		for (T questionario : this.questionarios) {

			if (!questionario.getFlagConcluido() && questionario.getUsuarioCadastroModel().equals(usuario)) {
				valida = false;
				super.addErrorMessage("Este usuário já possui um questionário aberto para este paciente");
				break;
			}

		}

		return valida;
	}

	protected boolean validarCopiaQuestionario() {

		boolean valida = true;

		TipoQuizModel tipoQuiz = this.tipoQuizBS.obter(this.quizQuestionarioModel.getTipoQuizModel().getTabModel(), Utilitario.getUsuarioLogado().getFuncaoLogada());

		if (TSUtil.isEmpty(tipoQuiz) || !tipoQuiz.equals(this.quizQuestionarioModel.getTipoQuizModel())) {
			valida = false;
			super.addErrorMessage("Não é possível copiar o questionário de tipos diferentes");
		}

		UsuarioModel usuario = Utilitario.getUsuarioLogado();

		for (T questionario : this.questionarios) {

			if (!questionario.getFlagConcluido() && questionario.getUsuarioCadastroModel().equals(usuario)) {
				valida = false;
				super.addErrorMessage("Este usuário já possui um questionário aberto para este paciente");
				break;
			}

		}

		return valida;
	}

	public boolean isExibirBotaoCriar() {

		for (T questionario : this.questionarios) {

			if (TSUtil.isEmpty(questionario.getId())) {

				return true;

			}

		}

		return false;

	}

	public boolean isExibirBotaoCopiar() {
		return TSUtil.isEmpty(getQuizQuestionarioModel().getId());
	}

	protected void criarExtensaoQuestionario() {

	}

	public String criarQuestionario() {

		if (!this.validarCriacaoQuestionario()) {
			return null;
		}

		this.iniciarQuestionario();

		this.questionarios.add(0, this.quizQuestionarioModel);

		this.criarExtensaoQuestionario();

		return null;

	}

	protected void copiarCampos(T questionarioNovo) {

	}

	protected void finalizaCopia() {

	}

	public String copiarQuestionario() {

		if (!this.validarCopiaQuestionario()) {
			return null;
		}

		T questionario = this.getInstanciaQuiz();

		this.iniciarExtensaoQuestionario(questionario);

		questionario.setData(new Date());
		questionario.setFlagConcluido(false);
		questionario.setRespostas(new ArrayList<R>());

		this.copiarCampos(questionario);

		R resposta = null;

		for (R respostaAntiga : getQuizQuestionarioModel().getRespostas()) {

			resposta = this.getInstanciaResposta();

			resposta.setQuizModel(respostaAntiga.getQuizModel());
			resposta.getQuizModel().setRespostaEscolhida(null);
			resposta.getQuizModel().setRespostasEscolhidas(new ArrayList<String>());
			resposta.setQuizTemplate(questionario);

			questionario.getRespostas().add(resposta);

		}

		questionario.setTipoQuizModel(this.quizQuestionarioModel.getTipoQuizModel());
		questionario.setUsuarioCadastroModel(Utilitario.getUsuarioLogado());
		questionario.setFuncaoModel(Utilitario.getUsuarioLogado().getFuncaoLogada());

		if (this.isQuestionarioComMovimentacao()) {
			questionario.setAtendimentoModel(Utilitario.getAtendimentoSessao());
		}

		T quizCopia = this.getQuizQuestionarioBS().obterQuiz(this.quizQuestionarioModel);

		questionario.setQuizGrupos(quizCopia.getQuizGrupos());

		for (QuizGrupoModel grupo : questionario.getQuizGrupos()) {

			for (QuizModel quiz : grupo.getQuizes()) {

				if (!TSUtil.isEmpty(quiz.getFlagCopia()) && !quiz.getFlagCopia()) {

					quiz.limparResposta();

				}

			}

		}

		this.quizQuestionarioModel = questionario;

		this.questionarios.add(0, this.quizQuestionarioModel);

		this.finalizaCopia();

		return null;

	}

	public String imprimir() {

		try {

			Map<String, Object> parametros = new HashMap<String, Object>();

			parametros.put("ID", getQuizQuestionarioModel().getId());

			JasperUtil jasperUtil = new JasperUtil();

			parametros.put("SUBREPORT_DIR", Utilitario.obterPastaRelatorio());

			if (TSUtil.isEmpty(this.quizQuestionarioModel.getTipoQuizModel().getRelatorio())) {

				jasperUtil.gerarRelatorio(getNomeRelatorio(), parametros);

			} else {

				jasperUtil.gerarRelatorio(this.quizQuestionarioModel.getTipoQuizModel().getRelatorio(), parametros);

			}

		} catch (Exception ex) {
			this.addErrorMessageKey("ERRO_INESPERADO");
			ex.printStackTrace();
		}

		return null;
	}

	public String excluir(T model) {

		this.questionarios.remove(model);

		if (!TSUtil.isEmpty(this.questionarios)) {
			this.quizQuestionarioModel = this.questionarios.get(0);
			this.carregarRespostas();
		}

		return null;
	}

	public String converterUnidade(CamposRespostaAb quiz) {

		quiz.setDoubleEscolhido(this.quizUtil.obterValorConvertido(quiz));
		quiz.popularReferenciaPontoFlutuante();
		quiz.setUnidadeAnterior(quiz.getUnidade());

		return null;
	}

	@Override
	public String addInputText(QuizModel quiz) {

		if (TSUtil.isEmpty(quiz.getRespostaEscolhida())) {
			super.addErrorMessage(quiz.getApelido() + ": Campo obrigatório");
			return null;
		}

		if (quiz.getRespostasEscolhidas() == null) {
			quiz.setRespostasEscolhidas(new ArrayList<String>());
		}

		quiz.getRespostasEscolhidas().add(quiz.getRespostaEscolhida());

		quiz.setRespostaEscolhida(null);

		return null;
	}

	@Override
	public String removeInputText(QuizModel quiz, String inputText) {
		quiz.getRespostasEscolhidas().remove(inputText);
		return null;
	}
	
	@Override
	public String subir(QuizModel quiz, String resposta) {
		Utilitario.ordenarListaPraCima(quiz.getRespostasEscolhidas(), resposta);
		return null;
	}

	@Override
	public String descer(QuizModel quiz, String resposta) {
		Utilitario.ordenarListaPraBaixo(quiz.getRespostasEscolhidas(), resposta);
		return null;
	}

	public T getQuizQuestionarioModel() {
		return quizQuestionarioModel;
	}

	public void setQuizQuestionarioModel(T quizQuestionarioModel) {
		this.quizQuestionarioModel = quizQuestionarioModel;
	}

	public List<T> getQuestionarios() {
		return questionarios;
	}

	public void setQuestionarios(List<T> questionarios) {
		this.questionarios = questionarios;
	}

	public QuizGrupoBS getQuizGrupoBS() {
		return quizGrupoBS;
	}

}
