package br.com.restaurante.model;

import java.io.Serializable;

import br.com.topsys.util.TSUtil;

@SuppressWarnings("serial")
public class QuizValidadorModel implements Serializable {

	private Long id;
	
	private QuizModel quizModel;
	
	private ValidadorModel validadorModel;
	
	private String respostaPadrao;

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public QuizModel getQuizModel() {
		return quizModel;
	}

	public void setQuizModel(QuizModel quizModel) {
		this.quizModel = quizModel;
	}

	public ValidadorModel getValidadorModel() {
		return validadorModel;
	}

	public void setValidadorModel(ValidadorModel validadorModel) {
		this.validadorModel = validadorModel;
	}

	public String getRespostaPadrao() {
		return respostaPadrao;
	}

	public void setRespostaPadrao(String respostaPadrao) {
		this.respostaPadrao = respostaPadrao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((quizModel == null) ? 0 : quizModel.hashCode());
		result = prime * result + ((validadorModel == null) ? 0 : validadorModel.hashCode());
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
		QuizValidadorModel other = (QuizValidadorModel) obj;
		if (quizModel == null) {
			if (other.quizModel != null)
				return false;
		} else if (!quizModel.equals(other.quizModel))
			return false;
		if (validadorModel == null) {
			if (other.validadorModel != null)
				return false;
		} else if (!validadorModel.equals(other.validadorModel))
			return false;
		return true;
	}
	
}
