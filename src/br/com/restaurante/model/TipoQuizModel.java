package br.com.restaurante.model;

import java.util.List;

@SuppressWarnings("serial")
public class TipoQuizModel extends BaseModel implements CrudModel<TipoQuizModel> {

	private Long idExterno;
	
	private String descricao;
	
	private TabModel tabModel;
	
	private FuncaoModel funcaoModel;
	
	private List<QuizGrupoModel> grupos;
	
	private List<QuizModel> quiz;
	
	private String relatorio;
	
	private ParametrizacaoTipoQuizModel parametrizacaoTipoQuizModel;
	
	private TipoQuizModel tipoQuizPaiModel;
	
	private List<TipoQuizModel> filhos;
	
	private Integer ordem;
	
	private List<TipoQuizModel> historico;
	
	public TipoQuizModel() {
	}
	
	public TipoQuizModel(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<QuizModel> getQuiz() {
		return quiz;
	}

	public void setQuiz(List<QuizModel> quiz) {
		this.quiz = quiz;
	}

	public TabModel getTabModel() {
		return tabModel;
	}

	public void setTabModel(TabModel tabModel) {
		this.tabModel = tabModel;
	}

	public FuncaoModel getFuncaoModel() {
		return funcaoModel;
	}

	public void setFuncaoModel(FuncaoModel funcaoModel) {
		this.funcaoModel = funcaoModel;
	}

	public String getRelatorio() {
		return relatorio;
	}

	public void setRelatorio(String relatorio) {
		this.relatorio = relatorio;
	}

	public List<QuizGrupoModel> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<QuizGrupoModel> grupos) {
		this.grupos = grupos;
	}
	
	public ParametrizacaoTipoQuizModel getParametrizacaoTipoQuizModel() {
		return parametrizacaoTipoQuizModel;
	}

	public void setParametrizacaoTipoQuizModel(ParametrizacaoTipoQuizModel parametrizacaoTipoQuizModel) {
		this.parametrizacaoTipoQuizModel = parametrizacaoTipoQuizModel;
	}

	public Long getIdExterno() {
		return idExterno;
	}

	public void setIdExterno(Long idExterno) {
		this.idExterno = idExterno;
	}

	public TipoQuizModel getTipoQuizPaiModel() {
		return tipoQuizPaiModel;
	}

	public void setTipoQuizPaiModel(TipoQuizModel tipoQuizPaiModel) {
		this.tipoQuizPaiModel = tipoQuizPaiModel;
	}

	public List<TipoQuizModel> getFilhos() {
		return filhos;
	}

	public void setFilhos(List<TipoQuizModel> filhos) {
		this.filhos = filhos;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	public List<TipoQuizModel> getHistorico() {
		return historico;
	}

	public void setHistorico(List<TipoQuizModel> historico) {
		this.historico = historico;
	}

}
