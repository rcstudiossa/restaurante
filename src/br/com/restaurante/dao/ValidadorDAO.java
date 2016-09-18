package br.com.restaurante.dao;


import java.sql.Timestamp;
import java.util.List;

import br.com.restaurante.model.QuizModel;
import br.com.restaurante.model.QuizPerguntaModel;
import br.com.restaurante.model.ValidadorModel;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

public final class ValidadorDAO implements CrudDAO<ValidadorModel> {

	public ValidadorModel obterCrudModel(final ValidadorModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO, TIPO_AVISO, EXPRESSAO, FLAG_ATIVO, AVISO, QUERY, QUERY_VALIDACAO, ID_EXTERNO, FLAG_VALIDACAO_FINAL, OBTER_DATA_CADASTRO_LOG(ID, 'VALIDADOR'), OBTER_USUARIO_CADASTRO_LOG(ID, 'VALIDADOR'), DATA_CADASTRO DATA_ALTERACAO, (SELECT NOME FROM USUARIO U1 WHERE U1.ID = V.USUARIO_CADASTRO_ID) USUARIO_ALTERACAO FROM VALIDADOR V WHERE ID = ?", model.getId());

		return (ValidadorModel) broker.getObjectBean(ValidadorModel.class, "id", "descricao", "tipoAviso", "expressao", "flagAtivo", "aviso", "query", "queryValidacao", "idExterno", "flagValidacaoFinal", "dataCadastro", "usuarioCadastroModel.nome", "dataAtualizacao", "usuarioAtualizacaoModel.nome");

	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ValidadorModel> pesquisarLog(ValidadorModel model) {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO, TIPO_AVISO, EXPRESSAO, FLAG_ATIVO, AVISO, QUERY, QUERY_VALIDACAO, ID_EXTERNO, FLAG_VALIDACAO_FINAL, DATA_CADASTRO, (SELECT NOME FROM USUARIO U1 WHERE U1.ID = V.USUARIO_CADASTRO_ID) FROM LOG.VALIDADOR V WHERE ID = ? ORDER BY DATA_CADASTRO DESC", model.getId());

		return broker.getCollectionBean(ValidadorModel.class, "id", "descricao", "tipoAviso", "expressao", "flagAtivo", "aviso", "query", "queryValidacao", "idExterno", "flagValidacaoFinal", "dataCadastro", "usuarioCadastroModel.nome");
		
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ValidadorModel> pesquisarCrudModel(ValidadorModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO, TIPO_AVISO, EXPRESSAO, FLAG_ATIVO, AVISO, QUERY, QUERY_VALIDACAO, FLAG_VALIDACAO_FINAL FROM VALIDADOR ORDER BY DESCRICAO");

		return broker.getCollectionBean(ValidadorModel.class, "id", "descricao", "tipoAviso", "expressao", "flagAtivo", "aviso", "query", "queryValidacao", "flagValidacaoFinal");

	}

	@SuppressWarnings("unchecked")
	public List<ValidadorModel> pesquisar(QuizPerguntaModel quizPerguntaModel, QuizModel quizModel) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO, TIPO_AVISO, EXPRESSAO, FLAG_ATIVO, AVISO, QUERY, QUERY_VALIDACAO, FLAG_VALIDACAO_FINAL FROM VALIDADOR V WHERE FLAG_ATIVO AND (EXPRESSAO ILIKE ? OR EXPRESSAO ILIKE ? OR EXISTS (SELECT 1 FROM QUIZ_VALIDADOR QQ WHERE QQ.QUIZ_ID = ? AND QQ.VALIDADOR_ID = V.ID)) ORDER BY ID", "%P" + quizPerguntaModel.getId() + "P%", "%Q" + quizModel.getId() + "Q%", quizModel.getId());

		return broker.getCollectionBean(ValidadorModel.class, "id", "descricao", "tipoAviso", "expressao", "flagAtivo", "aviso", "query", "queryValidacao", "flagValidacaoFinal");

	}
	
	@SuppressWarnings("unchecked")
	public List<ValidadorModel> pesquisar(Long idPergunta) {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("SELECT ID, DESCRICAO, TIPO_AVISO, EXPRESSAO, FLAG_ATIVO, AVISO, QUERY, QUERY_VALIDACAO, FLAG_VALIDACAO_FINAL FROM VALIDADOR V WHERE FLAG_ATIVO AND EXPRESSAO ILIKE ? ORDER BY ID", "%C" + idPergunta + "C%");
		
		return broker.getCollectionBean(ValidadorModel.class, "id", "descricao", "tipoAviso", "expressao", "flagAtivo", "aviso", "query", "queryValidacao", "flagValidacaoFinal");
		
	}

	public ValidadorModel inserirCrudModel(final ValidadorModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("validador_id_seq"));

		broker.setSQL("INSERT INTO VALIDADOR(ID, DESCRICAO, TIPO_AVISO, EXPRESSAO, FLAG_ATIVO, AVISO, QUERY, QUERY_VALIDACAO, ID_EXTERNO, FLAG_VALIDACAO_FINAL, DATA_CADASTRO, USUARIO_CADASTRO_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", model.getId(), model.getDescricao(), model.getTipoAviso(), model.getExpressao(), model.getFlagAtivo(), model.getAviso(), model.getQuery(), model.getQueryValidacao(), model.getIdExterno(), model.getFlagValidacaoFinal(), new Timestamp(model.getDataCadastro().getTime()), model.getUsuarioCadastroModel().getId());

		broker.execute();

		return model;

	}

	public ValidadorModel alterarCrudModel(final ValidadorModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE VALIDADOR SET DESCRICAO=?, TIPO_AVISO=?, EXPRESSAO=?, FLAG_ATIVO=?, AVISO=?, QUERY=?, QUERY_VALIDACAO=?, FLAG_VALIDACAO_FINAL=?, DATA_CADASTRO = ?, USUARIO_CADASTRO_ID = ? WHERE ID=?", model.getDescricao(), model.getTipoAviso(), model.getExpressao(), model.getFlagAtivo(), model.getAviso(), model.getQuery(), model.getQueryValidacao(), model.getFlagValidacaoFinal(), new Timestamp(model.getDataAtualizacao().getTime()), model.getUsuarioAtualizacaoModel().getId(), model.getId());

		broker.execute();

		return model;

	}

	public ValidadorModel excluirCrudModel(final ValidadorModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("DELETE FROM VALIDADOR WHERE ID=?", model.getId());

		broker.execute();

		return model;

	}

}
