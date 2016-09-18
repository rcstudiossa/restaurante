package br.com.restaurante.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.restaurante.dao.CrudDAO;
import br.com.restaurante.dao.MedidaDAO;
import br.com.restaurante.model.MedidaModel;
import br.com.restaurante.model.MedidaUnidadeMedidaModel;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

@Stateless
@LocalBean
public class MedidaBS extends CrudBS<MedidaModel> {

	@Inject
	private MedidaDAO medidaDAO;

	@EJB
	private UnidadeMedidaBS unidadeMedidaBS;

	@Override
	protected CrudDAO<MedidaModel> getCrudDAO() {
		return medidaDAO;
	}

	@Override
	public MedidaModel obterCrudModel(MedidaModel crudModel) {

		MedidaModel medida = this.medidaDAO.obterCrudModel(crudModel);

		if (!TSUtil.isEmpty(medida)) {

			medida.setUnidadesMedidas(this.medidaDAO.pesquisarUnidades(medida));

		}

		return medida;

	}

	@Override
	public MedidaModel inserirCrudModel(MedidaModel crudModel) throws TSApplicationException {

		this.medidaDAO.inserirCrudModel(crudModel);

		for (MedidaUnidadeMedidaModel unidade : crudModel.getUnidadesMedidas()) {

			unidade.setMedidaModel(crudModel);

			this.medidaDAO.inserirUnidade(unidade);

		}

		return crudModel;
	}

	@Override
	public MedidaModel alterarCrudModel(MedidaModel crudModel) throws TSApplicationException {

		this.medidaDAO.alterarCrudModel(crudModel);

		this.medidaDAO.excluirMedidasUnidade(crudModel);

		for (MedidaUnidadeMedidaModel unidade : crudModel.getUnidadesMedidas()) {

			unidade.setMedidaModel(crudModel);

			this.medidaDAO.inserirUnidade(unidade);

		}

		return crudModel;
	}

	public MedidaUnidadeMedidaModel excluirUnidade(final MedidaUnidadeMedidaModel model) throws TSApplicationException {
		return this.medidaDAO.excluirUnidade(model);
	}

	public List<MedidaUnidadeMedidaModel> pesquisarUnidades(final MedidaModel model) {
		return this.medidaDAO.pesquisarUnidades(model);
	}

}
