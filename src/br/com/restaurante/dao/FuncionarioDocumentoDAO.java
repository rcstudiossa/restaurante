package br.com.restaurante.dao;

import java.util.List;

import br.com.restaurante.model.FuncionarioDocumentoModel;
import br.com.restaurante.model.FuncionarioModel;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

public final class FuncionarioDocumentoDAO {

	public Boolean existeDocumento(final FuncionarioModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT EXISTS (SELECT 1 FROM FUNCIONARIO_DOCUMENTO WHERE FUNCIONARIO_ID = ? AND FLAG_ATIVO)", model.getId());

		return (Boolean) broker.getObject();

	}

	@SuppressWarnings("unchecked")
	public List<FuncionarioDocumentoModel> pesquisar(final FuncionarioModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, FUNCIONARIO_ID, DESCRICAO, ARQUIVO, USUARIO_CADASTRO_ID, DATA_CADASTRO, FLAG_ATIVO FROM FUNCIONARIO_DOCUMENTO WHERE FUNCIONARIO_ID = ? AND FLAG_ATIVO ORDER BY ID DESC", model.getId());

		return broker.getCollectionBean(FuncionarioDocumentoModel.class, "id", "funcionarioModel.id", "descricao", "arquivo", "usuarioCadastroModel.id", "dataCadastro", "flagAtivo");

	}

	public void inserir(final FuncionarioDocumentoModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("funcionario_documento_id_seq"));

		broker.setSQL("INSERT INTO FUNCIONARIO_DOCUMENTO(ID, FUNCIONARIO_ID, DESCRICAO, ARQUIVO, USUARIO_CADASTRO_ID, DATA_CADASTRO) VALUES (?, ?, ?, ?, ?, NOW())", model.getId(), model.getFuncionarioModel().getId(), model.getDescricao(), model.getArquivo(), model.getUsuarioCadastroModel().getId());

		broker.execute();

	}

	public void alterar(final FuncionarioDocumentoModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE FUNCIONARIO_DOCUMENTO SET ID=?, FUNCIONARIO_ID=?, DESCRICAO=?, ARQUIVO=?, USUARIO_CADASTRO_ID=?, DATA_CADASTRO=?, FLAG_ATIVO=? WHERE ID=?", model.getFuncionarioModel().getId(), model.getDescricao(), model.getArquivo(), model.getUsuarioCadastroModel().getId(), model.getDataCadastro(), model.getFlagAtivo(), model.getId());

		broker.execute();

	}

	public void excluir(final FuncionarioDocumentoModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE FUNCIONARIO_DOCUMENTO SET FLAG_ATIVO = ? WHERE ID = ?", model.getFlagAtivo(), model.getId());

		broker.execute();

	}
}
