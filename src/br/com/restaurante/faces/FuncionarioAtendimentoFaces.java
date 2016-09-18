package br.com.restaurante.faces;

import java.io.File;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

import br.com.restaurante.business.AtendimentoBS;
import br.com.restaurante.business.ComboBS;
import br.com.restaurante.business.CrudBS;
import br.com.restaurante.model.AtendimentoModel;
import br.com.restaurante.model.StatusAtendimentoModel;
import br.com.restaurante.model.UsuarioModel;
import br.com.restaurante.util.Constantes;
import br.com.restaurante.util.JasperUtil;
import br.com.restaurante.util.Utilitario;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.util.TSFacesUtil;

@ViewScoped
@SuppressWarnings("serial")
@ManagedBean(name = "funcionarioAtendimentoFaces")
public class FuncionarioAtendimentoFaces extends CrudFaces<AtendimentoModel> {

	@EJB
	private AtendimentoBS atendimentoBS;

	@EJB
	private ComboBS comboBS;

	private List<SelectItem> comboStatusAtendimento;
	private List<SelectItem> comboUsuarioResponsavel;
	
	private Date dataConvocacao;
	private boolean flagImpressaoCompleta;

	@Override
	@PostConstruct
	protected void clearFields() {

		this.crudModel = this.getInstanceAtendimento();

		this.crudPesquisaModel = new AtendimentoModel();
		this.crudPesquisaModel.setFuncionarioModel(Utilitario.getFuncionarioSessao());
		this.crudPesquisaModel.setOrigemModel(Utilitario.getOrigemAtual());

		this.comboStatusAtendimento = super.initCombo(this.comboBS.pesquisarStatusAtendimento(), "id", "descricao");
		this.comboUsuarioResponsavel = super.initCombo(this.comboBS.pesquisarUsuarios(), "id", "nome");

		this.grid = this.atendimentoBS.pesquisarCrudModel(this.crudPesquisaModel);

	}

	private AtendimentoModel getInstanceAtendimento() {

		AtendimentoModel model = new AtendimentoModel();

		model.setStatusAtendimentoModel(new StatusAtendimentoModel());
		model.setUsuarioResponsavelModel(new UsuarioModel());
		model.setFuncionarioModel(Utilitario.getFuncionarioSessao());
		model.setOrigemModel(Utilitario.getOrigemAtual());

		return model;

	}
	
	@Override
	protected boolean validaCampos() {

		boolean valida = true;
		
		if(TSUtil.isEmpty(this.crudModel.getData())){
			valida = false;
			super.addErrorMessage("Data: Campo obrigatório");
		}
		
		if(TSUtil.isEmpty(this.crudModel.getStatusAtendimentoModel().getId())){
			valida = false;
			super.addErrorMessage("Status: Campo obrigatório");
		}
		
		if(TSUtil.isEmpty(this.crudModel.getUsuarioResponsavelModel().getId())){
			valida = false;
			super.addErrorMessage("Responsável: Campo obrigatório");
		}
		
		RequestContext.getCurrentInstance().addCallbackParam("valido", valida);
		
		return valida;
	}
	
	@Override
	protected void posPersist() throws TSApplicationException {
		Utilitario.atualizarAtendimentoSessao(this.atendimentoBS.obterCrudModel(this.crudModel));
	}
	
	public String instanciarAtendimento() {
		this.crudModel = this.getInstanceAtendimento();
		return null;
	}
	
	public String imprimirConvocacao() {

		if(TSUtil.isEmpty(this.dataConvocacao)){
			super.addErrorMessage("Data de convocação: Campo obrigatório");
			RequestContext.getCurrentInstance().addCallbackParam("valido", false);
			return null;
		}
		
		try {

			Map<String, Object> parametros = new HashMap<String, Object>();

			parametros.put("ID", this.crudModel.getId());
			parametros.put("DATA_CONVOCACAO", new Timestamp(this.dataConvocacao.getTime()));
			parametros.put("SUBREPORT_DIR", TSFacesUtil.getServletContext().getRealPath("WEB-INF" + File.separator + Constantes.PASTA_RELATORIO) + File.separator);

			new JasperUtil().gerarRelatorio("convocacao.jasper", parametros);
			
			RequestContext.getCurrentInstance().addCallbackParam("valido", true);

		} catch (Exception ex) {

			ex.printStackTrace();

			this.addErrorMessage("Houve um erro ao imprimir relatório, contate o administrador do sistema");

		}

		return null;

	}
	
	public String imprimirSolicitacoesExame() {
		
		try {
			
			Map<String, Object> parametros = new HashMap<String, Object>();
			
			parametros.put("ATENDIMENTO_ID", this.crudModel.getId());
			
			parametros.put("SUBREPORT_DIR", TSFacesUtil.getServletContext().getRealPath("WEB-INF" + File.separator + Constantes.PASTA_RELATORIO) + File.separator);
			
			new JasperUtil().gerarRelatorio("solicitacaoExameCompleta.jasper", parametros);
			
		} catch (Exception ex) {
			
			ex.printStackTrace();
			
			this.addErrorMessage("Houve um erro ao imprimir relatório, contate o administrador do sistema");
			
		}
		
		return null;
		
	}
	
	public String imprimirCompleto() {
		
		if(TSUtil.isEmpty(this.dataConvocacao)){
			super.addErrorMessage("Data de convocação: Campo obrigatório");
			RequestContext.getCurrentInstance().addCallbackParam("valido", false);
			return null;
		}
		
		try {
			
			Map<String, Object> parametros = new HashMap<String, Object>();
			
			parametros.put("ATENDIMENTO_ID", this.crudModel.getId());
			parametros.put("DATA_CONVOCACAO", new Timestamp(this.dataConvocacao.getTime()));
			parametros.put("SUBREPORT_DIR", TSFacesUtil.getServletContext().getRealPath("WEB-INF" + File.separator + Constantes.PASTA_RELATORIO) + File.separator);
			
			new JasperUtil().gerarRelatorio("atendimentoCompleto.jasper", parametros);
			
			RequestContext.getCurrentInstance().addCallbackParam("valido", true);
			
		} catch (Exception ex) {
			
			ex.printStackTrace();
			
			this.addErrorMessage("Houve um erro ao imprimir relatório, contate o administrador do sistema");
			
		}
		
		return null;
		
	}

	@Override
	protected CrudBS<AtendimentoModel> getCrudBS() {
		return this.atendimentoBS;
	}

	public List<SelectItem> getComboStatusAtendimento() {
		return comboStatusAtendimento;
	}

	public void setComboStatusAtendimento(List<SelectItem> comboStatusAtendimento) {
		this.comboStatusAtendimento = comboStatusAtendimento;
	}

	public List<SelectItem> getComboUsuarioResponsavel() {
		return comboUsuarioResponsavel;
	}

	public void setComboUsuarioResponsavel(List<SelectItem> comboUsuarioResponsavel) {
		this.comboUsuarioResponsavel = comboUsuarioResponsavel;
	}

	public Date getDataConvocacao() {
		return dataConvocacao;
	}

	public void setDataConvocacao(Date dataConvocacao) {
		this.dataConvocacao = dataConvocacao;
	}

	public boolean isFlagImpressaoCompleta() {
		return flagImpressaoCompleta;
	}

	public void setFlagImpressaoCompleta(boolean flagImpressaoCompleta) {
		this.flagImpressaoCompleta = flagImpressaoCompleta;
	}

}
