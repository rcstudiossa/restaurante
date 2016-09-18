package br.com.restaurante.dao;

import java.util.List;

import br.com.restaurante.model.FuncaoModel;
import br.com.restaurante.model.FuncaoOrigemModel;
import br.com.restaurante.model.OrigemModel;
import br.com.restaurante.model.UsuarioFuncaoModel;
import br.com.restaurante.model.UsuarioModel;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

public final class UsuarioFuncaoDAO {

	@SuppressWarnings("unchecked")
	public List<UsuarioFuncaoModel> pesquisar(final UsuarioModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT UF.ID, UF.USUARIO_ID, UF.FUNCAO_ID, (SELECT F.DESCRICAO FROM FUNCAO F WHERE F.ID = UF.FUNCAO_ID), UF.ORIGEM_ID, (SELECT O.DESCRICAO FROM ORIGEM O WHERE O.ID = UF.ORIGEM_ID), UF.FLAG_ATIVO, F.MENU_INICIAL_ID FROM USUARIO_FUNCAO UF, FUNCAO F WHERE F.ID = UF.FUNCAO_ID AND UF.USUARIO_ID = ? ORDER BY 4, 6", model.getId());

		return broker.getCollectionBean(UsuarioFuncaoModel.class, "id", "usuarioModel.id", "funcaoModel.id", "funcaoModel.descricao", "origemModel.id", "origemModel.descricao", "flagAtivo", "funcaoModel.menuInicial.id");

	}

	public UsuarioFuncaoModel obter(final UsuarioFuncaoModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT UF.ID, UF.USUARIO_ID, U.NOME, U.APELIDO, UF.FUNCAO_ID, F.DESCRICAO, UF.ORIGEM_ID, O.DESCRICAO, O.NOME FROM USUARIO_FUNCAO UF, USUARIO U, FUNCAO F, ORIGEM O WHERE UF.USUARIO_ID = U.ID AND UF.FUNCAO_ID = F.ID AND UF.ORIGEM_ID = O.ID AND UF.ID = ? ", model.getId());

		return (UsuarioFuncaoModel) broker.getObjectBean(UsuarioFuncaoModel.class, "id", "usuarioModel.id", "usuarioModel.nome", "usuarioModel.apelido", "funcaoModel.id", "funcaoModel.descricao", "origemModel.id", "origemModel.descricao", "origemModel.nome");

	}

	public UsuarioFuncaoModel obter(final FuncaoModel funcaoModel, final UsuarioModel usuarioModel) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT UF.ID, UF.USUARIO_ID, U.NOME, U.APELIDO, UF.FUNCAO_ID, F.DESCRICAO, UF.ORIGEM_ID, O.DESCRICAO, O.NOME FROM USUARIO_FUNCAO UF, USUARIO U, FUNCAO F, ORIGEM O WHERE UF.USUARIO_ID = U.ID AND UF.FUNCAO_ID = F.ID AND UF.ORIGEM_ID = O.ID AND UF.FUNCAO_ID = ? AND UF.USUARIO_ID = ? ", funcaoModel.getId(), usuarioModel.getId());

		return (UsuarioFuncaoModel) broker.getObjectBean(UsuarioFuncaoModel.class, "id", "usuarioModel.id", "usuarioModel.nome", "usuarioModel.apelido", "funcaoModel.id", "funcaoModel.descricao", "origemModel.id", "origemModel.descricao", "origemModel.nome");

	}

	@SuppressWarnings("unchecked")
	public List<UsuarioFuncaoModel> pesquisar(final OrigemModel origemModel, final UsuarioModel usuarioModel) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT UF.ID, UF.USUARIO_ID, UF.FUNCAO_ID, F.DESCRICAO, F.CSS, F.MENU_INICIAL_ID, UF.ORIGEM_ID FROM USUARIO_FUNCAO UF, FUNCAO F WHERE F.ID = UF.FUNCAO_ID AND F.FLAG_ATIVO AND F.FLAG_PERMISSAO_LOGIN AND UF.FLAG_ATIVO AND ORIGEM_ID = ? AND USUARIO_ID = ? ORDER BY F.DESCRICAO", origemModel.getId(), usuarioModel.getId());

		return broker.getCollectionBean(UsuarioFuncaoModel.class, "id", "usuarioModel.id", "funcaoModel.id", "funcaoModel.descricao", "funcaoModel.css", "funcaoModel.menuInicial.id", "origemModel.id");

	}

	@SuppressWarnings("unchecked")
	public List<UsuarioFuncaoModel> pesquisar(OrigemModel origem) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT UF.ID, UF.USUARIO_ID, U.NOME, UF.FUNCAO_ID, (SELECT F.DESCRICAO FROM FUNCAO F WHERE F.ID = UF.FUNCAO_ID) FROM USUARIO_FUNCAO UF, USUARIO U WHERE U.ID = UF.USUARIO_ID AND UF.FLAG_ATIVO AND U.FLAG_ATIVO AND UF.ORIGEM_ID = ? ORDER BY 3, 5", origem.getId());

		return broker.getCollectionBean(UsuarioFuncaoModel.class, "id", "usuarioModel.id", "usuarioModel.nome", "funcaoModel.id", "funcaoModel.descricao");

	}

	@SuppressWarnings("unchecked")
	public List<UsuarioFuncaoModel> pesquisar(FuncaoOrigemModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT UF.ID, UF.USUARIO_ID, U.NOME, UF.FUNCAO_ID, F.DESCRICAO, UF.ORIGEM_ID, O.DESCRICAO, UF.FLAG_ATIVO FROM USUARIO_FUNCAO UF, FUNCAO F, ORIGEM O, USUARIO U WHERE F.ID = UF.FUNCAO_ID AND O.ID = UF.ORIGEM_ID AND UF.USUARIO_ID = U.ID AND F.ID = ? AND O.ID = ? ORDER BY O.ID, U.NOME", model.getFuncaoModel().getId(), model.getOrigemModel().getId());

		return broker.getCollectionBean(UsuarioFuncaoModel.class, "id", "usuarioModel.id", "usuarioModel.nome", "funcaoModel.id", "funcaoModel.descricao", "origemModel.id", "origemModel.descricao", "flagAtivo");

	}

	@SuppressWarnings("unchecked")
	public List<UsuarioFuncaoModel> pesquisarSimilares(UsuarioFuncaoModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT UF.ID, UF.USUARIO_ID, U.NOME, UF.FUNCAO_ID, F.DESCRICAO, UF.ORIGEM_ID, O.DESCRICAO, UF.FLAG_ATIVO FROM USUARIO_FUNCAO UF, FUNCAO F, ORIGEM O, USUARIO U WHERE F.ID = UF.FUNCAO_ID AND O.ID = UF.ORIGEM_ID AND UF.USUARIO_ID = U.ID AND F.FLAG_ATIVO AND U.FLAG_ATIVO AND UF.FLAG_ATIVO AND F.ID = (SELECT UF2.FUNCAO_ID FROM USUARIO_FUNCAO UF2 WHERE UF2.ID = ?) AND O.ID = (SELECT UF2.ORIGEM_ID FROM USUARIO_FUNCAO UF2 WHERE UF2.ID = ?) ORDER BY O.ID, U.NOME", model.getId(), model.getId());

		return broker.getCollectionBean(UsuarioFuncaoModel.class, "id", "usuarioModel.id", "usuarioModel.nome", "funcaoModel.id", "funcaoModel.descricao", "origemModel.id", "origemModel.descricao", "flagAtivo");

	}

	public UsuarioFuncaoModel inserir(final UsuarioFuncaoModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("usuario_funcao_id_seq"));

		broker.setSQL("INSERT INTO USUARIO_FUNCAO (ID, USUARIO_ID, FUNCAO_ID, ORIGEM_ID) VALUES (?,?,?,?)", model.getId(), model.getUsuarioModel().getId(), model.getFuncaoModel().getId(), model.getOrigemModel().getId());

		broker.execute();
		
		return model;

	}

	public UsuarioFuncaoModel alterar(final UsuarioFuncaoModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE USUARIO_FUNCAO SET FLAG_ATIVO = ? WHERE ID = ?", model.getFlagAtivo(), model.getId());

		broker.execute();
		
		return model;

	}

	public UsuarioFuncaoModel excluir(final UsuarioFuncaoModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE USUARIO_FUNCAO SET FLAG_ATIVO = FALSE WHERE ID = ?", model.getId());

		broker.execute();
		
		return model;

	}

	public void alterarSituacao(final UsuarioFuncaoModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE USUARIO_FUNCAO SET FLAG_ATIVO = ? WHERE ID = ?", model.getFlagAtivo(), model.getId());

		broker.execute();

	}

	@SuppressWarnings("unchecked")
	public List<UsuarioFuncaoModel> pesquisar(UsuarioFuncaoModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT UF.ID, UF.USUARIO_ID, UF.FUNCAO_ID, UF.ORIGEM_ID FROM USUARIO_FUNCAO UF, USUARIO U, FUNCAO F WHERE U.ID = UF.USUARIO_ID AND F.ID = UF.FUNCAO_ID AND UF.FLAG_ATIVO AND F.FLAG_ATIVO AND U.FLAG_ATIVO AND U.SETOR_ID = COALESCE(?, U.SETOR_ID) AND UF.FUNCAO_ID = COALESCE(?, UF.FUNCAO_ID) AND UF.USUARIO_ID = COALESCE(?, UF.USUARIO_ID) AND UF.ORIGEM_ID = ? ORDER BY UF.ID", model.getUsuarioModel().getSetorModel().getId(), model.getFuncaoModel().getId(), model.getUsuarioModel().getId(), model.getOrigemModel().getId());

		return broker.getCollectionBean(UsuarioFuncaoModel.class, "id", "usuarioModel.id", "funcaoModel.id", "origemModel.id", "tipoEscalaModel.id", "localDistribuicaoModel.id");

	}

}
