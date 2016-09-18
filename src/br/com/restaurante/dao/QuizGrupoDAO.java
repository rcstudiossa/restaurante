package br.com.restaurante.dao;

import java.util.List;

import br.com.restaurante.model.QuizGrupoModel;
import br.com.restaurante.model.TipoQuizModel;
import br.com.restaurante.util.Utilitario;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

public final class QuizGrupoDAO implements CrudDAO<QuizGrupoModel> {

	public QuizGrupoModel obterCrudModel(final QuizGrupoModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO, FLAG_ATIVO, ORDEM, ID_EXTERNO, OBTER_DATA_CADASTRO_LOG(ID, 'QUIZ_GRUPO') DATA_CADASTRO, OBTER_USUARIO_CADASTRO_LOG(ID, 'QUIZ_GRUPO') USUARIO_CADASTRO, DATA_CADASTRO DATA_ATUALIZACAO, (SELECT NOME FROM USUARIO U1 WHERE U1.ID = QG.USUARIO_CADASTRO_ID) FROM QUIZ_GRUPO QG WHERE ID = ?", model.getId());

		return (QuizGrupoModel) broker.getObjectBean(QuizGrupoModel.class, "id", "descricao", "flagAtivo", "ordem", "idExterno", "dataCadastro", "usuarioCadastroModel.nome", "dataAtualizacao", "usuarioAtualizacaoModel.nome");

	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<QuizGrupoModel> pesquisarLog(QuizGrupoModel model) {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO, FLAG_ATIVO, ORDEM, ID_EXTERNO, OBTER_DATA_CADASTRO_LOG(ID, 'QUIZ_GRUPO') DATA_CADASTRO, OBTER_USUARIO_CADASTRO_LOG(ID, 'QUIZ_GRUPO') USUARIO_CADASTRO, DATA_CADASTRO DATA_ATUALIZACAO, (SELECT NOME FROM USUARIO U1 WHERE U1.ID = QG.USUARIO_CADASTRO_ID) FROM LOG.QUIZ_GRUPO QG WHERE ID = ? ORDER BY DATA_CADASTRO DESC", model.getId());

		return broker.getCollectionBean(QuizGrupoModel.class, "id", "descricao", "flagAtivo", "ordem", "idExterno", "dataCadastro", "usuarioCadastroModel.nome", "dataAtualizacao", "usuarioAtualizacaoModel.nome");
		
	}

	@SuppressWarnings("unchecked")
	public List<QuizGrupoModel> pesquisarCrudModel(final QuizGrupoModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO, FLAG_ATIVO, ORDEM FROM QUIZ_GRUPO QAG WHERE SEM_ACENTOS(DESCRICAO) LIKE SEM_ACENTOS(COALESCE(?, DESCRICAO)) AND FLAG_ATIVO = COALESCE(?, FLAG_ATIVO) ORDER BY FLAG_ATIVO, DESCRICAO", Utilitario.getStringIlike(model.getDescricao(), true), model.getFlagAtivo());

		return broker.getCollectionBean(QuizGrupoModel.class, "id", "descricao", "flagAtivo", "ordem");

	}

	@SuppressWarnings("unchecked")
	public List<QuizGrupoModel> pesquisarComFilhos(final TipoQuizModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO, FLAG_ATIVO FROM QUIZ_GRUPO QG WHERE EXISTS (SELECT 1 FROM QUIZ Q INNER JOIN QUIZ_PERGUNTA QP ON Q.QUIZ_PERGUNTA_ID = QP.ID WHERE QP.FLAG_ATIVO AND Q.FLAG_ATIVO AND Q.TIPO_QUIZ_ID = ? AND Q.QUIZ_GRUPO_ID = QG.ID) ORDER BY ORDEM, DESCRICAO", model.getId());

		return broker.getCollectionBean(QuizGrupoModel.class, "id", "descricao", "flagAtivo");

	}

	public QuizGrupoModel inserirCrudModel(final QuizGrupoModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("quiz_grupo_id_seq"));

		broker.setSQL("INSERT INTO QUIZ_GRUPO (ID, DESCRICAO, FLAG_ATIVO, ORDEM, ID_EXTERNO) VALUES (?,?,?,?,?)", model.getId(), model.getDescricao(), model.getFlagAtivo(), model.getOrdem(), model.getIdExterno());

		broker.execute();

		return model;

	}

	public QuizGrupoModel alterarCrudModel(final QuizGrupoModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE QUIZ_GRUPO SET DESCRICAO = ?, FLAG_ATIVO = ?, ORDEM=? WHERE ID = ?", model.getDescricao(), model.getFlagAtivo(), model.getOrdem(), model.getId());

		broker.execute();

		return model;

	}

	public QuizGrupoModel excluirCrudModel(final QuizGrupoModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("DELETE FROM QUIZ_GRUPO WHERE ID = ?", model.getId());

		broker.execute();

		return model;

	}
	
	@SuppressWarnings("unchecked")
	public List<QuizGrupoModel> pesquisarExames() {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("SELECT ID, DESCRICAO, FLAG_ATIVO, ORDEM, FLAG_EXAME FROM QUIZ_GRUPO QAG WHERE FLAG_ATIVO AND FLAG_EXAME ORDER BY DESCRICAO");
		
		return broker.getCollectionBean(QuizGrupoModel.class, "id", "descricao", "flagAtivo", "ordem", "flagExame");
		
	}

}
