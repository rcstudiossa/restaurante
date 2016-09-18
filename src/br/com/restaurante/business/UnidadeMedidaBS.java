package br.com.restaurante.business;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.restaurante.dao.CrudDAO;
import br.com.restaurante.dao.UnidadeMedidaDAO;
import br.com.restaurante.model.UnidadeMedidaModel;

@Stateless
@LocalBean
public class UnidadeMedidaBS extends CrudBS<UnidadeMedidaModel> {

	@Inject
	private UnidadeMedidaDAO unidadeMedidaDAO;

	@Override
	protected CrudDAO<UnidadeMedidaModel> getCrudDAO() {
		return this.unidadeMedidaDAO;
	}

}
