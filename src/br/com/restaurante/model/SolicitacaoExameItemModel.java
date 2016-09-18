package br.com.restaurante.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.restaurante.util.Constantes;
import br.com.topsys.util.TSDateUtil;
import br.com.topsys.util.TSParseUtil;
import br.com.topsys.util.TSUtil;

@SuppressWarnings("serial")
public class SolicitacaoExameItemModel extends BaseModel implements CampoQuestionarioIF, Serializable {

	private SolicitacaoExameModel solicitacaoExameModel;
	
	private ProcedimentoModel procedimentoModel;
	
	private String observacao;
	
	private String laudo;
	
	private String arquivoResultado;
	
	private PerfilModel perfilModel;
	
	private Date dataRealizacao;
	
	private Boolean flagResultadoNormal;
	
	private Boolean flagResultadoAlterado;
	
	private SolicitacaoExameItemResultadoModel agrupadorResultadoUnico;
	
	private List<SolicitacaoExameItemResultadoModel> resultados;
	private List<SolicitacaoExameItemModel> procedimentosFilhos;
	
	private SolicitacaoExameItemModel solicitacaoRaizAux;
	
	private boolean flagSelecionado;
	
	private List<QuizGrupoModel> quizGrupos;
	
	private List<String> querys;

	public SolicitacaoExameModel getSolicitacaoExameModel() {
		return solicitacaoExameModel;
	}

	public void setSolicitacaoExameModel(SolicitacaoExameModel solicitacaoExameModel) {
		this.solicitacaoExameModel = solicitacaoExameModel;
	}

	public ProcedimentoModel getProcedimentoModel() {
		return procedimentoModel;
	}

	public void setProcedimentoModel(ProcedimentoModel procedimentoModel) {
		this.procedimentoModel = procedimentoModel;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public List<SolicitacaoExameItemResultadoModel> getResultados() {
		return resultados;
	}

	public void setResultados(List<SolicitacaoExameItemResultadoModel> resultados) {
		this.resultados = resultados;
	}
	
	public boolean isFlagSelecionado() {
		return flagSelecionado;
	}

	public void setFlagSelecionado(boolean flagSelecionado) {
		this.flagSelecionado = flagSelecionado;
	}

	public List<SolicitacaoExameItemModel> getProcedimentosFilhos() {
		return procedimentosFilhos;
	}

	public void setProcedimentosFilhos(List<SolicitacaoExameItemModel> procedimentosFilhos) {
		this.procedimentosFilhos = procedimentosFilhos;
	}

	public String getLaudo() {
		return laudo;
	}

	public void setLaudo(String laudo) {
		this.laudo = laudo;
	}

	public SolicitacaoExameItemModel getSolicitacaoRaizAux() {
		return solicitacaoRaizAux;
	}

	public void setSolicitacaoRaizAux(SolicitacaoExameItemModel solicitacaoRaizAux) {
		this.solicitacaoRaizAux = solicitacaoRaizAux;
	}

	public List<QuizGrupoModel> getQuizGrupos() {
		return quizGrupos;
	}

	public void setQuizGrupos(List<QuizGrupoModel> quizGrupos) {
		this.quizGrupos = quizGrupos;
	}
	
	@Override
	public void resetarRespostas() {
		this.resultados = new ArrayList<SolicitacaoExameItemResultadoModel>();
	}
	
	@Override
	public void addResposta(Object resposta) {
		this.resultados.add((SolicitacaoExameItemResultadoModel) resposta);
	}
	
	@Override
	public RespostasQuestionarioIF getRespostaInstance() {
		return new SolicitacaoExameItemResultadoModel();
	}
	
	@Override
	public ArquetipoRespostaIF getArquetipoInstance() {
		return null;
	}
	
	public String getInformacoesComplementares(){

		StringBuilder query = new StringBuilder();
		
		query.append("Código: ").append(this.id);
		query.append("<br/>Cadastrado por: ").append(this.usuarioCadastroModel.getNome());
		query.append("<br/>Cadastrado as: ").append(TSParseUtil.dateToString(this.dataCadastro, TSDateUtil.DD_MM_YYYY_HH_MM));
		
		return query.toString();
	}
	
	@Override
	public boolean isExibirPontuacao() {
		return false;
	}

	public List<String> getQuerys() {
		return querys;
	}

	public void setQuerys(List<String> querys) {
		this.querys = querys;
	}

	public String getArquivoResultado() {
		return arquivoResultado;
	}

	public void setArquivoResultado(String arquivoResultado) {
		this.arquivoResultado = arquivoResultado;
	}
	
	public Date getDataRealizacao() {
		return dataRealizacao;
	}

	public void setDataRealizacao(Date dataRealizacao) {
		this.dataRealizacao = dataRealizacao;
	}

	public Boolean getFlagResultadoNormal() {
		return flagResultadoNormal;
	}

	public void setFlagResultadoNormal(Boolean flagResultadoNormal) {
		this.flagResultadoNormal = flagResultadoNormal;
	}

	public Boolean getFlagResultadoAlterado() {
		return flagResultadoAlterado;
	}

	public void setFlagResultadoAlterado(Boolean flagResultadoAlterado) {
		this.flagResultadoAlterado = flagResultadoAlterado;
	}

	public PerfilModel getPerfilModel() {
		return perfilModel;
	}

	public void setPerfilModel(PerfilModel perfilModel) {
		this.perfilModel = perfilModel;
	}

	public SolicitacaoExameItemResultadoModel getAgrupadorResultadoUnico() {
		return agrupadorResultadoUnico;
	}

	public void setAgrupadorResultadoUnico(SolicitacaoExameItemResultadoModel agrupadorResultadoUnico) {
		this.agrupadorResultadoUnico = agrupadorResultadoUnico;
	}

	public String getArquivoResultadoView() {
		return !TSUtil.isEmpty(this.arquivoResultado) ? Constantes.PASTA_DOWNLOAD_ARQUIVO + Constantes.PASTA_RESULTADOS + this.arquivoResultado : null;
	}

	@Override
	public List<SolicitacaoExameItemResultadoModel> getRespostas() {
		return this.resultados;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((procedimentoModel == null) ? 0 : procedimentoModel.hashCode());
		result = prime * result + ((solicitacaoExameModel == null) ? 0 : solicitacaoExameModel.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SolicitacaoExameItemModel other = (SolicitacaoExameItemModel) obj;
		if (procedimentoModel == null) {
			if (other.procedimentoModel != null)
				return false;
		} if (solicitacaoExameModel == null) {
			if (other.solicitacaoExameModel != null)
				return false;
		} else if (!solicitacaoExameModel.equals(other.solicitacaoExameModel))
			return false;
		return true;
	}

}
