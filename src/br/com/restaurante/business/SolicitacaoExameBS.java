package br.com.restaurante.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.restaurante.dao.CrudDAO;
import br.com.restaurante.dao.QuizPerguntaDAO;
import br.com.restaurante.dao.SolicitacaoExameDAO;
import br.com.restaurante.model.FuncionarioModel;
import br.com.restaurante.model.OrigemModel;
import br.com.restaurante.model.ProcedimentoModel;
import br.com.restaurante.model.QuizPerguntaModel;
import br.com.restaurante.model.QuizRespostaModel;
import br.com.restaurante.model.SolicitacaoExameItemModel;
import br.com.restaurante.model.SolicitacaoExameItemResultadoModel;
import br.com.restaurante.model.SolicitacaoExameModel;
import br.com.restaurante.model.UsuarioModel;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

@Stateless
@LocalBean
public class SolicitacaoExameBS extends CrudBS<SolicitacaoExameModel> {

	@EJB
	private QuizBS quizBS;

	@Inject
	private SolicitacaoExameDAO solicitacaoExameDAO;

	@Inject
	private QuizPerguntaDAO quizPerguntaDAO;

	@Override
	public SolicitacaoExameModel obterCrudModel(SolicitacaoExameModel crudModel) {

		SolicitacaoExameModel model = super.obterCrudModel(crudModel);

		if (!TSUtil.isEmpty(model)) {

			model.setFlagConcluido(crudModel.getFlagConcluido());

			model.setExames(this.solicitacaoExameDAO.pesquisarItens(model));

		}

		return model;

	}

	public SolicitacaoExameModel obterSimples(SolicitacaoExameModel crudModel) {
		return this.solicitacaoExameDAO.obterCrudModel(crudModel);
	}

	@Override
	public SolicitacaoExameModel inserirCrudModel(SolicitacaoExameModel crudModel) throws TSApplicationException {

		this.solicitacaoExameDAO.inserirCrudModel(crudModel);

		for (SolicitacaoExameItemModel item : crudModel.getExames()) {

			item.setDataCadastro(crudModel.getDataCadastro());
			item.setUsuarioCadastroModel(crudModel.getUsuarioCadastroModel());
			item.setDataAtualizacao(crudModel.getDataAtualizacao());
			item.setUsuarioAtualizacaoModel(crudModel.getUsuarioAtualizacaoModel());

			this.inserir(item);

		}

		return crudModel;
	}
	
	private void salvarResultado(SolicitacaoExameItemResultadoModel resposta) throws TSApplicationException{
		
		resposta.setResultado(resposta.getRespostaDada());

		if (TSUtil.isEmpty(resposta.getId())) {

			resposta.setDataCadastro(resposta.getDataAtualizacao());
			resposta.setUsuarioCadastroModel(resposta.getUsuarioAtualizacaoModel());
			
			this.solicitacaoExameDAO.inserir(resposta);

		} else {

			if (!String.valueOf(resposta.getResultado()).equals(String.valueOf(resposta.getResultadoAnterior())) || !String.valueOf(resposta.getObservacao()).equals(String.valueOf(resposta.getObservacaoAnterior())) || !String.valueOf(resposta.getPercentual()).equals(String.valueOf(resposta.getPercentualAnterior()))) {

				this.solicitacaoExameDAO.alterar(resposta);

			}

		}
		
		for (SolicitacaoExameItemResultadoModel respostaFilha : resposta.getFilhos()) {
			
			respostaFilha.setDataAtualizacao(resposta.getDataAtualizacao());
			respostaFilha.setUsuarioAtualizacaoModel(resposta.getUsuarioAtualizacaoModel());
			
			this.salvarResultado(respostaFilha);
			
		}
		
	}

	private void salvarFilhosProcedimentos(SolicitacaoExameItemModel model) throws TSApplicationException {

		if (!TSUtil.isEmpty(model.getResultados())) {

			for (SolicitacaoExameItemResultadoModel resposta : model.getResultados()) {

				resposta.setDataCadastro(model.getDataAtualizacao());
				resposta.setUsuarioCadastroModel(model.getUsuarioAtualizacaoModel());
				resposta.setDataAtualizacao(model.getDataAtualizacao());
				resposta.setUsuarioAtualizacaoModel(model.getUsuarioAtualizacaoModel());
				
				this.salvarResultado(resposta);

			}

		}

		if (!TSUtil.isEmpty(model.getProcedimentosFilhos())) {

			for (SolicitacaoExameItemModel solicitacao : model.getProcedimentosFilhos()) {

				solicitacao.setId(model.getId());
				solicitacao.setDataCadastro(model.getDataAtualizacao());
				solicitacao.setUsuarioCadastroModel(model.getUsuarioAtualizacaoModel());
				solicitacao.setDataAtualizacao(model.getDataAtualizacao());
				solicitacao.setUsuarioAtualizacaoModel(model.getUsuarioAtualizacaoModel());

				this.salvarFilhosProcedimentos(solicitacao);

			}

		}

	}

	public void inserir(SolicitacaoExameItemModel model) throws TSApplicationException {

		this.solicitacaoExameDAO.inserir(model);

		this.salvarFilhosProcedimentos(model);

	}
	
	public SolicitacaoExameModel concluir(SolicitacaoExameModel model) throws TSApplicationException {
		this.solicitacaoExameDAO.concluir(model);
		model.setFlagConcluido(true);
		return model;
	}

	@Override
	public SolicitacaoExameModel alterarCrudModel(SolicitacaoExameModel crudModel) throws TSApplicationException {

		super.alterarCrudModel(crudModel);

		for (SolicitacaoExameItemModel item : crudModel.getExames()) {

			item.setDataCadastro(crudModel.getDataCadastro());
			item.setUsuarioCadastroModel(crudModel.getUsuarioCadastroModel());
			item.setDataAtualizacao(crudModel.getDataAtualizacao());
			item.setUsuarioAtualizacaoModel(crudModel.getUsuarioAtualizacaoModel());

			if (TSUtil.isEmpty(item.getId())) {

				this.inserir(item);

			} else {

				this.solicitacaoExameDAO.alterar(item);

				this.salvarFilhosProcedimentos(item);

			}

		}

		return crudModel;
	}

	public List<SolicitacaoExameItemModel> pesquisarProcedimentosFilhos(final SolicitacaoExameItemModel model) {
		return this.solicitacaoExameDAO.pesquisarProcedimentosFilhos(model);
	}

	public List<SolicitacaoExameModel> pesquisarCrudModel(SolicitacaoExameModel crudModel) {

		List<SolicitacaoExameModel> exames = this.solicitacaoExameDAO.pesquisarCrudModel(crudModel);

		for (SolicitacaoExameModel exame : exames) {

			exame.setFlagConcluido(crudModel.getFlagConcluido());

			exame.setExames(this.solicitacaoExameDAO.pesquisarItens(exame));

		}

		return exames;
	}

	public List<SolicitacaoExameItemResultadoModel> pesquisarResultadosExamesPais(final SolicitacaoExameItemModel model) {

		List<SolicitacaoExameItemResultadoModel> exames = this.solicitacaoExameDAO.pesquisarResultadosExamesPais(model);

		if (TSUtil.isEmpty(exames)) {

			exames = this.solicitacaoExameDAO.pesquisarSubexamesPais(model.getProcedimentoModel());

		}

		for (SolicitacaoExameItemResultadoModel examePai : exames) {

			examePai.setSolicitacaoExameItemModel(model);
			examePai.setRespostaDada(examePai.getResultado());
			examePai.getQuizPerguntaModel().setRespostas(this.quizPerguntaDAO.pesquisarRespostas(examePai.getQuizPerguntaModel()));

			for (QuizRespostaModel resposta : examePai.getQuizPerguntaModel().getRespostas()) {

				if (TSUtil.isEmpty(examePai.getRespostaDada()) && resposta.getFlagDefault()) {
					examePai.setRespostaDada(resposta.getResposta());
					break;
				}

			}

		}

		return exames;
	}

	public List<SolicitacaoExameItemResultadoModel> pesquisarResultadosExamesFilhos(final SolicitacaoExameItemResultadoModel resultado) {

		List<SolicitacaoExameItemResultadoModel> exames = this.solicitacaoExameDAO.pesquisarResultadosExamesFilhos(resultado);

		if(TSUtil.isEmpty(exames)){
			exames = this.solicitacaoExameDAO.pesquisarResultadosExamesFilhosInicial(resultado);
		}
		
		for (SolicitacaoExameItemResultadoModel exameFilho : exames) {

			exameFilho.setSolicitacaoExameItemModel(resultado.getSolicitacaoExameItemModel());
			exameFilho.setRespostaDada(exameFilho.getResultado());
			exameFilho.getQuizPerguntaModel().setRespostas(this.quizPerguntaDAO.pesquisarRespostas(exameFilho.getQuizPerguntaModel()));

			for (QuizRespostaModel resposta : exameFilho.getQuizPerguntaModel().getRespostas()) {

				if (TSUtil.isEmpty(exameFilho.getRespostaDada()) && resposta.getFlagDefault()) {
					exameFilho.setRespostaDada(resposta.getResposta());
					break;
				}

			}

		}

		return exames;
	}

	public List<SolicitacaoExameItemResultadoModel> pesquisarResultadosExames(final QuizPerguntaModel exame, FuncionarioModel funcionario) {
		return this.solicitacaoExameDAO.pesquisarResultadosExames(exame, funcionario);
	}

	public void alterarResultadoExame(List<SolicitacaoExameItemResultadoModel> resultados, UsuarioModel usuarioAtualizacao) throws TSApplicationException {

		for (SolicitacaoExameItemResultadoModel resultado : resultados) {

			resultado.setResultado(resultado.getRespostaDada());

			if (!String.valueOf(resultado.getResultado()).equals(String.valueOf(resultado.getResultadoAnterior())) || !String.valueOf(resultado.getObservacao()).equals(String.valueOf(resultado.getObservacaoAnterior())) || !String.valueOf(resultado.getPercentual()).equals(String.valueOf(resultado.getPercentualAnterior()))) {

				resultado.setUsuarioAtualizacaoModel(usuarioAtualizacao);
				resultado.setDataAtualizacao(new Date());

				this.solicitacaoExameDAO.alterar(resultado);

			}

			this.alterarResultadoExame(resultado.getFilhos(), usuarioAtualizacao);

		}

	}

	public void alterarResultadoProcedimento(List<SolicitacaoExameItemModel> exames, UsuarioModel usuarioAtualizacao) throws TSApplicationException {

		for (SolicitacaoExameItemModel exame : exames) {

			if (!TSUtil.isEmpty(exame.getProcedimentosFilhos())) {

				this.alterarResultadoProcedimento(exame.getProcedimentosFilhos(), usuarioAtualizacao);

			}

			if (!TSUtil.isEmpty(exame.getResultados())) {

				this.alterarResultadoExame(exame.getResultados(), usuarioAtualizacao);

				exame.setDataAtualizacao(new Date());
				exame.setUsuarioAtualizacaoModel(usuarioAtualizacao);

			} else {

				exame.setDataAtualizacao(new Date());
				exame.setUsuarioAtualizacaoModel(usuarioAtualizacao);

				this.alterarLaudo(exame);

			}

		}

	}

	public List<SolicitacaoExameModel> pesquisar(final FuncionarioModel model, OrigemModel origemModel) {

		List<SolicitacaoExameModel> exames = this.solicitacaoExameDAO.pesquisar(model, origemModel);

		for (SolicitacaoExameModel exame : exames) {

			exame.setExames(this.solicitacaoExameDAO.pesquisarItens(exame));

		}

		return exames;
	}

	public void alterarLaudo(final SolicitacaoExameItemModel model) throws TSApplicationException {
		this.solicitacaoExameDAO.alterarLaudo(model);
	}

	public List<SolicitacaoExameItemModel> pesquisarUltimos(FuncionarioModel model, Long limit) {
		return this.solicitacaoExameDAO.pesquisarUltimos(model, limit);
	}

	public SolicitacaoExameItemModel obterItem(final SolicitacaoExameItemModel model) {
		return this.solicitacaoExameDAO.obterItem(model);
	}

	public SolicitacaoExameItemResultadoModel obterResultado(final FuncionarioModel pacienteModel, Long idExterno) {
		return this.solicitacaoExameDAO.obterResultado(pacienteModel, idExterno);
	}

	public void salvarArquivoResultado(SolicitacaoExameItemModel exame) throws TSApplicationException {
		this.solicitacaoExameDAO.salvarArquivoResultado(exame);
	}

	public void salvarArquivoResultadoLista(List<SolicitacaoExameItemModel> exames) throws TSApplicationException {

		for (SolicitacaoExameItemModel exame : exames) {

			this.solicitacaoExameDAO.salvarArquivoResultado(exame);

		}

	}

	@Override
	protected CrudDAO<SolicitacaoExameModel> getCrudDAO() {
		return this.solicitacaoExameDAO;
	}

	public List<SolicitacaoExameItemResultadoModel> carregarResultados2(SolicitacaoExameItemModel model) {

		List<SolicitacaoExameItemResultadoModel> resultados = new ArrayList<SolicitacaoExameItemResultadoModel>();

		List<ProcedimentoModel> procedimentosFilhos = this.solicitacaoExameDAO.pesquisarProcedimentos(model);

		for (ProcedimentoModel procedimentoModel : procedimentosFilhos) {

			if (procedimentoModel.getFlagPossuiSubexames()) {

				resultados.addAll(this.solicitacaoExameDAO.pesquisarSubexamesPais(procedimentoModel));

			}

		}

		return resultados;
	}

}
