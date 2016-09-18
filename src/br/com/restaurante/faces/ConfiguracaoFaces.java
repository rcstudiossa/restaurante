package br.com.restaurante.faces;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.restaurante.business.ConfiguracaoBS;
import br.com.restaurante.model.ConfiguracaoModel;
import br.com.restaurante.model.UsuarioModel;
import br.com.restaurante.util.Utilitario;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.web.faces.TSMainFaces;

@SuppressWarnings("serial")
@ViewScoped
@ManagedBean(name = "configuracaoFaces")
public class ConfiguracaoFaces extends TSMainFaces {

	@EJB
	private ConfiguracaoBS configuracaoBS;

	private List<ConfiguracaoModel> configuracoes;

	@Override
	@PostConstruct
	protected void clearFields() {

		this.configuracoes = this.configuracaoBS.pesquisar(Utilitario.getOrigemAtual());

		this.tratarRespostaDetail();

	}

	private void tratarResposta() {

		Date dataCadastro = new Date();
		UsuarioModel usuarioModel = Utilitario.getUsuarioLogado();
				
		for (ConfiguracaoModel model : this.configuracoes) {

			if (model.getTipoConfiguracaoModel().getTipoRespostaModel().isNumerico()) {

				model.setValor(model.getRespostaEscolhidaLong().toString());

			} else if (model.getTipoConfiguracaoModel().getTipoRespostaModel().isPontoFlutuante()) {

				model.setValor(model.getRespostaEscolhidaDouble().toString());

			} else if (model.getTipoConfiguracaoModel().getTipoRespostaModel().isBooleano()) {

				model.setValor(model.getRespostaEscolhidaBoolean().toString());

			} else {

				model.setValor(model.getRespostaEscolhida().toString());

			}

			model.setDataCadastro(dataCadastro);
			model.setUsuarioCadastroModel(usuarioModel);

		}

	}

	private void tratarRespostaDetail() {

		for (ConfiguracaoModel model : this.configuracoes) {

			if (model.getTipoConfiguracaoModel().getTipoRespostaModel().isNumerico()) {

				model.setRespostaEscolhidaLong(Long.valueOf(model.getValor()));

			} else if (model.getTipoConfiguracaoModel().getTipoRespostaModel().isPontoFlutuante()) {

				model.setRespostaEscolhidaDouble(Double.valueOf(model.getValor()));

			} else if (model.getTipoConfiguracaoModel().getTipoRespostaModel().isBooleano()) {

				model.setRespostaEscolhidaBoolean(Boolean.parseBoolean(model.getValor()));

			} else {

				model.setRespostaEscolhida(model.getValor());

			}

		}

	}

	@Override
	protected String update() throws TSApplicationException {

		this.tratarResposta();

		this.configuracaoBS.alterar(this.configuracoes);

		return null;

	}

	public List<ConfiguracaoModel> getConfiguracoes() {
		return configuracoes;
	}

	public void setConfiguracoes(List<ConfiguracaoModel> configuracoes) {
		this.configuracoes = configuracoes;
	}
}
