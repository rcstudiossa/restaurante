package br.com.restaurante.dao;

import java.util.List;

import br.com.restaurante.model.FuncaoModel;
import br.com.restaurante.model.FuncaoOrigemModel;
import br.com.restaurante.model.OrigemModel;
import br.com.restaurante.model.PermissaoFuncaoOrigemModel;
import br.com.restaurante.model.PermissaoModel;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

public class PermissaoFuncaoOrigemDAO {

	public void inserir(final PermissaoFuncaoOrigemModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("INSERT INTO PERMISSAO_FUNCAO_ORIGEM(PERMISSAO_ID,FUNCAO_ORIGEM_ID) VALUES(?,?)", model.getPermissaoModel().getId(), model.getFuncaoOrigemModel().getId());

		broker.execute();

	}

	@SuppressWarnings("unchecked")
	public List<PermissaoFuncaoOrigemModel> pesquisar(final PermissaoModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID,PERMISSAO_ID,(SELECT P.DESCRICAO FROM PERMISSAO P WHERE P.ID = PERMISSAO_ID),FUNCAO_ORIGEM_ID,(SELECT TRIM(F.DESCRICAO) FROM FUNCAO F WHERE F.ID = (SELECT FO.FUNCAO_ID FROM FUNCAO_ORIGEM FO WHERE FO.ID = FUNCAO_ORIGEM_ID)) AS FUNCAO, (SELECT O.DESCRICAO FROM ORIGEM O WHERE O.ID = (SELECT FO.ORIGEM_ID FROM FUNCAO_ORIGEM FO WHERE FO.ID = FUNCAO_ORIGEM_ID))AS ORIGEM FROM PERMISSAO_FUNCAO_ORIGEM WHERE PERMISSAO_ID = ? ORDER BY 5", model.getId());

		return broker.getCollectionBean(PermissaoFuncaoOrigemModel.class, "id", "permissaoModel.id", "permissaoModel.descricao", "funcaoOrigemModel.id", "funcaoOrigemModel.funcaoModel.descricao", "funcaoOrigemModel.origemModel.descricao");

	}

	@SuppressWarnings("unchecked")
	public List<PermissaoFuncaoOrigemModel> pesquisar(final FuncaoOrigemModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT PFO.ID, PFO.PERMISSAO_ID, (SELECT P.DESCRICAO FROM PERMISSAO P WHERE P.ID = PFO.PERMISSAO_ID), PFO.FUNCAO_ORIGEM_ID, (SELECT F.DESCRICAO FROM FUNCAO F WHERE F.ID = FO.FUNCAO_ID) AS FUNCAO, (SELECT O.DESCRICAO FROM ORIGEM O WHERE O.ID = FO.ORIGEM_ID) AS ORIGEM FROM PERMISSAO_FUNCAO_ORIGEM PFO, FUNCAO_ORIGEM FO WHERE PFO.FUNCAO_ORIGEM_ID = FO.ID AND FO.FUNCAO_ID = ? AND FO.ORIGEM_ID = ? ORDER BY 3", model.getFuncaoModel().getId(), model.getOrigemModel().getId());

		return broker.getCollectionBean(PermissaoFuncaoOrigemModel.class, "id", "permissaoModel.id", "permissaoModel.descricao", "funcaoOrigemModel.id", "funcaoOrigemModel.funcaoModel.descricao", "funcaoOrigemModel.origemModel.descricao");

	}

	@SuppressWarnings("unchecked")
	public List<PermissaoFuncaoOrigemModel> pesquisarNaoAssociados(final PermissaoModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT FO.ID,(SELECT TRIM(F.DESCRICAO) FROM FUNCAO F WHERE F.ID = FO.FUNCAO_ID), (SELECT O.DESCRICAO FROM ORIGEM O WHERE O.ID = FO.ORIGEM_ID) FROM FUNCAO_ORIGEM FO, ORIGEM O WHERE FO.ORIGEM_ID = O.ID AND O.FLAG_ATIVO AND FO.ID NOT IN(SELECT PFO.FUNCAO_ORIGEM_ID FROM PERMISSAO_FUNCAO_ORIGEM PFO WHERE PFO.PERMISSAO_ID = ?) ORDER BY 3, 2", model.getId());

		return broker.getCollectionBean(PermissaoFuncaoOrigemModel.class, "funcaoOrigemModel.id", "funcaoOrigemModel.funcaoModel.descricao", "funcaoOrigemModel.origemModel.descricao");

	}

	public PermissaoFuncaoOrigemModel obter(final Integer permissaoId, FuncaoModel funcaoModel, OrigemModel origemModel) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID FROM PERMISSAO_FUNCAO_ORIGEM PFO WHERE PFO.PERMISSAO_ID = ? AND EXISTS (SELECT ID FROM FUNCAO_ORIGEM FO WHERE FO.ID = PFO.FUNCAO_ORIGEM_ID AND FO.ORIGEM_ID = ? AND FO.FUNCAO_ID = ?) LIMIT 1", permissaoId, origemModel.getId(), funcaoModel.getId());

		return (PermissaoFuncaoOrigemModel) broker.getObjectBean(PermissaoFuncaoOrigemModel.class, "id");

	}

	public void deletarPorPermissao(final PermissaoFuncaoOrigemModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("DELETE FROM PERMISSAO_FUNCAO_ORIGEM WHERE PERMISSAO_ID = ?", model.getPermissaoModel().getId());

		broker.execute();

	}

	public void excluir(final PermissaoFuncaoOrigemModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("DELETE FROM PERMISSAO_FUNCAO_ORIGEM WHERE ID = ?", model.getId());

		broker.execute();

	}

}
