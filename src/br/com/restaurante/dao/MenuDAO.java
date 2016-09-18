package br.com.restaurante.dao;

import java.util.List;

import br.com.restaurante.model.MenuModel;
import br.com.restaurante.model.OrigemModel;
import br.com.restaurante.model.UsuarioModel;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;

public final class MenuDAO {

	public MenuModel obter(final MenuModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, ARVORE_MENU(M.ID, ''), URL, MANAGED_BEAN, MENU_ID, (SELECT M2.DESCRICAO FROM MENU M2 WHERE M2.ID = M.MENU_ID), FLAG_ATIVO, DATA_CADASTRO, (SELECT NOME FROM USUARIO U WHERE U.ID = M.USUARIO_CADASTRO_ID), M.FLAG_DEPENDE_FUNCIONARIO FROM MENU M WHERE ID = ?", model.getId());

		return (MenuModel) broker.getObjectBean(MenuModel.class, "id", "descricao", "url", "managedBean", "menuModel.id", "menuModel.descricao", "flagAtivo", "dataCadastro", "usuarioCadastroModel.nome", "flagDependeFuncionario");

	}

	@SuppressWarnings("unchecked")
	public List<MenuModel> pesquisar(final MenuModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO, URL, (SELECT M2.DESCRICAO FROM MENU M2 WHERE M2.ID = M.MENU_ID), FLAG_ATIVO, M.FLAG_DEPENDE_FUNCIONARIO FROM MENU M WHERE MENU_ID = COALESCE(?, MENU_ID) AND FLAG_ATIVO = COALESCE(?, FLAG_ATIVO) ORDER BY M.FLAG_ATIVO, COALESCE(M.MENU_ID, 0), M.DESCRICAO", model.getMenuModel().getId(), model.getFlagAtivo());

		return broker.getCollectionBean(MenuModel.class, "id", "descricao", "url", "menuModel.descricao", "flagAtivo", "flagDependeFuncionario");

	}

	@SuppressWarnings("unchecked")
	public List<MenuModel> pesquisarCombo() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT M.ID, ARVORE_MENU(M.ID, '') FROM MENU M WHERE M.FLAG_ATIVO AND NOT EXISTS (SELECT 1 FROM MENU M2 WHERE M2.MENU_ID = M.ID) ORDER BY 2");

		return broker.getCollectionBean(MenuModel.class, "id", "descricao");

	}

	@SuppressWarnings("unchecked")
	public List<MenuModel> pesquisarMenuRaizAdm() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT DISTINCT M.ID, M.DESCRICAO, M.URL, M.TAB_ATIVA, M.MENU_ID, M.FLAG_DEPENDE_FUNCIONARIO FROM MENU M, MENU M2 WHERE M.ID = M2.MENU_ID AND M.FLAG_ATIVO = TRUE AND M.MENU_ID IS NULL ORDER BY M.ORDEM, M.DESCRICAO");

		return broker.getCollectionBean(MenuModel.class, "id", "descricao", "url", "tabAtiva", "menuModel.id", "flagDependeFuncionario");

	}

	@SuppressWarnings("unchecked")
	public List<MenuModel> pesquisarMenuRaiz(final UsuarioModel usuarioModel, final OrigemModel origemModel) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT DISTINCT M.ID, M.DESCRICAO, M.URL, M.TAB_ATIVA, M.MENU_ID, M.ORDEM, M.ICONE, M.FLAG_DEPENDE_FUNCIONARIO FROM MENU M WHERE M.FLAG_ATIVO = TRUE AND M.MENU_ID IS NULL AND NOT M.FLAG_USO_ADMINISTRADOR AND EXISTS ( SELECT 1 FROM MENU_FUNCAO_ORIGEM MFO, FUNCAO_ORIGEM FO WHERE MFO.FUNCAO_ORIGEM_ID = FO.ID AND FO.FUNCAO_ID = ? AND FO.ORIGEM_ID = ? AND M.ID IN (MENU_RAIZ(MFO.MENU_ID))) ORDER BY M.ORDEM, M.DESCRICAO", usuarioModel.getUsuarioFuncaoModel().getFuncaoModel().getId(), origemModel.getId());

		return broker.getCollectionBean(MenuModel.class, "id", "descricao", "url", "tabAtiva", "menuModel.id", "ordem", "icone", "flagDependeFuncionario");

	}

	@SuppressWarnings("unchecked")
	public List<MenuModel> pesquisarMenuFilhos(final MenuModel menuModel, final UsuarioModel usuarioModel, final OrigemModel origemModel) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT DISTINCT M.ID, M.DESCRICAO, M.URL, M.TAB_ATIVA, M.MENU_ID, (SELECT M2.DESCRICAO FROM MENU M2 WHERE M2.ID = M.MENU_ID), M.MANAGED_BEAN, M.ORDEM, (SELECT MFO.FLAG_INSERIR FROM MENU_FUNCAO_ORIGEM MFO,FUNCAO_ORIGEM FO WHERE MFO.FUNCAO_ORIGEM_ID = FO.ID AND MFO.MENU_ID = M.ID AND FO.FUNCAO_ID = ? AND FO.ORIGEM_ID = ?) FLAG_INSERIR, (SELECT MFO.FLAG_ALTERAR FROM MENU_FUNCAO_ORIGEM MFO,FUNCAO_ORIGEM FO WHERE MFO.FUNCAO_ORIGEM_ID = FO.ID AND MFO.MENU_ID = M.ID AND FO.FUNCAO_ID = ? AND FO.ORIGEM_ID = ?) FLAG_ALTERAR, (SELECT MFO.FLAG_EXCLUIR FROM MENU_FUNCAO_ORIGEM MFO,FUNCAO_ORIGEM FO WHERE MFO.FUNCAO_ORIGEM_ID = FO.ID AND MFO.MENU_ID = M.ID AND FO.FUNCAO_ID = ? AND FO.ORIGEM_ID = ?) FLAG_EXCLUIR, (SELECT MFO.FLAG_IMPRIMIR FROM MENU_FUNCAO_ORIGEM MFO,FUNCAO_ORIGEM FO WHERE MFO.FUNCAO_ORIGEM_ID = FO.ID AND MFO.MENU_ID = M.ID AND FO.FUNCAO_ID = ? AND FO.ORIGEM_ID = ?) FLAG_IMPRIMIR, ICONE, M.FLAG_DEPENDE_FUNCIONARIO FROM MENU M WHERE NOT M.FLAG_USO_ADMINISTRADOR AND M.FLAG_ATIVO = TRUE AND M.MENU_ID = ? AND EXISTS (SELECT SUB.FLAG_PERMISSAO FROM (WITH RECURSIVE MENU_FILHOS (id, MENU_ID) AS (SELECT id, MENU_ID FROM menu WHERE id = M.ID UNION ALL SELECT M.id, M.MENU_ID FROM menu M INNER JOIN MENU_FILHOS M2 ON M.menu_id = M2.id) SELECT MF.ID, MF.MENU_ID, (EXISTS (SELECT 1 FROM MENU_FUNCAO_ORIGEM MFO2, FUNCAO_ORIGEM FO2 WHERE MFO2.FUNCAO_ORIGEM_ID = FO2.ID AND FO2.FUNCAO_ID = ? AND FO2.ORIGEM_ID = ? AND MFO2.MENU_ID = MF.ID)) FLAG_PERMISSAO FROM MENU_FILHOS MF) SUB WHERE (SUB.MENU_ID = M.ID OR SUB.ID = M.ID) AND SUB.FLAG_PERMISSAO = TRUE) ORDER BY M.ORDEM", usuarioModel.getUsuarioFuncaoModel().getFuncaoModel().getId(), origemModel.getId(), usuarioModel.getUsuarioFuncaoModel().getFuncaoModel().getId(), origemModel.getId(), usuarioModel.getUsuarioFuncaoModel().getFuncaoModel().getId(), origemModel.getId(), usuarioModel.getUsuarioFuncaoModel().getFuncaoModel().getId(), origemModel.getId(), menuModel.getId(), usuarioModel.getUsuarioFuncaoModel().getFuncaoModel().getId(), origemModel.getId());

		return broker.getCollectionBean(MenuModel.class, "id", "descricao", "url", "tabAtiva", "menuModel.id", "menuModel.descricao", "managedBean", "ordem", "flagInserir", "flagAlterar", "flagExcluir", "flagImprimir", "icone", "flagDependeFuncionario");

	}

	@SuppressWarnings("unchecked")
	public List<MenuModel> pesquisarMenusADM(MenuModel menu) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT M.ID, M.DESCRICAO, M.URL, M.TAB_ATIVA, M.MENU_ID, (SELECT DESCRICAO FROM MENU M2 WHERE M.MENU_ID = M2.ID), M.MANAGED_BEAN, M.ORDEM, TRUE FLAG_INSERIR, TRUE FLAG_ALTERAR, TRUE FLAG_EXCLUIR, TRUE FLAG_IMPRIMIR, ICONE, M.FLAG_DEPENDE_FUNCIONARIO FROM MENU M WHERE M.FLAG_ATIVO AND COALESCE(M.MENU_ID, -1) = COALESCE(?, -1) ORDER BY M.ORDEM, M.DESCRICAO", menu.getId());

		return broker.getCollectionBean(MenuModel.class, "id", "descricao", "url", "tabAtiva", "menuModel.id", "menuModel.descricao", "managedBean", "ordem", "flagInserir", "flagAlterar", "flagExcluir", "flagImprimir", "icone", "flagDependeFuncionario");

	}

}
