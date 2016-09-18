package br.com.restaurante.model;

import java.io.Serializable;

import br.com.topsys.util.TSUtil;

@SuppressWarnings("serial")
public class CidadeModel implements Serializable {

    private Long id;
    private String sigla;
    private String descricao;
    private EstadoModel estadoModel;
    private String codigoIBGE;
    private String descricaoCompleta;

    public CidadeModel(Long id) {
		super();
		this.id = id;
	}

	public CidadeModel() {
		super();
	}

	public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public EstadoModel getEstadoModel() {
        return estadoModel;
    }

    public void setEstadoModel(EstadoModel estadoModel) {
        this.estadoModel = estadoModel;
    }

    public Long getId() {
        return TSUtil.tratarLong(id);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getCodigoIBGE() {
        return codigoIBGE;
    }

    public void setCodigoIBGE(String codigoIBGE) {
        this.codigoIBGE = codigoIBGE;
    }

	public String getDescricaoCompleta() {
		return descricaoCompleta;
	}

	public void setDescricaoCompleta(String descricaoCompleta) {
		this.descricaoCompleta = descricaoCompleta;
	}
}
