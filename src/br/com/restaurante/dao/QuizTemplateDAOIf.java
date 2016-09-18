package br.com.restaurante.dao;


import java.util.List;

import br.com.restaurante.model.QuizTemplateArquetipoRespostaAb;
import br.com.restaurante.model.QuizTemplateIf;
import br.com.restaurante.model.QuizTemplateRespostaIf;
import br.com.topsys.exception.TSApplicationException;

public interface QuizTemplateDAOIf <T extends QuizTemplateIf<R>, R extends QuizTemplateRespostaIf<T, R, A>, A extends QuizTemplateArquetipoRespostaAb<T, R, A>> {

	public T obter(final T model);
	
	public T obterExistente(final T model);
	
	public T inserir(final T model) throws TSApplicationException;
	
	public T alterar(final T model) throws TSApplicationException;
	
	public T reabrir(final T model) throws TSApplicationException;
	
	public List<T> pesquisarQuiz(final T model);
	
	public List<T> pesquisarQuiz(final T model, int limit);
	
	public List<R> pesquisarRespostas(T model);
	
	public List<A> pesquisarRespostasArquetipos(R model);
	
	public R inserirResposta(R model) throws TSApplicationException;
	
	public A inserirRespostaArquetipo(A model) throws TSApplicationException;
	
	public void excluirRespostas(T model) throws TSApplicationException;
	
	public void excluirRespostasArquetipos(R model) throws TSApplicationException;
	
}
