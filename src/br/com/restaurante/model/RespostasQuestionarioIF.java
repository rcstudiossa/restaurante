package br.com.restaurante.model;

import java.util.Date;
import java.util.List;


public interface RespostasQuestionarioIF {

	public QuizModel getQuizModel();
	
	public void setQuizModel(QuizModel quizModel);
	
	public String getResposta();
	
	public void setResposta(String resposta);
	
	public Long getIdResposta();
	
	public void setIdResposta(Long idResposta);
	
	public void setQuizPerguntaModel(QuizPerguntaModel quizPerguntaModel);
	
	public void setQuestionario(CampoQuestionarioIF questionario);
	
	public void setOutros(String outros);
	
	public String getOutros();
	
	public String getUnidade();
	
	public void setUnidade(String unidade);
	
	public void instanciarArquetipo();
	
	public void addArquetipo(ArquetipoRespostaIF arquetipo);
	
	public Date getDataInicial();
	
	@SuppressWarnings("rawtypes")
	public List getArquetiposRespostas();	
}
