package br.com.restaurante.model;

import java.util.ArrayList;


@SuppressWarnings("serial")
public class QuizQuestionarioRespostaModel extends QuizQuestionarioRespostaAb<QuizQuestionarioModel, QuizQuestionarioRespostaModel, QuizQuestionarioArquetipoRespostaModel> {

	private QuizQuestionarioModel quizTemplateModel;
	
	@Override
	public void instanciarArquetipo() {
		this.arquetiposRespostas = new ArrayList<QuizQuestionarioArquetipoRespostaModel>();
	}
	
	@Override
	public void setQuestionario(CampoQuestionarioIF questionario) {
		this.setQuizTemplate((QuizQuestionarioModel)questionario);
	}
	
	@Override
	public QuizTemplateRespostaIf<QuizQuestionarioModel, QuizQuestionarioRespostaModel, QuizQuestionarioArquetipoRespostaModel> getInstance() {
		return new QuizQuestionarioRespostaModel();
	}

	@Override
	public QuizQuestionarioModel getQuizTemplate() {
		return this.quizTemplateModel;
	}

	@Override
	public void setQuizTemplate(QuizQuestionarioModel quizTemplate) {
		this.quizTemplateModel = quizTemplate;
	}
	
	@Override
	public void addArquetipo(ArquetipoRespostaIF arquetipo) {
		this.arquetiposRespostas.add((QuizQuestionarioArquetipoRespostaModel)arquetipo);
	}
	
}
