package br.com.restaurante.dao;

import java.sql.Timestamp;
import java.util.List;

import br.com.restaurante.model.CronExameModel;
import br.com.restaurante.model.CronModel;
import br.com.restaurante.util.Utilitario;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

public class CronDAO implements CrudDAO<CronModel> {

	public CronModel obterCrudModel(final CronModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, OBTER_DATA_CADASTRO_LOG(ID, 'CRON') DATA_CADASTRO, OBTER_USUARIO_CADASTRO_LOG(ID, 'CRON') USUARIO_CADASTRO, DATA_CADASTRO, (SELECT U.NOME FROM USUARIO U WHERE U.ID = C.USUARIO_CADASTRO_ID), DESCRICAO, QUERY, DATA_INICIAL, DATA_FINAL, HORAS, MINUTOS, DIA_SEMANA, DIA_MES, MES, SEGUNDOS, ANO, FLAG_ATIVO, TIPO_CRON_ID, ATIVIDADE_ID, FREQUENCIA_MARCACAO_EXAME_ID, SEXO, IDADE_MINIMA, IDADE_MAXIMA FROM CRON C WHERE C.ID = ?", model.getId());

		return (CronModel) broker.getObjectBean(CronModel.class, "id", "dataCadastro", "usuarioCadastroModel.nome", "dataAtualizacao", "usuarioAtualizacaoModel.nome", "descricao", "query", "dataInicial", "dataFinal", "horas", "minutos", "diaSemana", "diaMes", "mes", "segundos", "ano", "flagAtivo", "tipoCronModel.id", "atividadeModel.id", "frequenciaMarcacaoExameModel.id", "sexo", "idadeMinima", "idadeMaxima");
	}

	@SuppressWarnings("unchecked")
	public List<CronModel> pesquisarAtivos() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, OBTER_DATA_CADASTRO_LOG(ID, 'CRON') DATA_CADASTRO, OBTER_USUARIO_CADASTRO_LOG(ID, 'CRON') USUARIO_CADASTRO, DATA_CADASTRO, (SELECT U.NOME FROM USUARIO U WHERE U.ID = C.USUARIO_CADASTRO_ID), DESCRICAO, QUERY, DATA_INICIAL, DATA_FINAL, HORAS, MINUTOS, DIA_SEMANA, DIA_MES, MES, SEGUNDOS, ANO, FLAG_ATIVO FROM CRON C WHERE FLAG_ATIVO AND CURRENT_DATE BETWEEN DATA_INICIAL AND COALESCE(DATA_FINAL, CURRENT_DATE)");

		return broker.getCollectionBean(CronModel.class, "id", "dataCadastro", "usuarioCadastroModel.nome", "dataAtualizacao", "usuarioAtualizacaoModel.nome", "descricao", "query", "dataInicial", "dataFinal", "horas", "minutos", "diaSemana", "diaMes", "mes", "segundos", "ano", "flagAtivo");
	}

	@SuppressWarnings("unchecked")
	public List<CronModel> pesquisarLog(final CronModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DATA_CADASTRO, (SELECT U.NOME FROM USUARIO U WHERE U.ID = C.USUARIO_CADASTRO_ID), DESCRICAO, QUERY, DATA_INICIAL, DATA_FINAL, HORAS, MINUTOS, DIA_SEMANA, DIA_MES, MES, SEGUNDOS, ANO, FLAG_ATIVO, TIPO_CRON_ID, ATIVIDADE_ID, FREQUENCIA_MARCACAO_EXAME_ID, SEXO, IDADE_MINIMA, IDADE_MAXIMA FROM LOG.CRON C WHERE C.ID = ? ORDER BY DATA_CADASTRO DESC", model.getId());

		return broker.getCollectionBean(CronModel.class, "id", "dataCadastro", "usuarioCadastroModel.nome", "descricao", "query", "dataInicial", "dataFinal", "horas", "minutos", "diaSemana", "diaMes", "mes", "segundos", "ano", "flagAtivo", "tipoCronModel.id", "atividadeModel.id", "frequenciaMarcacaoExameModel.id", "sexo", "idadeMinima", "idadeMaxima");

	}

	@SuppressWarnings("unchecked")
	public List<CronModel> pesquisarCrudModel(final CronModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DATA_CADASTRO, (SELECT U.NOME FROM USUARIO U WHERE U.ID = C.USUARIO_CADASTRO_ID), DESCRICAO, QUERY, DATA_INICIAL, DATA_FINAL, HORAS, MINUTOS, DIA_SEMANA, DIA_MES, MES FROM CRON C WHERE SEM_ACENTOS(DESCRICAO) ILIKE SEM_ACENTOS(COALESCE(?, DESCRICAO)) ORDER BY ATIVIDADE_ID, DESCRICAO", Utilitario.getStringIlike(model.getDescricao(), true));

		return broker.getCollectionBean(CronModel.class, "id", "dataCadastro", "usuarioCadastroModel.nome", "descricao", "query", "dataInicial", "dataFinal", "horas", "minutos", "diaSemana", "diaMes", "mes");

	}

	public CronModel inserirCrudModel(final CronModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("cron_id_seq"));

		broker.setSQL("INSERT INTO CRON(ID, DATA_CADASTRO, USUARIO_CADASTRO_ID, DESCRICAO, QUERY, DATA_INICIAL, DATA_FINAL, HORAS, MINUTOS, DIA_SEMANA, DIA_MES, MES, SEGUNDOS, ANO, FLAG_ATIVO, TIPO_CRON_ID, ATIVIDADE_ID, FREQUENCIA_MARCACAO_EXAME_ID, SEXO, IDADE_MINIMA, IDADE_MAXIMA) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", model.getId(), new Timestamp(model.getDataCadastro().getTime()), model.getUsuarioCadastroModel().getId(), model.getDescricao(), model.getQuery(), model.getDataInicial(), model.getDataFinal(), model.getHoras(), model.getMinutos(), model.getDiaSemana(), model.getDiaMes(), model.getMes(), model.getSegundos(), model.getAno(), model.getFlagAtivo(), model.getTipoCronModel().getId(), model.getAtividadeModel().getId(), model.getFrequenciaMarcacaoExameModel().getId(), model.getSexo(), model.getIdadeMinima(), model.getIdadeMaxima());

		broker.execute();

		return model;
	}

	public CronModel alterarCrudModel(final CronModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE CRON SET DATA_CADASTRO = ?, USUARIO_CADASTRO_ID = ?, DESCRICAO = ?, QUERY = ?, DATA_INICIAL = ?, DATA_FINAL = ?, HORAS = ?, MINUTOS = ?, DIA_SEMANA = ?, DIA_MES = ?, MES = ?, SEGUNDOS = ?, ANO = ?, FLAG_ATIVO = ?, TIPO_CRON_ID = ?, ATIVIDADE_ID = ?, FREQUENCIA_MARCACAO_EXAME_ID = ?, SEXO = ?, IDADE_MINIMA = ?, IDADE_MAXIMA = ? WHERE ID = ?", new Timestamp(model.getDataAtualizacao().getTime()), model.getUsuarioAtualizacaoModel().getId(), model.getDescricao(), model.getQuery(), model.getDataInicial(), model.getDataFinal(), model.getHoras(), model.getMinutos(), model.getDiaSemana(), model.getDiaMes(), model.getMes(), model.getSegundos(), model.getAno(), model.getFlagAtivo(), model.getTipoCronModel().getId(), model.getAtividadeModel().getId(), model.getFrequenciaMarcacaoExameModel().getId(), model.getSexo(), model.getIdadeMinima(), model.getIdadeMaxima(), model.getId());

		broker.execute();

		return model;
	}

	public CronModel excluirCrudModel(final CronModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE CRON SET FLAG_ATIVO = FALSE, DATA_CADASTRO = ?, USUARIO_CADASTRO_ID = ? WHERE ID = ?", new Timestamp(model.getDataAtualizacao().getTime()), model.getUsuarioAtualizacaoModel().getId(), model.getId());

		broker.execute();

		return model;
	}
	
	public CronExameModel inserir(final CronExameModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("cron_exame_id_seq"));

		broker.setSQL("INSERT INTO CRON_EXAME(ID, CRON_ID, PROCEDIMENTO_ID, PERFIL_ID) VALUES (?, ?, ?, ?)", model.getId(), model.getCronModel().getId(), model.getProcedimentoModel().getId(), model.getPerfilModel().getId());

		broker.execute();

		return model;
	}
	
	public void excluir(final CronExameModel model) throws TSApplicationException {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("DELETE FROM CRON_EXAME WHERE ID = ?", model.getId());
		
		broker.execute();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<CronExameModel> pesquisarExames(CronModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, CRON_ID, PROCEDIMENTO_ID, (SELECT P.DESCRICAO FROM PROCEDIMENTO P WHERE P.ID = CE.PROCEDIMENTO_ID), PERFIL_ID, (SELECT P.DESCRICAO FROM PERFIL P WHERE P.ID = CE.PERFIL_ID) FROM CRON_EXAME CE WHERE CRON_ID = ? ORDER BY CE.ID", model.getId());

		return broker.getCollectionBean(CronExameModel.class, "id", "cronModel.id", "procedimentoModel.id", "procedimentoModel.descricao", "perfilModel.id", "perfilModel.descricao");
	}
	
	public void agendarExames(CronModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.prepareProcedure("agendar_exames", 1);

		broker.setProcedureOrFunctionParameter(model.getId());

		broker.executeProcedureOrFunction();

	}
	
}
