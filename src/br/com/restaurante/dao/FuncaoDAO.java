package br.com.restaurante.dao;

import java.sql.Timestamp;
import java.util.List;

import br.com.restaurante.model.FuncaoModel;
import br.com.restaurante.model.OrigemModel;
import br.com.restaurante.util.Utilitario;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

public final class FuncaoDAO implements CrudDAO<FuncaoModel> {

	public FuncaoModel obterCrudModel(final FuncaoModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO, FLAG_ATIVO, OBTER_DATA_CADASTRO_LOG(ID, 'FUNCAO') DATA_CADASTRO, OBTER_USUARIO_CADASTRO_LOG(ID, 'FUNCAO') USUARIO_CADASTRO, DATA_CADASTRO, (SELECT NOME FROM USUARIO U WHERE U.ID = F.USUARIO_CADASTRO_ID), MENU_INICIAL_ID, (SELECT URL FROM MENU M WHERE M.ID = MENU_INICIAL_ID), (SELECT M.MENU_ID FROM MENU M WHERE M.ID = MENU_INICIAL_ID), FLAG_PERMISSAO_LOGIN FROM FUNCAO F WHERE ID = ?", model.getId());

		return (FuncaoModel) broker.getObjectBean(FuncaoModel.class, "id", "descricao", "flagAtivo", "dataCadastro", "usuarioCadastroModel.nome", "dataAtualizacao", "usuarioAtualizacaoModel.nome", "menuInicial.id", "menuInicial.url", "menuInicial.menuModel.id", "flagPermissaoLogin");

	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<FuncaoModel> pesquisarLog(FuncaoModel model) {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO, FLAG_ATIVO, OBTER_DATA_CADASTRO_LOG(ID, 'FUNCAO') DATA_CADASTRO, OBTER_USUARIO_CADASTRO_LOG(ID, 'FUNCAO') USUARIO_CADASTRO, DATA_CADASTRO, (SELECT NOME FROM USUARIO U WHERE U.ID = F.USUARIO_CADASTRO_ID), MENU_INICIAL_ID, (SELECT URL FROM MENU M WHERE M.ID = MENU_INICIAL_ID), (SELECT M.MENU_ID FROM MENU M WHERE M.ID = MENU_INICIAL_ID), FLAG_PERMISSAO_LOGIN FROM LOG.FUNCAO F WHERE ID = ? ORDER BY F.DATA_CADASTRO DESC", model.getId());

		return broker.getCollectionBean(FuncaoModel.class, "id", "descricao", "flagAtivo", "dataCadastro", "usuarioCadastroModel.nome", "dataAtualizacao", "usuarioAtualizacaoModel.nome", "menuInicial.id", "menuInicial.url", "menuInicial.menuModel.id", "flagPermissaoLogin");
		
	}

	public FuncaoModel obterSimples(final FuncaoModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO FROM FUNCAO F WHERE ID = ?", model.getId());

		return (FuncaoModel) broker.getObjectBean(FuncaoModel.class, "id", "descricao");

	}

	@SuppressWarnings("unchecked")
	public List<FuncaoModel> pesquisarCrudModel(final FuncaoModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO, FLAG_ATIVO FROM FUNCAO WHERE SEM_ACENTOS(DESCRICAO) LIKE SEM_ACENTOS(COALESCE(?, DESCRICAO)) AND FLAG_ATIVO = COALESCE(?, FLAG_ATIVO) ORDER BY FLAG_ATIVO, DESCRICAO", Utilitario.getStringIlike(model.getDescricao(), true), model.getFlagAtivo());

		return broker.getCollectionBean(FuncaoModel.class, "id", "descricao", "flagAtivo");

	}

	@SuppressWarnings("unchecked")
	public List<FuncaoModel> pesquisarPorOrigem(final FuncaoModel model, OrigemModel origem) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT F.ID, F.DESCRICAO, F.FLAG_ATIVO FROM FUNCAO F WHERE F.FLAG_ATIVO = COALESCE(?, F.FLAG_ATIVO) AND EXISTS (SELECT 1 FROM FUNCAO_ORIGEM FO WHERE FO.FUNCAO_ID = F.ID AND FO.ORIGEM_ID = ?) AND EXISTS(SELECT 1 FROM USUARIO_FUNCAO UF WHERE UF.FUNCAO_ID = F.ID AND UF.ORIGEM_ID = ?) ORDER BY F.FLAG_ATIVO, F.DESCRICAO", model.getFlagAtivo(), origem.getId(), origem.getId());

		return broker.getCollectionBean(FuncaoModel.class, "id", "descricao", "flagAtivo");

	}
	
	public FuncaoModel inserirCrudModel(final FuncaoModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("funcao_id_seq"));

		broker.setSQL("INSERT INTO FUNCAO (ID, DESCRICAO, FLAG_ATIVO, DATA_CADASTRO, USUARIO_CADASTRO_ID, MENU_INICIAL_ID, FLAG_PERMISSAO_LOGIN) VALUES (?, ?, ?, ?, ?, ?, ?)", model.getId(), model.getDescricao(), model.getFlagAtivo(), new Timestamp(model.getDataCadastro().getTime()), model.getUsuarioCadastroModel().getId(), model.getMenuInicial().getId(), model.getFlagPermissaoLogin());

		broker.execute();

		return model;

	}

	public FuncaoModel alterarCrudModel(final FuncaoModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE FUNCAO SET DESCRICAO = ?, FLAG_ATIVO = ?, MENU_INICIAL_ID = ?, DATA_CADASTRO = ?, USUARIO_CADASTRO_ID = ?, FLAG_PERMISSAO_LOGIN = ? WHERE ID = ?", model.getDescricao(), model.getFlagAtivo(), model.getMenuInicial().getId(), new Timestamp(model.getDataAtualizacao().getTime()), model.getUsuarioAtualizacaoModel().getId(), model.getFlagPermissaoLogin(), model.getId());

		broker.execute();

		return model;

	}

	public FuncaoModel excluirCrudModel(final FuncaoModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("DELETE FROM FUNCAO WHERE ID = ?", model.getId());

		broker.execute();

		return model;

	}

}
