package br.com.restaurante.faces;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.restaurante.business.AtendimentoBS;
import br.com.restaurante.business.ComboBS;
import br.com.restaurante.model.AtendimentoModel;
import br.com.restaurante.model.FuncionarioModel;
import br.com.restaurante.model.StatusAtendimentoModel;
import br.com.restaurante.util.Constantes;
import br.com.restaurante.util.Utilitario;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.faces.TSMainFaces;

@ViewScoped
@SuppressWarnings("serial")
@ManagedBean(name = "funcionarioAtivoFaces")
public class FuncionarioAtivoFaces extends TSMainFaces {

	@EJB
	private AtendimentoBS atendimentoBS;

	@EJB
	private ComboBS comboBS;

	private FuncionarioModel funcionarioModel;

	private AtendimentoModel atendimentoModel;

	@PostConstruct
	@Override
	protected void clearFields() {

		this.funcionarioModel = Utilitario.getFuncionarioSessao();

		this.atendimentoModel = Utilitario.getAtendimentoSessao();

	}

	public String iniciarAtendimento() {

		this.atendimentoModel.setDataAtualizacao(new Date());
		this.atendimentoModel.setUsuarioAtualizacaoModel(Utilitario.getUsuarioLogado());
		this.atendimentoModel.setStatusAtendimentoModel(this.comboBS.obter(new StatusAtendimentoModel(Constantes.STATUS_ATENDIMENTO_EM_ATENDIMENTO)));

		try {
			
			this.atendimentoBS.iniciarAtendimento(this.atendimentoModel);

			super.addInfoMessageKey(Constantes.OPERACAO_SUCESSO);

		} catch (TSApplicationException e) {

			super.throwException(e);

		}

		return null;
	}
	
	public String concluirAtendimento() {
		
		this.atendimentoModel.setDataAtualizacao(new Date());
		this.atendimentoModel.setUsuarioAtualizacaoModel(Utilitario.getUsuarioLogado());
		this.atendimentoModel.setStatusAtendimentoModel(this.comboBS.obter(new StatusAtendimentoModel(Constantes.STATUS_ATENDIMENTO_CONCLUIDO)));
		
		try {
			
			this.atendimentoBS.alterarStatus(this.atendimentoModel);
			
			super.addInfoMessageKey(Constantes.OPERACAO_SUCESSO);
			
		} catch (TSApplicationException e) {
			
			super.throwException(e);
			
		}
		
		return null;
	}

	public boolean isEmAtendimento() {
		return !TSUtil.isEmpty(this.atendimentoModel) && !TSUtil.isEmpty(this.atendimentoModel.getStatusAtendimentoModel()) && Constantes.STATUS_ATENDIMENTO_EM_ATENDIMENTO.equals(this.atendimentoModel.getStatusAtendimentoModel().getId());
	}

	public boolean isPacienteMovimentacaoExistente() {
		return !TSUtil.isEmpty(this.atendimentoModel);
	}

	public FuncionarioModel getFuncionarioModel() {
		return funcionarioModel;
	}

	public void setFuncionarioModel(FuncionarioModel funcionarioModel) {
		this.funcionarioModel = funcionarioModel;
	}

	public AtendimentoModel getAtendimentoModel() {
		return atendimentoModel;
	}

	public void setAtendimentoModel(AtendimentoModel atendimentoModel) {
		this.atendimentoModel = atendimentoModel;
	}

}
