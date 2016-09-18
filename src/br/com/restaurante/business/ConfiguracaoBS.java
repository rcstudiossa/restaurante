package br.com.restaurante.business;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.restaurante.dao.ConfiguracaoDAO;
import br.com.restaurante.model.ConfiguracaoModel;
import br.com.restaurante.model.OrigemModel;
import br.com.restaurante.model.TipoConfiguracaoModel;
import br.com.topsys.exception.TSApplicationException;

@LocalBean
@Stateless
public class ConfiguracaoBS {

	@Inject
	private ConfiguracaoDAO configuracaoDAO;

	public List<ConfiguracaoModel> pesquisar(OrigemModel model) {
		return this.configuracaoDAO.pesquisar(model);
	}

	public ConfiguracaoModel obter(ConfiguracaoModel model) {
		return this.configuracaoDAO.obter(model);
	}
	
	public ConfiguracaoModel obter(TipoConfiguracaoModel tipoConfiguracaoModel, OrigemModel origemModel) {
		return this.configuracaoDAO.obter(tipoConfiguracaoModel, origemModel);
	}

	public void alterar(List<ConfiguracaoModel> configuracoes) throws TSApplicationException {

		for (ConfiguracaoModel model : configuracoes) {

			ConfiguracaoModel configuracaoAnterior = this.obter(model);

			if (!configuracaoAnterior.getValor().equals(model.getValor())) {

				this.configuracaoDAO.alterar(model);
				
			}

		}

	}

}
