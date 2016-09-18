package br.com.restaurante.business;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.restaurante.dao.MenuFuncaoOrigemDAO;
import br.com.restaurante.model.MenuFuncaoOrigemModel;
import br.com.restaurante.model.MenuModel;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

@Stateless
@LocalBean
public class MenuFuncaoOrigemBS {
	
	@Inject
	private MenuFuncaoOrigemDAO menuFuncaoOrigemDAO;
	
	public void atualizar(List<MenuFuncaoOrigemModel> permissoes) throws TSApplicationException {
		
		for (MenuFuncaoOrigemModel model : permissoes) {

			if (TSUtil.isEmpty(TSUtil.tratarLong(model.getId()))) {

				try{
					
					this.menuFuncaoOrigemDAO.inserir(model);
					
				} catch(TSApplicationException e){
					
					model.setId(null);
					throw e;
					
				}
				
			} else {

				MenuFuncaoOrigemModel modelAntigo = this.menuFuncaoOrigemDAO.obter(model);
				
				if(!modelAntigo.getFlagAlterar().equals(model.getFlagAlterar())
						|| !modelAntigo.getFlagAtalho().equals(model.getFlagAtalho())
						|| !modelAntigo.getFlagExcluir().equals(model.getFlagExcluir())
						|| !modelAntigo.getFlagInserir().equals(model.getFlagInserir())){
					
					this.menuFuncaoOrigemDAO.alterar(model);
					
				}

			}

		}
		
	}
	
	public List<MenuFuncaoOrigemModel> pesquisar(final MenuModel model) {
	    return this.menuFuncaoOrigemDAO.pesquisar(model);
	  }  

	  public void excluir(final MenuFuncaoOrigemModel model) throws TSApplicationException {
	    this.menuFuncaoOrigemDAO.excluir(model);
	  }

}
