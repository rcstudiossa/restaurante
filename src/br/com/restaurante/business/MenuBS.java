package br.com.restaurante.business;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.restaurante.dao.MenuDAO;
import br.com.restaurante.model.MenuModel;
import br.com.restaurante.model.OrigemModel;
import br.com.restaurante.model.UsuarioModel;

@Stateless
@LocalBean
public class MenuBS {

	@Inject
	private MenuDAO menuDAO;

	public List<MenuModel> pesquisarMenuRaizAdm() {
		return this.menuDAO.pesquisarMenuRaizAdm();
	}

	public List<MenuModel> pesquisarMenuRaiz(final UsuarioModel usuarioModel, final OrigemModel origemModel) {
		return this.menuDAO.pesquisarMenuRaiz(usuarioModel, origemModel);
	}

	public List<MenuModel> pesquisarCombo() {
		return this.menuDAO.pesquisarCombo();
	}

	public List<MenuModel> pesquisarMenusADM(MenuModel model) {
		return this.menuDAO.pesquisarMenusADM(model);
	}

	public List<MenuModel> pesquisarMenuFilho(final MenuModel menuModel, final UsuarioModel usuarioModel, final OrigemModel origemModel) {
		return this.menuDAO.pesquisarMenuFilhos(menuModel, usuarioModel, origemModel);
	}
	
	public MenuModel obter(final MenuModel model) {
		return this.menuDAO.obter(model);
	}

}
