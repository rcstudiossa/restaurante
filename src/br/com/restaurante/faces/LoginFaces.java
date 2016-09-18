package br.com.restaurante.faces;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

import br.com.relatec.util.ConstantesRelatec;
import br.com.restaurante.business.ComboBS;
import br.com.restaurante.business.ConfiguracaoBS;
import br.com.restaurante.business.OrigemBS;
import br.com.restaurante.business.UsuarioBS;
import br.com.restaurante.business.UsuarioFuncaoBS;
import br.com.restaurante.model.ConfiguracaoModel;
import br.com.restaurante.model.OrigemModel;
import br.com.restaurante.model.TipoConfiguracaoModel;
import br.com.restaurante.model.UsuarioFuncaoModel;
import br.com.restaurante.model.UsuarioModel;
import br.com.restaurante.util.Constantes;
import br.com.restaurante.util.Utilitario;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.faces.TSMainFaces;
import br.com.topsys.web.util.TSFacesUtil;

@ViewScoped
@SuppressWarnings("serial")
@ManagedBean(name = "loginFaces")
public final class LoginFaces extends TSMainFaces {

	@EJB
	private OrigemBS origemBS;

	@EJB
	private UsuarioBS usuarioBS;

	@EJB
	private UsuarioFuncaoBS usuarioFuncaoBS;

	@EJB
	private ConfiguracaoBS configuracaoBS;
	
	@EJB
	private ComboBS comboBS;
	
	private OrigemModel origemModel;
	private UsuarioModel usuarioModel;
	private UsuarioModel usuarioAuxiliarModel;
	private List<SelectItem> comboOrigens;
	private String novaSenha;
	private String novaSenhaConfirmacao;
	private List<UsuarioFuncaoModel> funcoesAutenticacao;
	private UsuarioFuncaoModel usuarioFuncaoEscolhida;

	private boolean flagEsqueceuSenha;
	
	@PostConstruct
	protected void clearFields() {
		this.origemModel = new OrigemModel();
		this.usuarioModel = new UsuarioModel();
		this.usuarioFuncaoEscolhida = new UsuarioFuncaoModel();
		this.limparNovaSenha();
	}
	
	public void carregarFuncoes() {
		this.funcoesAutenticacao = this.usuarioFuncaoBS.pesquisar(this.origemModel, this.usuarioModel);
	}
	
	public String acessar() {

		try {
			
			this.usuarioModel.setUsuarioFuncaoModel(new UsuarioFuncaoModel());
			
			this.usuarioModel = this.usuarioBS.autenticar(this.usuarioModel);
			
			this.comboOrigens = super.initCombo(this.comboBS.pesquisarOrigens(this.usuarioModel), "id", "descricao");
			
			if(TSUtil.isEmpty(this.comboOrigens)){
				this.usuarioModel = new UsuarioModel();
				super.addErrorMessage("Nenhuma origem cadastrada para este usuário!");
				return null;
			}
			
			this.origemModel.setId(Long.valueOf((String) this.comboOrigens.get(0).getValue()));
			
			this.carregarFuncoes();
			
			if (!TSUtil.isEmpty(this.funcoesAutenticacao)) {

				if (this.funcoesAutenticacao.size() > 1 || this.comboOrigens.size() > 1) {

					RequestContext.getCurrentInstance().addCallbackParam("possuiVariasFuncoes", true);
					return null;

				} else {

					this.usuarioFuncaoEscolhida = this.funcoesAutenticacao.get(0);

					this.escolherFuncao();

				}

			} else {
				
				super.addErrorMessage("Usuário não possui função ativa");
				return null;
				
			}

			RequestContext.getCurrentInstance().addCallbackParam("possuiVariasFuncoes", false);

			return "/pages/dashboard.xhtml?faces-redirect=true";

		} catch (TSApplicationException e) {

			RequestContext.getCurrentInstance().addCallbackParam("possuiVariasFuncoes", false);
			
			super.throwException(e);

			this.clearFields();
			
		}

		return null;

	}

	public String escolherFuncao() {
		
		if(TSUtil.isEmpty(this.usuarioModel.getId())){
			super.addErrorMessage("Não foi possível autenticar no sistema");
			return null;
		}
		
		this.origemModel = this.origemBS.obterCrudModel(this.origemModel);
		
		this.usuarioModel.setOrigemModel(this.origemModel);
		
		this.usuarioModel.setUsuarioFuncaoModel(this.usuarioFuncaoEscolhida);

		return this.registrarAutenticacao();
	}
	
	private String registrarAutenticacao() {
		
		TSFacesUtil.getRequest().getSession().invalidate();
		
		this.usuarioModel.setOrigemModel(this.origemModel);
		
		TSFacesUtil.addObjectInSession(Constantes.SESSION_ORIGEM_ATUAL, this.origemModel);
		TSFacesUtil.addObjectInSession(Constantes.SESSION_USUARIO_LOGADO, this.usuarioModel);
		TSFacesUtil.addObjectInSession(ConstantesRelatec.GRUPO_LOGADO_ID, this.usuarioModel.getFuncaoLogada().getId());
		TSFacesUtil.addObjectInSession(ConstantesRelatec.FLAG_ACESSO_JAR, true);
		
		ConfiguracaoModel config = this.configuracaoBS.obter(new TipoConfiguracaoModel(Constantes.TIPO_CONFIGURACAO_PERIODO_MAXIMO_INATIVIDADE_SESSAO), this.origemModel);

		TSFacesUtil.getRequest().getSession().setMaxInactiveInterval((Integer.valueOf(config.getValor()) + 1) * 60);

		return "/pages/dashboard.xhtml?faces-redirect=true";
	}

	public String trocarSenha() {

		if (!this.validaAlterarSenha()) {
			return null;
		}

		try {

			this.usuarioAuxiliarModel.setOrigemModel(this.origemModel);
			
			this.usuarioBS.alterarSenha(this.usuarioAuxiliarModel, this.novaSenha, this.origemModel);

			this.addInfoMessage("Senha alterada com sucesso!");

		} catch (TSApplicationException e) {

			super.throwException(e);

		}

		this.limparNovaSenha();

		return null;

	}

	private void limparNovaSenha() {
		this.usuarioAuxiliarModel = new UsuarioModel();
		this.novaSenha = "";
		this.novaSenhaConfirmacao = "";
	}

	private boolean validaAlterarSenha() {

		if (!TSUtil.isEmpty(this.novaSenhaConfirmacao) && !TSUtil.isEmpty(this.novaSenha) && !this.novaSenha.equals(this.novaSenhaConfirmacao)) {
			this.addErrorMessage("Senhas não conferem");
			return false;
		}

		String erro = Utilitario.validarSenha(this.novaSenha);

		if (!TSUtil.isEmpty(erro)) {

			super.addErrorMessageKey(erro);
			return false;

		}

		return true;

	}
	
	public List<SelectItem> getComboOrigens() {
		return comboOrigens;
	}

	public void setComboOrigens(List<SelectItem> comboOrigens) {
		this.comboOrigens = comboOrigens;
	}

	public OrigemModel getOrigemModel() {
		return origemModel;
	}

	public void setOrigemModel(OrigemModel origemModel) {
		this.origemModel = origemModel;
	}

	public UsuarioModel getUsuarioModel() {
		return usuarioModel;
	}

	public void setUsuarioModel(UsuarioModel usuarioModel) {
		this.usuarioModel = usuarioModel;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getNovaSenhaConfirmacao() {
		return novaSenhaConfirmacao;
	}

	public void setNovaSenhaConfirmacao(String novaSenhaConfirmacao) {
		this.novaSenhaConfirmacao = novaSenhaConfirmacao;
	}

	public UsuarioModel getUsuarioAuxiliarModel() {
		return usuarioAuxiliarModel;
	}

	public void setUsuarioAuxiliarModel(UsuarioModel usuarioAuxiliarModel) {
		this.usuarioAuxiliarModel = usuarioAuxiliarModel;
	}

	public List<UsuarioFuncaoModel> getFuncoesAutenticacao() {
		return funcoesAutenticacao;
	}

	public void setFuncoesAutenticacao(List<UsuarioFuncaoModel> funcoesAutenticacao) {
		this.funcoesAutenticacao = funcoesAutenticacao;
	}

	public UsuarioFuncaoModel getUsuarioFuncaoEscolhida() {
		return usuarioFuncaoEscolhida;
	}

	public void setUsuarioFuncaoEscolhida(UsuarioFuncaoModel usuarioFuncaoEscolhida) {
		this.usuarioFuncaoEscolhida = usuarioFuncaoEscolhida;
	}

	public boolean isFlagEsqueceuSenha() {
		return flagEsqueceuSenha;
	}

	public void setFlagEsqueceuSenha(boolean flagEsqueceuSenha) {
		this.flagEsqueceuSenha = flagEsqueceuSenha;
	}

}
