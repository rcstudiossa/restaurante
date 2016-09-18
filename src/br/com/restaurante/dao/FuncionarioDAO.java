package br.com.restaurante.dao;

import java.sql.Timestamp;
import java.util.List;

import br.com.restaurante.model.FuncionarioModel;
import br.com.restaurante.util.Utilitario;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSCryptoUtil;

public class FuncionarioDAO implements CrudDAO<FuncionarioModel> {

	public FuncionarioModel obterCrudModel(final FuncionarioModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, NOME, FLAG_ATIVO, OBTER_DATA_CADASTRO_LOG(ID, 'FUNCIONARIO'), OBTER_USUARIO_CADASTRO_LOG(ID, 'FUNCIONARIO'), DATA_CADASTRO DATA_ALTERACAO, (SELECT NOME FROM USUARIO U1 WHERE U1.ID = F.USUARIO_CADASTRO_ID) USUARIO_ALTERACAO, EMAIL, CPF, CARGO_ID, (SELECT C.DESCRICAO FROM CARGO C WHERE C.ID = F.CARGO_ID), RG, TELEFONE, TELEFONE_COMERCIAL, CELULAR, MATRICULA, OBSERVACAO, DATA_NASCIMENTO, APELIDO, SEXO, ENDERECO, NUMERO, COMPLEMENTO, BAIRRO_ID, CIDADE_ID, (SELECT C.ESTADO_ID FROM CIDADE C WHERE C.ID = F.CIDADE_ID), CEP, ATIVIDADE_ID, ORIGEM_ID, FOTO FROM FUNCIONARIO F WHERE ID = ?", model.getId());

		return (FuncionarioModel) broker.getObjectBean(FuncionarioModel.class, "id", "decrypt.nome", "flagAtivo", "dataCadastro", "usuarioCadastroModel.nome", "dataAtualizacao", "usuarioAtualizacaoModel.nome", "decrypt.email", "decrypt.cpf", "cargoModel.id", "cargoModel.descricao", "decrypt.rg", "decrypt.telefone", "decrypt.telefoneComercial", "decrypt.celular", "decrypt.matricula", "decrypt.observacao", "dataNascimento", "decrypt.apelido", "sexo", "decrypt.endereco", "decrypt.numero", "decrypt.complemento", "bairroModel.id", "cidadeModel.id", "cidadeModel.estadoModel.id", "decrypt.cep", "atividadeModel.id", "origemModel.id", "foto");
	}

	@SuppressWarnings("unchecked")
	public List<FuncionarioModel> pesquisarCrudModel(final FuncionarioModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, MATRICULA, NOME, FLAG_ATIVO, EMAIL, CPF, (SELECT C.DESCRICAO FROM CARGO C WHERE C.ID = F.CARGO_ID) FROM FUNCIONARIO F WHERE SEM_ACENTOS(CONVERTER(NOME, 'top10sysSistemas')) ILIKE SEM_ACENTOS(COALESCE(?, CONVERTER(NOME, 'top10sysSistemas'))) AND SEM_ACENTOS(CONVERTER(MATRICULA, 'top10sysSistemas')) ILIKE SEM_ACENTOS(COALESCE(?, CONVERTER(MATRICULA, 'top10sysSistemas'))) AND FLAG_ATIVO = COALESCE(?, FLAG_ATIVO) AND ORIGEM_ID = COALESCE(?, ORIGEM_ID) AND COALESCE(ATIVIDADE_ID, 0) = COALESCE(?, COALESCE(ATIVIDADE_ID, 0)) ORDER BY CONVERTER(NOME, 'top10sysSistemas')", Utilitario.getStringIlike(model.getNome(), true), Utilitario.getStringIlike(model.getMatricula(), false), model.getFlagAtivo(), model.getOrigemModel().getId(), model.getAtividadeModel().getId());

		return broker.getCollectionBean(FuncionarioModel.class, "id", "decrypt.matricula", "decrypt.nome", "flagAtivo", "decrypt.email", "decrypt.cpf", "cargoModel.descricao");
	}

	@SuppressWarnings("unchecked")
	public List<FuncionarioModel> pesquisarLog(final FuncionarioModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, NOME, FLAG_ATIVO, DATA_CADASTRO DATA_ALTERACAO, (SELECT NOME FROM USUARIO U1 WHERE U1.ID = F.USUARIO_CADASTRO_ID) USUARIO_ALTERACAO, EMAIL, CPF, CARGO_ID, (SELECT C.DESCRICAO FROM CARGO C WHERE C.ID = F.CARGO_ID), RG, TELEFONE, TELEFONE_COMERCIAL, CELULAR, MATRICULA, OBSERVACAO, DATA_NASCIMENTO, APELIDO, SEXO, ENDERECO, NUMERO, COMPLEMENTO, BAIRRO_ID, CIDADE_ID, (SELECT C.ESTADO_ID FROM CIDADE C WHERE C.ID = F.CIDADE_ID), CEP, ATIVIDADE_ID, ORIGEM_ID, FOTO FROM LOG.FUNCIONARIO F WHERE ID = ? ORDER BY DATA_CADASTRO DESC", model.getId());

		return broker.getCollectionBean(FuncionarioModel.class, "id", "decrypt.nome", "flagAtivo", "dataCadastro", "usuarioCadastroModel.nome", "decrypt.email", "decrypt.cpf", "cargoModel.id", "cargoModel.descricao", "decrypt.rg", "decrypt.telefone", "decrypt.telefoneComercial", "decrypt.celular", "decrypt.matricula", "decrypt.observacao", "dataNascimento", "decrypt.apelido", "sexo", "decrypt.endereco", "decrypt.numero", "decrypt.complemento", "bairroModel.id", "cidadeModel.id", "cidadeModel.estadoModel.id", "decrypt.cep", "atividadeModel.id", "origemModel.id", "foto");
	}

	public FuncionarioModel inserirCrudModel(final FuncionarioModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("funcionario_id_seq"));

		broker.setSQL("INSERT INTO FUNCIONARIO (ID, NOME, FLAG_ATIVO, DATA_CADASTRO, USUARIO_CADASTRO_ID, EMAIL, CPF, CARGO_ID, RG, TELEFONE, TELEFONE_COMERCIAL, CELULAR, MATRICULA, OBSERVACAO, DATA_NASCIMENTO, APELIDO, SEXO, ENDERECO, NUMERO, COMPLEMENTO, BAIRRO_ID, CIDADE_ID, CEP, ATIVIDADE_ID, ORIGEM_ID, FOTO) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", model.getId(), TSCryptoUtil.criptografarByte(model.getNome()), model.getFlagAtivo(), new Timestamp(model.getDataCadastro().getTime()), model.getUsuarioCadastroModel().getId(), TSCryptoUtil.criptografarByte(model.getEmail()), TSCryptoUtil.criptografarByte(model.getCpf()), model.getCargoModel().getId(), TSCryptoUtil.criptografarByte(model.getRg()), TSCryptoUtil.criptografarByte(model.getTelefone()), TSCryptoUtil.criptografarByte(model.getTelefoneComercial()), TSCryptoUtil.criptografarByte(model.getCelular()), TSCryptoUtil.criptografarByte(model.getMatricula()), TSCryptoUtil.criptografarByte(model.getObservacao()), model.getDataNascimento(), TSCryptoUtil.criptografarByte(model.getApelido()), model.getSexo(), TSCryptoUtil.criptografarByte(model.getEndereco()), TSCryptoUtil.criptografarByte(model.getNumero()), TSCryptoUtil.criptografarByte(model.getComplemento()), model.getBairroModel().getId(), model.getCidadeModel().getId(), TSCryptoUtil.criptografarByte(model.getCep()), model.getAtividadeModel().getId(), model.getOrigemModel().getId(), model.getFoto());

		broker.execute();

		return model;
	}

	public FuncionarioModel alterarCrudModel(final FuncionarioModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE FUNCIONARIO SET NOME = ?, FLAG_ATIVO = ?, EMAIL = ?, CPF = ?, CARGO_ID = ?, RG = ?, TELEFONE = ?, TELEFONE_COMERCIAL = ?, CELULAR = ?, MATRICULA = ?, OBSERVACAO = ?, DATA_CADASTRO = ?, USUARIO_CADASTRO_ID = ?, DATA_NASCIMENTO = ?, APELIDO = ?, SEXO = ?, ENDERECO = ?, NUMERO = ?, COMPLEMENTO = ?, BAIRRO_ID = ?, CIDADE_ID = ?, CEP = ?, ATIVIDADE_ID = ?, ORIGEM_ID = ?, FOTO = ? WHERE ID = ?", TSCryptoUtil.criptografarByte(model.getNome()), model.getFlagAtivo(), TSCryptoUtil.criptografarByte(model.getEmail()), TSCryptoUtil.criptografarByte(model.getCpf()), model.getCargoModel().getId(), TSCryptoUtil.criptografarByte(model.getRg()), TSCryptoUtil.criptografarByte(model.getTelefone()), TSCryptoUtil.criptografarByte(model.getTelefoneComercial()), TSCryptoUtil.criptografarByte(model.getCelular()), TSCryptoUtil.criptografarByte(model.getMatricula()), TSCryptoUtil.criptografarByte(model.getObservacao()), new Timestamp(model.getDataAtualizacao().getTime()), model.getUsuarioAtualizacaoModel().getId(), model.getDataNascimento(), TSCryptoUtil.criptografarByte(model.getApelido()), model.getSexo(), TSCryptoUtil.criptografarByte(model.getEndereco()), TSCryptoUtil.criptografarByte(model.getNumero()), TSCryptoUtil.criptografarByte(model.getComplemento()), model.getBairroModel().getId(), model.getCidadeModel().getId(), TSCryptoUtil.criptografarByte(model.getCep()), model.getAtividadeModel().getId(), model.getOrigemModel().getId(), model.getFoto(), model.getId());

		broker.execute();

		return model;
	}

	public FuncionarioModel excluirCrudModel(final FuncionarioModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE FUNCIONARIO SET FLAG_ATIVO = FALSE, DATA_CADASTRO = ?, USUARIO_CADASTRO_ID = ? WHERE ID = ?", new Timestamp(model.getDataCadastro().getTime()), model.getUsuarioCadastroModel().getId(), model.getId());

		broker.execute();

		return model;
	}

}