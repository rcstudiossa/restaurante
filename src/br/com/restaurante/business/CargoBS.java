package br.com.restaurante.business;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.restaurante.dao.CargoDAO;
import br.com.restaurante.dao.CrudDAO;
import br.com.restaurante.model.CargoModel;

@Stateless
@LocalBean
public class CargoBS extends CrudBS<CargoModel> {

	@Inject
	private CargoDAO cargoDAO;
	
	@Override
	protected CrudDAO<CargoModel> getCrudDAO() {
		return this.cargoDAO;
	}

}
