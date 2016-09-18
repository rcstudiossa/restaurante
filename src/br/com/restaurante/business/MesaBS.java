package br.com.restaurante.business;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.restaurante.dao.CrudDAO;
import br.com.restaurante.dao.MesaDAO;
import br.com.restaurante.model.MesaModel;

@Stateless
@LocalBean
public class MesaBS extends CrudBS<MesaModel>{

	@Inject
	private MesaDAO mesaDAO;
	
	@Override
	protected CrudDAO<MesaModel> getCrudDAO() {
		return this.mesaDAO;
	}
	
}
