package br.com.restaurante.dao;

import java.sql.Timestamp;
import java.util.List;

import br.com.restaurante.model.TabModel;
import br.com.restaurante.util.Utilitario;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

public final class TabDAO implements CrudDAO<TabModel> {

	public TabModel obterCrudModel(final TabModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO, URL, FLAG_ATIVO, TITULO, FACES_RESET, FLAG_GENERICA, TAB_ID, OBTER_DATA_CADASTRO_LOG(ID, 'TAB'), OBTER_USUARIO_CADASTRO_LOG(ID, 'TAB'), DATA_CADASTRO, (SELECT U.NOME FROM USUARIO U WHERE U.ID = T.USUARIO_CADASTRO_ID) FROM TAB T WHERE ID = ?", model.getId());

		return (TabModel) broker.getObjectBean(TabModel.class, "id", "descricao", "url", "flagAtivo", "titulo", "facesReset", "flagGenerica", "tabModel.id", "dataCadastro", "usuarioCadastroModel.nome", "dataAtualizacao", "usuarioAtualizacaoModel.nome");

	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<TabModel> pesquisarLog(TabModel model) {
	
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO, URL, FLAG_ATIVO, TITULO, FACES_RESET, FLAG_GENERICA, TAB_ID, DATA_CADASTRO, (SELECT U.NOME FROM USUARIO U WHERE U.ID = T.USUARIO_CADASTRO_ID) FROM LOG.TAB T WHERE ID = ? ORDER BY DATA_CADASTRO DESC", model.getId());

		return broker.getCollectionBean(TabModel.class, "id", "descricao", "url", "flagAtivo", "titulo", "facesReset", "flagGenerica", "tabModel.id", "dataCadastro", "usuarioCadastroModel.nome");

	}

	@SuppressWarnings("unchecked")
	public List<TabModel> pesquisarCrudModel(final TabModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO, URL, FLAG_ATIVO, TITULO, FACES_RESET, TAB_ID FROM TAB WHERE SEM_ACENTOS(DESCRICAO) LIKE SEM_ACENTOS(COALESCE(?, DESCRICAO)) AND COALESCE(TAB_ID, 0) = COALESCE(?, COALESCE(TAB_ID, 0)) ORDER BY DESCRICAO", Utilitario.getStringIlike(model.getDescricao(), true), model.getTabModel().getId());

		return broker.getCollectionBean(TabModel.class, "id", "descricao", "url", "flagAtivo", "titulo", "facesReset", "tabModel.id");

	}

	public TabModel inserirCrudModel(final TabModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("tab_id_seq"));

		broker.setSQL("INSERT INTO TAB (ID, DESCRICAO, URL, FLAG_ATIVO, TITULO, FACES_RESET, FLAG_GENERICA, TAB_ID, DATA_CADASTRO, USUARIO_CADASTRO_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", model.getId(), model.getDescricao(), model.getUrl(), model.getFlagAtivo(), model.getTitulo(), model.getFacesReset(), model.getFlagGenerica(), model.getTabModel().getId(), new Timestamp(model.getDataCadastro().getTime()), model.getUsuarioCadastroModel().getId());

		broker.execute();

		return model;

	}

	public TabModel alterarCrudModel(final TabModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE TAB SET DESCRICAO = ?, URL = ?, FLAG_ATIVO = ?, TITULO = ?, FACES_RESET = ?, FLAG_GENERICA = ?, TAB_ID = ?, DATA_CADASTRO = ?, USUARIO_CADASTRO_ID = ? WHERE ID = ?", model.getDescricao(), model.getUrl(), model.getFlagAtivo(), model.getTitulo(), model.getFacesReset(), model.getFlagGenerica(), model.getTabModel().getId(), new Timestamp(model.getDataAtualizacao().getTime()), model.getUsuarioAtualizacaoModel().getId(), model.getId());

		broker.execute();

		return model;

	}

	@Override
	public TabModel excluirCrudModel(TabModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("DELETE FROM TAB WHERE ID = ?", model.getId());

		broker.execute();

		return model;
	}


}
