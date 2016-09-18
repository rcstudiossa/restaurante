/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.restaurante.dao;

import java.sql.Timestamp;
import java.util.List;

import br.com.restaurante.model.ProcedimentoModel;
import br.com.restaurante.util.Utilitario;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

public final class ProcedimentoDAO implements CrudDAO<ProcedimentoModel> {

	public ProcedimentoModel obterCrudModel(final ProcedimentoModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, CODIGO, DESCRICAO, PROCEDIMENTO_ID, ORDEM, FLAG_ATIVO, FLAG_GRUPO, OBTER_DATA_CADASTRO_LOG(ID, 'PROCEDIMENTO'), OBTER_USUARIO_CADASTRO_LOG(ID, 'PROCEDIMENTO'), DATA_CADASTRO, (SELECT U.NOME FROM USUARIO U WHERE U.ID = P.USUARIO_CADASTRO_ID) FROM PROCEDIMENTO P WHERE ID = ?", model.getId());

		return (ProcedimentoModel) broker.getObjectBean(ProcedimentoModel.class, "id", "codigo", "descricao", "procedimentoModel.id", "ordem", "flagAtivo", "flagGrupo", "dataCadastro", "usuarioCadastroModel.nome", "dataAtualizacao", "usuarioAtualizacaoModel.nome");

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ProcedimentoModel> pesquisarLog(ProcedimentoModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, CODIGO, DESCRICAO, PROCEDIMENTO_ID, ORDEM, FLAG_ATIVO, FLAG_GRUPO, P.DATA_CADASTRO, (SELECT U.NOME FROM USUARIO U WHERE U.ID = P.USUARIO_CADASTRO_ID) FROM LOG.PROCEDIMENTO P WHERE ID = ? ORDER BY ID DESC", model.getId());

		return broker.getCollectionBean(ProcedimentoModel.class, "id", "codigo", "descricao", "procedimentoModel.id", "ordem", "flagAtivo", "flagGrupo", "dataCadastro", "usuarioCadastroModel.nome");
	}

	@SuppressWarnings("unchecked")
	public List<ProcedimentoModel> pesquisarCrudModel(final ProcedimentoModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, CODIGO, DESCRICAO, PROCEDIMENTO_ID, (SELECT P2.DESCRICAO FROM PROCEDIMENTO P2 WHERE P2.ID = P.PROCEDIMENTO_ID), ORDEM FROM PROCEDIMENTO P WHERE SEM_ACENTOS(DESCRICAO) ILIKE COALESCE(?, SEM_ACENTOS(DESCRICAO)) AND COALESCE(P.PROCEDIMENTO_ID, 0) = COALESCE(?, COALESCE(P.PROCEDIMENTO_ID, 0)) AND P.FLAG_ATIVO = ? ORDER BY P.DESCRICAO", Utilitario.getStringIlike(model.getDescricao(), true), model.getProcedimentoModel().getId(), model.getFlagAtivo());

		return broker.getCollectionBean(ProcedimentoModel.class, "id", "codigo", "descricao", "procedimentoModel.id", "procedimentoModel.descricao", "ordem");

	}

	public ProcedimentoModel inserirCrudModel(final ProcedimentoModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("procedimento_id_seq"));

		broker.setSQL("INSERT INTO PROCEDIMENTO (ID, CODIGO, DESCRICAO, PROCEDIMENTO_ID, ORDEM, FLAG_ATIVO, FLAG_GRUPO, DATA_CADASTRO, USUARIO_CADASTRO_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", model.getId(), model.getCodigo(), model.getDescricao(), model.getProcedimentoModel().getId(), model.getOrdem(), model.getFlagAtivo(), model.getFlagGrupo(), new Timestamp(model.getDataCadastro().getTime()), model.getUsuarioCadastroModel().getId());

		broker.execute();

		return model;

	}

	public ProcedimentoModel alterarCrudModel(final ProcedimentoModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE PROCEDIMENTO SET CODIGO = ?, DESCRICAO = ?, PROCEDIMENTO_ID = ?, ORDEM = ?, FLAG_ATIVO = ?, FLAG_GRUPO = ?, DATA_CADASTRO = ?, USUARIO_CADASTRO_ID = ? WHERE ID = ?", model.getCodigo(), model.getDescricao(), model.getProcedimentoModel().getId(), model.getOrdem(), model.getFlagAtivo(), model.getFlagGrupo(), new Timestamp(model.getDataAtualizacao().getTime()), model.getUsuarioAtualizacaoModel().getId(), model.getId());

		broker.execute();

		return model;

	}

	public ProcedimentoModel excluirCrudModel(final ProcedimentoModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE PROCEDIMENTO SET FLAG_ATIVO = FALSE, DATA_CADASTRO = ?, USUARIO_CADASTRO_ID = ? WHERE ID = ?", new Timestamp(model.getDataAtualizacao().getTime()), model.getUsuarioAtualizacaoModel().getId(), model.getId());

		broker.execute();

		return model;

	}

	@SuppressWarnings("unchecked")
	public List<ProcedimentoModel> pesquisarAutoComplete(String texto) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, CODIGO, DESCRICAO, 'PROCEDIMENTO' FROM PROCEDIMENTO P WHERE (P.CODIGO ILIKE ? OR SEM_ACENTOS(P.DESCRICAO) LIKE SEM_ACENTOS(?)) AND P.FLAG_ATIVO = TRUE UNION ALL SELECT P.ID, NULL, DESCRICAO, 'PERFIL' FROM PERFIL P WHERE SEM_ACENTOS(P.DESCRICAO) LIKE SEM_ACENTOS(?) ORDER BY DESCRICAO", Utilitario.getStringIlike(texto, false), Utilitario.getStringIlike(texto, true), Utilitario.getStringIlike(texto, true));

		return broker.getCollectionBean(ProcedimentoModel.class, "id", "codigo", "descricao", "tipo");

	}

}
