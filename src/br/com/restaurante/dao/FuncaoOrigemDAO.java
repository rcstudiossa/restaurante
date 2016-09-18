package br.com.restaurante.dao;

import java.util.List;

import br.com.restaurante.model.FuncaoModel;
import br.com.restaurante.model.FuncaoOrigemModel;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

public final class FuncaoOrigemDAO {

	public FuncaoOrigemModel obter(final FuncaoOrigemModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID,FUNCAO_ID,(SELECT F.DESCRICAO FROM FUNCAO F WHERE F.ID = FUNCAO_ID),ORIGEM_ID,(SELECT O.DESCRICAO FROM ORIGEM O WHERE O.ID = ORIGEM_ID) FROM FUNCAO_ORIGEM WHERE ID = ?", model.getId());

		return (FuncaoOrigemModel) broker.getObjectBean(FuncaoOrigemModel.class, "id", "funcaoModel.id", "funcaoModel.descricao", "origemModel.id", "origemModel.descricao");

	}

	@SuppressWarnings("unchecked")
	public List<FuncaoOrigemModel> pesquisar(final FuncaoModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, ORIGEM_ID, (SELECT O.DESCRICAO FROM ORIGEM O WHERE O.ID = ORIGEM_ID), FUNCAO_ID FROM FUNCAO_ORIGEM WHERE FUNCAO_ID = ?", model.getId());

		return broker.getCollectionBean(FuncaoOrigemModel.class, "id", "origemModel.id", "origemModel.descricao", "funcaoModel.id");

	}

	@SuppressWarnings("unchecked")
	public List<FuncaoOrigemModel> pesquisarComboFuncaoOrigem() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT FO.ID, F.ID, F.DESCRICAO, O.ID, O.DESCRICAO FROM FUNCAO_ORIGEM FO, FUNCAO F, ORIGEM O WHERE FO.FUNCAO_ID = F.ID AND FO.ORIGEM_ID = O.ID AND F.FLAG_ATIVO = TRUE AND O.FLAG_ATIVO = TRUE ORDER BY F.DESCRICAO, O.DESCRICAO");

		return broker.getCollectionBean(FuncaoOrigemModel.class, "id", "funcaoModel.id", "funcaoModel.descricao", "origemModel.id", "origemModel.descricao");

	}

	public void inserir(final FuncaoOrigemModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("funcao_origem_id_seq"));

		broker.setSQL("INSERT INTO FUNCAO_ORIGEM (ID, FUNCAO_ID, ORIGEM_ID) VALUES (?,?,?)", model.getId(), model.getFuncaoModel().getId(), model.getOrigemModel().getId());

		broker.execute();

	}

	public void alterar(final FuncaoOrigemModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE FUNCAO_ORIGEM SET FUNCAO_ID = ?, ORIGEM_ID = ? WHERE ID = ?", model.getFuncaoModel().getId(), model.getOrigemModel().getId(), model.getId());

		broker.execute();

	}

	public void excluir(final FuncaoOrigemModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("DELETE FROM FUNCAO_ORIGEM WHERE ID = ?", model.getId());

		broker.execute();

	}
}
