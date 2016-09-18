package br.com.restaurante.business;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.restaurante.dao.PermissaoFuncaoOrigemDAO;
import br.com.restaurante.model.FuncaoModel;
import br.com.restaurante.model.OrigemModel;
import br.com.restaurante.model.PermissaoFuncaoOrigemModel;
import br.com.restaurante.model.PermissaoModel;
import br.com.topsys.exception.TSApplicationException;

@Stateless
@LocalBean
public class PermissaoFuncaoOrigemBS {

	@Inject
	private PermissaoFuncaoOrigemDAO permissaoFuncaoOrigemDAO;

	public List<PermissaoFuncaoOrigemModel> pesquisar(final PermissaoModel model) {
		return this.permissaoFuncaoOrigemDAO.pesquisar(model);
	}

	public List<PermissaoFuncaoOrigemModel> pesquisarNaoAssociados(final PermissaoModel model) {
		return this.permissaoFuncaoOrigemDAO.pesquisarNaoAssociados(model);
	}

	public PermissaoFuncaoOrigemModel obter(final Integer permissaoId, FuncaoModel funcaoModel, OrigemModel origem) {
		return this.permissaoFuncaoOrigemDAO.obter(permissaoId, funcaoModel, origem);

	}

	public PermissaoFuncaoOrigemModel alterar(final PermissaoFuncaoOrigemModel model) throws TSApplicationException {

		/*
		this.permissaoFuncaoOrigemDAO.deletarPorPermissao(model);

		for (PermissaoFuncaoOrigemModel permissao : model.getPermissoes()) {

			permissao.setPermissaoModel(model.getPermissaoModel());

			this.permissaoFuncaoOrigemDAO.inserir(permissao);

		}
	*/
		return model;

	}

	public void excluir(final PermissaoFuncaoOrigemModel model) throws TSApplicationException {
		this.permissaoFuncaoOrigemDAO.excluir(model);
	}

}
