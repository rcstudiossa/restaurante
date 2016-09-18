package br.com.restaurante.business;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.restaurante.dao.CentroResultadoDAO;
import br.com.restaurante.dao.CrudDAO;
import br.com.restaurante.model.CentroResultadoModel;

@Stateless
@LocalBean
public class CentroResultadoBS extends CrudBS<CentroResultadoModel> {

	@Inject
	private CentroResultadoDAO centroResultadoDAO;

	@Override
	protected CrudDAO<CentroResultadoModel> getCrudDAO() {
		return this.centroResultadoDAO;
	}

}
