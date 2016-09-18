package br.com.restaurante.business;

import java.util.List;

import br.com.restaurante.dao.CrudDAO;
import br.com.restaurante.model.CrudModel;
import br.com.topsys.exception.TSApplicationException;

public abstract class CrudBS<T extends CrudModel<T>> {

	public CrudBS() {

	}

	protected abstract CrudDAO<T> getCrudDAO();

	public List<T> pesquisarCrudModel(final T crudModel) {
		return getCrudDAO().pesquisarCrudModel(crudModel);
	}
	
	public List<T> pesquisarLog(final T crudModel) {
		return getCrudDAO().pesquisarLog(crudModel);
	}

	public T obterCrudModel(final T crudModel) {
		return getCrudDAO().obterCrudModel(crudModel);
	}

	public T inserirCrudModel(final T crudModel) throws TSApplicationException {
		return getCrudDAO().inserirCrudModel(crudModel);
	}

	public T alterarCrudModel(final T crudModel) throws TSApplicationException {
		return getCrudDAO().alterarCrudModel(crudModel);
	}

	public T excluirCrudModel(final T crudModel) throws TSApplicationException {
		return getCrudDAO().excluirCrudModel(crudModel);
	}

}
