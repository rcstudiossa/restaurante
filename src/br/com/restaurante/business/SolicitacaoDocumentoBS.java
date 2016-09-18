package br.com.restaurante.business;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.restaurante.dao.CrudDAO;
import br.com.restaurante.dao.SolicitacaoDocumentoDAO;
import br.com.restaurante.model.SolicitacaoDocumentoModel;
import br.com.topsys.exception.TSApplicationException;

@LocalBean
@Stateless
public class SolicitacaoDocumentoBS extends CrudBS<SolicitacaoDocumentoModel>{

	@Inject
	private SolicitacaoDocumentoDAO solicitacaoDocumentoDAO;

	public SolicitacaoDocumentoModel inserirCrudModel(final SolicitacaoDocumentoModel crudModel) throws TSApplicationException {
		return this.solicitacaoDocumentoDAO.inserirCrudModel(crudModel);
	}

	public SolicitacaoDocumentoModel alterarCrudModel(final SolicitacaoDocumentoModel crudModel) throws TSApplicationException {
		return this.solicitacaoDocumentoDAO.alterarCrudModel(crudModel);
	}

	public void cancelarSolicitacaoDocumento(SolicitacaoDocumentoModel model) throws TSApplicationException {
		model.setFlagAtivo(Boolean.FALSE);
		this.solicitacaoDocumentoDAO.alterarCrudModel(model);
	}

	public SolicitacaoDocumentoModel cancelar(final SolicitacaoDocumentoModel model) throws TSApplicationException {
		return this.solicitacaoDocumentoDAO.cancelar(model);
	}

	@Override
	protected CrudDAO<SolicitacaoDocumentoModel> getCrudDAO() {
		return this.solicitacaoDocumentoDAO;
	}
}
