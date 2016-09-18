package br.com.restaurante.dao;

import java.sql.Timestamp;
import java.util.List;

import br.com.restaurante.model.FuncaoModel;
import br.com.restaurante.model.OrigemModel;
import br.com.restaurante.util.Utilitario;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

public final class OrigemDAO implements CrudDAO<OrigemModel> {

	public OrigemModel obterCrudModel(final OrigemModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, NOME, DESCRICAO, IMAGEM, FLAG_ATIVO, OBTER_DATA_CADASTRO_LOG(ID, 'ORIGEM') DATA_CADASTRO, OBTER_USUARIO_CADASTRO_LOG(ID, 'ORIGEM') USUARIO_CADASTRO, DATA_CADASTRO DATA_ATUALIZACAO, (SELECT NOME FROM USUARIO U1 WHERE U1.ID = O.USUARIO_CADASTRO_ID) USUARIO_ATUALIZACAO, CIDADE_ID, (SELECT C.DESCRICAO FROM CIDADE C WHERE C.ID = O.CIDADE_ID), (SELECT C.ESTADO_ID FROM CIDADE C WHERE C.ID = O.CIDADE_ID), CNPJ, RAZAO_SOCIAL, GMT, NOME_EMPRESA, ENDERECO, CODIGO FROM ORIGEM O WHERE ID = ?", model.getId());

		return (OrigemModel) broker.getObjectBean(OrigemModel.class, "id", "nome", "descricao", "imagem", "flagAtivo", "dataCadastro", "usuarioCadastroModel.nome", "dataAtualizacao", "usuarioAtualizacaoModel.nome", "cidadeModel.id", "cidadeModel.descricao", "cidadeModel.estadoModel.id", "cnpj", "razaoSocial", "gmt", "nomeEmpresa", "endereco", "codigo");

	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<OrigemModel> pesquisarLog(OrigemModel model) {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, NOME, DESCRICAO, IMAGEM, FLAG_ATIVO, DATA_CADASTRO DATA_ATUALIZACAO, (SELECT NOME FROM USUARIO U1 WHERE U1.ID = O.USUARIO_CADASTRO_ID) USUARIO_ATUALIZACAO, CIDADE_ID, (SELECT C.DESCRICAO FROM CIDADE C WHERE C.ID = O.CIDADE_ID), (SELECT C.ESTADO_ID FROM CIDADE C WHERE C.ID = O.CIDADE_ID), CNPJ, RAZAO_SOCIAL, GMT, NOME_EMPRESA, ENDERECO, CODIGO FROM LOG.ORIGEM O WHERE ID = ? ORDER BY O.DATA_CADASTRO DESC", model.getId());

		return broker.getCollectionBean(OrigemModel.class, "id", "nome", "descricao", "imagem", "flagAtivo", "dataCadastro", "usuarioCadastroModel.nome", "cidadeModel.id", "cidadeModel.descricao", "cidadeModel.estadoModel.id", "cnpj", "razaoSocial", "gmt", "nomeEmpresa", "endereco", "codigo");

	}


	@SuppressWarnings("unchecked")
	public List<OrigemModel> pesquisarCrudModel(final OrigemModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, NOME, DESCRICAO, FLAG_ATIVO, CODIGO FROM ORIGEM WHERE FLAG_ATIVO = COALESCE(?, FLAG_ATIVO) AND (SEM_ACENTOS(NOME) ILIKE SEM_ACENTOS(COALESCE(?, NOME)) OR SEM_ACENTOS(DESCRICAO) ILIKE SEM_ACENTOS(COALESCE(?, DESCRICAO))) AND SEM_ACENTOS(COALESCE(NOME_EMPRESA, '1')) ILIKE SEM_ACENTOS(COALESCE(?, COALESCE(NOME_EMPRESA, ''))) ORDER BY FLAG_ATIVO, DESCRICAO", model.getFlagAtivo(), Utilitario.getStringIlike(model.getDescricao(), true), Utilitario.getStringIlike(model.getDescricao(), true), Utilitario.getStringIlike(model.getNomeEmpresa(), true));

		return broker.getCollectionBean(OrigemModel.class, "id", "nome", "descricao", "flagAtivo", "codigo");

	}

	@SuppressWarnings("unchecked")
	public List<OrigemModel> pesquisarPorFuncao(final FuncaoModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, NOME, DESCRICAO, FLAG_ATIVO FROM ORIGEM WHERE FLAG_ATIVO = COALESCE(?, FLAG_ATIVO) AND ID IN (SELECT ORIGEM_ID FROM FUNCAO_ORIGEM WHERE FUNCAO_ID = ?) ORDER BY FLAG_ATIVO, DESCRICAO", model.getFlagAtivo(), model.getId());

		return broker.getCollectionBean(OrigemModel.class, "id", "nome", "descricao", "flagAtivo");

	}

	public OrigemModel inserirCrudModel(final OrigemModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("origem_id_seq"));

		broker.setSQL("INSERT INTO ORIGEM (ID, NOME, DESCRICAO, IMAGEM, FLAG_ATIVO, DATA_CADASTRO, USUARIO_CADASTRO_ID, CNPJ, RAZAO_SOCIAL, CIDADE_ID, GMT, NOME_EMPRESA, ENDERECO, CODIGO) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)", model.getId(), model.getNome(), model.getDescricao(), model.getImagem(), model.getFlagAtivo(), new Timestamp(model.getDataCadastro().getTime()), model.getUsuarioCadastroModel().getId(), model.getCnpj(), model.getRazaoSocial(), TSUtil.tratarLong(model.getCidadeModel().getId()), model.getGmt(), model.getNomeEmpresa(), model.getEndereco(), model.getCodigo());

		broker.execute();

		return model;

	}

	public OrigemModel alterarCrudModel(final OrigemModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE ORIGEM SET NOME = ?, DESCRICAO = ?, IMAGEM = ?, FLAG_ATIVO = ?, CNPJ = ?, RAZAO_SOCIAL = ?, CIDADE_ID = ?, GMT = ?, NOME_EMPRESA = ?, DATA_CADASTRO = ?, USUARIO_CADASTRO_ID = ?, ENDERECO = ?, CODIGO = ? WHERE ID = ?", model.getNome(), model.getDescricao(), model.getImagem(), model.getFlagAtivo(), model.getCnpj(), model.getRazaoSocial(), TSUtil.tratarLong(model.getCidadeModel().getId()), model.getGmt(), model.getNomeEmpresa(), new Timestamp(model.getDataAtualizacao().getTime()), model.getUsuarioAtualizacaoModel().getId(), model.getEndereco(), model.getCodigo(), model.getId());

		broker.execute();

		return model;

	}

	public OrigemModel excluirCrudModel(final OrigemModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE ORIGEM SET FLAG_ATIVO = FALSE WHERE ID = ?", model.getId());

		broker.execute();

		return model;

	}

}
