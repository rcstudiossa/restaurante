package br.com.restaurante.dao;

import java.sql.Timestamp;
import java.util.List;

import br.com.restaurante.model.MedidaModel;
import br.com.restaurante.model.MedidaUnidadeMedidaModel;
import br.com.restaurante.util.Utilitario;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

public final class MedidaDAO implements CrudDAO<MedidaModel> {

	@Override
	public MedidaModel obterCrudModel(final MedidaModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO, ID_EXTERNO, OBTER_DATA_CADASTRO_LOG(M.ID, 'MEDIDA'), OBTER_USUARIO_CADASTRO_LOG(M.ID, 'MEDIDA'), M.DATA_CADASTRO, (SELECT U.NOME FROM USUARIO U WHERE U.ID = M.USUARIO_CADASTRO_ID) FROM MEDIDA M WHERE ID = ?", model.getId());

		return (MedidaModel) broker.getObjectBean(MedidaModel.class, "id", "descricao", "idExterno", "dataCadastro", "usuarioCadastroModel.nome", "dataAtualizacao", "usuarioAtualizacaoModel.nome");

	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<MedidaModel> pesquisarLog(MedidaModel model) {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("SELECT ID, DESCRICAO, ID_EXTERNO, M.DATA_CADASTRO, (SELECT U.NOME FROM USUARIO U WHERE U.ID = M.USUARIO_CADASTRO_ID) FROM LOG.MEDIDA M WHERE ID = ? ORDER BY M.DATA_CADASTRO DESC", model.getId());
		
		return broker.getCollectionBean(MedidaModel.class, "id", "descricao", "idExterno", "dataCadastro", "usuarioCadastroModel.nome");
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<MedidaModel> pesquisarCrudModel(final MedidaModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO FROM MEDIDA WHERE SEM_ACENTOS(DESCRICAO) LIKE SEM_ACENTOS(COALESCE(?, DESCRICAO)) ORDER BY DESCRICAO", Utilitario.getStringIlike(model.getDescricao(), true));

		return broker.getCollectionBean(MedidaModel.class, "id", "descricao");

	}

	@SuppressWarnings("unchecked")
	public List<MedidaUnidadeMedidaModel> pesquisarUnidades(final MedidaModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT MUM.ID, M.ID, M.DESCRICAO, UM.ID, UM.DESCRICAO, MUM.FORMULA_IDA, MUM.FORMULA_VOLTA, MUM.FLAG_PADRAO FROM MEDIDA_UNIDADE_MEDIDA MUM, MEDIDA M, UNIDADE_MEDIDA UM WHERE MUM.MEDIDA_ID = M.ID AND MUM.UNIDADE_MEDIDA_ID = UM.ID AND MUM.MEDIDA_ID = ?", model.getId());

		return broker.getCollectionBean(MedidaUnidadeMedidaModel.class, "id", "medidaModel.id", "medidaModel.descricao", "unidadeMedidaModel.id", "unidadeMedidaModel.descricao", "formulaIda", "formulaVolta", "flagPadrao");

	}

	public MedidaUnidadeMedidaModel inserirUnidade(final MedidaUnidadeMedidaModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("medida_unidade_medida_id_seq"));

		broker.setSQL("INSERT INTO MEDIDA_UNIDADE_MEDIDA (ID, MEDIDA_ID, UNIDADE_MEDIDA_ID, FORMULA_IDA, FORMULA_VOLTA, FLAG_PADRAO) VALUES (?, ?, ?, ?, ?, ?)", model.getId(), model.getMedidaModel().getId(), model.getUnidadeMedidaModel().getId(), model.getFormulaIda(), model.getFormulaVolta(), model.getFlagPadrao());

		broker.execute();

		return model;

	}

	public MedidaUnidadeMedidaModel alterarUnidade(final MedidaUnidadeMedidaModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE MEDIDA_UNIDADE_MEDIDA SET FORMULA_IDA = ?, FORMULA_VOLTA = ?, FLAG_PADRAO = ? WHERE ID = ?", model.getFormulaIda(), model.getFormulaVolta(), model.getFlagPadrao(), model.getId());

		broker.execute();

		return model;

	}

	public MedidaUnidadeMedidaModel excluirUnidade(final MedidaUnidadeMedidaModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("DELETE FROM MEDIDA_UNIDADE_MEDIDA WHERE ID = ?", model.getId());

		broker.execute();

		return model;

	}

	public void excluirMedidasUnidade(final MedidaModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("DELETE FROM MEDIDA_UNIDADE_MEDIDA WHERE MEDIDA_ID = ?", model.getId());

		broker.execute();

	}

	@Override
	public MedidaModel inserirCrudModel(final MedidaModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("medida_id_seq"));

		broker.setSQL("INSERT INTO MEDIDA (ID, DESCRICAO, ID_EXTERNO, DATA_CADASTRO, USUARIO_CADASTRO_ID) VALUES (?, ?, ?, ?, ?)", model.getId(), model.getDescricao(), model.getIdExterno(), new Timestamp(model.getDataCadastro().getTime()), model.getUsuarioCadastroModel().getId());

		broker.execute();

		return model;

	}

	@Override
	public MedidaModel alterarCrudModel(final MedidaModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE MEDIDA SET DESCRICAO = ?, DATA_CADASTRO = ?, USUARIO_CADASTRO_ID = ? WHERE ID = ?", model.getDescricao(), new Timestamp(model.getDataAtualizacao().getTime()), model.getUsuarioAtualizacaoModel().getId(), model.getId());

		broker.execute();

		return model;

	}

	@Override
	public MedidaModel excluirCrudModel(MedidaModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("DELETE FROM MEDIDA WHERE ID = ?", model.getId());

		broker.execute();

		return model;
	}

}
