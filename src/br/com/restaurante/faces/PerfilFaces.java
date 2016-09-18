package br.com.restaurante.faces;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import br.com.restaurante.business.ComboBS;
import br.com.restaurante.business.CrudBS;
import br.com.restaurante.business.PerfilBS;
import br.com.restaurante.model.PerfilModel;
import br.com.restaurante.model.PerfilProcedimentoModel;
import br.com.restaurante.model.ProcedimentoModel;
import br.com.restaurante.util.Constantes;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

@SuppressWarnings("serial")
@ViewScoped
@ManagedBean(name = "perfilFaces")
public class PerfilFaces extends CrudFaces<PerfilModel> {

	@EJB
	private PerfilBS perfilBS;

	@EJB
	private ComboBS comboBS;

	private PerfilProcedimentoModel perfilProcedimentoModel;

	private ProcedimentoModel procedimentoModel;

	private List<SelectItem> comboProcedimento;

	@Override
	@PostConstruct
	protected void clearFields() {

		this.crudModel = new PerfilModel();
		this.crudModel.setFlagAtivo(Boolean.TRUE);
		this.crudModel.setProcedimentos(new ArrayList<PerfilProcedimentoModel>());

		this.crudPesquisaModel = new PerfilModel();
		this.crudPesquisaModel.setFlagAtivo(Boolean.TRUE);

		this.procedimentoModel = new ProcedimentoModel();

		this.comboProcedimento = super.initCombo(this.comboBS.pesquisarProcedimentos(new ProcedimentoModel()), "id", "descricao");

	}

	public String addProcedimento() {

		if (TSUtil.isEmpty(this.procedimentoModel.getId())) {
			super.addErrorMessage("Procedimento: Campo obrigatório");
			return null;
		}

		PerfilProcedimentoModel model = new PerfilProcedimentoModel(this.crudModel, this.comboBS.obter(this.procedimentoModel));

		if (this.crudModel.getProcedimentos().contains(model)) {
			super.addErrorMessage("Procedimento já adicionado anteriormente");
			return null;
		}

		this.crudModel.getProcedimentos().add(model);

		this.procedimentoModel.setId(null);

		return null;
	}

	public String removerProcedimento() {

		try {

			this.perfilBS.excluir(this.perfilProcedimentoModel);

			this.crudModel.getProcedimentos().remove(this.perfilProcedimentoModel);

			super.addInfoMessageKey(Constantes.OPERACAO_SUCESSO);

		} catch (TSApplicationException e) {

			super.throwException(e);

		}

		return null;
	}
	
	@Override
	protected void tratarClone() {
		
		for(PerfilProcedimentoModel procedimento : this.crudModel.getProcedimentos()){
			
			procedimento.setId(null);
			procedimento.setPerfilModel(this.crudModel);
			
		}
		
	}

	@Override
	protected CrudBS<PerfilModel> getCrudBS() {
		return this.perfilBS;
	}

	public ProcedimentoModel getProcedimentoModel() {
		return procedimentoModel;
	}

	public void setProcedimentoModel(ProcedimentoModel procedimentoModel) {
		this.procedimentoModel = procedimentoModel;
	}

	public List<SelectItem> getComboProcedimento() {
		return comboProcedimento;
	}

	public void setComboProcedimento(List<SelectItem> comboProcedimento) {
		this.comboProcedimento = comboProcedimento;
	}

	public PerfilProcedimentoModel getPerfilProcedimentoModel() {
		return perfilProcedimentoModel;
	}

	public void setPerfilProcedimentoModel(PerfilProcedimentoModel perfilProcedimentoModel) {
		this.perfilProcedimentoModel = perfilProcedimentoModel;
	}

}
