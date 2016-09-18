package br.com.restaurante.model;

import java.io.Serializable;
import java.util.List;

import br.com.topsys.util.TSUtil;

@SuppressWarnings("serial")
public class FuncaoOrigemModel implements Serializable {

	private Long id;
	private FuncaoModel funcaoModel;
	private OrigemModel origemModel;
	private List<MenuFuncaoOrigemModel> menus;
	private List<PermissaoFuncaoOrigemModel> permissoes;
	private List<UsuarioFuncaoModel> usuarios;

	public FuncaoOrigemModel() {
		super();
	}

	public FuncaoOrigemModel(FuncaoModel funcaoModel, OrigemModel origemModel) {
		this.funcaoModel = funcaoModel;
		this.origemModel = origemModel;
	}

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FuncaoModel getFuncaoModel() {
		return funcaoModel;
	}

	public void setFuncaoModel(FuncaoModel funcaoModel) {
		this.funcaoModel = funcaoModel;
	}

	public OrigemModel getOrigemModel() {
		return origemModel;
	}

	public void setOrigemModel(OrigemModel origemModel) {
		this.origemModel = origemModel;
	}

	public String getDescricao() {
		return this.origemModel.getDescricao() + " - " + this.funcaoModel.getDescricao();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((funcaoModel == null) ? 0 : funcaoModel.hashCode());
		result = prime * result + ((origemModel == null) ? 0 : origemModel.hashCode());
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
		FuncaoOrigemModel other = (FuncaoOrigemModel) obj;
		if (funcaoModel == null) {
			if (other.funcaoModel != null)
				return false;
		} else if (!funcaoModel.equals(other.funcaoModel))
			return false;
		if (origemModel == null) {
			if (other.origemModel != null)
				return false;
		} else if (!origemModel.equals(other.origemModel))
			return false;
		return true;
	}

	public List<MenuFuncaoOrigemModel> getMenus() {
		return menus;
	}

	public void setMenus(List<MenuFuncaoOrigemModel> menus) {
		this.menus = menus;
	}

	public List<PermissaoFuncaoOrigemModel> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<PermissaoFuncaoOrigemModel> permissoes) {
		this.permissoes = permissoes;
	}

	public List<UsuarioFuncaoModel> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<UsuarioFuncaoModel> usuarios) {
		this.usuarios = usuarios;
	}

}
