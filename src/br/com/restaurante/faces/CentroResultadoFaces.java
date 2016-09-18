package br.com.restaurante.faces;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.restaurante.business.CentroResultadoBS;
import br.com.restaurante.business.CrudBS;
import br.com.restaurante.model.CentroResultadoModel;

@SuppressWarnings("serial")
@ViewScoped
@ManagedBean(name = "centroResultadoFaces")
public class CentroResultadoFaces extends CrudFaces<CentroResultadoModel> {

	@EJB
	private CentroResultadoBS centroResultadoBS;

	@Override
	@PostConstruct
	protected void clearFields() {

		this.crudModel = new CentroResultadoModel();
		this.crudModel.setFlagAtivo(Boolean.TRUE);

		this.crudPesquisaModel = new CentroResultadoModel();
		this.crudPesquisaModel.setFlagAtivo(Boolean.TRUE);

	}

	@Override
	protected CrudBS<CentroResultadoModel> getCrudBS() {
		return this.centroResultadoBS;
	}

}
