package br.com.restaurante.business;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.restaurante.dao.BairroDAO;
import br.com.restaurante.dao.CrudDAO;
import br.com.restaurante.model.BairroModel;
import br.com.restaurante.model.CidadeModel;

@Stateless
@LocalBean
public class BairroBS extends CrudBS<BairroModel>{

	@Inject
	private BairroDAO bairroDAO;

	public List<BairroModel> pesquisar(final CidadeModel model) {
    	return this.bairroDAO.pesquisar(model);
    }
	
	@Override
	protected CrudDAO<BairroModel> getCrudDAO() {
		return this.bairroDAO;
	}
	
}
