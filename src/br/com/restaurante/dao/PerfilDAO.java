package br.com.restaurante.dao;

import java.sql.Timestamp;
import java.util.List;

import br.com.restaurante.model.PerfilModel;
import br.com.restaurante.model.PerfilProcedimentoModel;
import br.com.restaurante.util.Utilitario;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

public class PerfilDAO implements CrudDAO<PerfilModel> {

	@SuppressWarnings("unchecked")
	public List<PerfilModel> pesquisarCrudModel(final PerfilModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT C.ID, C.DESCRICAO, C.CODIGO FROM PERFIL C WHERE SEM_ACENTOS(C.DESCRICAO) ILIKE SEM_ACENTOS(COALESCE(?, C.DESCRICAO)) AND C.FLAG_ATIVO = ? ORDER BY C.DESCRICAO", Utilitario.getStringIlike(model.getDescricao(), true), model.getFlagAtivo());

		return broker.getCollectionBean(PerfilModel.class, "id", "descricao", "codigo");

	}

	public PerfilModel obterCrudModel(final PerfilModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT C.ID, C.DESCRICAO, C.CODIGO, C.DATA_CADASTRO, C.USUARIO_CADASTRO_ID, (SELECT U.NOME FROM USUARIO U WHERE U.ID = C.USUARIO_CADASTRO_ID), C.FLAG_ATIVO FROM PERFIL C WHERE C.ID = ?", model.getId());

		return (PerfilModel) broker.getObjectBean(PerfilModel.class, "id", "descricao", "codigo", "dataCadastro", "usuarioCadastroModel.id", "usuarioCadastroModel.nome", "flagAtivo");
	}

	public PerfilModel inserirCrudModel(final PerfilModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("perfil_id_seq"));

		broker.setSQL("INSERT INTO PERFIL (ID, DESCRICAO, CODIGO, DATA_CADASTRO, USUARIO_CADASTRO_ID, FLAG_ATIVO) VALUES (?, ?, ?, ?, ?, ?)", model.getId(), model.getDescricao(), model.getCodigo(), new Timestamp(model.getDataCadastro().getTime()), model.getUsuarioCadastroModel().getId(), model.getFlagAtivo());

		broker.execute();

		return model;

	}
	
	public void inserir(final PerfilProcedimentoModel model) throws TSApplicationException {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		model.setId(broker.getSequenceNextValue("perfil_procedimento_id_seq"));
		
		broker.setSQL("INSERT INTO PERFIL_PROCEDIMENTO (ID, PERFIL_ID, PROCEDIMENTO_ID) VALUES (?, ?, ?)", model.getId(), model.getPerfilModel().getId(), model.getProcedimentoModel().getId());
		
		broker.execute();
		
	}
	
	public void excluir(final PerfilProcedimentoModel model) throws TSApplicationException {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("DELETE FROM PERFIL_PROCEDIMENTO WHERE ID = ?", model.getId());
		
		broker.execute();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<PerfilProcedimentoModel> pesquisarProcedimentos(final PerfilModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT PP.ID, PP.PERFIL_ID, PP.PROCEDIMENTO_ID, (SELECT P.DESCRICAO FROM PROCEDIMENTO P WHERE P.ID = PP.PROCEDIMENTO_ID) FROM PERFIL_PROCEDIMENTO PP WHERE PP.PERFIL_ID = ? ORDER BY PP.ID", model.getId());

		return broker.getCollectionBean(PerfilProcedimentoModel.class, "id", "perfilModel.id", "procedimentoModel.id", "procedimentoModel.descricao");

	}
	
	public PerfilModel alterarCrudModel(final PerfilModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE PERFIL SET DESCRICAO = ?, CODIGO = ?, DATA_CADASTRO = ?, USUARIO_CADASTRO_ID = ?, FLAG_ATIVO = ? WHERE ID = ?", model.getDescricao(), model.getCodigo(), new Timestamp(model.getDataAtualizacao().getTime()), model.getUsuarioAtualizacaoModel().getId(), model.getFlagAtivo(), model.getId());

		broker.execute();

		return model;
	}

	public PerfilModel excluirCrudModel(final PerfilModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE PERFIL SET FLAG_ATIVO = FALSE, DATA_CADASTRO = ?, USUARIO_CADASTRO_ID = ? WHERE ID = ?", new Timestamp(model.getDataAtualizacao().getTime()), model.getUsuarioAtualizacaoModel().getId(), model.getId());

		broker.execute();

		return model;
	}
	
	@SuppressWarnings("unchecked")
	public List<PerfilModel> pesquisarLog(final PerfilModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT C.ID, C.DESCRICAO, C.CODIGO, C.DATA_CADASTRO, C.USUARIO_CADASTRO_ID, (SELECT U.NOME FROM USUARIO U WHERE U.ID = C.USUARIO_CADASTRO_ID), C.FLAG_ATIVO FROM LOG.PERFIL C WHERE C.ID = ? ORDER BY C.DATA_CADASTRO DESC", model.getId());

		return broker.getCollectionBean(PerfilModel.class, "id", "descricao", "codigo", "dataCadastro", "usuarioCadastroModel.id", "usuarioCadastroModel.nome", "flagAtivo");
	}
	

}