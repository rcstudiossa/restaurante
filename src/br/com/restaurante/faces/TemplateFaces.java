package br.com.restaurante.faces;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import br.com.restaurante.business.CrudBS;
import br.com.restaurante.business.FuncaoBS;
import br.com.restaurante.business.TemplateBS;
import br.com.restaurante.model.FuncaoModel;
import br.com.restaurante.model.TabModel;
import br.com.restaurante.model.TemplateModel;
import br.com.restaurante.model.TemplateTabModel;
import br.com.restaurante.util.Utilitario;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

@ViewScoped
@SuppressWarnings("serial")
@ManagedBean(name = "templateFaces")
public class TemplateFaces extends CrudFaces<TemplateModel> {

	@EJB
	private TemplateBS templateBS;

	@EJB
	private FuncaoBS funcaoBS;

	private TemplateTabModel templateTabSelecionada;
	private TabModel tabSelecionada;
	private TemplateModel templateModel;

	private List<SelectItem> comboFuncoes;
	private List<SelectItem> comboTiposMovimentacoes;
	private List<SelectItem> comboTabs;
	private List<SelectItem> comboTemplates;

	@Override
	@PostConstruct
	protected void clearFields() {

		this.crudModel = new TemplateModel();
		this.crudModel.setFuncaoModel(new FuncaoModel());
		this.crudModel.setTabs(new ArrayList<TemplateTabModel>());

		this.crudPesquisaModel = new TemplateModel();
		this.crudPesquisaModel.setFuncaoModel(new FuncaoModel());

		this.tabSelecionada = new TabModel();
		this.templateTabSelecionada = new TemplateTabModel();
		this.templateModel = new TemplateModel();

		this.comboFuncoes = super.initCombo(this.funcaoBS.pesquisarPorOrigem(new FuncaoModel(), Utilitario.getOrigemAtual()), "id", "descricao");
		this.comboTabs = super.initCombo(this.templateBS.pesquisarTabs(), "id", "descricao");

		this.comboTemplates = super.initCombo(this.templateBS.pesquisarCrudModel(new TemplateModel(new FuncaoModel())), "id", "descricao");

	}

	@Override
	protected boolean validaCampos() {

		boolean valida = true;

		if (TSUtil.isEmpty(this.crudModel.getTabs())) {
			valida = false;
			super.addErrorMessage("É necessário adicionar ao menos uma Tab");
		}
		
		for(TemplateTabModel tab : this.crudModel.getTabs()){
			
			if(tab.getFlagImpressaoAutomatica() && tab.getFlagPularTab()){
				
				valida = false;
				super.addErrorMessage("Tab " + tab.getTabModel().getTitulo() + ": Os flags de impressão automática e pular tab são incompatíveis. Selecione apenas 1");
				
			}
			
		}

		return valida;
	}

	public String addTab() {

		TemplateTabModel tab = new TemplateTabModel();

		if (TSUtil.isEmpty(this.tabSelecionada.getId())) {
			super.addErrorMessage("Selecione uma tab");
			return null;
		}

		tab.setTabModel(this.templateBS.obterTab(this.tabSelecionada));
		tab.setTemplateModel(this.crudModel);
		tab.setOrdem(this.crudModel.getTabs().size());
		tab.setFlagEditarRetroativo(Boolean.FALSE);

		if (this.crudModel.getTabs().contains(tab)) {

			super.addErrorMessage("A tab selecionada já foi adicionada anteriormente");
			return null;

		} else {

			this.crudModel.getTabs().add(tab);

		}

		return null;
	}

	public String removeTab() {

		try {

			this.templateBS.excluirTab(this.templateTabSelecionada);

			this.crudModel.getTabs().remove(this.templateTabSelecionada);

			super.addInfoMessage("Operação realizada com sucesso");

		} catch (TSApplicationException e) {

			super.throwException(e);

		}

		return null;
	}

	public String copiar() {

		if (TSUtil.isEmpty(this.templateModel.getId())) {
			super.addErrorMessage("Selecione o template");
			return null;
		}

		TemplateModel template = this.getCrudBS().obterCrudModel(this.templateModel);

		for (TemplateTabModel tab : template.getTabs()) {

			tab.setId(null);
			tab.setTemplateModel(this.crudModel);

			if (!this.crudModel.getTabs().contains(tab)) {

				this.crudModel.getTabs().add(tab);

			}

		}

		return null;
	}
	
	@Override
	protected void tratarClone() {
		
		for(TemplateTabModel tab : this.crudModel.getTabs()){
			
			tab.setId(null);
			tab.setTemplateModel(this.crudModel);
			
		}
		
	}

	@Override
	protected CrudBS<TemplateModel> getCrudBS() {
		return this.templateBS;
	}

	public List<SelectItem> getComboFuncoes() {
		return comboFuncoes;
	}

	public void setComboFuncoes(List<SelectItem> comboFuncoes) {
		this.comboFuncoes = comboFuncoes;
	}

	public List<SelectItem> getComboTiposMovimentacoes() {
		return comboTiposMovimentacoes;
	}

	public void setComboTiposMovimentacoes(List<SelectItem> comboTiposMovimentacoes) {
		this.comboTiposMovimentacoes = comboTiposMovimentacoes;
	}

	public TemplateTabModel getTemplateTabSelecionada() {
		return templateTabSelecionada;
	}

	public void setTemplateTabSelecionada(TemplateTabModel templateTabSelecionada) {
		this.templateTabSelecionada = templateTabSelecionada;
	}

	public TabModel getTabSelecionada() {
		return tabSelecionada;
	}

	public void setTabSelecionada(TabModel tabSelecionada) {
		this.tabSelecionada = tabSelecionada;
	}

	public List<SelectItem> getComboTabs() {
		return comboTabs;
	}

	public void setComboTabs(List<SelectItem> comboTabs) {
		this.comboTabs = comboTabs;
	}

	public TemplateModel getTemplateModel() {
		return templateModel;
	}

	public void setTemplateModel(TemplateModel templateModel) {
		this.templateModel = templateModel;
	}

	public List<SelectItem> getComboTemplates() {
		return comboTemplates;
	}

	public void setComboTemplates(List<SelectItem> comboTemplates) {
		this.comboTemplates = comboTemplates;
	}
}
