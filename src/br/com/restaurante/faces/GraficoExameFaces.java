package br.com.restaurante.faces;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import br.com.restaurante.business.QuizPerguntaBS;
import br.com.restaurante.business.QuizQuestionarioBS;
import br.com.restaurante.business.SolicitacaoExameBS;
import br.com.restaurante.model.QuizPerguntaModel;
import br.com.restaurante.model.QuizQuestionarioRespostaModel;
import br.com.restaurante.model.SolicitacaoExameItemResultadoModel;
import br.com.restaurante.util.Utilitario;
import br.com.topsys.util.TSDateUtil;
import br.com.topsys.util.TSParseUtil;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.faces.TSMainFaces;

@ViewScoped
@SuppressWarnings("serial")
@ManagedBean(name = "graficoExameFaces")
public class GraficoExameFaces extends TSMainFaces {

	@EJB
	private QuizPerguntaBS quizPerguntaBS;

	@EJB
	private SolicitacaoExameBS solicitacaoExameBS;

	@EJB
	private QuizQuestionarioBS quizQuestionarioBS;

	private LineChartModel lineChart;
	private LineChartModel lineChartFuncaoVital;

	private List<SelectItem> comboExames;
	private List<SelectItem> comboFuncoesVitais;

	private QuizPerguntaModel exameModel;
	private QuizPerguntaModel funcaoVitalModel;

	private Integer tabIndex;

	@Override
	@PostConstruct
	protected void clearFields() {

		this.comboExames = super.initCombo(this.quizPerguntaBS.pesquisarExamesLaboratoriaisCalculaveis(), "id", "apelido");
		this.comboFuncoesVitais = super.initCombo(this.quizPerguntaBS.pesquisarFuncoesVitais(), "id", "apelido");

		this.exameModel = new QuizPerguntaModel();
		this.funcaoVitalModel = new QuizPerguntaModel();

		this.lineChart = this.iniciarLineChart();
		this.lineChartFuncaoVital = this.iniciarLineChart();

		this.tabIndex = 0;

	}

	private LineChartModel iniciarLineChart() {

		LineChartModel lineChart = new LineChartModel();

		ChartSeries charSerie = new ChartSeries();

		charSerie.setLabel("Nenhum registro encontrado");
		charSerie.set(TSParseUtil.dateToString(new Date()), 0);

		lineChart.addSeries(charSerie);
		
		return lineChart;
	}

	public String visualizarGrafico() {
		
		this.tabIndex = 0;

		if (TSUtil.isEmpty(this.exameModel.getId())) {
			super.addErrorMessage("Selecione o exame desejado");
			return null;
		}

		List<SolicitacaoExameItemResultadoModel> resultados = this.solicitacaoExameBS.pesquisarResultadosExames(this.exameModel, Utilitario.getFuncionarioSessao());

		if (!TSUtil.isEmpty(resultados)) {

			this.lineChart = new LineChartModel();

			this.lineChart.setTitle("Histórico do Exame");
			this.lineChart.setLegendPosition("e");
			this.lineChart.setShowPointLabels(true);
			this.lineChart.setZoom(true);

			CategoryAxis xAxis = new CategoryAxis("Data");
			xAxis.setTickAngle(-50);

			this.lineChart.getAxes().put(AxisType.X, xAxis);

			Axis yAxis = this.lineChart.getAxis(AxisType.Y);
			yAxis.setLabel("Valor");

			LineChartSeries charSerie = new LineChartSeries();

			charSerie.setLabel(this.quizPerguntaBS.obterSimples(this.exameModel).getNomePerguntaSimples());

			Collections.reverse(resultados);

			double minValue = 0;
			double maxValue = 0;

			for (SolicitacaoExameItemResultadoModel resultado : resultados) {

				if (!TSUtil.isEmpty(resultado.getResultado()) && Utilitario.isNumeric(resultado.getResultado())) {

					charSerie.set(TSParseUtil.dateToString(resultado.getDataAtualizacao(), TSDateUtil.DD_MM_YYYY_HH_MM), Double.valueOf(resultado.getResultado()));

					if (Double.valueOf(resultado.getResposta()) > maxValue) {
						maxValue = Double.valueOf(resultado.getResposta());
					}

					if (Double.valueOf(resultado.getResposta()) < minValue) {
						minValue = Double.valueOf(resultado.getResposta());
					}

				}

			}

			yAxis.setMin(minValue);
			yAxis.setMax(maxValue + maxValue / 10);

			this.lineChart.addSeries(charSerie);

		} else {

			this.lineChart = this.iniciarLineChart();

		}

		return null;
	}

	public String visualizarGraficoFuncaoVital() {
		
		this.tabIndex = 1;

		if (TSUtil.isEmpty(this.funcaoVitalModel.getId())) {
			super.addErrorMessage("Selecione a função vital desejada");
			return null;
		}

		List<QuizQuestionarioRespostaModel> resultados = this.quizQuestionarioBS.pesquisarRespostasFuncoesVitais(Utilitario.getFuncionarioSessao(), this.funcaoVitalModel);

		if (!TSUtil.isEmpty(resultados)) {

			Collections.reverse(resultados);

			this.lineChartFuncaoVital = new LineChartModel();

			this.lineChartFuncaoVital.setTitle("Histórico da Função Vital");
			this.lineChartFuncaoVital.setLegendPosition("e");
			this.lineChartFuncaoVital.setShowPointLabels(true);
			this.lineChartFuncaoVital.setZoom(true);

			CategoryAxis xAxis = new CategoryAxis("Data");
			xAxis.setTickAngle(-50);

			this.lineChartFuncaoVital.getAxes().put(AxisType.X, xAxis);

			Axis yAxis = this.lineChartFuncaoVital.getAxis(AxisType.Y);
			yAxis.setLabel("Valor");

			ChartSeries charSerie = new ChartSeries(this.quizPerguntaBS.obterSimples(this.funcaoVitalModel).getNomePerguntaSimples());
			ChartSeries charPaSistolica = new ChartSeries("Pressão arterial sistólica");
			ChartSeries charPaDiastolica = new ChartSeries("Pressão arterial diastólica");

			double minValue = 0;
			double maxValue = 0;

			int cont = 0;
			boolean acheiResultado = false;
			boolean acheiResultadoPA = false;

			for (QuizQuestionarioRespostaModel resultado : resultados) {

				if (!TSUtil.isEmpty(resultado.getResposta()) && Utilitario.isNumeric(resultado.getResposta())) {

					acheiResultado = true;

					charSerie.set(TSParseUtil.dateToString(resultado.getQuizTemplate().getDataCadastro(), TSDateUtil.DD_MM_YYYY_HH_MM), Double.valueOf(resultado.getResposta()));

					if (Double.valueOf(resultado.getResposta()) > maxValue) {
						maxValue = Double.valueOf(resultado.getResposta());
					}

					if (cont == 0 || Double.valueOf(resultado.getResposta()) < minValue) {
						minValue = Double.valueOf(resultado.getResposta());
					}

					cont++;

				} else if (resultado.getResposta().contains("/")) {

					acheiResultadoPA = true;

					maxValue = 200;
					minValue = 0;

					charPaSistolica.set(TSParseUtil.dateToString(resultado.getQuizTemplate().getDataCadastro(), TSDateUtil.DD_MM_YYYY_HH_MM), Double.valueOf(resultado.getResposta().split("/")[0]));
					charPaDiastolica.set(TSParseUtil.dateToString(resultado.getQuizTemplate().getDataCadastro(), TSDateUtil.DD_MM_YYYY_HH_MM), Double.valueOf(resultado.getResposta().split("/")[1]));

				}

			}

			yAxis.setMin(minValue - minValue / 10);
			yAxis.setMax(maxValue + maxValue / 10);

			if (acheiResultado) {
				this.lineChartFuncaoVital.addSeries(charSerie);
			}

			if (acheiResultadoPA) {
				this.lineChartFuncaoVital.addSeries(charPaSistolica);
				this.lineChartFuncaoVital.addSeries(charPaDiastolica);
			}

		} else {

			this.lineChartFuncaoVital = this.iniciarLineChart();

		}

		return null;
	}

	public LineChartModel getLineChart() {
		return lineChart;
	}

	public void setLineChart(LineChartModel lineChart) {
		this.lineChart = lineChart;
	}

	public List<SelectItem> getComboExames() {
		return comboExames;
	}

	public void setComboExames(List<SelectItem> comboExames) {
		this.comboExames = comboExames;
	}

	public QuizPerguntaModel getExameModel() {
		return exameModel;
	}

	public void setExameModel(QuizPerguntaModel exameModel) {
		this.exameModel = exameModel;
	}

	public List<SelectItem> getComboFuncoesVitais() {
		return comboFuncoesVitais;
	}

	public void setComboFuncoesVitais(List<SelectItem> comboFuncoesVitais) {
		this.comboFuncoesVitais = comboFuncoesVitais;
	}

	public QuizPerguntaModel getFuncaoVitalModel() {
		return funcaoVitalModel;
	}

	public void setFuncaoVitalModel(QuizPerguntaModel funcaoVitalModel) {
		this.funcaoVitalModel = funcaoVitalModel;
	}

	public LineChartModel getLineChartFuncaoVital() {
		return lineChartFuncaoVital;
	}

	public void setLineChartFuncaoVital(LineChartModel lineChartFuncaoVital) {
		this.lineChartFuncaoVital = lineChartFuncaoVital;
	}

	public Integer getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(Integer tabIndex) {
		this.tabIndex = tabIndex;
	}

}
