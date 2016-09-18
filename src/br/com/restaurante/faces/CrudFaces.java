package br.com.restaurante.faces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.restaurante.business.CrudBS;
import br.com.restaurante.model.CrudModel;
import br.com.restaurante.util.Utilitario;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.faces.TSMainFaces;

@SuppressWarnings("serial")
public abstract class CrudFaces<T extends CrudModel<T>> extends TSMainFaces {

	protected T crudModel;

	protected T crudSelectionModel;

	protected T crudPesquisaModel;

	protected List<T> grid;
	
	protected List<T> gridFiltrado;

	protected boolean alterar;

	protected boolean manterCampos;

	protected boolean ocultarMsg;

	protected boolean retornoSucesso;

	public CrudFaces() {

	}

	protected abstract CrudBS<T> getCrudBS();

	public String limpar() {
		this.alterar = false;
		clearFields();
		return SUCESSO;
	}

	public String limparPesquisa() {
		this.clearFields();
		grid = new ArrayList<T>();
		return SUCESSO;
	}

	protected void preFind() {

	}

	protected void preDetail() {

	}

	protected void posDetail() {

	}

	protected void preInsert() {
		this.crudModel.setDataCadastro(new Date());
		this.crudModel.setUsuarioCadastroModel(Utilitario.getUsuarioLogado());
	}

	protected void preUpdate() {
		this.crudModel.setDataAtualizacao(new Date());
		this.crudModel.setUsuarioAtualizacaoModel(Utilitario.getUsuarioLogado());
	}

	protected void posInsert() {

	}

	protected void posUpdate() {

	}

	protected void prePersist() {
		
	}

	protected void preDelete() {
		this.crudModel.setDataAtualizacao(new Date());
		this.crudModel.setUsuarioAtualizacaoModel(Utilitario.getUsuarioLogado());
	}

	protected void posDelete() {

	}

	protected void posPersist() throws TSApplicationException {

	}
	
	protected void tratarClone() {
		
	}
	
	public String obterClonar() {
		this.detail();
		return this.clonar();
	}
	
	public String clonar() {
		
		this.crudModel.setId(null);
		
		this.tratarClone();
		
		this.alterar = false;
		
		super.addInfoMessage("Clonagem executada com sucesso!");
		
		return null;
	}
	
	public String visualizarHistorico() {
		this.crudModel.setHistorico(this.getCrudBS().pesquisarLog(this.crudModel));
		return null;
	}

	public void gerarResultadoLista(List<?> lista) {

		if (TSUtil.isEmpty(lista)) {

			super.addInfoMessage("A pesquisa não retornou ocorrência");

		} else {

			Integer tamanho = lista.size();

			if (tamanho.equals(1)) {

				super.addInfoMessage("A pesquisa retornou 1 ocorrência");

			} else {

				super.addInfoMessage("A pesquisa retornou " + tamanho + " ocorrências");

			}

		}

	}

	@Override
	protected String find() {

		if (!validaCamposPesquisa()) {
			return null;
		}
		
		this.preFind();

		this.executeFind();

		this.gerarResultadoLista(grid);

		return null;

	}

	protected void executeFind() {
		this.grid = this.getCrudBS().pesquisarCrudModel(this.crudPesquisaModel);
	}

	@Override
	protected String detail() {

		if (!TSUtil.isEmpty(this.crudModel)) {

			this.preDetail();

			this.executeDetail();

			this.alterar = true;

			this.posDetail();

		}

		return null;

	}

	protected String detailSelection() {

		if (!TSUtil.isEmpty(this.crudSelectionModel)) {

			this.crudModel = this.crudSelectionModel;

			this.detail();

		}

		return null;

	}

	public final String detailSelectionEvent() {

		if (!TSUtil.isEmpty(this.crudSelectionModel)) {

			this.crudModel = this.crudSelectionModel;

			return this.detailEvent();

		}

		return null;
	}

	@Override
	protected String insert() throws TSApplicationException {

		super.setDefaultMessage(false);

		boolean valido = validaCampos();

		super.setClearFields(valido);

		if (!valido) {
			return null;
		}

		this.prePersist();

		this.preInsert();

		this.executeInsert();

		this.posInsert();

		this.posPersist();

		super.setDefaultMessage(true);

		super.setClearFields(!this.manterCampos);

		this.alterar = this.manterCampos;

		if (retornoSucesso) {
			return SUCESSO;
		}

		return null;

	}

	protected void executeUpdate() throws TSApplicationException {
		this.getCrudBS().alterarCrudModel(this.crudModel);
	}

	protected void executeInsert() throws TSApplicationException {
		this.getCrudBS().inserirCrudModel(this.crudModel);
	}

	protected void executeDelete() throws TSApplicationException {
		this.getCrudBS().excluirCrudModel(this.crudModel);
	}

	protected void executeDetail() {
		this.crudModel = (T) this.getCrudBS().obterCrudModel(this.crudModel);
	}

	@Override
	protected String update() throws TSApplicationException {

		super.setDefaultMessage(false);

		super.setClearFields(false);

		if (!validaCampos()) {
			return null;
		}

		this.prePersist();

		this.preUpdate();

		this.executeUpdate();

		this.posUpdate();

		this.posPersist();

		super.setDefaultMessage(true);

		super.setClearFields(!this.manterCampos);

		if (retornoSucesso) {
			return SUCESSO;
		}

		return null;

	}

	@Override
	protected String delete() throws TSApplicationException {

		this.preDelete();

		this.executeDelete();

		this.executeFind();

		this.posDelete();

		if (retornoSucesso) {
			return SUCESSO;
		}

		return null;

	}

	protected boolean validaCampos() {
		return true;
	}
	
	protected boolean validaCamposPesquisa() {
		return true;
	}

	public boolean isAlterar() {
		return alterar;
	}

	public void setAlterar(boolean alterar) {
		this.alterar = alterar;
	}

	public T getCrudModel() {
		return crudModel;
	}

	public void setCrudModel(T crudModel) {
		this.crudModel = crudModel;
	}

	public T getCrudPesquisaModel() {
		return crudPesquisaModel;
	}

	public void setCrudPesquisaModel(T crudPesquisaModel) {
		this.crudPesquisaModel = crudPesquisaModel;
	}

	public boolean isManterCampos() {
		return manterCampos;
	}

	public void setManterCampos(boolean manterCampos) {
		this.manterCampos = manterCampos;
	}

	public boolean isOcultarMsg() {
		return ocultarMsg;
	}

	public void setOcultarMsg(boolean ocultarMsg) {
		this.ocultarMsg = ocultarMsg;
	}

	public List<T> getGrid() {
		return grid;
	}

	public void setGrid(List<T> grid) {
		this.grid = grid;
	}

	public T getCrudSelectionModel() {
		return crudSelectionModel;
	}

	public void setCrudSelectionModel(T crudSelectionModel) {
		this.crudSelectionModel = crudSelectionModel;
	}

	public List<T> getGridFiltrado() {
		return gridFiltrado;
	}

	public void setGridFiltrado(List<T> gridFiltrado) {
		this.gridFiltrado = gridFiltrado;
	}

}
