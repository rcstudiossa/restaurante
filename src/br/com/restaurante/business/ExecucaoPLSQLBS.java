package br.com.restaurante.business;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.restaurante.dao.ExecucaoPLSQLDAO;
import br.com.restaurante.model.UsuarioModel;
import br.com.topsys.exception.TSApplicationException;

@Stateless
@LocalBean
public class ExecucaoPLSQLBS {

	@Inject
	private ExecucaoPLSQLDAO execucaoPLSQLDAO;

	public void executarPLSQL(final String plpgsql, Long atendimentoId, Long idExterno, UsuarioModel usuarioModel, String objeto) throws TSApplicationException {
		execucaoPLSQLDAO.executarPLSQL(plpgsql, atendimentoId, idExterno, usuarioModel, objeto);
	}

}
