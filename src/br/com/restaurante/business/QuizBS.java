package br.com.restaurante.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.restaurante.dao.MedidaDAO;
import br.com.restaurante.dao.QuizDAO;
import br.com.restaurante.dao.QuizGrupoDAO;
import br.com.restaurante.dao.QuizPerguntaDAO;
import br.com.restaurante.model.ArquetipoRespostaIF;
import br.com.restaurante.model.AtendimentoModel;
import br.com.restaurante.model.CampoQuestionarioIF;
import br.com.restaurante.model.MedidaUnidadeMedidaModel;
import br.com.restaurante.model.QuizGrupoModel;
import br.com.restaurante.model.QuizModel;
import br.com.restaurante.model.QuizPerguntaArquetipoModel;
import br.com.restaurante.model.QuizRespostaModel;
import br.com.restaurante.model.QuizValidadorModel;
import br.com.restaurante.model.RespostasQuestionarioIF;
import br.com.restaurante.model.TipoQuizModel;
import br.com.restaurante.util.Constantes;
import br.com.restaurante.util.Utilitario;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

@Stateless
@LocalBean
public class QuizBS {

	@Inject
	private QuizDAO quizDAO;

	@Inject
	private QuizGrupoDAO quizGrupoDAO;

	@Inject
	private QuizPerguntaDAO quizPerguntaDAO;

	@Inject
	private MedidaDAO medidaDAO;

	@EJB
	private ValidadorBS validadorBS;

	@EJB
	private QuizPerguntaBS quizPerguntaBS;

	
	public QuizModel obter(QuizModel crudModel) {

		QuizModel model = this.quizDAO.obter(crudModel);

		model.getQuizPerguntaModel().setRespostas(this.quizPerguntaBS.pesquisarRespostas(model.getQuizPerguntaModel()));

		return model;
	}

	
	public QuizModel excluir(QuizModel crudModel) throws TSApplicationException {
		return this.quizDAO.excluir(crudModel);
	}
	
	public QuizModel inserir(QuizModel crudModel) throws TSApplicationException {

		this.quizDAO.inserir(crudModel);

		this.quizDAO.excluirValidadores(crudModel);

		for (QuizValidadorModel validador : crudModel.getValidadores()) {

			this.quizDAO.inserirValidador(validador);

		}

		return crudModel;

	}

	
	public QuizModel alterar(QuizModel crudModel) throws TSApplicationException {

		this.quizDAO.alterar(crudModel);

		this.quizDAO.excluirValidadores(crudModel);

		for (QuizValidadorModel validador : crudModel.getValidadores()) {

			this.quizDAO.inserirValidador(validador);

		}

		return crudModel;
	}

	
	public List<QuizModel> pesquisar(QuizModel crudModel) {

		List<QuizModel> lista = this.quizDAO.pesquisar(crudModel);

		for (QuizModel quiz : lista) {

			quiz.getQuizPerguntaModel().setRespostas(this.quizPerguntaBS.pesquisarRespostas(quiz.getQuizPerguntaModel()));

		}

		return lista;
	}

	public List<QuizModel> pesquisarSimples(TipoQuizModel model) {
		return this.quizDAO.pesquisar(model);
	}

	public List<QuizModel> pesquisarPorGrupo(QuizModel quiz, QuizGrupoModel grupo, AtendimentoModel atendimentoModel) {
		return this.quizDAO.pesquisarPorGrupo(quiz.getTipoQuizModel(), grupo, atendimentoModel);
	}

	public List<QuizValidadorModel> pesquisarValidadores(QuizModel model) {
		return this.quizDAO.pesquisarValidadores(model);
	}

	public QuizValidadorModel excluirValidador(QuizValidadorModel model) throws TSApplicationException {
		return this.quizDAO.excluirValidador(model);
	}

	public QuizValidadorModel alterarValidador(QuizValidadorModel model) throws TSApplicationException {
		return this.quizDAO.alterarValidador(model);
	}

	private void executarQuery(String query, Long atendimentoId) throws TSApplicationException {
		this.quizDAO.executarQuery(query, atendimentoId);
	}

	public List<QuizModel> pesquisarPorGrupo(TipoQuizModel tipoQuiz, QuizGrupoModel grupo, AtendimentoModel atendimentoModel) {

		List<QuizModel> quizes = this.quizDAO.pesquisarPorGrupo(tipoQuiz, grupo, atendimentoModel);

		for (QuizModel quiz : quizes) {

			quiz.setValidadoresEnvolvidos(this.validadorBS.pesquisar(quiz.getQuizPerguntaModel(), quiz));

		}

		return quizes;
	}

	public void carregarCamposQuiz(QuizModel quiz) {

		quiz.setValidadores(this.quizDAO.pesquisarValidadores(quiz));

		if (!TSUtil.isEmpty(quiz.getQuizPerguntaModel().getMedidaModel().getId())) {

			quiz.getQuizPerguntaModel().getMedidaModel().setUnidadesMedidas(this.medidaDAO.pesquisarUnidades(quiz.getQuizPerguntaModel().getMedidaModel()));

			for (MedidaUnidadeMedidaModel unidade : quiz.getQuizPerguntaModel().getMedidaModel().getUnidadesMedidas()) {

				if (unidade.getFlagPadrao()) {

					quiz.setUnidade(unidade.getUnidadeMedidaModel().getDescricao());
					quiz.setUnidadeAnterior(unidade.getUnidadeMedidaModel().getDescricao());
					break;

				}

			}

		}

		if (quiz.getQuizPerguntaModel().isComboSQL()) {

			quiz.getQuizPerguntaModel().setRespostas(this.quizPerguntaDAO.pesquisarRespostasQuery(quiz.getQuizPerguntaModel()));

		} else {

			quiz.getQuizPerguntaModel().setRespostas(this.quizPerguntaDAO.pesquisarRespostas(quiz.getQuizPerguntaModel()));

		}

		quiz.getQuizPerguntaModel().setArquetipos(this.quizPerguntaDAO.pesquisarArquetipo(quiz.getQuizPerguntaModel()));

		for (QuizPerguntaArquetipoModel arquetipo : quiz.getQuizPerguntaModel().getArquetipos()) {
			arquetipo.getArquetipoModel().setRespostas(this.quizPerguntaDAO.pesquisarRespostas(arquetipo.getArquetipoModel()));
		}

	}

	public void iniciarRespostasPadrao(List<QuizGrupoModel> quizGrupos) {

		for (QuizGrupoModel grupo : quizGrupos) {

			for (QuizModel pergunta : grupo.getQuizes()) {

				if (TSUtil.isEmpty(pergunta.getRespostaPadrao())) {

					for (QuizRespostaModel resposta : pergunta.getQuizPerguntaModel().getRespostas()) {

						if (resposta.getFlagDefault()) {

							pergunta.setRespostaPadrao(resposta.getResposta());
							break;

						}
					}

				}

				if (!TSUtil.isEmpty(pergunta.getRespostaPadrao())) {

					if (pergunta.getQuizPerguntaModel().isListaInputText() || pergunta.getQuizPerguntaModel().isListaInputTextArea()) {

						String[] respostas = pergunta.getRespostaPadrao().split(Constantes.CODIGO_SPLIT);

						for (String resposta : respostas) {

							Utilitario.setResposta(pergunta, resposta, null);

						}

					} else {

						Utilitario.setResposta(pergunta, pergunta.getRespostaPadrao(), null);
					}

				}
			}
		}

	}

	public void executarQuerys(CampoQuestionarioIF model, Long atendimentoId) throws TSApplicationException {

		if (!TSUtil.isEmpty(model.getQuerys())) {

			for (String query : model.getQuerys()) {

				this.executarQuery(query, atendimentoId);

			}

			model.getQuerys().clear();

		}

	}

	public void tratarQuizPersist(CampoQuestionarioIF questionario) {

		questionario.resetarRespostas();

		RespostasQuestionarioIF resposta = null;

		if (!TSUtil.isEmpty(questionario.getQuizGrupos())) {

			for (QuizGrupoModel grupo : questionario.getQuizGrupos()) {

				for (QuizModel pergunta : grupo.getQuizes()) {

					if (pergunta.isPossuiResposta()) {

						if ((pergunta.getQuizPerguntaRespondida().isListaInputText() || pergunta.getQuizPerguntaRespondida().isListaInputTextArea()) && !TSUtil.isEmpty(pergunta.getRespostasEscolhidas())) {

							for (String resp : pergunta.getRespostasEscolhidas()) {

								resposta = this.getResposta(questionario, pergunta);

								resposta.setResposta(resp);

								questionario.addResposta(resposta);

							}

						} else {

							questionario.addResposta(this.getResposta(questionario, pergunta));

						}

					}

				}

			}

		}

	}

	private RespostasQuestionarioIF getResposta(CampoQuestionarioIF questionario, QuizModel quiz) {

		RespostasQuestionarioIF resposta = questionario.getRespostaInstance();

		resposta.setQuizPerguntaModel(quiz.getQuizPerguntaModel());
		resposta.setQuizModel(quiz);
		resposta.setQuestionario(questionario);
		resposta.setOutros(quiz.getOutros());
		resposta.setUnidade(quiz.getUnidade());
		resposta.setIdResposta(quiz.getIdResposta());

		resposta.setResposta(quiz.getRespostaDada());
		resposta.instanciarArquetipo();

		if(!TSUtil.isEmpty(quiz.getQuizPerguntaModel().getArquetipos())){
			
			for (QuizPerguntaArquetipoModel arquetipo : quiz.getQuizPerguntaModel().getArquetipos()) {
				
				if (!TSUtil.isEmpty(arquetipo.getRespostaEscolhida()) || !TSUtil.isEmpty(arquetipo.getDataEscolhida()) || !TSUtil.isEmpty(arquetipo.getRespostasEscolhidas()) || !TSUtil.isEmpty(arquetipo.getInteiroEscolhido()) || !TSUtil.isEmpty(arquetipo.getDoubleEscolhido())) {
					
					ArquetipoRespostaIF arquetipoResposta = questionario.getArquetipoInstance();
					
					arquetipoResposta.setQuizPerguntaArquetipoModel(arquetipo);
					arquetipoResposta.setQuizTemplateRespostaModel(resposta);
					arquetipoResposta.setResposta(arquetipo.getRespostaDada());
					
					resposta.addArquetipo(arquetipoResposta);
					
				}
				
			}
			
		}

		return resposta;
	}

	public void carregar(CampoQuestionarioIF questionario, TipoQuizModel tipoQuiz, AtendimentoModel atendimentoModel) {

		questionario.setQuizGrupos(this.quizGrupoDAO.pesquisarComFilhos(tipoQuiz));

		for (QuizGrupoModel grupo : questionario.getQuizGrupos()) {

			grupo.setQuizes(this.pesquisarPorGrupo(tipoQuiz, grupo, atendimentoModel));

			for (QuizModel quiz : grupo.getQuizes()) {

				quiz.setQuizGrupoModel(grupo);

				quiz.setValidadores(this.pesquisarValidadores(quiz));

				if (!TSUtil.isEmpty(quiz.getQuizPerguntaModel().getMedidaModel().getId())) {

					quiz.getQuizPerguntaModel().getMedidaModel().setUnidadesMedidas(this.medidaDAO.pesquisarUnidades(quiz.getQuizPerguntaModel().getMedidaModel()));

					for (MedidaUnidadeMedidaModel unidade : quiz.getQuizPerguntaModel().getMedidaModel().getUnidadesMedidas()) {

						if (unidade.getFlagPadrao()) {

							quiz.setUnidade(unidade.getUnidadeMedidaModel().getDescricao());
							quiz.setUnidadeAnterior(unidade.getUnidadeMedidaModel().getDescricao());
							break;

						}

					}

				}

				if (quiz.getQuizPerguntaModel().isComboSQL()) {

					quiz.getQuizPerguntaModel().setRespostas(this.quizPerguntaDAO.pesquisarRespostasQuery(quiz.getQuizPerguntaModel()));

				} else {

					quiz.getQuizPerguntaModel().setRespostas(this.quizPerguntaDAO.pesquisarRespostas(quiz.getQuizPerguntaModel()));

				}

				quiz.getQuizPerguntaModel().setArquetipos(this.quizPerguntaDAO.pesquisarArquetipo(quiz.getQuizPerguntaModel()));

				for (QuizPerguntaArquetipoModel arquetipo : quiz.getQuizPerguntaModel().getArquetipos()) {
					arquetipo.getArquetipoModel().setRespostas(this.quizPerguntaDAO.pesquisarRespostas(arquetipo.getArquetipoModel()));
				}

			}

		}

		this.tratarQuizDetail(questionario);

	}
	
	private void tratarQuizDetail(CampoQuestionarioIF questionario) {

		RespostasQuestionarioIF resposta = null;
		
		for (Object object : questionario.getRespostas()) {

			resposta = (RespostasQuestionarioIF) object;
			
			for (QuizGrupoModel grupo : questionario.getQuizGrupos()) {

				int index = grupo.getQuizes().indexOf(resposta.getQuizModel());

				if (index >= 0) {

					QuizModel quiz = grupo.getQuizes().get(index);

					quiz.setOutros(resposta.getOutros());
					quiz.setUnidadeAnterior(resposta.getUnidade());
					quiz.setUnidade(resposta.getUnidade());
					quiz.setIdResposta(resposta.getIdResposta());
					quiz.setHorarioInicial(resposta.getDataInicial());

					if (!TSUtil.isEmpty(quiz.getQuizPerguntaModel().getMedidaModel().getId()) && TSUtil.isEmpty(quiz.getUnidade())) {

						for (MedidaUnidadeMedidaModel unidade : quiz.getQuizPerguntaModel().getMedidaModel().getUnidadesMedidas()) {

							if (unidade.getFlagPadrao()) {

								quiz.setUnidade(unidade.getUnidadeMedidaModel().getDescricao());
								quiz.setUnidadeAnterior(unidade.getUnidadeMedidaModel().getDescricao());
								break;

							}

						}

					}

					Utilitario.setResposta(quiz, resposta.getResposta(), resposta.getIdResposta());

					if(!TSUtil.isEmpty(resposta.getArquetiposRespostas())){
						
						ArquetipoRespostaIF arquetipoResposta = null;
						
						for (Object objectArquetipo : resposta.getArquetiposRespostas()) {
							
							arquetipoResposta = (ArquetipoRespostaIF)objectArquetipo;
							
							int indexArquetipo = quiz.getQuizPerguntaModel().getArquetipos().indexOf(arquetipoResposta.getQuizPerguntaArquetipoModel());
							
							if (indexArquetipo >= 0) {
								
								Utilitario.setResposta(quiz.getQuizPerguntaModel().getArquetipos().get(indexArquetipo), arquetipoResposta.getResposta(), null);
								
							}
							
						}
					
					}

				}

			}

		}

	}

}
