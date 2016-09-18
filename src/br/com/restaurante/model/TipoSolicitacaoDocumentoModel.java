package br.com.restaurante.model;

import java.util.Date;
import java.util.List;

import br.com.topsys.util.TSParseUtil;
import br.com.topsys.util.TSUtil;

@SuppressWarnings("serial")
public class TipoSolicitacaoDocumentoModel extends BaseModel implements CrudModel<TipoSolicitacaoDocumentoModel> {

	private String descricao;

	private String referencia;

	private Boolean flagEditavel;

	private Boolean flagConcluir;

	private TipoQuizModel tipoQuizModel;

	private List<TipoSolicitacaoDocumentoFuncaoModel> funcoes;

	private List<TipoSolicitacaoDocumentoModel> historico;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Boolean getFlagEditavel() {
		return flagEditavel;
	}

	public void setFlagEditavel(Boolean flagEditavel) {
		this.flagEditavel = flagEditavel;
	}

	public Boolean getFlagConcluir() {
		return flagConcluir;
	}

	public void setFlagConcluir(Boolean flagConcluir) {
		this.flagConcluir = flagConcluir;
	}

	public TipoQuizModel getTipoQuizModel() {
		return tipoQuizModel;
	}

	public void setTipoQuizModel(TipoQuizModel tipoQuizModel) {
		this.tipoQuizModel = tipoQuizModel;
	}

	public List<TipoSolicitacaoDocumentoFuncaoModel> getFuncoes() {
		return funcoes;
	}

	public void setFuncoes(List<TipoSolicitacaoDocumentoFuncaoModel> funcoes) {
		this.funcoes = funcoes;
	}

	public List<TipoSolicitacaoDocumentoModel> getHistorico() {
		return historico;
	}

	public void setHistorico(List<TipoSolicitacaoDocumentoModel> historico) {
		this.historico = historico;
	}

	public String getReferenciaComReplace2(FuncionarioModel funcionario, OrigemModel origem) {
		return TSUtil.isEmpty(referencia) ? "" : referencia.replace("[NOME_PACIENTE]", funcionario.getNome()).replace("[RG_PACIENTE]", TSUtil.isEmpty(funcionario.getRg()) ? "" : funcionario.getRg()).replace("[CPF_PACIENTE]", TSUtil.isEmpty(funcionario.getCpf()) ? "" : funcionario.getCpf()).replace("[ORIGEM_COMPLETA]", origem.getNome()).replace("[ORIGEM]", origem.getDescricao()).replace("[ORIGEM_CIDADE]", origem.getCidadeModel().getDescricao()).replace("[DATA_ATUAL]", TSParseUtil.dateToString(new Date())).replace("[DATA_HORA_ATUAL]", TSParseUtil.dateHourToString(new Date()));
	}

}