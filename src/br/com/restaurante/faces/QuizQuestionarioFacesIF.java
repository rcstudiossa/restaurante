package br.com.restaurante.faces;

import br.com.restaurante.model.CamposRespostaAb;
import br.com.restaurante.model.QuizModel;

public interface QuizQuestionarioFacesIF extends QuestionarioFacesIF{

	public String converterUnidade(CamposRespostaAb quiz);
	
	public String processarCampo(QuizModel quiz);
	
	public String addInputText(QuizModel quiz);
	
	public String removeInputText(QuizModel quiz, String inputText);
	
	public String subir(QuizModel quiz, String resposta);
	
	public String descer(QuizModel quiz, String resposta);

}
