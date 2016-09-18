package br.com.restaurante.model;

import java.io.Serializable;

import br.com.topsys.util.TSUtil;

@SuppressWarnings("serial")
public class PermissaoFuncaoOrigemModel implements Serializable {

	private Long id;
	private PermissaoModel permissaoModel;
	private FuncaoOrigemModel funcaoOrigemModel;

	public FuncaoOrigemModel getFuncaoOrigemModel() {
		return funcaoOrigemModel;
	}

	public void setFuncaoOrigemModel(FuncaoOrigemModel funcaoOrigemModel) {
		this.funcaoOrigemModel = funcaoOrigemModel;
	}

	public PermissaoModel getPermissaoModel() {
		return permissaoModel;
	}

	public void setPermissaoModel(PermissaoModel permissaoModel) {
		this.permissaoModel = permissaoModel;
	}

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((funcaoOrigemModel == null) ? 0 : funcaoOrigemModel.hashCode());
		result = prime * result + ((permissaoModel == null) ? 0 : permissaoModel.hashCode());
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
		PermissaoFuncaoOrigemModel other = (PermissaoFuncaoOrigemModel) obj;
		if (funcaoOrigemModel == null) {
			if (other.funcaoOrigemModel != null)
				return false;
		} else if (!funcaoOrigemModel.equals(other.funcaoOrigemModel))
			return false;
		if (permissaoModel == null) {
			if (other.permissaoModel != null)
				return false;
		} else if (!permissaoModel.equals(other.permissaoModel))
			return false;
		return true;
	}

}
