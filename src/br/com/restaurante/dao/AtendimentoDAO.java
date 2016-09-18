package br.com.restaurante.dao;

import java.sql.Timestamp;
import java.util.List;

import br.com.restaurante.model.AtendimentoModel;
import br.com.restaurante.model.FuncionarioModel;
import br.com.restaurante.model.OrigemModel;
import br.com.restaurante.util.Constantes;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSCryptoUtil;
import br.com.topsys.util.TSUtil;

public class AtendimentoDAO implements CrudDAO<AtendimentoModel> {

	public AtendimentoModel obterCrudModel(final AtendimentoModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT A.ID, OBTER_DATA_CADASTRO_LOG(ID, 'ATENDIMENTO') DATA_CADASTRO, OBTER_USUARIO_CADASTRO_LOG(ID, 'ATENDIMENTO') USUARIO_CADASTRO, A.DATA_CADASTRO, (SELECT U.NOME FROM USUARIO U WHERE U.ID = A.USUARIO_CADASTRO_ID), A.DATA, A.FUNCIONARIO_ID, (SELECT F.NOME FROM FUNCIONARIO F WHERE F.ID = A.FUNCIONARIO_ID), A.USUARIO_RESPONSAVEL_ID, (SELECT U.NOME FROM USUARIO U WHERE U.ID = A.USUARIO_RESPONSAVEL_ID), A.STATUS_ATENDIMENTO_ID, (SELECT SA.DESCRICAO FROM STATUS_ATENDIMENTO SA WHERE SA.ID = A.STATUS_ATENDIMENTO_ID), (SELECT SA.FLAG_FECHADO FROM STATUS_ATENDIMENTO SA WHERE SA.ID = A.STATUS_ATENDIMENTO_ID), A.ORIGEM_ID, (SELECT O.DESCRICAO FROM ORIGEM O WHERE O.ID = A.ORIGEM_ID) FROM ATENDIMENTO A WHERE A.ID = ? ", model.getId());

		return (AtendimentoModel) broker.getObjectBean(AtendimentoModel.class, "id", "dataCadastro", "usuarioCadastroModel.nome", "dataAtualizacao", "usuarioAtualizacaoModel.nome", "data", "funcionarioModel.id", "funcionarioModel.nome", "usuarioResponsavelModel.id", "usuarioResponsavelModel.nome", "statusAtendimentoModel.id", "statusAtendimentoModel.descricao", "statusAtendimentoModel.flagFechado", "origemModel.id", "origemModel.descricao");
	}

	public AtendimentoModel obterUltimoAtendimento(final FuncionarioModel funcionarioModel, OrigemModel origemModel) {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("SELECT A.ID, OBTER_DATA_CADASTRO_LOG(ID, 'ATENDIMENTO') DATA_CADASTRO, OBTER_USUARIO_CADASTRO_LOG(ID, 'ATENDIMENTO') USUARIO_CADASTRO, A.DATA_CADASTRO, (SELECT U.NOME FROM USUARIO U WHERE U.ID = A.USUARIO_CADASTRO_ID), A.DATA, A.FUNCIONARIO_ID, (SELECT F.NOME FROM FUNCIONARIO F WHERE F.ID = A.FUNCIONARIO_ID), A.USUARIO_RESPONSAVEL_ID, (SELECT U.NOME FROM USUARIO U WHERE U.ID = A.USUARIO_RESPONSAVEL_ID), A.STATUS_ATENDIMENTO_ID, (SELECT SA.DESCRICAO FROM STATUS_ATENDIMENTO SA WHERE SA.ID = A.STATUS_ATENDIMENTO_ID), (SELECT SA.FLAG_FECHADO FROM STATUS_ATENDIMENTO SA WHERE SA.ID = A.STATUS_ATENDIMENTO_ID), A.ORIGEM_ID, (SELECT O.DESCRICAO FROM ORIGEM O WHERE O.ID = A.ORIGEM_ID) FROM ATENDIMENTO A WHERE A.FUNCIONARIO_ID = ? AND A.ORIGEM_ID = ? ORDER BY A.DATA DESC, A.ID DESC LIMIT 1", funcionarioModel.getId(), origemModel.getId());
		
		return (AtendimentoModel) broker.getObjectBean(AtendimentoModel.class, "id", "dataCadastro", "usuarioCadastroModel.nome", "dataAtualizacao", "usuarioAtualizacaoModel.nome", "data", "funcionarioModel.id", "funcionarioModel.nome", "usuarioResponsavelModel.id", "usuarioResponsavelModel.nome", "statusAtendimentoModel.id", "statusAtendimentoModel.descricao", "statusAtendimentoModel.flagFechado", "origemModel.id", "origemModel.descricao");
	}
	
	@SuppressWarnings("unchecked")
	public List<AtendimentoModel> pesquisarLog(final AtendimentoModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT A.ID, A.DATA_CADASTRO, (SELECT U.NOME FROM USUARIO U WHERE U.ID = A.USUARIO_CADASTRO_ID), A.DATA, A.FUNCIONARIO_ID, (SELECT F.NOME FROM FUNCIONARIO F WHERE F.ID = A.FUNCIONARIO_ID), A.USUARIO_RESPONSAVEL_ID, (SELECT U.NOME FROM USUARIO U WHERE U.ID = A.USUARIO_RESPONSAVEL_ID), A.STATUS_ATENDIMENTO_ID, (SELECT SA.DESCRICAO FROM STATUS_ATENDIMENTO SA WHERE SA.ID = A.STATUS_ATENDIMENTO_ID), (SELECT SA.FLAG_FECHADO FROM STATUS_ATENDIMENTO SA WHERE SA.ID = A.STATUS_ATENDIMENTO_ID), A.ORIGEM_ID, (SELECT O.DESCRICAO FROM ORIGEM O WHERE O.ID = A.ORIGEM_ID) FROM LOG.ATENDIMENTO A WHERE A.ID = ? ORDER BY A.DATA_CADASTRO DESC", model.getId());

		return broker.getCollectionBean(AtendimentoModel.class, "id", "dataCadastro", "usuarioCadastroModel.nome", "data", "funcionarioModel.id", "funcionarioModel.nome", "usuarioResponsavelModel.id", "usuarioResponsavelModel.nome", "statusAtendimentoModel.id", "statusAtendimentoModel.descricao", "statusAtendimentoModel.flagFechado", "origemModel.id", "origemModel.descricao");

	}

	@SuppressWarnings("unchecked")
	public List<AtendimentoModel> pesquisarCrudModel(final AtendimentoModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT A.ID, OBTER_DATA_CADASTRO_LOG(ID, 'ATENDIMENTO') DATA_CADASTRO, OBTER_USUARIO_CADASTRO_LOG(ID, 'ATENDIMENTO') USUARIO_CADASTRO, A.DATA_CADASTRO, (SELECT U.NOME FROM USUARIO U WHERE U.ID = A.USUARIO_CADASTRO_ID), A.DATA, A.FUNCIONARIO_ID, (SELECT F.NOME FROM FUNCIONARIO F WHERE F.ID = A.FUNCIONARIO_ID), A.USUARIO_RESPONSAVEL_ID, (SELECT U.NOME FROM USUARIO U WHERE U.ID = A.USUARIO_RESPONSAVEL_ID), A.STATUS_ATENDIMENTO_ID, (SELECT SA.DESCRICAO FROM STATUS_ATENDIMENTO SA WHERE SA.ID = A.STATUS_ATENDIMENTO_ID), (SELECT SA.FLAG_FECHADO FROM STATUS_ATENDIMENTO SA WHERE SA.ID = A.STATUS_ATENDIMENTO_ID), A.ORIGEM_ID, (SELECT O.DESCRICAO FROM ORIGEM O WHERE O.ID = A.ORIGEM_ID), EXISTS(SELECT 1 FROM SOLICITACAO_EXAME SE WHERE SE.ATENDIMENTO_ID = A.ID) FLAG_POSSUI_EXAMES FROM ATENDIMENTO A WHERE A.FUNCIONARIO_ID = ? AND A.ORIGEM_ID = ? ORDER BY A.DATA DESC, ID DESC", model.getFuncionarioModel().getId(), model.getOrigemModel().getId());

		return broker.getCollectionBean(AtendimentoModel.class, "id", "dataCadastro", "usuarioCadastroModel.nome", "dataAtualizacao", "usuarioAtualizacaoModel.nome", "data", "funcionarioModel.id", "funcionarioModel.nome", "usuarioResponsavelModel.id", "usuarioResponsavelModel.nome", "statusAtendimentoModel.id", "statusAtendimentoModel.descricao", "statusAtendimentoModel.flagFechado", "origemModel.id", "origemModel.descricao", "flagPossuiExames");

	}
	
	@SuppressWarnings("unchecked")
	public List<AtendimentoModel> pesquisarAtendimentos(final AtendimentoModel model) {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("SELECT A.ID, OBTER_DATA_CADASTRO_LOG(A.ID, 'ATENDIMENTO') DATA_CADASTRO, OBTER_USUARIO_CADASTRO_LOG(A.ID, 'ATENDIMENTO') USUARIO_CADASTRO, A.DATA_CADASTRO, (SELECT U.NOME FROM USUARIO U WHERE U.ID = A.USUARIO_CADASTRO_ID), A.DATA, A.FUNCIONARIO_ID, F.MATRICULA, F.NOME, F.DATA_NASCIMENTO, F.SEXO, A.USUARIO_RESPONSAVEL_ID, (SELECT U.NOME FROM USUARIO U WHERE U.ID = A.USUARIO_RESPONSAVEL_ID), A.STATUS_ATENDIMENTO_ID, (SELECT SA.DESCRICAO FROM STATUS_ATENDIMENTO SA WHERE SA.ID = A.STATUS_ATENDIMENTO_ID), (SELECT SA.FLAG_FECHADO FROM STATUS_ATENDIMENTO SA WHERE SA.ID = A.STATUS_ATENDIMENTO_ID), A.ORIGEM_ID, (SELECT O.DESCRICAO FROM ORIGEM O WHERE O.ID = A.ORIGEM_ID) FROM ATENDIMENTO A, FUNCIONARIO F, STATUS_ATENDIMENTO SA WHERE F.ID = A.FUNCIONARIO_ID AND SA.ID = A.STATUS_ATENDIMENTO_ID AND A.USUARIO_RESPONSAVEL_ID = COALESCE(?, A.USUARIO_RESPONSAVEL_ID) AND F.CARGO_ID = COALESCE(?, F.CARGO_ID) AND F.MATRICULA = COALESCE(?, F.MATRICULA) AND A.ORIGEM_ID = ? AND NOT SA.FLAG_FECHADO ORDER BY A.DATA DESC", (TSUtil.isEmpty(model.getUsuarioResponsavelModel()) ? null : model.getUsuarioResponsavelModel().getId()), model.getFuncionarioModel().getCargoModel().getId(), TSCryptoUtil.criptografarByte(TSUtil.tratarString(model.getFuncionarioModel().getMatricula())), model.getOrigemModel().getId());
		
		return broker.getCollectionBean(AtendimentoModel.class, "id", "dataCadastro", "usuarioCadastroModel.nome", "dataAtualizacao", "usuarioAtualizacaoModel.nome", "data", "funcionarioModel.id", "decrypt.funcionarioModel.matricula", "decrypt.funcionarioModel.nome", "funcionarioModel.dataNascimento", "funcionarioModel.sexo", "usuarioResponsavelModel.id", "usuarioResponsavelModel.nome", "statusAtendimentoModel.id", "statusAtendimentoModel.descricao", "statusAtendimentoModel.flagFechado", "origemModel.id", "origemModel.descricao");
		
	}
	
	@SuppressWarnings("unchecked")
	public List<AtendimentoModel> pesquisarAgendamentos(final AtendimentoModel model) {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("SELECT A.ID, OBTER_DATA_CADASTRO_LOG(A.ID, 'ATENDIMENTO') DATA_CADASTRO, OBTER_USUARIO_CADASTRO_LOG(A.ID, 'ATENDIMENTO') USUARIO_CADASTRO, A.DATA_CADASTRO, (SELECT U.NOME FROM USUARIO U WHERE U.ID = A.USUARIO_CADASTRO_ID), A.DATA, A.FUNCIONARIO_ID, F.MATRICULA, F.NOME, F.DATA_NASCIMENTO, F.SEXO, A.USUARIO_RESPONSAVEL_ID, (SELECT U.NOME FROM USUARIO U WHERE U.ID = A.USUARIO_RESPONSAVEL_ID), A.STATUS_ATENDIMENTO_ID, (SELECT SA.DESCRICAO FROM STATUS_ATENDIMENTO SA WHERE SA.ID = A.STATUS_ATENDIMENTO_ID), (SELECT SA.FLAG_FECHADO FROM STATUS_ATENDIMENTO SA WHERE SA.ID = A.STATUS_ATENDIMENTO_ID), A.ORIGEM_ID, (SELECT O.DESCRICAO FROM ORIGEM O WHERE O.ID = A.ORIGEM_ID) FROM ATENDIMENTO A, FUNCIONARIO F, STATUS_ATENDIMENTO SA WHERE F.ID = A.FUNCIONARIO_ID AND SA.ID = A.STATUS_ATENDIMENTO_ID AND A.ORIGEM_ID = ? AND A.STATUS_ATENDIMENTO_ID = ? ORDER BY A.DATA DESC", model.getOrigemModel().getId(), Constantes.STATUS_ATENDIMENTO_AGENDADO);
		
		return broker.getCollectionBean(AtendimentoModel.class, "id", "dataCadastro", "usuarioCadastroModel.nome", "dataAtualizacao", "usuarioAtualizacaoModel.nome", "data", "funcionarioModel.id", "decrypt.funcionarioModel.matricula", "decrypt.funcionarioModel.nome", "funcionarioModel.dataNascimento", "funcionarioModel.sexo", "usuarioResponsavelModel.id", "usuarioResponsavelModel.nome", "statusAtendimentoModel.id", "statusAtendimentoModel.descricao", "statusAtendimentoModel.flagFechado", "origemModel.id", "origemModel.descricao");
		
	}

	public AtendimentoModel inserirCrudModel(final AtendimentoModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("atendimento_id_seq"));

		broker.setSQL("INSERT INTO ATENDIMENTO (ID, DATA_CADASTRO, USUARIO_CADASTRO_ID, DATA, FUNCIONARIO_ID, USUARIO_RESPONSAVEL_ID, STATUS_ATENDIMENTO_ID, ORIGEM_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", model.getId(), new Timestamp(model.getDataCadastro().getTime()), model.getUsuarioCadastroModel().getId(), model.getData(), model.getFuncionarioModel().getId(), model.getUsuarioResponsavelModel().getId(), model.getStatusAtendimentoModel().getId(), model.getOrigemModel().getId());

		broker.execute();

		return model;
	}

	public AtendimentoModel alterarCrudModel(final AtendimentoModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE ATENDIMENTO SET DATA = ?, USUARIO_RESPONSAVEL_ID = ?, STATUS_ATENDIMENTO_ID = ?, DATA_CADASTRO = ?, USUARIO_CADASTRO_ID = ? WHERE ID = ?", model.getData(), model.getUsuarioResponsavelModel().getId(), model.getStatusAtendimentoModel().getId(), new Timestamp(model.getDataAtualizacao().getTime()), model.getUsuarioAtualizacaoModel().getId(), model.getId());

		broker.execute();

		return model;
	}

	public AtendimentoModel iniciarAtendimento(final AtendimentoModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE ATENDIMENTO SET USUARIO_RESPONSAVEL_ID = ?, STATUS_ATENDIMENTO_ID = ?, DATA_CADASTRO = ?, USUARIO_CADASTRO_ID = ? WHERE ID = ?", model.getUsuarioResponsavelModel().getId(), model.getStatusAtendimentoModel().getId(), new Timestamp(model.getDataAtualizacao().getTime()), model.getUsuarioAtualizacaoModel().getId(), model.getId());

		broker.execute();

		return model;
	}
	
	public AtendimentoModel alterarStatus(final AtendimentoModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE ATENDIMENTO SET STATUS_ATENDIMENTO_ID = ?, DATA_CADASTRO = ?, USUARIO_CADASTRO_ID = ? WHERE ID = ?", model.getStatusAtendimentoModel().getId(), new Timestamp(model.getDataAtualizacao().getTime()), model.getUsuarioAtualizacaoModel().getId(), model.getId());

		broker.execute();

		return model;
	}

	public AtendimentoModel excluirCrudModel(final AtendimentoModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("DELETE FROM ATENDIMENTO WHERE ID = ?", model.getId());

		broker.execute();

		return model;
	}

}