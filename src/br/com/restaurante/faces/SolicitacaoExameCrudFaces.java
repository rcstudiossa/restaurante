package br.com.restaurante.faces;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;

import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import br.com.restaurante.business.AtendimentoBS;
import br.com.restaurante.business.CrudBS;
import br.com.restaurante.business.FuncionarioBS;
import br.com.restaurante.business.PerfilBS;
import br.com.restaurante.business.ProcedimentoBS;
import br.com.restaurante.business.SolicitacaoExameBS;
import br.com.restaurante.model.AtendimentoModel;
import br.com.restaurante.model.CidModel;
import br.com.restaurante.model.FuncionarioModel;
import br.com.restaurante.model.PerfilModel;
import br.com.restaurante.model.PerfilProcedimentoModel;
import br.com.restaurante.model.ProcedimentoModel;
import br.com.restaurante.model.QuizGrupoModel;
import br.com.restaurante.model.SolicitacaoExameItemModel;
import br.com.restaurante.model.SolicitacaoExameItemResultadoModel;
import br.com.restaurante.model.SolicitacaoExameModel;
import br.com.restaurante.model.UsuarioModel;
import br.com.restaurante.util.Constantes;
import br.com.restaurante.util.ExameUtil;
import br.com.restaurante.util.JasperUtil;
import br.com.restaurante.util.Utilitario;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.exception.TSSystemException;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.util.TSFacesUtil;

@SuppressWarnings("serial")
public class SolicitacaoExameCrudFaces<T extends Serializable> extends CrudFaces<SolicitacaoExameModel> {

	@EJB
	protected SolicitacaoExameBS solicitacaoExameBS;

	@EJB
	protected FuncionarioBS funcionarioBS;

	@EJB
	protected AtendimentoBS atendimentoBS;

	@EJB
	private ProcedimentoBS procedimentoBS;

	@EJB
	private PerfilBS perfilBS;

	protected ExameUtil exameUtil;

	protected ProcedimentoModel procedimentoModel;

	protected SolicitacaoExameItemModel solicitacaoExameItemModel;
	protected SolicitacaoExameModel solicitacaoExameSelecionadaModel;

	private TreeNode arvoreExames;

	@Override
	protected void clearFields() {
		this.exameUtil = new ExameUtil();
	}

	protected SolicitacaoExameModel getInstanceSolicitacaoExame() {

		SolicitacaoExameModel model = new SolicitacaoExameModel();

		model.setFuncionarioModel(new FuncionarioModel());
		model.setSolicitanteModel(new UsuarioModel());
		model.setOrigemModel(Utilitario.getOrigemAtual());
		model.setAtendimentoModel(new AtendimentoModel());
		model.setDataAgendamento(new Date());
		model.setCidModel(new CidModel());

		model.setExames(new ArrayList<SolicitacaoExameItemModel>());

		return model;
	}

	public String instanciarSolicitacaoAvulsa() {

		this.crudModel = this.getInstanceSolicitacaoExame();

		if (!TSUtil.isEmpty(Utilitario.getFuncionarioSessao())) {
			this.crudModel.getFuncionarioModel().setId(Utilitario.getFuncionarioSessao().getId());
			this.crudModel.getFuncionarioModel().setMatricula(Utilitario.getFuncionarioSessao().getMatricula());
			this.crudModel.getFuncionarioModel().setNome(Utilitario.getFuncionarioSessao().getNome());
			this.crudModel.getFuncionarioModel().setDataNascimento(Utilitario.getFuncionarioSessao().getDataNascimento());
			this.crudModel.getFuncionarioModel().setSexo(Utilitario.getFuncionarioSessao().getSexo());
		}

		return null;
	}

	public List<ProcedimentoModel> buscaExame(String query) {
		return this.procedimentoBS.pesquisarAutoComplete(query);
	}

	protected SolicitacaoExameItemModel adicionarProcedimento() {

		SolicitacaoExameItemModel model = new SolicitacaoExameItemModel();

		model.setSolicitacaoExameModel(this.crudModel);
		model.setFlagAtivo(true);
		model.setProcedimentoModel(this.procedimentoBS.obterSimples(this.procedimentoModel));
		model.setPerfilModel(new PerfilModel());

		if (this.crudModel.getExames().contains(model)) {
			Utilitario.addWarnMessage("Atenção! Exame já adicionado anteriormente. Remova-o caso necessário");
		}

		model.setResultados(this.solicitacaoExameBS.carregarResultados2(model));

		this.solicitacaoExameItemModel = model;

		this.crudModel.getExames().add(model);

		this.carregarResultados(model);

		return model;

	}

	public String carregarResultados(SolicitacaoExameItemModel exame) {

		this.arvoreExames = new DefaultTreeNode("root", null);

		exame.setFlagSelecionado(true);
		exame.getProcedimentoModel().setFlagSelecionado(true);
		exame.setSolicitacaoRaizAux(exame);

		this.carregarFilhosProcedimentos(exame, this.arvoreExames);

		return null;
	}

	public void carregarFilhosProcedimentos(SolicitacaoExameItemModel model, TreeNode noRaiz) {

		TreeNode noProcedimento = null;

		if (TSUtil.isEmpty(noRaiz.getParent())) {

			noProcedimento = new DefaultTreeNode("procedimento", model, noRaiz);
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

		// model.setResultados(this.solicitacaoExameBS.pesquisarResultadosExamesPais(model));

		for (SolicitacaoExameItemResultadoModel resultado : model.getResultados()) {

			resultado.setSolicitacaoExameItemModel(model);

			this.carregarArvoreResultados(resultado, noProcedimento);

		}

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

	public String addProcedimento() {

		if (TSUtil.isEmpty(this.procedimentoModel) || TSUtil.isEmpty(this.procedimentoModel.getId())) {
			super.addErrorMessage("Selecione o Procedimento");
			return null;
		}

		if (this.procedimentoModel.getTipo().equals("PROCEDIMENTO")) {

			this.adicionarProcedimento();

		} else if (this.procedimentoModel.getTipo().equals("PERFIL")) {

			PerfilModel perfilModel = this.perfilBS.obterCrudModel(new PerfilModel(this.procedimentoModel.getId()));

			for (PerfilProcedimentoModel procPerfil : perfilModel.getProcedimentos()) {

				SolicitacaoExameItemModel model = new SolicitacaoExameItemModel();

				model.setSolicitacaoExameModel(this.crudModel);
				model.setFlagAtivo(true);
				model.setProcedimentoModel(procPerfil.getProcedimentoModel());
				model.setPerfilModel(perfilModel);

				model.setResultados(this.solicitacaoExameBS.carregarResultados2(model));

				this.solicitacaoExameItemModel = model;

				this.crudModel.getExames().add(model);

				this.carregarResultados(model);

			}

		}

		this.procedimentoModel = null;

		return null;
	}

	public String removerProcedimento(SolicitacaoExameItemModel model) {

		if (!TSUtil.isEmpty(model.getId())) {

			super.addInfoMessageKey(Constantes.OPERACAO_SUCESSO);

		}

		this.crudModel.getExames().remove(model);

		return null;
	}

	private boolean validaCamposSolicitacaoAvulsa() {

		boolean valida = true;

		if (TSUtil.isEmpty(this.crudModel.getExames())) {
			valida = false;
			super.addErrorMessage("Adicione ao menos 1 exame");
		}

		return valida;
	}

	public String salvarSolicitacao() {

		if (!this.validaCamposSolicitacaoAvulsa()) {
			return null;
		}

		this.crudModel.setDataCadastro(new Date());
		this.crudModel.setUsuarioCadastroModel(Utilitario.getUsuarioLogado());
		this.crudModel.setDataAtualizacao(new Date());
		this.crudModel.setUsuarioAtualizacaoModel(Utilitario.getUsuarioLogado());

		try {

			if (TSUtil.isEmpty(this.crudModel.getId())) {

				this.solicitacaoExameBS.inserirCrudModel(this.crudModel);

			} else {

				this.solicitacaoExameBS.alterarCrudModel(this.crudModel);

			}

			RequestContext.getCurrentInstance().addCallbackParam("valido", true);

			this.find();

			super.addInfoMessageKey(Constantes.OPERACAO_SUCESSO);

		} catch (TSApplicationException e) {

			super.throwException(e);

		}

		return null;
	}

	public String cancelarSolicitacaoExame() {

		RequestContext.getCurrentInstance().addCallbackParam("valido", true);

		super.addInfoMessageKey(Constantes.OPERACAO_SUCESSO);

		return null;
	}

	public String carregarSolicitacao(SolicitacaoExameModel model) {

		this.crudModel = this.solicitacaoExameBS.obterCrudModel(model);

		Utilitario.atualizarFuncionarioSessao(this.funcionarioBS.obterCrudModel(model.getFuncionarioModel()));
		Utilitario.atualizarAtendimentoSessao(this.atendimentoBS.obterUltimoAtendimento(model.getFuncionarioModel(), Utilitario.getOrigemAtual()));

		return null;
	}

	public String limparExames() {
		this.crudModel.getExames().clear();
		return null;
	}

	public String imprimirSolicitacao() {

		try {

			Map<String, Object> parametros = new HashMap<String, Object>();

			parametros.put("ID", this.crudModel.getId());
			parametros.put("SUBREPORT_DIR", TSFacesUtil.getServletContext().getRealPath("WEB-INF" + File.separator + Constantes.PASTA_RELATORIO) + File.separator);

			new JasperUtil().gerarRelatorio("solicitacaoExame.jasper", parametros);

		} catch (TSSystemException ex) {
			super.addErrorMessage("Erro ao imprimir a solicitação de exame, contate o administrador do sistema");
			ex.printStackTrace();
		}

		return null;

	}

	protected Map<String, Object> obterParametrosRelatorio() {

		Map<String, Object> parametros = new HashMap<String, Object>();

		parametros.put("SUBREPORT_DIR", TSFacesUtil.getServletContext().getRealPath("WEB-INF" + File.separator + Constantes.PASTA_RELATORIO) + File.separator);

		return parametros;

	}

	public String imprimirResultado(SolicitacaoExameModel model, boolean flagImprimirResultadosAnteriores) {

		Map<String, Object> parametros = this.obterParametrosRelatorio();

		parametros.put("SE_ID", model.getId());
		parametros.put("FLAG_EXIBIR_RESULTADO_ANTERIOR", flagImprimirResultadosAnteriores);

		List<Long> idsSolicitacoes = new ArrayList<Long>();

		List<SolicitacaoExameItemModel> examesImpressao = new ArrayList<SolicitacaoExameItemModel>();

		for (SolicitacaoExameItemModel solicitacao : model.getExames()) {

			examesImpressao.add(solicitacao);
			idsSolicitacoes.add(solicitacao.getId());

		}

		if (TSUtil.isEmpty(idsSolicitacoes)) {
			super.addErrorMessage("Selecione ao menos um exame para impressão");
			return null;
		}

		parametros.put("IDS_SOLICITACOES", idsSolicitacoes);

		new JasperUtil().gerarRelatorio("resultadoLaboratorioNovo.jasper", parametros);

		this.find();

		RequestContext.getCurrentInstance().addCallbackParam("valido", true);

		return null;
	}

	@Override
	protected CrudBS<SolicitacaoExameModel> getCrudBS() {
		return this.solicitacaoExameBS;
	}

	public ProcedimentoModel getProcedimentoModel() {
		return procedimentoModel;
	}

	public void setProcedimentoModel(ProcedimentoModel procedimentoModel) {
		this.procedimentoModel = procedimentoModel;
	}

	public SolicitacaoExameItemModel getSolicitacaoExameItemModel() {
		return solicitacaoExameItemModel;
	}

	public void setSolicitacaoExameItemModel(SolicitacaoExameItemModel solicitacaoExameItemModel) {
		this.solicitacaoExameItemModel = solicitacaoExameItemModel;
	}

	public SolicitacaoExameModel getSolicitacaoExameSelecionadaModel() {
		return solicitacaoExameSelecionadaModel;
	}

	public void setSolicitacaoExameSelecionadaModel(SolicitacaoExameModel solicitacaoExameSelecionadaModel) {
		this.solicitacaoExameSelecionadaModel = solicitacaoExameSelecionadaModel;
	}

	public TreeNode getArvoreExames() {
		return arvoreExames;
	}

	public void setArvoreExames(TreeNode arvoreExames) {
		this.arvoreExames = arvoreExames;
	}

}
