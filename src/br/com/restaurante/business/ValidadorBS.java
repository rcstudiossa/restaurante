package br.com.restaurante.business;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.restaurante.dao.CrudDAO;
import br.com.restaurante.dao.ValidadorDAO;
import br.com.restaurante.model.QuizModel;
import br.com.restaurante.model.QuizPerguntaModel;
import br.com.restaurante.model.ValidadorModel;

@Stateless
@LocalBean
public class ValidadorBS extends CrudBS<ValidadorModel> {

	@Inject
	private ValidadorDAO validadorDAO;

	public List<ValidadorModel> pesquisar(QuizPerguntaModel quizPerguntaModel, QuizModel quizModel) {
		return validadorDAO.pesquisar(quizPerguntaModel, quizModel);
	}
	
	public List<ValidadorModel> pesquisar(Long idPergunta) {
		return this.validadorDAO.pesquisar(idPergunta);
	}
	
	@Override
	protected CrudDAO<ValidadorModel> getCrudDAO() {
		return this.validadorDAO;
	}
}
