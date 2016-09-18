package br.com.restaurante.model;

import java.io.Serializable;

import br.com.topsys.util.TSUtil;

@SuppressWarnings("serial")
public class UsuarioSenhaModel implements Serializable {

	private Long id;
	
	private UsuarioModel usuarioModel;
	
	private String senha;

	public UsuarioSenhaModel() {
		super();
	}

	public UsuarioSenhaModel(UsuarioModel usuarioModel, String senha) {
		super();
		this.usuarioModel = usuarioModel;
		this.senha = senha;
	}

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UsuarioModel getUsuarioModel() {
		return usuarioModel;
	}

	public void setUsuarioModel(UsuarioModel usuarioModel) {
		this.usuarioModel = usuarioModel;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
