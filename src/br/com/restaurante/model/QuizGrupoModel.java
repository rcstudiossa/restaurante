package br.com.restaurante.model;

import java.util.List;

import br.com.topsys.util.TSUtil;

@SuppressWarnings("serial")
public class QuizGrupoModel extends BaseModel implements CrudModel<QuizGrupoModel> {

	private Long idExterno;
	
	private String descricao;

	private Integer ordem;
	
	private Boolean flagExame;
	
	private QuizPerguntaModel quizPerguntaModel;
	
	private List<QuizModel> quizes;
	
	private List<QuizGrupoModel> historico;
	
	public QuizGrupoModel() {

	}

	public Long getIdExterno() {
		return idExterno;
	}

	public void setIdExterno(Long idExterno) {
		this.idExterno = idExterno;
	}

	public QuizGrupoModel(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	public List<QuizModel> getQuizes() {
		return quizes;
	}

	public void setQuizes(List<QuizModel> quizes) {
		this.quizes = quizes;
	}

	public QuizPerguntaModel getQuizPerguntaModel() {
		return quizPerguntaModel;
	}

	public void setQuizPerguntaModel(QuizPerguntaModel quizPerguntaModel) {
		this.quizPerguntaModel = quizPerguntaModel;
	}

	public List<QuizGrupoModel> getHistorico() {
		return historico;
	}

	public void setHistorico(List<QuizGrupoModel> historico) {
		this.historico = historico;
	}

	public Boolean getFlagExame() {
		return flagExame;
	}

	public void setFlagExame(Boolean flagExame) {
		this.flagExame = flagExame;
	}

	public Double getPontuacaoAcumulada() {

		double pontuacao = 0;

		for (QuizModel quiz : this.quizes) {

			pontuacao += quiz.getPontuacao();

		}

		return pontuacao;
	}

	public boolean isExibirPontuacao() {

		for (QuizModel quiz : this.quizes) {

			if (!TSUtil.isEmpty(quiz.getFlagPossuiPontuacao()) && quiz.getFlagPossuiPontuacao()) {
				return true;
			}

		}

		return false;
	}
	
	public boolean isPermissaoExcluir() {
		
		for (QuizModel quiz : this.quizes) {
			
			if (!TSUtil.isEmpty(quiz.getId())) {
				return false;
			}
			
		}
		
		return true;
	}

}
