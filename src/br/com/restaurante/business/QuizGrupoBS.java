package br.com.restaurante.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.restaurante.dao.CrudDAO;
import br.com.restaurante.dao.QuizGrupoDAO;
import br.com.restaurante.model.QuizGrupoModel;
import br.com.restaurante.model.QuizModel;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

@Stateless
@LocalBean
public class QuizGrupoBS extends CrudBS<QuizGrupoModel> {

	@Inject
	private QuizGrupoDAO quizGrupoDAO;

	@EJB
	private QuizBS quizBS;

	@Override
	public QuizGrupoModel excluirCrudModel(QuizGrupoModel grupo) throws TSApplicationException {

		if(!TSUtil.isEmpty(grupo.getQuizes())){
			
			for (QuizModel quiz : grupo.getQuizes()) {
				
				this.quizBS.excluir(quiz);
				
			}
			
		}

		return this.quizGrupoDAO.excluirCrudModel(grupo);
	}
	
	@Override
	protected CrudDAO<QuizGrupoModel> getCrudDAO() {
		return this.quizGrupoDAO;
	}
	
	public List<QuizGrupoModel> pesquisarExames() {
		return this.quizGrupoDAO.pesquisarExames();
	}

}
