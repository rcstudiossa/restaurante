package br.com.restaurante.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public interface QuizTemplateIf<R extends Serializable> extends Serializable, CampoQuestionarioIF {

	public List<R> getRespostas();

	public void setRespostas(List<R> respostas);

	public AtendimentoModel getAtendimentoModel();

	public UsuarioModel getUsuarioCadastroModel();

	public TipoQuizModel getTipoQuizModel();

	public void setTipoQuizModel(TipoQuizModel tipoQuizModel);

	public List<QuizGrupoModel> getQuizGrupos();
	
	public List<String> getQuerys();

	public void setQuizGrupos(List<QuizGrupoModel> quizGrupos);
	
	public void setAtendimentoModel(AtendimentoModel model);
	
	public void setFlagConcluido(Boolean flagConcluido);
	
	public Boolean getFlagConcluido();
	
	public void setUsuarioCadastroModel(UsuarioModel usuarioModel);
	
	public void setFuncaoModel(FuncaoModel funcaoModel);
	
	public void setQuerys(List<String> querys);
	
	public Date getDataCadastro();
	
	public void setDataCadastro(Date dataCadastro);
	
	public void setId(Long id);
	
	public void setData(Date data);
	
}
