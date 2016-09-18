package br.com.restaurante.util;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.imageio.stream.FileImageOutputStream;

import org.primefaces.event.CaptureEvent;

import br.com.restaurante.model.AtendimentoModel;
import br.com.restaurante.model.CamposRespostaAb;
import br.com.restaurante.model.CargoModel;
import br.com.restaurante.model.FuncionarioModel;
import br.com.restaurante.model.OrigemModel;
import br.com.restaurante.model.StatusAtendimentoModel;
import br.com.restaurante.model.TemplateTabModel;
import br.com.restaurante.model.UsuarioModel;
import br.com.topsys.util.TSDateUtil;
import br.com.topsys.util.TSParseUtil;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.util.TSFacesUtil;

public final class Utilitario {

	private Utilitario() {

	}

	public static String getStringIlike(String campo, boolean percentDuplo) {
		return TSUtil.isEmpty(campo) ? null : percentDuplo ? "%" + campo.trim() + "%" : campo.trim() + "%";
	}

	public static Date getDataOperacaoDia(int qtdDias) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, qtdDias);
		return c.getTime();
	}

	public static Date getDataOperacaoDia(Date data, int qtdDias) {
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.add(Calendar.DAY_OF_MONTH, qtdDias);
		return c.getTime();
	}

	public static String validarSenha(String senha) {

		if (senha.length() < 8) {
			return "ERRO.SENHA.CURTA";
		}

		if (TSUtil.removerNaoDigitos(senha).length() < 1 || TSUtil.removerNaoDigitos(senha).length() == senha.length()) {
			return "ERRO.SENHA.MINIMO.NUMERO";
		}

		return null;

	}

	public static OrigemModel getOrigemAtual() {
		return (OrigemModel) TSFacesUtil.getObjectInSession(Constantes.SESSION_ORIGEM_ATUAL);
	}

	public static UsuarioModel getUsuarioLogado() {
		return (UsuarioModel) TSFacesUtil.getObjectInSession(Constantes.SESSION_USUARIO_LOGADO);
	}

	public static FuncionarioModel getFuncionarioSessao() {
		return (FuncionarioModel) TSFacesUtil.getObjectInSession(Constantes.SESSION_FUNCIONARIO_SESSAO);
	}

	public static AtendimentoModel getAtendimentoSessao() {
		return (AtendimentoModel) TSFacesUtil.getObjectInSession(Constantes.SESSION_ATENDIMENTO_SESSAO);
	}

	public static void setResposta(CamposRespostaAb campoResposta, String resposta, Long idResposta) {

		if (TSUtil.isEmpty(resposta)) {
			return;
		}

		if (Constantes.TIPO_RESPOSTA_QUIZ_DATA.equals(campoResposta.getQuizPerguntaRespondida().getTipoRespostaModel().getId())) {

			campoResposta.setDataEscolhida(TSParseUtil.stringToDate(resposta, TSDateUtil.DD_MM_YYYY));

		} else if (Constantes.TIPO_RESPOSTA_QUIZ_DATA_HORA.equals(campoResposta.getQuizPerguntaRespondida().getTipoRespostaModel().getId())) {

			campoResposta.setDataEscolhida(TSParseUtil.stringToDate(resposta, TSDateUtil.DD_MM_YYYY_HH_MM));

		} else if (Constantes.TIPO_RESPOSTA_QUIZ_HORA.equals(campoResposta.getQuizPerguntaRespondida().getTipoRespostaModel().getId())) {

			campoResposta.setDataEscolhida(TSParseUtil.stringToDate(resposta, TSDateUtil.HH_MM));

		} else if (Constantes.TIPO_RESPOSTA_QUIZ_NUMERICO.equals(campoResposta.getQuizPerguntaRespondida().getTipoRespostaModel().getId())) {

			campoResposta.setInteiroEscolhido(Long.valueOf(resposta));
			campoResposta.popularReferenciaNumerico();

		} else if (Constantes.TIPO_RESPOSTA_QUIZ_PONTO_FLUTUANTE.equals(campoResposta.getQuizPerguntaRespondida().getTipoRespostaModel().getId())) {

			campoResposta.setDoubleEscolhido(Double.valueOf(resposta));
			campoResposta.popularReferenciaPontoFlutuante();

		} else if (Constantes.TIPO_RESPOSTA_QUIZ_MULTIPLO.equals(campoResposta.getQuizPerguntaRespondida().getTipoRespostaModel().getId()) || Constantes.TIPO_RESPOSTA_MULTIPLO_PANEL.equals(campoResposta.getQuizPerguntaRespondida().getTipoRespostaModel().getId())) {

			String[] split = resposta.split(",");

			campoResposta.setRespostasEscolhidas(new ArrayList<String>());

			for (String item : split) {

				campoResposta.getRespostasEscolhidas().add(item.trim());

			}

		} else if (Constantes.TIPO_RESPOSTA_LISTA_INPUT_TEXT.equals(campoResposta.getQuizPerguntaRespondida().getTipoRespostaModel().getId()) || Constantes.TIPO_RESPOSTA_LISTA_INPUT_TEXTAREA.equals(campoResposta.getQuizPerguntaRespondida().getTipoRespostaModel().getId())) {

			if (campoResposta.getRespostasEscolhidas() == null) {

				campoResposta.setRespostasEscolhidas(new ArrayList<String>());

			}

			campoResposta.getRespostasEscolhidas().add(resposta);

		} else {

			campoResposta.setRespostaEscolhida(resposta);

		}

	}

	public static void atualizarFuncionarioSessao(FuncionarioModel funcionarioModel) {
		TSFacesUtil.removeObjectInSession(Constantes.SESSION_FUNCIONARIO_SESSAO);
		TSFacesUtil.addObjectInSession(Constantes.SESSION_FUNCIONARIO_SESSAO, funcionarioModel);
		addMapaVariaveisSessao(funcionarioModel);
	}

	public static void atualizarAtendimentoSessao(AtendimentoModel atendimentoModel) {
		TSFacesUtil.removeObjectInSession(Constantes.SESSION_ATENDIMENTO_SESSAO);
		TSFacesUtil.addObjectInSession(Constantes.SESSION_ATENDIMENTO_SESSAO, atendimentoModel);
	}

	public static String calcularIdade(Date dataNascimento) {

		Calendar cal = Calendar.getInstance();

		String idade;

		int d1 = cal.get(Calendar.DAY_OF_YEAR);
		int dm1 = cal.get(Calendar.DAY_OF_MONTH);
		int m1 = cal.get(Calendar.MONTH);
		int a1 = cal.get(Calendar.YEAR);

		cal.setTime(dataNascimento);

		int d2 = cal.get(Calendar.DAY_OF_YEAR);
		int dm2 = cal.get(Calendar.DAY_OF_MONTH);
		int m2 = cal.get(Calendar.MONTH);
		int a2 = cal.get(Calendar.YEAR);

		int anos = a1 - a2;
		int meses = m1 - m2;

		if (meses < 0) {
			meses = meses + 12;
		}

		if (meses == 0) {

			if (d1 < d2) {

				meses = 12;

			} else {

				meses = 0;

			}

		}

		if (d1 < d2) {

			anos--; // Ainda não completou aniversario esse ano.

		}

		if (dm1 < dm2) {

			meses--;

		}

		idade = anos + "A " + meses + "M";

		return idade;

	}

	public static Map<String, String> tratarErroExecucaoPLSQL(String erro) {

		Map<String, String> mensagens = new HashMap<String, String>();

		String msg = erro.replace("ERROR: ", "");
		msg = msg.replace("ERRO: ", "");

		String tipo = msg.split(Constantes.CODIGO_SPLIT)[0];
		msg = msg.split(Constantes.CODIGO_SPLIT)[1];

		if ("INFO".equalsIgnoreCase(tipo)) {

			mensagens.put(msg, Constantes.TIPO_MENSAGEM_INFO);

		} else if ("ERRO".equalsIgnoreCase(tipo)) {

			mensagens.put(msg, Constantes.TIPO_MENSAGEM_ERRO);

		} else if ("AVISO".equalsIgnoreCase(tipo)) {

			mensagens.put(msg, Constantes.TIPO_MENSAGEM_ALERTA);

		}

		return mensagens;

	}

	public static boolean isNumeric(String numero) {

		try {

			new BigDecimal(numero);

			return true;

		} catch (NumberFormatException ex) {

			return false;

		}

	}

	public static void addMapaVariaveisSessao(FuncionarioModel funcionarioModel) {

		HashMap<String, Object> mapa = new HashMap<String, Object>();

		if (funcionarioModel != null) {

			mapa.put("SEXO", funcionarioModel.getSexo());
			mapa.put("CARGO", funcionarioModel.getCargoModel().getId());
			mapa.put("IDADE", getIdade(funcionarioModel.getDataNascimento()));
			mapa.put("IDADE_DIAS", getIdadeEmDias(funcionarioModel.getDataNascimento()));

		}

		TSFacesUtil.removeObjectInSession(Constantes.SESSION_VALIDADOR);
		TSFacesUtil.addObjectInSession(Constantes.SESSION_VALIDADOR, mapa);

	}

	public static Long getIdade(Date nascimento) {
		Calendar dataNascimento = Calendar.getInstance();
		dataNascimento.setTime(nascimento);
		Calendar dataAtual = Calendar.getInstance();
		Integer diferencaMes = dataAtual.get(Calendar.MONTH) - dataNascimento.get(Calendar.MONTH);
		Integer diferencaDia = dataAtual.get(Calendar.DAY_OF_MONTH) - dataNascimento.get(Calendar.DAY_OF_MONTH);
		Integer idade = (dataAtual.get(Calendar.YEAR) - dataNascimento.get(Calendar.YEAR));
		if (diferencaMes < 0 || (diferencaMes == 0 && diferencaDia < 0)) {
			idade--;
		}
		return idade.longValue();
	}

	public static Long getIdadeEmDias(Date dataNascimento) {
		Long tempo = new Date().getTime() - dataNascimento.getTime();
		return tempo / (24 * 60 * 60 * 1000);
	}

	public static boolean tratarMensagensValidacaoQuiz(Map<String, String> mensagensValidacao, String concatInicial) {

		if (concatInicial == null) {
			concatInicial = "";
		}

		boolean flagErro = false;

		if (!TSUtil.isEmpty(mensagensValidacao)) {

			for (Entry<String, String> msg : mensagensValidacao.entrySet()) {

				if (Constantes.TIPO_MENSAGEM_ERRO.equalsIgnoreCase(msg.getValue())) {

					TSFacesUtil.addErrorMessage(concatInicial + msg.getKey());

					flagErro = true;

				} else if (Constantes.TIPO_MENSAGEM_INFO.equalsIgnoreCase(msg.getValue())) {

					TSFacesUtil.addInfoMessage(concatInicial + msg.getKey());

				} else if (Constantes.TIPO_MENSAGEM_ALERTA.equalsIgnoreCase(msg.getValue())) {

					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, concatInicial + msg.getKey()));

				}

			}

		}

		return !flagErro;

	}

	public static TemplateTabModel getTabTemplateSessao() {
		return (TemplateTabModel) TSFacesUtil.getObjectInSession(Constantes.SESSION_TAB_TEMPLATE);
	}

	public static void ordenarListaPraCima(List<String> lista, String item) {

		int posicao = lista.indexOf(item);

		if (posicao > 0) {

			lista.remove(posicao);
			lista.add(posicao - 1, item);

		}

	}

	public static void ordenarListaPraBaixo(List<String> lista, String item) {

		int posicao = lista.indexOf(item);

		if (posicao < lista.size() - 1) {

			lista.remove(posicao);
			lista.add(posicao + 1, item);

		}

	}

	public static String obterPastaRelatorio() {
		return TSFacesUtil.getServletContext().getRealPath("WEB-INF" + File.separator + "relatorios") + File.separator;
	}
	
	public static boolean isPeriodoInvalido(Date dataInicial, Date dataFinal) {
		return dataFinal.before(dataInicial);
	}
	
	public static void addWarnMessage(String warnMessage) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, warnMessage));
	}
	
	public static void removerFuncionarioSessao() {
		TSFacesUtil.removeObjectInSession(Constantes.SESSION_FUNCIONARIO_SESSAO);
		TSFacesUtil.removeObjectInSession(Constantes.SESSION_VALIDADOR);
	}
	
	public static void removerAtendimentoSessao() {
		TSFacesUtil.removeObjectInSession(Constantes.SESSION_ATENDIMENTO_SESSAO);
	}

	public static AtendimentoModel getAtendimentoFiltroSession() {

		AtendimentoModel atendimentoFiltroModel = new AtendimentoModel();

		atendimentoFiltroModel.setOrigemModel(Utilitario.getOrigemAtual());
		atendimentoFiltroModel.setFuncionarioModel(new FuncionarioModel());
		atendimentoFiltroModel.getFuncionarioModel().setCargoModel(new CargoModel());
		atendimentoFiltroModel.setStatusAtendimentoModel(new StatusAtendimentoModel());
		atendimentoFiltroModel.setUsuarioResponsavelModel(new UsuarioModel());

		return atendimentoFiltroModel;

	}
	
	public static Integer getSexoImportacao(String sexo){
		return String.valueOf(sexo).equals("MASCULINO") ? 1 : 2;
	}
	
	public static void salvarFotoFuncionario(FuncionarioModel crudModel, CaptureEvent captureEvent) {

		crudModel.setFoto(TSUtil.gerarId() + ".jpg");

		byte[] data = captureEvent.getData();

		FileImageOutputStream imageOutput = null;

		try {

			imageOutput = new FileImageOutputStream(new File(Constantes.PASTA_UPLOAD_ARQUIVO + Constantes.PASTA_FOTOS + crudModel.getFoto()));
			imageOutput.write(data, 0, data.length);

		} catch (IOException e) {

			TSFacesUtil.addErrorMessage("Ocorreu um erro ao enviar a imagem");
			e.printStackTrace();

		} finally {

			try {

				imageOutput.close();

			} catch (IOException e) {

				e.printStackTrace();
				TSFacesUtil.addErrorMessage("Ocorreu um erro ao fechar o arquivo de imagem");

			}
		}
	}

}
