package br.com.restaurante.dao;

import java.sql.Timestamp;
import java.util.List;

import br.com.restaurante.model.FuncionarioModel;
import br.com.restaurante.model.OrigemModel;
import br.com.restaurante.model.ProcedimentoModel;
import br.com.restaurante.model.QuizPerguntaModel;
import br.com.restaurante.model.SolicitacaoExameItemModel;
import br.com.restaurante.model.SolicitacaoExameItemResultadoModel;
import br.com.restaurante.model.SolicitacaoExameModel;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

public final class SolicitacaoExameDAO implements CrudDAO<SolicitacaoExameModel> {

	public SolicitacaoExameModel obterCrudModel(final SolicitacaoExameModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT SE.ID, SE.DATA_CADASTRO, SE.USUARIO_CADASTRO_ID, (SELECT U.NOME FROM USUARIO U WHERE U.ID = SE.USUARIO_CADASTRO_ID), SE.FUNCIONARIO_ID, FUN.MATRICULA, FUN.NOME, FUN.DATA_NASCIMENTO, FUN.SEXO, SE.OBSERVACAO, SE.SOLICITANTE_ID, (SELECT U.NOME FROM USUARIO U WHERE U.ID = SE.SOLICITANTE_ID), SE.FLAG_CONCLUIDO, SE.ORIGEM_ID, SE.ATENDIMENTO_ID, SE.DATA_AGENDAMENTO, SE.CID_ID, (SELECT C.CODIGO FROM CID C WHERE C.ID = SE.CID_ID), (SELECT C.DESCRICAO FROM CID C WHERE C.ID = SE.CID_ID) FROM SOLICITACAO_EXAME SE, FUNCIONARIO FUN WHERE FUN.ID = SE.FUNCIONARIO_ID AND SE.ID = ?", model.getId());

		return (SolicitacaoExameModel) broker.getObjectBean(SolicitacaoExameModel.class, "id", "dataCadastro", "usuarioCadastroModel.id", "usuarioCadastroModel.nome", "funcionarioModel.id", "decrypt.funcionarioModel.matricula", "decrypt.funcionarioModel.nome", "funcionarioModel.dataNascimento", "funcionarioModel.sexo", "observacao", "solicitanteModel.id", "solicitanteModel.nome", "flagConcluido", "origemModel.id", "atendimentoModel.id", "dataAgendamento", "cidModel.id", "cidModel.codigo", "cidModel.descricao");

	}

	@SuppressWarnings("unchecked")
	public List<SolicitacaoExameItemModel> pesquisarItens(final SolicitacaoExameModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT SEI.ID, SEI.ARQUIVO_RESULTADO, SEI.SOLICITACAO_EXAME_ID, (SELECT SE.ATENDIMENTO_ID FROM SOLICITACAO_EXAME SE WHERE SE.ID = SEI.SOLICITACAO_EXAME_ID), SEI.DATA_CADASTRO, SEI.USUARIO_CADASTRO_ID, (SELECT U.NOME FROM USUARIO U WHERE U.ID = SEI.USUARIO_CADASTRO_ID), SEI.PROCEDIMENTO_ID, PROC.CODIGO, PROC.DESCRICAO, PROC.FLAG_GRUPO, SEI.OBSERVACAO, SEI.LAUDO, SEI.DATA_REALIZACAO, SEI.FLAG_RESULTADO_NORMAL, SEI.FLAG_RESULTADO_ALTERADO, SEI.PERFIL_ID, (SELECT PER.DESCRICAO FROM PERFIL PER WHERE PER.ID = SEI.PERFIL_ID) FROM SOLICITACAO_EXAME_ITEM SEI INNER JOIN PROCEDIMENTO PROC ON PROC.ID = SEI.PROCEDIMENTO_ID WHERE SEI.SOLICITACAO_EXAME_ID = ? ORDER BY PROC.ORDEM, SEI.DATA_CADASTRO ", model.getId());

		return broker.getCollectionBean(SolicitacaoExameItemModel.class, "id", "arquivoResultado", "solicitacaoExameModel.id", "solicitacaoExameModel.atendimentoModel.id", "dataCadastro", "usuarioCadastroModel.id", "usuarioCadastroModel.nome", "procedimentoModel.id", "procedimentoModel.codigo", "procedimentoModel.descricao", "procedimentoModel.flagGrupo", "observacao", "laudo", "dataRealizacao", "flagResultadoNormal", "flagResultadoAlterado", "perfilModel.id", "perfilModel.descricao");

	}

	public SolicitacaoExameItemModel obterItem(final SolicitacaoExameItemModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT SEI.ID, SEI.ARQUIVO_RESULTADO, SEI.SOLICITACAO_EXAME_ID, (SELECT SE.ATENDIMENTO_ID FROM SOLICITACAO_EXAME SE WHERE SE.ID = SEI.SOLICITACAO_EXAME_ID), SEI.DATA_CADASTRO, SEI.USUARIO_CADASTRO_ID, (SELECT U.NOME FROM USUARIO U WHERE U.ID = SEI.USUARIO_CADASTRO_ID), SEI.PROCEDIMENTO_ID, PROC.CODIGO, PROC.DESCRICAO, SEI.OBSERVACAO, SEI.LAUDO FROM SOLICITACAO_EXAME_ITEM SEI INNER JOIN PROCEDIMENTO PROC ON PROC.ID = SEI.PROCEDIMENTO_ID WHERE SEI.ID = ? ORDER BY SEI.DATA_CADASTRO ", model.getId());

		return (SolicitacaoExameItemModel) broker.getObjectBean(SolicitacaoExameItemModel.class, "id", "arquivoResultado", "solicitacaoExameModel.id", "solicitacaoExameModel.atendimentoModel.id", "dataCadastro", "usuarioCadastroModel.id", "usuarioCadastroModel.nome", "procedimentoModel.id", "procedimentoModel.codigo", "procedimentoModel.descricao", "observacao", "laudo");

	}

	@SuppressWarnings("unchecked")
	public List<SolicitacaoExameModel> pesquisarCrudModel(final SolicitacaoExameModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT SE.ID, SE.DATA_CADASTRO, SE.USUARIO_CADASTRO_ID, (SELECT U.NOME FROM USUARIO U WHERE U.ID = SE.USUARIO_CADASTRO_ID), SE.FUNCIONARIO_ID, FUN.MATRICULA, FUN.NOME, FUN.DATA_NASCIMENTO, FUN.SEXO, SE.OBSERVACAO, SE.SOLICITANTE_ID, (SELECT U.NOME FROM USUARIO U WHERE U.ID = SE.SOLICITANTE_ID), SE.FLAG_CONCLUIDO, SE.ORIGEM_ID, SE.ATENDIMENTO_ID SE.DATA_AGENDAMENTO, SE.CID_ID, (SELECT C.CODIGO FROM CID C WHERE C.ID = SE.CID_ID), (SELECT C.DESCRICAO FROM CID C WHERE C.ID = SE.CID_ID) FROM SOLICITACAO_EXAME SE, FUNCIONARIO FUN WHERE FUN.ID = SE.FUNCIONARIO_ID ");

		return broker.getCollectionBean(SolicitacaoExameModel.class, "id", "dataCadastro", "usuarioCadastroModel.id", "usuarioCadastroModel.nome", "funcionarioModel.id", "decrypt.funcionarioModel.matricula", "decrypt.funcionarioModel.nome", "funcionarioModel.dataNascimento", "funcionarioModel.sexo", "observacao", "solicitanteModel.id", "solicitanteModel.nome", "flagConcluido", "origemModel.id", "atendimentoModel.id", "dataAgendamento", "cidModel.id", "cidModel.codigo", "cidModel.descricao");

	}

	@SuppressWarnings("unchecked")
	public List<SolicitacaoExameModel> pesquisar(final FuncionarioModel model, OrigemModel origemModel) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT SE.ID, SE.DATA_CADASTRO, SE.USUARIO_CADASTRO_ID, (SELECT U.NOME FROM USUARIO U WHERE U.ID = SE.USUARIO_CADASTRO_ID), SE.FUNCIONARIO_ID, FUN.MATRICULA, FUN.NOME, FUN.DATA_NASCIMENTO, FUN.SEXO, SE.OBSERVACAO, SE.SOLICITANTE_ID, (SELECT U.NOME FROM USUARIO U WHERE U.ID = SE.SOLICITANTE_ID), SE.FLAG_CONCLUIDO, SE.ORIGEM_ID, SE.ATENDIMENTO_ID, SE.DATA_AGENDAMENTO, (SELECT CASE WHEN LENGTH(X.DESCRICAO_PROCEDIMENTO) <= 22 THEN X.DESCRICAO_PROCEDIMENTO ELSE SUBSTRING(X.DESCRICAO_PROCEDIMENTO, 0, 22) || '...' END DESCRICAO_PROCEDIMENTO FROM (SELECT REPLACE(REPLACE(REPLACE(REPLACE(ARRAY(SELECT DISTINCT COALESCE(P.DESCRICAO, PRO.DESCRICAO) FROM SOLICITACAO_EXAME_ITEM SEI LEFT JOIN PERFIL P ON SEI.PERFIL_ID = P.ID INNER JOIN PROCEDIMENTO PRO ON PRO.ID = SEI.PROCEDIMENTO_ID WHERE SEI.SOLICITACAO_EXAME_ID = SE.ID ORDER BY COALESCE(P.DESCRICAO, PRO.DESCRICAO))::TEXT, '{', ''), '}', ''), ',', ', '), '\"', '') DESCRICAO_PROCEDIMENTO) X) DESCRICAO_PROCEDIMENTO, SE.CID_ID, (SELECT C.CODIGO FROM CID C WHERE C.ID = SE.CID_ID), (SELECT C.DESCRICAO FROM CID C WHERE C.ID = SE.CID_ID) FROM SOLICITACAO_EXAME SE INNER JOIN FUNCIONARIO FUN ON FUN.ID = SE.FUNCIONARIO_ID LEFT JOIN ATENDIMENTO A ON A.ID = SE.ATENDIMENTO_ID WHERE SE.ORIGEM_ID = COALESCE(?, SE.ORIGEM_ID) AND SE.FUNCIONARIO_ID = ? ORDER BY SE.ID DESC, SE.DATA_CADASTRO DESC", origemModel.getId(), model.getId());

		return broker.getCollectionBean(SolicitacaoExameModel.class, "id", "dataCadastro", "usuarioCadastroModel.id", "usuarioCadastroModel.nome", "funcionarioModel.id", "decrypt.funcionarioModel.matricula", "decrypt.funcionarioModel.nome", "funcionarioModel.dataNascimento", "funcionarioModel.sexo", "observacao", "solicitanteModel.id", "solicitanteModel.nome", "flagConcluido", "origemModel.id", "atendimentoModel.id", "dataAgendamento", "descricaoProcedimento", "cidModel.id", "cidModel.codigo", "cidModel.descricao");

	}

	@SuppressWarnings("unchecked")
	public List<SolicitacaoExameItemResultadoModel> pesquisarResultados(final SolicitacaoExameModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT SER.ID, SER.DATA_CADASTRO, SER.USUARIO_CADASTRO_ID, (SELECT U.NOME FROM USUARIO U WHERE U.ID = SER.USUARIO_CADASTRO_ID), SER.DATA_ATUALIZACAO, SER.USUARIO_ATUALIZACAO_ID, (SELECT U.NOME FROM USUARIO U WHERE U.ID = SER.USUARIO_ATUALIZACAO_ID), SER.SOLICITACAO_EXAME_ITEM_ID, SER.RESULTADO, SER.OBSERVACAO FROM SOLICITACAO_EXAME_ITEM_RESULTADO SER WHERE SER.SOLICITACAO_EXAME_ITEM_ID = ? ORDER BY SER.ID", model.getId());

		return broker.getCollectionBean(SolicitacaoExameItemResultadoModel.class, "id", "dataCadastro", "usuarioCadastroModel.id", "usuarioCadastroModel.nome", "dataAtualizacao", "usuarioAtualizacaoModel.id", "usuarioAtualizacaoModel.nome", "solicitacaoExameModel.id", "resultado", "observacao");

	}

	public SolicitacaoExameModel inserirCrudModel(final SolicitacaoExameModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("solicitacao_exame_id_seq"));

		broker.setSQL("INSERT INTO SOLICITACAO_EXAME(ID, DATA_CADASTRO, USUARIO_CADASTRO_ID, SOLICITANTE_ID, FUNCIONARIO_ID, OBSERVACAO, ORIGEM_ID, ATENDIMENTO_ID, DATA_AGENDAMENTO, CID_ID, FLAG_CONCLUIDO) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, COALESCE(?, FALSE))", model.getId(), new Timestamp(model.getDataCadastro().getTime()), model.getUsuarioCadastroModel().getId(), model.getSolicitanteModel().getId(), model.getFuncionarioModel().getId(), model.getObservacao(), model.getOrigemModel().getId(), model.getAtendimentoModel().getId(), new Timestamp(model.getDataAgendamento().getTime()), model.getCidModel().getId(), model.getFlagConcluido());

		broker.execute();

		return model;

	}

	public SolicitacaoExameItemModel inserir(final SolicitacaoExameItemModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("solicitacao_exame_item_id_seq"));

		broker.setSQL("INSERT INTO SOLICITACAO_EXAME_ITEM(ID, SOLICITACAO_EXAME_ID, DATA_CADASTRO, USUARIO_CADASTRO_ID, PROCEDIMENTO_ID, OBSERVACAO, DATA_REALIZACAO, FLAG_RESULTADO_NORMAL, FLAG_RESULTADO_ALTERADO, PERFIL_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", model.getId(), model.getSolicitacaoExameModel().getId(), new Timestamp(model.getDataCadastro().getTime()), model.getUsuarioCadastroModel().getId(), model.getProcedimentoModel().getId(), model.getObservacao(), model.getDataRealizacao(), model.getFlagResultadoNormal(), model.getFlagResultadoAlterado(), model.getPerfilModel().getId());

		broker.execute();

		return model;

	}
	
	public SolicitacaoExameItemResultadoModel inserir(final SolicitacaoExameItemResultadoModel model) throws TSApplicationException {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		model.setId(broker.getSequenceNextValue("solicitacao_exame_item_resultado_id_seq"));
		
		broker.setSQL("INSERT INTO SOLICITACAO_EXAME_ITEM_RESULTADO (ID, SOLICITACAO_EXAME_ITEM_ID, RESULTADO, PERCENTUAL, OBSERVACAO, QUIZ_PERGUNTA_ID, ID_RESPOSTA, DATA_CADASTRO, USUARIO_CADASTRO_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", model.getId(), model.getSolicitacaoExameItemModel().getId(), model.getResultado(), model.getPercentual(), model.getObservacao(), model.getQuizPerguntaModel().getId(), model.getIdResposta(), new Timestamp(model.getDataCadastro().getTime()), model.getUsuarioCadastroModel().getId());
		
		broker.execute();
		
		return model;
		
	}

	public void alterar(final SolicitacaoExameItemResultadoModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE SOLICITACAO_EXAME_ITEM_RESULTADO SET RESULTADO = ?, OBSERVACAO = ?, PERCENTUAL = ?, DATA_ATUALIZACAO = ?, USUARIO_ATUALIZACAO_ID = ? WHERE ID = ?", model.getResultado(), model.getObservacao(), model.getPercentual(), new Timestamp(model.getDataAtualizacao().getTime()), model.getUsuarioAtualizacaoModel().getId(), model.getId());

		broker.execute();

	}
	
	public void concluir(final SolicitacaoExameModel model) throws TSApplicationException {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("UPDATE SOLICITACAO_EXAME SET FLAG_CONCLUIDO = TRUE WHERE ID = ?", model.getId());
		
		broker.execute();
		
	}

	public void alterarLaudo(final SolicitacaoExameItemModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE SOLICITACAO_EXAME_ITEM SET LAUDO = ?, DATA_ATUALIZACAO = ?, USUARIO_ATUALIZACAO_ID = ? WHERE ID = ?", model.getLaudo(), new Timestamp(model.getDataAtualizacao().getTime()), model.getUsuarioAtualizacaoModel().getId(), model.getId());

		broker.execute();

	}

	public SolicitacaoExameModel alterarCrudModel(final SolicitacaoExameModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE SOLICITACAO_EXAME SET CID_ID = ?, OBSERVACAO = ?, SOLICITANTE_ID = ?, DATA_AGENDAMENTO = ? WHERE ID = ?", model.getCidModel().getId(), model.getObservacao(), model.getSolicitanteModel().getId(), new Timestamp(model.getDataAgendamento().getTime()), model.getId());

		broker.execute();

		return model;

	}

	public SolicitacaoExameItemModel alterar(final SolicitacaoExameItemModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE SOLICITACAO_EXAME_ITEM SET OBSERVACAO = ?, DATA_REALIZACAO = ?, FLAG_RESULTADO_NORMAL = ?, FLAG_RESULTADO_ALTERADO = ? WHERE ID = ?", model.getObservacao(), model.getDataRealizacao(), model.getFlagResultadoNormal(), model.getFlagResultadoAlterado(), model.getId());

		broker.execute();

		return model;

	}
	
	public SolicitacaoExameItemModel salvarArquivoResultado(final SolicitacaoExameItemModel model) throws TSApplicationException {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("UPDATE SOLICITACAO_EXAME_ITEM SET ARQUIVO_RESULTADO = ? WHERE ID = ?", model.getArquivoResultado(), model.getId());
		
		broker.execute();
		
		return model;
		
	}

	@Override
	public SolicitacaoExameModel excluirCrudModel(SolicitacaoExameModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("DELETE FROM SOLICITACAO_EXAME WHERE ID = ?", model.getId());

		broker.execute();

		return model;
	}

	public SolicitacaoExameItemModel excluir(SolicitacaoExameItemModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("DELETE FROM SOLICITACAO_EXAME_ITEM WHERE ID = ?", model.getId());

		broker.execute();

		return model;
	}

	@SuppressWarnings("unchecked")
	public List<SolicitacaoExameItemModel> pesquisarProcedimentosFilhos(final SolicitacaoExameItemModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT P.ID, P.DESCRICAO, FLAG_ATIVO, P.FLAG_GRUPO, NULL FROM PROCEDIMENTO P WHERE PROCEDIMENTO_ID = ? AND FLAG_ATIVO ORDER BY P.ORDEM, P.DESCRICAO", model.getProcedimentoModel().getId());

		return broker.getCollectionBean(SolicitacaoExameItemModel.class, "procedimentoModel.id", "procedimentoModel.descricao", "procedimentoModel.flagAtivo", "procedimentoModel.flagGrupo", "perfilModel.id");

	}

	@SuppressWarnings("unchecked")
	public List<SolicitacaoExameItemResultadoModel> pesquisarResultadosExamesPais(final SolicitacaoExameItemModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT SER.ID, SER.SOLICITACAO_EXAME_ITEM_ID, QP.ID, QP.PERGUNTA, QP.APELIDO, QP.FORMULA, QP.FORMULA_REFERENCIA_MINIMA, QP.FORMULA_REFERENCIA_MAXIMA, QP.TIPO_RESPOSTA_ID, QP.UNIDADE_MEDIDA_ID, (SELECT UM.DESCRICAO FROM UNIDADE_MEDIDA UM WHERE UM.ID = QP.UNIDADE_MEDIDA_ID), QP.FLAG_PERCENTUAL, QP.REFERENCIA, QP.ORDEM, QP.CODIGO_SUBEXAME, QP.PROCEDIMENTO_ID, QP.FLAG_OBRIGATORIO, SER.RESULTADO, SER.RESULTADO, SER.PERCENTUAL, SER.PERCENTUAL, SER.OBSERVACAO, SER.OBSERVACAO, QP.GRUPO_SUBEXAME_ID, (SELECT GSE.DESCRICAO FROM QUIZ_GRUPO GSE WHERE GSE.ID = QP.GRUPO_SUBEXAME_ID), EXISTS(SELECT 1 FROM QUIZ_PERGUNTA QP2 WHERE QP2.SUBEXAME_PAI_ID = QP.ID AND QP2.FLAG_ATIVO) FROM SOLICITACAO_EXAME_ITEM_RESULTADO SER, SOLICITACAO_EXAME_ITEM SEI, SOLICITACAO_EXAME SE, QUIZ_PERGUNTA QP WHERE SEI.ID = SER.SOLICITACAO_EXAME_ITEM_ID AND SEI.SOLICITACAO_EXAME_ID = SE.ID AND QP.ID = SER.QUIZ_PERGUNTA_ID AND QP.FLAG_ATIVO AND SER.SOLICITACAO_EXAME_ITEM_ID = ? AND QP.PROCEDIMENTO_ID = ? AND QP.SUBEXAME_PAI_ID IS NULL ORDER BY COALESCE((SELECT QG.ORDEM FROM QUIZ_GRUPO QG WHERE QG.ID = QP.GRUPO_SUBEXAME_ID), 999), COALESCE(QP.ORDEM, 999), QP.PERGUNTA", model.getSolicitacaoRaizAux().getId(), model.getProcedimentoModel().getId());

		return broker.getCollectionBean(SolicitacaoExameItemResultadoModel.class, "id", "solicitacaoExameItemModel.id", "quizPerguntaModel.id", "quizPerguntaModel.pergunta", "quizPerguntaModel.apelido", "quizPerguntaModel.formula", "quizPerguntaModel.formulaReferenciaMinima", "quizPerguntaModel.formulaReferenciaMaxima", "quizPerguntaModel.tipoRespostaModel.id", "quizPerguntaModel.unidadeMedidaModel.id", "quizPerguntaModel.unidadeMedidaModel.descricao", "quizPerguntaModel.flagPercentual", "quizPerguntaModel.referencia", "quizPerguntaModel.ordem", "quizPerguntaModel.codigoSubexame", "quizPerguntaModel.procedimentoModel.id", "quizPerguntaModel.flagObrigatorio", "resultado", "resultadoAnterior", "percentual", "percentualAnterior", "observacao", "observacaoAnterior", "quizPerguntaModel.grupoSubexameModel.id", "quizPerguntaModel.grupoSubexameModel.descricao", "quizPerguntaModel.flagPossuiFilhos");

	}

	@SuppressWarnings("unchecked")
	public List<SolicitacaoExameItemResultadoModel> pesquisarResultadosExamesFilhos(final SolicitacaoExameItemResultadoModel resultado) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT SER.ID, SER.SOLICITACAO_EXAME_ITEM_ID, QP.ID, QP.PERGUNTA, QP.APELIDO, QP.FORMULA, QP.FORMULA_REFERENCIA_MINIMA, QP.FORMULA_REFERENCIA_MAXIMA, QP.TIPO_RESPOSTA_ID, QP.UNIDADE_MEDIDA_ID, (SELECT UM.DESCRICAO FROM UNIDADE_MEDIDA UM WHERE UM.ID = QP.UNIDADE_MEDIDA_ID), QP.FLAG_PERCENTUAL, QP.REFERENCIA, QP.ORDEM, QP.CODIGO_SUBEXAME, QP.PROCEDIMENTO_ID, QP.FLAG_OBRIGATORIO, SER.RESULTADO, SER.RESULTADO, SER.PERCENTUAL, SER.PERCENTUAL, SER.OBSERVACAO, SER.OBSERVACAO, QP.GRUPO_SUBEXAME_ID, (SELECT GSE.DESCRICAO FROM QUIZ_GRUPO GSE WHERE GSE.ID = QP.GRUPO_SUBEXAME_ID), EXISTS(SELECT 1 FROM QUIZ_PERGUNTA QP2 WHERE QP2.SUBEXAME_PAI_ID = QP.ID AND QP2.FLAG_ATIVO) FROM SOLICITACAO_EXAME_ITEM_RESULTADO SER, SOLICITACAO_EXAME_ITEM SEI, SOLICITACAO_EXAME SE, QUIZ_PERGUNTA QP WHERE SEI.ID = SER.SOLICITACAO_EXAME_ITEM_ID AND SEI.SOLICITACAO_EXAME_ID = SE.ID AND QP.ID = SER.QUIZ_PERGUNTA_ID AND QP.FLAG_ATIVO AND SER.SOLICITACAO_EXAME_ITEM_ID = ? AND QP.SUBEXAME_PAI_ID = ? ORDER BY COALESCE((SELECT QG.ORDEM FROM QUIZ_GRUPO QG WHERE QG.ID = QP.GRUPO_SUBEXAME_ID), 999), COALESCE(QP.ORDEM, 999), SER.QUIZ_PERGUNTA_ID", resultado.getSolicitacaoExameItemModel().getSolicitacaoRaizAux().getId(), resultado.getQuizPerguntaModel().getId());

		return broker.getCollectionBean(SolicitacaoExameItemResultadoModel.class, "id", "solicitacaoExameItemModel.id", "quizPerguntaModel.id", "quizPerguntaModel.pergunta", "quizPerguntaModel.apelido", "quizPerguntaModel.formula", "quizPerguntaModel.formulaReferenciaMinima", "quizPerguntaModel.formulaReferenciaMaxima", "quizPerguntaModel.tipoRespostaModel.id", "quizPerguntaModel.unidadeMedidaModel.id", "quizPerguntaModel.unidadeMedidaModel.descricao", "quizPerguntaModel.flagPercentual", "quizPerguntaModel.referencia", "quizPerguntaModel.ordem", "quizPerguntaModel.codigoSubexame", "quizPerguntaModel.procedimentoModel.id", "quizPerguntaModel.flagObrigatorio", "resultado", "resultadoAnterior", "percentual", "percentualAnterior", "observacao", "observacaoAnterior", "quizPerguntaModel.grupoSubexameModel.id", "quizPerguntaModel.grupoSubexameModel.descricao", "quizPerguntaModel.flagPossuiFilhos");

	}
	
	@SuppressWarnings("unchecked")
	public List<SolicitacaoExameItemResultadoModel> pesquisarResultadosExamesFilhosInicial(final SolicitacaoExameItemResultadoModel resultado) {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("SELECT QP.ID, QP.PERGUNTA, QP.APELIDO, QP.FORMULA, QP.FORMULA_REFERENCIA_MINIMA, QP.FORMULA_REFERENCIA_MAXIMA, QP.TIPO_RESPOSTA_ID, QP.UNIDADE_MEDIDA_ID, (SELECT UM.DESCRICAO FROM UNIDADE_MEDIDA UM WHERE UM.ID = QP.UNIDADE_MEDIDA_ID), QP.FLAG_PERCENTUAL, QP.REFERENCIA, QP.ORDEM, QP.CODIGO_SUBEXAME, QP.PROCEDIMENTO_ID, QP.FLAG_OBRIGATORIO, QP.GRUPO_SUBEXAME_ID, (SELECT GSE.DESCRICAO FROM QUIZ_GRUPO GSE WHERE GSE.ID = QP.GRUPO_SUBEXAME_ID), EXISTS(SELECT 1 FROM QUIZ_PERGUNTA QP2 WHERE QP2.SUBEXAME_PAI_ID = QP.ID AND QP2.FLAG_ATIVO) FROM QUIZ_PERGUNTA QP WHERE QP.FLAG_ATIVO AND QP.SUBEXAME_PAI_ID = ? ORDER BY COALESCE((SELECT QG.ORDEM FROM QUIZ_GRUPO QG WHERE QG.ID = QP.GRUPO_SUBEXAME_ID), 999), COALESCE(QP.ORDEM, 999), QP.ID", resultado.getQuizPerguntaModel().getId());
		
		return broker.getCollectionBean(SolicitacaoExameItemResultadoModel.class, "quizPerguntaModel.id", "quizPerguntaModel.pergunta", "quizPerguntaModel.apelido", "quizPerguntaModel.formula", "quizPerguntaModel.formulaReferenciaMinima", "quizPerguntaModel.formulaReferenciaMaxima", "quizPerguntaModel.tipoRespostaModel.id", "quizPerguntaModel.unidadeMedidaModel.id", "quizPerguntaModel.unidadeMedidaModel.descricao", "quizPerguntaModel.flagPercentual", "quizPerguntaModel.referencia", "quizPerguntaModel.ordem", "quizPerguntaModel.codigoSubexame", "quizPerguntaModel.procedimentoModel.id", "quizPerguntaModel.flagObrigatorio", "quizPerguntaModel.grupoSubexameModel.id", "quizPerguntaModel.grupoSubexameModel.descricao", "quizPerguntaModel.flagPossuiFilhos");
		
	}

	@SuppressWarnings("unchecked")
	public List<SolicitacaoExameItemResultadoModel> pesquisarResultadosExames(final QuizPerguntaModel exame, FuncionarioModel paciente) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT SER.ID, SER.SOLICITACAO_EXAME_ITEM_ID, QP.ID, QP.PERGUNTA, QP.APELIDO, QP.FORMULA, QP.FORMULA_REFERENCIA_MINIMA, QP.FORMULA_REFERENCIA_MAXIMA, QP.TIPO_RESPOSTA_ID, QP.UNIDADE_MEDIDA_ID, (SELECT UM.DESCRICAO FROM UNIDADE_MEDIDA UM WHERE UM.ID = QP.UNIDADE_MEDIDA_ID), QP.FLAG_PERCENTUAL, QP.REFERENCIA, QP.ORDEM, QP.CODIGO_SUBEXAME, QP.PROCEDIMENTO_ID, QP.FLAG_OBRIGATORIO, SER.RESULTADO, SER.RESULTADO, SER.PERCENTUAL, SER.PERCENTUAL, SER.OBSERVACAO, SER.OBSERVACAO, QP.GRUPO_SUBEXAME_ID, (SELECT GSE.DESCRICAO FROM QUIZ_GRUPO GSE WHERE GSE.ID = QP.GRUPO_SUBEXAME_ID), SER.DATA_ATUALIZACAO FROM SOLICITACAO_EXAME_ITEM_RESULTADO SER, SOLICITACAO_EXAME_ITEM SEI, SOLICITACAO_EXAME SE, QUIZ_PERGUNTA QP WHERE SEI.ID = SER.SOLICITACAO_EXAME_ITEM_ID AND SEI.SOLICITACAO_EXAME_ID = SE.ID AND QP.ID = SER.QUIZ_PERGUNTA_ID AND QP.FLAG_ATIVO AND SER.QUIZ_PERGUNTA_ID = ? AND SE.FUNCIONARIO_ID = ? AND NULLIF(TRIM(SER.RESULTADO), '') IS NOT NULL ORDER BY SER.DATA_ATUALIZACAO DESC", exame.getId(), paciente.getId());

		return broker.getCollectionBean(SolicitacaoExameItemResultadoModel.class, "id", "solicitacaoExameItemModel.id", "quizPerguntaModel.id", "quizPerguntaModel.pergunta", "quizPerguntaModel.apelido", "quizPerguntaModel.formula", "quizPerguntaModel.formulaReferenciaMinima", "quizPerguntaModel.formulaReferenciaMaxima", "quizPerguntaModel.tipoRespostaModel.id", "quizPerguntaModel.unidadeMedidaModel.id", "quizPerguntaModel.unidadeMedidaModel.descricao", "quizPerguntaModel.flagPercentual", "quizPerguntaModel.referencia", "quizPerguntaModel.ordem", "quizPerguntaModel.codigoSubexame", "quizPerguntaModel.procedimentoModel.id", "quizPerguntaModel.flagObrigatorio", "resultado", "resultadoAnterior", "percentual", "percentualAnterior", "observacao", "observacaoAnterior", "quizPerguntaModel.grupoSubexameModel.id", "quizPerguntaModel.grupoSubexameModel.descricao", "dataAtualizacao");

	}

	@SuppressWarnings("unchecked")
	public List<SolicitacaoExameItemModel> pesquisarUltimos(FuncionarioModel model, Long limit) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT SEI.ID, SEI.ARQUIVO_RESULTADO, P.ID, P.DESCRICAO, SE.ID SOLICITACAO_EXAME_ID, SE.FUNCIONARIO_ID, SEI.DATA_CADASTRO, (SELECT U.NOME FROM USUARIO U WHERE U.ID = SE.SOLICITANTE_ID) SOLICITANTE FROM SOLICITACAO_EXAME SE, SOLICITACAO_EXAME_ITEM SEI, PROCEDIMENTO P WHERE SE.ID = SEI.SOLICITACAO_EXAME_ID AND P.ID = SEI.PROCEDIMENTO_ID AND SE.FUNCIONARIO_ID = ? ORDER BY SEI.ID DESC LIMIT ?", model.getId(), limit);

		return broker.getCollectionBean(SolicitacaoExameItemModel.class, "id", "arquivoResultado", "procedimentoModel.id", "procedimentoModel.descricao", "solicitacaoExameModel.id", "solicitacaoExameModel.funcionarioModel.id", "dataCadastro", "solicitacaoExameModel.solicitanteModel.nome");

	}

	public SolicitacaoExameItemResultadoModel obterResultado(final FuncionarioModel funcionarioModel, Long id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT SEIR.ID, SEIR.DATA_ATUALIZACAO, SEIR.RESULTADO, SEIR.PERCENTUAL, SEIR.OBSERVACAO FROM SOLICITACAO_EXAME_ITEM_RESULTADO SEIR, SOLICITACAO_EXAME_ITEM SEI, SOLICITACAO_EXAME SE, QUIZ_PERGUNTA QP WHERE SEIR.SOLICITACAO_EXAME_ITEM_ID = SEI.ID AND SEI.SOLICITACAO_EXAME_ID = SE.ID AND SEIR.QUIZ_PERGUNTA_ID = QP.ID AND NULLIF(SEIR.RESULTADO, '') IS NOT NULL AND SE.FUNCIONARIO_ID = ? AND QP.ID = ? ORDER BY SEIR.DATA_ATUALIZACAO DESC LIMIT 1", funcionarioModel.getId(), id);

		return (SolicitacaoExameItemResultadoModel) broker.getObjectBean(SolicitacaoExameItemResultadoModel.class, "id", "dataAtualizacao", "resultado", "percentual", "observacao");

	}
	
	public void excluirRespostas(SolicitacaoExameItemModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("DELETE FROM SOLICITACAO_EXAME_ITEM_RESULTADO WHERE SOLICITACAO_EXAME_ITEM_ID = ? AND QUIZ_ID IS NOT NULL", model.getId());

		broker.execute();

	}
	
	@SuppressWarnings("unchecked")
	public List<SolicitacaoExameItemResultadoModel> pesquisarRespostas(final SolicitacaoExameItemModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT SEIR.ID, SEIR.SOLICITACAO_EXAME_ITEM_ID, SEIR.QUIZ_ID, Q.QUIZ_GRUPO_ID, Q.ORDEM, Q.FLAG_COPIA, Q.FORMULA, Q.QUIZ_PERGUNTA_ID, QP.PERGUNTA, SEIR.RESULTADO, SEIR.OBSERVACAO, SEIR.UNIDADE, SEIR.ID_RESPOSTA FROM SOLICITACAO_EXAME_ITEM_RESULTADO SEIR, QUIZ Q, QUIZ_PERGUNTA QP WHERE Q.ID = SEIR.QUIZ_ID AND QP.ID = Q.QUIZ_PERGUNTA_ID AND QP.FLAG_ATIVO AND SEIR.SOLICITACAO_EXAME_ITEM_ID = ? ORDER BY SEIR.ID", model.getId());

		return broker.getCollectionBean(SolicitacaoExameItemResultadoModel.class, "id", "solicitacaoExameItemModel.id", "quizModel.id", "quizModel.quizGrupoModel.id", "quizModel.ordem", "quizModel.flagCopia", "quizModel.formula", "quizModel.quizPerguntaModel.id", "quizModel.quizPerguntaModel.pergunta", "resultado", "observacao", "unidade", "idResposta");

	}
	
	@SuppressWarnings("unchecked")
	public List<ProcedimentoModel> pesquisarProcedimentos(final SolicitacaoExameItemModel model) {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("WITH RECURSIVE ARVORE_PROCEDIMENTOS(ID, DESCRICAO, FLAG_POSSUI_SUBEXAMES) AS (SELECT ID, DESCRICAO, EXISTS(SELECT 1 FROM QUIZ_PERGUNTA QP WHERE QP.PROCEDIMENTO_ID = P.ID) FROM PROCEDIMENTO P WHERE FLAG_ATIVO AND PROCEDIMENTO_ID = ? UNION ALL SELECT P.ID, P.DESCRICAO, EXISTS(SELECT 1 FROM QUIZ_PERGUNTA QP WHERE QP.PROCEDIMENTO_ID = P.ID) FROM PROCEDIMENTO P INNER JOIN ARVORE_PROCEDIMENTOS AP ON P.PROCEDIMENTO_ID = AP.ID WHERE P.FLAG_ATIVO) SELECT ID, DESCRICAO, FLAG_POSSUI_SUBEXAMES FROM ARVORE_PROCEDIMENTOS UNION ALL SELECT ID, DESCRICAO, EXISTS(SELECT 1 FROM QUIZ_PERGUNTA QP WHERE QP.PROCEDIMENTO_ID = P.ID) FROM PROCEDIMENTO P WHERE ID = ?", model.getProcedimentoModel().getId(), model.getProcedimentoModel().getId());
		
		return broker.getCollectionBean(ProcedimentoModel.class, "id", "descricao", "flagPossuiSubexames");
		
	}
	
	@SuppressWarnings("unchecked")
	public List<SolicitacaoExameItemResultadoModel> pesquisarSubexamesPais(final ProcedimentoModel model) {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("SELECT NULL, NULL, QP.ID, QP.PERGUNTA, QP.APELIDO, QP.FORMULA, QP.FORMULA_REFERENCIA_MINIMA, QP.FORMULA_REFERENCIA_MAXIMA, QP.TIPO_RESPOSTA_ID, QP.UNIDADE_MEDIDA_ID, (SELECT UM.DESCRICAO FROM UNIDADE_MEDIDA UM WHERE UM.ID = QP.UNIDADE_MEDIDA_ID), QP.FLAG_PERCENTUAL, QP.REFERENCIA, QP.ORDEM, QP.CODIGO_SUBEXAME, QP.PROCEDIMENTO_ID, QP.FLAG_OBRIGATORIO, QP.GRUPO_SUBEXAME_ID, (SELECT GSE.DESCRICAO FROM QUIZ_GRUPO GSE WHERE GSE.ID = QP.GRUPO_SUBEXAME_ID), EXISTS(SELECT 1 FROM QUIZ_PERGUNTA QP2 WHERE QP2.SUBEXAME_PAI_ID = QP.ID AND QP2.FLAG_ATIVO) FROM QUIZ_PERGUNTA QP WHERE QP.FLAG_ATIVO AND QP.PROCEDIMENTO_ID = ? AND QP.SUBEXAME_PAI_ID IS NULL ORDER BY QP.ORDEM", model.getId());
		
		return broker.getCollectionBean(SolicitacaoExameItemResultadoModel.class, "id", "solicitacaoExameItemModel.id", "quizPerguntaModel.id", "quizPerguntaModel.pergunta", "quizPerguntaModel.apelido", "quizPerguntaModel.formula", "quizPerguntaModel.formulaReferenciaMinima", "quizPerguntaModel.formulaReferenciaMaxima", "quizPerguntaModel.tipoRespostaModel.id", "quizPerguntaModel.unidadeMedidaModel.id", "quizPerguntaModel.unidadeMedidaModel.descricao", "quizPerguntaModel.flagPercentual", "quizPerguntaModel.referencia", "quizPerguntaModel.ordem", "quizPerguntaModel.codigoSubexame", "quizPerguntaModel.procedimentoModel.id", "quizPerguntaModel.flagObrigatorio", "quizPerguntaModel.grupoSubexameModel.id", "quizPerguntaModel.grupoSubexameModel.descricao", "quizPerguntaModel.flagPossuiFilhos");
		
	}

	@Override
	public List<SolicitacaoExameModel> pesquisarLog(SolicitacaoExameModel model) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
