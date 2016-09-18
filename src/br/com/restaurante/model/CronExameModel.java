package br.com.restaurante.model;

import java.io.Serializable;

import br.com.topsys.util.TSUtil;

@SuppressWarnings("serial")
public class CronExameModel implements Serializable {

	private Long id;
	
	private CronModel cronModel;
	
	private ProcedimentoModel procedimentoModel;
	
	private PerfilModel perfilModel;

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CronModel getCronModel() {
		return cronModel;
	}

	public void setCronModel(CronModel cronModel) {
		this.cronModel = cronModel;
	}

	public ProcedimentoModel getProcedimentoModel() {
		return procedimentoModel;
	}

	public void setProcedimentoModel(ProcedimentoModel procedimentoModel) {
		this.procedimentoModel = procedimentoModel;
	}

	public PerfilModel getPerfilModel() {
		return perfilModel;
	}

	public void setPerfilModel(PerfilModel perfilModel) {
		this.perfilModel = perfilModel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cronModel == null) ? 0 : cronModel.hashCode());
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
		CronExameModel other = (CronExameModel) obj;
		if (cronModel == null) {
			if (other.cronModel != null)
				return false;
		} else if (!cronModel.equals(other.cronModel))
			return false;
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
