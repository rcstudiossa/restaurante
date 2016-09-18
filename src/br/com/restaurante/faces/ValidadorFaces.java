package br.com.restaurante.faces;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.restaurante.business.CrudBS;
import br.com.restaurante.business.ValidadorBS;
import br.com.restaurante.model.ValidadorModel;
import br.com.topsys.util.TSUtil;

@ViewScoped
@SuppressWarnings("serial")
@ManagedBean(name = "validadorFaces")
public class ValidadorFaces extends CrudFaces<ValidadorModel> {

	@EJB
	private ValidadorBS validadorBS;

	@Override
	@PostConstruct
	protected void clearFields() {

		super.clearFields();

		this.crudModel = new ValidadorModel();

		this.crudModel.setFlagAtivo(Boolean.TRUE);

		this.crudPesquisaModel = new ValidadorModel();

	}

	@Override
	protected boolean validaCampos() {

		boolean valida = true;

		if (TSUtil.isEmpty(this.crudModel.getExpressao()) && TSUtil.isEmpty(this.crudModel.getQueryValidacao())) {

			super.addErrorMessage("Expressão / Query Validação: Ao menos um dos campos são obrigatórios");
			valida = false;

		}

		if (!TSUtil.isEmpty(this.crudModel.getExpressao()) && TSUtil.isEmpty(this.crudModel.getAviso())) {

			super.addErrorMessage("Aviso: Campo obrigatório");
			valida = false;

		}

		return valida;
	}

	@Override
	protected CrudBS<ValidadorModel> getCrudBS() {
		return this.validadorBS;
	}
}
