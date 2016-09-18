package br.com.restaurante.faces;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import br.com.restaurante.business.ComboBS;
import br.com.restaurante.business.SolicitacaoExameBS;
import br.com.restaurante.business.ValidadorBS;
import br.com.restaurante.model.FuncionarioModel;
import br.com.restaurante.model.PerfilModel;
import br.com.restaurante.model.QuizGrupoModel;
import br.com.restaurante.model.SolicitacaoExameItemModel;
import br.com.restaurante.model.SolicitacaoExameItemResultadoModel;
import br.com.restaurante.model.SolicitacaoExameModel;
import br.com.restaurante.model.ValidadorModel;
import br.com.restaurante.util.Constantes;
import br.com.restaurante.util.Utilitario;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

@ViewScoped
@SuppressWarnings("serial")
@ManagedBean(name = "solicitacaoExameFuncionarioFaces")
public class SolicitacaoExameFuncionarioFaces extends SolicitacaoExameCrudFaces<SolicitacaoExameModel> {

	@EJB
	private SolicitacaoExameBS solicitacaoExameBS;

	@EJB
	private ValidadorBS validadorBS;

	@EJB
	private ComboBS comboBS;

	private TreeNode arvoreExames;
	private TreeNode arvoreResultados;
	private SolicitacaoExameItemResultadoModel resultadoSelecionado;

	private List<SelectItem> comboCid;

	@Override
	@PostConstruct
	protected void clearFields() {

		super.clearFields();

		FuncionarioModel funcionario = Utilitario.getFuncionarioSessao();

		this.grid = this.solicitacaoExameBS.pesquisar(funcionario, Utilitario.getOrigemAtual());

		if (!TSUtil.isEmpty(this.grid)) {

			this.crudModel = this.grid.get(0);

			this.carregar();

		}

		this.comboCid = super.initCombo(this.comboBS.pesquisarCids(), "id", "descricaoCompleta");

	}

	@Override
	protected String find() {
		return null;
	}

	public String carregar() {

		if (!TSUtil.isEmpty(this.crudModel.getId())) {

			this.crudModel = this.solicitacaoExameBS.obterCrudModel(this.crudModel);

			super.posDetail();

			this.carregarArvore();

		}

		return null;
	}

	private void carregarArvore() {

		this.arvoreResultados = new DefaultTreeNode("root", null);

		for (SolicitacaoExameItemModel model : this.crudModel.getExames()) {

			model.setFlagSelecionado(true);
			model.getProcedimentoModel().setFlagSelecionado(true);
			model.setSolicitacaoRaizAux(model);

			this.carregarFilhosProcedimentos(model, this.arvoreResultados);

		}

	}

	public String carregarResultadosItem(SolicitacaoExameItemModel model) {

		this.solicitacaoExameSelecionadaModel = this.solicitacaoExameBS.obterCrudModel(model.getSolicitacaoExameModel());

		this.arvoreResultados = new DefaultTreeNode("root", null);

		for (SolicitacaoExameItemModel exame : this.solicitacaoExameSelecionadaModel.getExames()) {

			exame.setFlagSelecionado(true);
			exame.getProcedimentoModel().setFlagSelecionado(true);
			exame.setSolicitacaoRaizAux(exame);

			this.carregarFilhosProcedimentos(exame, this.arvoreResultados);

		}

		return null;

	}

	public void carregarFilhosProcedimentos(SolicitacaoExameItemModel model, TreeNode noRaiz) {

		TreeNode noProcedimento = null;
		TreeNode noPerfil = null;
		
		for (TreeNode n : noRaiz.getChildren()) {

			if (n.getData() instanceof PerfilModel) {

				if (((PerfilModel) n.getData()).getDescricao().equals(model.getPerfilModel().getDescricao())) {
					noPerfil = n;
				}

			}
			
		}
		
		if(noPerfil == null && !TSUtil.isEmpty(model.getPerfilModel().getId())){
			noPerfil = new DefaultTreeNode("perfil", model.getPerfilModel(), noRaiz);
			noPerfil.setExpanded(true);
		}

		if (TSUtil.isEmpty(noRaiz.getParent())) {
			
			if(!TSUtil.isEmpty(noPerfil)){
				
				noProcedimento = new DefaultTreeNode("procedimento", model, noPerfil);
				
			} else {
				
				noProcedimento = new DefaultTreeNode("procedimento", model, noRaiz);
				
			}
			
			noProcedimento.setExpanded(true);

		} else {

			noProcedimento = new DefaultTreeNode("procedimento", model.getProcedimentoModel().getDescricao(), noRaiz);
			noProcedimento.setExpanded(true);

		}

		model.setProcedimentosFilhos(this.solicitacaoExameBS.pesquisarProcedimentosFilhos(model));

		for (SolicitacaoExameItemModel solicitacao : model.getProcedimentosFilhos()) {

			solicitacao.setSolicitacaoRaizAux(model.getSolicitacaoRaizAux());

			this.carregarFilhosProcedimentos(solicitacao, noProcedimento);

		}

		model.setResultados(this.solicitacaoExameBS.pesquisarResultadosExamesPais(model));

		for (SolicitacaoExameItemResultadoModel resultado : model.getResultados()) {

			resultado.setSolicitacaoExameItemModel(model);

			this.carregarArvoreResultados(resultado, noProcedimento);

		}

	}

	public void marcarResultadoNormal(SolicitacaoExameItemModel model) {
		model.setFlagResultadoAlterado(!model.getFlagResultadoNormal());
	}

	public void marcarResultadoAlterado(SolicitacaoExameItemModel model) {
		model.setFlagResultadoNormal(!model.getFlagResultadoAlterado());
	}

	private void carregarArvoreResultados(SolicitacaoExameItemResultadoModel resultado, TreeNode noRaiz) {

		TreeNode noGrupo = null;
		TreeNode noExame = null;

		if (!TSUtil.isEmpty(resultado.getQuizPerguntaModel().getGrupoSubexameModel().getId())) {

			for (TreeNode n : noRaiz.getChildren()) {

				if (n.getData().getClass().equals(QuizGrupoModel.class) && ((QuizGrupoModel) n.getData()).equals(resultado.getQuizPerguntaModel().getGrupoSubexameModel())) {
					noGrupo = n;
					break;
				}

			}

			if (TSUtil.isEmpty(noGrupo)) {
				noGrupo = new DefaultTreeNode("grupo", resultado.getQuizPerguntaModel().getGrupoSubexameModel(), noRaiz);
				noGrupo.setExpanded(true);
			}

		}

		if (TSUtil.isEmpty(noGrupo)) {

			noExame = new DefaultTreeNode("exame", resultado, noRaiz);
			noExame.setExpanded(true);

		} else {

			noExame = new DefaultTreeNode("exame", resultado, noGrupo);
			noExame.setExpanded(true);

		}

		this.exameUtil.carregarValoresReferencia(resultado);

		resultado.setFilhos(this.solicitacaoExameBS.pesquisarResultadosExamesFilhos(resultado));

		for (SolicitacaoExameItemResultadoModel resultadoFilho : resultado.getFilhos()) {

			resultadoFilho.setSolicitacaoExameItemModel(resultado.getSolicitacaoExameItemModel());
			resultadoFilho.setResultadoPai(resultado);

			this.carregarArvoreResultados(resultadoFilho, noExame);

		}

	}

	public void calcularValorExame(SolicitacaoExameItemResultadoModel model) {

		if (TSUtil.isEmpty(model.getPercentual())) {
			model.setDoubleEscolhido(null);
			this.processarCampoResultado(model);
		}

		if (!TSUtil.isEmpty(model.getResultadoPai()) && !TSUtil.isEmpty(model.getResultadoPai().getRespostaDada()) && Utilitario.isNumeric(model.getResultadoPai().getRespostaDada()) && !TSUtil.isEmpty(model.getPercentual())) {

			BigDecimal percentual = new BigDecimal(model.getResultadoPai().getRespostaDada()).multiply(new BigDecimal(model.getPercentual())).divide(new BigDecimal(100));

			model.setRespostaDada(percentual.setScale(2, BigDecimal.ROUND_HALF_UP).toString());

			this.processarCampoResultado(model);

		}

	}

	public void processarCampoResultado(SolicitacaoExameItemResultadoModel model) {

		if (!TSUtil.isEmpty(model.getQuizPerguntaModel().getFlagPercentual()) && model.getQuizPerguntaModel().getFlagPercentual()) {
			this.calcularPercentualExame(model);
		}

		List<SolicitacaoExameItemResultadoModel> subexames = this.popularListaExamesCompleto(model.getSolicitacaoExameItemModel().getSolicitacaoRaizAux());

		this.exameUtil.processarCampo(model, subexames, false);

		if (!TSUtil.isEmpty(model.getDoubleEscolhido()) && !TSUtil.isEmpty(model.getQuizPerguntaModel().getReferenciaMinima()) && !TSUtil.isEmpty(model.getQuizPerguntaModel().getReferenciaMaxima())) {

			if (model.getDoubleEscolhido() < model.getQuizPerguntaModel().getReferenciaMinima() || model.getDoubleEscolhido() > model.getQuizPerguntaModel().getReferenciaMaxima()) {
				Utilitario.addWarnMessage((TSUtil.isEmpty(model.getQuizPerguntaModel().getApelido()) ? model.getQuizPerguntaModel().getPergunta() : model.getQuizPerguntaModel().getApelido()) + " fora da faixa de referência");
			}

		}

	}

	private List<SolicitacaoExameItemResultadoModel> popularListaExamesCompleto(SolicitacaoExameItemModel model) {

		List<SolicitacaoExameItemResultadoModel> exames = new ArrayList<SolicitacaoExameItemResultadoModel>();

		for (SolicitacaoExameItemResultadoModel examePai : model.getResultados()) {

			exames.add(examePai);

			this.popularListaExamesCompletoFilhos(examePai, exames);

		}

		for (SolicitacaoExameItemModel item : model.getProcedimentosFilhos()) {

			exames.addAll(this.popularListaExamesCompleto(item));

		}

		return exames;

	}

	private void popularListaExamesCompletoFilhos(SolicitacaoExameItemResultadoModel exame, List<SolicitacaoExameItemResultadoModel> examesFilhos) {

		for (SolicitacaoExameItemResultadoModel filho : exame.getFilhos()) {

			examesFilhos.add(filho);

			this.popularListaExamesCompletoFilhos(filho, examesFilhos);

		}

	}

	public void calcularPercentualExame(SolicitacaoExameItemResultadoModel model) {

		if (TSUtil.isEmpty(model.getRespostaDada())) {
			model.setPercentual(null);
		}

		if (!TSUtil.isEmpty(model.getResultadoPai()) && !TSUtil.isEmpty(model.getResultadoPai().getRespostaDada()) && Utilitario.isNumeric(model.getResultadoPai().getRespostaDada()) && !TSUtil.isEmpty(model.getRespostaDada()) && Utilitario.isNumeric(model.getRespostaDada())) {

			BigDecimal percentual = new BigDecimal(model.getRespostaDada()).divide(new BigDecimal(model.getResultadoPai().getRespostaDada()), 5, BigDecimal.ROUND_HALF_UP);

			model.setPercentual(percentual.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

		}

	}

	public String criar() {
		return this.addExame();
	}

	public String addExame() {

		super.instanciarSolicitacaoAvulsa();

		this.crudModel.setAtendimentoModel(Utilitario.getAtendimentoSessao());
		this.crudModel.setSolicitanteModel(Utilitario.getUsuarioLogado());

		this.grid.add(0, this.crudModel);

		this.carregarArvore();

		return null;

	}

	public String copiar() {

		SolicitacaoExameModel model = this.crudModel;

		this.instanciarSolicitacaoAvulsa();

		this.crudModel.setAtendimentoModel(Utilitario.getAtendimentoSessao());
		this.crudModel.setSolicitanteModel(Utilitario.getUsuarioLogado());
		this.crudModel.setObservacao(model.getObservacao());

		for (SolicitacaoExameItemModel exame : model.getExames()) {

			super.setProcedimentoModel(exame.getProcedimentoModel());

			super.adicionarProcedimento();

		}

		this.carregarArvore();

		this.grid.add(0, this.crudModel);

		super.setProcedimentoModel(null);

		return null;

	}

	public boolean validaCampos(SolicitacaoExameItemModel solicitacaoExame) {

		boolean valida = true;

		List<SolicitacaoExameItemResultadoModel> exames = this.popularListaExamesCompleto(solicitacaoExame);

		for (SolicitacaoExameItemResultadoModel exame : exames) {

			List<ValidadorModel> validadores = this.validadorBS.pesquisar(exame.getQuizPerguntaModel().getId());

			valida = this.exameUtil.validar(exame, exames, validadores, true) && valida;

		}

		return valida;
	}

	private boolean validaCamposConclusao(SolicitacaoExameItemModel model) {

		boolean valida = true;

		if (model.getProcedimentoModel().getFlagSelecionado()) {

			valida = this.validaCampos(model) && valida;

			if (!TSUtil.isEmpty(model.getProcedimentosFilhos())) {

				valida = this.validarCamposProcedimento(model) && valida;

			} else {

				valida = this.validarCamposExame(model.getResultados()) && valida;

			}

		}

		return valida;

	}

	private boolean validarCamposProcedimento(SolicitacaoExameItemModel model) {

		boolean valida = true;

		for (SolicitacaoExameItemModel resultado : model.getProcedimentosFilhos()) {

			valida = this.validarCamposProcedimento(resultado);

			if (!valida) {
				return valida;
			}

		}

		if (!TSUtil.isEmpty(model.getResultados())) {
			valida = this.validarCamposExame(model.getResultados());
		}

		return valida;
	}

	private boolean validarCamposExame(List<SolicitacaoExameItemResultadoModel> resultados) {

		boolean retorno = true;

		for (SolicitacaoExameItemResultadoModel exame : resultados) {

			if (TSUtil.isEmpty(exame.getRespostaDada()) && exame.getQuizPerguntaModel().getFlagObrigatorio()) {
				super.addErrorMessage("Para concluir é necessário um valor de resultado para o exame " + (!TSUtil.isEmpty(exame.getResultadoPai()) ? exame.getResultadoPai().getQuizPerguntaModel().getNomePergunta() : "A") + " - " + exame.getQuizPerguntaModel().getNomePergunta());
				retorno = false;
			}

			this.carregarZerosAutomaticos(exame);

			if (!TSUtil.isEmpty(exame.getFilhos())) {

				retorno = this.validarCamposExame(exame.getFilhos());

				if (!retorno) {

					return retorno;

				}

			}

		}

		return retorno;

	}

	private void carregarZerosAutomaticos(SolicitacaoExameItemResultadoModel exame) {

		if (!TSUtil.isEmpty(exame.getQuizPerguntaModel().getFlagPercentual()) && exame.getQuizPerguntaModel().getFlagPercentual() && !TSUtil.isEmpty(exame.getQuizPerguntaModel().getFlagObrigatorio()) && !exame.getQuizPerguntaModel().getFlagObrigatorio()) {

			if (TSUtil.isEmpty(exame.getRespostaDada()) && TSUtil.isEmpty(exame.getPercentual())) {

				exame.setPercentual(0.0);
				exame.setRespostaDada("0.0");

			}

		}

	}

	public String concluir() {

		boolean valida = true;

		for (SolicitacaoExameItemModel item : this.crudModel.getExames()) {

			if (item.isFlagSelecionado()) {

				valida = this.validaCamposConclusao(item) && valida;

			}

		}

		if (!valida) {
			return null;
		}

		try {

			this.crudModel.setDataAtualizacao(new Date());
			this.crudModel.setUsuarioAtualizacaoModel(Utilitario.getUsuarioLogado());

			this.solicitacaoExameBS.concluir(this.crudModel);

			super.addInfoMessageKey(Constantes.OPERACAO_SUCESSO);

		} catch (TSApplicationException e) {

			super.throwException(e);

		}

		return null;

	}

	public String excluirSolicitacaoExame(SolicitacaoExameModel model) {

		this.grid.remove(model);

		if (!TSUtil.isEmpty(this.grid)) {
			this.crudModel = this.grid.get(0);
			this.carregar();
		}

		return null;
	}

	@Override
	public String addProcedimento() {
		super.addProcedimento();
		this.carregarArvore();
		return null;
	}

	@Override
	public String removerProcedimento(SolicitacaoExameItemModel model) {
		super.removerProcedimento(model);
		this.carregarArvore();
		return null;
	}

	@Override
	public String cancelarSolicitacaoExame() {
		super.cancelarSolicitacaoExame();
		return null;
	}

	@Override
	protected SolicitacaoExameModel getInstanceSolicitacaoExame() {
		SolicitacaoExameModel model = super.getInstanceSolicitacaoExame();
		model.setFuncionarioModel(Utilitario.getFuncionarioSessao());
		model.setAtendimentoModel(Utilitario.getAtendimentoSessao());
		return model;
	}

	@Override
	public String limparExames() {
		super.limparExames();
		this.carregarArvore();
		return null;
	}

	public TreeNode getArvoreExames() {
		return arvoreExames;
	}

	public void setArvoreExames(TreeNode arvoreExames) {
		this.arvoreExames = arvoreExames;
	}

	public TreeNode getArvoreResultados() {
		return arvoreResultados;
	}

	public void setArvoreResultados(TreeNode arvoreResultados) {
		this.arvoreResultados = arvoreResultados;
	}

	public SolicitacaoExameItemResultadoModel getResultadoSelecionado() {
		return resultadoSelecionado;
	}

	public void setResultadoSelecionado(SolicitacaoExameItemResultadoModel resultadoSelecionado) {
		this.resultadoSelecionado = resultadoSelecionado;
	}

	public List<SelectItem> getComboCid() {
		return comboCid;
	}

	public void setComboCid(List<SelectItem> comboCid) {
		this.comboCid = comboCid;
	}

}
