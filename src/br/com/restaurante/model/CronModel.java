package br.com.restaurante.model;

import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class CronModel extends BaseModel implements CrudModel<CronModel> {

	private String descricao;
	
	private String query;
	
	private Date dataInicial;
	
	private Date dataFinal;
	
	private String horas;
	
	private String minutos;
	
	private String segundos;
	
	private String diaSemana;
	
	private String diaMes;
	
	private String mes;
	
	private String ano;
	
	private Boolean flagAtivo;
	
	private TipoCronModel tipoCronModel;
	
	private AtividadeModel atividadeModel;
	
	private FrequenciaMarcacaoExameModel frequenciaMarcacaoExameModel;
	
	private Integer sexo;
	
	private Integer idadeMinima;
	
	private Integer idadeMaxima;
	
	private List<CronExameModel> exames;
	
	private List<CronModel> historico;

	public CronModel() {
		super();
	}
	
	public CronModel(Long id) {
		super();
		this.id = id;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public String getHoras() {
		return horas;
	}

	public void setHoras(String horas) {
		this.horas = horas;
	}

	public String getMinutos() {
		return minutos;
	}

	public void setMinutos(String minutos) {
		this.minutos = minutos;
	}

	public String getDiaSemana() {
		return diaSemana;
	}

	public void setDiaSemana(String diaSemana) {
		this.diaSemana = diaSemana;
	}

	public String getDiaMes() {
		return diaMes;
	}

	public void setDiaMes(String diaMes) {
		this.diaMes = diaMes;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public List<CronModel> getHistorico() {
		return historico;
	}

	public void setHistorico(List<CronModel> historico) {
		this.historico = historico;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getSegundos() {
		return segundos;
	}

	public void setSegundos(String segundos) {
		this.segundos = segundos;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public Boolean getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public TipoCronModel getTipoCronModel() {
		return tipoCronModel;
	}

	public void setTipoCronModel(TipoCronModel tipoCronModel) {
		this.tipoCronModel = tipoCronModel;
	}

	public AtividadeModel getAtividadeModel() {
		return atividadeModel;
	}

	public void setAtividadeModel(AtividadeModel atividadeModel) {
		this.atividadeModel = atividadeModel;
	}

	public FrequenciaMarcacaoExameModel getFrequenciaMarcacaoExameModel() {
		return frequenciaMarcacaoExameModel;
	}

	public void setFrequenciaMarcacaoExameModel(FrequenciaMarcacaoExameModel frequenciaMarcacaoExameModel) {
		this.frequenciaMarcacaoExameModel = frequenciaMarcacaoExameModel;
	}

	public Integer getSexo() {
		return sexo;
	}

	public void setSexo(Integer sexo) {
		this.sexo = sexo;
	}

	public Integer getIdadeMinima() {
		return idadeMinima;
	}

	public void setIdadeMinima(Integer idadeMinima) {
		this.idadeMinima = idadeMinima;
	}

	public Integer getIdadeMaxima() {
		return idadeMaxima;
	}

	public void setIdadeMaxima(Integer idadeMaxima) {
		this.idadeMaxima = idadeMaxima;
	}

	public List<CronExameModel> getExames() {
		return exames;
	}

	public void setExames(List<CronExameModel> exames) {
		this.exames = exames;
	}
}
