package br.com.restaurante.business;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.restaurante.dao.FuncionarioDocumentoDAO;
import br.com.restaurante.model.FuncionarioDocumentoModel;
import br.com.restaurante.model.FuncionarioModel;
import br.com.topsys.exception.TSApplicationException;

@Stateless
@LocalBean
public class FuncionarioDocumentoBS {

	@Inject
	private FuncionarioDocumentoDAO funcionarioDocumentoDAO;

	public Boolean existeDocumento(final FuncionarioModel model) {
		return this.funcionarioDocumentoDAO.existeDocumento(model);
	}

	public List<FuncionarioDocumentoModel> pesquisar(final FuncionarioModel model) {
		return this.funcionarioDocumentoDAO.pesquisar(model);
	}

	public void inserir(final FuncionarioDocumentoModel model) throws TSApplicationException {
		this.funcionarioDocumentoDAO.inserir(model);
	}

	public void alterar(final FuncionarioDocumentoModel model) throws TSApplicationException {
		this.funcionarioDocumentoDAO.alterar(model);
	}

	public void excluir(final FuncionarioDocumentoModel model) throws TSApplicationException {
		this.funcionarioDocumentoDAO.excluir(model);
	}

}
