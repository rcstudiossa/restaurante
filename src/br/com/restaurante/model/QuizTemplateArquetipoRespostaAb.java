package br.com.restaurante.model;

import java.io.Serializable;

import br.com.topsys.util.TSUtil;

@SuppressWarnings("serial")
public abstract class QuizTemplateArquetipoRespostaAb<T extends QuizTemplateIf<R>, R extends QuizTemplateRespostaIf<T, R, A>, A extends QuizTemplateArquetipoRespostaAb<T, R, A>> implements Serializable, ArquetipoRespostaIF {

	private Long id;

	private QuizPerguntaArquetipoModel quizPerguntaArquetipoModel;

	private String resposta;

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public QuizPerguntaArquetipoModel getQuizPerguntaArquetipoModel() {
		return quizPerguntaArquetipoModel;
	}

	public void setQuizPerguntaArquetipoModel(QuizPerguntaArquetipoModel quizPerguntaArquetipoModel) {
		this.quizPerguntaArquetipoModel = quizPerguntaArquetipoModel;
	}

	public String getResposta() {
		return resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
	}

	public abstract R getQuizTemplateRespostaModel();

	public abstract void setQuizTemplateRespostaModel(R quizTemplateRespostaModel);

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuizTemplateArquetipoRespostaAb<T, R, A> other = (QuizTemplateArquetipoRespostaAb<T, R, A>) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
