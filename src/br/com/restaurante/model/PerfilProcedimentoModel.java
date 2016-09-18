package br.com.restaurante.model;

import java.io.Serializable;

import br.com.topsys.util.TSUtil;

@SuppressWarnings("serial")
public class PerfilProcedimentoModel implements Serializable {

	private Long id;
	
	private PerfilModel perfilModel;
	
	private ProcedimentoModel procedimentoModel;

	public PerfilProcedimentoModel(PerfilModel perfilModel, ProcedimentoModel procedimentoModel) {
		super();
		this.perfilModel = perfilModel;
		this.procedimentoModel = procedimentoModel;
	}

	public PerfilProcedimentoModel() {
		super();
	}

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PerfilModel getPerfilModel() {
		return perfilModel;
	}

	public void setPerfilModel(PerfilModel perfilModel) {
		this.perfilModel = perfilModel;
	}

	public ProcedimentoModel getProcedimentoModel() {
		return procedimentoModel;
	}

	public void setProcedimentoModel(ProcedimentoModel procedimentoModel) {
		this.procedimentoModel = procedimentoModel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((perfilModel == null) ? 0 : perfilModel.hashCode());
		result = prime * result + ((procedimentoModel == null) ? 0 : procedimentoModel.hashCode());
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
		PerfilProcedimentoModel other = (PerfilProcedimentoModel) obj;
		if (perfilModel == null) {
			if (other.perfilModel != null)
				return false;
		} else if (!perfilModel.equals(other.perfilModel))
			return false;
		if (procedimentoModel == null) {
			if (other.procedimentoModel != null)
				return false;
		} else if (!procedimentoModel.equals(other.procedimentoModel))
			return false;
		return true;
	}
	
}
