package br.com.restaurante.dao;

import java.util.List;

import br.com.restaurante.model.AtendimentoModel;
import br.com.restaurante.model.QuizGrupoModel;
import br.com.restaurante.model.QuizModel;
import br.com.restaurante.model.QuizValidadorModel;
import br.com.restaurante.model.TipoQuizModel;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

public class QuizDAO {

	public QuizModel obter(QuizModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT Q.ID, QP.ID, QP.PERGUNTA, QP.APELIDO, QP.MAXLENGHT, QP.ROWS, QP.PATTERN, QP.TIPO_RESPOSTA_ID, T.DESCRICAO, QP.FLAG_OUTROS, QP.MEDIDA_ID, QP.FORMULA, Q.FLAG_OBRIGATORIO, Q.ORDEM, Q.QUIZ_DEPENDENCIA_ID, Q.RESPOSTA_PADRAO, Q.QUIZ_GRUPO_ID, (SELECT DESCRICAO FROM QUIZ_GRUPO QAG WHERE Q.QUIZ_GRUPO_ID = QAG.ID), Q.CSS_DIV, Q.CSS_COLUNAS, Q.CSS_AJUSTE, Q.FLAG_ATIVO, Q.QUIZ_RESPOSTA_DEPENDENTE_ID, QR.RESPOSTA, Q.ROWINDEX_DEPENDENTE, Q.QTD_COLUNAS, Q.QUIZ_RESPOSTA_PADRAO_ID, (SELECT Q2.TIPO_QUIZ_ID FROM QUIZ Q2 WHERE Q2.ID = Q.QUIZ_RESPOSTA_PADRAO_ID), OBTER_RESPOSTA(COALESCE(null, 0), Q.QUIZ_RESPOSTA_PADRAO_ID, Q.QUERY_RESPOSTA_PADRAO), EXISTS (SELECT 1 FROM QUIZ Q2 WHERE Q2.TIPO_QUIZ_ID = Q.TIPO_QUIZ_ID AND EXISTS (SELECT 1 FROM QUIZ_RESPOSTA QR2 WHERE QR2.QUIZ_PERGUNTA_ID = QP.ID AND QR2.ID = Q2.QUIZ_RESPOSTA_DEPENDENTE_ID)), EXISTS (SELECT 1 FROM QUIZ_RESPOSTA QR WHERE QR.QUIZ_PERGUNTA_ID = QP.ID AND QR.PONTOS > 0) FLAG_POSSUI_PONTUACAO, COALESCE(NULLIF(Q.APELIDO, ''), COALESCE(NULLIF(QP.APELIDO, ''), QP.PERGUNTA)) APELIDO, Q.FLAG_OUTROS, Q.MAXLENGTH, Q.ROWS, Q.QUERY_RESPOSTA_PADRAO, Q.ID_EXTERNO, Q.FORMULA FROM QUIZ Q INNER JOIN QUIZ_PERGUNTA QP ON QP.ID = Q.QUIZ_PERGUNTA_ID INNER JOIN TIPO_RESPOSTA T ON T.ID = QP.TIPO_RESPOSTA_ID LEFT JOIN QUIZ_RESPOSTA QR ON QR.ID = Q.QUIZ_RESPOSTA_DEPENDENTE_ID WHERE Q.ID = ? ", model.getId());

		return (QuizModel) broker.getObjectBean(QuizModel.class, "id", "quizPerguntaModel.id", "quizPerguntaModel.pergunta", "quizPerguntaModel.apelido", "quizPerguntaModel.maxlenght", "quizPerguntaModel.rows", "quizPerguntaModel.pattern", "quizPerguntaModel.tipoRespostaModel.id", "quizPerguntaModel.tipoRespostaModel.descricao", "quizPerguntaModel.flagOutros", "quizPerguntaModel.medidaModel.id", "quizPerguntaModel.formula", "flagObrigatorio", "ordem", "quizDependenciaModel.id", "respostaPadrao", "quizGrupoModel.id", "quizGrupoModel.descricao", "cssDiv", "cssColunas", "cssAjuste", "flagAtivo", "quizRespostaDependenteModel.id", "quizRespostaDependenteModel.resposta", "rowindexDependente", "qtdColunas", "quizRespostaPadraoModel.id", "quizRespostaPadraoModel.tipoQuizModel.id", "respostaPadrao", "flagPossuiDependentes", "flagPossuiPontuacao", "apelido", "flagOutros", "maxlength", "rows", "queryRespostaPadrao", "idExterno", "formula");
	}

	public List<QuizModel> pesquisar(QuizModel model) {
		return this.pesquisar(model.getTipoQuizModel());
	}
	
	@SuppressWarnings("unchecked")
	public List<QuizModel> pesquisar(TipoQuizModel model) {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("SELECT Q.ID, QP.ID, QP.PERGUNTA, QP.APELIDO, QP.MAXLENGHT, QP.ROWS, QP.PATTERN, QP.FLAG_OUTROS, TR.ID, TR.DESCRICAO, Q.FLAG_OBRIGATORIO, Q.ORDEM, Q.QUIZ_DEPENDENCIA_ID, Q.RESPOSTA_PADRAO, Q.QUIZ_GRUPO_ID, (SELECT DESCRICAO FROM QUIZ_GRUPO QAG WHERE Q.QUIZ_GRUPO_ID = QAG.ID), (SELECT QAG.ORDEM FROM QUIZ_GRUPO QAG WHERE Q.QUIZ_GRUPO_ID = QAG.ID), (SELECT QAG.FLAG_ATIVO FROM QUIZ_GRUPO QAG WHERE Q.QUIZ_GRUPO_ID = QAG.ID), Q.TIPO_QUIZ_ID, Q.CSS_DIV, Q.CSS_COLUNAS, Q.CSS_AJUSTE, Q.FLAG_ATIVO, Q.QUIZ_RESPOSTA_DEPENDENTE_ID, (SELECT QP2.ID FROM QUIZ_RESPOSTA QR2 INNER JOIN QUIZ_PERGUNTA QP2 ON QP2.ID = QR2.QUIZ_PERGUNTA_ID WHERE QR2.ID = Q.QUIZ_RESPOSTA_DEPENDENTE_ID), Q.ROWINDEX_DEPENDENTE, Q.QTD_COLUNAS, Q.QUIZ_RESPOSTA_PADRAO_ID, (SELECT Q2.TIPO_QUIZ_ID FROM QUIZ Q2 WHERE Q2.ID = Q.QUIZ_RESPOSTA_PADRAO_ID), COALESCE(NULLIF(Q.APELIDO, ''), COALESCE(NULLIF(QP.APELIDO, ''), QP.PERGUNTA)) APELIDO, Q.FLAG_OUTROS, Q.MAXLENGTH, Q.ROWS, Q.QUERY_RESPOSTA_PADRAO, Q.FLAG_TAMANHO_FIXO, Q.CSS_AJUSTE, Q.ID_EXTERNO, Q.FLAG_COPIA, Q.FORMULA, QG.DESCRICAO || ': ' || COALESCE(Q.APELIDO, QP.APELIDO, QP.PERGUNTA) FROM QUIZ Q INNER JOIN QUIZ_PERGUNTA QP ON QP.ID = Q.QUIZ_PERGUNTA_ID INNER JOIN TIPO_RESPOSTA TR ON QP.TIPO_RESPOSTA_ID = TR.ID INNER JOIN QUIZ_GRUPO QG ON QG.ID = Q.QUIZ_GRUPO_ID WHERE QP.FLAG_ATIVO AND Q.TIPO_QUIZ_ID = ? ORDER BY QG.ORDEM, QG.DESCRICAO, Q.ORDEM, QP.PERGUNTA", model.getId());
		
		return broker.getCollectionBean(QuizModel.class, "id", "quizPerguntaModel.id", "quizPerguntaModel.pergunta", "quizPerguntaModel.apelido", "quizPerguntaModel.maxlenght", "quizPerguntaModel.rows", "quizPerguntaModel.pattern", "quizPerguntaModel.flagOutros", "quizPerguntaModel.tipoRespostaModel.id", "quizPerguntaModel.tipoRespostaModel.descricao", "flagObrigatorio", "ordem", "quizDependenciaModel.id", "respostaPadrao", "quizGrupoModel.id", "quizGrupoModel.descricao", "quizGrupoModel.ordem", "quizGrupoModel.flagAtivo", "tipoQuizModel.id", "cssDiv", "cssColunas", "cssAjuste", "flagAtivo", "quizRespostaDependenteModel.id", "quizRespostaDependenteModel.quizPerguntaModel.id", "rowindexDependente", "qtdColunas", "quizRespostaPadraoModel.id", "quizRespostaPadraoModel.tipoQuizModel.id", "apelido", "flagOutros", "maxlength", "rows", "queryRespostaPadrao", "flagTamanhoFixo", "cssAjuste", "idExterno", "flagCopia", "formula", "descricaoCompleta");
		
	}

	@SuppressWarnings("unchecked")
	public List<QuizModel> pesquisarPorGrupo(TipoQuizModel tipoQuiz, QuizGrupoModel grupo, AtendimentoModel atendimentoModel) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT Q.ID, QP.ID, QP.PERGUNTA, QP.APELIDO, QP.MAXLENGHT, QP.ROWS, QP.PATTERN, QP.TIPO_RESPOSTA_ID, T.DESCRICAO, QP.FLAG_OUTROS, QP.MEDIDA_ID, QP.FORMULA, QP.CONSULTA_COMBO_SQL, Q.FLAG_OBRIGATORIO, Q.ORDEM, Q.QUIZ_DEPENDENCIA_ID, Q.RESPOSTA_PADRAO, Q.QUIZ_GRUPO_ID, (SELECT DESCRICAO FROM QUIZ_GRUPO QAG WHERE Q.QUIZ_GRUPO_ID = QAG.ID), Q.CSS_DIV, Q.CSS_COLUNAS, Q.CSS_AJUSTE, Q.FLAG_ATIVO, Q.QUIZ_RESPOSTA_DEPENDENTE_ID, QR.RESPOSTA, (SELECT ROW_NUMBER - 1 FROM (SELECT ID, ROW_NUMBER() OVER (ORDER BY Q2.ORDEM) FROM QUIZ Q2 WHERE Q2.TIPO_QUIZ_ID = Q.TIPO_QUIZ_ID AND Q2.QUIZ_GRUPO_ID = Q.QUIZ_GRUPO_ID AND Q2.FLAG_ATIVO) SUB WHERE SUB.ID = Q.QUIZ_DEPENDENCIA_ID) ROWINDEX_DEPENDENTE, Q.QTD_COLUNAS, Q.QUIZ_RESPOSTA_PADRAO_ID, (SELECT Q2.TIPO_QUIZ_ID FROM QUIZ Q2 WHERE Q2.ID = Q.QUIZ_RESPOSTA_PADRAO_ID), OBTER_RESPOSTA(COALESCE(?, 0), Q.QUIZ_RESPOSTA_PADRAO_ID, Q.QUERY_RESPOSTA_PADRAO), EXISTS (SELECT 1 FROM QUIZ Q2 WHERE Q2.TIPO_QUIZ_ID = Q.TIPO_QUIZ_ID AND EXISTS (SELECT 1 FROM QUIZ_RESPOSTA QR2 WHERE QR2.QUIZ_PERGUNTA_ID = QP.ID AND QR2.ID = Q2.QUIZ_RESPOSTA_DEPENDENTE_ID)), EXISTS (SELECT 1 FROM QUIZ_RESPOSTA QR WHERE QR.QUIZ_PERGUNTA_ID = QP.ID AND QR.PONTOS > 0) FLAG_POSSUI_PONTUACAO, COALESCE(NULLIF(Q.APELIDO, ''), COALESCE(NULLIF(QP.APELIDO, ''), QP.PERGUNTA)) APELIDO, Q.FLAG_OUTROS, Q.MAXLENGTH, Q.ROWS, Q.QUERY_RESPOSTA_PADRAO, Q.FLAG_TAMANHO_FIXO, Q.FLAG_TAMANHO_FIXO, Q.CSS_AJUSTE, Q.FLAG_COPIA, Q.FORMULA FROM QUIZ Q INNER JOIN QUIZ_PERGUNTA QP ON QP.ID = Q.QUIZ_PERGUNTA_ID INNER JOIN TIPO_RESPOSTA T ON T.ID = QP.TIPO_RESPOSTA_ID LEFT JOIN QUIZ_RESPOSTA QR ON QR.ID = Q.QUIZ_RESPOSTA_DEPENDENTE_ID WHERE QP.FLAG_ATIVO AND Q.FLAG_ATIVO AND Q.TIPO_QUIZ_ID = ? AND Q.QUIZ_GRUPO_ID = ? ORDER BY Q.ORDEM, QP.PERGUNTA", atendimentoModel.getId(), tipoQuiz.getId(), grupo.getId());

		return broker.getCollectionBean(QuizModel.class, "id", "quizPerguntaModel.id", "quizPerguntaModel.pergunta", "quizPerguntaModel.apelido", "quizPerguntaModel.maxlenght", "quizPerguntaModel.rows", "quizPerguntaModel.pattern", "quizPerguntaModel.tipoRespostaModel.id", "quizPerguntaModel.tipoRespostaModel.descricao", "quizPerguntaModel.flagOutros", "quizPerguntaModel.medidaModel.id", "quizPerguntaModel.formula", "quizPerguntaModel.consultaComboSQL", "flagObrigatorio", "ordem", "quizDependenciaModel.id", "respostaPadrao", "quizGrupoModel.id", "quizGrupoModel.descricao", "cssDiv", "cssColunas", "cssAjuste", "flagAtivo", "quizRespostaDependenteModel.id", "quizRespostaDependenteModel.resposta", "rowindexDependente", "qtdColunas", "quizRespostaPadraoModel.id", "quizRespostaPadraoModel.tipoQuizModel.id", "respostaPadrao", "flagPossuiDependentes", "flagPossuiPontuacao", "apelido", "flagOutros", "maxlength", "rows", "queryRespostaPadrao", "flagTamanhoFixo", "flagTamanhoFixo", "cssAjuste", "flagCopia", "formula");

	}

	
	public QuizModel inserir(QuizModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("quiz_id_seq"));

		broker.setSQL("INSERT INTO QUIZ(ID, QUIZ_PERGUNTA_ID, FLAG_OBRIGATORIO, ORDEM, QUIZ_DEPENDENCIA_ID, RESPOSTA_PADRAO, QUIZ_GRUPO_ID, TIPO_QUIZ_ID, CSS_DIV, CSS_COLUNAS, FLAG_ATIVO, QUIZ_RESPOSTA_DEPENDENTE_ID, ROWINDEX_DEPENDENTE, QTD_COLUNAS, QUIZ_RESPOSTA_PADRAO_ID, APELIDO, FLAG_OUTROS, MAXLENGTH, ROWS, QUERY_RESPOSTA_PADRAO, ID_EXTERNO, FLAG_TAMANHO_FIXO, CSS_AJUSTE, FLAG_COPIA, FORMULA) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", model.getId(), model.getQuizPerguntaModel().getId(), model.getFlagObrigatorio(), model.getOrdem(), model.getQuizDependenciaModel().getId(), model.getRespostaPadrao(), model.getQuizGrupoModel().getId(), model.getTipoQuizModel().getId(), model.getCssDiv(), model.getCssColunas(), model.getFlagAtivo(), TSUtil.tratarLong(model.getQuizRespostaDependenteModel().getId()), model.getRowindexDependente(), model.getQtdColunas(), TSUtil.tratarLong(model.getQuizRespostaPadraoModel().getId()), model.getApelido(), model.getFlagOutros(), model.getMaxlength(), model.getRows(), model.getQueryRespostaPadrao(), model.getIdExterno(), model.getFlagTamanhoFixo(), model.getCssAjuste(), model.getFlagCopia(), model.getFormula());

		broker.execute();

		return model;
	}

	
	public QuizModel alterar(QuizModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE QUIZ SET FLAG_OBRIGATORIO = ?, ORDEM = ?, QUIZ_DEPENDENCIA_ID = ?, RESPOSTA_PADRAO = ?, QUIZ_GRUPO_ID = ?, TIPO_QUIZ_ID = ?, CSS_DIV = ?, CSS_COLUNAS = ?, FLAG_ATIVO = ?, QUIZ_RESPOSTA_DEPENDENTE_ID = ?, ROWINDEX_DEPENDENTE = ?, QTD_COLUNAS = ?, QUIZ_RESPOSTA_PADRAO_ID = ?, APELIDO = ?, FLAG_OUTROS = ?, MAXLENGTH = ?, ROWS = ?, QUERY_RESPOSTA_PADRAO = ?, FLAG_TAMANHO_FIXO = ?, CSS_AJUSTE = ?, FLAG_COPIA = ?, FORMULA = ? WHERE ID = ?", model.getFlagObrigatorio(), model.getOrdem(), model.getQuizDependenciaModel().getId(), model.getRespostaPadrao(), model.getQuizGrupoModel().getId(), model.getTipoQuizModel().getId(), model.getCssDiv(), model.getCssColunas(), model.getFlagAtivo(), TSUtil.tratarLong(model.getQuizRespostaDependenteModel().getId()), model.getRowindexDependente(), model.getQtdColunas(), TSUtil.tratarLong(model.getQuizRespostaPadraoModel().getId()), model.getApelido(), model.getFlagOutros(), model.getMaxlength(), model.getRows(), model.getQueryRespostaPadrao(), model.getFlagTamanhoFixo(), model.getCssAjuste(), model.getFlagCopia(), model.getFormula(), model.getId());

		broker.execute();

		return model;

	}

	
	public QuizModel excluir(QuizModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE QUIZ SET FLAG_ATIVO = FALSE WHERE ID = ?", model.getId());

		broker.execute();

		return model;

	}

	public void excluir(TipoQuizModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("DELETE FROM QUIZ WHERE TIPO_QUIZ_ID = ?", model.getId());

		broker.execute();

	}

	@SuppressWarnings("unchecked")
	public List<QuizValidadorModel> pesquisarValidadores(QuizModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT QV.ID, QV.QUIZ_ID, QV.VALIDADOR_ID, V.DESCRICAO, V.TIPO_AVISO, V.EXPRESSAO, V.AVISO, V.FLAG_ATIVO, V.QUERY, V.QUERY_VALIDACAO, QV.RESPOSTA_PADRAO FROM QUIZ_VALIDADOR QV, VALIDADOR V WHERE V.ID = QV.VALIDADOR_ID AND V.FLAG_ATIVO AND QV.QUIZ_ID = ? ORDER BY QV.ID", model.getId());

		return broker.getCollectionBean(QuizValidadorModel.class, "id", "quizModel.id", "validadorModel.id", "validadorModel.descricao", "validadorModel.tipoAviso", "validadorModel.expressao", "validadorModel.aviso", "validadorModel.flagAtivo", "validadorModel.query", "validadorModel.queryValidacao", "respostaPadrao");

	}

	public QuizValidadorModel inserirValidador(QuizValidadorModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("quiz_validador_id_seq"));

		broker.setSQL("INSERT INTO QUIZ_VALIDADOR (ID, QUIZ_ID, VALIDADOR_ID, RESPOSTA_PADRAO) VALUES (?, ?, ?, ?)", model.getId(), model.getQuizModel().getId(), model.getValidadorModel().getId(), model.getRespostaPadrao());

		broker.execute();

		return model;
	}

	public QuizValidadorModel alterarValidador(QuizValidadorModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE QUIZ_VALIDADOR SET RESPOSTA_PADRAO = ? WHERE ID = ?", model.getRespostaPadrao(), model.getId());

		broker.execute();

		return model;
	}

	public QuizValidadorModel excluirValidador(QuizValidadorModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("DELETE FROM QUIZ_VALIDADOR WHERE ID = ?", model.getId());

		broker.execute();

		return model;
	}

	public void excluirValidadores(QuizModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("DELETE FROM QUIZ_VALIDADOR WHERE QUIZ_ID = ?", model.getId());

		broker.execute();

	}

	public void executarQuery(String query, Long atendimentoId) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		if (query.contains("PM_ID")) {

			query = query.replaceAll("PM_ID", atendimentoId.toString());

		}

		broker.setSQL("DO $$ " + query + "$$;");

		broker.execute();

	}

}
