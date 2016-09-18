package br.com.restaurante.business;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.restaurante.dao.CrudDAO;
import br.com.restaurante.dao.TabDAO;
import br.com.restaurante.dao.TemplateDAO;
import br.com.restaurante.model.FuncaoModel;
import br.com.restaurante.model.TabModel;
import br.com.restaurante.model.TemplateModel;
import br.com.restaurante.model.TemplateTabModel;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

@Stateless
@LocalBean
public class TemplateBS extends CrudBS<TemplateModel> {

	@Inject
	private TemplateDAO templateDAO;
	
	@Inject
	private TabDAO tabDAO;

	@Override
	public TemplateModel obterCrudModel(TemplateModel crudModel) {

		TemplateModel model = super.obterCrudModel(crudModel);

		if (!TSUtil.isEmpty(model)) {

			model.setTabs(this.templateDAO.pesquisarTabs(model));

		}

		return model;

	}
	
	public List<TemplateTabModel> pesquisarOutrasTabs(final TemplateModel model, Long atendimentoId) {
		return this.templateDAO.pesquisarOutrasTabs(model, atendimentoId);
	}
	
	@Override
	public TemplateModel inserirCrudModel(TemplateModel crudModel) throws TSApplicationException {

		super.inserirCrudModel(crudModel);

		for (TemplateTabModel tab : crudModel.getTabs()) {

			this.templateDAO.inserirTab(tab);

		}

		return crudModel;
	}

	@Override
	public TemplateModel alterarCrudModel(TemplateModel crudModel) throws TSApplicationException {

		super.alterarCrudModel(crudModel);

		for (TemplateTabModel tab : crudModel.getTabs()) {

			if (TSUtil.isEmpty(tab.getId())) {

				this.templateDAO.inserirTab(tab);

			} else {

				this.templateDAO.alterarTab(tab);

			}

		}

		return crudModel;

	}
	
	public List<TabModel> pesquisarTabs() {
		return this.templateDAO.pesquisarTabs();
	}
	
	public TabModel obterTab(TabModel model) {
		return this.tabDAO.obterCrudModel(model);
	}

	public void excluirTab(TemplateTabModel model) throws TSApplicationException {
		this.templateDAO.excluirTab(model);
	}
	
	public TemplateModel obterDinamico(FuncaoModel funcaoModel) {
		
		TemplateModel model = this.templateDAO.obterDinamico(funcaoModel);
		
		if(TSUtil.isEmpty(model)){
			model = this.templateDAO.obterPadrao();
		}
		
		if (!TSUtil.isEmpty(model)) {
			model.setTabs(this.templateDAO.pesquisarTabs(model, funcaoModel));
		}
		
		return model;
		
	}

	@Override
	protected CrudDAO<TemplateModel> getCrudDAO() {
		return this.templateDAO;
	}

}
