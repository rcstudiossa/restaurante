package br.com.restaurante.model;

import java.util.ArrayList;
import java.util.List;

import br.com.topsys.util.TSDateUtil;
import br.com.topsys.util.TSParseUtil;


@SuppressWarnings("serial")
public class QuizQuestionarioModel extends QuizQuestionarioAb<QuizQuestionarioRespostaModel> {

	private boolean flagEdicaoAtual;
	
	private QuizQuestionarioModel quizQuestionarioPaiModel;
	
	private List<QuizQuestionarioModel> questionariosAninhados;
	
	public QuizQuestionarioModel() {
	}
	
	public QuizQuestionarioModel(Long id) {
		this.id = id;
	}
	
	public QuizQuestionarioModel(AtendimentoModel atendimentoModel, UsuarioModel usuarioModel) {
		this.atendimentoModel = atendimentoModel;
		this.usuarioCadastroModel = usuarioModel;
	}
	
	public QuizQuestionarioModel getQuizQuestionarioPaiModel() {
		return quizQuestionarioPaiModel;
	}

	public void setQuizQuestionarioPaiModel(QuizQuestionarioModel quizQuestionarioPaiModel) {
		this.quizQuestionarioPaiModel = quizQuestionarioPaiModel;
	}

	public List<QuizQuestionarioModel> getQuestionariosAninhados() {
		return questionariosAninhados;
	}

	public void setQuestionariosAninhados(List<QuizQuestionarioModel> questionariosAninhados) {
		this.questionariosAninhados = questionariosAninhados;
	}
	
	public boolean isFlagEdicaoAtual() {
		return flagEdicaoAtual;
	}

	public void setFlagEdicaoAtual(boolean flagEdicaoAtual) {
		this.flagEdicaoAtual = flagEdicaoAtual;
	}
	
	@Override
	public void addResposta(Object resposta) {
		this.respostas.add((QuizQuestionarioRespostaModel)resposta);
	}
	
	@Override
	public void resetarRespostas() {
		this.respostas = new ArrayList<QuizQuestionarioRespostaModel>();
	}
	
	@Override
	public RespostasQuestionarioIF getRespostaInstance() {
		return new QuizQuestionarioRespostaModel();
	}
	
	@Override
	public ArquetipoRespostaIF getArquetipoInstance() {
		return new QuizQuestionarioArquetipoRespostaModel();
	}

	public String getDescritivo(){
		
		StringBuilder texto = new StringBuilder();
		
		texto.append(TSParseUtil.dateToString(this.dataCadastro));
		texto.append(" - ");
		texto.append(this.usuarioCadastroModel.getNome());
		
		return texto.toString();
	}
	
	public String getDataCadastroFormatada(){
		return TSParseUtil.dateToString(this.dataCadastro, TSDateUtil.DD_MM_YYYY_HH_MM);
	}

}
