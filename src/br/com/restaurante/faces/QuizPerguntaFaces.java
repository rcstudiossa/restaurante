package br.com.restaurante.faces;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import br.com.restaurante.business.ComboBS;
import br.com.restaurante.business.CrudBS;
import br.com.restaurante.business.MedidaBS;
import br.com.restaurante.business.QuizPerguntaBS;
import br.com.restaurante.business.UnidadeMedidaBS;
import br.com.restaurante.model.MedidaModel;
import br.com.restaurante.model.QuizPerguntaArquetipoModel;
import br.com.restaurante.model.QuizPerguntaModel;
import br.com.restaurante.model.QuizRespostaModel;
import br.com.restaurante.model.TipoRespostaModel;
import br.com.restaurante.util.Constantes;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

@SuppressWarnings("serial")
@ViewScoped
@ManagedBean(name = "quizPerguntaFaces")
public class QuizPerguntaFaces extends CrudFaces<QuizPerguntaModel> {

	@EJB
	private QuizPerguntaBS quizPerguntaBS;

	@EJB
	private ComboBS comboBS;

	@EJB
	private MedidaBS medidaBS;

	@EJB
	private UnidadeMedidaBS unidadeMedidaBS;

	private List<SelectItem> comboTipoResposta;
	private List<SelectItem> comboTipoMovimentacao;
	private List<SelectItem> comboQuizDependencia;
	private List<SelectItem> comboQuizGrupo;
	private List<SelectItem> comboTipoQuiz;
	private List<SelectItem> comboMedidas;
	private List<SelectItem> comboArquetipos;

	private QuizPerguntaModel quizPerguntaModel;
	private QuizPerguntaModel dependenciaModel;
	private QuizPerguntaArquetipoModel quizPerguntaArquetipoSelecionado;
	private QuizRespostaModel respostaQuizModel;
	private String resposta;

	@PostConstruct
	@Override
	protected void clearFields() {

		super.clearFields();

		this.crudModel = QuizPerguntaModel.getInstanceCrudModel();

		this.crudPesquisaModel = new QuizPerguntaModel();
		this.crudPesquisaModel.setFlagAtivo(Boolean.TRUE);
		this.crudPesquisaModel.setTipoRespostaModel(new TipoRespostaModel());

		this.quizPerguntaModel = new QuizPerguntaModel();
		this.dependenciaModel = new QuizPerguntaModel();

		this.comboTipoResposta = super.initCombo(this.comboBS.pesquisarTiposResposta(), "id", "descricao");
		this.comboMedidas = super.initCombo(this.medidaBS.pesquisarCrudModel(new MedidaModel()), "id", "descricao");
		this.comboArquetipos = super.initCombo(this.quizPerguntaBS.pesquisarCrudModel(new QuizPerguntaModel(new TipoRespostaModel(), Boolean.TRUE, Boolean.TRUE)), "id", "pergunta");

	}

	@Override
	protected boolean validaCampos() {

		if ((this.crudModel.isCombo() || this.crudModel.isMultiplo() || this.crudModel.isRadio() || this.crudModel.isConsequencia()) && TSUtil.isEmpty(this.crudModel.getRespostas())) {

			this.addErrorMessage("Para este tipo de pergunta é necessário adicionar algumas respostas");
			return false;

		}

		return true;

	}

	public String addArquetipo() {

		if (TSUtil.isEmpty(this.quizPerguntaModel.getId())) {
			super.addErrorMessage("Selecione o arquétipo");
			return null;
		}

		QuizPerguntaArquetipoModel arquetipoModel = new QuizPerguntaArquetipoModel();

		arquetipoModel.setQuizPerguntaModel(this.crudModel);

		arquetipoModel.setArquetipoModel(this.quizPerguntaBS.obterSimples(this.quizPerguntaModel));

		arquetipoModel.setOrdem(this.crudModel.getArquetipos().size());

		if (this.crudModel.getArquetipos().contains(arquetipoModel)) {
			super.addErrorMessage("Arquétipo já adicionado anterioriomente");
			return null;
		}

		this.crudModel.getArquetipos().add(arquetipoModel);

		return null;
	}

	public String removeArquetipo() {

		if (!TSUtil.isEmpty(this.quizPerguntaArquetipoSelecionado.getId())) {

			try {

				this.quizPerguntaBS.excluir(this.quizPerguntaArquetipoSelecionado);

				this.crudModel.getArquetipos().remove(this.quizPerguntaArquetipoSelecionado);

				super.addInfoMessage("Operação realizada com sucesso");

			} catch (TSApplicationException e) {

				super.throwException(e);

			}

		} else {

			this.crudModel.getArquetipos().remove(this.quizPerguntaArquetipoSelecionado);

		}

		return null;
	}

	public List<QuizPerguntaModel> buscaPergunta(String query) {
		return this.quizPerguntaBS.pesquisarAutocomplete(query);
	}

	@Override
	protected CrudBS<QuizPerguntaModel> getCrudBS() {
		return this.quizPerguntaBS;
	}

	public String addResposta() {
		this.crudModel.getRespostas().add(new QuizRespostaModel(this.resposta));
		this.resposta = null;
		return null;
	}

	public String removerResposta() {
		
		try {
			
			this.quizPerguntaBS.excluir(this.respostaQuizModel);
			
			this.crudModel.getRespostas().remove(this.respostaQuizModel);
			
			super.addInfoMessageKey(Constantes.OPERACAO_SUCESSO);
		
		} catch (TSApplicationException e) {
			
			super.throwException(e);
			
		}
		
		return null;
	}
	
	@Override
	protected void tratarClone() {
		
		for(QuizPerguntaArquetipoModel arquetipo : this.crudModel.getArquetipos()){
			
			arquetipo.setId(null);
			arquetipo.setQuizPerguntaModel(this.crudModel);
			
		}
		
	}

	public List<SelectItem> getComboTipoResposta() {
		return comboTipoResposta;
	}

	public void setComboTipoResposta(List<SelectItem> comboTipoResposta) {
		this.comboTipoResposta = comboTipoResposta;
	}

	public QuizRespostaModel getRespostaQuizModel() {
		return respostaQuizModel;
	}

	public void setRespostaQuizModel(QuizRespostaModel respostaQuizModel) {
		this.respostaQuizModel = respostaQuizModel;
	}

	public List<SelectItem> getComboTipoMovimentacao() {
		return comboTipoMovimentacao;
	}

	public void setComboTipoMovimentacao(List<SelectItem> comboTipoMovimentacao) {
		this.comboTipoMovimentacao = comboTipoMovimentacao;
	}

	public List<SelectItem> getComboQuizDependencia() {
		return comboQuizDependencia;
	}

	public void setComboQuizDependencia(List<SelectItem> comboQuizDependencia) {
		this.comboQuizDependencia = comboQuizDependencia;
	}

	public List<SelectItem> getComboQuizGrupo() {
		return comboQuizGrupo;
	}

	public void setComboQuizGrupo(List<SelectItem> comboQuizGrupo) {
		this.comboQuizGrupo = comboQuizGrupo;
	}

	public List<SelectItem> getComboTipoQuiz() {
		return comboTipoQuiz;
	}

	public void setComboTipoQuiz(List<SelectItem> comboTipoQuiz) {
		this.comboTipoQuiz = comboTipoQuiz;
	}

	public List<SelectItem> getComboArquetipos() {
		return comboArquetipos;
	}

	public void setComboArquetipos(List<SelectItem> comboArquetipos) {
		this.comboArquetipos = comboArquetipos;
	}

	public QuizPerguntaModel getQuizPerguntaModel() {
		return quizPerguntaModel;
	}

	public void setQuizPerguntaModel(QuizPerguntaModel quizPerguntaModel) {
		this.quizPerguntaModel = quizPerguntaModel;
	}

	public QuizPerguntaArquetipoModel getQuizPerguntaArquetipoSelecionado() {
		return quizPerguntaArquetipoSelecionado;
	}

	public void setQuizPerguntaArquetipoSelecionado(QuizPerguntaArquetipoModel quizPerguntaArquetipoSelecionado) {
		this.quizPerguntaArquetipoSelecionado = quizPerguntaArquetipoSelecionado;
	}

	public List<SelectItem> getComboMedidas() {
		return comboMedidas;
	}

	public void setComboMedidas(List<SelectItem> comboMedidas) {
		this.comboMedidas = comboMedidas;
	}

	public QuizPerguntaModel getDependenciaModel() {
		return dependenciaModel;
	}

	public void setDependenciaModel(QuizPerguntaModel dependenciaModel) {
		this.dependenciaModel = dependenciaModel;
	}

	public String getResposta() {
		return resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
	}

}
