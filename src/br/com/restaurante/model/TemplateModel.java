package br.com.restaurante.model;

import java.util.List;

@SuppressWarnings("serial")
public class TemplateModel extends BaseModel implements CrudModel<TemplateModel> {

	private FuncaoModel funcaoModel;
	
	private List<TemplateTabModel> tabs;
	
	private List<TemplateModel> historico;
	
	private String descricao;
	
	public TemplateModel(FuncaoModel funcaoModel) {
		super();
		this.funcaoModel = funcaoModel;
	}

	public TemplateModel() {
		super();
	}

	public FuncaoModel getFuncaoModel() {
		return funcaoModel;
	}

	public void setFuncaoModel(FuncaoModel funcaoModel) {
		this.funcaoModel = funcaoModel;
	}

	public List<TemplateTabModel> getTabs() {
		return tabs;
	}

	public void setTabs(List<TemplateTabModel> tabs) {
		this.tabs = tabs;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<TemplateModel> getHistorico() {
		return historico;
	}

	public void setHistorico(List<TemplateModel> historico) {
		this.historico = historico;
	}

}
