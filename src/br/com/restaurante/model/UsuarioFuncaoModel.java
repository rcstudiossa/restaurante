package br.com.restaurante.model;

import java.io.Serializable;

import br.com.topsys.util.TSUtil;

@SuppressWarnings("serial")
public class UsuarioFuncaoModel extends BaseModel implements Serializable{

	private UsuarioModel usuarioModel;
	private FuncaoModel funcaoModel;
	private OrigemModel origemModel;
	
	public UsuarioFuncaoModel() {

	}
	
	public UsuarioFuncaoModel(UsuarioModel usuarioModel, FuncaoModel funcaoModel, OrigemModel origemModel) {
		this.usuarioModel = usuarioModel;
		this.funcaoModel = funcaoModel;
		this.origemModel = origemModel;
	}
	
	public FuncaoModel getFuncaoModel() {
		return funcaoModel;
	}

	public void setFuncaoModel(FuncaoModel funcaoModel) {
		this.funcaoModel = funcaoModel;
	}

	public UsuarioModel getUsuarioModel() {
		return usuarioModel;
	}

	public void setUsuarioModel(UsuarioModel usuarioModel) {
		this.usuarioModel = usuarioModel;
	}

	public OrigemModel getOrigemModel() {
		return origemModel;
	}

	public void setOrigemModel(OrigemModel origemModel) {
		this.origemModel = origemModel;
	}

	public String getDescricaoUsuarioFuncao() {
		if (!TSUtil.isEmpty(this.usuarioModel) && !TSUtil.isEmpty(this.funcaoModel)) {
			return this.usuarioModel.getNomeMaiusculo() + " - " + this.funcaoModel.getDescricao();
		}
		return null;
	}
	
	public String getDescricaoCombo(){
		
		StringBuilder descricao = new StringBuilder();
		
		descricao.append(this.usuarioModel.getNome()).append(" (");
		descricao.append(this.funcaoModel.getDescricao()).append(")");
		
		return descricao.toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((funcaoModel == null) ? 0 : funcaoModel.hashCode());
		result = prime * result + ((origemModel == null) ? 0 : origemModel.hashCode());
		result = prime * result + ((usuarioModel == null) ? 0 : usuarioModel.hashCode());
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
		UsuarioFuncaoModel other = (UsuarioFuncaoModel) obj;
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
		if (usuarioModel == null) {
			if (other.usuarioModel != null)
				return false;
		} else if (!usuarioModel.equals(other.usuarioModel))
			return false;
		return true;
	}

}
