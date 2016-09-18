package br.com.restaurante.faces;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.Collator;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.event.CaptureEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.restaurante.business.AtendimentoBS;
import br.com.restaurante.business.ComboBS;
import br.com.restaurante.business.CrudBS;
import br.com.restaurante.business.FuncionarioBS;
import br.com.restaurante.business.PermissaoFuncaoOrigemBS;
import br.com.restaurante.model.AtendimentoModel;
import br.com.restaurante.model.AtividadeModel;
import br.com.restaurante.model.BairroModel;
import br.com.restaurante.model.CargoModel;
import br.com.restaurante.model.CidModel;
import br.com.restaurante.model.CidadeModel;
import br.com.restaurante.model.EnderecoModel;
import br.com.restaurante.model.EstadoModel;
import br.com.restaurante.model.FuncaoModel;
import br.com.restaurante.model.FuncionarioModel;
import br.com.restaurante.model.OrigemModel;
import br.com.restaurante.model.PerfilModel;
import br.com.restaurante.model.ProcedimentoModel;
import br.com.restaurante.model.QuizModel;
import br.com.restaurante.model.QuizPerguntaModel;
import br.com.restaurante.model.QuizQuestionarioModel;
import br.com.restaurante.model.QuizQuestionarioRespostaModel;
import br.com.restaurante.model.SolicitacaoExameItemModel;
import br.com.restaurante.model.SolicitacaoExameItemResultadoModel;
import br.com.restaurante.model.SolicitacaoExameModel;
import br.com.restaurante.model.StatusAtendimentoModel;
import br.com.restaurante.model.TipoQuizModel;
import br.com.restaurante.model.UsuarioModel;
import br.com.restaurante.util.ClienteViaCepWS;
import br.com.restaurante.util.Constantes;
import br.com.restaurante.util.Utilitario;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSDateUtil;
import br.com.topsys.util.TSParseUtil;
import br.com.topsys.util.TSUtil;

@ViewScoped
@SuppressWarnings("serial")
@ManagedBean(name = "funcionarioFaces")
public class FuncionarioFaces extends CrudFaces<FuncionarioModel> {

	@EJB
	private FuncionarioBS funcionarioBS;

	@EJB
	private AtendimentoBS atendimentoBS;

	@EJB
	private ComboBS comboBS;
	
	@EJB
	private PermissaoFuncaoOrigemBS permissaoFuncaoOrigemBS;

	private List<SelectItem> comboEstado;
	private List<SelectItem> comboCidade;
	private List<SelectItem> comboCargo;
	private List<SelectItem> comboSexo;
	private List<SelectItem> comboBairro;
	private List<SelectItem> comboAtividade;
	private List<SelectItem> comboOrigem;

	private List<String> listaNao;
	private int contColuna;
	private boolean permissaoImportarFuncionario;
	private boolean flagExibirCam;
	private UploadedFile arquivoImportacao;

	@Override
	@PostConstruct
	protected void clearFields() {

		this.crudModel = new FuncionarioModel();
		this.crudModel.setCargoModel(new CargoModel());
		this.crudModel.setFlagAtivo(true);
		this.crudModel.setCidadeModel(new CidadeModel());
		this.crudModel.getCidadeModel().setEstadoModel(new EstadoModel());
		this.crudModel.setBairroModel(new BairroModel());
		this.crudModel.setAtividadeModel(new AtividadeModel());
		this.crudModel.setOrigemModel(new OrigemModel());
		this.crudModel.getOrigemModel().setId(Utilitario.getOrigemAtual().getId());

		this.crudPesquisaModel = new FuncionarioModel();
		this.crudPesquisaModel.setFlagAtivo(Boolean.TRUE);
		this.crudPesquisaModel.setOrigemModel(new OrigemModel());
		this.crudPesquisaModel.getOrigemModel().setId(Utilitario.getOrigemAtual().getId());
		this.crudPesquisaModel.setAtividadeModel(new AtividadeModel());

		this.comboEstado = super.initCombo(this.comboBS.pesquisarEstados(), "id", "sigla");
		this.comboCargo = super.initCombo(this.comboBS.pesquisarCargos(), "id", "descricao");
		this.comboSexo = super.initCombo(this.comboBS.pesquisarSexo(), "id", "descricao");
		this.comboAtividade = super.initCombo(this.comboBS.pesquisarAtividades(), "id", "descricaoCompleta");
		this.comboOrigem = super.initCombo(this.comboBS.pesquisarOrigens(), "id", "descricao");

		FuncionarioModel funcionarioSessao = Utilitario.getFuncionarioSessao();

		if (!TSUtil.isEmpty(funcionarioSessao)) {

			FuncionarioModel funcionarioPesquisaModel = new FuncionarioModel();

			funcionarioPesquisaModel.setId(funcionarioSessao.getId());

			this.crudModel = this.funcionarioBS.obterCrudModel(funcionarioPesquisaModel);

			this.posDetail();

			this.alterar = true;

		}

		super.setManterCampos(true);

		this.listaNao = new ArrayList<String>();
		this.listaNao.add("N");
		this.listaNao.add("NÃO");
		this.listaNao.add("NAO");
		
		this.permissaoImportarFuncionario = !TSUtil.isEmpty(this.permissaoFuncaoOrigemBS.obter(Constantes.PERMISSAO_IMPORTAR_FUNCIONARIO, Utilitario.getUsuarioLogado().getFuncaoLogada(), Utilitario.getOrigemAtual()));

	}

	@Override
	protected String detail() {
		super.detail();
		return SUCESSO;
	}

	@Override
	protected String insert() throws TSApplicationException {
		super.insert();
		return this.detail();
	}

	@Override
	protected String update() throws TSApplicationException {
		super.update();
		return SUCESSO;
	}

	@Override
	protected void posDetail() {
		this.instanciarComboCidade();
		this.instanciarComboBairro();
		Utilitario.atualizarFuncionarioSessao(this.crudModel);
		Utilitario.atualizarAtendimentoSessao(this.atendimentoBS.obterUltimoAtendimento(this.crudModel, Utilitario.getOrigemAtual()));
	}
	
	@Override
	protected void tratarClone() {
		Utilitario.removerFuncionarioSessao();
		Utilitario.removerAtendimentoSessao();
	}

	public void carregarComboCidades() {
		this.comboCidade = super.initCombo(this.comboBS.pesquisarCidades(this.crudModel.getCidadeModel().getEstadoModel()), "id", "descricaoCompleta");
	}

	public void instanciarComboCidade() {
		if (!TSUtil.isEmpty(this.crudModel.getCidadeModel().getEstadoModel().getId())) {
			this.comboCidade = super.initCombo(this.comboBS.pesquisarCidades(this.crudModel.getCidadeModel().getEstadoModel()), "id", "descricaoCompleta");
		}
	}

	public void instanciarComboBairro() {
		if (!TSUtil.isEmpty(this.crudModel.getCidadeModel().getId())) {
			this.comboBairro = super.initCombo(this.comboBS.pesquisarBairros(this.crudModel.getCidadeModel()), "id", "descricao");
		}
	}

	public boolean isNaSessao() {
		return !TSUtil.isEmpty(Utilitario.getFuncionarioSessao());
	}

	public String limpar() {

		if (this.isNaSessao()) {

			Utilitario.removerFuncionarioSessao();

			Utilitario.removerAtendimentoSessao();

		}

		return SUCESSO;

	}

	public void buscarCep() {

		if (!TSUtil.isEmpty(this.crudModel.getCep())) {

			EnderecoModel enderecoModel = ClienteViaCepWS.buscarCep(TSUtil.removerNaoDigitos(this.crudModel.getCep()));

			if (!TSUtil.isEmpty(enderecoModel)) {

				this.crudModel.setEndereco(enderecoModel.getLogradouro());
				this.crudModel.setComplemento(enderecoModel.getComplemento());

				BairroModel bairro = this.comboBS.obterBairro(enderecoModel.getBairro(), enderecoModel.getLocalidade());

				if (!TSUtil.isEmpty(bairro)) {

					this.crudModel.setBairroModel(bairro);
					this.crudModel.setCidadeModel(bairro.getCidadeModel());
					this.crudModel.setEstadoModel(bairro.getCidadeModel().getEstadoModel());

					this.instanciarComboCidade();
					this.instanciarComboBairro();

				}

				super.addInfoMessage("Cep encontrado");

			}

		}

	}

	public void importarArquivo(FileUploadEvent event) {
		this.arquivoImportacao = event.getFile();
	}
	
	public void importarFuncionarios() {

		if (!TSUtil.isEmpty(this.arquivoImportacao)) {

			String linha = "";
			BufferedReader br = null;
			FuncionarioModel funcionarioAtual;
			boolean sucesso = true;

			List<FuncionarioModel> funcionariosImportados = new ArrayList<FuncionarioModel>();

			List<CargoModel> cargosCadastrados = this.comboBS.pesquisarCargos();
			List<AtividadeModel> atividadesCadastradas = this.comboBS.pesquisarAtividades();
			List<OrigemModel> origensCadastradas = this.comboBS.pesquisarOrigens();

			try {

				br = new BufferedReader(new InputStreamReader(this.arquivoImportacao.getInputstream(), "ISO-8859-1"));

				int contLinha = 1;

				while ((linha = br.readLine()) != null) {

					try {

						this.contColuna = 0;

						String[] coluna = linha.split(";", -1);

						funcionarioAtual = this.instanciarFuncionarioImportacao(coluna, contLinha, cargosCadastrados, atividadesCadastradas, origensCadastradas);

						if (funcionarioAtual == null) {
							return;
						}

						AtendimentoModel atendimentoModel = this.instanciarAtendimento(funcionarioAtual);

						funcionarioAtual.setAtendimentos(new ArrayList<AtendimentoModel>());
						funcionarioAtual.getAtendimentos().add(atendimentoModel);

						QuizQuestionarioModel quizQuestionarioModel = this.instanciarFichaEpidemiologia(coluna, atendimentoModel, funcionarioAtual);

						atendimentoModel.setQuestionarios(new ArrayList<QuizQuestionarioModel>());
						atendimentoModel.getQuestionarios().add(quizQuestionarioModel);

						List<SolicitacaoExameModel> solicitacoesExame = this.instanciarExames(coluna, atendimentoModel, funcionarioAtual);

						atendimentoModel.setSolicitacoesExame(solicitacoesExame);

						funcionariosImportados.add(funcionarioAtual);

					} catch (ArrayIndexOutOfBoundsException e) {

						sucesso = false;
						super.addErrorMessage("Linha nº " + contLinha + " não possui todas as colunas necessárias");
						break;

					}

					contLinha++;

				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
				super.addErrorMessage("Arquivo não encontrado");
				sucesso = false;
			} catch (IOException e) {
				e.printStackTrace();
				super.addErrorMessage("Ocorreu um erro ao manipular o arquivo");
				sucesso = false;
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			if (sucesso) {

				try {

					this.funcionarioBS.inserir(funcionariosImportados);

					super.addInfoMessageKey(Constantes.OPERACAO_SUCESSO);

				} catch (TSApplicationException e) {

					super.throwException(e);

				}

			}
		}

	}

	private FuncionarioModel instanciarFuncionarioImportacao(String[] coluna, int contLinha, List<CargoModel> cargosCadastrados, List<AtividadeModel> atividadesCadastradas, List<OrigemModel> origensCadastradas) {

		FuncionarioModel funcionarioAtual = new FuncionarioModel();
		
		CargoModel cargoModel = new CargoModel(coluna[contColuna].trim());

		if (!cargosCadastrados.contains(cargoModel)) {
			super.addErrorMessage("Cargo não encontrado. Código: " + coluna[contColuna]);
			return null;
		}

		funcionarioAtual.setCargoModel(cargosCadastrados.get(cargosCadastrados.indexOf(cargoModel)));

		if (TSUtil.isEmpty(coluna[++contColuna])) {
			super.addErrorMessage("Nome vazio na linha " + contLinha);
			return null;
		}

		funcionarioAtual.setNome(coluna[contColuna]);

		if (TSUtil.isEmpty(coluna[++contColuna])) {
			super.addErrorMessage("Matrícula vazia na linha " + contLinha);
			return null;
		}

		funcionarioAtual.setMatricula(coluna[contColuna]);

		if (TSUtil.isEmpty(coluna[++contColuna])) {
			super.addErrorMessage("Sexo vazio na linha " + contLinha);
			return null;
		}

		funcionarioAtual.setSexo(Utilitario.getSexoImportacao(coluna[contColuna]));

		if (TSUtil.isEmpty(coluna[++contColuna])) {
			super.addErrorMessage("Data de nascimento vazia na linha " + contLinha);
			return null;
		}

		funcionarioAtual.setDataNascimento(TSParseUtil.stringToDate(coluna[contColuna], TSDateUtil.DD_MM_YYYY));

		AtividadeModel atividadeModel = new AtividadeModel(coluna[++contColuna].trim());

		if (!atividadesCadastradas.contains(atividadeModel)) {
			super.addErrorMessage("Atividade não encontrada. Código: " + coluna[contColuna]);
			return null;
		}

		funcionarioAtual.setAtividadeModel(atividadesCadastradas.get(atividadesCadastradas.indexOf(atividadeModel)));

		funcionarioAtual.setEndereco(coluna[++contColuna]);

		OrigemModel origemModel = new OrigemModel(coluna[++contColuna].trim());

		if (!origensCadastradas.contains(origemModel)) {
			super.addErrorMessage("Origem não encontrada. Código: " + coluna[contColuna]);
			return null;
		}

		funcionarioAtual.setOrigemModel(origensCadastradas.get(origensCadastradas.indexOf(origemModel)));

		if(!TSUtil.isEmpty(coluna[++contColuna])){
			
			funcionarioAtual.setDataCadastro(TSParseUtil.stringToDate(coluna[contColuna], TSDateUtil.DD_MM_YYYY));
			
		} else {
			
			funcionarioAtual.setDataCadastro(new Date());
			
		}

		funcionarioAtual.setUsuarioCadastroModel(Utilitario.getUsuarioLogado());

		funcionarioAtual.setCidadeModel(new CidadeModel());
		funcionarioAtual.getCidadeModel().setEstadoModel(new EstadoModel());
		funcionarioAtual.setBairroModel(new BairroModel());
		funcionarioAtual.setFlagAtivo(true);

		return funcionarioAtual;

	}

	private AtendimentoModel instanciarAtendimento(FuncionarioModel funcionarioAtual) {

		AtendimentoModel atendimentoModel = new AtendimentoModel();

		atendimentoModel.setStatusAtendimentoModel(new StatusAtendimentoModel(Constantes.STATUS_ATENDIMENTO_CONCLUIDO));
		atendimentoModel.setUsuarioResponsavelModel(new UsuarioModel());
		atendimentoModel.setFuncionarioModel(funcionarioAtual);
		atendimentoModel.setOrigemModel(funcionarioAtual.getOrigemModel());
		atendimentoModel.setDataCadastro(funcionarioAtual.getDataCadastro());
		atendimentoModel.setData(funcionarioAtual.getDataCadastro());
		atendimentoModel.setUsuarioCadastroModel(funcionarioAtual.getUsuarioCadastroModel());

		return atendimentoModel;
	}
	
	public static String removeAcentos(String string) {
	    return Normalizer.normalize(string, Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
	}

	private QuizQuestionarioModel instanciarFichaEpidemiologia(String[] coluna, AtendimentoModel atendimentoModel, FuncionarioModel funcionarioAtual) {

		QuizQuestionarioModel quizQuestionarioModel = new QuizQuestionarioModel();

		quizQuestionarioModel.setAtendimentoModel(atendimentoModel);
		quizQuestionarioModel.setData(new Date());
		quizQuestionarioModel.setDataCadastro(funcionarioAtual.getDataCadastro());
		quizQuestionarioModel.setUsuarioCadastroModel(funcionarioAtual.getUsuarioCadastroModel());
		quizQuestionarioModel.setTipoQuizModel(new TipoQuizModel(Constantes.TIPO_QUIZ_FICHA_EPIDEMIOLOGICA));
		quizQuestionarioModel.setFuncaoModel(new FuncaoModel(Constantes.FUNCAO_MEDICO));
		quizQuestionarioModel.setFlagConcluido(true);

		quizQuestionarioModel.setRespostas(new ArrayList<QuizQuestionarioRespostaModel>());

		this.adicionarResposta(coluna, ++contColuna, 24L, quizQuestionarioModel, false); // Dependentes

		boolean flagSim = this.adicionarResposta(coluna, ++contColuna, 25L, quizQuestionarioModel, true); // Outra Atividade

		if (flagSim) {
			this.adicionarResposta(coluna, contColuna, 26L, quizQuestionarioModel, false); // Qual Atividade
		}
		Collator collator = Collator.getInstance (new Locale ("pt", "BR"));
		collator.setStrength(Collator.PRIMARY);

		flagSim = this.adicionarResposta(coluna, ++contColuna, 27L, quizQuestionarioModel, true); // Possui plano de saúde

		if (flagSim) {

			if (!TSUtil.isEmpty(coluna[contColuna]) && coluna[contColuna].equalsIgnoreCase("PLANSERV")) {
				this.adicionarResposta("Planserv", 28L, quizQuestionarioModel, null); // Possui plano de saúde
			} else {
				this.adicionarResposta("Outro", 28L, quizQuestionarioModel, coluna[contColuna]); // Possui plano de saúde
			}

		}

		if (!TSUtil.isEmpty(coluna[++contColuna])) {

			if (coluna[11].equalsIgnoreCase("SEM")) {
				this.adicionarResposta("Sim", 29L, quizQuestionarioModel, null); // Sem Queixas
			} else {
				this.adicionarResposta(coluna[contColuna], 30L, quizQuestionarioModel, null); // Especificar queixas
			}

		}

		if (!TSUtil.isEmpty(coluna[++contColuna])) {

			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("HIPERTENSÃO ARTERIAL"))) {
				this.adicionarResposta("Sim", 31L, quizQuestionarioModel, null);
			}

			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("CARDIOPATIA"))) {
				this.adicionarResposta("Sim", 32L, quizQuestionarioModel, null);
			}

			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("DIABETES"))) {
				this.adicionarResposta("Sim", 33L, quizQuestionarioModel, null);
			}

			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("DOENÇA MENTAL"))) {
				this.adicionarResposta("Sim", 34L, quizQuestionarioModel, null);
			}

			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("ALCOOLISMO"))) {
				this.adicionarResposta("Sim", 35L, quizQuestionarioModel, null);
			}

			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("CÂNCER"))) {
				this.adicionarResposta("Sim", 36L, quizQuestionarioModel, null);
				this.adicionarResposta(coluna[contColuna], 109L, quizQuestionarioModel, null);
			}

		}

		if (!TSUtil.isEmpty(coluna[++contColuna])) {

			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("BEBIDA ALCOÓLICA"))) {
				this.adicionarResposta("Sim", 37L, quizQuestionarioModel, null);
			}

			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("FUMANTE"))) {
				this.adicionarResposta("Sim", 38L, quizQuestionarioModel, null);
			}

			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("USO DE DROGAS"))) {
				this.adicionarResposta("Sim", 39L, quizQuestionarioModel, null);
			}

			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("PRÁTICA DE ESPORTES"))) {
				this.adicionarResposta("Sim", 40L, quizQuestionarioModel, null);
			}

		}

		if (!TSUtil.isEmpty(coluna[++contColuna])) {

			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("SARAMPO"))) {
				this.adicionarResposta("Sim", 41L, quizQuestionarioModel, null);
			}

			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("DOENÇA PULMONAR"))) {
				this.adicionarResposta("Sim", 42L, quizQuestionarioModel, null);
			}

			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("INFLAMAÇÃO DO OUVIDO"))) {
				this.adicionarResposta("Sim", 43L, quizQuestionarioModel, null);
			}

			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("RUBÉOLA"))) {
				this.adicionarResposta("Sim", 44L, quizQuestionarioModel, null);
			}

			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("DOENÇA CARDÍACA"))) {
				this.adicionarResposta("Sim", 45L, quizQuestionarioModel, null);
			}

			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("HIPERTENSÃO ARTERIAL"))) {
				this.adicionarResposta("Sim", 46L, quizQuestionarioModel, null);
			}

			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("PAPERA"))) {
				this.adicionarResposta("Sim", 47L, quizQuestionarioModel, null);
			}

			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("CONVULSÃO"))) {
				this.adicionarResposta("Sim", 48L, quizQuestionarioModel, null);
			}

			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("ACIDENTE DE TRABALHO"))) {
				this.adicionarResposta("Sim", 49L, quizQuestionarioModel, null);
			}

			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("HEPATITE"))) {
				this.adicionarResposta("Sim", 50L, quizQuestionarioModel, null);
			}

			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("DIABETES"))) {
				this.adicionarResposta("Sim", 51L, quizQuestionarioModel, null);
			}

			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("DOENÇA ÓSSEA / MUSCULAR"))) {
				this.adicionarResposta("Sim", 52L, quizQuestionarioModel, null);
			}

			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("MENINGITE"))) {
				this.adicionarResposta("Sim", 53L, quizQuestionarioModel, null);
			}

			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("ALERGIA A MEDICAMENTOS"))) {
				this.adicionarResposta("Sim", 54L, quizQuestionarioModel, null);
			}

			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("USO DE MEDICAMENTOS"))) {
				this.adicionarResposta("Sim", 55L, quizQuestionarioModel, null);
			}

			this.adicionarResposta(coluna[contColuna], 56L, quizQuestionarioModel, null);

		}

		if (!TSUtil.isEmpty(coluna[++contColuna])) {

			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("HEPATITE B"))) {
				this.adicionarResposta("Sim", 57L, quizQuestionarioModel, null);
			}

			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("TÉTANO"))) {
				this.adicionarResposta("Sim", 58L, quizQuestionarioModel, null);
			}

			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("RUBÉOLA"))) {
				this.adicionarResposta("Sim", 59L, quizQuestionarioModel, null);
			}

			this.adicionarResposta(coluna[contColuna], 60L, quizQuestionarioModel, null);

		}
		
		if (!TSUtil.isEmpty(coluna[++contColuna])) {
			this.adicionarResposta(coluna[contColuna], 61L, quizQuestionarioModel, null);
		}
		
		//Biometria e dados vitais
		if (!TSUtil.isEmpty(coluna[++contColuna])) {
			this.adicionarResposta(coluna[contColuna].replace(",", "."), 62L, quizQuestionarioModel, null);
		}
		
		if (!TSUtil.isEmpty(coluna[++contColuna])) {
			this.adicionarResposta(coluna[contColuna].replace(",", "."), 63L, quizQuestionarioModel, null);
		}
		
		if (!TSUtil.isEmpty(coluna[++contColuna])) {
			this.adicionarResposta(coluna[contColuna].replace(",", "."), 64L, quizQuestionarioModel, null);
		}
		
		if (!TSUtil.isEmpty(coluna[++contColuna])) {
			this.adicionarResposta(coluna[contColuna].replace(",", "."), 65L, quizQuestionarioModel, null);
		}
		
		if (!TSUtil.isEmpty(coluna[++contColuna])) {
			this.adicionarResposta(coluna[contColuna].replace("X", "/"), 66L, quizQuestionarioModel, null);
		}
		
		if (!TSUtil.isEmpty(coluna[++contColuna])) {
			this.adicionarResposta(coluna[contColuna].replace(",", "."), 67L, quizQuestionarioModel, null);
		}
		
		//Pele
		if (!TSUtil.isEmpty(coluna[++contColuna])) {
			
			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("ALERGIAS"))) {
				this.adicionarResposta("Sim", 68L, quizQuestionarioModel, null);
			}
			
			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("MANCHAS"))) {
				this.adicionarResposta("Sim", 69L, quizQuestionarioModel, null);
			}
			
			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("CICATRIZES"))) {
				this.adicionarResposta("Sim", 70L, quizQuestionarioModel, null);
			}
			
			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("MICOSE"))) {
				this.adicionarResposta("Sim", 71L, quizQuestionarioModel, null);
			}
			
		}
		
		//Cabeça de pescoço
		if (!TSUtil.isEmpty(coluna[++contColuna])) {
			
			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("ALT. OUVIDO"))) {
				this.adicionarResposta("Sim", 72L, quizQuestionarioModel, null);
			}
			
			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("ALT. OLHOS"))) {
				this.adicionarResposta("Sim", 73L, quizQuestionarioModel, null);
			}
			
			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("ALT. GARGANTA"))) {
				this.adicionarResposta("Sim", 74L, quizQuestionarioModel, null);
			}
			
			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("ALT. NARIZ"))) {
				this.adicionarResposta("Sim", 75L, quizQuestionarioModel, null);
			}
			
			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("ESTASE JUGULAR"))) {
				this.adicionarResposta("Sim", 76L, quizQuestionarioModel, null);
			}
			
			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("DOR DE CABEÇA"))) {
				this.adicionarResposta("Sim", 77L, quizQuestionarioModel, null);
			}
			
		}
		
		//Coluna e extremidade
		if (!TSUtil.isEmpty(coluna[++contColuna])) {
			
			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("EDEMA"))) {
				this.adicionarResposta("Sim", 78L, quizQuestionarioModel, null);
			}
			
			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("DEDOS"))) {
				this.adicionarResposta("C/ alt.", 79L, quizQuestionarioModel, null);
			}
			
			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("ALT. JOELHO"))) {
				this.adicionarResposta("Sim", 80L, quizQuestionarioModel, null);
			}
			
			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("VARIZES"))) {
				this.adicionarResposta("Sim", 81L, quizQuestionarioModel, null);
			}
			
			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("PÊLOS"))) {
				this.adicionarResposta("C/ alt.", 82L, quizQuestionarioModel, null);
			}
			
			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("ALT. TORNOZELO"))) {
				this.adicionarResposta("Sim", 83L, quizQuestionarioModel, null);
			}
			
			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("LOMBALGIA"))) {
				this.adicionarResposta("Sim", 84L, quizQuestionarioModel, null);
			}
			
			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("MICOSES"))) {
				this.adicionarResposta("Sim", 85L, quizQuestionarioModel, null);
			}
			
			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("ALT. COTOVELO"))) {
				this.adicionarResposta("Sim", 86L, quizQuestionarioModel, null);
			}
			
			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("CERVICALGIA"))) {
				this.adicionarResposta("Sim", 87L, quizQuestionarioModel, null);
			}
			
			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("OMBROS"))) {
				this.adicionarResposta("Sim", 88L, quizQuestionarioModel, null);
			}
			
			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("ALT. PUNHOS"))) {
				this.adicionarResposta("Sim", 89L, quizQuestionarioModel, null);
			}
			
			this.adicionarResposta(coluna[contColuna], 90L, quizQuestionarioModel, null);
			
		}
		
		//Aparelho genito urinário
		if (!TSUtil.isEmpty(coluna[++contColuna])) {
			
			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("ARDOR MICCIONAL"))) {
				this.adicionarResposta("Sim", 91L, quizQuestionarioModel, null);
			}
			
			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("PREVENTIVO"))) {
				this.adicionarResposta("Sim", 92L, quizQuestionarioModel, null);
			}
			
			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("CICLO MENSTRUAL"))) {
				this.adicionarResposta("C/ alt.", 93L, quizQuestionarioModel, null);
			}
			
			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("HISTÓRICO DE DST"))) {
				this.adicionarResposta("Sim", 94L, quizQuestionarioModel, null);
			}
			
			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("GRAVIDEZ"))) {
				this.adicionarResposta("Sim", 95L, quizQuestionarioModel, null);
			}
			
			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("ALT. DAS MAMAS"))) {
				this.adicionarResposta("Sim", 96L, quizQuestionarioModel, null);
			}
			
			this.adicionarResposta(coluna[contColuna], 97L, quizQuestionarioModel, null);
			
		}
		
		//Odontologia
		if (!TSUtil.isEmpty(coluna[++contColuna])) {
			
			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("DENTES CONSERVADOS"))) {
				this.adicionarResposta("Sim", 98L, quizQuestionarioModel, null);
			}
			
			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("EXTRAÇÕES"))) {
				this.adicionarResposta("Sim", 99L, quizQuestionarioModel, null);
			}
			
			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("GENGIVITES"))) {
				this.adicionarResposta("Sim", 100L, quizQuestionarioModel, null);
			}
			
			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("CÁRIES"))) {
				this.adicionarResposta("Sim", 101L, quizQuestionarioModel, null);
			}
			
			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("LESÕES BUCAIS"))) {
				this.adicionarResposta("Sim", 102L, quizQuestionarioModel, null);
			}
			
			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("PRÓTESES"))) {
				this.adicionarResposta("Sim", 103L, quizQuestionarioModel, null);
			}
			
			this.adicionarResposta(coluna[contColuna], 104L, quizQuestionarioModel, null);
			
		}
		
		//Sistema nervoso central
		if (!TSUtil.isEmpty(coluna[++contColuna])) {
			
			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("TONTURA"))) {
				this.adicionarResposta("Sim", 105L, quizQuestionarioModel, null);
			}
			
			if (removeAcentos(coluna[contColuna]).toUpperCase().contains(removeAcentos("TREMORES"))) {
				this.adicionarResposta("Sim", 106L, quizQuestionarioModel, null);
			}
			
			this.adicionarResposta(coluna[contColuna], 107L, quizQuestionarioModel, null);
			
		}

		//Outras Informações
		if (!TSUtil.isEmpty(coluna[++contColuna])) {
			this.adicionarResposta(coluna[contColuna], 108L, quizQuestionarioModel, null);
		}

		return quizQuestionarioModel;
	}

	private boolean adicionarResposta(String[] coluna, int posicao, Long quizId, QuizQuestionarioModel quizQuestionarioModel, boolean flag) {

		boolean retorno = false;

		if (!TSUtil.isEmpty(coluna[posicao])) {

			if (flag) {

				if (this.listaNao.contains(coluna[posicao].toUpperCase())) {

					this.adicionarResposta("Não", quizId, quizQuestionarioModel, null);

				} else {

					this.adicionarResposta("Sim", quizId, quizQuestionarioModel, null);
					retorno = true;

				}

			} else {

				this.adicionarResposta(coluna[posicao], quizId, quizQuestionarioModel, null);

			}

		}

		return retorno;

	}

	private void adicionarResposta(String valor, Long quizId, QuizQuestionarioModel quizQuestionarioModel, String outros) {

		QuizQuestionarioRespostaModel resposta = new QuizQuestionarioRespostaModel();
		resposta.setQuizTemplate(quizQuestionarioModel);
		resposta.setQuizModel(new QuizModel(quizId));
		resposta.setResposta(valor);
		resposta.setOutros(outros);

		quizQuestionarioModel.getRespostas().add(resposta);

	}

	private SolicitacaoExameModel instanciarSolicitacaoExame(String[] coluna, AtendimentoModel atendimentoModel, FuncionarioModel funcionarioAtual) {
		
		SolicitacaoExameModel solicitacaoExameModel = new SolicitacaoExameModel();

		solicitacaoExameModel.setAtendimentoModel(atendimentoModel);
		solicitacaoExameModel.setFuncionarioModel(funcionarioAtual);
		solicitacaoExameModel.setCidModel(new CidModel());
		solicitacaoExameModel.setOrigemModel(funcionarioAtual.getOrigemModel());
		solicitacaoExameModel.setSolicitanteModel(funcionarioAtual.getUsuarioCadastroModel());
		solicitacaoExameModel.setDataAgendamento(funcionarioAtual.getDataCadastro());
		solicitacaoExameModel.setDataCadastro(funcionarioAtual.getDataCadastro());
		solicitacaoExameModel.setUsuarioCadastroModel(funcionarioAtual.getUsuarioCadastroModel());
		solicitacaoExameModel.setFlagConcluido(true);
		solicitacaoExameModel.setExames(new ArrayList<SolicitacaoExameItemModel>());
		
		return solicitacaoExameModel;
		
	}
	
	private List<SolicitacaoExameModel> instanciarExames(String[] coluna, AtendimentoModel atendimentoModel, FuncionarioModel funcionarioAtual) {

		List<SolicitacaoExameModel> solicitacoesExame = new ArrayList<SolicitacaoExameModel>();
		
		SolicitacaoExameModel solicitacaoLaboratorio = this.instanciarSolicitacaoExame(coluna, atendimentoModel, funcionarioAtual);

		SolicitacaoExameItemModel solicitacaoExameItemModel = this.instanciarSolicitacaoExameItem(coluna, solicitacaoLaboratorio, 2L, 1L);
		
		contColuna++;
		
		if(!TSUtil.isEmpty(coluna[contColuna]) && !coluna[contColuna].equals("-")){
			solicitacaoExameItemModel.getResultados().add(this.instanciarSolicitacaoExameItemResultado(coluna, solicitacaoExameItemModel, 111L, true)); //Hb
		}
		
		contColuna++;
		
		if(!TSUtil.isEmpty(coluna[contColuna]) && !coluna[contColuna].equals("-")){
			solicitacaoExameItemModel.getResultados().add(this.instanciarSolicitacaoExameItemResultado(coluna, solicitacaoExameItemModel, 112L, true)); //Ht
		}
		
		contColuna++;
		
		if(!TSUtil.isEmpty(coluna[contColuna]) && !coluna[contColuna].equals("-")){
			solicitacaoExameItemModel.getResultados().add(this.instanciarSolicitacaoExameItemResultado(coluna, solicitacaoExameItemModel, 119L, true)); //Leuco
		}
		
		contColuna++;
		
		if(!TSUtil.isEmpty(coluna[contColuna]) && !coluna[contColuna].equals("-")){
			solicitacaoExameItemModel.getResultados().add(this.instanciarSolicitacaoExameItemResultado(coluna, solicitacaoExameItemModel, 133L, true)); //Plaquetas
		}
		
		if(!TSUtil.isEmpty(solicitacaoExameItemModel.getResultados())){
			solicitacaoLaboratorio.getExames().add(solicitacaoExameItemModel);
		}
		
		solicitacaoExameItemModel = this.instanciarSolicitacaoExameItem(coluna, solicitacaoLaboratorio, 11L, 1L);
		
		contColuna++;
		
		if(!TSUtil.isEmpty(coluna[contColuna]) && !coluna[contColuna].equals("-")){
			solicitacaoExameItemModel.getResultados().add(this.instanciarSolicitacaoExameItemResultado(coluna, solicitacaoExameItemModel, 224L, true)); //Glicemia
			solicitacaoLaboratorio.getExames().add(solicitacaoExameItemModel);
		}
		
		solicitacaoExameItemModel = this.instanciarSolicitacaoExameItem(coluna, solicitacaoLaboratorio, 12L, 1L);
		
		contColuna++;
		
		if(!TSUtil.isEmpty(coluna[contColuna]) && !coluna[contColuna].equals("-")){
			solicitacaoExameItemModel.getResultados().add(this.instanciarSolicitacaoExameItemResultado(coluna, solicitacaoExameItemModel, 226L, true)); //Colesterol
		}
		
		contColuna++;
		
		if(!TSUtil.isEmpty(coluna[contColuna]) && !coluna[contColuna].equals("-")){
			solicitacaoExameItemModel.getResultados().add(this.instanciarSolicitacaoExameItemResultado(coluna, solicitacaoExameItemModel, 227L, true)); //HDL
		}
		
		contColuna++;
		
		if(!TSUtil.isEmpty(coluna[contColuna]) && !coluna[contColuna].equals("-")){
			solicitacaoExameItemModel.getResultados().add(this.instanciarSolicitacaoExameItemResultado(coluna, solicitacaoExameItemModel, 293L, true)); //LDL
		}
		
		contColuna++;
		
		if(!TSUtil.isEmpty(coluna[contColuna]) && !coluna[contColuna].equals("-")){
			solicitacaoExameItemModel.getResultados().add(this.instanciarSolicitacaoExameItemResultado(coluna, solicitacaoExameItemModel, 294L, true)); //VLDL
		}
		
		if(!TSUtil.isEmpty(solicitacaoExameItemModel.getResultados())){
			solicitacaoLaboratorio.getExames().add(solicitacaoExameItemModel);
		}
		
		solicitacaoExameItemModel = this.instanciarSolicitacaoExameItem(coluna, solicitacaoLaboratorio, 13L, 1L);
		
		contColuna++;
		
		if(!TSUtil.isEmpty(coluna[contColuna]) && !coluna[contColuna].equals("-")){
			solicitacaoExameItemModel.getResultados().add(this.instanciarSolicitacaoExameItemResultado(coluna, solicitacaoExameItemModel, 228L, true)); //Triglicérides
			solicitacaoLaboratorio.getExames().add(solicitacaoExameItemModel);
		}
		
		solicitacaoExameItemModel = this.instanciarSolicitacaoExameItem(coluna, solicitacaoLaboratorio, 19L, 1L);
		
		contColuna++;
		
		if(!TSUtil.isEmpty(coluna[contColuna]) && !coluna[contColuna].equals("-")){
			solicitacaoExameItemModel.getResultados().add(this.instanciarSolicitacaoExameItemResultado(coluna, solicitacaoExameItemModel, 264L, false)); //Sumário de urina
			solicitacaoLaboratorio.getExames().add(solicitacaoExameItemModel);
		}
		
		solicitacaoExameItemModel = this.instanciarSolicitacaoExameItem(coluna, solicitacaoLaboratorio, 20L, 1L);
		
		contColuna++;
		
		if(!TSUtil.isEmpty(coluna[contColuna]) && !coluna[contColuna].equals("-")){
			solicitacaoExameItemModel.getResultados().add(this.instanciarSolicitacaoExameItemResultado(coluna, solicitacaoExameItemModel, 265L, false)); //Parazitológico de fezes
			solicitacaoLaboratorio.getExames().add(solicitacaoExameItemModel);
		}
		
		contColuna++;
		
		if(!TSUtil.isEmpty(coluna[contColuna]) && !coluna[contColuna].equals("-")){
			solicitacaoExameItemModel = this.instanciarSolicitacaoExameItem(coluna, solicitacaoLaboratorio, 14L, 2L);
			solicitacaoExameItemModel.getResultados().add(this.instanciarSolicitacaoExameItemResultado(coluna, solicitacaoExameItemModel, 230L, true)); //Uréia
			solicitacaoLaboratorio.getExames().add(solicitacaoExameItemModel);
		}
		
		contColuna++;
		
		if(!TSUtil.isEmpty(coluna[contColuna]) && !coluna[contColuna].equals("-")){
			solicitacaoExameItemModel = this.instanciarSolicitacaoExameItem(coluna, solicitacaoLaboratorio, 15L, 2L);
			solicitacaoExameItemModel.getResultados().add(this.instanciarSolicitacaoExameItemResultado(coluna, solicitacaoExameItemModel, 232L, true)); //Creatinina
			solicitacaoLaboratorio.getExames().add(solicitacaoExameItemModel);
		}
		
		contColuna++;
		
		if(!TSUtil.isEmpty(coluna[contColuna]) && !coluna[contColuna].equals("-")){
			solicitacaoExameItemModel = this.instanciarSolicitacaoExameItem(coluna, solicitacaoLaboratorio, 16L, 2L);
			solicitacaoExameItemModel.getResultados().add(this.instanciarSolicitacaoExameItemResultado(coluna, solicitacaoExameItemModel, 235L, true)); //TGO
			solicitacaoLaboratorio.getExames().add(solicitacaoExameItemModel);
		}
		
		contColuna++;
		
		if(!TSUtil.isEmpty(coluna[contColuna]) && !coluna[contColuna].equals("-")){
			solicitacaoExameItemModel = this.instanciarSolicitacaoExameItem(coluna, solicitacaoLaboratorio, 17L, 2L);
			solicitacaoExameItemModel.getResultados().add(this.instanciarSolicitacaoExameItemResultado(coluna, solicitacaoExameItemModel, 236L, true)); //TGP
			solicitacaoLaboratorio.getExames().add(solicitacaoExameItemModel);
		}
		
		contColuna++;
		
		if(!TSUtil.isEmpty(coluna[contColuna]) && !coluna[contColuna].equals("-")){
			solicitacaoExameItemModel = this.instanciarSolicitacaoExameItem(coluna, solicitacaoLaboratorio, 18L, 2L);
			solicitacaoExameItemModel.getResultados().add(this.instanciarSolicitacaoExameItemResultado(coluna, solicitacaoExameItemModel, 238L, true)); //GGT
			solicitacaoLaboratorio.getExames().add(solicitacaoExameItemModel);
		}
		
		SolicitacaoExameModel solicitacaoExameModel = this.instanciarSolicitacaoExame(coluna, atendimentoModel, funcionarioAtual);
		
		contColuna++;
		
		if(!TSUtil.isEmpty(coluna[contColuna]) && !coluna[contColuna].equals("-")){
			solicitacaoExameItemModel = this.instanciarSolicitacaoExameItem(coluna, solicitacaoExameModel, 24L, 3L);
			solicitacaoExameItemModel.getResultados().add(this.instanciarSolicitacaoExameItemResultado(coluna, solicitacaoExameItemModel, 272L, false)); //Teste Ergométrico
			solicitacaoExameModel.getExames().add(solicitacaoExameItemModel);
		}
		
		contColuna++;
		
		if(!TSUtil.isEmpty(coluna[contColuna]) && !coluna[contColuna].equals("-")){
			solicitacaoExameItemModel = this.instanciarSolicitacaoExameItem(coluna, solicitacaoExameModel, 25L, 3L);
			solicitacaoExameItemModel.getResultados().add(this.instanciarSolicitacaoExameItemResultado(coluna, solicitacaoExameItemModel, 273L, false)); //ECO
			solicitacaoExameModel.getExames().add(solicitacaoExameItemModel);
		}
		
		contColuna++;
		
		if(!TSUtil.isEmpty(coluna[contColuna]) && !coluna[contColuna].equals("-")){
			solicitacaoExameItemModel = this.instanciarSolicitacaoExameItem(coluna, solicitacaoExameModel, 26L, 3L);
			solicitacaoExameItemModel.getResultados().add(this.instanciarSolicitacaoExameItemResultado(coluna, solicitacaoExameItemModel, 274L, false)); //Avaliação cardiológica
			solicitacaoExameModel.getExames().add(solicitacaoExameItemModel);
		}
		
		if(!TSUtil.isEmpty(solicitacaoExameModel.getExames())){
			solicitacoesExame.add(solicitacaoExameModel);
		}
		
		contColuna++;
		
		if(!TSUtil.isEmpty(coluna[contColuna]) && !coluna[contColuna].equals("-")){
			solicitacaoExameModel = this.instanciarSolicitacaoExame(coluna, atendimentoModel, funcionarioAtual);
			solicitacaoExameItemModel = this.instanciarSolicitacaoExameItem(coluna, solicitacaoExameModel, 42L, null);
			solicitacaoExameItemModel.getResultados().add(this.instanciarSolicitacaoExameItemResultado(coluna, solicitacaoExameItemModel, 291L, false)); //Rx coluna
			solicitacaoExameModel.getExames().add(solicitacaoExameItemModel);
			solicitacoesExame.add(solicitacaoExameModel);
		}
		
		contColuna++;
		
		if(!TSUtil.isEmpty(coluna[contColuna]) && !coluna[contColuna].equals("-")){
			solicitacaoExameModel = this.instanciarSolicitacaoExame(coluna, atendimentoModel, funcionarioAtual);
			solicitacaoExameItemModel = this.instanciarSolicitacaoExameItem(coluna, solicitacaoExameModel, 39L, null);
			solicitacaoExameItemModel.getResultados().add(this.instanciarSolicitacaoExameItemResultado(coluna, solicitacaoExameItemModel, 288L, false)); //Av Psicológica
			solicitacaoExameModel.getExames().add(solicitacaoExameItemModel);
			solicitacoesExame.add(solicitacaoExameModel);
		}
		
		contColuna++;
		
		if(!TSUtil.isEmpty(coluna[contColuna]) && !coluna[contColuna].equals("-")){
			solicitacaoExameModel = this.instanciarSolicitacaoExame(coluna, atendimentoModel, funcionarioAtual);
			solicitacaoExameItemModel = this.instanciarSolicitacaoExameItem(coluna, solicitacaoExameModel, 38L, null);
			solicitacaoExameItemModel.getResultados().add(this.instanciarSolicitacaoExameItemResultado(coluna, solicitacaoExameItemModel, 287L, false)); //Av Psicológica
			solicitacaoExameModel.getExames().add(solicitacaoExameItemModel);
			solicitacoesExame.add(solicitacaoExameModel);
		}
		
		contColuna++;
		
		if(!TSUtil.isEmpty(coluna[contColuna]) && !coluna[contColuna].equals("-")){
			solicitacaoExameModel = this.instanciarSolicitacaoExame(coluna, atendimentoModel, funcionarioAtual);
			solicitacaoExameItemModel = this.instanciarSolicitacaoExameItem(coluna, solicitacaoExameModel, 27L, null);
			solicitacaoExameItemModel.getResultados().add(this.instanciarSolicitacaoExameItemResultado(coluna, solicitacaoExameItemModel, 275L, false)); //EEG
			solicitacaoExameModel.getExames().add(solicitacaoExameItemModel);
			solicitacoesExame.add(solicitacaoExameModel);
		}
		
		contColuna++;
		
		if(!TSUtil.isEmpty(coluna[contColuna]) && !coluna[contColuna].equals("-")){
			solicitacaoExameModel = this.instanciarSolicitacaoExame(coluna, atendimentoModel, funcionarioAtual);
			solicitacaoExameItemModel = this.instanciarSolicitacaoExameItem(coluna, solicitacaoExameModel, 44L, null);
			solicitacaoExameItemModel.getResultados().add(this.instanciarSolicitacaoExameItemResultado(coluna, solicitacaoExameItemModel, 295L, false)); //Avaliação Oftalmológica
			solicitacaoExameModel.getExames().add(solicitacaoExameItemModel);
			solicitacoesExame.add(solicitacaoExameModel);
		}
		
		contColuna++;
		
		if(!TSUtil.isEmpty(coluna[contColuna]) && !coluna[contColuna].equals("-")){
			solicitacaoExameModel = this.instanciarSolicitacaoExame(coluna, atendimentoModel, funcionarioAtual);
			solicitacaoExameItemModel = this.instanciarSolicitacaoExameItem(coluna, solicitacaoExameModel, 10L, null);
			solicitacaoExameItemModel.getResultados().add(this.instanciarSolicitacaoExameItemResultado(coluna, solicitacaoExameItemModel, 139L, false)); //Rx toráx
			solicitacaoExameModel.getExames().add(solicitacaoExameItemModel);
			solicitacoesExame.add(solicitacaoExameModel);
		}
		
		contColuna++;
		
		if(!TSUtil.isEmpty(coluna[contColuna]) && !coluna[contColuna].equals("-")){
			solicitacaoExameModel = this.instanciarSolicitacaoExame(coluna, atendimentoModel, funcionarioAtual);
			solicitacaoExameItemModel = this.instanciarSolicitacaoExameItem(coluna, solicitacaoExameModel, 28L, null);
			solicitacaoExameItemModel.getResultados().add(this.instanciarSolicitacaoExameItemResultado(coluna, solicitacaoExameItemModel, 276L, false)); //Espirometria
			solicitacaoExameModel.getExames().add(solicitacaoExameItemModel);
			solicitacoesExame.add(solicitacaoExameModel);
		}
		
		solicitacaoExameModel = this.instanciarSolicitacaoExame(coluna, atendimentoModel, funcionarioAtual);
		
		contColuna++;
		
		if(!TSUtil.isEmpty(coluna[contColuna]) && !coluna[contColuna].equals("-")){
			solicitacaoExameItemModel = this.instanciarSolicitacaoExameItem(coluna, solicitacaoExameModel, 21L, 5L);
			solicitacaoExameItemModel.getResultados().add(this.instanciarSolicitacaoExameItemResultado(coluna, solicitacaoExameItemModel, 266L, false)); //AgHBS
			solicitacaoExameModel.getExames().add(solicitacaoExameItemModel);
		}
		
		contColuna++;
		
		if(!TSUtil.isEmpty(coluna[contColuna]) && !coluna[contColuna].equals("-")){
			solicitacaoExameItemModel = this.instanciarSolicitacaoExameItem(coluna, solicitacaoExameModel, 22L, 5L);
			solicitacaoExameItemModel.getResultados().add(this.instanciarSolicitacaoExameItemResultado(coluna, solicitacaoExameItemModel, 268L, false)); //Anti-HCV
			solicitacaoExameModel.getExames().add(solicitacaoExameItemModel);
		}
		
		contColuna++;
		
		if(!TSUtil.isEmpty(coluna[contColuna]) && !coluna[contColuna].equals("-")){
			solicitacaoExameItemModel = this.instanciarSolicitacaoExameItem(coluna, solicitacaoExameModel, 23L, 5L);
			solicitacaoExameItemModel.getResultados().add(this.instanciarSolicitacaoExameItemResultado(coluna, solicitacaoExameItemModel, 271L, false)); //HIV
			solicitacaoExameModel.getExames().add(solicitacaoExameItemModel);
		}

		if(!TSUtil.isEmpty(solicitacaoExameModel.getExames())){
			solicitacoesExame.add(solicitacaoExameModel);
		}
			
		contColuna++;
		
		if(!TSUtil.isEmpty(coluna[contColuna]) && !coluna[contColuna].equals("-")){
			solicitacaoExameModel = this.instanciarSolicitacaoExame(coluna, atendimentoModel, funcionarioAtual);
			solicitacaoExameItemModel = this.instanciarSolicitacaoExameItem(coluna, solicitacaoExameModel, 45L, null);
			solicitacaoExameItemModel.getResultados().add(this.instanciarSolicitacaoExameItemResultado(coluna, solicitacaoExameItemModel, 296L, false)); //Preventivo feminino
			solicitacaoExameModel.getExames().add(solicitacaoExameItemModel);
			solicitacoesExame.add(solicitacaoExameModel);
		}
		
		contColuna++;
		
		if(!TSUtil.isEmpty(coluna[contColuna]) && !coluna[contColuna].equals("-")){
			solicitacaoExameModel = this.instanciarSolicitacaoExame(coluna, atendimentoModel, funcionarioAtual);
			solicitacaoExameItemModel = this.instanciarSolicitacaoExameItem(coluna, solicitacaoExameModel, 32L, null);
			solicitacaoExameItemModel.getResultados().add(this.instanciarSolicitacaoExameItemResultado(coluna, solicitacaoExameItemModel, 283L, false)); //Mamografia
			solicitacaoExameModel.getExames().add(solicitacaoExameItemModel);
			solicitacoesExame.add(solicitacaoExameModel);
		}
		
		contColuna++;
		
		if(!TSUtil.isEmpty(coluna[contColuna]) && !coluna[contColuna].equals("-")){
			solicitacaoExameItemModel = this.instanciarSolicitacaoExameItem(coluna, solicitacaoLaboratorio, 31L, null);
			solicitacaoExameItemModel.getResultados().add(this.instanciarSolicitacaoExameItemResultado(coluna, solicitacaoExameItemModel, 280L, false)); //PSA
			solicitacaoLaboratorio.getExames().add(solicitacaoExameItemModel);
		}
		
		contColuna++;
		
		if(!TSUtil.isEmpty(coluna[contColuna]) && !coluna[contColuna].equals("-")){
			solicitacaoExameModel = this.instanciarSolicitacaoExame(coluna, atendimentoModel, funcionarioAtual);
			solicitacaoExameItemModel = this.instanciarSolicitacaoExameItem(coluna, solicitacaoExameModel, 43L, null);
			solicitacaoExameItemModel.getResultados().add(this.instanciarSolicitacaoExameItemResultado(coluna, solicitacaoExameItemModel, 292L, false)); //Colonoscopia
			solicitacaoExameModel.getExames().add(solicitacaoExameItemModel);
		}
		
		if(!TSUtil.isEmpty(solicitacaoLaboratorio.getExames())){
			solicitacoesExame.add(solicitacaoLaboratorio);
		}
		
		return solicitacoesExame;
	}
	
	private SolicitacaoExameItemModel instanciarSolicitacaoExameItem(String[] coluna, SolicitacaoExameModel solicitacaoExameModel, Long procedimentoId, Long perfilId) {
		
		SolicitacaoExameItemModel solicitacaoExameItemModel = new SolicitacaoExameItemModel();
		solicitacaoExameItemModel.setSolicitacaoExameModel(solicitacaoExameModel);
		solicitacaoExameItemModel.setProcedimentoModel(new ProcedimentoModel(procedimentoId));
		solicitacaoExameItemModel.setPerfilModel(new PerfilModel(perfilId));
		solicitacaoExameItemModel.setDataRealizacao(solicitacaoExameModel.getDataCadastro());
		solicitacaoExameItemModel.setDataCadastro(solicitacaoExameModel.getDataCadastro());
		solicitacaoExameItemModel.setUsuarioCadastroModel(solicitacaoExameModel.getUsuarioCadastroModel());
		
		solicitacaoExameItemModel.setResultados(new ArrayList<SolicitacaoExameItemResultadoModel>());
		
		return solicitacaoExameItemModel;
	}
	
	private SolicitacaoExameItemResultadoModel instanciarSolicitacaoExameItemResultado(String[] coluna, SolicitacaoExameItemModel solicitacaoExameItemModel, Long quizPerguntaId, boolean flagDecimal) {
		
		String resultado = coluna[contColuna];
		
		if(flagDecimal){
			
			resultado = resultado.replace(".", "");
			resultado = resultado.replace(",", ".");
			
			try {
				
				new Double(resultado);
				
			} catch (Exception e){
				
				resultado = null;
				
			}
			
		}
		
		SolicitacaoExameItemResultadoModel solicitacaoExameItemResultadoModel = new SolicitacaoExameItemResultadoModel();
		solicitacaoExameItemResultadoModel.setSolicitacaoExameItemModel(solicitacaoExameItemModel);
		solicitacaoExameItemResultadoModel.setQuizPerguntaModel(new QuizPerguntaModel(quizPerguntaId));
		solicitacaoExameItemResultadoModel.setResultado(resultado);
		solicitacaoExameItemResultadoModel.setDataCadastro(solicitacaoExameItemModel.getDataCadastro());
		solicitacaoExameItemResultadoModel.setUsuarioCadastroModel(solicitacaoExameItemModel.getUsuarioCadastroModel());
		
		return solicitacaoExameItemResultadoModel;
	}
	
	public void oncapture(CaptureEvent captureEvent) {
		Utilitario.salvarFotoFuncionario(this.crudModel, captureEvent);
	}

	@Override
	protected CrudBS<FuncionarioModel> getCrudBS() {
		return this.funcionarioBS;
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

	public List<SelectItem> getComboCargo() {
		return comboCargo;
	}

	public void setComboCargo(List<SelectItem> comboCargo) {
		this.comboCargo = comboCargo;
	}

	public List<SelectItem> getComboSexo() {
		return comboSexo;
	}

	public void setComboSexo(List<SelectItem> comboSexo) {
		this.comboSexo = comboSexo;
	}

	public List<SelectItem> getComboBairro() {
		return comboBairro;
	}

	public void setComboBairro(List<SelectItem> comboBairro) {
		this.comboBairro = comboBairro;
	}

	public List<SelectItem> getComboAtividade() {
		return comboAtividade;
	}

	public void setComboAtividade(List<SelectItem> comboAtividade) {
		this.comboAtividade = comboAtividade;
	}

	public List<SelectItem> getComboOrigem() {
		return comboOrigem;
	}

	public void setComboOrigem(List<SelectItem> comboOrigem) {
		this.comboOrigem = comboOrigem;
	}

	public UploadedFile getArquivoImportacao() {
		return arquivoImportacao;
	}

	public void setArquivoImportacao(UploadedFile arquivoImportacao) {
		this.arquivoImportacao = arquivoImportacao;
	}

	public boolean isPermissaoImportarFuncionario() {
		return permissaoImportarFuncionario;
	}

	public void setPermissaoImportarFuncionario(boolean permissaoImportarFuncionario) {
		this.permissaoImportarFuncionario = permissaoImportarFuncionario;
	}

	public boolean isFlagExibirCam() {
		return flagExibirCam;
	}

	public void setFlagExibirCam(boolean flagExibirCam) {
		this.flagExibirCam = flagExibirCam;
	}

}
