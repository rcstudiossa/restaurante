package br.com.restaurante.dao;

import java.sql.Timestamp;
import java.util.List;

import br.com.restaurante.model.FuncaoModel;
import br.com.restaurante.model.TabModel;
import br.com.restaurante.model.TemplateModel;
import br.com.restaurante.model.TemplateTabModel;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

public final class TemplateDAO implements CrudDAO<TemplateModel> {

	public TemplateModel obterCrudModel(final TemplateModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT TP.ID, TP.FUNCAO_ID, F.DESCRICAO, OBTER_DATA_CADASTRO_LOG(TP.ID, 'TEMPLATE'), OBTER_USUARIO_CADASTRO_LOG(TP.ID, 'TEMPLATE'), TP.DATA_CADASTRO, (SELECT U.NOME FROM USUARIO U WHERE U.ID = TP.USUARIO_CADASTRO_ID) FROM TEMPLATE TP INNER JOIN FUNCAO F ON F.ID = TP.FUNCAO_ID WHERE TP.ID = ?", model.getId());

		return (TemplateModel) broker.getObjectBean(TemplateModel.class, "id", "funcaoModel.id", "funcaoModel.descricao", "dataCadastro", "usuarioCadastroModel.nome", "dataAtualizacao", "usuarioAtualizacaoModel.nome");

	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<TemplateModel> pesquisarLog(TemplateModel model) {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT TP.ID, TP.FUNCAO_ID, F.DESCRICAO, TP.DATA_CADASTRO, (SELECT U.NOME FROM USUARIO U WHERE U.ID = TP.USUARIO_CADASTRO_ID) FROM LOG.TEMPLATE TP INNER JOIN FUNCAO F ON F.ID = TP.FUNCAO_ID WHERE TP.ID = ? ORDER BY TP.DATA_CADASTRO DESC", model.getId());

		return broker.getCollectionBean(TemplateModel.class, "id", "funcaoModel.id", "funcaoModel.descricao", "dataCadastro", "usuarioCadastroModel.nome");
		
	}

	public TemplateModel obterPadrao() {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("SELECT TP.ID, TP.FUNCAO_ID, F.DESCRICAO FROM TEMPLATE TP INNER JOIN FUNCAO F ON F.ID = TP.FUNCAO_ID WHERE TP.FLAG_PADRAO");
		
		return (TemplateModel) broker.getObjectBean(TemplateModel.class, "id", "funcaoModel.id", "funcaoModel.descricao");
		
	}

	@SuppressWarnings("unchecked")
	public List<TemplateModel> pesquisarCrudModel(final TemplateModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT TP.ID, TP.FUNCAO_ID, F.DESCRICAO, F.DESCRICAO FROM TEMPLATE TP INNER JOIN FUNCAO F ON F.ID = TP.FUNCAO_ID WHERE TP.FUNCAO_ID = COALESCE(?, TP.FUNCAO_ID) ORDER BY F.DESCRICAO", model.getFuncaoModel().getId());

		return broker.getCollectionBean(TemplateModel.class, "id", "funcaoModel.id", "funcaoModel.descricao", "descricao");

	}

	public TemplateModel inserirCrudModel(final TemplateModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("template_id_seq"));

		broker.setSQL("INSERT INTO TEMPLATE (ID, FUNCAO_ID, DATA_CADASTRO, USUARIO_CADASTRO_ID) VALUES (?, ?, ?, ?)", model.getId(), model.getFuncaoModel().getId(), new Timestamp(model.getDataCadastro().getTime()), model.getUsuarioCadastroModel().getId());

		broker.execute();

		return model;

	}

	public TemplateModel alterarCrudModel(final TemplateModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE TEMPLATE SET FUNCAO_ID = ?, DATA_CADASTRO = ?, USUARIO_CADASTRO_ID = ? WHERE ID = ?", model.getFuncaoModel().getId(), new Timestamp(model.getDataAtualizacao().getTime()), model.getUsuarioAtualizacaoModel().getId(), model.getId());

		broker.execute();

		return model;

	}

	@Override
	public TemplateModel excluirCrudModel(TemplateModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("DELETE FROM TEMPLATE WHERE ID = ?", model.getId());

		broker.execute();

		return model;
	}

	@SuppressWarnings("unchecked")
	public List<TemplateTabModel> pesquisarTabs(final TemplateModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT TPT.ID, TPT.TEMPLATE_ID, TPT.TAB_ID, TP.DESCRICAO, TP.URL, TP.FLAG_ATIVO, TP.TITULO, EXISTS(SELECT 1 FROM TAB TP2 WHERE TP2.TAB_ID = TP.ID), COALESCE(TPT.FLAG_CRIAR, FALSE), COALESCE(TPT.FLAG_COPIAR, FALSE), TP.FLAG_GENERICA, TP.FACES_RESET, COALESCE(TPT.FLAG_IMPRESSAO_AUTOMATICA, FALSE), COALESCE(TPT.FLAG_PULAR_TAB, FALSE), COALESCE(TPT2.FLAG_INSERIR, FALSE) FLAG_INSERIR, COALESCE(TPT2.FLAG_ALTERAR, FALSE) FLAG_ALTERAR, COALESCE(TPT2.FLAG_EXCLUIR, FALSE) FLAG_EXCLUIR, COALESCE(TPT2.FLAG_CONCLUIR, FALSE) FLAG_CONCLUIR, COALESCE(TPT2.FLAG_IMPRIMIR, FALSE) FLAG_IMPRIMIR, COALESCE(TPT2.FLAG_PERMISSAO_MOVIMENTACAO_FECHADA, FALSE) FLAG_PERMISSAO_MOVIMENTACAO_FECHADA, TPT.ORDEM, TPT.VALIDACAO, TPT.FLAG_EDITAR_RETROATIVO FROM TEMPLATE_TAB TPT INNER JOIN TAB TP ON TP.ID = TPT.TAB_ID LEFT JOIN TEMPLATE_TAB TPT2 ON TPT2.TAB_ID = TPT.TAB_ID AND EXISTS (SELECT 1 FROM TEMPLATE TP2 WHERE TP2.ID = TPT2.TEMPLATE_ID AND TP2.FUNCAO_ID = ?) WHERE TPT.TEMPLATE_ID = ? ORDER BY TPT.ORDEM", model.getFuncaoModel().getId(), model.getId());

		return broker.getCollectionBean(TemplateTabModel.class, "id", "templateModel.id", "tabModel.id", "tabModel.descricao", "tabModel.url", "tabModel.flagAtivo", "tabModel.titulo", "tabModel.flagPossuiFilhos", "flagCriar", "flagCopiar", "tabModel.flagGenerica", "tabModel.facesReset", "flagImpressaoAutomatica", "flagPularTab", "flagInserir", "flagAlterar", "flagExcluir", "flagConcluir", "flagImprimir", "flagPermissaoMovimentacaoFechada", "ordem", "validacao", "flagEditarRetroativo");

	}
	
	@SuppressWarnings("unchecked")
	public List<TemplateTabModel> pesquisarTabs(final TemplateModel model, FuncaoModel funcaoUsuarioModel) {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("SELECT TPT.ID, TPT.TEMPLATE_ID, TPT.TAB_ID, TP.DESCRICAO, TP.URL, TP.FLAG_ATIVO, TP.TITULO, EXISTS(SELECT 1 FROM TAB TP2 WHERE TP2.TAB_ID = TP.ID), COALESCE(TPT.FLAG_CRIAR, FALSE), COALESCE(TPT.FLAG_COPIAR, FALSE), TP.FLAG_GENERICA, TP.FACES_RESET, COALESCE(TPT.FLAG_IMPRESSAO_AUTOMATICA, FALSE), COALESCE(TPT.FLAG_PULAR_TAB, FALSE), COALESCE(TPT2.FLAG_INSERIR, FALSE) FLAG_INSERIR, COALESCE(TPT2.FLAG_ALTERAR, FALSE) FLAG_ALTERAR, COALESCE(TPT2.FLAG_EXCLUIR, FALSE) FLAG_EXCLUIR, COALESCE(TPT2.FLAG_CONCLUIR, FALSE) FLAG_CONCLUIR, COALESCE(TPT2.FLAG_IMPRIMIR, FALSE) FLAG_IMPRIMIR, COALESCE(TPT2.FLAG_PERMISSAO_MOVIMENTACAO_FECHADA, FALSE) FLAG_PERMISSAO_MOVIMENTACAO_FECHADA, TPT.ORDEM, TPT.VALIDACAO, TPT.FLAG_EDITAR_RETROATIVO FROM TEMPLATE_TAB TPT INNER JOIN TAB TP ON TP.ID = TPT.TAB_ID LEFT JOIN TEMPLATE_TAB TPT2 ON TPT2.TAB_ID = TPT.TAB_ID AND EXISTS (SELECT 1 FROM TEMPLATE TP2 WHERE TP2.ID = TPT2.TEMPLATE_ID AND TP2.FUNCAO_ID = ?) WHERE TPT.TEMPLATE_ID = ? AND TP.TAB_ID IS NULL ORDER BY TPT.ORDEM", funcaoUsuarioModel.getId(), model.getId());
		
		return broker.getCollectionBean(TemplateTabModel.class, "id", "templateModel.id", "tabModel.id", "tabModel.descricao", "tabModel.url", "tabModel.flagAtivo", "tabModel.titulo", "tabModel.flagPossuiFilhos", "flagCriar", "flagCopiar", "tabModel.flagGenerica", "tabModel.facesReset", "flagImpressaoAutomatica", "flagPularTab", "flagInserir", "flagAlterar", "flagExcluir", "flagConcluir", "flagImprimir", "flagPermissaoMovimentacaoFechada", "ordem", "validacao", "flagEditarRetroativo");
		
	}
	
	@SuppressWarnings("unchecked")
	public List<TemplateTabModel> pesquisarTabsFilhas(final TemplateModel model, FuncaoModel funcaoUsuarioModel, TabModel tabPai) {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("SELECT TPT.ID, TPT.TEMPLATE_ID, TPT.TAB_ID, TP.DESCRICAO, TP.URL, TP.FLAG_ATIVO, TP.TITULO, EXISTS(SELECT 1 FROM TAB TP2 WHERE TP2.TAB_ID = TP.ID), COALESCE(TPT.FLAG_CRIAR, FALSE), COALESCE(TPT.FLAG_COPIAR, FALSE), TP.FLAG_GENERICA, TP.FACES_RESET, COALESCE(TPT.FLAG_IMPRESSAO_AUTOMATICA, FALSE), COALESCE(TPT.FLAG_PULAR_TAB, FALSE), COALESCE(TPT2.FLAG_INSERIR, FALSE) FLAG_INSERIR, COALESCE(TPT2.FLAG_ALTERAR, FALSE) FLAG_ALTERAR, COALESCE(TPT2.FLAG_EXCLUIR, FALSE) FLAG_EXCLUIR, COALESCE(TPT2.FLAG_CONCLUIR, FALSE) FLAG_CONCLUIR, COALESCE(TPT2.FLAG_IMPRIMIR, FALSE) FLAG_IMPRIMIR, COALESCE(TPT2.FLAG_PERMISSAO_MOVIMENTACAO_FECHADA, FALSE) FLAG_PERMISSAO_MOVIMENTACAO_FECHADA, TPT.ORDEM, TPT.VALIDACAO, TPT.FLAG_EDITAR_RETROATIVO FROM TEMPLATE_TAB TPT INNER JOIN TAB TP ON TP.ID = TPT.TAB_ID LEFT JOIN TEMPLATE_TAB TPT2 ON TPT2.TAB_ID = TPT.TAB_ID AND EXISTS (SELECT 1 FROM TEMPLATE TP2 WHERE TP2.ID = TPT2.TEMPLATE_ID AND TP2.FUNCAO_ID = ? WHERE TPT.TEMPLATE_ID = ? AND TP.TAB_ID = ? ORDER BY TPT.ORDEM", funcaoUsuarioModel.getId(), model.getId(), tabPai.getId());
		
		return broker.getCollectionBean(TemplateTabModel.class, "id", "templateModel.id", "tabModel.id", "tabModel.descricao", "tabModel.url", "tabModel.flagAtivo", "tabModel.titulo", "tabModel.flagPossuiFilhos", "flagCriar", "flagCopiar", "tabModel.flagGenerica", "tabModel.facesReset", "flagImpressaoAutomatica", "flagPularTab", "flagInserir", "flagAlterar", "flagExcluir", "flagConcluir", "flagImprimir", "flagPermissaoMovimentacaoFechada", "ordem", "validacao", "flagEditarRetroativo");
		
	}
	
	@SuppressWarnings("unchecked")
	public List<TemplateTabModel> pesquisarOutrasTabs(final TemplateModel model, final Long atendimentoId) {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("SELECT DISTINCT TP.ID, TP.DESCRICAO, TP.URL, TP.FLAG_ATIVO, TP.TITULO, FALSE, FALSE, TP.FLAG_GENERICA, TP.FACES_RESET, FALSE, FALSE, FALSE, TRUE, FALSE, FALSE, (SELECT TPT2.ORDEM FROM TEMPLATE_TAB TPT2 WHERE TPT2.TAB_ID = TP.ID ORDER BY TPT2.ID DESC LIMIT 1) FROM TEMPLATE_TAB TPT, TAB TP WHERE TP.ID = TPT.TAB_ID AND NOT EXISTS (SELECT 1 FROM TEMPLATE_TAB TPT2 WHERE TPT2.TAB_ID = TP.ID AND TPT2.TEMPLATE_ID = ?) AND FC_POSSUI_INFORMACOES_PEP(TP.ID, ?) ORDER BY 12", model.getId(), atendimentoId);
		
		return broker.getCollectionBean(TemplateTabModel.class, "tabModel.id", "tabModel.descricao", "tabModel.url", "tabModel.flagAtivo", "tabModel.titulo", "flagCriar", "flagCopiar", "tabModel.flagGenerica", "tabModel.facesReset", "flagInserir", "flagAlterar", "flagExcluir", "flagImprimir", "flagPermissaoMovimentacaoFechada", "flagEditarRetroativo");
		
	}

	@SuppressWarnings("unchecked")
	public List<TabModel> pesquisarTabs() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO FROM TAB WHERE FLAG_ATIVO ORDER BY DESCRICAO");

		return broker.getCollectionBean(TabModel.class, "id", "descricao");

	}

	public void excluirTab(TemplateTabModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("DELETE FROM TEMPLATE_TAB WHERE ID = ?", model.getId());

		broker.execute();

	}

	public void inserirTab(TemplateTabModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("template_tab_id_seq"));

		broker.setSQL("INSERT INTO TEMPLATE_TAB (ID, TEMPLATE_ID, TAB_ID, FLAG_INSERIR, FLAG_ALTERAR, FLAG_EXCLUIR, FLAG_CONCLUIR, FLAG_IMPRIMIR, FLAG_PERMISSAO_MOVIMENTACAO_FECHADA, ORDEM, VALIDACAO, FLAG_CRIAR, FLAG_COPIAR, FLAG_PULAR_TAB, FLAG_IMPRESSAO_AUTOMATICA, FLAG_EDITAR_RETROATIVO) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", model.getId(), model.getTemplateModel().getId(), model.getTabModel().getId(), model.getFlagInserir(), model.getFlagAlterar(), model.getFlagExcluir(), model.getFlagConcluir(), model.getFlagImprimir(), model.getFlagPermissaoMovimentacaoFechada(), model.getOrdem(), model.getValidacao(), model.getFlagCriar(), model.getFlagCopiar(), model.getFlagPularTab(), model.getFlagImpressaoAutomatica(), model.getFlagEditarRetroativo());

		broker.execute();

	}

	public void alterarTab(TemplateTabModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE TEMPLATE_TAB SET FLAG_INSERIR = ?, FLAG_ALTERAR = ?, FLAG_EXCLUIR = ?, FLAG_CONCLUIR = ?, FLAG_IMPRIMIR = ?, FLAG_PERMISSAO_MOVIMENTACAO_FECHADA = ?, ORDEM = ?, VALIDACAO = ?, FLAG_CRIAR = ?, FLAG_COPIAR = ?, FLAG_PULAR_TAB = ?, FLAG_IMPRESSAO_AUTOMATICA = ?, FLAG_EDITAR_RETROATIVO = ? WHERE ID = ?", model.getFlagInserir(), model.getFlagAlterar(), model.getFlagExcluir(), model.getFlagConcluir(), model.getFlagImprimir(), model.getFlagPermissaoMovimentacaoFechada(), model.getOrdem(), model.getValidacao(), model.getFlagCriar(), model.getFlagCopiar(), model.getFlagPularTab(), model.getFlagImpressaoAutomatica(), model.getFlagEditarRetroativo(), model.getId());

		broker.execute();

	}
	
	public TemplateModel obterDinamico(final FuncaoModel funcaoModel) {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("SELECT TP.ID, TP.FUNCAO_ID, F.DESCRICAO FROM TEMPLATE TP INNER JOIN FUNCAO F ON F.ID = TP.FUNCAO_ID WHERE TP.FUNCAO_ID = ? ", funcaoModel.getId());
		
		return (TemplateModel) broker.getObjectBean(TemplateModel.class, "id", "funcaoModel.id", "funcaoModel.descricao");
		
	}

}
