package br.com.restaurante.business;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.restaurante.dao.CrudDAO;
import br.com.restaurante.dao.PerfilDAO;
import br.com.restaurante.model.PerfilModel;
import br.com.restaurante.model.PerfilProcedimentoModel;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

@Stateless
@LocalBean
public class PerfilBS extends CrudBS<PerfilModel> {

	@Inject
	private PerfilDAO perfilDAO;

	@Override
	public PerfilModel obterCrudModel(PerfilModel crudModel) {

		PerfilModel model = super.obterCrudModel(crudModel);

		if (!TSUtil.isEmpty(model)) {

			model.setProcedimentos(this.perfilDAO.pesquisarProcedimentos(model));

		}

		return model;
	}

	@Override
	public PerfilModel inserirCrudModel(PerfilModel crudModel) throws TSApplicationException {

		super.inserirCrudModel(crudModel);

		for (PerfilProcedimentoModel proc : crudModel.getProcedimentos()) {

			this.perfilDAO.inserir(proc);

		}

		return crudModel;
	}

	@Override
	public PerfilModel alterarCrudModel(PerfilModel crudModel) throws TSApplicationException {

		super.alterarCrudModel(crudModel);

		for (PerfilProcedimentoModel proc : crudModel.getProcedimentos()) {

			if (TSUtil.isEmpty(proc.getId())) {

				this.perfilDAO.inserir(proc);

			}

		}

		return crudModel;

	}
	
	public void excluir(PerfilProcedimentoModel model) throws TSApplicationException {
		this.perfilDAO.excluir(model);
	}

	@Override
	protected CrudDAO<PerfilModel> getCrudDAO() {
		return this.perfilDAO;
	}

}
