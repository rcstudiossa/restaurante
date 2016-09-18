package br.com.restaurante.model;

import java.util.Date;
import java.util.List;

import br.com.topsys.util.TSUtil;

@SuppressWarnings("serial")
public class SolicitacaoDocumentoModel extends BaseModel implements CrudModel<SolicitacaoDocumentoModel> {

	private TipoSolicitacaoDocumentoModel tipoSolicitacaoDocumentoModel;
	private FuncaoModel funcaoModel;
	private AtendimentoModel atendimentoModel;
	private String texto;
	private Boolean flagConcluido;
	private QuizQuestionarioModel quizQuestionarioModel;
	private String motivoCancelamento;
	private List<SolicitacaoDocumentoModel> historico;
	
	private Date dataInicial;
	private Date dataFinal;
	
	public SolicitacaoDocumentoModel() {
		super();
	}

	public SolicitacaoDocumentoModel(AtendimentoModel atendimentoModel) {
		super();
		this.atendimentoModel = atendimentoModel;
	}

	public SolicitacaoDocumentoModel(AtendimentoModel atendimentoModel, UsuarioModel usuarioCadastroModel) {
		super();
		this.usuarioCadastroModel = usuarioCadastroModel;
		this.atendimentoModel = atendimentoModel;
	}

	public String getSituacao() {
		return !TSUtil.isEmpty(flagAtivo) && flagAtivo.equals(Boolean.TRUE) ? "Ativo" : "Inativo";
	}

	public TipoSolicitacaoDocumentoModel getTipoSolicitacaoDocumentoModel() {
		return tipoSolicitacaoDocumentoModel;
	}

	public void setTipoSolicitacaoDocumentoModel(TipoSolicitacaoDocumentoModel tipoSolicitacaoDocumentoModel) {
		this.tipoSolicitacaoDocumentoModel = tipoSolicitacaoDocumentoModel;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Boolean getFlagConcluido() {
		return flagConcluido;
	}

	public void setFlagConcluido(Boolean flagConcluido) {
		this.flagConcluido = flagConcluido;
	}

	public QuizQuestionarioModel getQuizQuestionarioModel() {
		return quizQuestionarioModel;
	}

	public void setQuizQuestionarioModel(QuizQuestionarioModel quizQuestionarioModel) {
		this.quizQuestionarioModel = quizQuestionarioModel;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public FuncaoModel getFuncaoModel() {
		return funcaoModel;
	}

	public void setFuncaoModel(FuncaoModel funcaoModel) {
		this.funcaoModel = funcaoModel;
	}

	public String getMotivoCancelamento() {
		return motivoCancelamento;
	}

	public void setMotivoCancelamento(String motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
	}
	
	public String getStatus(){
		return !this.flagAtivo ? "CANCELADO" : this.flagConcluido ? "CONCLUÍDO" : "ABERTO"; 
	}
	
	public String getCss(){
		return !this.flagAtivo ? "estiloVermelho" : this.flagConcluido ? "estiloVerde" : "estiloAzul"; 
	}

	public AtendimentoModel getAtendimentoModel() {
		return atendimentoModel;
	}

	public void setAtendimentoModel(AtendimentoModel atendimentoModel) {
		this.atendimentoModel = atendimentoModel;
	}

	public List<SolicitacaoDocumentoModel> getHistorico() {
		return historico;
	}

	public void setHistorico(List<SolicitacaoDocumentoModel> historico) {
		this.historico = historico;
	}

}
