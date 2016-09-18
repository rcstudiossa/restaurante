package br.com.restaurante.model;


public interface ArquetipoRespostaIF {

	public QuizPerguntaArquetipoModel getQuizPerguntaArquetipoModel();

	public void setQuizPerguntaArquetipoModel(QuizPerguntaArquetipoModel quizPerguntaArquetipoModel);

	public String getResposta();

	public void setResposta(String resposta);

	public abstract void setQuizTemplateRespostaModel(RespostasQuestionarioIF quizTemplateRespostaModel);
	
}
