package br.com.restaurante.model;

import java.util.List;

@SuppressWarnings("serial")
public class MesaModel extends BaseModel implements CrudModel<MesaModel> {

	private String codigo;
	
	private String descricao;
	
	private List<MesaModel> historico;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<MesaModel> getHistorico() {
		return historico;
	}

	public void setHistorico(List<MesaModel> historico) {
		this.historico = historico;
	}

}
