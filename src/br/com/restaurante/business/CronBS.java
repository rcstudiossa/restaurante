package br.com.restaurante.business;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.restaurante.dao.CronDAO;
import br.com.restaurante.dao.CrudDAO;
import br.com.restaurante.model.CronExameModel;
import br.com.restaurante.model.CronModel;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

@Stateless
@LocalBean
public class CronBS extends CrudBS<CronModel> {

	@Inject
	private CronDAO cronDAO;
	
	@Override
	public CronModel obterCrudModel(CronModel crudModel) {
		
		CronModel model = super.obterCrudModel(crudModel);
		
		if(!TSUtil.isEmpty(model)){
			
			model.setExames(this.cronDAO.pesquisarExames(model));
			
		}
		
		return model;
	}
	
	@Override
	public CronModel inserirCrudModel(CronModel crudModel) throws TSApplicationException {
		
		super.inserirCrudModel(crudModel);
		
		for(CronExameModel exame : crudModel.getExames()){
			
			this.cronDAO.inserir(exame);
			
		}
		
		return crudModel;
		
	}
	
	@Override
	public CronModel alterarCrudModel(CronModel crudModel) throws TSApplicationException {
		
		super.alterarCrudModel(crudModel);
		
		for(CronExameModel exame : crudModel.getExames()){
			
			if(TSUtil.isEmpty(exame.getId())){
				
				this.cronDAO.inserir(exame);
				
			}
			
		}
		
		return crudModel;
		
	}
	
	public void excluir(CronExameModel model) throws TSApplicationException {
		this.cronDAO.excluir(model);
	}
	
	public void agendarExames(CronModel model) throws TSApplicationException {
		this.cronDAO.agendarExames(model);
	}

	@Override
	protected CrudDAO<CronModel> getCrudDAO() {
		return this.cronDAO;
	}

}
