package br.com.restaurante.dao;

import java.sql.Timestamp;
import java.util.List;

import br.com.restaurante.model.UnidadeMedidaModel;
import br.com.restaurante.util.Utilitario;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

public final class UnidadeMedidaDAO implements CrudDAO<UnidadeMedidaModel> {

	@Override
	public UnidadeMedidaModel obterCrudModel(UnidadeMedidaModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO, OBTER_DATA_CADASTRO_LOG(UM.ID, 'UNIDADE_MEDIDA'), OBTER_USUARIO_CADASTRO_LOG(UM.ID, 'UNIDADE_MEDIDA'), DATA_CADASTRO, (SELECT NOME FROM USUARIO U1 WHERE U1.ID = UM.USUARIO_CADASTRO_ID), DESCRICAO_DETALHADA FROM UNIDADE_MEDIDA UM WHERE UM.ID = ?", model.getId());

		return (UnidadeMedidaModel) broker.getObjectBean(UnidadeMedidaModel.class, "id", "descricao", "dataCadastro", "usuarioCadastroModel.nome", "dataAtualizacao", "usuarioAtualizacaoModel.nome", "descricaoDetalhada");

	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<UnidadeMedidaModel> pesquisarLog(UnidadeMedidaModel model) {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("SELECT ID, DESCRICAO, OBTER_DATA_CADASTRO_LOG(UM.ID, 'UNIDADE_MEDIDA'), OBTER_USUARIO_CADASTRO_LOG(UM.ID, 'UNIDADE_MEDIDA'), DATA_CADASTRO, (SELECT NOME FROM USUARIO U1 WHERE U1.ID = UM.USUARIO_CADASTRO_ID), DESCRICAO_DETALHADA FROM LOG.UNIDADE_MEDIDA UM WHERE UM.ID = ? ORDER BY UM.DATA_CADASTRO ASC", model.getId());
		
		return broker.getCollectionBean(UnidadeMedidaModel.class, "id", "descricao", "dataCadastro", "usuarioCadastroModel.nome", "dataAtualizacao", "usuarioAtualizacaoModel.nome", "descricaoDetalhada");
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<UnidadeMedidaModel> pesquisarCrudModel(UnidadeMedidaModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO, DESCRICAO_DETALHADA FROM UNIDADE_MEDIDA WHERE DESCRICAO ILIKE COALESCE(?, DESCRICAO) ORDER BY 2", Utilitario.getStringIlike(model.getDescricao(), true));

		return broker.getCollectionBean(UnidadeMedidaModel.class, "id", "descricao", "descricaoDetalhada");

	}

	@Override
	public UnidadeMedidaModel inserirCrudModel(UnidadeMedidaModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("unidade_medida_id_seq"));

		broker.setSQL("INSERT INTO UNIDADE_MEDIDA (ID, DESCRICAO, DATA_CADASTRO, USUARIO_CADASTRO_ID, DESCRICAO_DETALHADA, ID_EXTERNO) VALUES (?,?,?,?,?,?)", model.getId(), model.getDescricao(), new Timestamp(model.getDataCadastro().getTime()), model.getUsuarioCadastroModel().getId(), model.getDescricaoDetalhada(), model.getIdExterno());

		broker.execute();

		return model;
	}

	@Override
	public UnidadeMedidaModel alterarCrudModel(UnidadeMedidaModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE UNIDADE_MEDIDA SET DESCRICAO = ?, DESCRICAO_DETALHADA = ?, DATA_CADASTRO = ?, USUARIO_CADASTRO_ID = ? WHERE ID = ?", model.getDescricao(), model.getDescricaoDetalhada(), new Timestamp(model.getDataAtualizacao().getTime()), model.getUsuarioAtualizacaoModel().getId(), model.getId());

		broker.execute();

		return model;
	}

	@Override
	public UnidadeMedidaModel excluirCrudModel(UnidadeMedidaModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("DELETE FROM UNIDADE_MEDIDA WHERE ID = ?", model.getId());

		broker.execute();

		return model;

	}

}
