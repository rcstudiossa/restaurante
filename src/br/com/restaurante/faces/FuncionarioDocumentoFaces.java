package br.com.restaurante.faces;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FileUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

import br.com.restaurante.business.FuncionarioDocumentoBS;
import br.com.restaurante.model.FuncionarioDocumentoModel;
import br.com.restaurante.util.Constantes;
import br.com.restaurante.util.Utilitario;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.file.TSFile;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.faces.TSMainFaces;

@ViewScoped
@SuppressWarnings("serial")
@ManagedBean(name = "funcionarioDocumentoFaces")
public class FuncionarioDocumentoFaces extends TSMainFaces {

	@EJB
	private FuncionarioDocumentoBS funcionarioDocumentoBS;

	private List<FuncionarioDocumentoModel> documentos;

	private FuncionarioDocumentoModel funcionarioDocumentoModel;

	@Override
	@PostConstruct
	protected void clearFields() {

		this.documentos = this.funcionarioDocumentoBS.pesquisar(Utilitario.getFuncionarioSessao());

		if (!TSUtil.isEmpty(this.documentos)) {

			this.funcionarioDocumentoModel = this.documentos.get(0);

		}

	}

	@Override
	protected String delete() throws TSApplicationException {

		super.setDefaultMessage(false);
		
		this.funcionarioDocumentoModel.setFlagAtivo(Boolean.FALSE);

		this.funcionarioDocumentoBS.excluir(this.funcionarioDocumentoModel);
		
		this.documentos.remove(this.funcionarioDocumentoModel);
		
		if(!TSUtil.isEmpty(this.documentos)){
			
			this.funcionarioDocumentoModel = this.documentos.get(0);
			
		} else {
			
			this.funcionarioDocumentoModel = null;
			
		}

		this.clearFields();

		return null;

	}

	public void handleFileUpload(FileUploadEvent event) {
		
		String extensao = TSFile.obterExtensaoArquivo(event.getFile().getFileName());
		
		if(!extensao.equalsIgnoreCase(".gif") && !extensao.equalsIgnoreCase(".jpg") && !extensao.equalsIgnoreCase(".jpeg") && !extensao.equalsIgnoreCase(".png") && !extensao.equalsIgnoreCase(".pdf")){
			super.addErrorMessage("Extensão do arquivo é inválida. Permitidos: gif, jpg, jpeg, png e pdf");
			return;
		}
		
		this.funcionarioDocumentoModel = new FuncionarioDocumentoModel();
		this.funcionarioDocumentoModel.setFuncionarioModel(Utilitario.getFuncionarioSessao());
		
		this.funcionarioDocumentoModel.setArquivo(TSUtil.gerarId() + extensao);

		try {

			FileUtils.copyInputStreamToFile(event.getFile().getInputstream(), new File(Constantes.PASTA_UPLOAD_ARQUIVO + Constantes.PASTA_PRONTUARIOS + this.funcionarioDocumentoModel.getArquivo()));
			
			this.documentos.add(0, this.funcionarioDocumentoModel);

		} catch (Exception ex) {

			super.addErrorMessage("Ocorreu um erro ao anexar o arquivo");

		}

	}

	public void inserirDocumento() {

		if(TSUtil.isEmpty(this.funcionarioDocumentoModel.getDescricao())){
			super.addErrorMessage("Descrição do documento: Campo obrigatório");
			return;
		}
		
		try {

			this.funcionarioDocumentoModel.setDataCadastro(new Date());
			this.funcionarioDocumentoModel.setUsuarioCadastroModel(Utilitario.getUsuarioLogado());
			
			this.funcionarioDocumentoBS.inserir(this.funcionarioDocumentoModel);

			super.addInfoMessageKey(Constantes.OPERACAO_SUCESSO);

		} catch (TSApplicationException e) {

			super.throwException(e);

		}

	}
	
	public String imprimir() {

		String destino = Utilitario.getUsuarioLogado().getId() + "_" + this.funcionarioDocumentoModel.getArquivo();

		try {

			FileUtils.copyFile(new File(Constantes.PASTA_UPLOAD_ARQUIVO + Constantes.PASTA_PRONTUARIOS + this.funcionarioDocumentoModel.getArquivo()), new File(Constantes.PASTA_UPLOAD_RELATORIOS + destino));

			RequestContext.getCurrentInstance().addCallbackParam("arquivoImpressao", destino);

		} catch (IOException e) {

			e.printStackTrace();
			super.addErrorMessage("Ocorreu um erro ao imprimir o arquivo");

		}

		return null;
	}

	public List<FuncionarioDocumentoModel> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<FuncionarioDocumentoModel> documentos) {
		this.documentos = documentos;
	}

	public FuncionarioDocumentoModel getFuncionarioDocumentoModel() {
		return funcionarioDocumentoModel;
	}

	public void setFuncionarioDocumentoModel(FuncionarioDocumentoModel funcionarioDocumentoModel) {
		this.funcionarioDocumentoModel = funcionarioDocumentoModel;
	}

}
