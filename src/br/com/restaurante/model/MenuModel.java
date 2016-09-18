package br.com.restaurante.model;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class MenuModel extends BaseModel implements Serializable {

	private String descricao;
	private String url;
	private Integer tabAtiva;
	private MenuModel menuModel;
	private String managedBean;
	private Boolean flagInserir;
	private Boolean flagAlterar;
	private Boolean flagExcluir;
	private Boolean flagImprimir;
	private Integer ordem;
	private List<MenuModel> subMenus;
	private String icone;
	private Boolean flagDependeFuncionario;

	public MenuModel(String url) {
		this.url = url;
	}

	public MenuModel(Long id) {
		this.id = id;
	}

	public MenuModel() {

	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getTabAtiva() {
		return tabAtiva;
	}

	public void setTabAtiva(Integer tabAtiva) {
		this.tabAtiva = tabAtiva;
	}

	public MenuModel getMenuModel() {
		return menuModel;
	}

	public void setMenuModel(MenuModel menuModel) {
		this.menuModel = menuModel;
	}

	public String getManagedBean() {
		return managedBean;
	}

	public void setManagedBean(String managedBean) {
		this.managedBean = managedBean;
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

	public Boolean getFlagImprimir() {
		return flagImprimir;
	}

	public void setFlagImprimir(Boolean flagImprimir) {
		this.flagImprimir = flagImprimir;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	public List<MenuModel> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(List<MenuModel> subMenus) {
		this.subMenus = subMenus;
	}

	public String getIcone() {
		return icone;
	}

	public void setIcone(String icone) {
		this.icone = icone;
	}

	public Boolean getFlagDependeFuncionario() {
		return flagDependeFuncionario;
	}

	public void setFlagDependeFuncionario(Boolean flagDependeFuncionario) {
		this.flagDependeFuncionario = flagDependeFuncionario;
	}

}
