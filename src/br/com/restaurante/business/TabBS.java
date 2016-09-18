package br.com.restaurante.business;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.restaurante.dao.CrudDAO;
import br.com.restaurante.dao.TabDAO;
import br.com.restaurante.model.TabModel;

@Stateless
@LocalBean
public class TabBS extends CrudBS<TabModel>{
	
	@Inject
	private TabDAO tabDAO;

	@Override
	protected CrudDAO<TabModel> getCrudDAO() {
		return this.tabDAO;
	}
	
}
