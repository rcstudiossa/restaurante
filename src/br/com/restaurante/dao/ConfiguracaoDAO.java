package br.com.restaurante.dao;

import java.sql.Timestamp;
import java.util.List;

import br.com.restaurante.model.ConfiguracaoModel;
import br.com.restaurante.model.OrigemModel;
import br.com.restaurante.model.TipoConfiguracaoModel;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

public class ConfiguracaoDAO {

	public ConfiguracaoModel obter(ConfiguracaoModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT C.ID, TC.ID, TC.DESCRICAO, C.VALOR, TC.TIPO_RESPOSTA_ID, C.ORIGEM_ID FROM CONFIGURACAO C, TIPO_CONFIGURACAO TC WHERE TC.ID = C.TIPO_CONFIGURACAO_ID AND C.ID = ?", model.getId());

		return (ConfiguracaoModel)broker.getObjectBean(ConfiguracaoModel.class, "id", "tipoConfiguracaoModel.id", "tipoConfiguracaoModel.descricao", "valor", "tipoConfiguracaoModel.tipoRespostaModel.id", "origemModel.id");
	}
	
	public ConfiguracaoModel obter(TipoConfiguracaoModel tipoConfiguracaoModel, OrigemModel origemModel) {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("SELECT C.ID, TC.ID, TC.DESCRICAO, C.VALOR, TC.TIPO_RESPOSTA_ID, C.ORIGEM_ID FROM CONFIGURACAO C, TIPO_CONFIGURACAO TC WHERE TC.ID = C.TIPO_CONFIGURACAO_ID AND TC.ID = ? AND COALESCE(C.ORIGEM_ID, 0) = COALESCE(?, COALESCE(C.ORIGEM_ID, 0)) ", tipoConfiguracaoModel.getId(), origemModel.getId());
		
		return (ConfiguracaoModel)broker.getObjectBean(ConfiguracaoModel.class, "id", "tipoConfiguracaoModel.id", "tipoConfiguracaoModel.descricao", "valor", "tipoConfiguracaoModel.tipoRespostaModel.id", "origemModel.id");
	}
	
	@SuppressWarnings("unchecked")
	public List<ConfiguracaoModel> pesquisar(OrigemModel origemModel) {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("SELECT C.ID, TC.ID, TC.DESCRICAO, C.VALOR, TC.TIPO_RESPOSTA_ID, C.ORIGEM_ID, C.DATA_CADASTRO, C.USUARIO_CADASTRO_ID, (SELECT U.NOME FROM USUARIO U WHERE U.ID = C.USUARIO_CADASTRO_ID) FROM CONFIGURACAO C, TIPO_CONFIGURACAO TC WHERE TC.ID = C.TIPO_CONFIGURACAO_ID AND (C.ORIGEM_ID = ? OR C.ORIGEM_ID IS NULL) ORDER BY TC.ID", origemModel.getId());
		
		return broker.getCollectionBean(ConfiguracaoModel.class, "id", "tipoConfiguracaoModel.id", "tipoConfiguracaoModel.descricao", "valor", "tipoConfiguracaoModel.tipoRespostaModel.id", "origemModel.id", "dataCadastro", "usuarioCadastroModel.id", "usuarioCadastroModel.nome");
	}

	public ConfiguracaoModel alterar(final ConfiguracaoModel model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("UPDATE CONFIGURACAO SET VALOR = ?, DATA_CADASTRO = ?, USUARIO_CADASTRO_ID = ? WHERE ID = ?", model.getValor(), new Timestamp(model.getDataCadastro().getTime()), model.getUsuarioCadastroModel().getId(), model.getId());

		broker.execute();

		return model;
	}
}
