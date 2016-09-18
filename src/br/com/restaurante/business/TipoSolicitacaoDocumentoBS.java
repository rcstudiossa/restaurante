package br.com.restaurante.business;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.restaurante.dao.CrudDAO;
import br.com.restaurante.dao.TipoSolicitacaoDocumentoDAO;
import br.com.restaurante.model.FuncaoModel;
import br.com.restaurante.model.TipoSolicitacaoDocumentoFuncaoModel;
import br.com.restaurante.model.TipoSolicitacaoDocumentoModel;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

@Stateless
@LocalBean
public class TipoSolicitacaoDocumentoBS extends CrudBS<TipoSolicitacaoDocumentoModel> {

	@Inject
	private TipoSolicitacaoDocumentoDAO tipoSolicitacaoDocumentoDAO;

	@Override
	public TipoSolicitacaoDocumentoModel obterCrudModel(final TipoSolicitacaoDocumentoModel crudModel) {

		TipoSolicitacaoDocumentoModel model = this.tipoSolicitacaoDocumentoDAO.obterCrudModel(crudModel);

		if (!TSUtil.isEmpty(model)) {

			model.setFuncoes(this.tipoSolicitacaoDocumentoDAO.pesquisarFuncoes(crudModel));

		}

		return model;
	}

	@Override
	public TipoSolicitacaoDocumentoModel inserirCrudModel(final TipoSolicitacaoDocumentoModel crudModel) throws TSApplicationException {

		TipoSolicitacaoDocumentoModel model = this.tipoSolicitacaoDocumentoDAO.inserirCrudModel(crudModel);

		for (TipoSolicitacaoDocumentoFuncaoModel funcao : crudModel.getFuncoes()) {

			this.tipoSolicitacaoDocumentoDAO.inserir(funcao);

		}

		return model;

	}

	public TipoSolicitacaoDocumentoModel alterarCrudModel(final TipoSolicitacaoDocumentoModel crudModel) throws TSApplicationException {

		TipoSolicitacaoDocumentoModel model = this.tipoSolicitacaoDocumentoDAO.alterarCrudModel(crudModel);

		for (TipoSolicitacaoDocumentoFuncaoModel funcao : crudModel.getFuncoes()) {

			if (TSUtil.isEmpty(funcao.getId())) {

				this.tipoSolicitacaoDocumentoDAO.inserir(funcao);

			}

		}

		return model;

	}

	public void excluir(final TipoSolicitacaoDocumentoFuncaoModel crudModel) throws TSApplicationException {
		this.tipoSolicitacaoDocumentoDAO.excluir(crudModel);
	}

	public List<TipoSolicitacaoDocumentoModel> pesquisar(FuncaoModel funcaoModel) {
		return this.tipoSolicitacaoDocumentoDAO.pesquisar(funcaoModel);
	}

	public String getReferenciaComReplace(final TipoSolicitacaoDocumentoModel model, final Long movimentacaoId) {
		return this.tipoSolicitacaoDocumentoDAO.getReferenciaComReplace(model, movimentacaoId);
	}

	@Override
	protected CrudDAO<TipoSolicitacaoDocumentoModel> getCrudDAO() {
		return this.tipoSolicitacaoDocumentoDAO;
	}

}
