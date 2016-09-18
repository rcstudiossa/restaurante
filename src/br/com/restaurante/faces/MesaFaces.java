package br.com.restaurante.faces;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.restaurante.business.CrudBS;
import br.com.restaurante.business.MesaBS;
import br.com.restaurante.model.MesaModel;

@ViewScoped
@SuppressWarnings("serial")
@ManagedBean(name = "mesaFaces")
public class MesaFaces extends CrudFaces<MesaModel> {

	@EJB
	private MesaBS mesaBS;
	

	@Override
	@PostConstruct
	protected void clearFields() {

		this.crudModel = new MesaModel();
		this.crudModel.setFlagAtivo(Boolean.TRUE);

		
		
		
		
		
	}
	
	@Override
	protected CrudBS<MesaModel> getCrudBS() {
		return this.mesaBS;
	}

}
