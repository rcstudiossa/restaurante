package br.com.restaurante.model;

import java.util.Date;
import java.util.List;

import br.com.topsys.util.TSUtil;

@SuppressWarnings("serial")
public class QuizModel extends CamposRespostaAb {

	private Long id;
	private Long idExterno;
	private Integer ordem;
	private Boolean flagObrigatorio;
	private Boolean flagAtivo;
	private QuizModel quizDependenciaModel;
	private QuizGrupoModel quizGrupoModel;
	private TipoQuizModel tipoQuizModel;
	private String respostaPadrao;
	private String cssDiv;
	private String cssColunas;
	private String cssAjuste;
	private QuizRespostaModel quizRespostaDependenteModel;
	private Integer rowindexDependente;
	private Boolean flagPossuiDependentes;
	private Boolean flagPossuiPontuacao;
	private Integer qtdColunas;
	private QuizModel quizRespostaPadraoModel;
	private boolean flagSelecionado;
	private boolean flagDesabilitado;
	private String apelido;
	private Boolean flagOutros;
	private Boolean flagTamanhoFixo;
	private Integer maxlength;
	private Integer rows;
	private String queryRespostaPadrao;
	private List<QuizValidadorModel> validadores;
	private Date horarioInicial;
	private Boolean flagCopia;
	private String formula;
	private String descricaoCompleta;

	public QuizModel() {
	}

	public QuizModel(Long id) {
		super();
		this.id = id;
	}

	public QuizModel(TipoQuizModel tipoQuizModel) {
		super();
		this.tipoQuizModel = tipoQuizModel;
	}

	public QuizModel(QuizPerguntaModel quizPerguntaModel) {
		super();
		this.quizPerguntaModel = quizPerguntaModel;
	}

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdExterno() {
		return idExterno;
	}

	public void setIdExterno(Long idExterno) {
		this.idExterno = idExterno;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	public Boolean getFlagObrigatorio() {
		return flagObrigatorio;
	}

	public void setFlagObrigatorio(Boolean flagObrigatorio) {
		this.flagObrigatorio = flagObrigatorio;
	}

	public QuizModel getQuizDependenciaModel() {
		return quizDependenciaModel;
	}

	public void setQuizDependenciaModel(QuizModel quizDependenciaModel) {
		this.quizDependenciaModel = quizDependenciaModel;
	}

	public QuizGrupoModel getQuizGrupoModel() {
		return quizGrupoModel;
	}

	public void setQuizGrupoModel(QuizGrupoModel quizGrupoModel) {
		this.quizGrupoModel = quizGrupoModel;
	}

	public TipoQuizModel getTipoQuizModel() {
		return tipoQuizModel;
	}

	public void setTipoQuizModel(TipoQuizModel tipoQuizModel) {
		this.tipoQuizModel = tipoQuizModel;
	}

	public String getRespostaPadrao() {
		return respostaPadrao;
	}

	public void setRespostaPadrao(String respostaPadrao) {
		this.respostaPadrao = respostaPadrao;
	}

	public String getCssDiv() {
		return cssDiv;
	}

	public void setCssDiv(String cssDiv) {
		this.cssDiv = cssDiv;
	}

	public String getCssColunas() {
		return cssColunas;
	}

	public void setCssColunas(String cssColunas) {
		this.cssColunas = cssColunas;
	}

	public Boolean getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public QuizRespostaModel getQuizRespostaDependenteModel() {
		return quizRespostaDependenteModel;
	}

	public void setQuizRespostaDependenteModel(QuizRespostaModel quizRespostaDependenteModel) {
		this.quizRespostaDependenteModel = quizRespostaDependenteModel;
	}

	public Integer getRowindexDependente() {
		return rowindexDependente;
	}

	public void setRowindexDependente(Integer rowindexDependente) {
		this.rowindexDependente = rowindexDependente;
	}

	public Boolean getFlagPossuiDependentes() {
		return flagPossuiDependentes;
	}

	public void setFlagPossuiDependentes(Boolean flagPossuiDependentes) {
		this.flagPossuiDependentes = flagPossuiDependentes;
	}

	public Integer getQtdColunas() {
		return qtdColunas;
	}

	public void setQtdColunas(Integer qtdColunas) {
		this.qtdColunas = qtdColunas;
	}

	public QuizModel getQuizRespostaPadraoModel() {
		return quizRespostaPadraoModel;
	}

	public void setQuizRespostaPadraoModel(QuizModel quizRespostaPadraoModel) {
		this.quizRespostaPadraoModel = quizRespostaPadraoModel;
	}

	public Boolean getFlagPossuiPontuacao() {
		return flagPossuiPontuacao;
	}

	public void setFlagPossuiPontuacao(Boolean flagPossuiPontuacao) {
		this.flagPossuiPontuacao = flagPossuiPontuacao;
	}

	public boolean isFlagSelecionado() {
		return flagSelecionado;
	}

	public void setFlagSelecionado(boolean flagSelecionado) {
		this.flagSelecionado = flagSelecionado;
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public Boolean getFlagOutros() {
		return flagOutros;
	}

	public void setFlagOutros(Boolean flagOutros) {
		this.flagOutros = flagOutros;
	}

	public Integer getMaxlength() {
		return maxlength;
	}

	public void setMaxlength(Integer maxlength) {
		this.maxlength = maxlength;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getQueryRespostaPadrao() {
		return queryRespostaPadrao;
	}

	public void setQueryRespostaPadrao(String queryRespostaPadrao) {
		this.queryRespostaPadrao = queryRespostaPadrao;
	}

	public List<QuizValidadorModel> getValidadores() {
		return validadores;
	}

	public void setValidadores(List<QuizValidadorModel> validadores) {
		this.validadores = validadores;
	}

	public Date getHorarioInicial() {
		return horarioInicial;
	}

	public void setHorarioInicial(Date horarioInicial) {
		this.horarioInicial = horarioInicial;
	}

	public boolean isFlagDesabilitado() {
		return flagDesabilitado;
	}

	public void setFlagDesabilitado(boolean flagDesabilitado) {
		this.flagDesabilitado = flagDesabilitado;
	}

	public Boolean getFlagTamanhoFixo() {
		return flagTamanhoFixo;
	}

	public void setFlagTamanhoFixo(Boolean flagTamanhoFixo) {
		this.flagTamanhoFixo = flagTamanhoFixo;
	}

	public String getCssAjuste() {
		return cssAjuste;
	}

	public void setCssAjuste(String cssAjuste) {
		this.cssAjuste = cssAjuste;
	}

	public Boolean getFlagCopia() {
		return flagCopia;
	}

	public void setFlagCopia(Boolean flagCopia) {
		this.flagCopia = flagCopia;
	}

	public String getDescricaoCompleta() {
		return descricaoCompleta;
	}

	public void setDescricaoCompleta(String descricaoCompleta) {
		this.descricaoCompleta = descricaoCompleta;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public Double getPontuacao() {

		double pontuacao = 0.0;

		if (!TSUtil.isEmpty(this.quizPerguntaModel.getRespostas())) {

			if (this.flagPossuiPontuacao) {

				for (QuizRespostaModel resposta : this.quizPerguntaModel.getRespostas()) {

					if (resposta.getResposta().equals(this.respostaEscolhida)) {

						pontuacao += resposta.getPontos();

					} else if (!TSUtil.isEmpty(this.respostasEscolhidas) && this.respostasEscolhidas.contains(resposta.getResposta())) {

						pontuacao += resposta.getPontos();

					}

				}

			}

		}

		return pontuacao;

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ordem == null) ? 0 : ordem.hashCode());
		result = prime * result + ((quizGrupoModel == null) ? 0 : quizGrupoModel.hashCode());
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
		QuizModel other = (QuizModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (ordem == null) {
			if (other.ordem != null)
				return false;
		} else if (!ordem.equals(other.ordem))
			return false;
		if (quizGrupoModel == null) {
			if (other.quizGrupoModel != null)
				return false;
		} else if (!quizGrupoModel.equals(other.quizGrupoModel))
			return false;
		if (quizPerguntaModel == null) {
			if (other.quizPerguntaModel != null)
				return false;
		} else if (!quizPerguntaModel.equals(other.quizPerguntaModel))
			return false;
		return true;
	}

}
