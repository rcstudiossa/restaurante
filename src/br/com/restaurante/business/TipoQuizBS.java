package br.com.restaurante.business;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.restaurante.dao.CrudDAO;
import br.com.restaurante.dao.QuizPerguntaDAO;
import br.com.restaurante.dao.TipoQuizDAO;
import br.com.restaurante.model.FuncaoModel;
import br.com.restaurante.model.QuizGrupoModel;
import br.com.restaurante.model.QuizModel;
import br.com.restaurante.model.TabModel;
import br.com.restaurante.model.TipoQuizModel;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

@Stateless
@LocalBean
public class TipoQuizBS extends CrudBS<TipoQuizModel> {

	@EJB
	private QuizBS quizBS;

	@EJB
	private QuizGrupoBS quizGrupoBS;

	@Inject
	private TipoQuizDAO tipoQuizDAO;

	@Inject
	private QuizPerguntaDAO quizPerguntaDAO;

	@Override
	protected CrudDAO<TipoQuizModel> getCrudDAO() {
		return this.tipoQuizDAO;
	}

	@Override
	public TipoQuizModel obterCrudModel(TipoQuizModel crudModel) {

		TipoQuizModel model = this.tipoQuizDAO.obterCrudModel(crudModel);

		model.setQuiz(this.quizBS.pesquisar(new QuizModel(crudModel)));

		for (QuizModel quiz : model.getQuiz()) {

			quiz.setTipoQuizModel(model);
			
			quiz.setValidadores(this.quizBS.pesquisarValidadores(quiz));

			quiz.getQuizPerguntaModel().setRespostas(this.quizPerguntaDAO.pesquisarRespostas(quiz.getQuizPerguntaModel()));

		}

		this.visualizarQuiz(model);

		return model;

	}

	public void visualizarQuiz(TipoQuizModel model) {

		model.setGrupos(new ArrayList<QuizGrupoModel>());

		for (QuizModel quiz : model.getQuiz()) {

			if (!model.getGrupos().contains(quiz.getQuizGrupoModel())) {

				model.getGrupos().add(quiz.getQuizGrupoModel());

				quiz.getQuizGrupoModel().setQuizes(new ArrayList<QuizModel>());

				quiz.getQuizGrupoModel().getQuizes().add(quiz);

			} else {

				QuizGrupoModel quizGrupo = model.getGrupos().get(model.getGrupos().indexOf(quiz.getQuizGrupoModel()));

				quizGrupo.getQuizes().add(quiz);
				quiz.setQuizGrupoModel(quizGrupo);

			}

		}

	}

	public TipoQuizModel obter(final TabModel tabModel, final FuncaoModel funcaoModel) {
		return this.tipoQuizDAO.obter(tabModel, funcaoModel);
	}

	@Override
	public TipoQuizModel inserirCrudModel(TipoQuizModel crudModel) throws TSApplicationException {

		this.tipoQuizDAO.inserirCrudModel(crudModel);

		for (QuizGrupoModel grupo : crudModel.getGrupos()) {

			if (TSUtil.isEmpty(grupo.getId())) {

				this.quizGrupoBS.inserirCrudModel(grupo);

			}

			for (QuizModel quiz : grupo.getQuizes()) {

				if (quiz.getFlagAtivo()) {

					this.quizBS.inserir(quiz);

				}

			}

		}

		return crudModel;
	}

	@Override
	public TipoQuizModel alterarCrudModel(TipoQuizModel crudModel) throws TSApplicationException {

		this.tipoQuizDAO.alterarCrudModel(crudModel);

		for (QuizGrupoModel grupo : crudModel.getGrupos()) {

			if (TSUtil.isEmpty(grupo.getId())) {

				this.quizGrupoBS.inserirCrudModel(grupo);

			}

			for (QuizModel quiz : grupo.getQuizes()) {

				if (TSUtil.isEmpty(quiz.getId())) {

					if (quiz.getFlagAtivo()) {

						this.quizBS.inserir(quiz);

					}

				} else {

					this.quizBS.alterar(quiz);

				}

			}

		}

		return crudModel;
	}

	public TabModel obterTab(final Long tabId) {
		return this.tipoQuizDAO.obterTab(tabId);
	}

}
