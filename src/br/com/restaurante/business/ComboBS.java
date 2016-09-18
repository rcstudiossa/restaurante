package br.com.restaurante.business;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.restaurante.dao.ComboDAO;
import br.com.restaurante.model.AtividadeModel;
import br.com.restaurante.model.BairroModel;
import br.com.restaurante.model.CargoModel;
import br.com.restaurante.model.CentroResultadoModel;
import br.com.restaurante.model.CidModel;
import br.com.restaurante.model.CidadeModel;
import br.com.restaurante.model.ComboModel;
import br.com.restaurante.model.EstadoModel;
import br.com.restaurante.model.FrequenciaMarcacaoExameModel;
import br.com.restaurante.model.FuncaoModel;
import br.com.restaurante.model.OrigemModel;
import br.com.restaurante.model.PermissaoModel;
import br.com.restaurante.model.ProcedimentoModel;
import br.com.restaurante.model.SetorModel;
import br.com.restaurante.model.StatusAtendimentoModel;
import br.com.restaurante.model.TabModel;
import br.com.restaurante.model.TipoCronModel;
import br.com.restaurante.model.TipoQuizModel;
import br.com.restaurante.model.TipoRespostaModel;
import br.com.restaurante.model.UnidadeMedidaModel;
import br.com.restaurante.model.UsuarioModel;

@Stateless
@LocalBean
public class ComboBS {

	@Inject
	private ComboDAO comboDAO;

	public List<ComboModel> pesquisarSexo() {
		return this.comboDAO.pesquisarSexo();
	}

	public List<CidadeModel> pesquisarCidades(final EstadoModel model) {
		return this.comboDAO.pesquisarCidades(model);
	}
	
	public List<BairroModel> pesquisarBairros(final CidadeModel model) {
		return this.comboDAO.pesquisarBairros(model);
	}

	public List<EstadoModel> pesquisarEstados() {
		return this.comboDAO.pesquisarEstados();
	}

	public List<FuncaoModel> pesquisarFuncoes(final UsuarioModel usuario, OrigemModel origem) {
		return this.comboDAO.pesquisarFuncoes(usuario, origem);
	}

	public List<OrigemModel> pesquisarOrigens() {
		return this.comboDAO.pesquisarOrigens();
	}

	public List<ComboModel> pesquisarTipoLogradouro() {
		return this.comboDAO.pesquisarTipoLogradouro();
	}
	
	public List<PermissaoModel> pesquisarPermissoes() {
		return this.comboDAO.pesquisarPermissoes();
	}
	
	public List<FuncaoModel> pesquisarFuncoes() {
		return this.comboDAO.pesquisarFuncoes();
	}
	
	public List<SetorModel> pesquisarSetores() {
		return this.comboDAO.pesquisarSetores();
	}
	
	public List<UsuarioModel> pesquisarUsuarios() {
		return this.comboDAO.pesquisarUsuarios();
	}
	
	public List<TipoRespostaModel> pesquisarTiposResposta() {
		return this.comboDAO.pesquisarTiposResposta();
	}
	
	public List<TabModel> pesquisarTab() {
		return this.comboDAO.pesquisarTab();
	}
	
	public List<SetorModel> pesquisarSetores(OrigemModel origem) {
		return this.comboDAO.pesquisarSetores(origem);
	}
	
	public List<CentroResultadoModel> pesquisarCentroResultado() {
		return this.comboDAO.pesquisarCentroResultado();
	}
	
	public List<TipoQuizModel> pesquisarTiposQuizDocumento() {
		return this.comboDAO.pesquisarTiposQuizDocumento();
	}
	
	public List<CargoModel> pesquisarCargos() {
		return this.comboDAO.pesquisarCargos();
	}
	
	public List<AtividadeModel> pesquisarAtividades() {
		return this.comboDAO.pesquisarAtividades();
	}
	
	public List<StatusAtendimentoModel> pesquisarStatusAtendimento() {
		return this.comboDAO.pesquisarStatusAtendimento();
	}
	
	public List<ProcedimentoModel> pesquisarProcedimentos(final ProcedimentoModel model) {
		return this.comboDAO.pesquisarProcedimentos(model);
	}
	
	public List<UnidadeMedidaModel> pesquisarUnidadesMedida() {
		return this.comboDAO.pesquisarUnidadesMedida();
	}
	
	public List<OrigemModel> pesquisarOrigens(final UsuarioModel model) {
		return this.comboDAO.pesquisarOrigens(model);
	}
	
	public List<CidModel> pesquisarCids() {
		return this.comboDAO.pesquisarCids();
	}
	
	public List<TipoCronModel> pesquisarTiposCron() {
		return this.comboDAO.pesquisarTiposCron();
	}
	
	public List<FrequenciaMarcacaoExameModel> pesquisarFrequenciaMarcacaoExame() {
		return this.comboDAO.pesquisarFrequenciaMarcacaoExame();
	}
	

	
	
	
	
	public PermissaoModel obter(final PermissaoModel model) {
		return this.comboDAO.obter(model);
	}

	public OrigemModel obter(final OrigemModel model) {
		return this.comboDAO.obter(model);
	}
	
	public FuncaoModel obter(FuncaoModel model) {
		return this.comboDAO.obter(model);
	}
	
	public StatusAtendimentoModel obter(StatusAtendimentoModel model) {
		return this.comboDAO.obter(model);
	}
	
	public BairroModel obterBairro(final String bairro, final String cidade) {
		return this.comboDAO.obterBairro(bairro, cidade);
	}
	
	public ProcedimentoModel obter(ProcedimentoModel model) {
		return this.comboDAO.obter(model);
	}
	
	public TipoCronModel obter(TipoCronModel model) {
		return this.comboDAO.obter(model);
	}
	
	public FrequenciaMarcacaoExameModel obter(FrequenciaMarcacaoExameModel model) {
		return this.comboDAO.obter(model);
	}

}
