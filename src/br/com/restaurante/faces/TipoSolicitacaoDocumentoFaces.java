package br.com.restaurante.faces;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import br.com.restaurante.business.ComboBS;
import br.com.restaurante.business.CrudBS;
import br.com.restaurante.business.FuncaoBS;
import br.com.restaurante.business.TipoSolicitacaoDocumentoBS;
import br.com.restaurante.model.FuncaoModel;
import br.com.restaurante.model.TipoQuizModel;
import br.com.restaurante.model.TipoSolicitacaoDocumentoFuncaoModel;
import br.com.restaurante.model.TipoSolicitacaoDocumentoModel;
import br.com.restaurante.util.Constantes;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

@ViewScoped
@SuppressWarnings("serial")
@ManagedBean(name = "tipoSolicitacaoDocumentoFaces")
public class TipoSolicitacaoDocumentoFaces extends CrudFaces<TipoSolicitacaoDocumentoModel> {

	@EJB
	private TipoSolicitacaoDocumentoBS tipoSolicitacaoDocumentoBS;

	@EJB
	private ComboBS comboBS;

	@EJB
	private FuncaoBS funcaoBS;

	private FuncaoModel funcaoModel;
	private TipoSolicitacaoDocumentoFuncaoModel tipoSolicitacaoDocumentoFuncaoModel;

	private List<SelectItem> comboFuncoes;
	private List<SelectItem> comboTipoQuiz;

	@Override
	@PostConstruct
	protected void clearFields() {

		this.crudModel = new TipoSolicitacaoDocumentoModel();
		this.crudModel.setTipoQuizModel(new TipoQuizModel());
		this.crudModel.setFlagAtivo(true);
		this.crudModel.setFuncoes(new ArrayList<TipoSolicitacaoDocumentoFuncaoModel>());

		this.crudPesquisaModel = new TipoSolicitacaoDocumentoModel();
		this.crudPesquisaModel.setFlagAtivo(true);
		
		this.funcaoModel = new FuncaoModel();

		this.comboTipoQuiz = super.initCombo(this.comboBS.pesquisarTiposQuizDocumento(), "id", "descricao");
		this.comboFuncoes = super.initCombo(this.comboBS.pesquisarFuncoes(), "id", "descricao");

	}

	public String addFuncao() {

		if (TSUtil.isEmpty(this.funcaoModel.getId())) {
			super.addErrorMessage("Selecione a função");
			return null;
		}

		TipoSolicitacaoDocumentoFuncaoModel model = new TipoSolicitacaoDocumentoFuncaoModel();

		model.setTipoSolicitacaoDocumentoModel(this.crudModel);
		model.setFuncaoModel(this.comboBS.obter(this.funcaoModel));

		if (this.crudModel.getFuncoes().contains(model)) {
			super.addErrorMessage("Função já adicionada anteriormente");
			return null;
		}

		this.crudModel.getFuncoes().add(model);

		return null;

	}

	public String delOrigem() {

		try {

			this.tipoSolicitacaoDocumentoBS.excluir(this.tipoSolicitacaoDocumentoFuncaoModel);

			this.crudModel.getFuncoes().remove(this.tipoSolicitacaoDocumentoFuncaoModel);

			super.addInfoMessageKey(Constantes.OPERACAO_SUCESSO);

		} catch (TSApplicationException e) {

			super.throwException(e);

		}

		return null;

	}
	
	@Override
	protected void tratarClone() {
		
		for(TipoSolicitacaoDocumentoFuncaoModel funcao : this.crudModel.getFuncoes()){
			
			funcao.setId(null);
			funcao.setTipoSolicitacaoDocumentoModel(this.crudModel);
			
		}
		
	}

	@Override
	protected CrudBS<TipoSolicitacaoDocumentoModel> getCrudBS() {
		return this.tipoSolicitacaoDocumentoBS;
	}

	public List<SelectItem> getComboTipoQuiz() {
		return comboTipoQuiz;
	}

	public void setComboTipoQuiz(List<SelectItem> comboTipoQuiz) {
		this.comboTipoQuiz = comboTipoQuiz;
	}

	public List<SelectItem> getComboFuncoes() {
		return comboFuncoes;
	}

	public void setComboFuncoes(List<SelectItem> comboFuncoes) {
		this.comboFuncoes = comboFuncoes;
	}

	public FuncaoModel getFuncaoModel() {
		return funcaoModel;
	}

	public void setFuncaoModel(FuncaoModel funcaoModel) {
		this.funcaoModel = funcaoModel;
	}

	public TipoSolicitacaoDocumentoFuncaoModel getTipoSolicitacaoDocumentoFuncaoModel() {
		return tipoSolicitacaoDocumentoFuncaoModel;
	}

	public void setTipoSolicitacaoDocumentoFuncaoModel(TipoSolicitacaoDocumentoFuncaoModel tipoSolicitacaoDocumentoFuncaoModel) {
		this.tipoSolicitacaoDocumentoFuncaoModel = tipoSolicitacaoDocumentoFuncaoModel;
	}

}
