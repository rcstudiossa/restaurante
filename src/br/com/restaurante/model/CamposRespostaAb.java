package br.com.restaurante.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.restaurante.util.Constantes;
import br.com.topsys.util.TSDateUtil;
import br.com.topsys.util.TSParseUtil;
import br.com.topsys.util.TSUtil;

@SuppressWarnings("serial")
public abstract class CamposRespostaAb implements Serializable {

	protected QuizPerguntaModel quizPerguntaModel;
	protected QuizModel quizModel;

	protected Long inteiroEscolhido;
	protected Double doubleEscolhido;
	protected Date dataEscolhida;
	protected String respostaEscolhida;
	protected Boolean booleanEscolhido;
	protected List<String> respostasEscolhidas;
	protected Double qtdFrequencia;
	protected String outros;
	protected String unidadeAnterior;
	protected String unidade;
	protected Boolean habilitado = Boolean.FALSE;
	protected Long idResposta;
	private List<ValidadorModel> validadoresEnvolvidos;
	
	public abstract String getApelido(); 

	public QuizPerguntaModel getQuizPerguntaRespondida() {
		return quizPerguntaModel;
	}

	public QuizPerguntaModel getQuizPerguntaModel() {
		return quizPerguntaModel;
	}

	public void setQuizPerguntaModel(QuizPerguntaModel quizPerguntaModel) {
		this.quizPerguntaModel = quizPerguntaModel;
	}

	public Date getDataEscolhida() {
		return dataEscolhida;
	}

	public void setDataEscolhida(Date dataEscolhida) {
		this.dataEscolhida = dataEscolhida;
	}

	public String getRespostaEscolhida() {
		return respostaEscolhida;
	}

	public void setRespostaEscolhida(String respostaEscolhida) {
		this.respostaEscolhida = respostaEscolhida;
	}

	public List<String> getRespostasEscolhidas() {
		return respostasEscolhidas;
	}

	public void setRespostasEscolhidas(List<String> respostasEscolhidas) {
		this.respostasEscolhidas = respostasEscolhidas;
	}

	public Double getQtdFrequencia() {
		return qtdFrequencia;
	}

	public void setQtdFrequencia(Double qtdFrequencia) {
		this.qtdFrequencia = qtdFrequencia;
	}

	public String getOutros() {
		return TSUtil.tratarString(outros);
	}

	public void setOutros(String outros) {
		this.outros = outros;
	}

	public Boolean getHabilitado() {
		return habilitado;
	}

	public void setHabilitado(Boolean habilitado) {
		this.habilitado = habilitado;
	}

	public Long getInteiroEscolhido() {
		return inteiroEscolhido;
	}

	public void setInteiroEscolhido(Long inteiroEscolhido) {
		this.inteiroEscolhido = inteiroEscolhido;
	}

	public Double getDoubleEscolhido() {
		return doubleEscolhido;
	}

	public void setDoubleEscolhido(Double doubleEscolhido) {
		this.doubleEscolhido = doubleEscolhido;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public String getUnidadeAnterior() {
		return unidadeAnterior;
	}

	public void setUnidadeAnterior(String unidadeAnterior) {
		this.unidadeAnterior = unidadeAnterior;
	}

	public Long getIdResposta() {
		return idResposta;
	}

	public void setIdResposta(Long idResposta) {
		this.idResposta = idResposta;
	}

	public QuizModel getQuizModel() {
		return quizModel;
	}

	public void setQuizModel(QuizModel quizModel) {
		this.quizModel = quizModel;
	}

	public List<ValidadorModel> getValidadoresEnvolvidos() {
		return validadoresEnvolvidos;
	}

	public void setValidadoresEnvolvidos(List<ValidadorModel> validadoresEnvolvidos) {
		this.validadoresEnvolvidos = validadoresEnvolvidos;
	}

	public Boolean getBooleanEscolhido() {
		return booleanEscolhido;
	}

	public void setBooleanEscolhido(Boolean booleanEscolhido) {
		this.booleanEscolhido = booleanEscolhido;
	}

	public Object getResposta() {

		if (Constantes.TIPO_RESPOSTA_QUIZ_MULTIPLO.equals(this.getQuizPerguntaRespondida().getTipoRespostaModel().getId())) {

			String resp = "";

			for (String item : this.getRespostasEscolhidas()) {

				resp = resp + item + ", ";

			}

			resp = resp.substring(0, resp.lastIndexOf(","));

			return resp;

		} else if (Constantes.TIPO_RESPOSTA_QUIZ_DATA.equals(this.getQuizPerguntaRespondida().getTipoRespostaModel().getId())) {

			return this.getDataEscolhida();

		} else if (Constantes.TIPO_RESPOSTA_QUIZ_DATA_HORA.equals(this.getQuizPerguntaRespondida().getTipoRespostaModel().getId())) {

			return this.getDataEscolhida();

		} else if (Constantes.TIPO_RESPOSTA_QUIZ_HORA.equals(this.getQuizPerguntaRespondida().getTipoRespostaModel().getId())) {

			return this.getDataEscolhida();

		} else if (Constantes.TIPO_RESPOSTA_QUIZ_NUMERICO.equals(this.getQuizPerguntaRespondida().getTipoRespostaModel().getId())) {

			return this.getInteiroEscolhido();

		} else if (Constantes.TIPO_RESPOSTA_QUIZ_BOOLEANO.equals(this.getQuizPerguntaRespondida().getTipoRespostaModel().getId())) {

			return !TSUtil.isEmpty(this.booleanEscolhido) && this.booleanEscolhido ? "Sim" : "Não" ;

		} else if (Constantes.TIPO_RESPOSTA_QUIZ_PONTO_FLUTUANTE.equals(this.getQuizPerguntaRespondida().getTipoRespostaModel().getId())) {

			if (TSUtil.isEmpty(this.getDoubleEscolhido())) {
				return null;
			}

			return this.getDoubleEscolhido();

		} else {

			return this.getRespostaEscolhida();

		}

	}

	public void setRespostaDada(String resposta) {

		if (TSUtil.isEmpty(resposta)) {
			return;
		}

		if (Constantes.TIPO_RESPOSTA_QUIZ_DATA.equals(this.getQuizPerguntaRespondida().getTipoRespostaModel().getId())) {

			this.setDataEscolhida(TSParseUtil.stringToDate(resposta, TSDateUtil.DD_MM_YYYY));

		} else if (Constantes.TIPO_RESPOSTA_QUIZ_DATA_HORA.equals(this.getQuizPerguntaRespondida().getTipoRespostaModel().getId())) {

			this.setDataEscolhida(TSParseUtil.stringToDate(resposta, TSDateUtil.DD_MM_YYYY_HH_MM));

		} else if (Constantes.TIPO_RESPOSTA_QUIZ_HORA.equals(this.getQuizPerguntaRespondida().getTipoRespostaModel().getId())) {

			this.setDataEscolhida(TSParseUtil.stringToDate(resposta, TSDateUtil.HH_MM));

		} else if (Constantes.TIPO_RESPOSTA_QUIZ_NUMERICO.equals(this.getQuizPerguntaRespondida().getTipoRespostaModel().getId())) {

			this.setInteiroEscolhido(Long.valueOf(resposta));
			
			this.popularReferenciaNumerico();

		} else if (Constantes.TIPO_RESPOSTA_QUIZ_PONTO_FLUTUANTE.equals(this.getQuizPerguntaRespondida().getTipoRespostaModel().getId())) {

			this.setDoubleEscolhido(Double.valueOf(resposta));
			
			this.popularReferenciaPontoFlutuante();

		} else if (Constantes.TIPO_RESPOSTA_QUIZ_BOOLEANO.equals(this.getQuizPerguntaRespondida().getTipoRespostaModel().getId())) {

			if(String.valueOf(resposta).equals("Sim")){
				
				this.booleanEscolhido = true;
				
			} else {
				
				this.booleanEscolhido = false;
				
			}

		} else if (Constantes.TIPO_RESPOSTA_QUIZ_MULTIPLO.equals(this.getQuizPerguntaRespondida().getTipoRespostaModel().getId()) || Constantes.TIPO_RESPOSTA_MULTIPLO_PANEL.equals(this.getQuizPerguntaRespondida().getTipoRespostaModel().getId())) {

			String[] split = resposta.split(",");

			this.setRespostasEscolhidas(new ArrayList<String>());

			for (String item : split) {

				this.getRespostasEscolhidas().add(item.trim());

			}

		} else if (Constantes.TIPO_RESPOSTA_LISTA_INPUT_TEXT.equals(this.getQuizPerguntaRespondida().getTipoRespostaModel().getId()) || Constantes.TIPO_RESPOSTA_LISTA_INPUT_TEXTAREA.equals(this.getQuizPerguntaRespondida().getTipoRespostaModel().getId())) {

			if (this.getRespostasEscolhidas() == null) {

				this.setRespostasEscolhidas(new ArrayList<String>());

			}

			this.getRespostasEscolhidas().add(resposta);

		} else {

			this.setRespostaEscolhida(resposta);

		}

	}
	
	public void popularReferenciaPontoFlutuante(){
		this.setRespostaEscolhida(String.valueOf(this.getDoubleEscolhido()).replaceAll("\\.", ",").replaceAll("null", ""));
	}
	
	public void popularReferenciaNumerico(){
		this.setRespostaEscolhida(TSUtil.removerNaoDigitos(String.valueOf(this.getInteiroEscolhido())));
	}

	public String getRespostaDada() {

		if (Constantes.TIPO_RESPOSTA_QUIZ_MULTIPLO.equals(this.getQuizPerguntaRespondida().getTipoRespostaModel().getId()) || Constantes.TIPO_RESPOSTA_MULTIPLO_PANEL.equals(this.getQuizPerguntaRespondida().getTipoRespostaModel().getId())) {

			if (TSUtil.isEmpty(this.getRespostasEscolhidas())) {
				return null;
			}

			String resp = "";

			for (String item : this.getRespostasEscolhidas()) {

				resp = resp + item + ", ";

			}

			resp = resp.substring(0, resp.lastIndexOf(","));

			return resp;

		} else if (Constantes.TIPO_RESPOSTA_QUIZ_DATA.equals(this.getQuizPerguntaRespondida().getTipoRespostaModel().getId())) {

			return TSParseUtil.dateToString(this.getDataEscolhida(), TSDateUtil.DD_MM_YYYY);

		} else if (Constantes.TIPO_RESPOSTA_QUIZ_DATA_HORA.equals(this.getQuizPerguntaRespondida().getTipoRespostaModel().getId())) {

			return TSParseUtil.dateToString(this.getDataEscolhida(), TSDateUtil.DD_MM_YYYY_HH_MM);

		} else if (Constantes.TIPO_RESPOSTA_QUIZ_HORA.equals(this.getQuizPerguntaRespondida().getTipoRespostaModel().getId())) {

			return TSParseUtil.dateToString(this.getDataEscolhida(), TSDateUtil.HH_MM);

		} else if (Constantes.TIPO_RESPOSTA_QUIZ_NUMERICO.equals(this.getQuizPerguntaRespondida().getTipoRespostaModel().getId())) {

			if (TSUtil.isEmpty(this.getInteiroEscolhido())) {
				return null;
			}

			return this.getInteiroEscolhido().toString();

		} else if (Constantes.TIPO_RESPOSTA_QUIZ_PONTO_FLUTUANTE.equals(this.getQuizPerguntaRespondida().getTipoRespostaModel().getId())) {

			if (TSUtil.isEmpty(this.getDoubleEscolhido())) {
				return null;
			}

			return this.getDoubleEscolhido().toString();

		} else if (Constantes.TIPO_RESPOSTA_QUIZ_BOOLEANO.equals(this.getQuizPerguntaRespondida().getTipoRespostaModel().getId())) {

			return !TSUtil.isEmpty(this.booleanEscolhido) && this.booleanEscolhido ? "Sim" : "Não" ;

		} else {

			return this.getRespostaEscolhida();

		}

	}

	public Object getRespostaDadaObject() {

		if (Constantes.TIPO_RESPOSTA_QUIZ_MULTIPLO.equals(this.getQuizPerguntaRespondida().getTipoRespostaModel().getId()) || Constantes.TIPO_RESPOSTA_MULTIPLO_PANEL.equals(this.getQuizPerguntaRespondida().getTipoRespostaModel().getId())) {

			if (TSUtil.isEmpty(this.getRespostasEscolhidas())) {
				return null;
			}

			String resp = "";

			for (String item : this.getRespostasEscolhidas()) {

				resp = resp + item + ", ";

			}

			resp = resp.substring(0, resp.lastIndexOf(","));

			return resp;

		} else if (Constantes.TIPO_RESPOSTA_QUIZ_DATA.equals(this.getQuizPerguntaRespondida().getTipoRespostaModel().getId()) || Constantes.TIPO_RESPOSTA_QUIZ_DATA_HORA.equals(this.getQuizPerguntaRespondida().getTipoRespostaModel().getId()) || Constantes.TIPO_RESPOSTA_QUIZ_HORA.equals(this.getQuizPerguntaRespondida().getTipoRespostaModel().getId())) {

			return this.getDataEscolhida();

		} else if (Constantes.TIPO_RESPOSTA_QUIZ_NUMERICO.equals(this.getQuizPerguntaRespondida().getTipoRespostaModel().getId())) {

			return this.getInteiroEscolhido();

		} else if (Constantes.TIPO_RESPOSTA_QUIZ_PONTO_FLUTUANTE.equals(this.getQuizPerguntaRespondida().getTipoRespostaModel().getId())) {

			return this.getDoubleEscolhido();

		} else if (Constantes.TIPO_RESPOSTA_QUIZ_BOOLEANO.equals(this.getQuizPerguntaRespondida().getTipoRespostaModel().getId())) {

			return !TSUtil.isEmpty(this.booleanEscolhido) && this.booleanEscolhido ? "Sim" : "Não" ;

		} else {

			return this.getRespostaEscolhida();

		}

	}

	public void limparResposta() {
		this.setRespostaEscolhida(null);
		this.setRespostasEscolhidas(null);
		this.setDataEscolhida(null);
		this.setInteiroEscolhido(null);
		this.setDoubleEscolhido(null);
		this.setBooleanEscolhido(null);
	}

	public boolean isPossuiResposta() {
		return !TSUtil.isEmpty(this.getRespostasEscolhidas()) || !TSUtil.isEmpty(this.getDataEscolhida()) || !TSUtil.isEmpty(this.getInteiroEscolhido()) || !TSUtil.isEmpty(this.getDoubleEscolhido()) || !TSUtil.isEmpty(this.getRespostaEscolhida()) || !TSUtil.isEmpty(this.getBooleanEscolhido());
	}

}
