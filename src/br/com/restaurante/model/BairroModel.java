package br.com.restaurante.model;

import java.util.List;

@SuppressWarnings("serial")
public class BairroModel extends BaseModel implements CrudModel<BairroModel> {

	private String descricao;
	
	private CidadeModel cidadeModel;
	
	private List<BairroModel> historico;

	public CidadeModel getCidadeModel() {
		return cidadeModel;
	}

	public void setCidadeModel(CidadeModel cidadeModel) {
		this.cidadeModel = cidadeModel;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<BairroModel> getHistorico() {
		return historico;
	}

	public void setHistorico(List<BairroModel> historico) {
		this.historico = historico;
	}

}
