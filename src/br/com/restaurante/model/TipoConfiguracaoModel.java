package br.com.restaurante.model;

import java.io.Serializable;

import br.com.topsys.util.TSUtil;

@SuppressWarnings("serial")
public class TipoConfiguracaoModel implements Serializable {

	private Long id;

	private String descricao;
	
	private TipoRespostaModel tipoRespostaModel;

	private String respostaPadrao;

	public TipoConfiguracaoModel(Long id) {
		super();
		this.id = id;
	}

	public TipoConfiguracaoModel() {
		super();
	}

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoRespostaModel getTipoRespostaModel() {
		return tipoRespostaModel;
	}

	public void setTipoRespostaModel(TipoRespostaModel tipoRespostaModel) {
		this.tipoRespostaModel = tipoRespostaModel;
	}

	public String getRespostaPadrao() {
		return respostaPadrao;
	}

	public void setRespostaPadrao(String respostaPadrao) {
		this.respostaPadrao = respostaPadrao;
	}
}
