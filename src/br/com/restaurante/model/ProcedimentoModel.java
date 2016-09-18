package br.com.restaurante.model;

import java.util.List;

@SuppressWarnings("serial")
public class ProcedimentoModel extends BaseModel implements CrudModel<ProcedimentoModel>{

	private String codigo;
	
	private String descricao;
	
	private Boolean flagGrupo;
	
	private String tipo;
	
	private ProcedimentoModel procedimentoModel;
	
	private Integer ordem;
	
	private Boolean flagSelecionado;
	
	private Boolean flagPossuiSubexames;
	
	private List<QuizPerguntaModel> perguntas;
	
	private List<ProcedimentoModel> historico;
	
	public ProcedimentoModel() {
		super();
	}

	public ProcedimentoModel(Long id) {
		super();
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public ProcedimentoModel getProcedimentoModel() {
		return procedimentoModel;
	}

	public void setProcedimentoModel(ProcedimentoModel procedimentoModel) {
		this.procedimentoModel = procedimentoModel;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	public Boolean getFlagSelecionado() {
		return flagSelecionado;
	}

	public void setFlagSelecionado(Boolean flagSelecionado) {
		this.flagSelecionado = flagSelecionado;
	}

	public List<QuizPerguntaModel> getPerguntas() {
		return perguntas;
	}

	public void setPerguntas(List<QuizPerguntaModel> perguntas) {
		this.perguntas = perguntas;
	}

	public List<ProcedimentoModel> getHistorico() {
		return historico;
	}

	public void setHistorico(List<ProcedimentoModel> historico) {
		this.historico = historico;
	}

	public Boolean getFlagPossuiSubexames() {
		return flagPossuiSubexames;
	}

	public void setFlagPossuiSubexames(Boolean flagPossuiSubexames) {
		this.flagPossuiSubexames = flagPossuiSubexames;
	}

	public Boolean getFlagGrupo() {
		return flagGrupo;
	}

	public void setFlagGrupo(Boolean flagGrupo) {
		this.flagGrupo = flagGrupo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}
