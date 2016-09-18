package br.com.restaurante.business;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.restaurante.dao.CrudDAO;
import br.com.restaurante.dao.SetorDAO;
import br.com.restaurante.model.SetorModel;
import br.com.restaurante.model.SetorOrigemModel;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

@Stateless
@LocalBean
public class SetorBS extends CrudBS<SetorModel> {

	@Inject
	private SetorDAO setorDAO;

	@Override
	public SetorModel obterCrudModel(final SetorModel crudModel) {

		SetorModel setorModel = this.setorDAO.obterCrudModel(crudModel);

		if (!TSUtil.isEmpty(setorModel)) {

			setorModel.setOrigens(this.setorDAO.pesquisarOrigens(setorModel));

		}

		return setorModel;
	}

	@Override
	public SetorModel inserirCrudModel(final SetorModel crudModel) throws TSApplicationException {

		SetorModel setorModel = this.setorDAO.inserirCrudModel(crudModel);

		for (SetorOrigemModel model : crudModel.getOrigens()) {
			this.setorDAO.inserir(model);
		}

		return setorModel;
	}

	public SetorModel alterarCrudModel(final SetorModel crudModel) throws TSApplicationException {

		SetorModel setorModel = this.setorDAO.alterarCrudModel(crudModel);

		for (SetorOrigemModel model : crudModel.getOrigens()) {

			if (TSUtil.isEmpty(model.getId())) {

				this.setorDAO.inserir(model);

			}

		}

		return setorModel;
	}

	public SetorOrigemModel excluir(SetorOrigemModel model) throws TSApplicationException {
		return this.setorDAO.excluir(model);
	}

	@Override
	protected CrudDAO<SetorModel> getCrudDAO() {
		return setorDAO;
	}

}
