package br.com.restaurante.model;

import java.io.Serializable;

import br.com.topsys.util.TSUtil;

@SuppressWarnings("serial")
public class MedidaUnidadeMedidaModel implements Serializable {

	private Long id;

	private MedidaModel medidaModel;

	private UnidadeMedidaModel unidadeMedidaModel;
	
	private String formulaIda;
	
	private String formulaVolta;
	
	private Boolean flagPadrao;

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MedidaModel getMedidaModel() {
		return medidaModel;
	}

	public void setMedidaModel(MedidaModel medidaModel) {
		this.medidaModel = medidaModel;
	}

	public UnidadeMedidaModel getUnidadeMedidaModel() {
		return unidadeMedidaModel;
	}

	public void setUnidadeMedidaModel(UnidadeMedidaModel unidadeMedidaModel) {
		this.unidadeMedidaModel = unidadeMedidaModel;
	}

	public String getFormulaIda() {
		return formulaIda;
	}

	public void setFormulaIda(String formulaIda) {
		this.formulaIda = formulaIda;
	}

	public String getFormulaVolta() {
		return formulaVolta;
	}

	public void setFormulaVolta(String formulaVolta) {
		this.formulaVolta = formulaVolta;
	}

	public Boolean getFlagPadrao() {
		return flagPadrao;
	}

	public void setFlagPadrao(Boolean flagPadrao) {
		this.flagPadrao = flagPadrao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((medidaModel == null) ? 0 : medidaModel.hashCode());
		result = prime * result + ((unidadeMedidaModel == null) ? 0 : unidadeMedidaModel.hashCode());
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
		MedidaUnidadeMedidaModel other = (MedidaUnidadeMedidaModel) obj;
		if (medidaModel == null) {
			if (other.medidaModel != null)
				return false;
		} else if (!medidaModel.equals(other.medidaModel))
			return false;
		if (unidadeMedidaModel == null) {
			if (other.unidadeMedidaModel != null)
				return false;
		} else if (!unidadeMedidaModel.equals(other.unidadeMedidaModel))
			return false;
		return true;
	}

}
