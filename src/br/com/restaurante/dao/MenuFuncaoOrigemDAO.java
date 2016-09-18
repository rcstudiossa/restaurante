package br.com.restaurante.dao;

import java.util.List;

import br.com.restaurante.model.FuncaoOrigemModel;
import br.com.restaurante.model.MenuFuncaoOrigemModel;
import br.com.restaurante.model.MenuModel;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

public final class MenuFuncaoOrigemDAO {

	@SuppressWarnings("unchecked")
	public List<MenuFuncaoOrigemModel> pesquisar(final MenuModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, MENU_ID, FUNCAO_ORIGEM_ID, FLAG_INSERIR, FLAG_ALTERAR, FLAG_EXCLUIR, FLAG_IMPRIMIR, FLAG_ATALHO FROM MENU_FUNCAO_ORIGEM WHERE MENU_ID = ?", model.getId());

		return broker.getCollectionBean(MenuFuncaoOrigemModel.class, "id", "menuModel.id", "funcaoOrigemModel.id", "flagInserir", "flagAlterar", "flagExcluir", "flagImprimir", "flagAtalho");

	}

	@SuppressWarnings("unchecked")
	public List<MenuFuncaoOrigemModel> pesquisar(final FuncaoOrigemModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT MFO.ID, MFO.MENU_ID, ARVORE_MENU(MFO.MENU_ID, ''), MFO.FUNCAO_ORIGEM_ID, FO.FUNCAO_ID, FO.ORIGEM_ID, MFO.FLAG_INSERIR, MFO.FLAG_ALTERAR, MFO.FLAG_EXCLUIR, MFO.FLAG_IMPRIMIR, MFO.FLAG_ATALHO FROM MENU_FUNCAO_ORIGEM MFO, FUNCAO_ORIGEM FO WHERE MFO.FUNCAO_ORIGEM_ID = FO.ID AND FO.FUNCAO_ID = ? AND FO.ORIGEM_ID = ? ORDER BY ARVORE_MENU(MFO.MENU_ID, '')", model.getFuncaoModel().getId(), model.getOrigemModel().getId());

		return broker.getCollectionBean(MenuFuncaoOrigemModel.class, "id", "menuModel.id", "menuModel.descricao", "funcaoOrigemModel.id", "funcaoOrigemModel.funcaoModel.id", "funcaoOrigemModel.origemModel.id", "flagInserir", "flagAlterar", "flagExcluir", "flagImprimir", "flagAtalho");

	}

	public MenuFuncaoOrigemModel obter(final MenuFuncaoOrigemModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, MENU_ID, FUNCAO_ORIGEM_ID, FLAG_INSERIR, FLAG_ALTERAR, FLAG_EXCLUIR, FLAG_IMPRIMIR, FLAG_ATALHO FROM MENU_FUNCAO_ORIGEM WHERE ID = ?", model.getId());

		return (MenuFuncaoOrigemModel) broker.getObjectBean(MenuFuncaoOrigemModel.class, "id", "menuModel.id", "funcaoOrigemModel.id", "flagInserir", "flagAlterar", "flagExcluir", "flagImprimir", "flagAtalho");

	}

	public void inserir(final MenuFuncaoOrigemModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("menu_funcao_origem_id_seq"));

		broker.setSQL("INSERT INTO MENU_FUNCAO_ORIGEM (ID, MENU_ID, FUNCAO_ORIGEM_ID, FLAG_INSERIR, FLAG_ALTERAR, FLAG_EXCLUIR, FLAG_IMPRIMIR, FLAG_ATALHO) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", model.getId(), model.getMenuModel().getId(), model.getFuncaoOrigemModel().getId(), model.getFlagInserir(), model.getFlagAlterar(), model.getFlagExcluir(), model.getFlagImprimir(), model.getFlagAtalho());

		broker.execute();

	}

	public void alterar(final MenuFuncaoOrigemModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE MENU_FUNCAO_ORIGEM SET MENU_ID = ?, FUNCAO_ORIGEM_ID = ?, FLAG_INSERIR = ?, FLAG_ALTERAR = ?, FLAG_EXCLUIR = ?, FLAG_IMPRIMIR = ?, FLAG_ATALHO = ? WHERE ID = ?", model.getMenuModel().getId(), model.getFuncaoOrigemModel().getId(), model.getFlagInserir(), model.getFlagAlterar(), model.getFlagExcluir(), model.getFlagImprimir(), model.getFlagAtalho(), model.getId());

		broker.execute();

	}

	public void excluir(final MenuFuncaoOrigemModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("DELETE FROM MENU_FUNCAO_ORIGEM WHERE ID = ?", model.getId());

		broker.execute();

	}
}
