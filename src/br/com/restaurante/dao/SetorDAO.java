package br.com.restaurante.dao;

import java.sql.Timestamp;
import java.util.List;

import br.com.restaurante.model.SetorModel;
import br.com.restaurante.model.SetorOrigemModel;
import br.com.restaurante.util.Utilitario;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

public class SetorDAO implements CrudDAO<SetorModel> {
	
	public SetorModel obterCrudModel(final SetorModel model) {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("SELECT S.ID, S.DESCRICAO, S.FLAG_ATIVO, S.SETOR_ID, OBTER_DATA_CADASTRO_LOG(ID, 'SETOR') DATA_CADASTRO, OBTER_USUARIO_CADASTRO_LOG(ID, 'SETOR') USUARIO_CADASTRO, S.DATA_CADASTRO, (SELECT U.NOME FROM USUARIO U WHERE U.ID = S.USUARIO_CADASTRO_ID), S.CENTRO_RESULTADO_ID FROM SETOR S WHERE ID = ?", model.getId());
		
		return (SetorModel) broker.getObjectBean(SetorModel.class, "id", "descricao", "flagAtivo", "setorModel.id", "dataCadastro", "usuarioCadastroModel.nome", "dataAtualizacao", "usuarioAtualizacaoModel.nome", "centroResultadoModel.id");
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<SetorModel> pesquisarLog(SetorModel model) {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("SELECT S.ID, S.DESCRICAO, S.FLAG_ATIVO, S.SETOR_ID, S.DATA_CADASTRO, (SELECT U.NOME FROM USUARIO U WHERE U.ID = S.USUARIO_CADASTRO_ID), S.CENTRO_RESULTADO_ID FROM LOG.SETOR S WHERE S.ID = ? ORDER BY S.DATA_CADASTRO", model.getId());
		
		return broker.getCollectionBean(SetorModel.class, "id", "descricao", "flagAtivo", "setorModel.id", "dataCadastro", "usuarioCadastroModel.nome", "centroResultadoModel.id");
		
	}
	
	public SetorModel inserirCrudModel(final SetorModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("setor_id_seq"));

		broker.setSQL("INSERT INTO SETOR(ID, DESCRICAO, FLAG_ATIVO, SETOR_ID, DATA_CADASTRO, USUARIO_CADASTRO_ID, CENTRO_RESULTADO_ID) VALUES(?, ?, ?, ?, ?, ?, ?)", model.getId(), model.getDescricao(), model.getFlagAtivo(), model.getSetorModel().getId(), new Timestamp(model.getDataCadastro().getTime()), model.getUsuarioCadastroModel().getId(), model.getCentroResultadoModel().getId());

		broker.execute();

		return model;

	}

	@SuppressWarnings("unchecked")
	public List<SetorModel> pesquisarCrudModel(final SetorModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT S.ID, S.DESCRICAO, S.CENTRO_RESULTADO_ID, (SELECT CR.DESCRICAO FROM CENTRO_RESULTADO CR WHERE CR.ID = S.CENTRO_RESULTADO_ID), (SELECT CR.CODIGO FROM CENTRO_RESULTADO CR WHERE CR.ID = S.CENTRO_RESULTADO_ID) FROM SETOR S WHERE SEM_ACENTOS(S.DESCRICAO) ILIKE SEM_ACENTOS(COALESCE(?, S.DESCRICAO)) AND S.FLAG_ATIVO = COALESCE(?, S.FLAG_ATIVO) AND COALESCE(S.CENTRO_RESULTADO_ID, 0) = COALESCE(?, COALESCE(S.CENTRO_RESULTADO_ID, 0)) ORDER BY S.DESCRICAO", Utilitario.getStringIlike(model.getDescricao(), true), model.getFlagAtivo(), model.getCentroResultadoModel().getId());

		return broker.getCollectionBean(SetorModel.class, "id", "descricao", "centroResultadoModel.id", "centroResultadoModel.descricao", "centroResultadoModel.codigo");

	}

	public SetorModel alterarCrudModel(final SetorModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE SETOR SET DESCRICAO = ?, FLAG_ATIVO = ?,SETOR_ID = ?, CENTRO_RESULTADO_ID = ?, DATA_CADASTRO = ?, USUARIO_CADASTRO_ID = ? WHERE ID = ?", model.getDescricao(), model.getFlagAtivo(), model.getSetorModel().getId(), model.getCentroResultadoModel().getId(), new Timestamp(model.getDataAtualizacao().getTime()), model.getUsuarioAtualizacaoModel().getId(), model.getId());

		broker.execute();

		return model;

	}

	@Override
	public SetorModel excluirCrudModel(SetorModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE SETOR SET FLAG_ATIVO = FALSE WHERE ID = ?", model.getId());

		broker.execute();

		return null;
	}

	@SuppressWarnings("unchecked")
	public List<SetorOrigemModel> pesquisarOrigens(final SetorModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT SO.ID, SO.SETOR_ID, SO.ORIGEM_ID, O.DESCRICAO FROM SETOR_ORIGEM SO, ORIGEM O WHERE O.ID = SO.ORIGEM_ID AND SO.SETOR_ID = ?", model.getId());

		return broker.getCollectionBean(SetorOrigemModel.class, "id", "setorModel.id", "origemModel.id", "origemModel.descricao");

	}

	public void inserir(final SetorOrigemModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("setor_origem_id_seq"));

		broker.setSQL("INSERT INTO SETOR_ORIGEM (ID, SETOR_ID, ORIGEM_ID) VALUES (?, ?, ?)", model.getId(), model.getSetorModel().getId(), model.getOrigemModel().getId());

		broker.execute();

	}

	public SetorOrigemModel excluir(final SetorOrigemModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("DELETE FROM SETOR_ORIGEM WHERE ID = ?", model.getId());

		broker.execute();
		
		return model;

	}


}
