package br.com.restaurante.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.topsys.util.TSUtil;

@SuppressWarnings("serial")
public class QuizPerguntaModel extends BaseModel implements CrudModel<QuizPerguntaModel> {

	private Long idExterno;

	private String pergunta;

	private String apelido;

	private String formula;

	private Integer maxlenght;

	private Integer rows;

	private String pattern;

	private TipoRespostaModel tipoRespostaModel;

	private List<QuizRespostaModel> respostas;

	private Boolean flagOutros;

	private Boolean flagAtivo;

	private MedidaModel medidaModel;

	private List<QuizPerguntaArquetipoModel> arquetipos;

	private Boolean flagArquetipo;

	private boolean flagSelecionado;

	private List<ValidadorModel> validadores;

	private List<QuizPerguntaModel> historico;

	private UnidadeMedidaModel unidadeMedidaModel;

	private String referencia;

	private Integer ordem;

	private String codigoSubexame;

	private String codigoFormula;

	private ProcedimentoModel procedimentoModel;

	private Boolean flagPercentual;

	private Boolean flagObrigatorio;

	private QuizGrupoModel grupoSubexameModel;

	private QuizPerguntaModel subexamePaiModel;

	private Boolean flagPossuiFilhos;

	private List<QuizPerguntaModel> filhos;

	private String formulaReferenciaMinima;
	private String formulaReferenciaMaxima;
	private Double referenciaMinima;
	private Double referenciaMaxima;

	private Boolean flagPossuiDependencia;

	private String descricaoRespostas;

	private String consultaComboSQL;

	private boolean flagObservacao;

	public QuizPerguntaModel() {
		super();
	}

	public QuizPerguntaModel(Long id) {
		super();
		this.id = id;
	}

	public QuizPerguntaModel(TipoRespostaModel tipoRespostaModel, Boolean flagAtivo, Boolean flagArquetipo) {
		super();
		this.tipoRespostaModel = tipoRespostaModel;
		this.flagAtivo = flagAtivo;
		this.flagArquetipo = flagArquetipo;
	}

	public Long getIdExterno() {
		return idExterno;
	}

	public void setIdExterno(Long idExterno) {
		this.idExterno = idExterno;
	}

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public UsuarioModel getUsuarioCadastroModel() {
		return usuarioCadastroModel;
	}

	public void setUsuarioCadastroModel(UsuarioModel usuarioCadastroModel) {
		this.usuarioCadastroModel = usuarioCadastroModel;
	}

	public Integer getMaxlenght() {
		return maxlenght;
	}

	public void setMaxlenght(Integer maxlenght) {
		this.maxlenght = maxlenght;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getPergunta() {
		return pergunta;
	}

	public void setPergunta(String pergunta) {
		this.pergunta = pergunta;
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public TipoRespostaModel getTipoRespostaModel() {
		return tipoRespostaModel;
	}

	public void setTipoRespostaModel(TipoRespostaModel tipoRespostaModel) {
		this.tipoRespostaModel = tipoRespostaModel;
	}

	public List<QuizRespostaModel> getRespostas() {
		return respostas;
	}

	public void setRespostas(List<QuizRespostaModel> respostas) {
		this.respostas = respostas;
	}

	public Boolean getFlagOutros() {
		return flagOutros;
	}

	public void setFlagOutros(Boolean flagOutros) {
		this.flagOutros = flagOutros;
	}

	public Boolean getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public boolean isFlagSelecionado() {
		return flagSelecionado;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public void setFlagSelecionado(boolean flagSelecionado) {
		this.flagSelecionado = flagSelecionado;
	}

	public MedidaModel getMedidaModel() {
		return medidaModel;
	}

	public void setMedidaModel(MedidaModel medidaModel) {
		this.medidaModel = medidaModel;
	}

	public List<QuizPerguntaArquetipoModel> getArquetipos() {
		return arquetipos;
	}

	public void setArquetipos(List<QuizPerguntaArquetipoModel> arquetipos) {
		this.arquetipos = arquetipos;
	}

	public Boolean getFlagArquetipo() {
		return flagArquetipo;
	}

	public void setFlagArquetipo(Boolean flagArquetipo) {
		this.flagArquetipo = flagArquetipo;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String getNomePerguntaSimples() {
		return TSUtil.isEmpty(this.apelido) ? this.pergunta : this.apelido;
	}

	public List<ValidadorModel> getValidadores() {
		return validadores;
	}

	public void setValidadores(List<ValidadorModel> validadores) {
		this.validadores = validadores;
	}

	public String getAsteriscoCampoObrigatorio() {
		return "<span style='color: red'>*</span>";
	}

	public Boolean getFlagPossuiDependencia() {
		return flagPossuiDependencia;
	}

	public void setFlagPossuiDependencia(Boolean flagPossuiDependencia) {
		this.flagPossuiDependencia = flagPossuiDependencia;
	}

	public String getDescricaoRespostas() {
		return descricaoRespostas;
	}

	public void setDescricaoRespostas(String descricaoRespostas) {
		this.descricaoRespostas = descricaoRespostas;
	}

	public String getConsultaComboSQL() {
		return consultaComboSQL;
	}

	public void setConsultaComboSQL(String consultaComboSQL) {
		this.consultaComboSQL = consultaComboSQL;
	}

	public UnidadeMedidaModel getUnidadeMedidaModel() {
		return unidadeMedidaModel;
	}

	public void setUnidadeMedidaModel(UnidadeMedidaModel unidadeMedidaModel) {
		this.unidadeMedidaModel = unidadeMedidaModel;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	public String getCodigoSubexame() {
		return codigoSubexame;
	}

	public void setCodigoSubexame(String codigoSubexame) {
		this.codigoSubexame = codigoSubexame;
	}

	public String getCodigoFormula() {
		return codigoFormula;
	}

	public void setCodigoFormula(String codigoFormula) {
		this.codigoFormula = codigoFormula;
	}

	public ProcedimentoModel getProcedimentoModel() {
		return procedimentoModel;
	}

	public void setProcedimentoModel(ProcedimentoModel procedimentoModel) {
		this.procedimentoModel = procedimentoModel;
	}

	public Boolean getFlagPercentual() {
		return flagPercentual;
	}

	public void setFlagPercentual(Boolean flagPercentual) {
		this.flagPercentual = flagPercentual;
	}

	public Boolean getFlagObrigatorio() {
		return flagObrigatorio;
	}

	public void setFlagObrigatorio(Boolean flagObrigatorio) {
		this.flagObrigatorio = flagObrigatorio;
	}

	public QuizGrupoModel getGrupoSubexameModel() {
		return grupoSubexameModel;
	}

	public void setGrupoSubexameModel(QuizGrupoModel grupoSubexameModel) {
		this.grupoSubexameModel = grupoSubexameModel;
	}

	public QuizPerguntaModel getSubexamePaiModel() {
		return subexamePaiModel;
	}

	public void setSubexamePaiModel(QuizPerguntaModel subexamePaiModel) {
		this.subexamePaiModel = subexamePaiModel;
	}

	public Boolean getFlagPossuiFilhos() {
		return flagPossuiFilhos;
	}

	public void setFlagPossuiFilhos(Boolean flagPossuiFilhos) {
		this.flagPossuiFilhos = flagPossuiFilhos;
	}

	public List<QuizPerguntaModel> getFilhos() {
		return filhos;
	}

	public void setFilhos(List<QuizPerguntaModel> filhos) {
		this.filhos = filhos;
	}

	public String getFormulaReferenciaMinima() {
		return formulaReferenciaMinima;
	}

	public void setFormulaReferenciaMinima(String formulaReferenciaMinima) {
		this.formulaReferenciaMinima = formulaReferenciaMinima;
	}

	public String getFormulaReferenciaMaxima() {
		return formulaReferenciaMaxima;
	}

	public void setFormulaReferenciaMaxima(String formulaReferenciaMaxima) {
		this.formulaReferenciaMaxima = formulaReferenciaMaxima;
	}

	public Double getReferenciaMinima() {
		return referenciaMinima;
	}

	public void setReferenciaMinima(Double referenciaMinima) {
		this.referenciaMinima = referenciaMinima;
	}

	public Double getReferenciaMaxima() {
		return referenciaMaxima;
	}

	public void setReferenciaMaxima(Double referenciaMaxima) {
		this.referenciaMaxima = referenciaMaxima;
	}

	public boolean isFlagObservacao() {
		return flagObservacao;
	}

	public void setFlagObservacao(boolean flagObservacao) {
		this.flagObservacao = flagObservacao;
	}

	public List<QuizPerguntaModel> getHistorico() {
		return historico;
	}

	public void setHistorico(List<QuizPerguntaModel> historico) {
		this.historico = historico;
	}

	public boolean isCombo() {
		return this.tipoRespostaModel.isCombo();
	}

	public boolean isRadio() {
		return this.tipoRespostaModel.isRadio();
	}

	public boolean isBooleano() {
		return this.tipoRespostaModel.isBooleano();
	}

	public boolean isMultiplo() {
		return this.tipoRespostaModel.isMultiplo();
	}

	public boolean isNumerico() {
		return this.tipoRespostaModel.isNumerico();
	}

	public boolean isPontoFlutuante() {
		return this.tipoRespostaModel.isPontoFlutuante();
	}

	public boolean isData() {
		return this.tipoRespostaModel.isData();
	}

	public boolean isDataHora() {
		return this.tipoRespostaModel.isDataHora();
	}

	public boolean isTexto() {
		return this.tipoRespostaModel.isTexto();
	}

	public boolean isInputTexto() {
		return this.tipoRespostaModel.isInputTexto();
	}

	public boolean isHora() {
		return this.tipoRespostaModel.isHora();
	}

	public boolean isInput() {
		return this.tipoRespostaModel.isInput();
	}

	public boolean isInputMask() {
		return this.tipoRespostaModel.isInputMask();
	}

	public boolean isConsequencia() {
		return this.tipoRespostaModel.isConsequencia();
	}

	public boolean isInformativa() {
		return this.tipoRespostaModel.isInformativa();
	}

	public boolean isListaInputText() {
		return this.tipoRespostaModel.isListaInputText();
	}

	public boolean isListaInputTextArea() {
		return this.tipoRespostaModel.isListaInputTextArea();
	}

	public boolean isComboSQL() {
		return this.tipoRespostaModel.isComboSQL();
	}

	public boolean isKeyFilter() {
		return this.tipoRespostaModel.isKeyFilter();
	}

	public boolean isJqueryMask() {
		return this.tipoRespostaModel.isJqueryMask();
	}

	public boolean isVazia() {
		return this.tipoRespostaModel.isVazia();
	}
	
	public boolean isMultiploPanel() {
		return this.tipoRespostaModel.isMultiploPanel();
	}
	
	public String getNomePergunta(){
		return (TSUtil.isEmpty(this.apelido) ? this.pergunta : this.apelido) + (TSUtil.isEmpty(this.flagObrigatorio) || !this.flagObrigatorio ? "" : this.getAsteriscoCampoObrigatorio());
	}

	public static QuizPerguntaModel getInstanceCrudModel() {

		QuizPerguntaModel model = new QuizPerguntaModel();
		
		model.setTipoRespostaModel(new TipoRespostaModel());
		model.setMedidaModel(new MedidaModel());
		model.setFlagAtivo(Boolean.TRUE);
		model.setFlagOutros(Boolean.FALSE);
		model.setFlagArquetipo(Boolean.FALSE);
		model.setRespostas(new ArrayList<QuizRespostaModel>());
		model.setArquetipos(new ArrayList<QuizPerguntaArquetipoModel>());
		model.setGrupoSubexameModel(new QuizGrupoModel());
		model.setProcedimentoModel(new ProcedimentoModel());
		model.setUnidadeMedidaModel(new UnidadeMedidaModel());
		model.setSubexamePaiModel(new QuizPerguntaModel());

		return model;
	}

}
