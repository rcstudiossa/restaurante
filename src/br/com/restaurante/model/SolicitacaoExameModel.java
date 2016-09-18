package br.com.restaurante.model;

import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class SolicitacaoExameModel extends BaseModel implements CrudModel<SolicitacaoExameModel> {

	private List<SolicitacaoExameModel> historico;
	
	private UsuarioModel solicitanteModel;

	private FuncionarioModel funcionarioModel;
	
	private CidModel cidModel;

	private String observacao;
	
	private String descricaoProcedimento;

	private List<SolicitacaoExameItemModel> exames;

	private Boolean flagConcluido;

	private Boolean flagSelecionado;

	private OrigemModel origemModel;

	private AtendimentoModel atendimentoModel;
	
	private Date dataAgendamento;

	public UsuarioModel getSolicitanteModel() {
		return solicitanteModel;
	}

	public void setSolicitanteModel(UsuarioModel solicitanteModel) {
		this.solicitanteModel = solicitanteModel;
	}

	public FuncionarioModel getFuncionarioModel() {
		return funcionarioModel;
	}

	public void setFuncionarioModel(FuncionarioModel funcionarioModel) {
		this.funcionarioModel = funcionarioModel;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public List<SolicitacaoExameItemModel> getExames() {
		return exames;
	}

	public void setExames(List<SolicitacaoExameItemModel> exames) {
		this.exames = exames;
	}

	public Boolean getFlagConcluido() {
		return flagConcluido;
	}

	public void setFlagConcluido(Boolean flagConcluido) {
		this.flagConcluido = flagConcluido;
	}

	public Boolean getFlagSelecionado() {
		return flagSelecionado;
	}

	public void setFlagSelecionado(Boolean flagSelecionado) {
		this.flagSelecionado = flagSelecionado;
	}

	public OrigemModel getOrigemModel() {
		return origemModel;
	}

	public void setOrigemModel(OrigemModel origemModel) {
		this.origemModel = origemModel;
	}

	public AtendimentoModel getAtendimentoModel() {
		return atendimentoModel;
	}

	public void setAtendimentoModel(AtendimentoModel atendimentoModel) {
		this.atendimentoModel = atendimentoModel;
	}

	public Date getDataAgendamento() {
		return dataAgendamento;
	}

	public void setDataAgendamento(Date dataAgendamento) {
		this.dataAgendamento = dataAgendamento;
	}

	public List<SolicitacaoExameModel> getHistorico() {
		return historico;
	}

	public void setHistorico(List<SolicitacaoExameModel> historico) {
		this.historico = historico;
	}

	public String getDescricaoProcedimento() {
		return descricaoProcedimento;
	}

	public void setDescricaoProcedimento(String descricaoProcedimento) {
		this.descricaoProcedimento = descricaoProcedimento;
	}

	public CidModel getCidModel() {
		return cidModel;
	}

	public void setCidModel(CidModel cidModel) {
		this.cidModel = cidModel;
	}
	
}
