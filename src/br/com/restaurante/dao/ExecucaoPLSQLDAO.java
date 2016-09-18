package br.com.restaurante.dao;

import br.com.restaurante.model.UsuarioModel;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

public final class ExecucaoPLSQLDAO {

	public void executarPLSQL(final String plpgsql, Long atendimentoId, Long idExterno, UsuarioModel usuarioModel, String objeto) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		String plsql = plpgsql;

		if (plsql.contains("P_ATENDIMENTO_ID")) {

			plsql = plsql.replaceAll("P_ATENDIMENTO_ID", atendimentoId.toString());
		}
		
		if (plsql.contains("P_ID_EXTERNO")) {

			plsql = plsql.replaceAll("P_ID_EXTERNO", idExterno.toString());
		}
		
		if (plpgsql.contains("P_USUARIO_ID")) {

			plsql = plsql.replaceAll("P_USUARIO_ID", usuarioModel.getId().toString());
		}
		
		if (plpgsql.contains("P_FUNCAO_ID")) {

			plsql = plsql.replaceAll("P_FUNCAO_ID", usuarioModel.getFuncaoLogada().getId().toString());
		}
		
		if (plpgsql.contains("P_OBJETO")) {
			
			plsql = plsql.replaceAll("P_OBJETO", objeto);
		}

		broker.setSQL("DO $$ " + plsql + "$$;");

		broker.execute();

	}

}
