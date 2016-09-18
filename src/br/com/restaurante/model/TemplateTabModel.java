package br.com.restaurante.model;

import java.io.Serializable;

import br.com.topsys.util.TSUtil;

@SuppressWarnings("serial")
public class TemplateTabModel implements Serializable {

	private Long id;
	
	private TemplateModel templateModel;
	
	private TabModel tabModel;
	
	private Boolean flagInserir;
	
	private Boolean flagAlterar;
	
	private Boolean flagExcluir;
	
	private Boolean flagConcluir;
	
	private Boolean flagImprimir;
	
	private Boolean flagPermissaoMovimentacaoFechada;
	
	private Boolean flagImpressaoAutomatica;
	
	private Boolean flagPularTab;
	
	private Boolean flagCriar;
	
	private Boolean flagCopiar;
	
	private Boolean flagEditarRetroativo;
	
	private Integer ordem;
	
	private Boolean flagShow;
	
	private String validacao;
	
	private boolean flagBloqueado;
	
	private boolean flagUltimaTab;
	
	public TemplateTabModel() {
		super();
	}

	public TemplateTabModel(TemplateModel templateModel, TabModel tabModel) {
		super();
		this.templateModel = templateModel;
		this.tabModel = tabModel;
	}

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public TemplateModel getTemplateModel() {
		return templateModel;
	}

	public void setTemplateModel(TemplateModel templateModel) {
		this.templateModel = templateModel;
	}

	public TabModel getTabModel() {
		return tabModel;
	}

	public void setTabModel(TabModel tabModel) {
		this.tabModel = tabModel;
	}

	public Boolean getFlagInserir() {
		return flagInserir;
	}

	public void setFlagInserir(Boolean flagInserir) {
		this.flagInserir = flagInserir;
	}

	public Boolean getFlagAlterar() {
		return flagAlterar;
	}

	public void setFlagAlterar(Boolean flagAlterar) {
		this.flagAlterar = flagAlterar;
	}

	public Boolean getFlagExcluir() {
		return flagExcluir;
	}

	public void setFlagExcluir(Boolean flagExcluir) {
		this.flagExcluir = flagExcluir;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	public Boolean getFlagShow() {
		return flagShow;
	}

	public void setFlagShow(Boolean flagShow) {
		this.flagShow = flagShow;
	}

	public Boolean getFlagImprimir() {
		return flagImprimir;
	}

	public void setFlagImprimir(Boolean flagImprimir) {
		this.flagImprimir = flagImprimir;
	}

	public String getValidacao() {
		return validacao;
	}

	public void setValidacao(String validacao) {
		this.validacao = validacao;
	}

	public boolean isFlagBloqueado() {
		return flagBloqueado;
	}

	public void setFlagBloqueado(boolean flagBloqueado) {
		this.flagBloqueado = flagBloqueado;
	}

	public boolean isFlagUltimaTab() {
		return flagUltimaTab;
	}

	public void setFlagUltimaTab(boolean flagUltimaTab) {
		this.flagUltimaTab = flagUltimaTab;
	}

	public Boolean getFlagConcluir() {
		return flagConcluir;
	}

	public void setFlagConcluir(Boolean flagConcluir) {
		this.flagConcluir = flagConcluir;
	}

	public Boolean getFlagPermissaoMovimentacaoFechada() {
		return flagPermissaoMovimentacaoFechada;
	}

	public void setFlagPermissaoMovimentacaoFechada(Boolean flagPermissaoMovimentacaoFechada) {
		this.flagPermissaoMovimentacaoFechada = flagPermissaoMovimentacaoFechada;
	}
	
	public Boolean getFlagPularTab() {
		return flagPularTab;
	}
	
	public void setFlagPularTab(Boolean flagPularTab) {
		this.flagPularTab = flagPularTab;
	}
	
	public Boolean getFlagImpressaoAutomatica() {
		return flagImpressaoAutomatica;
	}
	
	public void setFlagImpressaoAutomatica(Boolean flagImpressaoAutomatica) {
		this.flagImpressaoAutomatica = flagImpressaoAutomatica;
	}
	
	public Boolean getFlagCriar() {
		return flagCriar;
	}

	public void setFlagCriar(Boolean flagCriar) {
		this.flagCriar = flagCriar;
	}

	public Boolean getFlagCopiar() {
		return flagCopiar;
	}

	public void setFlagCopiar(Boolean flagCopiar) {
		this.flagCopiar = flagCopiar;
	}

	public Boolean getFlagEditarRetroativo() {
		return flagEditarRetroativo;
	}

	public void setFlagEditarRetroativo(Boolean flagEditarRetroativo) {
		this.flagEditarRetroativo = flagEditarRetroativo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tabModel == null) ? 0 : tabModel.hashCode());
		result = prime * result + ((templateModel == null) ? 0 : templateModel.hashCode());
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
		TemplateTabModel other = (TemplateTabModel) obj;
		if (tabModel == null) {
			if (other.tabModel != null)
				return false;
		} else if (!tabModel.equals(other.tabModel))
			return false;
		if (templateModel == null) {
			if (other.templateModel != null)
				return false;
		} else if (!templateModel.equals(other.templateModel))
			return false;
		return true;
	}

}
