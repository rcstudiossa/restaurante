package br.com.restaurante.dao;

import java.sql.Timestamp;
import java.util.List;

import br.com.restaurante.model.FuncaoModel;
import br.com.restaurante.model.OrigemModel;
import br.com.restaurante.model.SetorModel;
import br.com.restaurante.model.UsuarioModel;
import br.com.restaurante.model.UsuarioSenhaModel;
import br.com.restaurante.util.Constantes;
import br.com.restaurante.util.Utilitario;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

public final class UsuarioDAO implements CrudDAO<UsuarioModel> {

	public UsuarioModel obterCrudModel(final UsuarioModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, NOME, LOGIN, SENHA, DESCRICAO, FLAG_ADMINISTRADOR, FLAG_ATIVO, OBTER_DATA_CADASTRO_LOG(ID, 'USUARIO'), OBTER_USUARIO_CADASTRO_LOG(ID, 'USUARIO'), DATA_CADASTRO DATA_ALTERACAO, (SELECT NOME FROM USUARIO U1 WHERE U1.ID = U.USUARIO_CADASTRO_ID) USUARIO_ALTERACAO, EMAIL, CPF, SETOR_ID, (SELECT S.DESCRICAO FROM SETOR S WHERE S.ID = U.SETOR_ID), RG, TELEFONE, DATA_ALTERACAO_SENHA, QTD_TENTATIVAS_LOGIN_INVALIDO, MATRICULA, OBSERVACAO, DATA_NASCIMENTO, FLAG_USUARIO, APELIDO, SEXO, ENDERECO, NUMERO, COMPLEMENTO, BAIRRO_ID, CIDADE_ID, (SELECT C.ESTADO_ID FROM CIDADE C WHERE C.ID = U.CIDADE_ID), CEP FROM USUARIO U WHERE ID = ?", model.getId());

		return (UsuarioModel) broker.getObjectBean(UsuarioModel.class, "id", "nome", "login", "senha", "descricao", "flagAdministrador", "flagAtivo", "dataCadastro", "usuarioCadastroModel.nome", "dataAtualizacao", "usuarioAtualizacaoModel.nome", "email", "cpf", "setorModel.id", "setorModel.descricao", "rg", "telefone", "dataAlteracaoSenha", "qtdTentativasLoginInvalido", "matricula", "observacao", "dataNascimento", "flagUsuario", "apelido", "sexo", "endereco", "numero", "complemento", "bairroModel.id", "cidadeModel.id", "cidadeModel.estadoModel.id", "cep");
	}

	public UsuarioModel obter(String nome) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, NOME FROM USUARIO WHERE NOME = ?", nome);

		return (UsuarioModel) broker.getObjectBean(UsuarioModel.class, "id", "nome");
	}

	public UsuarioModel obterAcesso(final UsuarioModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT U.ID, U.NOME, U.LOGIN, U.SENHA, U.DESCRICAO, U.FLAG_ADMINISTRADOR, U.FLAG_ATIVO, U.DATA_CADASTRO, (SELECT U2.NOME FROM USUARIO U2 WHERE U2.ID = U.USUARIO_CADASTRO_ID), U.EMAIL, U.CPF, U.SETOR_ID, (SELECT S.DESCRICAO FROM SETOR S WHERE S.ID = U.SETOR_ID), DATA_ALTERACAO_SENHA, QTD_TENTATIVAS_LOGIN_INVALIDO FROM USUARIO U WHERE U.FLAG_ATIVO = TRUE AND U.FLAG_USUARIO = TRUE AND U.LOGIN = ? ", model.getLogin());

		return (UsuarioModel) broker.getObjectBean(UsuarioModel.class, "id", "nome", "login", "senha", "descricao", "flagAdministrador", "flagAtivo", "dataCadastro", "usuarioCadastroModel.nome", "email", "cpf", "setorModel.id", "setorModel.descricao", "dataAlteracaoSenha", "qtdTentativasLoginInvalido");
	}

	@SuppressWarnings("unchecked")
	public List<UsuarioModel> pesquisarCrudModel(final UsuarioModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, NOME, LOGIN, DESCRICAO, FLAG_ADMINISTRADOR, FLAG_ATIVO, EMAIL, CPF, DATA_ALTERACAO_SENHA, QTD_TENTATIVAS_LOGIN_INVALIDO, QTD_TENTATIVAS_LOGIN_INVALIDO >= (SELECT VALOR::INTEGER FROM CONFIGURACAO C WHERE C.TIPO_CONFIGURACAO_ID = 2 AND C.ORIGEM_ID = ?) FROM USUARIO WHERE SEM_ACENTOS(NOME) ILIKE SEM_ACENTOS(COALESCE(?, NOME)) AND COALESCE(CPF, '0') ILIKE COALESCE(?, COALESCE(CPF, '0')) AND COALESCE(LOGIN, '') = COALESCE(?, COALESCE(LOGIN, '')) AND FLAG_ATIVO = COALESCE(?, FLAG_ATIVO) ORDER BY NOME, LOGIN", model.getOrigemModel().getId(), Utilitario.getStringIlike(model.getNome(), true), Utilitario.getStringIlike(model.getCpf(), false), Utilitario.getStringIlike(model.getLogin(), false), model.getFlagAtivo());

		return broker.getCollectionBean(UsuarioModel.class, "id", "nome", "login", "descricao", "flagAdministrador", "flagAtivo", "email", "cpf", "dataAlteracaoSenha", "qtdTentativasLoginInvalido", "flagBloqueado");
	}

	@SuppressWarnings("unchecked")
	public List<UsuarioModel> pesquisarLog(final UsuarioModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, NOME, LOGIN, SENHA, DESCRICAO, FLAG_ADMINISTRADOR, FLAG_ATIVO, DATA_CADASTRO, (SELECT U2.NOME FROM USUARIO U2 WHERE U2.ID = U.USUARIO_CADASTRO_ID), DATA_CADASTRO DATA_ALTERACAO, (SELECT NOME FROM USUARIO U1 WHERE U1.ID = U.USUARIO_CADASTRO_ID) USUARIO_ALTERACAO, EMAIL, CPF, SETOR_ID, (SELECT S.DESCRICAO FROM SETOR S WHERE S.ID = U.SETOR_ID), RG, TELEFONE, DATA_ALTERACAO_SENHA, QTD_TENTATIVAS_LOGIN_INVALIDO, MATRICULA, OBSERVACAO, DATA_NASCIMENTO, FLAG_USUARIO, APELIDO, SEXO, ENDERECO, NUMERO, COMPLEMENTO, BAIRRO_ID, CIDADE_ID, (SELECT C.ESTADO_ID FROM CIDADE C WHERE C.ID = U.CIDADE_ID), CEP FROM LOG.USUARIO U WHERE ID = ? ORDER BY DATA_CADASTRO DESC", model.getId());

		return broker.getCollectionBean(UsuarioModel.class, "id", "nome", "login", "senha", "descricao", "flagAdministrador", "flagAtivo", "dataCadastro", "usuarioCadastroModel.nome", "dataAtualizacao", "usuarioAtualizacaoModel.nome", "email", "cpf", "setorModel.id", "setorModel.descricao", "rg", "telefone", "dataAlteracaoSenha", "qtdTentativasLoginInvalido", "matricula", "observacao", "dataNascimento", "flagUsuario", "apelido", "sexo", "endereco", "numero", "complemento", "bairroModel.id", "cidadeModel.id", "cidadeModel.estadoModel.id", "cep");
	}

	@SuppressWarnings("unchecked")
	public List<UsuarioModel> pesquisar(OrigemModel origem, SetorModel setorModel) {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("SELECT DISTINCT U.ID, U.NOME, U.LOGIN, U.DESCRICAO, U.FLAG_ADMINISTRADOR, U.FLAG_ATIVO FROM USUARIO U, USUARIO_FUNCAO UF WHERE U.ID = UF.USUARIO_ID AND U.FLAG_ATIVO AND UF.FLAG_ATIVO AND UF.ORIGEM_ID = ? AND U.SETOR_ID = ? ORDER BY NOME", origem.getId(), setorModel.getId());
		
		return broker.getCollectionBean(UsuarioModel.class, "id", "nome", "login", "descricao", "flagAdministrador", "flagAtivo");
	}

	@SuppressWarnings("unchecked")
	public List<UsuarioModel> pesquisar(FuncaoModel funcaoModel) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT DISTINCT U.ID, U.NOME, U.LOGIN, U.DESCRICAO, U.FLAG_ADMINISTRADOR, U.FLAG_ATIVO FROM USUARIO U, USUARIO_FUNCAO UF WHERE UF.USUARIO_ID = U.ID AND UF.FUNCAO_ID = ? AND UF.FLAG_ATIVO AND U.FLAG_ATIVO ORDER BY U.NOME", funcaoModel.getId());

		return broker.getCollectionBean(UsuarioModel.class, "id", "nome", "login", "descricao", "flagAdministrador", "flagAtivo");
	}

	@SuppressWarnings("unchecked")
	public List<UsuarioModel> autoComplete(String query) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, NOME, FLAG_ATIVO FROM USUARIO U WHERE FLAG_ATIVO = TRUE AND SEM_ACENTOS(NOME) LIKE SEM_ACENTOS(?)", Utilitario.getStringIlike(query, true));

		return broker.getCollectionBean(UsuarioModel.class, "id", "nome", "flagAtivo");

	}

	public UsuarioModel inserirCrudModel(final UsuarioModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("usuario_id_seq"));

		broker.setSQL("INSERT INTO USUARIO (ID, NOME, LOGIN, SENHA, DESCRICAO, FLAG_ATIVO, DATA_CADASTRO, USUARIO_CADASTRO_ID, EMAIL, CPF, SETOR_ID, RG, TELEFONE, DATA_ALTERACAO_SENHA, MATRICULA, OBSERVACAO, DATA_NASCIMENTO, FLAG_USUARIO, APELIDO, SEXO, ENDERECO, NUMERO, COMPLEMENTO, BAIRRO_ID, CIDADE_ID, CEP) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", model.getId(), model.getNome(), model.getLogin(), model.getSenha(), model.getDescricao(), model.getFlagAtivo(), new Timestamp(model.getDataCadastro().getTime()), model.getUsuarioCadastroModel().getId(), model.getEmail(), model.getCpf(), TSUtil.tratarLong(model.getSetorModel().getId()), model.getRg(), model.getTelefone(), model.getDataAlteracaoSenha(), model.getMatricula(), model.getObservacao(), model.getDataNascimento(), model.getFlagUsuario(), model.getApelido(), model.getSexo(), model.getEndereco(), model.getNumero(), model.getComplemento(), model.getBairroModel().getId(), model.getCidadeModel().getId(), model.getCep());

		broker.execute();

		return model;
	}

	public UsuarioModel alterarCrudModel(final UsuarioModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE USUARIO SET NOME = ?, LOGIN = ?, SENHA = ?, DESCRICAO = ?, FLAG_ATIVO = ?, EMAIL = ?, CPF = ?, SETOR_ID = ?, RG = ?, TELEFONE = ?, DATA_ALTERACAO_SENHA = ?, QTD_TENTATIVAS_LOGIN_INVALIDO = ?, MATRICULA = ?, OBSERVACAO = ?, DATA_CADASTRO = ?, USUARIO_CADASTRO_ID = ?, DATA_NASCIMENTO = ?, FLAG_USUARIO = ?, APELIDO = ?, SEXO = ?, ENDERECO = ?, NUMERO = ?, COMPLEMENTO = ?, BAIRRO_ID = ?, CIDADE_ID = ?, CEP = ? WHERE ID = ?", model.getNome(), model.getLogin(), model.getSenha(), model.getDescricao(), model.getFlagAtivo(), model.getEmail(), model.getCpf(), TSUtil.tratarLong(model.getSetorModel().getId()), model.getRg(), model.getTelefone(), model.getDataAlteracaoSenha(), model.getQtdTentativasLoginInvalido(), model.getMatricula(), model.getObservacao(), new Timestamp(model.getDataAtualizacao().getTime()), model.getUsuarioAtualizacaoModel().getId(), model.getDataNascimento(), model.getFlagUsuario(), model.getApelido(), model.getSexo(), model.getEndereco(), model.getNumero(), model.getComplemento(), model.getBairroModel().getId(), model.getCidadeModel().getId(), model.getCep(), model.getId());

		broker.execute();

		return model;
	}

	public UsuarioModel excluirCrudModel(final UsuarioModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE USUARIO SET FLAG_ATIVO = FALSE, DATA_CADASTRO = ?, USUARIO_CADASTRO_ID = ? WHERE ID = ?", new Timestamp(model.getDataCadastro().getTime()), model.getUsuarioCadastroModel().getId(), model.getId());

		broker.execute();

		return model;
	}

	public UsuarioModel obterPorLogin(UsuarioModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, LOGIN, SENHA, QTD_TENTATIVAS_LOGIN_INVALIDO FROM USUARIO WHERE LOGIN = ? AND FLAG_USUARIO = TRUE", model.getLogin());

		return (UsuarioModel) broker.getObjectBean(UsuarioModel.class, "id", "login", "senha", "qtdTentativasLoginInvalido");
	}

	public void alterarSenha(UsuarioModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE USUARIO SET SENHA = ?, DATA_ALTERACAO_SENHA = ?, QTD_TENTATIVAS_LOGIN_INVALIDO = ? WHERE LOGIN = ?", model.getSenha(), model.getDataAlteracaoSenha(), model.getQtdTentativasLoginInvalido(), model.getLogin());

		broker.execute();
	}
	
	public void salvarSenhaAnterior(UsuarioSenhaModel model) throws TSApplicationException {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		model.setId(broker.getSequenceNextValue("usuario_senha_id_seq"));
		
		broker.setSQL("INSERT INTO USUARIO_SENHA (ID, USUARIO_ID, SENHA) VALUES (?, ?, ?)", model.getId(), model.getUsuarioModel().getId(), model.getSenha());
		
		broker.execute();
	}

	public void adicionarTentativaLoginInvalido(UsuarioModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE USUARIO SET QTD_TENTATIVAS_LOGIN_INVALIDO = QTD_TENTATIVAS_LOGIN_INVALIDO + 1 WHERE ID = ?", model.getId());

		broker.execute();
	}

	public void resetarQtdTentativaInvalidaLogin(UsuarioModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE USUARIO SET QTD_TENTATIVAS_LOGIN_INVALIDO = 0 WHERE ID = ?", model.getId());

		broker.execute();
	}

	public Boolean isCpfExistente(final UsuarioModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT EXISTS(SELECT 1 FROM USUARIO U WHERE U.CPF = ? AND U.ID <> COALESCE(?, 0))", model.getCpf(), model.getId());

		return (Boolean) broker.getObject();
	}
	
	@SuppressWarnings("unchecked")
	public List<UsuarioSenhaModel> pesquisarSenhasAnteriores(UsuarioModel model, OrigemModel origemModel) {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("SELECT US.SENHA FROM USUARIO_SENHA US WHERE US.USUARIO_ID = ? ORDER BY ID DESC LIMIT (SELECT C.VALOR::INTEGER FROM CONFIGURACAO C WHERE C.TIPO_CONFIGURACAO_ID = ? AND C.ORIGEM_ID = ?)", model.getId(), Constantes.TIPO_CONFIGURACAO_QTD_VALIDACAO_SENHAS_ANTERIORES, origemModel.getId());
		
		return broker.getCollectionBean(UsuarioSenhaModel.class, "senha");
	}
	
}
