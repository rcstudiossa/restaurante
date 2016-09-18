package br.com.restaurante.model;

import java.util.Date;
import java.util.List;

import br.com.topsys.util.TSUtil;

@SuppressWarnings("serial")
public class UsuarioModel extends BaseModel implements CrudModel<UsuarioModel> {

	private String nome;
	private String apelido;
	private String login;
	private String senha;
	private String descricao;
	private Boolean flagAdministrador;
	private Boolean flagBloqueado;
	private List<UsuarioFuncaoModel> usuarioFuncoes;
	private String email;
	private String cpf;
	private String rg;
	private String telefone;
	private boolean leuAviso;
	private SetorModel setorModel;
	private Date dataAlteracaoSenha;
	private Integer qtdTentativasLoginInvalido;
	private Boolean flagUsuario;
	private String matricula;
	private String observacao;
	private Date dataNascimento;
	private List<UsuarioModel> historico;
	private UsuarioFuncaoModel usuarioFuncaoModel;
	private Integer sexo;
	private String endereco;
	private String numero;
	private String complemento;
	private BairroModel bairroModel;
	private CidadeModel cidadeModel;
	private EstadoModel estadoModel;
	private String cep;
	private Long idExterno;
	private OrigemModel origemModel;
	
	public UsuarioModel() {
	}

	public UsuarioModel(String cpf) {
		this.cpf = cpf;
	}

	public UsuarioModel(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public UsuarioModel(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public String getNomeMaiusculo() {
		return nome.toUpperCase();
	}

	public String getNomeEscolhido() {
		return !TSUtil.isEmpty(apelido) ? apelido : nome; 
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Boolean getFlagAdministrador() {
		return flagAdministrador;
	}

	public void setFlagAdministrador(Boolean flagAdministrador) {
		this.flagAdministrador = flagAdministrador;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public List<UsuarioFuncaoModel> getUsuarioFuncoes() {
		return usuarioFuncoes;
	}

	public void setUsuarioFuncoes(List<UsuarioFuncaoModel> usuarioFuncoes) {
		this.usuarioFuncoes = usuarioFuncoes;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public boolean isLeuAviso() {
		return leuAviso;
	}

	public void setLeuAviso(boolean leuAviso) {
		this.leuAviso = leuAviso;
	}

	public Date getDataAlteracaoSenha() {
		return dataAlteracaoSenha;
	}

	public void setDataAlteracaoSenha(Date dataAlteracaoSenha) {
		this.dataAlteracaoSenha = dataAlteracaoSenha;
	}

	public Integer getQtdTentativasLoginInvalido() {
		return qtdTentativasLoginInvalido;
	}

	public void setQtdTentativasLoginInvalido(Integer qtdTentativasLoginInvalido) {
		this.qtdTentativasLoginInvalido = qtdTentativasLoginInvalido;
	}

	public Boolean getFlagUsuario() {
		return flagUsuario;
	}

	public void setFlagUsuario(Boolean flagUsuario) {
		this.flagUsuario = flagUsuario;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public List<UsuarioModel> getHistorico() {
		return historico;
	}

	public void setHistorico(List<UsuarioModel> historico) {
		this.historico = historico;
	}

	public UsuarioFuncaoModel getUsuarioFuncaoModel() {
		return usuarioFuncaoModel;
	}

	public void setUsuarioFuncaoModel(UsuarioFuncaoModel usuarioFuncaoModel) {
		this.usuarioFuncaoModel = usuarioFuncaoModel;
	}

	public Integer getSexo() {
		return sexo;
	}

	public void setSexo(Integer sexo) {
		this.sexo = sexo;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public BairroModel getBairroModel() {
		return bairroModel;
	}

	public void setBairroModel(BairroModel bairroModel) {
		this.bairroModel = bairroModel;
	}

	public CidadeModel getCidadeModel() {
		return cidadeModel;
	}

	public void setCidadeModel(CidadeModel cidadeModel) {
		this.cidadeModel = cidadeModel;
	}

	public EstadoModel getEstadoModel() {
		return estadoModel;
	}

	public void setEstadoModel(EstadoModel estadoModel) {
		this.estadoModel = estadoModel;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Long getIdExterno() {
		return idExterno;
	}

	public void setIdExterno(Long idExterno) {
		this.idExterno = idExterno;
	}

	public OrigemModel getOrigemModel() {
		return origemModel;
	}

	public void setOrigemModel(OrigemModel origemModel) {
		this.origemModel = origemModel;
	}

	public SetorModel getSetorModel() {
		return setorModel;
	}

	public void setSetorModel(SetorModel setorModel) {
		this.setorModel = setorModel;
	}
	
	public Boolean getFlagBloqueado() {
		return flagBloqueado;
	}

	public void setFlagBloqueado(Boolean flagBloqueado) {
		this.flagBloqueado = flagBloqueado;
	}

	public FuncaoModel getFuncaoLogada() {
		return TSUtil.isEmpty(this.usuarioFuncaoModel) ? new FuncaoModel() : this.getUsuarioFuncaoModel().getFuncaoModel();
	}
	
	public String getSituacaoBloqueio() {
		return !TSUtil.isEmpty(this.flagBloqueado) && this.flagBloqueado ? "Bloqueado" : "Desbloqueado";
	}

}
