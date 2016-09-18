package br.com.restaurante.model;

import java.util.Date;
import java.util.List;

import br.com.topsys.util.TSDateUtil;
import br.com.topsys.util.TSParseUtil;
import br.com.topsys.util.TSUtil;


@SuppressWarnings("serial")
public class SolicitacaoExameItemResultadoModel extends CamposRespostaAb implements RespostasQuestionarioIF {

	private Long id;
	
	private Date dataCadastro;
	
	private UsuarioModel usuarioCadastroModel;
	
	private Date dataAtualizacao;
	
	private UsuarioModel usuarioAtualizacaoModel;
	
	private SolicitacaoExameItemModel solicitacaoExameItemModel;
	
	private QuizModel quizModel;
	
	private String resultado;
	
	private String resultadoAnterior;
	
	private String observacao;
	
	private String observacaoAnterior;
	
	private Double percentual;
	
	private Double percentualAnterior;
	
	private String ultimoResultado1;
	private String ultimoResultado2;
	private String ultimoResultado3;
	
	private Date dataUltimoResultado1;
	private Date dataUltimoResultado2;
	private Date dataUltimoResultado3;
	
	private Double posicaoPonteiroReferencia;
	
	private SolicitacaoExameItemResultadoModel resultadoPai;
	private List<SolicitacaoExameItemResultadoModel> filhos;
	
	private List<SolicitacaoExameItemResultadoModel> respostas;
	
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

	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public UsuarioModel getUsuarioAtualizacaoModel() {
		return usuarioAtualizacaoModel;
	}

	public void setUsuarioAtualizacaoModel(UsuarioModel usuarioAtualizacaoModel) {
		this.usuarioAtualizacaoModel = usuarioAtualizacaoModel;
	}

	public SolicitacaoExameItemModel getSolicitacaoExameItemModel() {
		return solicitacaoExameItemModel;
	}

	public void setSolicitacaoExameItemModel(SolicitacaoExameItemModel solicitacaoExameItemModel) {
		this.solicitacaoExameItemModel = solicitacaoExameItemModel;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Double getPercentual() {
		return percentual;
	}

	public void setPercentual(Double percentual) {
		this.percentual = percentual;
	}

	public SolicitacaoExameItemResultadoModel getResultadoPai() {
		return resultadoPai;
	}

	public void setResultadoPai(SolicitacaoExameItemResultadoModel resultadoPai) {
		this.resultadoPai = resultadoPai;
	}

	public String getResultadoAnterior() {
		return resultadoAnterior;
	}

	public void setResultadoAnterior(String resultadoAnterior) {
		this.resultadoAnterior = resultadoAnterior;
	}

	public String getObservacaoAnterior() {
		return observacaoAnterior;
	}

	public void setObservacaoAnterior(String observacaoAnterior) {
		this.observacaoAnterior = observacaoAnterior;
	}

	public Double getPercentualAnterior() {
		return percentualAnterior;
	}

	public void setPercentualAnterior(Double percentualAnterior) {
		this.percentualAnterior = percentualAnterior;
	}

	public String getUltimoResultado1() {
		return ultimoResultado1;
	}

	public void setUltimoResultado1(String ultimoResultado1) {
		this.ultimoResultado1 = ultimoResultado1;
	}

	public String getUltimoResultado2() {
		return ultimoResultado2;
	}

	public void setUltimoResultado2(String ultimoResultado2) {
		this.ultimoResultado2 = ultimoResultado2;
	}

	public String getUltimoResultado3() {
		return ultimoResultado3;
	}

	public void setUltimoResultado3(String ultimoResultado3) {
		this.ultimoResultado3 = ultimoResultado3;
	}

	public Date getDataUltimoResultado1() {
		return dataUltimoResultado1;
	}
	
	public String getDataUltimoResultado1Formatada() {
		return TSParseUtil.dateToString(dataUltimoResultado1, TSDateUtil.DD_MM_YYYY_HH_MM);
	}

	public void setDataUltimoResultado1(Date dataUltimoResultado1) {
		this.dataUltimoResultado1 = dataUltimoResultado1;
	}

	public Date getDataUltimoResultado2() {
		return dataUltimoResultado2;
	}
	
	public String getDataUltimoResultado2Formatada() {
		return TSParseUtil.dateToString(dataUltimoResultado2, TSDateUtil.DD_MM_YYYY_HH_MM);
	}

	public void setDataUltimoResultado2(Date dataUltimoResultado2) {
		this.dataUltimoResultado2 = dataUltimoResultado2;
	}

	public Date getDataUltimoResultado3() {
		return dataUltimoResultado3;
	}
	
	public String getDataUltimoResultado3Formatada() {
		return TSParseUtil.dateToString(dataUltimoResultado3, TSDateUtil.DD_MM_YYYY_HH_MM);
	}

	public void setDataUltimoResultado3(Date dataUltimoResultado3) {
		this.dataUltimoResultado3 = dataUltimoResultado3;
	}

	public List<SolicitacaoExameItemResultadoModel> getFilhos() {
		return filhos;
	}

	public void setFilhos(List<SolicitacaoExameItemResultadoModel> filhos) {
		this.filhos = filhos;
	}

	public Double getPosicaoPonteiroReferencia() {
		return posicaoPonteiroReferencia;
	}

	public void setPosicaoPonteiroReferencia(Double posicaoPonteiroReferencia) {
		this.posicaoPonteiroReferencia = posicaoPonteiroReferencia;
	}
	
	public QuizModel getQuizModel() {
		return quizModel;
	}

	public void setQuizModel(QuizModel quizModel) {
		this.quizModel = quizModel;
	}

	public List<SolicitacaoExameItemResultadoModel> getRespostas() {
		return respostas;
	}

	public void setRespostas(List<SolicitacaoExameItemResultadoModel> respostas) {
		this.respostas = respostas;
	}
	
	@Override
	public void instanciarArquetipo() {
		
	}
	
	@Override
	public void setQuestionario(CampoQuestionarioIF questionario) {
		this.solicitacaoExameItemModel = (SolicitacaoExameItemModel)questionario;
	}
	
	@Override
	public void setResposta(String resposta) {
		this.resultado = resposta;
	}

	public String getIdsArrayPrioridade(){
		return TSUtil.isEmpty(this.filhos) ? "['campoResultado', 'campoPercentual']" : "['campoPercentual', 'campoResultado']";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		SolicitacaoExameItemResultadoModel other = (SolicitacaoExameItemResultadoModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public void addArquetipo(ArquetipoRespostaIF arquetipo) {
	}

	@Override
	public String getResposta() {
		return this.resultado;
	}
	
	@Override
	public void setOutros(String outros) {
		this.observacao = outros;
	}
	
	@Override
	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
	
	@Override
	public String getUnidade() {
		return this.unidade;
	}
	
	@Override
	public String getApelido() {
		return this.solicitacaoExameItemModel.getProcedimentoModel().getDescricao();
	}

	@Override
	public Date getDataInicial() {
		return null;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List getArquetiposRespostas() {
		return null;
	}

}
