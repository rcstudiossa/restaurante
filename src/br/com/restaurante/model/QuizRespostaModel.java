package br.com.restaurante.model;

import java.io.Serializable;

import br.com.topsys.util.TSUtil;


@SuppressWarnings("serial")
public class QuizRespostaModel implements Serializable {

	private Long id;
	private String resposta;
	private QuizPerguntaModel quizPerguntaModel;
	private Boolean flagDefault;
	private Double pontos;
	private String formulaConsequencia;
	
	private Boolean flagSelecionado;
	
	public QuizRespostaModel() {
		super();
	}

	public QuizRespostaModel(String resposta) {
		super();
		this.resposta = resposta;
	}

	public Long getId() {
		return TSUtil.tratarLong(id);
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getResposta() {
		return resposta;
	}
	
	public void setResposta(String resposta) {
		this.resposta = resposta;
	}
	
	public QuizPerguntaModel getQuizPerguntaModel() {
		return quizPerguntaModel;
	}

	public void setQuizPerguntaModel(QuizPerguntaModel quizPerguntaModel) {
		this.quizPerguntaModel = quizPerguntaModel;
	}

	public Boolean getFlagDefault() {
		return flagDefault;
	}
	
	public void setFlagDefault(Boolean flagDefault) {
		this.flagDefault = flagDefault;
	}

	public Double getPontos() {
		return pontos;
	}

	public void setPontos(Double pontos) {
		this.pontos = pontos;
	}

	public String getFormulaConsequencia() {
		return formulaConsequencia;
	}

	public void setFormulaConsequencia(String formulaConsequencia) {
		this.formulaConsequencia = formulaConsequencia;
	}

	public Boolean getFlagSelecionado() {
		return flagSelecionado;
	}

	public void setFlagSelecionado(Boolean flagSelecionado) {
		this.flagSelecionado = flagSelecionado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((resposta == null) ? 0 : resposta.hashCode());
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
		QuizRespostaModel other = (QuizRespostaModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (resposta == null) {
			if (other.resposta != null)
				return false;
		} else if (!resposta.equals(other.resposta))
			return false;
		return true;
	}

}
