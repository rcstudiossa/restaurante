package br.com.restaurante.dao;

import java.sql.Timestamp;
import java.util.List;

import br.com.restaurante.model.CidModel;
import br.com.restaurante.util.Utilitario;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

public class CidDAO implements CrudDAO<CidModel> {

	@SuppressWarnings("unchecked")
	public List<CidModel> pesquisarCrudModel(final CidModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT C.ID, C.DESCRICAO, C.CODIGO FROM CID C WHERE SEM_ACENTOS(C.DESCRICAO) ILIKE SEM_ACENTOS(COALESCE(?, C.DESCRICAO)) AND SEM_ACENTOS(C.CODIGO) ILIKE SEM_ACENTOS(COALESCE(?, C.CODIGO)) AND C.FLAG_ATIVO = ? ORDER BY C.DESCRICAO", Utilitario.getStringIlike(model.getDescricao(), true), Utilitario.getStringIlike(model.getCodigo(), true), model.getFlagAtivo());

		return broker.getCollectionBean(CidModel.class, "id", "descricao", "codigo");

	}

	public CidModel obterCrudModel(final CidModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT C.ID, C.DESCRICAO, C.CODIGO, C.DATA_CADASTRO, C.USUARIO_CADASTRO_ID, (SELECT U.NOME FROM USUARIO U WHERE U.ID = C.USUARIO_CADASTRO_ID), C.FLAG_ATIVO FROM CID C WHERE C.ID = ?", model.getId());

		return (CidModel) broker.getObjectBean(CidModel.class, "id", "descricao", "codigo", "dataCadastro", "usuarioCadastroModel.id", "usuarioCadastroModel.nome", "flagAtivo");
	}

	public CidModel inserirCrudModel(final CidModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("cid_id_seq"));

		broker.setSQL("INSERT INTO CID (ID, DESCRICAO, CODIGO, DATA_CADASTRO, USUARIO_CADASTRO_ID, FLAG_ATIVO) VALUES (?, ?, ?, ?, ?, ?)", model.getId(), model.getDescricao(), model.getCodigo(), new Timestamp(model.getDataCadastro().getTime()), model.getUsuarioCadastroModel().getId(), model.getFlagAtivo());

		broker.execute();

		return model;
	}

	public CidModel alterarCrudModel(final CidModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE CID SET DESCRICAO = ?, CODIGO = ?, DATA_CADASTRO = ?, USUARIO_CADASTRO_ID = ?, FLAG_ATIVO = ? WHERE ID = ?", model.getDescricao(), model.getCodigo(), new Timestamp(model.getDataAtualizacao().getTime()), model.getUsuarioAtualizacaoModel().getId(), model.getFlagAtivo(), model.getId());

		broker.execute();

		return model;
	}

	public CidModel excluirCrudModel(final CidModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE CID SET FLAG_ATIVO = FALSE, DATA_CADASTRO = ?, USUARIO_CADASTRO_ID = ? WHERE ID = ?", new Timestamp(model.getDataAtualizacao().getTime()), model.getUsuarioAtualizacaoModel().getId(), model.getId());

		broker.execute();

		return model;
	}
	
	@SuppressWarnings("unchecked")
	public List<CidModel> pesquisarLog(final CidModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT C.ID, C.DESCRICAO, C.CODIGO, C.DATA_CADASTRO, C.USUARIO_CADASTRO_ID, (SELECT U.NOME FROM USUARIO U WHERE U.ID = C.USUARIO_CADASTRO_ID), C.FLAG_ATIVO FROM LOG.CID C WHERE C.ID = ? ORDER BY C.DATA_CADASTRO DESC", model.getId());

		return broker.getCollectionBean(CidModel.class, "id", "descricao", "codigo", "dataCadastro", "usuarioCadastroModel.id", "usuarioCadastroModel.nome", "flagAtivo");
	}
	

}