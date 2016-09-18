package br.com.restaurante.faces;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import br.com.restaurante.business.CrudBS;
import br.com.restaurante.business.TabBS;
import br.com.restaurante.model.TabModel;

@ViewScoped
@SuppressWarnings("serial")
@ManagedBean(name = "tabFaces")
public class TabFaces extends CrudFaces<TabModel> {

	@EJB
	private TabBS tabPepBS;
	
	private List<SelectItem> comboTabs;
	
	@Override
	@PostConstruct
	protected void clearFields() {
		
		this.crudModel = new TabModel();
		this.crudModel.setUrl("/pages/movimentacao/includes/tab_quiz_questionario.xhtml");
		this.crudModel.setFacesReset("quizQuestionarioFaces");
		this.crudModel.setFlagAtivo(Boolean.TRUE);
		this.crudModel.setFlagGenerica(Boolean.TRUE);
		this.crudModel.setTabModel(new TabModel());
		
		this.crudPesquisaModel = new TabModel();
		this.crudPesquisaModel.setTabModel(new TabModel());
		
		this.comboTabs = super.initCombo(this.tabPepBS.pesquisarCrudModel(this.crudPesquisaModel), "id", "descricao");
		
	}
	
	@Override
	protected CrudBS<TabModel> getCrudBS() {
		return this.tabPepBS;
	}

	public List<SelectItem> getComboTabs() {
		return comboTabs;
	}

	public void setComboTabs(List<SelectItem> comboTabs) {
		this.comboTabs = comboTabs;
	}

}
