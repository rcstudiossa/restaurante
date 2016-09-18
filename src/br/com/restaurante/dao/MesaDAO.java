package br.com.restaurante.dao;

import java.sql.Timestamp;
import java.util.List;

import br.com.restaurante.model.MesaModel;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

public class MesaDAO implements CrudDAO<MesaModel> {

	@SuppressWarnings("unchecked")
	public List<MesaModel> pesquisarCrudModel(final MesaModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, CODIGO, DESCRICAO, FLAG_ATIVO, DATA_CADASTRO FROM MESA ORDER BY CODIGO");

		return broker.getCollectionBean(MesaModel.class, "id", "codigo", "descricao", "flagAtivo", "dataCadastro");

	}
	
	@SuppressWarnings("unchecked")
	public List<MesaModel> pesquisarLog(final MesaModel model) {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("SELECT ID, CODIGO, DESCRICAO, FLAG_ATIVO FROM MESA WHERE ID = ? ORDER BY DATA_CADASTRO DESC", model.getId());
		
		return broker.getCollectionBean(MesaModel.class, "id", "codigo", "descricao", "flagAtivo", "dataCadastro");
		
	}

	public MesaModel obterCrudModel(final MesaModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, CODIGO, DESCRICAO, FLAG_ATIVO, DATA_CADASTRO FROM MESA WHERE ID = ?", model.getId());

		return (MesaModel) broker.getObjectBean(MesaModel.class,  "id", "codigo", "descricao", "flagAtivo", "dataCadastro");
	}

	public MesaModel inserirCrudModel(final MesaModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("mesa_id_seq"));

		broker.setSQL("INSERT INTO MESA (ID, CODIGO, DESCRICAO, FLAG_ATIVO, DATA_CADASTRO) VALUES (?, ?, ?, ?, ?)", model.getId(), model.getCodigo(), model.getDescricao(), model.getFlagAtivo(), new Timestamp(model.getDataCadastro().getTime()));

		broker.execute();

		return model;
	}

	public MesaModel alterarCrudModel(final MesaModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE MESA SET DESCRICAO = ?, CODIGO = ?, FLAG_ATIVO = ?, DATA_CADASTRO = ? WHERE ID = ?", model.getDescricao(), model.getCodigo(), model.getFlagAtivo(), new Timestamp(model.getDataAtualizacao().getTime()), model.getId());

		broker.execute();

		return model;
	}

	public MesaModel excluirCrudModel(final MesaModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("DELETE FROM MESA WHERE ID = ?", model.getId());

		broker.execute();

		return model;
	}

}