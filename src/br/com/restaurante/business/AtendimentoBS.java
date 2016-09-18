package br.com.restaurante.business;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.restaurante.dao.AtendimentoDAO;
import br.com.restaurante.dao.CrudDAO;
import br.com.restaurante.model.AtendimentoModel;
import br.com.restaurante.model.FuncionarioModel;
import br.com.restaurante.model.OrigemModel;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

@Stateless
@LocalBean
public class AtendimentoBS extends CrudBS<AtendimentoModel>{

	@Inject
	private AtendimentoDAO atendimentoDAO;
	
	public AtendimentoModel iniciarAtendimento(final AtendimentoModel model) throws TSApplicationException {
		
		AtendimentoModel atendimentoModel = this.atendimentoDAO.obterCrudModel(model);
		
		if(TSUtil.isEmpty(atendimentoModel.getUsuarioResponsavelModel().getId())){
			
			model.setUsuarioResponsavelModel(model.getUsuarioAtualizacaoModel());
			
		}
		
		return this.atendimentoDAO.iniciarAtendimento(model);
	}
	
	public AtendimentoModel alterarStatus(final AtendimentoModel model) throws TSApplicationException {
		return this.atendimentoDAO.alterarStatus(model);
	}
	
	public AtendimentoModel obterUltimoAtendimento(final FuncionarioModel funcionarioModel, OrigemModel origemModel) {
		return this.atendimentoDAO.obterUltimoAtendimento(funcionarioModel, origemModel);
	}
	
	public List<AtendimentoModel> pesquisarAtendimentos(final AtendimentoModel model) {
		return this.atendimentoDAO.pesquisarAtendimentos(model);
	}
	
	public List<AtendimentoModel> pesquisarAgendamentos(final AtendimentoModel model) {
		return this.atendimentoDAO.pesquisarAgendamentos(model);
	}

	@Override
	protected CrudDAO<AtendimentoModel> getCrudDAO() {
		return this.atendimentoDAO;
	}
	
}
