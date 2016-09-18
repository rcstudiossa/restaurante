package br.com.restaurante.model;

import br.com.topsys.util.TSUtil;

@SuppressWarnings("serial")
public class QuizPerguntaArquetipoModel extends CamposRespostaAb {

	private Long id;

	private QuizPerguntaModel arquetipoModel;

	private Integer ordem;

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public QuizPerguntaModel getArquetipoModel() {
		return arquetipoModel;
	}

	public void setArquetipoModel(QuizPerguntaModel arquetipoModel) {
		this.arquetipoModel = arquetipoModel;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}
	
	public QuizPerguntaModel getQuizPerguntaRespondida() {
		return this.arquetipoModel;
	}
	
	@Override
	public String getApelido() {
		return this.quizPerguntaModel.getApelido();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arquetipoModel == null) ? 0 : arquetipoModel.hashCode());
		result = prime * result + ((quizPerguntaModel == null) ? 0 : quizPerguntaModel.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuizPerguntaArquetipoModel other = (QuizPerguntaArquetipoModel) obj;
		if (arquetipoModel == null) {
			if (other.arquetipoModel != null)
				return false;
		} else if (!arquetipoModel.equals(other.arquetipoModel))
			return false;
		if (quizPerguntaModel == null) {
			if (other.quizPerguntaModel != null)
				return false;
		} else if (!quizPerguntaModel.equals(other.quizPerguntaModel))
			return false;
		return true;
	}

}
