package br.com.restaurante.dao;

import java.sql.Timestamp;
import java.util.List;

import br.com.restaurante.model.SolicitacaoDocumentoModel;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

public class SolicitacaoDocumentoDAO implements CrudDAO<SolicitacaoDocumentoModel>{

	public SolicitacaoDocumentoModel obterCrudModel(final SolicitacaoDocumentoModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT SD.ID, SD.TIPO_SOLICITACAO_DOCUMENTO_ID, (SELECT TSD.DESCRICAO FROM TIPO_SOLICITACAO_DOCUMENTO TSD WHERE TSD.ID = SD.TIPO_SOLICITACAO_DOCUMENTO_ID), (SELECT TSD.TIPO_QUIZ_ID FROM TIPO_SOLICITACAO_DOCUMENTO TSD WHERE TSD.ID = SD.TIPO_SOLICITACAO_DOCUMENTO_ID), SD.DATA_CADASTRO, SD.USUARIO_CADASTRO_ID, (SELECT U.NOME FROM USUARIO U WHERE U.ID = SD.USUARIO_CADASTRO_ID), SD.FLAG_ATIVO, SD.ATENDIMENTO_ID, SD.TEXTO, SD.FLAG_CONCLUIDO, SD.QUIZ_QUESTIONARIO_ID, SD.MOTIVO_CANCELAMENTO FROM SOLICITACAO_DOCUMENTO SD WHERE ID = ? ORDER BY SD.ID DESC", model.getId());

		return (SolicitacaoDocumentoModel) broker.getObjectBean(SolicitacaoDocumentoModel.class, "id", "tipoSolicitacaoDocumentoModel.id", "tipoSolicitacaoDocumentoModel.descricao", "tipoSolicitacaoDocumentoModel.tipoQuizModel.id", "dataCadastro", "usuarioCadastroModel.id", "usuarioCadastroModel.nome", "flagAtivo", "atendimentoModel.id", "texto", "flagConcluido", "quizQuestionarioModel.id", "motivoCancelamento");

	}

	@SuppressWarnings("unchecked")
	public List<SolicitacaoDocumentoModel> pesquisarCrudModel(final SolicitacaoDocumentoModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT SD.ID, SD.TIPO_SOLICITACAO_DOCUMENTO_ID, (SELECT DESCRICAO FROM TIPO_SOLICITACAO_DOCUMENTO TSD WHERE TSD.ID = SD.TIPO_SOLICITACAO_DOCUMENTO_ID), (SELECT FLAG_EDITAVEL FROM TIPO_SOLICITACAO_DOCUMENTO TSD WHERE TSD.ID = SD.TIPO_SOLICITACAO_DOCUMENTO_ID), (SELECT FLAG_CONCLUIR FROM TIPO_SOLICITACAO_DOCUMENTO TSD WHERE TSD.ID = SD.TIPO_SOLICITACAO_DOCUMENTO_ID), (SELECT TSD.TIPO_QUIZ_ID FROM TIPO_SOLICITACAO_DOCUMENTO TSD WHERE TSD.ID = SD.TIPO_SOLICITACAO_DOCUMENTO_ID), SD.DATA_CADASTRO, SD.USUARIO_CADASTRO_ID, (SELECT NOME FROM USUARIO U WHERE U.ID = SD.USUARIO_CADASTRO_ID), SD.FLAG_ATIVO, SD.ATENDIMENTO_ID, (SELECT SA.DESCRICAO FROM STATUS_ATENDIMENTO SA WHERE SA.ID = A.STATUS_ATENDIMENTO_ID), SD.TEXTO, SD.FLAG_CONCLUIDO, SD.QUIZ_QUESTIONARIO_ID, SD.MOTIVO_CANCELAMENTO FROM SOLICITACAO_DOCUMENTO SD, ATENDIMENTO A, FUNCIONARIO F WHERE SD.ATENDIMENTO_ID = A.ID AND A.FUNCIONARIO_ID = F.ID AND F.ID = ? ORDER BY SD.ID DESC", model.getAtendimentoModel().getFuncionarioModel().getId());

		return broker.getCollectionBean(SolicitacaoDocumentoModel.class, "id", "tipoSolicitacaoDocumentoModel.id", "tipoSolicitacaoDocumentoModel.descricao", "tipoSolicitacaoDocumentoModel.flagEditavel", "tipoSolicitacaoDocumentoModel.flagConcluir", "tipoSolicitacaoDocumentoModel.tipoQuizModel.id", "dataCadastro", "usuarioCadastroModel.id", "usuarioCadastroModel.nome", "flagAtivo", "atendimentoModel.id", "atendimentoModel.statusAtendimentoModel.descricao", "texto", "flagConcluido", "quizQuestionarioModel.id", "motivoCancelamento");

	}

	public SolicitacaoDocumentoModel inserirCrudModel(final SolicitacaoDocumentoModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("solicitacao_documento_id_seq"));

		broker.setSQL("INSERT INTO SOLICITACAO_DOCUMENTO(ID, TIPO_SOLICITACAO_DOCUMENTO_ID, DATA_CADASTRO, USUARIO_CADASTRO_ID, FLAG_ATIVO, ATENDIMENTO_ID, TEXTO, QUIZ_QUESTIONARIO_ID, FUNCAO_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", model.getId(), model.getTipoSolicitacaoDocumentoModel().getId(), new Timestamp(model.getDataCadastro().getTime()), model.getUsuarioCadastroModel().getId(), model.getFlagAtivo(), model.getAtendimentoModel().getId(), model.getTexto(), model.getQuizQuestionarioModel().getId(), model.getFuncaoModel().getId());

		broker.execute();

		return model;

	}

	public SolicitacaoDocumentoModel alterarCrudModel(final SolicitacaoDocumentoModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE SOLICITACAO_DOCUMENTO SET FLAG_ATIVO = ?, DATA_CADASTRO = ?, USUARIO_CADASTRO_ID = ?, TEXTO = ?, FLAG_CONCLUIDO = ? WHERE ID = ?", model.getFlagAtivo(), new Timestamp(model.getDataCadastro().getTime()), model.getUsuarioCadastroModel().getId(), model.getTexto(), model.getFlagConcluido(), model.getId());

		broker.execute();

		return model;

	}

	public SolicitacaoDocumentoModel cancelar(final SolicitacaoDocumentoModel model) throws TSApplicationException {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("UPDATE SOLICITACAO_DOCUMENTO SET FLAG_ATIVO = FALSE, DATA_CADASTRO = ?, USUARIO_CADASTRO_ID = ?, MOTIVO_CANCELAMENTO = ? WHERE ID = ?", new Timestamp(model.getDataCadastro().getTime()), model.getUsuarioCadastroModel().getId(), model.getMotivoCancelamento(), model.getId());
		
		broker.execute();
		
		return model;
		
	}
	
	public SolicitacaoDocumentoModel excluirCrudModel(SolicitacaoDocumentoModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("DELETE FROM SOLICITACAO_DOCUMENTO WHERE ID = ?", model.getId());

		broker.execute();

		return model;
	}

	@Override
	public List<SolicitacaoDocumentoModel> pesquisarLog(SolicitacaoDocumentoModel model) {
		return null;
	}

}
