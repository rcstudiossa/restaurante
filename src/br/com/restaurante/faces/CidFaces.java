package br.com.restaurante.faces;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.restaurante.business.CidBS;
import br.com.restaurante.business.CrudBS;
import br.com.restaurante.model.CidModel;

@SuppressWarnings("serial")
@ViewScoped
@ManagedBean(name = "cidFaces")
public class CidFaces extends CrudFaces<CidModel> {

	@EJB
	private CidBS cidBS;
	
	@Override
	@PostConstruct
	protected void clearFields() {

		this.crudModel = new CidModel();
		this.crudModel.setFlagAtivo(Boolean.TRUE);

		this.crudPesquisaModel = new CidModel();
		this.crudPesquisaModel.setFlagAtivo(Boolean.TRUE);
		
	}
	
	@Override
	protected CrudBS<CidModel> getCrudBS() {
		return this.cidBS;
	}

}
