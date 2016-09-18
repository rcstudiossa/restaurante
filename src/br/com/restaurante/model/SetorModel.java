package br.com.restaurante.model;

import java.util.List;

@SuppressWarnings("serial")
public class SetorModel extends BaseModel implements CrudModel<SetorModel>{

	private String descricao;
	private String codigo;
	private SetorModel setorModel;
	private List<SetorOrigemModel> origens;
	private CentroResultadoModel centroResultadoModel;
	private List<SetorModel> historico;
	
	public SetorModel() {
		super();
	}

	public SetorModel(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public SetorModel getSetorModel() {
		return setorModel;
	}

	public void setSetorModel(SetorModel setorModel) {
		this.setorModel = setorModel;
	}

	public List<SetorOrigemModel> getOrigens() {
		return origens;
	}

	public void setOrigens(List<SetorOrigemModel> origens) {
		this.origens = origens;
	}

	public CentroResultadoModel getCentroResultadoModel() {
		return centroResultadoModel;
	}

	public void setCentroResultadoModel(CentroResultadoModel centroResultadoModel) {
		this.centroResultadoModel = centroResultadoModel;
	}

	public List<SetorModel> getHistorico() {
		return historico;
	}

	public void setHistorico(List<SetorModel> historico) {
		this.historico = historico;
	}

}
