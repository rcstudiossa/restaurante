package br.com.restaurante.business;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.restaurante.dao.CrudDAO;
import br.com.restaurante.dao.FuncaoDAO;
import br.com.restaurante.dao.FuncaoOrigemDAO;
import br.com.restaurante.dao.MenuFuncaoOrigemDAO;
import br.com.restaurante.dao.PermissaoFuncaoOrigemDAO;
import br.com.restaurante.dao.UsuarioFuncaoDAO;
import br.com.restaurante.model.FuncaoModel;
import br.com.restaurante.model.FuncaoOrigemModel;
import br.com.restaurante.model.MenuFuncaoOrigemModel;
import br.com.restaurante.model.OrigemModel;
import br.com.restaurante.model.PermissaoFuncaoOrigemModel;
import br.com.restaurante.model.UsuarioFuncaoModel;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

@Stateless
@LocalBean
public class FuncaoBS extends CrudBS<FuncaoModel> {

	@Inject
	private FuncaoDAO funcaoDAO;

	@Inject
	private FuncaoOrigemDAO funcaoOrigemDAO;

	@Inject
	private UsuarioFuncaoDAO usuarioFuncaoDAO;

	@Inject
	private MenuFuncaoOrigemDAO menuFuncaoOrigemDAO;

	@Inject
	private PermissaoFuncaoOrigemDAO permissaoFuncaoOrigemDAO;
	
	@Override
	public FuncaoModel obterCrudModel(FuncaoModel crudModel) {

		FuncaoModel funcaoModel = getCrudDAO().obterCrudModel(crudModel);

		if (!TSUtil.isEmpty(funcaoModel)) {

			funcaoModel.setFuncaoOrigens(funcaoOrigemDAO.pesquisar(funcaoModel));

			for (FuncaoOrigemModel funcaoOrigem : funcaoModel.getFuncaoOrigens()) {

				funcaoOrigem.setMenus(this.menuFuncaoOrigemDAO.pesquisar(funcaoOrigem));
				funcaoOrigem.setPermissoes(this.permissaoFuncaoOrigemDAO.pesquisar(funcaoOrigem));
				funcaoOrigem.setUsuarios(usuarioFuncaoDAO.pesquisar(funcaoOrigem));

			}

		}

		return funcaoModel;
	}

	public FuncaoModel obterSimples(final FuncaoModel crudModel) {
		return this.funcaoDAO.obterSimples(crudModel);
	}

	public List<FuncaoModel> pesquisarPorOrigem(final FuncaoModel model, OrigemModel origem) {
		return this.funcaoDAO.pesquisarPorOrigem(model, origem);
	}
	
	@Override
	public FuncaoModel inserirCrudModel(final FuncaoModel model) throws TSApplicationException {

		this.funcaoDAO.inserirCrudModel(model);

		for (FuncaoOrigemModel funcaoOrigem : model.getFuncaoOrigens()) {

			this.funcaoOrigemDAO.inserir(funcaoOrigem);

			for (MenuFuncaoOrigemModel menu : funcaoOrigem.getMenus()) {
				this.menuFuncaoOrigemDAO.inserir(menu);
			}

			for (PermissaoFuncaoOrigemModel permissao : funcaoOrigem.getPermissoes()) {
				this.permissaoFuncaoOrigemDAO.inserir(permissao);
			}

			for (UsuarioFuncaoModel usuario : funcaoOrigem.getUsuarios()) {
				if(usuario.getFlagAtivo()){
					this.usuarioFuncaoDAO.inserir(usuario);
				}
			}
			
		}

		return model;

	}

	@Override
	public FuncaoModel alterarCrudModel(final FuncaoModel model) throws TSApplicationException {

		this.funcaoDAO.alterarCrudModel(model);

		for (FuncaoOrigemModel funcaoOrigem : model.getFuncaoOrigens()) {

			if (TSUtil.isEmpty(funcaoOrigem.getId())) {
				this.funcaoOrigemDAO.inserir(funcaoOrigem);
			}

			for (MenuFuncaoOrigemModel menu : funcaoOrigem.getMenus()) {

				if (TSUtil.isEmpty(menu.getId())) {

					this.menuFuncaoOrigemDAO.inserir(menu);

				} else {

					this.menuFuncaoOrigemDAO.alterar(menu);

				}

			}

			for (PermissaoFuncaoOrigemModel permissao : funcaoOrigem.getPermissoes()) {

				if (TSUtil.isEmpty(permissao.getId())) {
					this.permissaoFuncaoOrigemDAO.inserir(permissao);
				}

			}

			for (UsuarioFuncaoModel usuario : funcaoOrigem.getUsuarios()) {

				if (TSUtil.isEmpty(usuario.getId()) && usuario.getFlagAtivo()) {
					
					this.usuarioFuncaoDAO.inserir(usuario);
					
				}

				if(!TSUtil.isEmpty(usuario.getId())){
					this.usuarioFuncaoDAO.alterarSituacao(usuario);
				}
				
			}
			
		}

		return model;

	}

	@Override
	protected CrudDAO<FuncaoModel> getCrudDAO() {
		return funcaoDAO;
	}

}
