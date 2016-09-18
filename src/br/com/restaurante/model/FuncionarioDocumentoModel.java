package br.com.restaurante.model;

import java.io.Serializable;
import java.util.Date;

import br.com.restaurante.util.Constantes;
import br.com.topsys.util.TSUtil;

@SuppressWarnings("serial")
public class FuncionarioDocumentoModel implements Serializable {

	private Long id;
	private FuncionarioModel funcionarioModel;
	private String descricao;
	private String arquivo;
	private UsuarioModel usuarioCadastroModel;
	private Date dataCadastro;
	private Boolean flagAtivo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FuncionarioModel getFuncionarioModel() {
		return funcionarioModel;
	}

	public void setFuncionarioModel(FuncionarioModel funcionarioModel) {
		this.funcionarioModel = funcionarioModel;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getArquivo() {
		return arquivo;
	}

	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}

	public UsuarioModel getUsuarioCadastroModel() {
		return usuarioCadastroModel;
	}

	public void setUsuarioCadastroModel(UsuarioModel usuarioCadastroModel) {
		this.usuarioCadastroModel = usuarioCadastroModel;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Boolean getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public String getArquivoView() {
		return Constantes.PASTA_DOWNLOAD_ARQUIVO + Constantes.PASTA_PRONTUARIOS + this.arquivo;
	}

	public String getDescricaoResumida() {

		if (!TSUtil.isEmpty(this.descricao)) {

			if (this.descricao.length() > 50) {

				return this.descricao.substring(0, 49) + "...";

			}
			
			return this.descricao;

		}

		return "";

	}

}
