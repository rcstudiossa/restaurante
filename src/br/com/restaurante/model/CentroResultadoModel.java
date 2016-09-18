package br.com.restaurante.model;

import java.util.List;

@SuppressWarnings("serial")
public class CentroResultadoModel extends BaseModel implements CrudModel<CentroResultadoModel> {

	private String descricao;
	
	private String codigo;
	
	private List<CentroResultadoModel> historico;

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

	public List<CentroResultadoModel> getHistorico() {
		return historico;
	}

	public void setHistorico(List<CentroResultadoModel> historico) {
		this.historico = historico;
	}
}
