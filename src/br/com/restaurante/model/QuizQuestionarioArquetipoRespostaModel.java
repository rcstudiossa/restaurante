package br.com.restaurante.model;

@SuppressWarnings("serial")
public class QuizQuestionarioArquetipoRespostaModel extends QuizTemplateArquetipoRespostaAb<QuizQuestionarioModel, QuizQuestionarioRespostaModel, QuizQuestionarioArquetipoRespostaModel>{

	private QuizQuestionarioRespostaModel quizTemplateRespostaModel;

	public QuizQuestionarioRespostaModel getQuizTemplateRespostaModel() {
		return quizTemplateRespostaModel;
	}

	public void setQuizTemplateRespostaModel(QuizQuestionarioRespostaModel quizTemplateRespostaModel) {
		this.quizTemplateRespostaModel = quizTemplateRespostaModel;
	}
	
	@Override
	public void setQuizTemplateRespostaModel(RespostasQuestionarioIF quizTemplateRespostaModel) {
		this.quizTemplateRespostaModel = (QuizQuestionarioRespostaModel) quizTemplateRespostaModel;
	}
}
