package br.com.restaurante.model;

import java.util.Date;
import java.util.List;

import br.com.topsys.util.TSUtil;

@SuppressWarnings("serial")
public abstract class QuizQuestionarioRespostaAb<T extends QuizTemplateIf<R>, R extends QuizTemplateRespostaIf<T, R, A>, A extends QuizTemplateArquetipoRespostaAb<T, R, A>> extends CamposRespostaAb implements QuizTemplateRespostaIf<T, R, A> {

	private Long id;

	private QuizModel quizModel;

	private String resposta;

	private String outros;
	
	private String unidade;
	
	private Long idResposta;

	protected List<A> arquetiposRespostas;

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public QuizModel getQuizModel() {
		return quizModel;
	}

	public void setQuizModel(QuizModel quizModel) {
		this.quizModel = quizModel;
	}

	public String getResposta() {
		return resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
	}

	public String getOutros() {
		return outros;
	}

	public void setOutros(String outros) {
		this.outros = outros;
	}

	public List<A> getArquetiposRespostas() {
		return arquetiposRespostas;
	}

	public void setArquetiposRespostas(List<A> arquetiposRespostas) {
		this.arquetiposRespostas = arquetiposRespostas;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public Long getIdResposta() {
		return idResposta;
	}

	public void setIdResposta(Long idResposta) {
		this.idResposta = idResposta;
	}
	
	@Override
	public Date getDataInicial() {
		return null;
	}

	@Override
	public String toString() {

		StringBuilder texto = new StringBuilder();

		if (!TSUtil.isEmpty(this.resposta)) {
			texto.append(resposta);
		}

		if (!TSUtil.isEmpty(this.outros)) {
			texto.append(outros);
		}

		return texto.toString();
	}
	
	@Override
	public String getApelido() {
		return this.quizModel.getApelido();
	}

}
