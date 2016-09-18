package br.com.restaurante.dao;

import java.sql.Timestamp;
import java.util.List;

import br.com.restaurante.model.CentroResultadoModel;
import br.com.restaurante.util.Utilitario;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

public final class CentroResultadoDAO implements CrudDAO<CentroResultadoModel> {

	public CentroResultadoModel obterCrudModel(final CentroResultadoModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT CR.ID, CR.DESCRICAO, CR.CODIGO, CR.FLAG_ATIVO, OBTER_DATA_CADASTRO_LOG(CR.ID, 'CENTRO_RESULTADO'), OBTER_USUARIO_CADASTRO_LOG(CR.ID, 'CENTRO_RESULTADO'), CR.DATA_CADASTRO, (SELECT U.NOME FROM USUARIO U WHERE U.ID = CR.USUARIO_CADASTRO_ID) FROM CENTRO_RESULTADO CR WHERE CR.ID = ?", model.getId());

		return (CentroResultadoModel) broker.getObjectBean(CentroResultadoModel.class, "id", "descricao", "codigo", "flagAtivo", "dataCadastro", "usuarioCadastroModel.nome", "dataAtualizacao", "usuarioAtualizacaoModel.nome");

	}
	
	@SuppressWarnings("unchecked")
	public List<CentroResultadoModel> pesquisarLog(final CentroResultadoModel model) {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("SELECT CR.ID, CR.DESCRICAO, CR.CODIGO, CR.FLAG_ATIVO, CR.DATA_CADASTRO, (SELECT U.NOME FROM USUARIO U WHERE U.ID = CR.USUARIO_CADASTRO_ID) FROM LOG.CENTRO_RESULTADO CR WHERE CR.ID = ? ORDER BY CR.DATA_CADASTRO DESC", model.getId());
		
		return broker.getCollectionBean(CentroResultadoModel.class, "id", "descricao", "codigo", "flagAtivo", "dataCadastro", "usuarioCadastroModel.nome");
		
	}

	@SuppressWarnings("unchecked")
	public List<CentroResultadoModel> pesquisarCrudModel(final CentroResultadoModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO, CODIGO FROM CENTRO_RESULTADO CR WHERE SEM_ACENTOS(DESCRICAO) LIKE SEM_ACENTOS(COALESCE(?, DESCRICAO)) AND SEM_ACENTOS(COALESCE(CODIGO, '')) LIKE SEM_ACENTOS(COALESCE(?, COALESCE(CODIGO, ''))) AND CR.FLAG_ATIVO = ? ORDER BY DESCRICAO", Utilitario.getStringIlike(model.getDescricao(), true), Utilitario.getStringIlike(model.getCodigo(), true), model.getFlagAtivo());

		return broker.getCollectionBean(CentroResultadoModel.class, "id", "descricao", "codigo");

	}

	public CentroResultadoModel inserirCrudModel(final CentroResultadoModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("centro_resultado_id_seq"));

		broker.setSQL("INSERT INTO CENTRO_RESULTADO (ID, DESCRICAO, CODIGO, DATA_CADASTRO, USUARIO_CADASTRO_ID, FLAG_ATIVO) VALUES (?, ?, ?, ?, ?, ?)", model.getId(), model.getDescricao(), model.getCodigo(), new Timestamp(model.getDataCadastro().getTime()), model.getUsuarioCadastroModel().getId(), model.getFlagAtivo());

		broker.execute();

		return model;

	}

	public CentroResultadoModel alterarCrudModel(final CentroResultadoModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE CENTRO_RESULTADO SET DESCRICAO = ?, CODIGO = ?, FLAG_ATIVO = ?, DATA_CADASTRO = ?, USUARIO_CADASTRO_ID = ? WHERE ID = ?", model.getDescricao(), model.getCodigo(), model.getFlagAtivo(), new Timestamp(model.getDataAtualizacao().getTime()), model.getUsuarioAtualizacaoModel().getId(), model.getId());

		broker.execute();

		return model;

	}

	@Override
	public CentroResultadoModel excluirCrudModel(CentroResultadoModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("DELETE FROM CENTRO_RESULTADO WHERE ID = ?", model.getId());

		broker.execute();

		return model;
	}

}
