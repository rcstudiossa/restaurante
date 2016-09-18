package br.com.restaurante.faces;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.restaurante.business.CargoBS;
import br.com.restaurante.business.CrudBS;
import br.com.restaurante.model.CargoModel;

@SuppressWarnings("serial")
@ViewScoped
@ManagedBean(name = "cargoFaces")
public class CargoFaces extends CrudFaces<CargoModel> {

	@EJB
	private CargoBS cargoBS;
	
	@Override
	@PostConstruct
	protected void clearFields() {

		this.crudModel = new CargoModel();
		this.crudModel.setFlagAtivo(Boolean.TRUE);

		this.crudPesquisaModel = new CargoModel();
		this.crudPesquisaModel.setFlagAtivo(Boolean.TRUE);
		
	}
	
	@Override
	protected CrudBS<CargoModel> getCrudBS() {
		return this.cargoBS;
	}

}
