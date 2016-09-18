package br.com.restaurante.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TipoSolicitacaoDocumentoFuncaoModel implements Serializable {

	private Long id;

	private TipoSolicitacaoDocumentoModel tipoSolicitacaoDocumentoModel;

	private FuncaoModel funcaoModel;
	
	public TipoSolicitacaoDocumentoFuncaoModel() {
		
	}

	public TipoSolicitacaoDocumentoFuncaoModel(TipoSolicitacaoDocumentoModel tipoSolicitacaoDocumentoModel, FuncaoModel funcaoModel) {
		this.tipoSolicitacaoDocumentoModel = tipoSolicitacaoDocumentoModel;
		this.funcaoModel = funcaoModel;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoSolicitacaoDocumentoModel getTipoSolicitacaoDocumentoModel() {
		return tipoSolicitacaoDocumentoModel;
	}

	public void setTipoSolicitacaoDocumentoModel(TipoSolicitacaoDocumentoModel tipoSolicitacaoDocumentoModel) {
		this.tipoSolicitacaoDocumentoModel = tipoSolicitacaoDocumentoModel;
	}

	public FuncaoModel getFuncaoModel() {
		return funcaoModel;
	}

	public void setFuncaoModel(FuncaoModel funcaoModel) {
		this.funcaoModel = funcaoModel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((funcaoModel == null) ? 0 : funcaoModel.hashCode());
		result = prime * result + ((tipoSolicitacaoDocumentoModel == null) ? 0 : tipoSolicitacaoDocumentoModel.hashCode());
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
		TipoSolicitacaoDocumentoFuncaoModel other = (TipoSolicitacaoDocumentoFuncaoModel) obj;
		if (funcaoModel == null) {
			if (other.funcaoModel != null)
				return false;
		} else if (!funcaoModel.equals(other.funcaoModel))
			return false;
		if (tipoSolicitacaoDocumentoModel == null) {
			if (other.tipoSolicitacaoDocumentoModel != null)
				return false;
		} else if (!tipoSolicitacaoDocumentoModel.equals(other.tipoSolicitacaoDocumentoModel))
			return false;
		return true;
	}

}
