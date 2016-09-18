package br.com.restaurante.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import br.com.restaurante.model.CamposRespostaAb;
import br.com.restaurante.model.QuizGrupoModel;
import br.com.restaurante.model.QuizModel;
import br.com.restaurante.model.QuizTemplateArquetipoRespostaAb;
import br.com.restaurante.model.QuizTemplateIf;
import br.com.restaurante.model.QuizTemplateRespostaIf;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.util.TSFacesUtil;

@SuppressWarnings("serial")
public class QuizTemplateUtil<T extends QuizTemplateIf<R>, R extends QuizTemplateRespostaIf<T, R, A>, A extends QuizTemplateArquetipoRespostaAb<T, R, A>> implements Serializable {

	private ValidadorUtil validadorUtil;
	private QuizUtil quizUtil;

	public QuizTemplateUtil() {
		this.validadorUtil = new ValidadorUtil();
		this.quizUtil = new QuizUtil();
	}

	public Map<String, Object> obterMapa() {

		@SuppressWarnings("unchecked")
		Map<String, Object> mapa = (HashMap<String, Object>) TSFacesUtil.getObjectInSession(Constantes.SESSION_VALIDADOR);

		if (TSUtil.isEmpty(mapa)) {

			mapa = new HashMap<String, Object>();

		}

		return mapa;
	}

	public Map<String, String> validar(CamposRespostaAb quiz, Map<String, Object> mapa, boolean flagSalvar) {

		Map<String, String> mensagensValidacao = new HashMap<String, String>();

		mensagensValidacao.putAll(this.validadorUtil.tratarValidadores(quiz.getRespostaDada(), mapa, quiz.getValidadoresEnvolvidos(), flagSalvar));

		return mensagensValidacao;

	}

	public void atualizarValoresCamposCalculaveis(T quizQuestionarioModel) {

		HashMap<QuizModel, String> formulas = new HashMap<QuizModel, String>();

		@SuppressWarnings("unchecked")
		HashMap<String, Object> mapa = (HashMap<String, Object>) TSFacesUtil.getObjectInSession(Constantes.SESSION_VALIDADOR);

		for (QuizGrupoModel grupo : quizQuestionarioModel.getQuizGrupos()) {

			for (QuizModel quiz : grupo.getQuizes()) {

				if (!TSUtil.isEmpty(quiz.getFormula())) {

					formulas.put(quiz, quiz.getFormula());

				} else if (!TSUtil.isEmpty(quiz.getQuizPerguntaModel().getFormula())) {

					formulas.put(quiz, quiz.getQuizPerguntaModel().getFormula());

				}

				if (!TSUtil.isEmpty(quiz.getDoubleEscolhido())) {

					mapa.put("P" + quiz.getQuizPerguntaModel().getId() + "P", TSUtil.isEmpty(quiz.getQuizPerguntaModel().getMedidaModel().getUnidadesMedidas()) ? quiz.getDoubleEscolhido() : this.quizUtil.obterValorUnidadePadrao(quiz));
					mapa.put("Q" + quiz.getId() + "Q", TSUtil.isEmpty(quiz.getQuizPerguntaModel().getMedidaModel().getUnidadesMedidas()) ? quiz.getDoubleEscolhido() : this.quizUtil.obterValorUnidadePadrao(quiz));

				} else if (!TSUtil.isEmpty(quiz.getInteiroEscolhido())) {

					mapa.put("P" + quiz.getQuizPerguntaModel().getId() + "P", TSUtil.isEmpty(quiz.getQuizPerguntaModel().getMedidaModel().getUnidadesMedidas()) ? quiz.getInteiroEscolhido() : this.quizUtil.obterValorUnidadePadrao(quiz));
					mapa.put("Q" + quiz.getId() + "Q", TSUtil.isEmpty(quiz.getQuizPerguntaModel().getMedidaModel().getUnidadesMedidas()) ? quiz.getInteiroEscolhido() : this.quizUtil.obterValorUnidadePadrao(quiz));

				} else if (quiz.isPossuiResposta()) {

					mapa.put("P" + quiz.getQuizPerguntaModel().getId() + "P", quiz.getResposta());
					mapa.put("Q" + quiz.getId() + "Q", quiz.getResposta());

				} else {

					mapa.remove("P" + quiz.getQuizPerguntaModel().getId() + "P");
					mapa.remove("Q" + quiz.getId() + "Q");

				}

			}

		}

		this.quizUtil.tratarFormulas(formulas, mapa);

	}

}
