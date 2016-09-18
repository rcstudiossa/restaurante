package br.com.restaurante.business;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.restaurante.dao.QuizQuestionarioDAO;
import br.com.restaurante.dao.QuizTemplateDAOIf;
import br.com.restaurante.model.FuncionarioModel;
import br.com.restaurante.model.QuizPerguntaModel;
import br.com.restaurante.model.QuizQuestionarioArquetipoRespostaModel;
import br.com.restaurante.model.QuizQuestionarioModel;
import br.com.restaurante.model.QuizQuestionarioRespostaModel;

@Stateless
@LocalBean
public class QuizQuestionarioBS extends QuizTemplateBSAb<QuizQuestionarioModel, QuizQuestionarioRespostaModel, QuizQuestionarioArquetipoRespostaModel> {

	@Inject
	private QuizQuestionarioDAO quizQuestionarioDAO;

	@Override
	protected QuizTemplateDAOIf<QuizQuestionarioModel, QuizQuestionarioRespostaModel, QuizQuestionarioArquetipoRespostaModel> getQuizQuestionarioGenericoDAO() {
		return this.quizQuestionarioDAO;
	}

	@Override
	protected QuizQuestionarioRespostaModel getInstanceResposta() {
		return new QuizQuestionarioRespostaModel();
	}

	@Override
	protected QuizQuestionarioArquetipoRespostaModel getInstanceArquetipoResposta() {
		return new QuizQuestionarioArquetipoRespostaModel();
	}
	
	public List<QuizQuestionarioRespostaModel> pesquisarRespostasFuncoesVitais(FuncionarioModel paciente, QuizPerguntaModel funcaoVital) {
		return this.quizQuestionarioDAO.pesquisarRespostasFuncoesVitais(paciente, funcaoVital);
	}

}
