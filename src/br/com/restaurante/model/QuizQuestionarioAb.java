package br.com.restaurante.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import br.com.topsys.util.TSDateUtil;
import br.com.topsys.util.TSParseUtil;
import br.com.topsys.util.TSUtil;

@SuppressWarnings("serial")
public abstract class QuizQuestionarioAb<R extends Serializable> implements QuizTemplateIf<R> {

	protected Long id;

	protected Date data;

	protected AtendimentoModel atendimentoModel;

	protected List<R> respostas;

	protected UsuarioModel usuarioCadastroModel;

	protected Date dataCadastro;

	protected Boolean flagConcluido;

	protected TipoQuizModel tipoQuizModel;

	protected List<QuizGrupoModel> quizGrupos;

	protected List<String> querys;

	protected FuncaoModel funcaoModel;

	protected Date dataAtualizacao;

	protected UsuarioModel usuarioAtualizacaoModel;

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public List<R> getRespostas() {
		return respostas;
	}

	public void setRespostas(List<R> respostas) {
		this.respostas = respostas;
	}

	public AtendimentoModel getAtendimentoModel() {
		return atendimentoModel;
	}

	public void setAtendimentoModel(AtendimentoModel atendimentoModel) {
		this.atendimentoModel = atendimentoModel;
	}

	public UsuarioModel getUsuarioCadastroModel() {
		return usuarioCadastroModel;
	}

	public void setUsuarioCadastroModel(UsuarioModel usuarioCadastroModel) {
		this.usuarioCadastroModel = usuarioCadastroModel;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Boolean getFlagConcluido() {
		return flagConcluido;
	}

	public void setFlagConcluido(Boolean flagConcluido) {
		this.flagConcluido = flagConcluido;
	}

	public TipoQuizModel getTipoQuizModel() {
		return tipoQuizModel;
	}

	public void setTipoQuizModel(TipoQuizModel tipoQuizModel) {
		this.tipoQuizModel = tipoQuizModel;
	}

	public List<QuizGrupoModel> getQuizGrupos() {
		return quizGrupos;
	}

	public void setQuizGrupos(List<QuizGrupoModel> quizGrupos) {
		this.quizGrupos = quizGrupos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuizQuestionarioAb<R> other = (QuizQuestionarioAb<R>) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getDescritivo() {
		return new StringBuilder().append(this.id).append(": ").append(TSParseUtil.dateToString(this.data, TSDateUtil.DD_MM_YYYY)).toString();
	}

	public List<String> getQuerys() {
		return querys;
	}

	public void setQuerys(List<String> querys) {
		this.querys = querys;
	}

	public FuncaoModel getFuncaoModel() {
		return funcaoModel;
	}

	public void setFuncaoModel(FuncaoModel funcaoModel) {
		this.funcaoModel = funcaoModel;
	}

	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public void setUsuarioAtualizacaoModel(UsuarioModel usuarioAtualizacaoModel) {
		this.usuarioAtualizacaoModel = usuarioAtualizacaoModel;
	}

	public UsuarioModel getUsuarioAtualizacaoModel() {
		return usuarioAtualizacaoModel;
	}

	public boolean isExibirPontuacao() {

		if(!TSUtil.isEmpty(this.quizGrupos)){
			
			for (QuizGrupoModel grupo : this.quizGrupos) {
				
				for (QuizModel quiz : grupo.getQuizes()) {
					
					if (!TSUtil.isEmpty(quiz.getFlagPossuiPontuacao()) && quiz.getFlagPossuiPontuacao()) {
						return true;
					}
					
				}
				
			}
			
		}
		
		return false;
	}

	public Double getPontuacaoAcumulada() {

		double pontuacao = 0;

		for (QuizGrupoModel grupo : this.quizGrupos) {

			for (QuizModel quiz : grupo.getQuizes()) {

				pontuacao += quiz.getPontuacao();

			}

		}

		return pontuacao;

	}

}
