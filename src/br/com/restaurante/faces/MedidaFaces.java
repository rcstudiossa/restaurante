package br.com.restaurante.faces;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import br.com.restaurante.business.CrudBS;
import br.com.restaurante.business.MedidaBS;
import br.com.restaurante.business.UnidadeMedidaBS;
import br.com.restaurante.model.MedidaModel;
import br.com.restaurante.model.MedidaUnidadeMedidaModel;
import br.com.restaurante.model.UnidadeMedidaModel;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

@ViewScoped
@SuppressWarnings("serial")
@ManagedBean(name = "medidaFaces")
public class MedidaFaces extends CrudFaces<MedidaModel> {

	@EJB
	private MedidaBS medidaBS;

	@EJB
	private UnidadeMedidaBS unidadeMedidaBS;

	private UnidadeMedidaModel unidadeMedidaModel;

	private MedidaUnidadeMedidaModel medidaUnidadeMedidaModel;

	private List<SelectItem> comboUnidades;

	@Override
	@PostConstruct
	protected void clearFields() {

		super.clearFields();

		this.crudModel = new MedidaModel();

		this.crudModel.setUnidadesMedidas(new ArrayList<MedidaUnidadeMedidaModel>());

		this.crudPesquisaModel = new MedidaModel();

		this.unidadeMedidaModel = new UnidadeMedidaModel();

		this.comboUnidades = super.initCombo(this.unidadeMedidaBS.pesquisarCrudModel(new UnidadeMedidaModel()), "id", "descricao");

	}

	public String adicionarUnidade() {

		if (TSUtil.isEmpty(this.unidadeMedidaModel.getId())) {
			super.addErrorMessage("Unidade de Medida: Campo obrigatório");
			return null;
		}

		MedidaUnidadeMedidaModel model = new MedidaUnidadeMedidaModel();

		model.setMedidaModel(this.crudModel);

		model.setUnidadeMedidaModel(this.unidadeMedidaBS.obterCrudModel(this.unidadeMedidaModel));

		if (this.crudModel.getUnidadesMedidas().contains(model)) {
			super.addErrorMessage("Unidade já adicionada anteriormente");
			return null;
		}

		this.crudModel.getUnidadesMedidas().add(model);

		this.unidadeMedidaModel = new UnidadeMedidaModel();

		return null;
	}

	public String removerUnidade() {

		try {

			this.medidaBS.excluirUnidade(this.medidaUnidadeMedidaModel);

			super.addInfoMessage("Operação realizada com sucesso");

		} catch (TSApplicationException e) {

			super.throwException(e);

		}

		this.crudModel.getUnidadesMedidas().remove(this.medidaUnidadeMedidaModel);

		return null;
	}

	@Override
	protected CrudBS<MedidaModel> getCrudBS() {
		return this.medidaBS;
	}

	public List<SelectItem> getComboUnidades() {
		return comboUnidades;
	}

	public void setComboUnidades(List<SelectItem> comboUnidades) {
		this.comboUnidades = comboUnidades;
	}

	public UnidadeMedidaModel getUnidadeMedidaModel() {
		return unidadeMedidaModel;
	}

	public void setUnidadeMedidaModel(UnidadeMedidaModel unidadeMedidaModel) {
		this.unidadeMedidaModel = unidadeMedidaModel;
	}

	public MedidaUnidadeMedidaModel getMedidaUnidadeMedidaModel() {
		return medidaUnidadeMedidaModel;
	}

	public void setMedidaUnidadeMedidaModel(MedidaUnidadeMedidaModel medidaUnidadeMedidaModel) {
		this.medidaUnidadeMedidaModel = medidaUnidadeMedidaModel;
	}
}
