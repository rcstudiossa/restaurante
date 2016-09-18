package br.com.restaurante.model;

import java.util.List;

@SuppressWarnings("serial")
public class ValidadorModel extends BaseModel implements CrudModel<ValidadorModel> {

	private Long idExterno;
	private String descricao;
	private String tipoAviso;
	private String expressao;
	private String aviso;
	private Boolean flagAtivo;
	private Boolean flagValidacaoFinal;
	private String query;
	private String queryValidacao;
	private List<ValidadorModel> historico;

	public Long getIdExterno() {
		return idExterno;
	}

	public void setIdExterno(Long idExterno) {
		this.idExterno = idExterno;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTipoAviso() {
		return tipoAviso;
	}

	public void setTipoAviso(String tipoAviso) {
		this.tipoAviso = tipoAviso;
	}

	public String getExpressao() {
		return expressao;
	}

	public void setExpressao(String expressao) {
		this.expressao = expressao;
	}

	public String getAviso() {
		return aviso;
	}

	public void setAviso(String aviso) {
		this.aviso = aviso;
	}

	public Boolean getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getQueryValidacao() {
		return queryValidacao;
	}

	public void setQueryValidacao(String queryValidacao) {
		this.queryValidacao = queryValidacao;
	}

	public Boolean getFlagValidacaoFinal() {
		return flagValidacaoFinal;
	}

	public void setFlagValidacaoFinal(Boolean flagValidacaoFinal) {
		this.flagValidacaoFinal = flagValidacaoFinal;
	}

	public List<ValidadorModel> getHistorico() {
		return historico;
	}

	public void setHistorico(List<ValidadorModel> historico) {
		this.historico = historico;
	}

}
