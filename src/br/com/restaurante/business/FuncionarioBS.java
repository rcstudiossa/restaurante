package br.com.restaurante.business;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.restaurante.dao.AtendimentoDAO;
import br.com.restaurante.dao.CrudDAO;
import br.com.restaurante.dao.FuncionarioDAO;
import br.com.restaurante.dao.QuizQuestionarioDAO;
import br.com.restaurante.dao.SolicitacaoExameDAO;
import br.com.restaurante.model.AtendimentoModel;
import br.com.restaurante.model.FuncionarioModel;
import br.com.restaurante.model.QuizQuestionarioModel;
import br.com.restaurante.model.QuizQuestionarioRespostaModel;
import br.com.restaurante.model.SolicitacaoExameItemModel;
import br.com.restaurante.model.SolicitacaoExameItemResultadoModel;
import br.com.restaurante.model.SolicitacaoExameModel;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

@Stateless
@LocalBean
public class FuncionarioBS extends CrudBS<FuncionarioModel> {

	@Inject
	private FuncionarioDAO funcionarioDAO;

	@Inject
	private AtendimentoDAO atendimentoDAO;

	@Inject
	private QuizQuestionarioDAO quizQuestionarioDAO;

	@Inject
	private SolicitacaoExameDAO solicitacaoExameDAO;

	public void inserir(List<FuncionarioModel> funcionariosImportados) throws TSApplicationException {

		for (FuncionarioModel funcionarioModel : funcionariosImportados) {

			this.funcionarioDAO.inserirCrudModel(funcionarioModel);

			if (!TSUtil.isEmpty(funcionarioModel.getAtendimentos())) {

				for (AtendimentoModel atendimentoModel : funcionarioModel.getAtendimentos()) {

					this.atendimentoDAO.inserirCrudModel(atendimentoModel);

					for (QuizQuestionarioModel quizQuestionarioModel : atendimentoModel.getQuestionarios()) {

						this.quizQuestionarioDAO.inserir(quizQuestionarioModel);

						for (QuizQuestionarioRespostaModel resposta : quizQuestionarioModel.getRespostas()) {

							this.quizQuestionarioDAO.inserirResposta(resposta);

						}

					}

					for (SolicitacaoExameModel solicitacaoExameModel : atendimentoModel.getSolicitacoesExame()) {

						this.solicitacaoExameDAO.inserirCrudModel(solicitacaoExameModel);

						for (SolicitacaoExameItemModel item : solicitacaoExameModel.getExames()) {

							this.solicitacaoExameDAO.inserir(item);

							for (SolicitacaoExameItemResultadoModel resultado : item.getResultados()) {

								this.solicitacaoExameDAO.inserir(resultado);

							}

						}

					}

				}

			}

		}

	}

	@Override
	protected CrudDAO<FuncionarioModel> getCrudDAO() {
		return this.funcionarioDAO;
	}

}
