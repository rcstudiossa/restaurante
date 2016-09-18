package br.com.restaurante.business;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;

import br.com.restaurante.dao.QuizGrupoDAO;
import br.com.restaurante.dao.QuizTemplateDAOIf;
import br.com.restaurante.dao.TipoQuizDAO;
import br.com.restaurante.model.QuizGrupoModel;
import br.com.restaurante.model.QuizModel;
import br.com.restaurante.model.QuizTemplateArquetipoRespostaAb;
import br.com.restaurante.model.QuizTemplateIf;
import br.com.restaurante.model.QuizTemplateRespostaIf;
import br.com.restaurante.model.TipoQuizModel;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

public abstract class QuizTemplateBSAb<T extends QuizTemplateIf<R>, R extends QuizTemplateRespostaIf<T, R, A>, A extends QuizTemplateArquetipoRespostaAb<T, R, A>> {

	@EJB
	protected QuizBS quizBS;
	
	@EJB
	protected ValidadorBS validadorBS;

	@Inject
	protected QuizGrupoDAO quizGrupoDAO;

	@Inject
	protected TipoQuizDAO tipoQuizDAO;

	protected abstract QuizTemplateDAOIf<T, R, A> getQuizQuestionarioGenericoDAO();

	protected abstract R getInstanceResposta();

	protected abstract A getInstanceArquetipoResposta();

	public T iniciarQuiz(T model) {

		TipoQuizModel tipoQuiz = null;

		if (!TSUtil.isEmpty(model.getTipoQuizModel().getId())) {

			tipoQuiz = this.tipoQuizDAO.obterCrudModel(model.getTipoQuizModel());

		} else {

			tipoQuiz = this.tipoQuizDAO.obter(model.getTipoQuizModel().getTabModel(), model.getTipoQuizModel().getFuncaoModel());

		}

		if (TSUtil.isEmpty(tipoQuiz)) {
			return model;
		}

		model.setTipoQuizModel(tipoQuiz);
		model.setRespostas(new ArrayList<R>());

		model.setQuizGrupos(this.quizGrupoDAO.pesquisarComFilhos(model.getTipoQuizModel()));

		for (QuizGrupoModel grupo : model.getQuizGrupos()) {

			grupo.setQuizes(this.quizBS.pesquisarPorGrupo(model.getTipoQuizModel(), grupo, model.getAtendimentoModel()));

			for (QuizModel quiz : grupo.getQuizes()) {

				quiz.setQuizGrupoModel(grupo);
				quiz.setValidadoresEnvolvidos(this.validadorBS.pesquisar(quiz.getQuizPerguntaModel(), quiz));

				this.quizBS.carregarCamposQuiz(quiz);

			}

		}

		this.iniciarRespostasPadrao(model.getQuizGrupos());

		return model;
	}

	public T obterQuiz(final T model) {

		T questionario = getQuizQuestionarioGenericoDAO().obter(model);

		this.carregar(questionario);

		return questionario;

	}
	
	protected void carregar(T questionario) {

		if (!TSUtil.isEmpty(questionario)) {

			questionario.setRespostas(this.getQuizQuestionarioGenericoDAO().pesquisarRespostas(questionario));

			for (R resposta : questionario.getRespostas()) {
				resposta.setArquetiposRespostas(this.getQuizQuestionarioGenericoDAO().pesquisarRespostasArquetipos(resposta));
			}
			
			this.quizBS.carregar(questionario, questionario.getTipoQuizModel(), questionario.getAtendimentoModel());

		}

	}

	public T obterExistente(final T model) {
		return getQuizQuestionarioGenericoDAO().obterExistente(model);
	}

	public T inserirQuiz(final T model, boolean flagInserirSomenteExistindoResposta) throws TSApplicationException {

		this.quizBS.tratarQuizPersist(model);

		if (!flagInserirSomenteExistindoResposta || !TSUtil.isEmpty(model.getRespostas())) {

			this.getQuizQuestionarioGenericoDAO().inserir(model);

			for (R resposta : model.getRespostas()) {

				this.getQuizQuestionarioGenericoDAO().inserirResposta(resposta);

				for (A arquetipoResposta : resposta.getArquetiposRespostas()) {

					this.getQuizQuestionarioGenericoDAO().inserirRespostaArquetipo(arquetipoResposta);

				}

			}

			this.quizBS.executarQuerys(model, model.getAtendimentoModel().getId());
			
		}

		return model;

	}

	public T inserirQuiz(final T model) throws TSApplicationException {
		return this.inserirQuiz(model, false);
	}

	public T alterarQuiz(final T model) throws TSApplicationException {

		this.quizBS.tratarQuizPersist(model);

		this.getQuizQuestionarioGenericoDAO().alterar(model);

		this.getQuizQuestionarioGenericoDAO().excluirRespostas(model);

		for (R resposta : model.getRespostas()) {

			this.getQuizQuestionarioGenericoDAO().inserirResposta(resposta);

			for (A arquetipoResposta : resposta.getArquetiposRespostas()) {

				this.getQuizQuestionarioGenericoDAO().inserirRespostaArquetipo(arquetipoResposta);

			}

		}

		this.quizBS.executarQuerys(model, model.getAtendimentoModel().getId());

		return model;

	}

	public List<T> pesquisarQuiz(final T model) {
		return this.getQuizQuestionarioGenericoDAO().pesquisarQuiz(model);
	}

	public List<T> pesquisarQuiz(final T model, int limit) {
		return this.getQuizQuestionarioGenericoDAO().pesquisarQuiz(model, limit);
	}

	public List<R> pesquisarRespostasSimples(final T model) {
		return this.getQuizQuestionarioGenericoDAO().pesquisarRespostas(model);
	}

	protected void iniciarRespostasPadrao(List<QuizGrupoModel> quizGrupos) {
		this.quizBS.iniciarRespostasPadrao(quizGrupos);
	}

	public void limparRespostas(List<QuizGrupoModel> grupos) {

		for (QuizGrupoModel grupo : grupos) {

			for (QuizModel quiz : grupo.getQuizes()) {

				quiz.limparResposta();

			}

		}

	}

	public T reabrirQuiz(final T model) throws TSApplicationException {
		return this.getQuizQuestionarioGenericoDAO().reabrir(model);
	}

}