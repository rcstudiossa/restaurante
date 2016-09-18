package br.com.restaurante.util;

import java.io.File;
import java.io.FileFilter;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.Map;

import org.primefaces.context.RequestContext;

import br.com.topsys.exception.TSSystemException;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.util.TSFacesUtil;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

public class JasperUtil {

	private final String CAMINHO_RELATORIO = "WEB-INF" + File.separator + "relatorios" + File.separator;

	public String gerarRelatorio(String jasper, Map<String, Object> parametros) {

		return this.gerarRelatorioCaminhoCompleto(TSFacesUtil.getServletContext().getRealPath(CAMINHO_RELATORIO + jasper), parametros);

	}

	public void gerarRelatorioExcel(String jasper, Map<String, Object> parametros, String nomeArquivo) {

		this.gerarRelatorioCaminhoCompletoExcel(TSFacesUtil.getServletContext().getRealPath(CAMINHO_RELATORIO + jasper), parametros, nomeArquivo);

	}

	public String gerarRelatorioCaminhoCompleto(String caminhoCompleto, Map<String, Object> parametros) {

		Connection con = null;
		FileFilter filter = null;
		File[] dirFiles = null;
		String caminhoCriacao = null;

		try {

			con = ConnectionFactory.getConnection();

			JasperPrint impressao = JasperFillManager.fillReport(caminhoCompleto, parametros, con);

			if (!TSUtil.isEmpty(impressao)) {

				String destino = Utilitario.getUsuarioLogado().getId() + "_" + TSUtil.gerarId() + ".pdf";

				RequestContext.getCurrentInstance().addCallbackParam("arquivoImpressao", destino);

				caminhoCriacao = Constantes.PASTA_UPLOAD_RELATORIOS + destino;

				filter = new FileFilter() {
					@Override
					public boolean accept(File pathname) {
						return pathname.getName().startsWith(Utilitario.getUsuarioLogado().getId() + "_") && pathname.getName().endsWith(".pdf");
					}
				};

				dirFiles = new File(Constantes.PASTA_UPLOAD_RELATORIOS).listFiles(filter);
				
				for (int i = 0; i < dirFiles.length; i++) {
					new File(Constantes.PASTA_UPLOAD_RELATORIOS + dirFiles[i].getName()).delete();
				}
				
				JasperExportManager.exportReportToPdfFile(impressao, caminhoCriacao);

			}

		} catch (Exception e) {

			throw new TSSystemException(e);

		} finally {
			
			filter = null;
			dirFiles = null;

			ConnectionFactory.closeConnection(con);
			
		}

		return caminhoCriacao;

	}

	public void gerarRelatorioCaminhoCompletoExcel(String caminhoCompleto, Map<String, Object> parametros, String nomeArquivo) {

		Connection con = null;

		try {

			con = ConnectionFactory.getConnection();

			JasperPrint impressao = JasperFillManager.fillReport(caminhoCompleto, parametros, con);

			if (!TSUtil.isEmpty(impressao)) {

				String destino = nomeArquivo + ".xls";

				OutputStream output = null;

				parametros.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);

				output = TSFacesUtil.getResponse().getOutputStream();

				TSFacesUtil.getResponse().setHeader("Content-Type", "application/vnd.ms-excel");
				TSFacesUtil.getResponse().setHeader("Content-Disposition", "attachment; filename=" + destino);

				JRXlsExporter exporter = new JRXlsExporter();
				
				exporter.setExporterInput(new SimpleExporterInput(impressao));
				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(output));
				
				SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
				
				configuration.setOnePagePerSheet(Boolean.FALSE);
				configuration.setDetectCellType(Boolean.TRUE);
				configuration.setCollapseRowSpan(Boolean.TRUE);
				configuration.setRemoveEmptySpaceBetweenColumns(Boolean.TRUE);
				configuration.setRemoveEmptySpaceBetweenRows(Boolean.TRUE);
				configuration.setWhitePageBackground(Boolean.FALSE);
				configuration.setIgnoreGraphics(Boolean.TRUE);
				
				exporter.setConfiguration(configuration);

				exporter.exportReport();

				TSFacesUtil.getFacesContext().renderResponse();
				TSFacesUtil.getFacesContext().responseComplete();

			}

		} catch (Exception e) {

			throw new TSSystemException(e);

		} finally {

			ConnectionFactory.closeConnection(con);

		}

	}

}
