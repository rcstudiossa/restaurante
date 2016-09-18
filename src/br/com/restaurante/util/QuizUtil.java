package br.com.restaurante.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import br.com.restaurante.model.CampoQuestionarioIF;
import br.com.restaurante.model.CamposRespostaAb;
import br.com.restaurante.model.MedidaUnidadeMedidaModel;
import br.com.restaurante.model.QuizGrupoModel;
import br.com.restaurante.model.QuizModel;
import br.com.restaurante.model.QuizRespostaModel;
import br.com.restaurante.model.QuizValidadorModel;
import br.com.restaurante.model.ValidadorModel;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.util.TSFacesUtil;

@SuppressWarnings("serial")
public class QuizUtil implements Serializable {

	private ValidadorUtil validadorUtil;

	public QuizUtil() {
		this.validadorUtil = new ValidadorUtil();
	}

	private HashMap<String, Object> obterMapaValidacao(QuizModel quiz) {

		@SuppressWarnings("unchecked")
		HashMap<String, Object> mapa = (HashMap<String, Object>) TSFacesUtil.getObjectInSession(Constantes.SESSION_VALIDADOR);

		if (TSUtil.isEmpty(mapa)) {

			mapa = new HashMap<String, Object>();

		}

		for (QuizModel model : quiz.getQuizGrupoModel().getQuizes()) {

			if (!TSUtil.isEmpty(model.getDoubleEscolhido())) {

				mapa.put("P" + model.getQuizPerguntaModel().getId() + "P", TSUtil.isEmpty(model.getQuizPerguntaModel().getMedidaModel().getUnidadesMedidas()) ? model.getDoubleEscolhido() : this.obterValorUnidadePadrao(model));
				mapa.put("Q" + model.getId() + "Q", TSUtil.isEmpty(model.getQuizPerguntaModel().getMedidaModel().getUnidadesMedidas()) ? model.getDoubleEscolhido() : this.obterValorUnidadePadrao(model));

			} else if (!TSUtil.isEmpty(model.getInteiroEscolhido())) {

				mapa.put("P" + model.getQuizPerguntaModel().getId() + "P", TSUtil.isEmpty(model.getQuizPerguntaModel().getMedidaModel().getUnidadesMedidas()) ? model.getInteiroEscolhido() : this.obterValorUnidadePadrao(model));
				mapa.put("Q" + model.getId() + "Q", TSUtil.isEmpty(model.getQuizPerguntaModel().getMedidaModel().getUnidadesMedidas()) ? model.getInteiroEscolhido() : this.obterValorUnidadePadrao(model));

			} else if (model.isPossuiResposta()) {

				mapa.put("P" + model.getQuizPerguntaModel().getId() + "P", model.getResposta());
				mapa.put("Q" + model.getId() + "Q", model.getResposta());

			} else {

				mapa.remove("P" + model.getQuizPerguntaModel().getId() + "P");
				mapa.remove("Q" + model.getId() + "Q");

			}

		}

		return mapa;

	}

	public Map<String, String> validar(QuizModel quiz, boolean flagSalvar) {

		HashMap<String, Object> mapa = this.obterMapaValidacao(quiz);

		return this.validadorUtil.tratarValidadores(quiz.getRespostaDada(), mapa, quiz.getValidadoresEnvolvidos(), flagSalvar);

	}

	public boolean isValidacaoFalhou(QuizModel quiz, ValidadorModel validadorModel) {

		HashMap<String, Object> mapa = this.obterMapaValidacao(quiz);

		Object xxx = this.validadorUtil.executarFormulaObject(validadorModel.getExpressao(), mapa);

		if (xxx.getClass() == Boolean.class && ((Boolean) xxx) == Boolean.TRUE) {
			return true;
		}

		return false;
	}

	public boolean isValidacaoFalhou(HashMap<String, Object> mapa, ValidadorModel validadorModel) {

		Object xxx = this.validadorUtil.executarFormulaObject(validadorModel.getExpressao(), mapa);

		if (xxx.getClass() == Boolean.class && ((Boolean) xxx) == Boolean.TRUE) {
			return true;
		}

		return false;
	}

	public Double obterValorUnidadePadrao(CamposRespostaAb quiz) {

		QuizModel quizAux = new QuizModel();

		quizAux.setQuizPerguntaModel(quiz.getQuizPerguntaModel());
		quizAux.setUnidadeAnterior(quiz.getUnidade());
		quizAux.setDoubleEscolhido(quiz.getDoubleEscolhido());
		quizAux.setInteiroEscolhido(quiz.getInteiroEscolhido());
		quizAux.popularReferenciaPontoFlutuante();

		for (MedidaUnidadeMedidaModel unidadeMedida : quiz.getQuizPerguntaModel().getMedidaModel().getUnidadesMedidas()) {

			if (unidadeMedida.getFlagPadrao()) {

				quizAux.setUnidade(unidadeMedida.getUnidadeMedidaModel().getDescricao());
				break;

			}

		}

		return this.obterValorConvertido(quizAux);

	}

	public Double obterValorConvertido(CamposRespostaAb quiz) {

		MedidaUnidadeMedidaModel unidadeOrigem = null;
		MedidaUnidadeMedidaModel unidadeDestino = null;

		if (!TSUtil.isEmpty(quiz.getQuizPerguntaModel().getMedidaModel().getUnidadesMedidas())) {
			unidadeOrigem = quiz.getQuizPerguntaModel().getMedidaModel().getUnidadesMedidas().get(0);
		}

		for (MedidaUnidadeMedidaModel unidade : quiz.getQuizPerguntaModel().getMedidaModel().getUnidadesMedidas()) {

			if (unidade.getUnidadeMedidaModel().getDescricao().equals(quiz.getUnidadeAnterior())) {
				unidadeOrigem = unidade;
			}

			if (unidade.getUnidadeMedidaModel().getDescricao().equals(quiz.getUnidade())) {
				unidadeDestino = unidade;
			}

		}

		Double valorConvertidoVolta = 0.0;

		if (quiz.getQuizPerguntaModel().isPontoFlutuante()) {

			valorConvertidoVolta = this.validadorUtil.executarFormula(unidadeOrigem.getFormulaVolta(), quiz.getDoubleEscolhido());

		} else if (quiz.getQuizPerguntaModel().isNumerico()) {

			valorConvertidoVolta = this.validadorUtil.executarFormula(unidadeOrigem.getFormulaVolta(), quiz.getInteiroEscolhido().doubleValue());

		}

		return this.validadorUtil.executarFormula(unidadeDestino.getFormulaIda(), valorConvertidoVolta);
	}

	public Map<String, String> processarCampo(QuizModel quiz, List<QuizGrupoModel> grupos, boolean flagSalvar) {

		Map<String, String> mensagensErro = new HashMap<String, String>();
		
		String mensagemErro = this.validarTipoResposta(quiz);
		
		if (!TSUtil.isEmpty(mensagemErro)) {
			mensagensErro.put(mensagemErro, Constantes.TIPO_MENSAGEM_ERRO);
		}
		
		HashMap<QuizModel, String> formulas = new HashMap<QuizModel, String>();

		@SuppressWarnings("unchecked")
		HashMap<String, Object> mapa = (HashMap<String, Object>) TSFacesUtil.getObjectInSession(Constantes.SESSION_VALIDADOR);

		for (QuizGrupoModel grupo : grupos) {

			for (QuizModel model : grupo.getQuizes()) {

				if (!TSUtil.isEmpty(model.getRespostaEscolhida())) {
					mapa.put("P" + model.getQuizPerguntaModel().getId() + "P", model.getRespostaEscolhida());
					mapa.put("Q" + model.getId() + "Q", model.getRespostaEscolhida());
				}

				if (!TSUtil.isEmpty(model.getDoubleEscolhido())) {
					mapa.put("P" + model.getQuizPerguntaModel().getId() + "P", TSUtil.isEmpty(model.getQuizPerguntaModel().getMedidaModel().getUnidadesMedidas()) ? model.getDoubleEscolhido() : this.obterValorUnidadePadrao(model));
					mapa.put("Q" + model.getId() + "Q", TSUtil.isEmpty(model.getQuizPerguntaModel().getMedidaModel().getUnidadesMedidas()) ? model.getDoubleEscolhido() : this.obterValorUnidadePadrao(model));
				}

				if (!TSUtil.isEmpty(model.getInteiroEscolhido())) {
					mapa.put("P" + model.getQuizPerguntaModel().getId() + "P", TSUtil.isEmpty(model.getQuizPerguntaModel().getMedidaModel().getUnidadesMedidas()) ? model.getInteiroEscolhido() : this.obterValorUnidadePadrao(model));
					mapa.put("Q" + model.getId() + "Q", TSUtil.isEmpty(model.getQuizPerguntaModel().getMedidaModel().getUnidadesMedidas()) ? model.getInteiroEscolhido() : this.obterValorUnidadePadrao(model));
				}

				if (!TSUtil.isEmpty(model.getFormula())) {

					if (model.getFormula().contains("Q" + quiz.getId() + "Q")) {

						formulas.put(model, model.getFormula());

					}

				} else if (!TSUtil.isEmpty(model.getQuizPerguntaModel().getFormula())) {

					if (model.getQuizPerguntaModel().getFormula().contains("P" + quiz.getQuizPerguntaModel().getId() + "P")) {

						formulas.put(model, model.getQuizPerguntaModel().getFormula());

					}

				}

			}

		}

		if (TSUtil.isEmpty(quiz.getRespostaDada())) {
			mapa.remove("P" + quiz.getQuizPerguntaModel().getId() + "P");
			mapa.remove("Q" + quiz.getId() + "Q");
		}

		this.tratarFormulas(formulas, mapa);

		if (!TSUtil.isEmpty(quiz.getValidadoresEnvolvidos())) {

			mensagensErro.putAll(this.validar(quiz, flagSalvar));
			
		}
		
		this.tratarValidacoes(quiz, grupos);

		return mensagensErro;
	}

	public void tratarFormulas(Map<QuizModel, String> formulas, Map<String, Object> mapa) {

		for (Entry<QuizModel, String> formula : formulas.entrySet()) {

			Object resultadoObject = this.validadorUtil.executarFormulaObject(formula.getValue(), mapa);
			Double resultadoDouble = this.validadorUtil.converterDouble(resultadoObject);

			QuizModel quizModel = ((QuizModel) formula.getKey());

			if (!quizModel.getQuizPerguntaModel().isConsequencia()) {

				quizModel.setDoubleEscolhido(resultadoDouble);
				quizModel.popularReferenciaPontoFlutuante();

				mapa.put("P" + quizModel.getQuizPerguntaModel().getId() + "P", TSUtil.isEmpty(quizModel.getQuizPerguntaModel().getMedidaModel().getUnidadesMedidas()) ? quizModel.getDoubleEscolhido() : this.obterValorUnidadePadrao(quizModel));
				mapa.put("Q" + quizModel.getId() + "Q", TSUtil.isEmpty(quizModel.getQuizPerguntaModel().getMedidaModel().getUnidadesMedidas()) ? quizModel.getDoubleEscolhido() : this.obterValorUnidadePadrao(quizModel));

			} else {

				if (resultadoObject != null) {

					for (QuizRespostaModel resposta : quizModel.getQuizPerguntaModel().getRespostas()) {

						boolean retorno = (Boolean) this.validadorUtil.executarFormulaObject(resposta.getFormulaConsequencia().replace("P_VALOR", resultadoObject.toString()), mapa);

						if (retorno) {
							quizModel.setRespostaEscolhida(resposta.getResposta());
							break;
						} else {
							quizModel.setRespostaEscolhida(null);
						}

					}

				} else {

					quizModel.setRespostaEscolhida(null);

				}

			}

		}

	}

	public String validarInteiro(CamposRespostaAb quiz) {

		if (TSUtil.isEmpty(quiz.getRespostaEscolhida())) {

			quiz.setInteiroEscolhido(null);

		} else {

			if (!TSUtil.isNumeric(quiz.getRespostaEscolhida())) {

				quiz.setRespostaEscolhida(null);
				return "Valor inválido para o campo " + quiz.getApelido();

			} else {

				quiz.setInteiroEscolhido(Long.valueOf(quiz.getRespostaEscolhida()));

			}

		}

		return null;

	}

	public String validarPontoFlutuante(CamposRespostaAb quiz) {

		if (TSUtil.isEmpty(quiz.getRespostaEscolhida())) {

			quiz.setDoubleEscolhido(null);

		} else {

			if (!Utilitario.isNumeric(quiz.getRespostaEscolhida().replaceAll(",", "."))) {

				quiz.setRespostaEscolhida(null);
				return "Valor inválido para o campo " + quiz.getApelido();

			} else {

				quiz.setDoubleEscolhido(Double.valueOf(quiz.getRespostaEscolhida().replaceAll(",", ".")));

			}

		}

		return null;

	}

	public String validarTipoResposta(CamposRespostaAb quiz){
		
		if (quiz.getQuizPerguntaModel().isNumerico()) {

			return this.validarInteiro(quiz);

		} else if (quiz.getQuizPerguntaModel().isPontoFlutuante()) {

			return this.validarPontoFlutuante(quiz);

		}
		
		return null;
		
	}
	
	public void tratarValidacoes(QuizModel quiz, List<QuizGrupoModel> grupos){
		
		boolean validacaoFalhou = false;
		
		for (QuizGrupoModel grupo : grupos) {

			for (QuizModel quizModel : grupo.getQuizes()) {

				for (QuizValidadorModel quizValidador : quizModel.getValidadores()) {

					if (quiz.getValidadoresEnvolvidos().contains(quizValidador.getValidadorModel())) {

						if (TSUtil.isEmpty(quizValidador.getValidadorModel().getQueryValidacao())) {

							validacaoFalhou = this.isValidacaoFalhou(quiz, quizValidador.getValidadorModel());

							if (validacaoFalhou) {
								Utilitario.setResposta(quizModel, quizValidador.getRespostaPadrao(), null);
							}

						}

					}

				}

			}

		}
		
	}
	
	public Map<String, String> validaCampos(CampoQuestionarioIF quizQuestionarioModel, List<QuizGrupoModel> grupos) {
		
		Map<String, String> mensagensErro = new HashMap<String, String>();

		quizQuestionarioModel.setQuerys(new ArrayList<String>());

		for (QuizGrupoModel grupo : grupos) {

			for (QuizModel quiz : grupo.getQuizes()) {

				if (quiz.getQuizPerguntaModel().isNumerico()) {

					String msg = this.validarInteiro(quiz);

					if (!TSUtil.isEmpty(msg)) {

						mensagensErro.put(msg, Constantes.TIPO_MENSAGEM_ERRO);

					}

				} else if (quiz.getQuizPerguntaModel().isPontoFlutuante()) {

					String msg = this.validarPontoFlutuante(quiz);

					if (!TSUtil.isEmpty(msg)) {

						mensagensErro.put(msg, Constantes.TIPO_MENSAGEM_ERRO);

					}

				}

				if (quiz.isPossuiResposta()) {

					mensagensErro.putAll(this.validar(quiz, true));

					boolean validacaoFalhou = false;

					for (ValidadorModel validadorModel : quiz.getValidadoresEnvolvidos()) {

						if (TSUtil.isEmpty(validadorModel.getQueryValidacao())) {

							validacaoFalhou = this.isValidacaoFalhou(quiz, validadorModel);

							if (validacaoFalhou && !TSUtil.isEmpty(validadorModel.getQuery())) {

								quizQuestionarioModel.getQuerys().add(validadorModel.getQuery());

							}

						}

						validacaoFalhou = false;

					}

				} else if (quiz.getFlagObrigatorio()) {

					mensagensErro.put((TSUtil.isEmpty(quiz.getApelido()) ? (TSUtil.isEmpty(quiz.getQuizPerguntaModel().getApelido()) ? quiz.getQuizPerguntaModel().getPergunta() : quiz.getQuizPerguntaModel().getApelido()) : quiz.getApelido()) + ": Campo obrigatório", Constantes.TIPO_MENSAGEM_ERRO);

				}

			}

		}
		
		return mensagensErro;
		
	}
	
}
