package br.com.restaurante.dao;

import java.sql.Timestamp;
import java.util.List;

import br.com.restaurante.model.FuncaoModel;
import br.com.restaurante.model.TipoSolicitacaoDocumentoFuncaoModel;
import br.com.restaurante.model.TipoSolicitacaoDocumentoModel;
import br.com.restaurante.util.Utilitario;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

public class TipoSolicitacaoDocumentoDAO implements CrudDAO<TipoSolicitacaoDocumentoModel> {

	public TipoSolicitacaoDocumentoModel obterCrudModel(final TipoSolicitacaoDocumentoModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT TSD.ID, OBTER_DATA_CADASTRO_LOG(TSD.ID, 'TIPO_SOLICITACAO_DOCUMENTO') DATA_CADASTRO, OBTER_USUARIO_CADASTRO_LOG(TSD.ID, 'TIPO_SOLICITACAO_DOCUMENTO') USUARIO_CADASTRO, TSD.DATA_CADASTRO, U.NOME, TSD.DESCRICAO, TSD.REFERENCIA, TSD.FLAG_EDITAVEL, TSD.FLAG_CONCLUIR, TSD.FLAG_ATIVO, TSD.TIPO_QUIZ_ID FROM TIPO_SOLICITACAO_DOCUMENTO TSD, USUARIO U WHERE U.ID = TSD.USUARIO_CADASTRO_ID AND TSD.ID = ?", model.getId());

		return (TipoSolicitacaoDocumentoModel) broker.getObjectBean(TipoSolicitacaoDocumentoModel.class, "id", "dataCadastro", "usuarioCadastroModel.nome", "dataAtualizacao", "usuarioAtualizacaoModel.nome", "descricao", "referencia", "flagEditavel", "flagConcluir", "flagAtivo", "tipoQuizModel.id");

	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<TipoSolicitacaoDocumentoModel> pesquisarLog(TipoSolicitacaoDocumentoModel model) {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT TSD.ID, TSD.DATA_CADASTRO, U.NOME, TSD.DESCRICAO, TSD.REFERENCIA, TSD.FLAG_EDITAVEL, TSD.FLAG_CONCLUIR, TSD.FLAG_ATIVO, TSD.TIPO_QUIZ_ID FROM LOG.TIPO_SOLICITACAO_DOCUMENTO TSD, USUARIO U WHERE U.ID = TSD.USUARIO_CADASTRO_ID AND TSD.ID = ? ORDER BY TSD.DATA_CADASTRO DESC", model.getId());

		return broker.getCollectionBean(TipoSolicitacaoDocumentoModel.class, "id", "dataCadastro", "usuarioCadastroModel.nome", "descricao", "referencia", "flagEditavel", "flagConcluir", "flagAtivo", "tipoQuizModel.id");
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TipoSolicitacaoDocumentoModel> pesquisarCrudModel(TipoSolicitacaoDocumentoModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT TSD.ID, TSD.USUARIO_CADASTRO_ID, U.NOME, TSD.DATA_CADASTRO, TSD.DESCRICAO, TSD.REFERENCIA, TSD.FLAG_EDITAVEL, TSD.FLAG_CONCLUIR, TSD.FLAG_ATIVO FROM TIPO_SOLICITACAO_DOCUMENTO TSD, USUARIO U WHERE U.ID = TSD.USUARIO_CADASTRO_ID AND TSD.FLAG_ATIVO AND SEM_ACENTOS(TSD.DESCRICAO) ILIKE SEM_ACENTOS(COALESCE(?, TSD.DESCRICAO)) ORDER BY TSD.DESCRICAO", Utilitario.getStringIlike(model.getDescricao(), true));

		return broker.getCollectionBean(TipoSolicitacaoDocumentoModel.class, "id", "usuarioCadastroModel.id", "usuarioCadastroModel.nome", "dataCadastro", "descricao", "referencia", "flagEditavel", "flagConcluir", "flagAtivo");
	}

	@SuppressWarnings("unchecked")
	public List<TipoSolicitacaoDocumentoModel> pesquisar(FuncaoModel funcaoModel) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO FROM TIPO_SOLICITACAO_DOCUMENTO TSD WHERE FLAG_ATIVO AND EXISTS (SELECT 1 FROM TIPO_SOLICITACAO_DOCUMENTO_FUNCAO TSDF WHERE TSD.ID = TSDF.TIPO_SOLICITACAO_DOCUMENTO_ID AND TSDF.FUNCAO_ID = ?) ORDER BY DESCRICAO", funcaoModel.getId());

		return broker.getCollectionBean(TipoSolicitacaoDocumentoModel.class, "id", "descricao");

	}

	public TipoSolicitacaoDocumentoModel inserirCrudModel(final TipoSolicitacaoDocumentoModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("tipo_solicitacao_documento_id_seq"));

		broker.setSQL("INSERT INTO TIPO_SOLICITACAO_DOCUMENTO (ID, USUARIO_CADASTRO_ID, DATA_CADASTRO, DESCRICAO, REFERENCIA, FLAG_EDITAVEL, FLAG_CONCLUIR, FLAG_ATIVO, TIPO_QUIZ_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", model.getId(), model.getUsuarioCadastroModel().getId(), new Timestamp(model.getDataCadastro().getTime()), model.getDescricao(), model.getReferencia(), model.getFlagEditavel(), model.getFlagConcluir(), model.getFlagAtivo(), model.getTipoQuizModel().getId());

		broker.execute();

		return model;

	}

	public TipoSolicitacaoDocumentoModel alterarCrudModel(final TipoSolicitacaoDocumentoModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE TIPO_SOLICITACAO_DOCUMENTO SET DATA_CADASTRO = ?, USUARIO_CADASTRO_ID = ?, DESCRICAO = ?, REFERENCIA = ?, FLAG_EDITAVEL = ?, FLAG_CONCLUIR = ?, TIPO_QUIZ_ID = ? WHERE ID = ?", new Timestamp(model.getDataAtualizacao().getTime()), model.getUsuarioAtualizacaoModel().getId(), model.getDescricao(), model.getReferencia(), model.getFlagEditavel(), model.getFlagConcluir(), model.getTipoQuizModel().getId(), model.getId());

		broker.execute();

		return model;

	}

	@Override
	public TipoSolicitacaoDocumentoModel excluirCrudModel(TipoSolicitacaoDocumentoModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE TIPO_SOLICITACAO_DOCUMENTO SET FLAG_ATIVO = FALSE WHERE ID = ?", model.getId());

		broker.execute();

		return model;
	}
	
	public String getReferenciaComReplace(final TipoSolicitacaoDocumentoModel model, final Long movimentacaoId) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT * FROM OBTER_TEXTO_SOLICITACAO_DOCUMENTO(?, ?)", model.getId(), movimentacaoId);

		return (String) broker.getObject();

	}
	
	@SuppressWarnings("unchecked")
	public List<TipoSolicitacaoDocumentoFuncaoModel> pesquisarFuncoes(TipoSolicitacaoDocumentoModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT TSDF.ID, TSDF.TIPO_SOLICITACAO_DOCUMENTO_ID, TSDF.FUNCAO_ID, (SELECT DESCRICAO FROM FUNCAO F WHERE F.ID = TSDF.FUNCAO_ID) FROM TIPO_SOLICITACAO_DOCUMENTO_FUNCAO TSDF WHERE TSDF.TIPO_SOLICITACAO_DOCUMENTO_ID = ? ORDER BY 4", model.getId());

		return broker.getCollectionBean(TipoSolicitacaoDocumentoFuncaoModel.class, "id", "tipoSolicitacaoDocumentoModel.id", "funcaoModel.id", "funcaoModel.descricao");
	}

	public TipoSolicitacaoDocumentoFuncaoModel inserir(final TipoSolicitacaoDocumentoFuncaoModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("tipo_solicitacao_documento_funcao_id_seq"));

		broker.setSQL("INSERT INTO TIPO_SOLICITACAO_DOCUMENTO_FUNCAO (ID, TIPO_SOLICITACAO_DOCUMENTO_ID, FUNCAO_ID) VALUES (?, ?, ?)", model.getId(), model.getTipoSolicitacaoDocumentoModel().getId(), model.getFuncaoModel().getId());

		broker.execute();

		return model;

	}

	public TipoSolicitacaoDocumentoFuncaoModel alterar(final TipoSolicitacaoDocumentoFuncaoModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE TIPO_SOLICITACAO_DOCUMENTO_FUNCAO SET TIPO_SOLICITACAO_DOCUMENTO_ID = ?, FUNCAO_ID = ? WHERE ID = ?", model.getTipoSolicitacaoDocumentoModel().getId(), model.getFuncaoModel().getId(), model.getId());

		broker.execute();

		return model;

	}

	public void excluir(TipoSolicitacaoDocumentoFuncaoModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("DELETE FROM TIPO_SOLICITACAO_DOCUMENTO_FUNCAO WHERE ID = ?", model.getId());

		broker.execute();

	}

}
