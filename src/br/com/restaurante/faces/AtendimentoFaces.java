package br.com.restaurante.faces;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.restaurante.business.ExecucaoPLSQLBS;
import br.com.restaurante.business.TemplateBS;
import br.com.restaurante.model.FuncaoModel;
import br.com.restaurante.model.TemplateModel;
import br.com.restaurante.model.TemplateTabModel;
import br.com.restaurante.util.Constantes;
import br.com.restaurante.util.Utilitario;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.faces.TSMainFaces;
import br.com.topsys.web.util.TSFacesUtil;

@ViewScoped
@SuppressWarnings("serial")
@ManagedBean(name = "atendimentoFaces")
public class AtendimentoFaces extends TSMainFaces {

	@EJB
	private TemplateBS templateBS;

	@EJB
	private ExecucaoPLSQLBS execucaoPLSQLBS;

	private TemplateModel templateModel;

	private Integer tabAtiva;

	private List<String> facesUtilizados;

	@Override
	@PostConstruct
	protected void clearFields() {

		this.templateModel = new TemplateModel();

		this.facesUtilizados = new ArrayList<String>();

		this.carregar(Utilitario.getUsuarioLogado().getFuncaoLogada());

	}

	private void carregar(FuncaoModel funcaoModel) {

		this.templateModel = this.templateBS.obterDinamico(funcaoModel);

		if (TSUtil.isEmpty(this.templateModel)) {
			super.addErrorMessage("Não existe template da Função");
			return;
		}

		if (!TSUtil.isEmpty(this.templateModel.getTabs())) {
			this.templateModel.getTabs().get(this.templateModel.getTabs().size() - 1).setFlagUltimaTab(true);
		}

		this.setTabAtiva(0);

	}

	public String getUrlTab() {
		return !TSUtil.isEmpty(this.templateModel) && !TSUtil.isEmpty(this.templateModel.getTabs()) ? this.templateModel.getTabs().get(this.tabAtiva).getTabModel().getUrl() : "";
	}

	public Long getIdTab() {
		return !TSUtil.isEmpty(this.templateModel) && !TSUtil.isEmpty(this.templateModel.getTabs()) ? this.templateModel.getTabs().get(this.tabAtiva).getTabModel().getId() : null;
	}

	public Integer getTabAtiva() {
		return tabAtiva;
	}

	public void setTabAtiva(Integer tabAtiva) {

		if (tabAtiva.equals(this.tabAtiva)) {
			return;
		}

		TemplateTabModel model = this.templateModel.getTabs().get(tabAtiva);

		this.processarTab(model);

		this.tabAtiva = tabAtiva;
	}

	private void processarTab(TemplateTabModel model) {

		if (!TSUtil.isEmpty(model.getTabModel().getFacesReset())) {

			if (this.facesUtilizados.contains(model.getTabModel().getFacesReset())) {

				TSFacesUtil.resetManagedBean(model.getTabModel().getFacesReset());

			} else {

				this.facesUtilizados.add(model.getTabModel().getFacesReset());

			}

		}

		if (!TSUtil.isEmpty(model.getTabModel().getFlagGenerica()) && model.getTabModel().getFlagGenerica()) {

			TSFacesUtil.removeObjectInSession(Constantes.SESSION_TAB_ID);
			TSFacesUtil.addObjectInSession(Constantes.SESSION_TAB_ID, model.getTabModel().getId());

		}

		TSFacesUtil.removeObjectInSession(Constantes.SESSION_TAB_TEMPLATE);
		TSFacesUtil.addObjectInSession(Constantes.SESSION_TAB_TEMPLATE, model);

		this.validarTab(model);

	}

	private void validarTab(TemplateTabModel tab) {

		tab.setFlagBloqueado(false);

		if (!TSUtil.isEmpty(tab.getValidacao())) {

			try {

				this.execucaoPLSQLBS.executarPLSQL(tab.getValidacao(), Utilitario.getAtendimentoSessao().getId(), null, Utilitario.getUsuarioLogado(), null);

			} catch (TSApplicationException e) {

				Map<String, String> mensagens = Utilitario.tratarErroExecucaoPLSQL(e.getMessage());

				for (Entry<String, String> msg : mensagens.entrySet()) {

					if (Constantes.TIPO_MENSAGEM_ERRO.equalsIgnoreCase(msg.getValue()) || Constantes.TIPO_MENSAGEM_BLOQUEIO.equalsIgnoreCase(msg.getValue())) {

						tab.setFlagBloqueado(true);

					}

				}

			} catch (Exception e) {

				e.printStackTrace();

			}

		}
	}

	public boolean isImpressaoAutomaticaPulaTab() {

		if (this.templateModel.getTabs().get(this.tabAtiva).getFlagImpressaoAutomatica()) {

			return true;

		} else if (this.templateModel.getTabs().get(this.tabAtiva).getFlagPularTab()) {

			if (this.tabAtiva < this.templateModel.getTabs().size() - 1) {

				this.setTabAtiva(tabAtiva + 1);

			}

		}

		return false;
	}

	public TemplateModel getTemplateModel() {
		return templateModel;
	}

	public void setTemplateModel(TemplateModel templateModel) {
		this.templateModel = templateModel;
	}

}
