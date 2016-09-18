package br.com.restaurante.business;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.restaurante.dao.CidDAO;
import br.com.restaurante.dao.CrudDAO;
import br.com.restaurante.model.CidModel;

@Stateless
@LocalBean
public class CidBS extends CrudBS<CidModel> {

	@Inject
	private CidDAO cidDAO;
	
	@Override
	protected CrudDAO<CidModel> getCrudDAO() {
		return this.cidDAO;
	}

}
