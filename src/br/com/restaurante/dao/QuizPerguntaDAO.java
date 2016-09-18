package br.com.restaurante.dao;

import java.sql.Timestamp;
import java.util.List;

import br.com.restaurante.model.ProcedimentoModel;
import br.com.restaurante.model.QuizModel;
import br.com.restaurante.model.QuizPerguntaArquetipoModel;
import br.com.restaurante.model.QuizPerguntaModel;
import br.com.restaurante.model.QuizRespostaModel;
import br.com.restaurante.model.TipoQuizModel;
import br.com.restaurante.util.Utilitario;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

public class QuizPerguntaDAO implements CrudDAO<QuizPerguntaModel> {

	public QuizPerguntaModel obterCrudModel(QuizPerguntaModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT Q.ID, Q.PERGUNTA, Q.APELIDO, Q.MAXLENGHT, Q.ROWS, Q.PATTERN, Q.TIPO_RESPOSTA_ID, OBTER_DATA_CADASTRO_LOG(Q.ID, 'QUIZ_PERGUNTA') DATA_CADASTRO, OBTER_USUARIO_CADASTRO_LOG(Q.ID, 'QUIZ_PERGUNTA'), Q.DATA_CADASTRO, U.NOME, Q.FLAG_OUTROS, Q.FLAG_ATIVO, Q.MEDIDA_ID, Q.FLAG_ARQUETIPO, Q.FORMULA, Q.ID_EXTERNO, Q.UNIDADE_MEDIDA_ID, Q.REFERENCIA, Q.ORDEM, Q.CODIGO_SUBEXAME, Q.PROCEDIMENTO_ID, Q.FLAG_PERCENTUAL, Q.GRUPO_SUBEXAME_ID, Q.CODIGO_FORMULA, Q.FLAG_OBRIGATORIO, Q.SUBEXAME_PAI_ID, Q.FORMULA_REFERENCIA_MINIMA, Q.FORMULA_REFERENCIA_MAXIMA, Q.CONSULTA_COMBO_SQL FROM QUIZ_PERGUNTA Q LEFT JOIN USUARIO U ON U.ID = Q.USUARIO_CADASTRO_ID WHERE Q.ID = ?", model.getId());

		return (QuizPerguntaModel) broker.getObjectBean(QuizPerguntaModel.class, "id", "pergunta", "apelido", "maxlenght", "rows", "pattern", "tipoRespostaModel.id", "dataCadastro", "usuarioCadastroModel.nome", "dataAtualizacao", "usuarioAtualizacaoModel.nome", "flagOutros", "flagAtivo", "medidaModel.id", "flagArquetipo", "formula", "idExterno", "unidadeMedidaModel.id", "referencia", "ordem", "codigoSubexame", "procedimentoModel.id", "flagPercentual", "grupoSubexameModel.id", "codigoFormula", "flagObrigatorio", "subexamePaiModel.id", "formulaReferenciaMinima", "formulaReferenciaMaxima", "consultaComboSQL");
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<QuizPerguntaModel> pesquisarLog(QuizPerguntaModel model) {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT Q.ID, Q.PERGUNTA, Q.APELIDO, Q.MAXLENGHT, Q.ROWS, Q.PATTERN, Q.TIPO_RESPOSTA_ID, Q.DATA_CADASTRO, U.NOME, Q.FLAG_OUTROS, Q.FLAG_ATIVO, Q.MEDIDA_ID, Q.FLAG_ARQUETIPO, Q.FORMULA, Q.ID_EXTERNO, Q.CONSULTA_COMBO_SQL FROM LOG.QUIZ_PERGUNTA Q LEFT JOIN USUARIO U ON U.ID = Q.USUARIO_CADASTRO_ID WHERE Q.ID = ? ORDER BY Q.DATA_CADASTRO DESC", model.getId());

		return broker.getCollectionBean(QuizPerguntaModel.class, "id", "pergunta", "apelido", "maxlenght", "rows", "pattern", "tipoRespostaModel.id", "dataCadastro", "usuarioCadastroModel.nome", "flagOutros", "flagAtivo", "medidaModel.id", "flagArquetipo", "formula", "idExterno", "consultaComboSQL");
		
	}

	@SuppressWarnings("unchecked")
	public List<QuizPerguntaModel> pesquisarCrudModel(QuizPerguntaModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT Q.ID, Q.PERGUNTA, Q.APELIDO, Q.MAXLENGHT, Q.ROWS, Q.PATTERN, Q.TIPO_RESPOSTA_ID, T.DESCRICAO, Q.FLAG_OUTROS, Q.FLAG_ATIVO, Q.CONSULTA_COMBO_SQL FROM QUIZ_PERGUNTA Q, TIPO_RESPOSTA T WHERE T.ID = Q.TIPO_RESPOSTA_ID AND (SEM_ACENTOS(Q.PERGUNTA) ILIKE SEM_ACENTOS(COALESCE(?,Q.PERGUNTA)) OR SEM_ACENTOS(COALESCE(Q.APELIDO, '')) ILIKE SEM_ACENTOS(COALESCE(?,COALESCE(Q.APELIDO, '')))) AND Q.TIPO_RESPOSTA_ID = COALESCE(?, Q.TIPO_RESPOSTA_ID) AND Q.FLAG_ATIVO = COALESCE(?, Q.FLAG_ATIVO) AND Q.FLAG_ARQUETIPO = COALESCE(?, Q.FLAG_ARQUETIPO) AND Q.ID = COALESCE(?, Q.ID) ORDER BY Q.PERGUNTA", Utilitario.getStringIlike(model.getPergunta(), true), Utilitario.getStringIlike(model.getPergunta(), true), TSUtil.tratarLong(model.getTipoRespostaModel().getId()), model.getFlagAtivo(), model.getFlagArquetipo(), model.getId());

		return broker.getCollectionBean(QuizPerguntaModel.class, "id", "pergunta", "apelido", "maxlenght", "rows", "pattern", "tipoRespostaModel.id", "tipoRespostaModel.descricao", "flagOutros", "flagAtivo", "consultaComboSQL");
	}

	@SuppressWarnings("unchecked")
	public List<QuizPerguntaModel> pesquisarAutocomplete(String query) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT Q.ID, Q.PERGUNTA, Q.APELIDO, Q.TIPO_RESPOSTA_ID, (SELECT TRQ.DESCRICAO FROM TIPO_RESPOSTA TRQ WHERE TRQ.ID = Q.TIPO_RESPOSTA_ID) TIPO_RESPOSTA, PESQUISAR_RESPOSTAS(Q.ID) FROM QUIZ_PERGUNTA Q WHERE Q.FLAG_ATIVO = TRUE AND (SEM_ACENTOS(Q.PERGUNTA) ILIKE SEM_ACENTOS(COALESCE(?,Q.PERGUNTA)) OR SEM_ACENTOS(Q.APELIDO) ILIKE SEM_ACENTOS(COALESCE(?,Q.APELIDO))) ORDER BY Q.PERGUNTA", Utilitario.getStringIlike(query, true), Utilitario.getStringIlike(query, true));

		return broker.getCollectionBean(QuizPerguntaModel.class, "id", "pergunta", "apelido", "tipoRespostaModel.id", "tipoRespostaModel.descricao", "descricaoRespostas");
	}

	@SuppressWarnings("unchecked")
	public List<QuizPerguntaModel> pesquisar(TipoQuizModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT Q.ID, Q.PERGUNTA, Q.APELIDO, Q.MAXLENGHT, Q.ROWS, Q.PATTERN, Q.TIPO_RESPOSTA_ID, T.DESCRICAO, Q.FLAG_OUTROS, Q.FLAG_ATIVO, Q.FORMULA, Q.CONSULTA_COMBO_SQL FROM QUIZ_PERGUNTA Q, TIPO_RESPOSTA T, QUIZ QUI WHERE T.ID = Q.TIPO_RESPOSTA_ID AND Q.ID = QUI.QUIZ_PERGUNTA_ID AND QUI.TIPO_QUIZ_ID = ? ORDER BY Q.PERGUNTA", model.getId());

		return broker.getCollectionBean(QuizPerguntaModel.class, "id", "pergunta", "apelido", "maxlenght", "rows", "pattern", "tipoRespostaModel.id", "tipoRespostaModel.descricao", "flagOutros", "flagAtivo", "formula", "consultaComboSQL");
	}

	@SuppressWarnings("unchecked")
	public List<QuizPerguntaArquetipoModel> pesquisarArquetipo(QuizPerguntaModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT QPA.ID, QPA.QUIZ_PERGUNTA_ID, QP.ID, QP.PERGUNTA, QP.APELIDO, QP.MAXLENGHT, QP.ROWS, QP.PATTERN, QP.TIPO_RESPOSTA_ID, T.DESCRICAO, QPA.ORDEM FROM QUIZ_PERGUNTA_ARQUETIPO QPA INNER JOIN QUIZ_PERGUNTA QP ON QP.ID = QPA.ARQUETIPO_ID INNER JOIN TIPO_RESPOSTA T ON T.ID = QP.TIPO_RESPOSTA_ID WHERE QP.FLAG_ATIVO AND QPA.QUIZ_PERGUNTA_ID = ? ORDER BY QPA.ORDEM, QP.PERGUNTA", model.getId());

		return broker.getCollectionBean(QuizPerguntaArquetipoModel.class, "id", "quizPerguntaModel.id", "arquetipoModel.id", "arquetipoModel.pergunta", "arquetipoModel.apelido", "arquetipoModel.maxlenght", "arquetipoModel.rows", "arquetipoModel.pattern", "arquetipoModel.tipoRespostaModel.id", "arquetipoModel.tipoRespostaModel.descricao", "ordem");
	}

	public QuizPerguntaModel inserirCrudModel(QuizPerguntaModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("quiz_pergunta_id_seq"));

		broker.setSQL("INSERT INTO QUIZ_PERGUNTA (ID, PERGUNTA, APELIDO, MAXLENGHT, ROWS, PATTERN, TIPO_RESPOSTA_ID, FLAG_OUTROS, FLAG_ATIVO, DATA_CADASTRO, USUARIO_CADASTRO_ID, MEDIDA_ID, FLAG_ARQUETIPO, FORMULA, ID_EXTERNO, UNIDADE_MEDIDA_ID, REFERENCIA, ORDEM, CODIGO_SUBEXAME, PROCEDIMENTO_ID, FLAG_PERCENTUAL, GRUPO_SUBEXAME_ID, CODIGO_FORMULA, FLAG_OBRIGATORIO, SUBEXAME_PAI_ID, FORMULA_REFERENCIA_MINIMA, FORMULA_REFERENCIA_MAXIMA, CONSULTA_COMBO_SQL) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", model.getId(), model.getPergunta(), model.getApelido(), model.getMaxlenght(), model.getRows(), model.getPattern(), model.getTipoRespostaModel().getId(), model.getFlagOutros(), model.getFlagAtivo(), new Timestamp(model.getDataCadastro().getTime()), model.getUsuarioCadastroModel().getId(), model.getMedidaModel().getId(), model.getFlagArquetipo(), model.getFormula(), model.getIdExterno(), model.getUnidadeMedidaModel().getId(), model.getReferencia(), model.getOrdem(), model.getCodigoSubexame(), model.getProcedimentoModel().getId(), model.getFlagPercentual(), model.getGrupoSubexameModel().getId(), model.getCodigoFormula(), model.getFlagObrigatorio(), model.getSubexamePaiModel().getId(), model.getFormulaReferenciaMinima(), model.getFormulaReferenciaMaxima(), model.getConsultaComboSQL());

		broker.execute();

		return model;
	}

	public QuizPerguntaArquetipoModel inserir(QuizPerguntaArquetipoModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("quiz_pergunta_arquetipo_id_seq"));

		broker.setSQL("INSERT INTO QUIZ_PERGUNTA_ARQUETIPO (ID, QUIZ_PERGUNTA_ID, ARQUETIPO_ID, ORDEM) VALUES(?, ?, ?, ?)", model.getId(), model.getQuizPerguntaModel().getId(), model.getArquetipoModel().getId(), model.getOrdem());

		broker.execute();

		return model;
	}

	public QuizPerguntaArquetipoModel alterar(QuizPerguntaArquetipoModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE QUIZ_PERGUNTA_ARQUETIPO SET ORDEM = ? WHERE ID = ?", model.getOrdem(), model.getId());

		broker.execute();

		return model;
	}

	public QuizPerguntaModel alterarCrudModel(QuizPerguntaModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE QUIZ_PERGUNTA SET PERGUNTA = ?, APELIDO = ?, MAXLENGHT = ?, ROWS = ?, PATTERN = ?, TIPO_RESPOSTA_ID = ?, FLAG_OUTROS = ?, FLAG_ATIVO = ?, MEDIDA_ID = ?, FLAG_ARQUETIPO = ?, FORMULA = ?, UNIDADE_MEDIDA_ID = ?, REFERENCIA = ?, ORDEM = ?, CODIGO_SUBEXAME = ?, PROCEDIMENTO_ID = ?, FLAG_PERCENTUAL = ?, GRUPO_SUBEXAME_ID = ?, CODIGO_FORMULA = ?, FLAG_OBRIGATORIO = ?, SUBEXAME_PAI_ID = ?, FORMULA_REFERENCIA_MINIMA = ?, FORMULA_REFERENCIA_MAXIMA = ?, CONSULTA_COMBO_SQL = ?, DATA_CADASTRO = ?, USUARIO_CADASTRO_ID = ? WHERE ID = ?", model.getPergunta(), model.getApelido(), model.getMaxlenght(), model.getRows(), model.getPattern(), model.getTipoRespostaModel().getId(), model.getFlagOutros(), model.getFlagAtivo(), model.getMedidaModel().getId(), model.getFlagArquetipo(), model.getFormula(), model.getUnidadeMedidaModel().getId(), model.getReferencia(), model.getOrdem(), model.getCodigoSubexame(), model.getProcedimentoModel().getId(), model.getFlagPercentual(), model.getGrupoSubexameModel().getId(), model.getCodigoFormula(), model.getFlagObrigatorio(), model.getSubexamePaiModel().getId(), model.getFormulaReferenciaMinima(), model.getFormulaReferenciaMaxima(), model.getConsultaComboSQL(), new Timestamp(model.getDataAtualizacao().getTime()), model.getUsuarioAtualizacaoModel().getId(), model.getId());

		broker.execute();

		return model;

	}

	public QuizPerguntaArquetipoModel excluir(QuizPerguntaArquetipoModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("DELETE FROM QUIZ_PERGUNTA_ARQUETIPO WHERE ID = ?", model.getId());

		broker.execute();

		return model;
	}
	
	public QuizRespostaModel excluir(QuizRespostaModel model) throws TSApplicationException {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("DELETE FROM QUIZ_RESPOSTA WHERE ID = ?", model.getId());
		
		broker.execute();
		
		return model;
	}
	
	public void excluirArquetipos(QuizPerguntaModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("DELETE FROM QUIZ_PERGUNTA_ARQUETIPO WHERE QUIZ_PERGUNTA_ID = ?", model.getId());

		broker.execute();

	}
	
	public QuizPerguntaModel excluirCrudModel(QuizPerguntaModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("DELETE FROM QUIZ_PERGUNTA WHERE ID = ?", model.getId());

		broker.execute();

		return model;
	}

	@SuppressWarnings("unchecked")
	public List<QuizRespostaModel> pesquisarRespostas(QuizPerguntaModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, RESPOSTA, QUIZ_PERGUNTA_ID, FLAG_DEFAULT, PONTOS, FORMULA_CONSEQUENCIA FROM QUIZ_RESPOSTA WHERE QUIZ_PERGUNTA_ID = ? ORDER BY ID", model.getId());

		return broker.getCollectionBean(QuizRespostaModel.class, "id", "resposta", "quizPerguntaModel.id", "flagDefault", "pontos", "formulaConsequencia");

	}
	
	@SuppressWarnings("unchecked")
	public List<QuizRespostaModel> pesquisarRespostas(QuizModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT QR.ID, QR.RESPOSTA, QR.QUIZ_PERGUNTA_ID, QR.FLAG_DEFAULT, QR.PONTOS, QR.FORMULA_CONSEQUENCIA FROM QUIZ Q, QUIZ_PERGUNTA QP, QUIZ_RESPOSTA QR WHERE Q.QUIZ_PERGUNTA_ID = QP.ID AND QP.ID = QR.QUIZ_PERGUNTA_ID AND Q.ID = ? ORDER BY QR.ID", model.getId());

		return broker.getCollectionBean(QuizRespostaModel.class, "id", "resposta", "quizPerguntaModel.id", "flagDefault", "pontos", "formulaConsequencia");

	}
	
	@SuppressWarnings("unchecked")
	public List<QuizRespostaModel> pesquisarRespostasQuery(QuizPerguntaModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL(model.getConsultaComboSQL());

		return broker.getCollectionBean(QuizRespostaModel.class, "id", "resposta", "quizPerguntaModel.id", "flagDefault", "pontos", "formulaConsequencia");

	}

	public void excluirRespostas(QuizPerguntaModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("DELETE FROM QUIZ_RESPOSTA WHERE QUIZ_PERGUNTA_ID = ?", model.getId());

		broker.execute();

	}

	public void inserir(QuizRespostaModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("quiz_resposta_id_seq"));

		broker.setSQL("INSERT INTO QUIZ_RESPOSTA (ID, RESPOSTA, QUIZ_PERGUNTA_ID, FLAG_DEFAULT, PONTOS, FORMULA_CONSEQUENCIA) VALUES (?,?,?,?,?,?)", model.getId(), model.getResposta(), model.getQuizPerguntaModel().getId(), model.getFlagDefault(), model.getPontos(), model.getFormulaConsequencia());

		broker.execute();

	}
	
	@SuppressWarnings("unchecked")
	public List<QuizPerguntaModel> pesquisarExamesLaboratoriaisCalculaveis() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT Q.ID, P.DESCRICAO || ' - ' || COALESCE(NULLIF(Q.APELIDO, ''), Q.PERGUNTA)  FROM QUIZ_PERGUNTA Q, PROCEDIMENTO P WHERE P.ID = Q.PROCEDIMENTO_ID AND Q.FLAG_ATIVO AND P.FLAG_ATIVO AND Q.TIPO_RESPOSTA_ID = 10 ORDER BY P.DESCRICAO || ' - ' || COALESCE(NULLIF(Q.APELIDO, ''), Q.PERGUNTA) ");

		return broker.getCollectionBean(QuizPerguntaModel.class, "id", "apelido");
	}
	
	@SuppressWarnings("unchecked")
	public List<QuizPerguntaModel> pesquisarFuncoesVitais() {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("SELECT Q.ID, COALESCE(NULLIF(Q.APELIDO, ''), Q.PERGUNTA) FROM QUIZ_PERGUNTA Q WHERE Q.FLAG_ATIVO AND IS_FUNCAO_VITAL(Q.ID) ORDER BY COALESCE(NULLIF(Q.APELIDO, ''), Q.PERGUNTA)");
		
		return broker.getCollectionBean(QuizPerguntaModel.class, "id", "apelido");
	}
	
	@SuppressWarnings("unchecked")
	public List<QuizPerguntaModel> pesquisarSubexames() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT Q.ID, COALESCE(NULLIF(Q.APELIDO, ''), Q.PERGUNTA) FROM QUIZ_PERGUNTA Q WHERE Q.FLAG_ATIVO AND Q.PROCEDIMENTO_ID IS NOT NULL ORDER BY COALESCE(NULLIF(Q.APELIDO, ''), Q.PERGUNTA)");

		return broker.getCollectionBean(QuizPerguntaModel.class, "id", "apelido");
	}
	
	@SuppressWarnings("unchecked")
	public List<QuizPerguntaModel> pesquisar(ProcedimentoModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT Q.ID, Q.PERGUNTA, Q.APELIDO, Q.MAXLENGHT, Q.ROWS, Q.PATTERN, Q.TIPO_RESPOSTA_ID, Q.USUARIO_CADASTRO_ID, U.NOME, Q.DATA_CADASTRO, Q.FLAG_OUTROS, Q.FLAG_ATIVO, Q.MEDIDA_ID, Q.FLAG_ARQUETIPO, Q.FORMULA, Q.ID_EXTERNO, Q.UNIDADE_MEDIDA_ID, Q.REFERENCIA, Q.ORDEM, Q.CODIGO_SUBEXAME, Q.PROCEDIMENTO_ID, Q.FLAG_PERCENTUAL, Q.GRUPO_SUBEXAME_ID, Q.CODIGO_FORMULA, Q.FLAG_OBRIGATORIO, Q.SUBEXAME_PAI_ID, Q.FORMULA_REFERENCIA_MINIMA, Q.FORMULA_REFERENCIA_MAXIMA, Q.CONSULTA_COMBO_SQL FROM QUIZ_PERGUNTA Q LEFT JOIN USUARIO U ON U.ID = Q.USUARIO_CADASTRO_ID WHERE Q.PROCEDIMENTO_ID = ? ORDER BY Q.ORDEM", model.getId());

		return broker.getCollectionBean(QuizPerguntaModel.class, "id", "pergunta", "apelido", "maxlenght", "rows", "pattern", "tipoRespostaModel.id", "usuarioCadastroModel.id", "usuarioCadastroModel.nome", "dataCadastro", "flagOutros", "flagAtivo", "medidaModel.id", "flagArquetipo", "formula", "idExterno", "unidadeMedidaModel.id", "referencia", "ordem", "codigoSubexame", "procedimentoModel.id", "flagPercentual", "grupoSubexameModel.id", "codigoFormula", "flagObrigatorio", "subexamePaiModel.id", "formulaReferenciaMinima", "formulaReferenciaMaxima", "consultaComboSQL");
	}

}
