package br.com.restaurante.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.restaurante.dao.CrudDAO;
import br.com.restaurante.dao.ProcedimentoDAO;
import br.com.restaurante.model.ProcedimentoModel;
import br.com.restaurante.model.QuizPerguntaModel;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

@Stateless
@LocalBean
public class ProcedimentoBS extends CrudBS<ProcedimentoModel> {

	@EJB
	private QuizPerguntaBS quizPerguntaBS;

	@Inject
	private ProcedimentoDAO procedimentoDAO;

	@Override
	public ProcedimentoModel obterCrudModel(ProcedimentoModel crudModel) {

		ProcedimentoModel model = super.obterCrudModel(crudModel);

		if (!TSUtil.isEmpty(model)) {

			model.setPerguntas(this.quizPerguntaBS.pesquisar(model));

		}

		return model;
	}
	
	public ProcedimentoModel obterSimples(ProcedimentoModel crudModel) {
		return super.obterCrudModel(crudModel);
	}

	@Override
	public ProcedimentoModel inserirCrudModel(final ProcedimentoModel model) throws TSApplicationException {

		this.procedimentoDAO.inserirCrudModel(model);

		if (!TSUtil.isEmpty(model.getPerguntas())) {

			for (QuizPerguntaModel pergunta : model.getPerguntas()) {

				pergunta.setDataCadastro(model.getDataCadastro());
				pergunta.setUsuarioCadastroModel(model.getUsuarioCadastroModel());

				this.quizPerguntaBS.inserirCrudModel(pergunta);

			}

		}

		return model;
	}

	@Override
	public ProcedimentoModel alterarCrudModel(final ProcedimentoModel model) throws TSApplicationException {

		this.procedimentoDAO.alterarCrudModel(model);

		if (!TSUtil.isEmpty(model.getPerguntas())) {

			for (QuizPerguntaModel pergunta : model.getPerguntas()) {

				pergunta.setDataCadastro(model.getDataAtualizacao());
				pergunta.setUsuarioCadastroModel(model.getUsuarioAtualizacaoModel());
				pergunta.setDataAtualizacao(model.getDataAtualizacao());
				pergunta.setUsuarioAtualizacaoModel(model.getUsuarioAtualizacaoModel());

				if (TSUtil.isEmpty(pergunta.getId())) {

					this.quizPerguntaBS.inserirCrudModel(pergunta);

				} else {

					this.quizPerguntaBS.alterarCrudModel(pergunta);

				}

			}

		}

		return model;
	}

	public List<ProcedimentoModel> pesquisarAutoComplete(String texto) {
		return this.procedimentoDAO.pesquisarAutoComplete(texto);
	}

	@Override
	protected CrudDAO<ProcedimentoModel> getCrudDAO() {
		return this.procedimentoDAO;
	}

}
