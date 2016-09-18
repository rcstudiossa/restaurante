package br.com.restaurante.business;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.restaurante.dao.FuncaoOrigemDAO;
import br.com.restaurante.model.FuncaoModel;
import br.com.restaurante.model.FuncaoOrigemModel;
import br.com.topsys.exception.TSApplicationException;

@Stateless
@LocalBean
public class FuncaoOrigemBS {
	
	@Inject
	private FuncaoOrigemDAO funcaoOrigemDAO;
	
	public FuncaoOrigemModel obter(final FuncaoOrigemModel model) {
        return this.funcaoOrigemDAO.obter(model);
    }

    public List<FuncaoOrigemModel> pesquisar(final FuncaoModel model) {
        return this.funcaoOrigemDAO.pesquisar(model);
    }

    public List<FuncaoOrigemModel> pesquisarComboFuncaoOrigem() {
        return this.funcaoOrigemDAO.pesquisarComboFuncaoOrigem();
    }
    
    public void excluir(FuncaoOrigemModel model)  throws TSApplicationException{
    	this.funcaoOrigemDAO.excluir(model);
    }

}
