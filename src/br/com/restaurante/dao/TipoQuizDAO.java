package br.com.restaurante.dao;


import java.sql.Timestamp;
import java.util.List;

import br.com.restaurante.model.FuncaoModel;
import br.com.restaurante.model.TabModel;
import br.com.restaurante.model.TipoQuizModel;
import br.com.restaurante.util.Utilitario;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

public class TipoQuizDAO implements CrudDAO<TipoQuizModel> {

	public TipoQuizModel obterCrudModel(final TipoQuizModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("SELECT ID, DESCRICAO, TAB_ID, FUNCAO_ID, (SELECT F.DESCRICAO FROM FUNCAO F WHERE F.ID = TQ.FUNCAO_ID), RELATORIO, ID_EXTERNO, TIPO_QUIZ_PAI_ID, ORDEM, OBTER_DATA_CADASTRO_LOG(ID, 'TIPO_QUIZ') DATA_CADASTRO, OBTER_USUARIO_CADASTRO_LOG(ID, 'TIPO_QUIZ') USUARIO_CADASTRO, DATA_CADASTRO DATA_ATUALIZACAO, (SELECT NOME FROM USUARIO U1 WHERE U1.ID = TQ.USUARIO_CADASTRO_ID) USUARIO_ATUALIZACAO FROM TIPO_QUIZ TQ WHERE ID = ?", model.getId());

		return (TipoQuizModel) broker.getObjectBean(TipoQuizModel.class, "id", "descricao", "tabModel.id", "funcaoModel.id", "funcaoModel.descricao", "relatorio", "idExterno", "tipoQuizPaiModel.id", "ordem", "dataCadastro", "usuarioCadastroModel.nome", "dataAtualizacao", "usuarioAtualizacaoModel.nome");

	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<TipoQuizModel> pesquisarLog(TipoQuizModel model) {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("SELECT ID, DESCRICAO, TAB_ID, FUNCAO_ID, (SELECT F.DESCRICAO FROM FUNCAO F WHERE F.ID = TQ.FUNCAO_ID), RELATORIO, ID_EXTERNO, TIPO_QUIZ_PAI_ID, ORDEM, DATA_CADASTRO, (SELECT NOME FROM USUARIO U1 WHERE U1.ID = TQ.USUARIO_CADASTRO_ID) FROM LOG.TIPO_QUIZ TQ WHERE ID = ? ORDER BY TQ.DATA_CADASTRO DESC", model.getId());
		
		return broker.getCollectionBean(TipoQuizModel.class, "id", "descricao", "tabModel.id", "funcaoModel.id", "funcaoModel.descricao", "relatorio", "idExterno", "tipoQuizPaiModel.id", "ordem", "dataCadastro", "usuarioCadastroModel.nome");
		
	}
	
	public TipoQuizModel obter(final TabModel tabModel, final FuncaoModel funcaoModel) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("SELECT ID, DESCRICAO, TAB_ID, FUNCAO_ID, (SELECT F.DESCRICAO FROM FUNCAO F WHERE F.ID = TQ.FUNCAO_ID), RELATORIO, TIPO_QUIZ_PAI_ID, ORDEM FROM TIPO_QUIZ TQ WHERE FLAG_ATIVO AND TAB_ID = ? AND COALESCE(FUNCAO_ID, ?) = COALESCE(?, FUNCAO_ID) AND TQ.TIPO_QUIZ_PAI_ID IS NULL ORDER BY TQ.FUNCAO_ID LIMIT 1", tabModel.getId(), funcaoModel.getId(), funcaoModel.getId());

		return (TipoQuizModel) broker.getObjectBean(TipoQuizModel.class, "id", "descricao", "tabModel.id", "funcaoModel.id", "funcaoModel.descricao", "relatorio", "tipoQuizPaiModel.id", "ordem");

	}

	@SuppressWarnings("unchecked")
	public List<TipoQuizModel> pesquisarCrudModel(final TipoQuizModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("SELECT ID, DESCRICAO, (SELECT TP.DESCRICAO FROM TAB TP WHERE TP.ID = TQ.TAB_ID) TAB, (SELECT F.DESCRICAO FROM FUNCAO F WHERE F.ID = TQ.FUNCAO_ID) FUNCAO FROM TIPO_QUIZ TQ WHERE SEM_ACENTOS(DESCRICAO) LIKE SEM_ACENTOS(COALESCE(?, DESCRICAO)) AND COALESCE(TAB_ID, 0) = COALESCE(?, COALESCE(TAB_ID, 0)) AND COALESCE(FUNCAO_ID, 0) = COALESCE(?, COALESCE(FUNCAO_ID, 0)) AND FLAG_ATIVO = ? ORDER BY DESCRICAO", Utilitario.getStringIlike(model.getDescricao(), true), model.getTabModel().getId(), model.getFuncaoModel().getId(), model.getFlagAtivo());

		return broker.getCollectionBean(TipoQuizModel.class, "id", "descricao", "tabModel.descricao", "funcaoModel.descricao");

	}

	public TabModel obterTab(final Long tabId) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("SELECT ID, DESCRICAO FROM TAB WHERE ID = ?", tabId);

		return (TabModel) broker.getObjectBean(TabModel.class, "id", "descricao");

	}

	@Override
	public TipoQuizModel inserirCrudModel(final TipoQuizModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("tipo_quiz_id_seq"));

		broker.setSQL("INSERT INTO TIPO_QUIZ (ID, DESCRICAO, TAB_ID, FUNCAO_ID, RELATORIO, ID_EXTERNO, TIPO_QUIZ_PAI_ID, ORDEM, DATA_CADASTRO, USUARIO_CADASTRO_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", model.getId(), model.getDescricao(), model.getTabModel().getId(), model.getFuncaoModel().getId(), model.getRelatorio(), model.getIdExterno(), model.getTipoQuizPaiModel().getId(), model.getOrdem(), new Timestamp(model.getDataCadastro().getTime()), model.getUsuarioCadastroModel().getId());

		broker.execute();

		return model;

	}

	public TipoQuizModel alterarCrudModel(final TipoQuizModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE TIPO_QUIZ SET DESCRICAO = ?, TAB_ID = ?, FUNCAO_ID = ?, RELATORIO = ?, TIPO_QUIZ_PAI_ID = ?, ORDEM = ?, DATA_CADASTRO = ?, USUARIO_CADASTRO_ID = ? WHERE ID = ?", model.getDescricao(), model.getTabModel().getId(), model.getFuncaoModel().getId(), model.getRelatorio(), model.getTipoQuizPaiModel().getId(), model.getOrdem(), new Timestamp(model.getDataAtualizacao().getTime()), model.getUsuarioAtualizacaoModel().getId(), model.getId());

		broker.execute();

		return model;

	}
	
	public TipoQuizModel excluirCrudModel(final TipoQuizModel model) throws TSApplicationException {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE TIPO_QUIZ SET FLAG_ATIVO = FALSE WHERE ID = ?", model.getId());

		broker.execute();

		return model;
		
	}

}
