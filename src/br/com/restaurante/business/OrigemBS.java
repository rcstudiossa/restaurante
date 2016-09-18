package br.com.restaurante.business;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.restaurante.dao.CrudDAO;
import br.com.restaurante.dao.OrigemDAO;
import br.com.restaurante.model.FuncaoModel;
import br.com.restaurante.model.OrigemModel;

@Stateless
@LocalBean
public class OrigemBS extends CrudBS<OrigemModel> {

	@Inject
	private OrigemDAO origemDAO;

	@Override
	protected CrudDAO<OrigemModel> getCrudDAO() {
		return origemDAO;
	}

	public List<OrigemModel> pesquisarPorFuncao(final FuncaoModel model) {
		return origemDAO.pesquisarPorFuncao(model);
	}

}
