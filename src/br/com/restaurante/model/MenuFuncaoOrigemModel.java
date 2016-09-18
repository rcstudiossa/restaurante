package br.com.restaurante.model;

import java.io.Serializable;

import br.com.topsys.util.TSUtil;

@SuppressWarnings("serial")
public class MenuFuncaoOrigemModel implements Serializable {

	private Long id;
	private MenuModel menuModel;
	private FuncaoOrigemModel funcaoOrigemModel;
	private Boolean flagInserir;
	private Boolean flagAlterar;
	private Boolean flagExcluir;
	private Boolean flagImprimir;
	private Boolean flagAtalho;

	public MenuFuncaoOrigemModel(MenuModel menuModel, FuncaoOrigemModel funcaoOrigemModel) {
		this.menuModel = menuModel;
		this.funcaoOrigemModel = funcaoOrigemModel;
	}

	public MenuFuncaoOrigemModel(MenuModel menuModel) {
		this.menuModel = menuModel;
	}

	public MenuFuncaoOrigemModel() {

	}

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MenuModel getMenuModel() {
		return menuModel;
	}

	public void setMenuModel(MenuModel menuModel) {
		this.menuModel = menuModel;
	}

	public FuncaoOrigemModel getFuncaoOrigemModel() {
		return funcaoOrigemModel;
	}

	public void setFuncaoOrigemModel(FuncaoOrigemModel funcaoOrigemModel) {
		this.funcaoOrigemModel = funcaoOrigemModel;
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

	public Boolean getFlagInserir() {
		return flagInserir;
	}

	public void setFlagInserir(Boolean flagInserir) {
		this.flagInserir = flagInserir;
	}

	public Boolean getFlagImprimir() {
		return flagImprimir;
	}

	public void setFlagImprimir(Boolean flagImprimir) {
		this.flagImprimir = flagImprimir;
	}

	public Boolean getFlagAtalho() {
		return flagAtalho;
	}

	public void setFlagAtalho(Boolean flagAtalho) {
		this.flagAtalho = flagAtalho;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((funcaoOrigemModel == null) ? 0 : funcaoOrigemModel.hashCode());
		result = prime * result + ((menuModel == null) ? 0 : menuModel.hashCode());
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
		MenuFuncaoOrigemModel other = (MenuFuncaoOrigemModel) obj;
		if (funcaoOrigemModel == null) {
			if (other.funcaoOrigemModel != null)
				return false;
		} else if (!funcaoOrigemModel.equals(other.funcaoOrigemModel))
			return false;
		if (menuModel == null) {
			if (other.menuModel != null)
				return false;
		} else if (!menuModel.equals(other.menuModel))
			return false;
		return true;
	}

}
