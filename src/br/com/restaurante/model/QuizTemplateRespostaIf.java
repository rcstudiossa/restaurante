package br.com.restaurante.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public interface QuizTemplateRespostaIf<T extends QuizTemplateIf<R>, R extends QuizTemplateRespostaIf<T, R, A>, A extends QuizTemplateArquetipoRespostaAb<T, R, A>> extends Serializable, RespostasQuestionarioIF {

	public abstract Long getId();
	
	public abstract T getQuizTemplate();

	public abstract void setQuizTemplate(T quizTemplate);
	
	public abstract QuizTemplateRespostaIf<T, R, A> getInstance();

	public QuizModel getQuizModel();

	public void setQuizModel(QuizModel quizModel);

	public String getResposta();

	public void setResposta(String resposta);

	public String getOutros();

	public void setOutros(String outros);
	
	public String getUnidade();
	
	public void setUnidade(String unidade);
	
	public Long getIdResposta();
	
	public void setIdResposta(Long idResposta);
	
	public List<A> getArquetiposRespostas();
	
	public void setArquetiposRespostas(List<A> arquetiposResposta);
	
	public Date getDataInicial();
	
}
