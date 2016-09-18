package br.com.restaurante.faces;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import br.com.restaurante.business.BairroBS;
import br.com.restaurante.business.ComboBS;
import br.com.restaurante.business.CrudBS;
import br.com.restaurante.model.BairroModel;
import br.com.restaurante.model.CidadeModel;
import br.com.restaurante.model.EstadoModel;
import br.com.topsys.util.TSUtil;

@ViewScoped
@SuppressWarnings("serial")
@ManagedBean(name = "bairroFaces")
public class BairroFaces extends CrudFaces<BairroModel> {

	@EJB
	private BairroBS bairroBS;
	
	@EJB
	private ComboBS comboBS;
	
	private List<SelectItem> comboEstado;
	private List<SelectItem> comboCidade;
	private List<SelectItem> comboCidadePesquisa;

	@Override
	@PostConstruct
	protected void clearFields() {

		this.crudModel = new BairroModel();
		this.crudModel.setCidadeModel(new CidadeModel());
		this.crudModel.getCidadeModel().setEstadoModel(new EstadoModel());

		this.crudPesquisaModel = new BairroModel();
		this.crudPesquisaModel.setCidadeModel(new CidadeModel());
		this.crudPesquisaModel.getCidadeModel().setEstadoModel(new EstadoModel());
		
		this.comboEstado = super.initCombo(this.comboBS.pesquisarEstados(), "id", "sigla");

	}
	
	@Override
	protected boolean validaCamposPesquisa() {
		
		boolean valida = true;
		
		if(TSUtil.isEmpty(this.crudPesquisaModel.getDescricao()) && TSUtil.isEmpty(this.crudPesquisaModel.getCidadeModel().getId()) && TSUtil.isEmpty(this.crudPesquisaModel.getCidadeModel().getEstadoModel().getId())){
			valida = false;
			super.addErrorMessage("Preencha ao menos um campo do filtro");
		}
		
		return valida;
	}
	
	@Override
	protected void posDetail() {
		this.carregarComboCidades();
	}
	
	public void carregarComboCidades(){
		if (!TSUtil.isEmpty(this.crudModel.getCidadeModel().getEstadoModel().getId())) {
			this.comboCidade = super.initCombo(this.comboBS.pesquisarCidades(this.crudModel.getCidadeModel().getEstadoModel()), "id", "descricaoCompleta");
		}
	}
	
	public void carregarComboCidadesPesquisa(){
		if (!TSUtil.isEmpty(this.crudPesquisaModel.getCidadeModel().getEstadoModel().getId())) {
			this.comboCidadePesquisa = super.initCombo(this.comboBS.pesquisarCidades(this.crudPesquisaModel.getCidadeModel().getEstadoModel()), "id", "descricaoCompleta");
		}
	}
	
	@Override
	protected CrudBS<BairroModel> getCrudBS() {
		return this.bairroBS;
	}

	public List<SelectItem> getComboEstado() {
		return comboEstado;
	}

	public void setComboEstado(List<SelectItem> comboEstado) {
		this.comboEstado = comboEstado;
	}

	public List<SelectItem> getComboCidade() {
		return comboCidade;
	}

	public void setComboCidade(List<SelectItem> comboCidade) {
		this.comboCidade = comboCidade;
	}

	public List<SelectItem> getComboCidadePesquisa() {
		return comboCidadePesquisa;
	}

	public void setComboCidadePesquisa(List<SelectItem> comboCidadePesquisa) {
		this.comboCidadePesquisa = comboCidadePesquisa;
	}

}
