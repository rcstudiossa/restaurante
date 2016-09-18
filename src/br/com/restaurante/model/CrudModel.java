package br.com.restaurante.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public interface CrudModel<T extends CrudModel<T>> extends Serializable {

	public Long getId();
	
	public void setId(Long id);
	
	public void setUsuarioCadastroModel(UsuarioModel usuarioCadastroModel);
	
	public void setDataCadastro(Date dataCadastro);
	
	public void setUsuarioAtualizacaoModel(UsuarioModel usuarioAtualizacaoModel);
	
	public void setDataAtualizacao(Date dataAtualizacao);
	
	public void setHistorico(List<T> historico);
	
}
