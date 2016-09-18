package br.com.restaurante.dao;

import java.sql.Timestamp;
import java.util.List;

import br.com.restaurante.model.FuncionarioModel;
import br.com.restaurante.model.QuizPerguntaModel;
import br.com.restaurante.model.QuizQuestionarioArquetipoRespostaModel;
import br.com.restaurante.model.QuizQuestionarioModel;
import br.com.restaurante.model.QuizQuestionarioRespostaModel;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

public class QuizQuestionarioDAO implements QuizTemplateDAOIf<QuizQuestionarioModel, QuizQuestionarioRespostaModel, QuizQuestionarioArquetipoRespostaModel> {

	public QuizQuestionarioModel obter(final QuizQuestionarioModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT QQ.ID, QQ.ATENDIMENTO_ID, SA.FLAG_FECHADO, QQ.DATA_CADASTRO, QQ.USUARIO_CADASTRO_ID, QQ.FLAG_CONCLUIDO, QQ.TIPO_QUIZ_ID, (SELECT F.DESCRICAO FROM TIPO_QUIZ TQ, FUNCAO F WHERE F.ID = TQ.FUNCAO_ID AND TQ.ID = QQ.TIPO_QUIZ_ID), (SELECT TQ.TAB_ID FROM TIPO_QUIZ TQ WHERE TQ.ID = QQ.TIPO_QUIZ_ID), (SELECT TQ.RELATORIO FROM TIPO_QUIZ TQ WHERE TQ.ID = QQ.TIPO_QUIZ_ID), A.ORIGEM_ID, O.GMT, A.FUNCIONARIO_ID FROM QUIZ_QUESTIONARIO QQ, ATENDIMENTO A, STATUS_ATENDIMENTO SA, ORIGEM O WHERE A.ID = QQ.ATENDIMENTO_ID AND O.ID = A.ORIGEM_ID AND SA.ID = A.STATUS_ATENDIMENTO_ID AND QQ.ID = ?", model.getId());

		return (QuizQuestionarioModel) broker.getObjectBean(QuizQuestionarioModel.class, "id", "atendimentoModel.id", "atendimentoModel.statusAtendimentoModel.flagFechado", "dataCadastro", "usuarioCadastroModel.id", "flagConcluido", "tipoQuizModel.id", "tipoQuizModel.funcaoModel.descricao", "tipoQuizModel.tabModel.id", "tipoQuizModel.relatorio", "atendimentoModel.origemModel.id", "atendimentoModel.origemModel.gmt", "atendimentoModel.funcionarioModel.id");

	}

	public QuizQuestionarioModel obterExistente(final QuizQuestionarioModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT QQ.ID, QQ.ATENDIMENTO_ID, QQ.DATA_CADASTRO, QQ.USUARIO_CADASTRO_ID, QQ.FLAG_CONCLUIDO, QQ.TIPO_QUIZ_ID, A.ORIGEM_ID, O.GMT FROM QUIZ_QUESTIONARIO QQ, ATENDIMENTO A, ORIGEM O WHERE A.ID = QQ.ATENDIMENTO_ID AND O.ID = A.ORIGEM_ID AND QQ.ATENDIMENTO_ID = ? AND EXISTS (SELECT 1 FROM TIPO_QUIZ TQ WHERE TQ.ID = QQ.TIPO_QUIZ_ID AND TQ.TAB_ID = ? AND COALESCE(TQ.FUNCAO_ID, ?) = ?)", model.getAtendimentoModel().getId(), model.getTipoQuizModel().getTabModel().getId(), model.getTipoQuizModel().getFuncaoModel().getId(), model.getTipoQuizModel().getFuncaoModel().getId());

		return (QuizQuestionarioModel) broker.getObjectBean(QuizQuestionarioModel.class, "id", "atendimentoModel.id", "dataCadastro", "usuarioCadastroModel.id", "flagConcluido", "tipoQuizModel.id", "atendimentoModel.origemModel.id", "atendimentoModel.origemModel.gmt");

	}

	public QuizQuestionarioModel inserir(final QuizQuestionarioModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("quiz_questionario_id_seq"));

		broker.setSQL("INSERT INTO QUIZ_QUESTIONARIO (ID, ATENDIMENTO_ID, USUARIO_CADASTRO_ID, FLAG_CONCLUIDO, TIPO_QUIZ_ID, DATA_CADASTRO, FUNCAO_ID) VALUES (?, ?, ?, ?, ?, ?, ?)", model.getId(), model.getAtendimentoModel().getId(), model.getUsuarioCadastroModel().getId(), model.getFlagConcluido(), model.getTipoQuizModel().getId(), new Timestamp(model.getDataCadastro().getTime()), model.getFuncaoModel().getId());

		broker.execute();

		return model;

	}

	public QuizQuestionarioModel alterar(final QuizQuestionarioModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE QUIZ_QUESTIONARIO SET DATA_CADASTRO = ?, USUARIO_CADASTRO_ID = ?, FLAG_CONCLUIDO = ? WHERE ID = ?", new Timestamp(model.getDataCadastro().getTime()), model.getUsuarioCadastroModel().getId(), model.getFlagConcluido(), model.getId());

		broker.execute();

		return model;

	}

	public QuizQuestionarioModel reabrir(final QuizQuestionarioModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE QUIZ_QUESTIONARIO SET DATA_CADASTRO = ?, USUARIO_CADASTRO_ID = ?, FLAG_CONCLUIDO = FALSE WHERE ID = ?", new Timestamp(model.getDataCadastro().getTime()), model.getUsuarioCadastroModel().getId(), model.getId());

		broker.execute();

		return model;

	}

	public List<QuizQuestionarioModel> pesquisarQuiz(QuizQuestionarioModel model) {
		return this.pesquisarQuiz(model, 999999);
	}
	
	@SuppressWarnings("unchecked")
	public List<QuizQuestionarioModel> pesquisarQuiz(QuizQuestionarioModel model, int limit) {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("SELECT Q.ID, Q.ATENDIMENTO_ID, SA.DESCRICAO, SA.FLAG_FECHADO, Q.DATA_CADASTRO, Q.USUARIO_CADASTRO_ID, U.NOME, Q.FLAG_CONCLUIDO, Q.TIPO_QUIZ_ID, TQ.RELATORIO, A.ORIGEM_ID, O.GMT, A.FUNCIONARIO_ID FROM QUIZ_QUESTIONARIO Q, TIPO_QUIZ TQ, TAB TP, ATENDIMENTO A, ORIGEM O, USUARIO U, STATUS_ATENDIMENTO SA WHERE TQ.ID = Q.TIPO_QUIZ_ID AND TP.ID = TQ.TAB_ID AND A.ID = Q.ATENDIMENTO_ID AND O.ID = A.ORIGEM_ID AND U.ID = Q.USUARIO_CADASTRO_ID AND SA.ID = A.STATUS_ATENDIMENTO_ID AND A.FUNCIONARIO_ID = ? AND TQ.TAB_ID = ? ORDER BY Q.ID DESC LIMIT ?", model.getAtendimentoModel().getFuncionarioModel().getId(), model.getTipoQuizModel().getTabModel().getId(), limit);
		
		return broker.getCollectionBean(QuizQuestionarioModel.class, "id", "atendimentoModel.id", "atendimentoModel.statusAtendimentoModel.descricao", "atendimentoModel.statusAtendimentoModel.flagFechado", "dataCadastro", "usuarioCadastroModel.id", "usuarioCadastroModel.nome", "flagConcluido", "tipoQuizModel.id", "tipoQuizModel.relatorio", "atendimentoModel.origemModel.id", "atendimentoModel.origemModel.gmt", "atendimentoModel.funcionarioModel.id");
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<QuizQuestionarioRespostaModel> pesquisarRespostas(QuizQuestionarioModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT QQR.ID, QQR.QUIZ_QUESTIONARIO_ID, QQR.QUIZ_ID, Q.QUIZ_GRUPO_ID, Q.ORDEM, Q.FLAG_COPIA, Q.FORMULA, Q.QUIZ_PERGUNTA_ID, QP.PERGUNTA, QQR.RESPOSTA, QQR.OUTROS, QQR.UNIDADE, QQR.ID_RESPOSTA FROM QUIZ_QUESTIONARIO_RESPOSTA QQR INNER JOIN QUIZ Q ON Q.ID = QQR.QUIZ_ID INNER JOIN QUIZ_PERGUNTA QP ON QP.ID = Q.QUIZ_PERGUNTA_ID WHERE QP.FLAG_ATIVO AND QQR.QUIZ_QUESTIONARIO_ID = ? ORDER BY QQR.ID", model.getId());

		return broker.getCollectionBean(QuizQuestionarioRespostaModel.class, "id", "quizTemplate.id", "quizModel.id", "quizModel.quizGrupoModel.id", "quizModel.ordem", "quizModel.flagCopia", "quizModel.formula", "quizModel.quizPerguntaModel.id", "quizModel.quizPerguntaModel.pergunta", "resposta", "outros", "unidade", "idResposta");
	}

	@Override
	public QuizQuestionarioRespostaModel inserirResposta(QuizQuestionarioRespostaModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("quiz_questionario_resposta_id_seq"));

		broker.setSQL("INSERT INTO QUIZ_QUESTIONARIO_RESPOSTA (ID, QUIZ_QUESTIONARIO_ID, QUIZ_ID, RESPOSTA, OUTROS, UNIDADE, ID_RESPOSTA) VALUES (?, ?, ?, ?, ?, ?, ?)", model.getId(), model.getQuizTemplate().getId(), model.getQuizModel().getId(), model.getResposta(), model.getOutros(), model.getUnidade(), model.getIdResposta());

		broker.execute();

		return model;
	}

	@Override
	public void excluirRespostas(QuizQuestionarioModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("DELETE FROM QUIZ_QUESTIONARIO_RESPOSTA WHERE QUIZ_QUESTIONARIO_ID = ?", model.getId());

		broker.execute();

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<QuizQuestionarioArquetipoRespostaModel> pesquisarRespostasArquetipos(QuizQuestionarioRespostaModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT QQAR.ID, QQAR.QUIZ_QUESTIONARIO_RESPOSTA_ID, QQAR.QUIZ_PERGUNTA_ARQUETIPO_ID, QPA.QUIZ_PERGUNTA_ID, QPA.ARQUETIPO_ID, QQAR.RESPOSTA FROM QUIZ_QUESTIONARIO_ARQUETIPO_RESPOSTA QQAR, QUIZ_PERGUNTA_ARQUETIPO QPA WHERE QPA.ID = QQAR.QUIZ_PERGUNTA_ARQUETIPO_ID AND QQAR.QUIZ_QUESTIONARIO_RESPOSTA_ID = ? ORDER BY QQAR.ID", model.getId());

		return broker.getCollectionBean(QuizQuestionarioArquetipoRespostaModel.class, "id", "quizTemplateRespostaModel.id", "quizPerguntaArquetipoModel.id", "quizPerguntaArquetipoModel.quizPerguntaModel.id", "quizPerguntaArquetipoModel.arquetipoModel.id", "resposta");
	}

	@Override
	public QuizQuestionarioArquetipoRespostaModel inserirRespostaArquetipo(QuizQuestionarioArquetipoRespostaModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("quiz_questionario_arquetipo_resposta_id_seq"));

		broker.setSQL("INSERT INTO QUIZ_QUESTIONARIO_ARQUETIPO_RESPOSTA (ID, QUIZ_QUESTIONARIO_RESPOSTA_ID, QUIZ_PERGUNTA_ARQUETIPO_ID, RESPOSTA) VALUES (?, ?, ?, ?)", model.getId(), model.getQuizTemplateRespostaModel().getId(), model.getQuizPerguntaArquetipoModel().getId(), model.getResposta());

		broker.execute();

		return model;
	}

	@Override
	public void excluirRespostasArquetipos(QuizQuestionarioRespostaModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("DELETE FROM QUIZ_QUESTIONARIO_ARQUETIPO_RESPOSTA WHERE QUIZ_QUESTIONARIO_RESPOSTA_ID = ?", model.getId());

		broker.execute();
	}
	
	@SuppressWarnings("unchecked")
	public List<QuizQuestionarioRespostaModel> pesquisarRespostasFuncoesVitais(FuncionarioModel funcionario, QuizPerguntaModel funcaoVital) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT QQR.RESPOSTA, QQ.DATA_CADASTRO FROM QUIZ_QUESTIONARIO_RESPOSTA QQR, QUIZ_QUESTIONARIO QQ, ATENDIMENTO A, QUIZ Q WHERE QQR.QUIZ_QUESTIONARIO_ID = QQ.ID AND QQ.ATENDIMENTO_ID = A.ID AND Q.ID = QQR.QUIZ_ID AND A.FUNCIONARIO_ID = ? AND Q.QUIZ_PERGUNTA_ID = ? ", funcionario.getId(), funcaoVital.getId());

		return broker.getCollectionBean(QuizQuestionarioRespostaModel.class, "resposta", "quizTemplate.dataCadastro");
	}

}
