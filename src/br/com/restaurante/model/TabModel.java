package br.com.restaurante.model;

import java.util.List;

@SuppressWarnings("serial")
public class TabModel extends BaseModel implements CrudModel<TabModel> {

	private String descricao;

	private String url;

	private String titulo;

	private String facesReset;

	private Boolean flagGenerica;

	private TabModel tabModel;

	private Boolean flagPossuiFilhos;
	
	private List<TabModel> historico;

	public TabModel() {
		super();
	}

	public TabModel(Long id) {
		super();
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getFacesReset() {
		return facesReset;
	}

	public void setFacesReset(String facesReset) {
		this.facesReset = facesReset;
	}

	public Boolean getFlagGenerica() {
		return flagGenerica;
	}

	public void setFlagGenerica(Boolean flagGenerica) {
		this.flagGenerica = flagGenerica;
	}

	public TabModel getTabModel() {
		return tabModel;
	}

	public void setTabModel(TabModel tabModel) {
		this.tabModel = tabModel;
	}

	public Boolean getFlagPossuiFilhos() {
		return flagPossuiFilhos;
	}

	public void setFlagPossuiFilhos(Boolean flagPossuiFilhos) {
		this.flagPossuiFilhos = flagPossuiFilhos;
	}

	public List<TabModel> getHistorico() {
		return historico;
	}

	public void setHistorico(List<TabModel> historico) {
		this.historico = historico;
	}

}
