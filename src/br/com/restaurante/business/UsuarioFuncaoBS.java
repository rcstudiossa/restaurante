package br.com.restaurante.business;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.restaurante.dao.UsuarioFuncaoDAO;
import br.com.restaurante.model.FuncaoModel;
import br.com.restaurante.model.FuncaoOrigemModel;
import br.com.restaurante.model.OrigemModel;
import br.com.restaurante.model.UsuarioFuncaoModel;
import br.com.restaurante.model.UsuarioModel;
import br.com.topsys.exception.TSApplicationException;

@Stateless
@LocalBean
public class UsuarioFuncaoBS {

	@Inject
	private UsuarioFuncaoDAO usuarioFuncaoDAO;

	public UsuarioFuncaoModel inserir(final UsuarioFuncaoModel model) throws TSApplicationException {
		return this.usuarioFuncaoDAO.inserir(model);
	}
	
	public UsuarioFuncaoModel alterar(final UsuarioFuncaoModel model) throws TSApplicationException {
		return this.usuarioFuncaoDAO.alterar(model);
	}
	
	public UsuarioFuncaoModel excluir(final UsuarioFuncaoModel model) throws TSApplicationException {
		return this.usuarioFuncaoDAO.excluir(model);
	}
	
	public List<UsuarioFuncaoModel> pesquisar(final UsuarioModel model) {
		return this.usuarioFuncaoDAO.pesquisar(model);
	}
	
	public UsuarioFuncaoModel obter(final FuncaoModel funcaoModel, final UsuarioModel usuarioModel) {
		return this.usuarioFuncaoDAO.obter(funcaoModel, usuarioModel);
	}
	
	public List<UsuarioFuncaoModel> pesquisar(final OrigemModel origemModel, final UsuarioModel usuarioModel) {
		return this.usuarioFuncaoDAO.pesquisar(origemModel, usuarioModel);
	}

	public List<UsuarioFuncaoModel> pesquisar(OrigemModel origem) {
		return this.usuarioFuncaoDAO.pesquisar(origem);
	}

	public List<UsuarioFuncaoModel> pesquisar(FuncaoOrigemModel model) {
		return this.usuarioFuncaoDAO.pesquisar(model);
	}

	public List<UsuarioFuncaoModel> pesquisarSimilares(UsuarioFuncaoModel model) {
		return this.usuarioFuncaoDAO.pesquisarSimilares(model);
	}
	
	public void alterarSituacao(final UsuarioFuncaoModel model) throws TSApplicationException {
		this.usuarioFuncaoDAO.alterarSituacao(model);
	}

}
