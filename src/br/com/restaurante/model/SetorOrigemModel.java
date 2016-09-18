package br.com.restaurante.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SetorOrigemModel extends BaseModel implements Serializable {

	private SetorModel setorModel;
	private OrigemModel origemModel;

	public SetorOrigemModel() {
		super();
	}

	public SetorOrigemModel(SetorModel setorModel, OrigemModel origemModel) {
		this.setorModel = setorModel;
		this.origemModel = origemModel;
	}

	public SetorModel getSetorModel() {
		return setorModel;
	}

	public void setSetorModel(SetorModel setorModel) {
		this.setorModel = setorModel;
	}

	public OrigemModel getOrigemModel() {
		return origemModel;
	}

	public void setOrigemModel(OrigemModel origemModel) {
		this.origemModel = origemModel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((origemModel == null) ? 0 : origemModel.hashCode());
		result = prime * result + ((setorModel == null) ? 0 : setorModel.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		SetorOrigemModel other = (SetorOrigemModel) obj;
		if (origemModel == null) {
			if (other.origemModel != null)
				return false;
		} else if (!origemModel.equals(other.origemModel))
			return false;
		if (setorModel == null) {
			if (other.setorModel != null)
				return false;
		} else if (!setorModel.equals(other.setorModel))
			return false;
		return true;
	}

}
