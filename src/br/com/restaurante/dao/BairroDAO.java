package br.com.restaurante.dao;

import java.sql.Timestamp;
import java.util.List;

import br.com.restaurante.model.BairroModel;
import br.com.restaurante.model.CidadeModel;
import br.com.restaurante.util.Utilitario;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

public class BairroDAO implements CrudDAO<BairroModel> {

	@SuppressWarnings("unchecked")
	public List<BairroModel> pesquisarCrudModel(final BairroModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT B.ID, B.DESCRICAO, C.DESCRICAO, E.SIGLA FROM BAIRRO B, CIDADE C, ESTADO E WHERE C.ID = B.CIDADE_ID AND E.ID = C.ESTADO_ID AND B.CIDADE_ID = COALESCE(?, B.CIDADE_ID) AND SEM_ACENTOS(B.DESCRICAO) ILIKE SEM_ACENTOS(COALESCE(?, B.DESCRICAO)) ORDER BY B.DESCRICAO", model.getCidadeModel().getId(), Utilitario.getStringIlike(model.getDescricao(), true));

		return broker.getCollectionBean(BairroModel.class, "id", "descricao", "cidadeModel.descricao", "cidadeModel.estadoModel.sigla");

	}
	
	@SuppressWarnings("unchecked")
	public List<BairroModel> pesquisarLog(final BairroModel model) {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("SELECT B.ID, B.DESCRICAO, B.CIDADE_ID, (SELECT C.DESCRICAO FROM CIDADE C WHERE C.ID = B.CIDADE_ID), C.ESTADO_ID, B.DATA_CADASTRO, (SELECT U.NOME FROM USUARIO U WHERE U.ID = B.USUARIO_CADASTRO_ID) FROM LOG.BAIRRO B, CIDADE C WHERE C.ID = B.CIDADE_ID AND B.ID = ? ORDER BY B.DATA_CADASTRO DESC", model.getId());
		
		return broker.getCollectionBean(BairroModel.class, "id", "descricao", "cidadeModel.id", "cidadeModel.descricao", "cidadeModel.estadoModel.id", "dataCadastro", "usuarioCadastroModel.nome");
		
	}

	@SuppressWarnings("unchecked")
	public List<BairroModel> pesquisar(final CidadeModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID,DESCRICAO FROM BAIRRO WHERE CIDADE_ID = ? ORDER BY DESCRICAO", model.getId());

		return broker.getCollectionBean(BairroModel.class, "id", "descricao");

	}

	public BairroModel obterCrudModel(final BairroModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT B.ID, B.DESCRICAO, B.CIDADE_ID, C.ESTADO_ID, OBTER_DATA_CADASTRO_LOG(B.ID, 'BAIRRO'), OBTER_USUARIO_CADASTRO_LOG(B.ID, 'BAIRRO'), USUARIO_CADASTRO_ID, (SELECT U.NOME FROM USUARIO U WHERE U.ID = B.USUARIO_CADASTRO_ID), DATA_CADASTRO FROM BAIRRO B, CIDADE C WHERE C.ID = B.CIDADE_ID AND B.ID = ?", model.getId());

		return (BairroModel) broker.getObjectBean(BairroModel.class, "id", "descricao", "cidadeModel.id", "cidadeModel.estadoModel.id", "dataCadastro", "usuarioCadastroModel.nome", "usuarioAtualizacaoModel.id", "usuarioAtualizacaoModel.nome", "dataAtualizacao");
	}

	public BairroModel inserirCrudModel(final BairroModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("bairro_id_seq"));

		broker.setSQL("INSERT INTO BAIRRO (ID, DESCRICAO, CIDADE_ID, USUARIO_CADASTRO_ID, DATA_CADASTRO) VALUES (?, ?, ?, ?, ?)", model.getId(), model.getDescricao(), model.getCidadeModel().getId(), model.getUsuarioCadastroModel().getId(), new Timestamp(model.getDataCadastro().getTime()));

		broker.execute();

		return model;
	}

	public BairroModel alterarCrudModel(final BairroModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE BAIRRO SET DESCRICAO = ?, CIDADE_ID = ?, USUARIO_CADASTRO_ID = ?, DATA_CADASTRO = ? WHERE ID = ?", model.getDescricao(), model.getCidadeModel().getId(), model.getUsuarioAtualizacaoModel().getId(), new Timestamp(model.getDataAtualizacao().getTime()), model.getId());

		broker.execute();

		return model;
	}

	public BairroModel excluirCrudModel(final BairroModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("DELETE FROM BAIRRO WHERE ID = ?", model.getId());

		broker.execute();

		return model;
	}

}