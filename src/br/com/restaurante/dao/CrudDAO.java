package br.com.restaurante.dao;

import java.util.List;

import br.com.restaurante.model.CrudModel;
import br.com.topsys.exception.TSApplicationException;

public interface CrudDAO<T extends CrudModel<T>> {

	public T obterCrudModel(final T model);

	public List<T> pesquisarCrudModel(final T model);
	
	public List<T> pesquisarLog(final T model);

	public T inserirCrudModel(final T model) throws TSApplicationException;

	public T alterarCrudModel(final T model) throws TSApplicationException;

	public T excluirCrudModel(final T model) throws TSApplicationException;
}
