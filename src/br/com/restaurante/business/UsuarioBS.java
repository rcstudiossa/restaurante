package br.com.restaurante.business;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.restaurante.dao.CrudDAO;
import br.com.restaurante.dao.UsuarioDAO;
import br.com.restaurante.model.ConfiguracaoModel;
import br.com.restaurante.model.FuncaoModel;
import br.com.restaurante.model.OrigemModel;
import br.com.restaurante.model.SetorModel;
import br.com.restaurante.model.TipoConfiguracaoModel;
import br.com.restaurante.model.UsuarioFuncaoModel;
import br.com.restaurante.model.UsuarioModel;
import br.com.restaurante.model.UsuarioSenhaModel;
import br.com.restaurante.util.Constantes;
import br.com.restaurante.util.Utilitario;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSCryptoUtil;
import br.com.topsys.util.TSUtil;

@Stateless
@LocalBean
public class UsuarioBS extends CrudBS<UsuarioModel> {

	@Inject
	private UsuarioDAO usuarioDAO;

	@EJB
	private UsuarioFuncaoBS usuarioFuncaoBS;

	@EJB
	private ConfiguracaoBS configuracaoBS;

	public UsuarioModel obter(String query) {
		return this.usuarioDAO.obter(query);
	}

	public UsuarioModel obterCrudModel(UsuarioModel model) {

		UsuarioModel usuario = this.usuarioDAO.obterCrudModel(model);

		if (!TSUtil.isEmpty(usuario)) {
			usuario.setUsuarioFuncoes(this.usuarioFuncaoBS.pesquisar(usuario));
		}

		return usuario;
	}

	public UsuarioModel obterAcesso(final UsuarioModel model) {
		return this.usuarioDAO.obterAcesso(model);
	}

	public List<UsuarioModel> pesquisar(FuncaoModel funcaoModel) {
		return this.usuarioDAO.pesquisar(funcaoModel);
	}

	public List<UsuarioModel> autoComplete(String query) {
		return this.usuarioDAO.autoComplete(query);
	}

	public UsuarioModel inserirCrudModel(final UsuarioModel model) throws TSApplicationException {

		this.usuarioDAO.inserirCrudModel(model);

		for (UsuarioFuncaoModel usuarioFuncaoModel : model.getUsuarioFuncoes()) {

			this.usuarioFuncaoBS.inserir(usuarioFuncaoModel);

		}

		return model;

	}

	public UsuarioModel alterarCrudModel(final UsuarioModel model) throws TSApplicationException {

		UsuarioModel usuario = this.usuarioDAO.obterCrudModel(model);

		if ((!TSUtil.isEmpty(usuario.getSenha()) && TSUtil.isEmpty(model.getSenha())) || (TSUtil.isEmpty(usuario.getSenha()) && !TSUtil.isEmpty(model.getSenha())) || (!TSUtil.isEmpty(usuario.getSenha()) && !TSUtil.isEmpty(model.getSenha()) && !usuario.getSenha().equals(model.getSenha()))) {

			this.validarAlteracaoSenha(usuario, model.getSenha(), model.getOrigemModel());
			
		}

		this.usuarioDAO.alterarCrudModel(model);

		for (UsuarioFuncaoModel funcao : model.getUsuarioFuncoes()) {

			if (TSUtil.isEmpty(funcao.getId())) {

				this.usuarioFuncaoBS.inserir(funcao);

			} else {

				this.usuarioFuncaoBS.alterar(funcao);

			}

		}

		return model;

	}

	public UsuarioModel obterPorLogin(UsuarioModel model) {
		return this.usuarioDAO.obterPorLogin(model);
	}
	
	private void validarAlteracaoSenha(UsuarioModel usuario, String senhaHash, OrigemModel origemModel) throws TSApplicationException {
		
		List<UsuarioSenhaModel> senhasAnteriores = this.usuarioDAO.pesquisarSenhasAnteriores(usuario, origemModel);
		
		for(UsuarioSenhaModel usuarioSenha : senhasAnteriores){
			
			if (senhaHash.equals(usuarioSenha.getSenha())) {
				
				throw new TSApplicationException("ERRO.VALIDACAO.SENHA.IGUAL.ANTERIOR");
				
			}
			
		}
		
	}

	public void alterarSenha(UsuarioModel model, String novaSenha, OrigemModel origemModel) throws TSApplicationException {

		UsuarioModel usuario = this.obterPorLogin(model);

		if (TSUtil.isEmpty(usuario)) {
			throw new TSApplicationException("ERRO.VALIDACAO.USUARIO.INVALIDO");
		}

		String senha = TSCryptoUtil.gerarHash(model.getSenha(), Constantes.CRIPTOGRAFIA_USUARIO);

		if (!usuario.getSenha().equals(senha)) {

			this.adicionarTentativaLoginInvalido(usuario);
			throw new TSApplicationException("ERRO.VALIDACAO.SENHA.INVALIDA");

		}

		this.validarNumeroMaximoTentativasLogin(usuario);

		this.validarAlteracaoSenha(usuario, TSCryptoUtil.gerarHash(novaSenha, Constantes.CRIPTOGRAFIA_USUARIO), origemModel);

		model.setSenha(TSCryptoUtil.gerarHash(novaSenha, Constantes.CRIPTOGRAFIA_USUARIO));
		model.setDataAlteracaoSenha(new Date());
		model.setQtdTentativasLoginInvalido(0);
		
		this.usuarioDAO.alterarSenha(model);
		
		this.usuarioDAO.salvarSenhaAnterior(new UsuarioSenhaModel(usuario, model.getSenha()));
		
	}

	public void adicionarTentativaLoginInvalido(UsuarioModel model) throws TSApplicationException {
		this.usuarioDAO.adicionarTentativaLoginInvalido(model);
	}

	private void validarNumeroMaximoTentativasLogin(UsuarioModel usuario) throws TSApplicationException {

		ConfiguracaoModel config = this.configuracaoBS.obter(new TipoConfiguracaoModel(Constantes.TIPO_CONFIGURACAO_NUMERO_MAXIMO_TENTATIVAS_LOGIN), new OrigemModel());

		if (usuario.getQtdTentativasLoginInvalido() >= Integer.valueOf(config.getValor())) {

			throw new TSApplicationException("ERRO.VALIDACAO.TENTATIVA.LOGIN.INVALIDO.EXCEDIDA");

		}

	}

	private void validarTempoMaximoTrocaSenha(UsuarioModel usuario) throws TSApplicationException {

		boolean valida = true;

		if (TSUtil.isEmpty(usuario.getDataAlteracaoSenha())) {

			valida = true;

		} else {

			ConfiguracaoModel config = this.configuracaoBS.obter(new TipoConfiguracaoModel(Constantes.TIPO_CONFIGURACAO_MAXIMO_DIAS_TROCA_SENHA), new OrigemModel());

			Date dataMaximaTrocaSenha = Utilitario.getDataOperacaoDia(usuario.getDataAlteracaoSenha(), Integer.valueOf(config.getValor()));

			if (dataMaximaTrocaSenha.before(new Date())) {
				valida = false;
			}
		}

		if (!valida) {
			throw new TSApplicationException("ERRO.VALIDACAO.SENHA.EXPIRADA");
		}

	}

	public UsuarioModel autenticar(UsuarioModel usuarioModel) throws TSApplicationException {

		UsuarioModel usuario = this.obterAcesso(usuarioModel);

		if (TSUtil.isEmpty(usuario)) {
			throw new TSApplicationException("ERRO.VALIDACAO.USUARIO.INVALIDO");
		}

		this.validarNumeroMaximoTentativasLogin(usuario);

		String senha = TSCryptoUtil.gerarHash(usuarioModel.getSenha(), Constantes.CRIPTOGRAFIA_USUARIO);

		if (!usuario.getSenha().equals(senha)) {

			this.adicionarTentativaLoginInvalido(usuario);

			usuarioModel.setId(usuario.getId());

			throw new TSApplicationException("ERRO.VALIDACAO.SENHA.INVALIDA");

		} else {

			this.usuarioDAO.resetarQtdTentativaInvalidaLogin(usuario);

		}

		this.validarTempoMaximoTrocaSenha(usuario);

		this.usuarioDAO.resetarQtdTentativaInvalidaLogin(usuario);

		return usuario;
	}

	public Boolean isCpfExistente(final UsuarioModel model) {
		return this.usuarioDAO.isCpfExistente(model);
	}

	public List<UsuarioModel> pesquisar(OrigemModel origem, SetorModel setorModel) {
		return this.usuarioDAO.pesquisar(origem, setorModel);
	}

	@Override
	protected CrudDAO<UsuarioModel> getCrudDAO() {
		return usuarioDAO;
	}

}
