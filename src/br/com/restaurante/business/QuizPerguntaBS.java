package br.com.restaurante.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.restaurante.dao.CrudDAO;
import br.com.restaurante.dao.QuizPerguntaDAO;
import br.com.restaurante.model.ProcedimentoModel;
import br.com.restaurante.model.QuizModel;
import br.com.restaurante.model.QuizPerguntaArquetipoModel;
import br.com.restaurante.model.QuizPerguntaModel;
import br.com.restaurante.model.QuizRespostaModel;
import br.com.restaurante.model.TipoQuizModel;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

@Stateless
@LocalBean
public class QuizPerguntaBS extends CrudBS<QuizPerguntaModel> {

	@Inject
	private QuizPerguntaDAO quizPerguntaDAO;

	@EJB
	private MedidaBS medidaBS;

	@Override
	protected CrudDAO<QuizPerguntaModel> getCrudDAO() {
		return this.quizPerguntaDAO;
	}

	public QuizPerguntaModel inserirCrudModel(QuizPerguntaModel crudModel) throws TSApplicationException {

		crudModel = this.quizPerguntaDAO.inserirCrudModel(crudModel);

		for (QuizRespostaModel resposta : crudModel.getRespostas()) {

			resposta.setQuizPerguntaModel(crudModel);

			this.quizPerguntaDAO.inserir(resposta);

		}
		
		if(!TSUtil.isEmpty(crudModel.getArquetipos())){
			
			for (QuizPerguntaArquetipoModel arquetipo : crudModel.getArquetipos()) {
				
				arquetipo.setQuizPerguntaModel(crudModel);
				
				this.quizPerguntaDAO.inserir(arquetipo);
				
			}
			
		}

		return crudModel;

	}

	public QuizPerguntaModel alterarCrudModel(QuizPerguntaModel crudModel) throws TSApplicationException {

		this.quizPerguntaDAO.alterarCrudModel(crudModel);

		this.quizPerguntaDAO.excluirRespostas(crudModel);

		for (QuizRespostaModel resposta : crudModel.getRespostas()) {

			resposta.setQuizPerguntaModel(crudModel);

			this.quizPerguntaDAO.inserir(resposta);

		}

		if(!TSUtil.isEmpty(crudModel.getArquetipos())){
			
			for (QuizPerguntaArquetipoModel arquetipo : crudModel.getArquetipos()) {
				
				arquetipo.setQuizPerguntaModel(crudModel);
				
				if(TSUtil.isEmpty(arquetipo.getId())){
					
					this.quizPerguntaDAO.inserir(arquetipo);
					
				} else {
					
					this.quizPerguntaDAO.alterar(arquetipo);
					
				}
				
			}
			
		}
		
		return crudModel;
	}

	public QuizPerguntaModel obterCrudModel(QuizPerguntaModel crudModel) {

		QuizPerguntaModel model = this.quizPerguntaDAO.obterCrudModel(crudModel);

		if (!TSUtil.isEmpty(model)) {

			model.setRespostas(this.pesquisarRespostas(model));
			model.setArquetipos(this.quizPerguntaDAO.pesquisarArquetipo(model));

		}

		return model;
	}

	public QuizPerguntaModel obterSimples(QuizPerguntaModel crudModel) {
		return this.quizPerguntaDAO.obterCrudModel(crudModel);
	}

	public List<QuizRespostaModel> pesquisarRespostas(QuizPerguntaModel model) {
		return this.quizPerguntaDAO.pesquisarRespostas(model);
	}
	
	public List<QuizRespostaModel> pesquisarRespostas(QuizModel model) {
		return this.quizPerguntaDAO.pesquisarRespostas(model);
	}

	public List<QuizPerguntaModel> pesquisarCrudModel(QuizPerguntaModel crudModel) {

		List<QuizPerguntaModel> lista = this.quizPerguntaDAO.pesquisarCrudModel(crudModel);

		for (QuizPerguntaModel quiz : lista) {

			quiz.setRespostas(this.quizPerguntaDAO.pesquisarRespostas(quiz));

		}

		return lista;
	}

	public List<QuizPerguntaModel> pesquisarAutocomplete(String query) {
		return this.quizPerguntaDAO.pesquisarAutocomplete(query);
	}

	public List<QuizPerguntaModel> pesquisarSimples(QuizPerguntaModel crudModel) {
		return this.quizPerguntaDAO.pesquisarCrudModel(crudModel);
	}

	public List<QuizPerguntaModel> pesquisarSimples(TipoQuizModel model) {
		return this.quizPerguntaDAO.pesquisar(model);
	}

	public void excluir(QuizPerguntaArquetipoModel model) throws TSApplicationException {
		this.quizPerguntaDAO.excluir(model);
	}
	
	public void excluir(QuizRespostaModel model) throws TSApplicationException {
		this.quizPerguntaDAO.excluir(model);
	}

	@Override
	public QuizPerguntaModel excluirCrudModel(QuizPerguntaModel crudModel) throws TSApplicationException {
		return this.excluirCrudModel(crudModel);
	}
	
	public List<QuizPerguntaModel> pesquisarExamesLaboratoriaisCalculaveis() {
		return this.quizPerguntaDAO.pesquisarExamesLaboratoriaisCalculaveis();
	}

	public List<QuizPerguntaModel> pesquisarFuncoesVitais() {
		return this.quizPerguntaDAO.pesquisarFuncoesVitais();
	}
	
	public List<QuizPerguntaArquetipoModel> pesquisarArquetipo(QuizPerguntaModel model) {
		return this.quizPerguntaDAO.pesquisarArquetipo(model);
	}
	
	public List<QuizPerguntaModel> pesquisarSubexames() {
		return this.quizPerguntaDAO.pesquisarSubexames();
	}
	
	public List<QuizPerguntaModel> pesquisar(ProcedimentoModel model) {
		
		List<QuizPerguntaModel> perguntas = this.quizPerguntaDAO.pesquisar(model);
		
		for(QuizPerguntaModel pergunta : perguntas){

			pergunta.setRespostas(this.pesquisarRespostas(pergunta));

		}
		
		return perguntas;
		
	}

}
