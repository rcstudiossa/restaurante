package br.com.restaurante.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ConfiguracaoModel extends BaseModel implements Serializable {

	private TipoConfiguracaoModel tipoConfiguracaoModel;

	private String valor;
	
	private OrigemModel origemModel;

	private Long respostaEscolhidaLong;
	private Double respostaEscolhidaDouble;
	private String respostaEscolhida;
	private Boolean respostaEscolhidaBoolean;

	public ConfiguracaoModel() {
		super();
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public Long getRespostaEscolhidaLong() {
		return respostaEscolhidaLong;
	}

	public void setRespostaEscolhidaLong(Long respostaEscolhidaLong) {
		this.respostaEscolhidaLong = respostaEscolhidaLong;
	}

	public TipoConfiguracaoModel getTipoConfiguracaoModel() {
		return tipoConfiguracaoModel;
	}

	public void setTipoConfiguracaoModel(TipoConfiguracaoModel tipoConfiguracaoModel) {
		this.tipoConfiguracaoModel = tipoConfiguracaoModel;
	}

	public String getRespostaEscolhida() {
		return respostaEscolhida;
	}

	public void setRespostaEscolhida(String respostaEscolhida) {
		this.respostaEscolhida = respostaEscolhida;
	}

	public Double getRespostaEscolhidaDouble() {
		return respostaEscolhidaDouble;
	}

	public void setRespostaEscolhidaDouble(Double respostaEscolhidaDouble) {
		this.respostaEscolhidaDouble = respostaEscolhidaDouble;
	}

	public OrigemModel getOrigemModel() {
		return origemModel;
	}

	public void setOrigemModel(OrigemModel origemModel) {
		this.origemModel = origemModel;
	}

	public Boolean getRespostaEscolhidaBoolean() {
		return respostaEscolhidaBoolean;
	}

	public void setRespostaEscolhidaBoolean(Boolean respostaEscolhidaBoolean) {
		this.respostaEscolhidaBoolean = respostaEscolhidaBoolean;
	}

}