package br.com.restaurante.util;

public final class Constantes {

	private Constantes() {

	}
	
	//public static final String PASTA_UPLOAD_ARQUIVO = "F:\\arquivos\\sgso\\";
	public static final String PASTA_UPLOAD_ARQUIVO = "/arquivos/sgso/";
	
	//public static final String PASTA_UPLOAD_RELATORIOS = "F:\\arquivos\\arquivos_sistema_basico\\relatorios\\";
	public static final String PASTA_UPLOAD_RELATORIOS = "/arquivos/arquivos_sistema_basico/relatorios/";
	
	public static final String PASTA_DOWNLOAD_ARQUIVO = "../../arquivos/sgso/";
	
	public static final String PASTA_FOTOS = "fotos/";
	public static final String PASTA_PRONTUARIOS = "prontuarios/";
	
	public static final String PASTA_RESULTADOS = "arquivos/resultados/";
	public static final String PASTA_RELATORIO = "relatorios";
		
	public static final String OPERACAO_SUCESSO = "OPERACAO_SUCESSO";
	
	public static final String MANAGED_BEAN_ATENDIMENTO = "atendimentoFaces";
	
	public static final String SESSION_ORIGEM_ATUAL = "origemAtual";
	public static final String SESSION_USUARIO_LOGADO = "usuarioLogado";
	public static final String SESSION_FUNCIONARIO_SESSAO = "funcionarioSessao";
	public static final String SESSION_ATENDIMENTO_SESSAO = "atendimentoSessao";
	public static final String SESSION_TAB_TEMPLATE = "tabTemplate";
	public static final String SESSION_TAB_ID = "tabId";
	public static final String SESSION_VALIDADOR = "tabelaValidador";
	public static final String SESSION_ATENDIMENTO_FILTRO = "atendimentoFiltro";
	
	public static final String MENU_FACES = "menuFaces";

	public static final Long TIPO_RESPOSTA_QUIZ_TEXTO_AREA = 1L;
	public static final Long TIPO_RESPOSTA_QUIZ_NUMERICO = 2L;
	public static final Long TIPO_RESPOSTA_QUIZ_DATA = 3L;
	public static final Long TIPO_RESPOSTA_QUIZ_COMBO = 4L;
	public static final Long TIPO_RESPOSTA_QUIZ_MULTIPLO = 5L;
	public static final Long TIPO_RESPOSTA_QUIZ_RADIO = 6L;
	public static final Long TIPO_RESPOSTA_QUIZ_TEXTO_INPUT = 7L;
	public static final Long TIPO_RESPOSTA_QUIZ_HORA = 8L;
	public static final Long TIPO_RESPOSTA_QUIZ_INPUTMASK = 9L;
	public static final Long TIPO_RESPOSTA_QUIZ_PONTO_FLUTUANTE = 10L;
	public static final Long TIPO_RESPOSTA_QUIZ_DATA_HORA = 15L;
	public static final Long TIPO_RESPOSTA_QUIZ_BOOLEANO = 16L;
	public static final Long TIPO_RESPOSTA_CONSEQUENCIA = 17L;
	public static final Long TIPO_RESPOSTA_INFORMATIVA = 18L;
	public static final Long TIPO_RESPOSTA_MULTIPLO_PANEL = 19L;
	public static final Long TIPO_RESPOSTA_LISTA_INPUT_TEXT = 21L;
	public static final Long TIPO_RESPOSTA_LISTA_INPUT_TEXTAREA = 22L;
	public static final Long TIPO_RESPOSTA_COMBO_SQL = 23L;
	public static final Long TIPO_RESPOSTA_VAZIA = 24L;
	public static final Long TIPO_RESPOSTA_KEY_FILTER = 25L;
	public static final Long TIPO_RESPOSTA_JQUERY_MASK = 28L;

	public static final Long TIPO_CONFIGURACAO_MAXIMO_DIAS_TROCA_SENHA = 1L;
	public static final Long TIPO_CONFIGURACAO_NUMERO_MAXIMO_TENTATIVAS_LOGIN = 2L;
	public static final Long TIPO_CONFIGURACAO_PERIODO_MAXIMO_INATIVIDADE_SESSAO = 3L;
	public static final Long TIPO_CONFIGURACAO_CODIGO_CIDADE_PADRAO = 12L;
	public static final Long TIPO_CONFIGURACAO_QTD_VALIDACAO_SENHAS_ANTERIORES = 33L;

	public static final String CRIPTOGRAFIA_USUARIO = "SHA-256";
	
	public static final String CODIGO_SPLIT = "!#.#!";
	
	public static final Long TAB_DOCUMENTO = 3L;
	
	public static final Long STATUS_ATENDIMENTO_AGENDADO = 1L;
	public static final Long STATUS_ATENDIMENTO_EM_ATENDIMENTO = 2L;
	public static final Long STATUS_ATENDIMENTO_PENDENTE = 3L;
	public static final Long STATUS_ATENDIMENTO_CANCELADO = 4L;
	public static final Long STATUS_ATENDIMENTO_CONCLUIDO = 5L;
	
	public static final Long MENU_FUNCIONARIO = 21L;
	public static final Long MENU_ATENDIMENTO = 22L;
	
	public static final String TIPO_MENSAGEM_ERRO = "Erro";
	public static final String TIPO_MENSAGEM_INFO = "Aviso";
	public static final String TIPO_MENSAGEM_ALERTA = "Alerta";
	public static final String TIPO_MENSAGEM_BLOQUEIO = "BLOQUEIO";
	
	public static final String CRON_JOB = "job";
	public static final String CRON_TRIGGER = "trigger";
	public static final String CRON_GROUP = "sistema";
	
	public static final Long TIPO_CRON_MARCACAO_EXAME = 1L;
	public static final Long TIPO_CRON_SQL = 2L;
	
	public static final Long TIPO_QUIZ_FICHA_EPIDEMIOLOGICA = 5L;
	
	public static final Long FUNCAO_MEDICO = 5L;
	
	public static final Integer PERMISSAO_IMPORTAR_FUNCIONARIO = 1;
	
}
