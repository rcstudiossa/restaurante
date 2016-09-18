package br.com.restaurante.dao;

import java.util.List;

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
import br.com.restaurante.util.Constantes;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;

public class ComboDAO {

	@SuppressWarnings("unchecked")
	public List<ComboModel> pesquisarSexo() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO FROM SEXO");

		return broker.getCollectionBean(ComboModel.class, "id", "descricao");

	}

	@SuppressWarnings("unchecked")
	public List<CidadeModel> pesquisarCidades(final EstadoModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO, DESCRICAO FROM CIDADE WHERE ESTADO_ID = ? ORDER BY COALESCE(FLAG_PRINCIPAL, FALSE) DESC, DESCRICAO", model.getId());

		return broker.getCollectionBean(CidadeModel.class, "id", "descricao", "descricaoCompleta");

	}

	@SuppressWarnings("unchecked")
	public List<BairroModel> pesquisarBairros(final CidadeModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO FROM BAIRRO WHERE CIDADE_ID = ? ORDER BY DESCRICAO", model.getId());

		return broker.getCollectionBean(BairroModel.class, "id", "descricao");

	}

	@SuppressWarnings("unchecked")
	public List<EstadoModel> pesquisarEstados() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID,SIGLA FROM ESTADO ORDER BY SIGLA");

		return broker.getCollectionBean(EstadoModel.class, "id", "sigla");

	}

	@SuppressWarnings("unchecked")
	public List<FuncaoModel> pesquisarFuncoes(final UsuarioModel usuario, OrigemModel origem) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO FROM FUNCAO F WHERE FLAG_ATIVO = TRUE AND EXISTS(SELECT 1 FROM USUARIO_FUNCAO UF WHERE UF.FLAG_ATIVO AND UF.USUARIO_ID = ? AND UF.ORIGEM_ID = ? AND UF.FUNCAO_ID = F.ID) ORDER BY F.DESCRICAO", usuario.getId(), origem.getId());

		return broker.getCollectionBean(FuncaoModel.class, "id", "descricao");

	}

	@SuppressWarnings("unchecked")
	public List<OrigemModel> pesquisarOrigens() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO, CODIGO FROM ORIGEM WHERE FLAG_ATIVO ORDER BY DESCRICAO");

		return broker.getCollectionBean(OrigemModel.class, "id", "descricao", "codigo");

	}

	@SuppressWarnings("unchecked")
	public List<ComboModel> pesquisarTipoLogradouro() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO FROM TIPO_LOGRADOURO ORDER BY DESCRICAO");

		return broker.getCollectionBean(ComboModel.class, "id", "descricao");

	}

	@SuppressWarnings("unchecked")
	public List<PermissaoModel> pesquisarPermissoes() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO, FLAG_ATIVO FROM PERMISSAO  WHERE FLAG_ATIVO IS TRUE ORDER BY DESCRICAO");

		return broker.getCollectionBean(PermissaoModel.class, "id", "descricao", "flagAtivo");

	}

	@SuppressWarnings("unchecked")
	public List<UsuarioModel> pesquisarUsuarios() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, NOME FROM USUARIO WHERE FLAG_ATIVO = TRUE ORDER BY NOME");

		return broker.getCollectionBean(UsuarioModel.class, "id", "nome");
	}

	@SuppressWarnings("unchecked")
	public List<FuncaoModel> pesquisarFuncoes() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO FROM FUNCAO WHERE FLAG_ATIVO = TRUE ORDER BY DESCRICAO");

		return broker.getCollectionBean(FuncaoModel.class, "id", "descricao");
	}

	@SuppressWarnings("unchecked")
	public List<SetorModel> pesquisarSetores() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO FROM SETOR WHERE FLAG_ATIVO = TRUE ORDER BY DESCRICAO");

		return broker.getCollectionBean(SetorModel.class, "id", "descricao");
	}

	@SuppressWarnings("unchecked")
	public List<TipoRespostaModel> pesquisarTiposResposta() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO FROM TIPO_RESPOSTA ORDER BY DESCRICAO");

		return broker.getCollectionBean(TipoRespostaModel.class, "id", "descricao");

	}

	@SuppressWarnings("unchecked")
	public List<TabModel> pesquisarTab() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO, URL, FLAG_ATIVO, TITULO, FACES_RESET FROM TAB WHERE FLAG_ATIVO ORDER BY DESCRICAO");

		return broker.getCollectionBean(TabModel.class, "id", "descricao", "url", "flagAtivo", "titulo", "facesReset");

	}

	@SuppressWarnings("unchecked")
	public List<SetorModel> pesquisarSetores(OrigemModel origem) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO FROM SETOR S WHERE FLAG_ATIVO AND EXISTS (SELECT 1 FROM SETOR_ORIGEM SO WHERE SO.SETOR_ID = S.ID AND SO.ORIGEM_ID = ?) ORDER BY DESCRICAO", origem.getId());

		return broker.getCollectionBean(SetorModel.class, "id", "descricao");

	}

	@SuppressWarnings("unchecked")
	public List<CentroResultadoModel> pesquisarCentroResultado() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO, CODIGO FROM CENTRO_RESULTADO CR WHERE CR.FLAG_ATIVO ORDER BY CR.DESCRICAO");

		return broker.getCollectionBean(CentroResultadoModel.class, "id", "descricao", "codigo");

	}

	@SuppressWarnings("unchecked")
	public List<TipoQuizModel> pesquisarTiposQuizDocumento() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO FROM TIPO_QUIZ WHERE TAB_ID = ? ORDER BY DESCRICAO", Constantes.TAB_DOCUMENTO);

		return broker.getCollectionBean(TipoQuizModel.class, "id", "descricao");

	}

	@SuppressWarnings("unchecked")
	public List<CargoModel> pesquisarCargos() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO, CODIGO FROM CARGO WHERE FLAG_ATIVO ORDER BY DESCRICAO");

		return broker.getCollectionBean(CargoModel.class, "id", "descricao", "codigo");

	}
	
	@SuppressWarnings("unchecked")
	public List<AtividadeModel> pesquisarAtividades() {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("SELECT ID, DESCRICAO, CODIGO, CODIGO || ' - ' || DESCRICAO FROM ATIVIDADE WHERE FLAG_ATIVO ORDER BY ID");
		
		return broker.getCollectionBean(AtividadeModel.class, "id", "descricao", "codigo", "descricaoCompleta");
		
	}

	@SuppressWarnings("unchecked")
	public List<StatusAtendimentoModel> pesquisarStatusAtendimento() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO, FLAG_FECHADO FROM STATUS_ATENDIMENTO WHERE FLAG_ATIVO ORDER BY DESCRICAO");

		return broker.getCollectionBean(StatusAtendimentoModel.class, "id", "descricao", "flagFechado");

	}

	@SuppressWarnings("unchecked")
	public List<ProcedimentoModel> pesquisarProcedimentos(final ProcedimentoModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, CODIGO, DESCRICAO FROM PROCEDIMENTO P WHERE FLAG_ATIVO AND P.ID <> COALESCE(?, 0) ORDER BY DESCRICAO", model.getId());

		return broker.getCollectionBean(ProcedimentoModel.class, "id", "codigo", "descricao");

	}

	@SuppressWarnings("unchecked")
	public List<OrigemModel> pesquisarOrigens(final UsuarioModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, NOME, DESCRICAO FROM ORIGEM O WHERE FLAG_ATIVO AND EXISTS(SELECT 1 FROM USUARIO_FUNCAO UF WHERE UF.ORIGEM_ID = O.ID AND UF.USUARIO_ID = ?) ORDER BY DESCRICAO", model.getId());

		return broker.getCollectionBean(OrigemModel.class, "id", "nome", "descricao");

	}

	@SuppressWarnings("unchecked")
	public List<CidModel> pesquisarCids() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, CODIGO, DESCRICAO FROM CID C WHERE FLAG_ATIVO ORDER BY CODIGO");

		return broker.getCollectionBean(CidModel.class, "id", "codigo", "descricao");

	}

	@SuppressWarnings("unchecked")
	public List<UnidadeMedidaModel> pesquisarUnidadesMedida() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO FROM UNIDADE_MEDIDA UM ORDER BY 2");

		return broker.getCollectionBean(UnidadeMedidaModel.class, "id", "descricao");

	}
	
	@SuppressWarnings("unchecked")
	public List<TipoCronModel> pesquisarTiposCron() {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("SELECT ID, DESCRICAO FROM TIPO_CRON UM ORDER BY 2");
		
		return broker.getCollectionBean(TipoCronModel.class, "id", "descricao");
		
	}
	
	@SuppressWarnings("unchecked")
	public List<FrequenciaMarcacaoExameModel> pesquisarFrequenciaMarcacaoExame() {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("SELECT ID, DESCRICAO, SQL FROM FREQUENCIA_MARCACAO_EXAME FME ORDER BY 1");
		
		return broker.getCollectionBean(FrequenciaMarcacaoExameModel.class, "id", "descricao", "sql");
		
	}
	
	
	
	
	

	public FuncaoModel obter(FuncaoModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO FROM FUNCAO WHERE ID = ?", model.getId());

		return (FuncaoModel) broker.getObjectBean(FuncaoModel.class, "id", "descricao");

	}

	public OrigemModel obter(OrigemModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO FROM ORIGEM WHERE ID = ?", model.getId());

		return (OrigemModel) broker.getObjectBean(OrigemModel.class, "id", "descricao");

	}

	public PermissaoModel obter(final PermissaoModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO, FLAG_ATIVO FROM PERMISSAO WHERE ID = ? ORDER BY DESCRICAO", model.getId());

		return (PermissaoModel) broker.getObjectBean(PermissaoModel.class, "id", "descricao", "flagAtivo");

	}

	public StatusAtendimentoModel obter(final StatusAtendimentoModel model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT ID, DESCRICAO, FLAG_FECHADO FROM STATUS_ATENDIMENTO WHERE ID = ? ORDER BY DESCRICAO", model.getId());

		return (StatusAtendimentoModel) broker.getObjectBean(StatusAtendimentoModel.class, "id", "descricao", "flagFechado");

	}

	public BairroModel obterBairro(final String bairro, final String cidade) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setSQL("SELECT B.ID, B.CIDADE_ID, C.ESTADO_ID FROM BAIRRO B, CIDADE C WHERE C.ID = B.CIDADE_ID AND SEM_ACENTOS(B.DESCRICAO) = SEM_ACENTOS(?) AND SEM_ACENTOS(C.DESCRICAO) = SEM_ACENTOS(?) ORDER BY B.ID LIMIT 1", bairro, cidade);

		return (BairroModel) broker.getObjectBean(BairroModel.class, "id", "cidadeModel.id", "cidadeModel.estadoModel.id");

	}
	
	public ProcedimentoModel obter(ProcedimentoModel model) {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("SELECT ID, CODIGO, DESCRICAO FROM PROCEDIMENTO P WHERE P.ID = ?", model.getId());
		
		return (ProcedimentoModel) broker.getObjectBean(ProcedimentoModel.class, "id", "codigo", "descricao");
		
	}
	
	public TipoCronModel obter(TipoCronModel model) {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("SELECT ID, DESCRICAO FROM TIPO_CRON TC WHERE TC.ID = ?", model.getId());
		
		return (TipoCronModel) broker.getObjectBean(TipoCronModel.class, "id", "descricao");
		
	}
	
	public FrequenciaMarcacaoExameModel obter(FrequenciaMarcacaoExameModel model) {
		
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		broker.setSQL("SELECT ID, DESCRICAO, SQL FROM FREQUENCIA_MARCACAO_EXAME FME WHERE FME.ID = ?", model.getId());
		
		return (FrequenciaMarcacaoExameModel) broker.getObjectBean(FrequenciaMarcacaoExameModel.class, "id", "descricao", "sql");
		
	}

}
