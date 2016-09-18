package br.com.restaurante.model;

import java.util.List;

public interface CampoQuestionarioIF {

	public Long getId();
	
	public boolean isExibirPontuacao();
	
	public List<String> getQuerys();

	public void setQuerys(List<String> querys);
	
	public List<QuizGrupoModel> getQuizGrupos();
	
	public void setQuizGrupos(List<QuizGrupoModel> quizGrupos);
	
	public RespostasQuestionarioIF getRespostaInstance();
	
	public ArquetipoRespostaIF getArquetipoInstance();
	
	public void resetarRespostas();
	
	public void addResposta(Object resposta);
	
	@SuppressWarnings("rawtypes")
	public List getRespostas();
	
}
