package br.com.restaurante.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import br.com.restaurante.model.SolicitacaoExameItemResultadoModel;
import br.com.restaurante.model.ValidadorModel;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.util.TSFacesUtil;

@SuppressWarnings("serial")
public class ExameUtil implements Serializable {

	private ValidadorUtil validadorUtil;

	public ExameUtil() {
		this.validadorUtil = new ValidadorUtil();
	}

	public boolean validar(SolicitacaoExameItemResultadoModel exame, List<SolicitacaoExameItemResultadoModel> exames, List<ValidadorModel> validadores, boolean flagSalvar) {

		HashMap<String, Object> mapa = this.obterMapaValidacao(exames);
		
		Map<String, String> mensagensValidacao = new HashMap<String, String>();

		mensagensValidacao.putAll(this.validadorUtil.tratarValidadores(exame.getRespostaDada(), mapa, validadores, flagSalvar));
		
		return Utilitario.tratarMensagensValidacaoQuiz(mensagensValidacao, null);

	}

	public String processarCampo(SolicitacaoExameItemResultadoModel exame, List<SolicitacaoExameItemResultadoModel> exames, boolean flagSalvar) {

		HashMap<SolicitacaoExameItemResultadoModel, String> formulas = new HashMap<SolicitacaoExameItemResultadoModel, String>();

		@SuppressWarnings("unchecked")
		HashMap<String, Object> mapa = (HashMap<String, Object>) TSFacesUtil.getObjectInSession(Constantes.SESSION_VALIDADOR);

		for (SolicitacaoExameItemResultadoModel model : exames) {

			if(TSUtil.isEmpty(model.getRespostaDada()) && mapa.containsKey("P" + model.getQuizPerguntaModel().getId() + "P")){
				mapa.put("P" + model.getQuizPerguntaModel().getId() + "P", null);
			}
			
			if (!TSUtil.isEmpty(model.getRespostaDada()) && Utilitario.isNumeric(model.getRespostaDada())) {
				mapa.put("P" + model.getQuizPerguntaModel().getId() + "P", new BigDecimal(model.getRespostaDada()).setScale(2, BigDecimal.ROUND_HALF_UP));
			}

			if (!TSUtil.isEmpty(model.getQuizPerguntaModel().getFormula())) {

				if (model.getQuizPerguntaModel().getFormula().contains("P" + exame.getQuizPerguntaModel().getId() + "P")) {

					formulas.put(model, model.getQuizPerguntaModel().getFormula());

				}

			}

		}

		this.tratarFormulasExame(formulas, mapa, exames, exame.getQuizPerguntaModel().getValidadores(), flagSalvar);

		if (!TSUtil.isEmpty(exame.getQuizPerguntaModel().getValidadores())) {

			this.validadorUtil.tratarValidadores(exame.getRespostaDada(), mapa, exame.getQuizPerguntaModel().getValidadores(), flagSalvar);

		}
		
		this.carregarPonteiroReferencia(exame);

		return null;
	}

	public void tratarFormulasExame(HashMap<SolicitacaoExameItemResultadoModel, String> formulas, HashMap<String, Object> mapa, List<SolicitacaoExameItemResultadoModel> exames, List<ValidadorModel> validadores, boolean flagSalvar) {

		for (Entry<SolicitacaoExameItemResultadoModel, String> formula : formulas.entrySet()) {

			Double resultado = this.validadorUtil.executarFormula(formula.getValue(), mapa);

			SolicitacaoExameItemResultadoModel exame = ((SolicitacaoExameItemResultadoModel) formula.getKey());

			if (TSUtil.isEmpty(resultado)) {

				exame.setRespostaDada(null);

			} else {

				exame.setRespostaDada(String.valueOf(resultado));
				mapa.put("P" + exame.getQuizPerguntaModel().getId() + "P", resultado);

			}

			this.processarCampo(exame, exames, flagSalvar);

		}

	}

	private HashMap<String, Object> obterMapaValidacao(List<SolicitacaoExameItemResultadoModel> exames) {

		@SuppressWarnings("unchecked")
		HashMap<String, Object> mapa = (HashMap<String, Object>) TSFacesUtil.getObjectInSession(Constantes.SESSION_VALIDADOR);

		if (TSUtil.isEmpty(mapa)) {
			mapa = new HashMap<String, Object>();
		}

		for (SolicitacaoExameItemResultadoModel model : exames) {

			if (!TSUtil.isEmpty(model.getRespostaDada()) && Utilitario.isNumeric(model.getRespostaDada())) {

				mapa.put("P" + model.getQuizPerguntaModel().getId() + "P", new Double(model.getRespostaDada()));

			}

		}

		return mapa;

	}
	
	
	private void carregarPonteiroReferencia(SolicitacaoExameItemResultadoModel exame){
	
		Double valorReferenciaMinima = exame.getQuizPerguntaModel().getReferenciaMinima();
		Double valorReferenciaMaxima = exame.getQuizPerguntaModel().getReferenciaMaxima();
		
		if(!TSUtil.isEmpty(valorReferenciaMinima) && !TSUtil.isEmpty(valorReferenciaMaxima) && !TSUtil.isEmpty(exame.getDoubleEscolhido())){
			
			Double limiteMinimo = valorReferenciaMinima - (valorReferenciaMaxima - valorReferenciaMinima);
			Double limiteMaximo = valorReferenciaMaxima + (valorReferenciaMaxima - valorReferenciaMinima);
			
			if(exame.getDoubleEscolhido() <= limiteMinimo){
				
				exame.setPosicaoPonteiroReferencia(0.0);
				
			} else if(exame.getDoubleEscolhido() >= limiteMaximo){
				
				exame.setPosicaoPonteiroReferencia(120.0);
				
			} else {
				
				Double fatorConversao = (valorReferenciaMaxima - valorReferenciaMinima) / 40.0;
				
				Double posicaoReal = exame.getDoubleEscolhido() - limiteMinimo;
				
				posicaoReal = posicaoReal / fatorConversao;
				
				exame.setPosicaoPonteiroReferencia(posicaoReal);
				
			}
			
		}
		
	}
	
	public void carregarValoresReferencia(SolicitacaoExameItemResultadoModel resultado){
		
		if(!TSUtil.isEmpty(resultado.getQuizPerguntaModel().getFormulaReferenciaMinima()) && !TSUtil.isEmpty(resultado.getQuizPerguntaModel().getFormulaReferenciaMaxima())){
			
			@SuppressWarnings("unchecked")
			HashMap<String, Object> mapa = (HashMap<String, Object>) TSFacesUtil.getObjectInSession(Constantes.SESSION_VALIDADOR);
			
			if (mapa == null) {
				mapa = new HashMap<String, Object>();
			}
			
			Double retorno = this.validadorUtil.executarFormula(resultado.getQuizPerguntaModel().getFormulaReferenciaMinima(), mapa);
			
			resultado.getQuizPerguntaModel().setReferenciaMinima(new BigDecimal(retorno).setScale(2, RoundingMode.HALF_EVEN).doubleValue());
			
			retorno = this.validadorUtil.executarFormula(resultado.getQuizPerguntaModel().getFormulaReferenciaMaxima(), mapa);
			
			resultado.getQuizPerguntaModel().setReferenciaMaxima(new BigDecimal(retorno).setScale(2, RoundingMode.HALF_EVEN).doubleValue());
			
			if(!TSUtil.isEmpty(resultado.getDoubleEscolhido())){
				this.carregarPonteiroReferencia(resultado);
			}
			
		}
		
	}

}
