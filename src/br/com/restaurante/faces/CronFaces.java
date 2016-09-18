package br.com.restaurante.faces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

import br.com.restaurante.business.ComboBS;
import br.com.restaurante.business.CronBS;
import br.com.restaurante.business.CrudBS;
import br.com.restaurante.business.PerfilBS;
import br.com.restaurante.business.ProcedimentoBS;
import br.com.restaurante.jobs.SistemaJob;
import br.com.restaurante.model.AtividadeModel;
import br.com.restaurante.model.CronExameModel;
import br.com.restaurante.model.CronModel;
import br.com.restaurante.model.FrequenciaMarcacaoExameModel;
import br.com.restaurante.model.PerfilModel;
import br.com.restaurante.model.ProcedimentoModel;
import br.com.restaurante.model.TipoCronModel;
import br.com.restaurante.util.Constantes;
import br.com.restaurante.util.Utilitario;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSParseUtil;
import br.com.topsys.util.TSUtil;

@ViewScoped
@SuppressWarnings("serial")
@ManagedBean(name = "cronFaces")
public class CronFaces extends CrudFaces<CronModel> {

	@EJB
	private CronBS cronBS;
	
	@EJB
	private ComboBS comboBS;
	
	@EJB
	private ProcedimentoBS procedimentoBS;
	
	@EJB
	private PerfilBS perfilBS;
	
	private List<SelectItem> comboAtividade;
	private List<SelectItem> comboTipoCron;
	private List<SelectItem> comboFrequenciaMarcacaoExame;
	private List<SelectItem> comboSexo;
	
	private ProcedimentoModel procedimentoModel;
	private CronExameModel cronExameModel;

	@Override
	@PostConstruct
	protected void clearFields() {

		this.crudModel = new CronModel();
		this.crudModel.setFlagAtivo(Boolean.TRUE);
		this.crudModel.setTipoCronModel(new TipoCronModel());
		this.crudModel.getTipoCronModel().setId(Constantes.TIPO_CRON_MARCACAO_EXAME);
		this.crudModel.setAtividadeModel(new AtividadeModel());
		this.crudModel.setFrequenciaMarcacaoExameModel(new FrequenciaMarcacaoExameModel());
		this.crudModel.setExames(new ArrayList<CronExameModel>());

		this.crudPesquisaModel = new CronModel();
		this.crudPesquisaModel.setFlagAtivo(Boolean.TRUE);
		
		this.comboTipoCron = super.initCombo(this.comboBS.pesquisarTiposCron(), "id", "descricao");
		this.comboAtividade = super.initCombo(this.comboBS.pesquisarAtividades(), "id", "descricaoCompleta");
		this.comboFrequenciaMarcacaoExame = super.initCombo(this.comboBS.pesquisarFrequenciaMarcacaoExame(), "id", "descricao");
		this.comboSexo = super.initCombo(this.comboBS.pesquisarSexo(), "id", "descricao");

	}

	private String obterConfiguracao() {

		StringBuilder configuracao = new StringBuilder();

		configuracao.append(this.crudModel.getSegundos()).append(" ");
		configuracao.append(this.crudModel.getMinutos()).append(" ");
		configuracao.append(this.crudModel.getHoras()).append(" ");
		configuracao.append(this.crudModel.getDiaMes()).append(" ");
		configuracao.append(this.crudModel.getMes()).append(" ");
		configuracao.append(this.crudModel.getDiaSemana());

		if (!TSUtil.isEmpty(this.crudModel.getAno())) {
			configuracao.append(" ").append(this.crudModel.getAno());
		}

		return configuracao.toString();
	}

	@Override
	protected void posInsert() {

		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched;

		try {

			Date dataAtual = TSParseUtil.stringToDate(TSParseUtil.dateHourToString(new Date()));
			Date dataInicial = this.crudModel.getDataInicial();
			Date dataFinal = (!TSUtil.isEmpty(this.crudModel.getDataFinal()) ? this.crudModel.getDataFinal() : Utilitario.getDataOperacaoDia(1));
			
			if (!dataInicial.after(dataAtual) && !dataFinal.before(dataAtual) && this.crudModel.getFlagAtivo()) {
				
				sched = sf.getScheduler();
				
				JobDetail job = JobBuilder.newJob(SistemaJob.class).withIdentity(Constantes.CRON_JOB + this.crudModel.getId(), Constantes.CRON_GROUP).usingJobData(SistemaJob.CRON_ID, this.crudModel.getId()).build();
				
				CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(Constantes.CRON_TRIGGER + this.crudModel.getId(), Constantes.CRON_GROUP).withSchedule(CronScheduleBuilder.cronSchedule(this.obterConfiguracao().toString())).build();
				
				sched.scheduleJob(job, trigger);
				
				sched.start();
				
			}

		} catch (SchedulerException e) {

			super.addErrorMessage("Ocorreu um erro na execução do agendador. Por favor, revise as configurações");
			e.printStackTrace();

		} catch (Exception e) {

			super.addErrorMessage("Ocorreu um erro na execução do agendador. Por favor, revise as configurações");
			e.printStackTrace();

		}

	}

	@Override
	protected boolean validaCampos() {

		boolean valida = true;

		if (!TSUtil.isEmpty(this.crudModel.getDataFinal()) && Utilitario.isPeriodoInvalido(this.crudModel.getDataInicial(), this.crudModel.getDataFinal())) {
			super.addErrorMessage("Período inválido");
			valida = false;
		}

		if (!CronExpression.isValidExpression(this.obterConfiguracao())) {
			super.addErrorMessage("Configuração inválida");
			valida = false;
		}
		
		if(Constantes.TIPO_CRON_MARCACAO_EXAME.equals(this.crudModel.getTipoCronModel().getId()) && TSUtil.isEmpty(this.crudModel.getExames())) {
			super.addErrorMessage("Adicione ao menos 1 exame");
			valida = false;
		}

		return valida;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void posUpdate() {

		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched;

		try {

			String configuracao = this.obterConfiguracao();

			sched = sf.getScheduler();

			Date dataAtual = TSParseUtil.stringToDate(TSParseUtil.dateHourToString(new Date()));
			Date dataInicial = this.crudModel.getDataInicial();
			Date dataFinal = (!TSUtil.isEmpty(this.crudModel.getDataFinal()) ? this.crudModel.getDataFinal() : Utilitario.getDataOperacaoDia(1));

			Trigger oldTrigger = sched.getTrigger(new TriggerKey(Constantes.CRON_TRIGGER + this.crudModel.getId(), Constantes.CRON_GROUP));

			if (!TSUtil.isEmpty(oldTrigger)) {

				TriggerBuilder tb = oldTrigger.getTriggerBuilder();

				Trigger newTrigger = tb.withSchedule(CronScheduleBuilder.cronSchedule(configuracao)).build();

				if (!dataInicial.after(dataAtual) && !dataFinal.before(dataAtual) && this.crudModel.getFlagAtivo()) {

					sched.rescheduleJob(oldTrigger.getKey(), newTrigger);

				} else {

					sched.shutdown(true);

				}

			} else {

				if (!dataInicial.after(dataAtual) && !dataFinal.before(dataAtual) && this.crudModel.getFlagAtivo()) {

					JobDetail job = JobBuilder.newJob(SistemaJob.class).withIdentity(Constantes.CRON_JOB + this.crudModel.getId(), Constantes.CRON_GROUP).usingJobData(SistemaJob.CRON_ID, this.crudModel.getId()).build();

					CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(Constantes.CRON_TRIGGER + this.crudModel.getId(), Constantes.CRON_GROUP).withSchedule(CronScheduleBuilder.cronSchedule(configuracao.toString())).build();

					sched.scheduleJob(job, trigger);

					sched.start();

				}

			}

		} catch (SchedulerException e) {

			super.addErrorMessage("Ocorreu um erro na execução do agendador. Por favor, revise as configurações");
			e.printStackTrace();

		} catch (Exception e) {

			super.addErrorMessage("Ocorreu um erro na execução do agendador. Por favor, revise as configurações");
			e.printStackTrace();

		}

	}
	
	public String addProcedimento() {

		if (TSUtil.isEmpty(this.procedimentoModel) || TSUtil.isEmpty(this.procedimentoModel.getId())) {
			super.addErrorMessage("Procedimento / Perfil: Campo obrigatório");
			return null;
		}
		
		CronExameModel cronExameModel = new CronExameModel();
		
		cronExameModel.setCronModel(this.crudModel);
		
		if (this.procedimentoModel.getTipo().equals("PROCEDIMENTO")) {

			cronExameModel.setProcedimentoModel(this.procedimentoBS.obterSimples(this.procedimentoModel));
			cronExameModel.setPerfilModel(new PerfilModel());

		} else if (this.procedimentoModel.getTipo().equals("PERFIL")) {
			
			PerfilModel perfilModel = this.perfilBS.obterCrudModel(new PerfilModel(this.procedimentoModel.getId()));
			
			cronExameModel.setProcedimentoModel(new ProcedimentoModel());
			cronExameModel.setPerfilModel(perfilModel);
			
		}
				
		if(this.crudModel.getExames().contains(cronExameModel)){
			super.addErrorMessage("Procedimento / Perfil já foi adicionado anteriormente");
			return null;
		}
		
		this.crudModel.getExames().add(cronExameModel);
		
		this.procedimentoModel = null;
		
		return null;

	}
	
	public void removerProcedimento() {

		try {
			
			this.cronBS.excluir(this.cronExameModel);
			
			this.crudModel.getExames().remove(this.cronExameModel);
			
			super.addInfoMessageKey(Constantes.OPERACAO_SUCESSO);
			
		} catch (TSApplicationException e) {
			
			super.throwException(e);
			
		}
		
	}
	
	@Override
	protected void tratarClone() {
		
		for(CronExameModel exame : this.crudModel.getExames()){
			
			exame.setId(null);
			exame.setCronModel(this.crudModel);
			
		}
		
	}
	
	public List<ProcedimentoModel> buscaExame(String query) {
		return this.procedimentoBS.pesquisarAutoComplete(query);
	}

	@Override
	protected CrudBS<CronModel> getCrudBS() {
		return this.cronBS;
	}

	public List<SelectItem> getComboAtividade() {
		return comboAtividade;
	}

	public void setComboAtividade(List<SelectItem> comboAtividade) {
		this.comboAtividade = comboAtividade;
	}

	public List<SelectItem> getComboTipoCron() {
		return comboTipoCron;
	}

	public void setComboTipoCron(List<SelectItem> comboTipoCron) {
		this.comboTipoCron = comboTipoCron;
	}

	public List<SelectItem> getComboFrequenciaMarcacaoExame() {
		return comboFrequenciaMarcacaoExame;
	}

	public void setComboFrequenciaMarcacaoExame(List<SelectItem> comboFrequenciaMarcacaoExame) {
		this.comboFrequenciaMarcacaoExame = comboFrequenciaMarcacaoExame;
	}

	public List<SelectItem> getComboSexo() {
		return comboSexo;
	}

	public void setComboSexo(List<SelectItem> comboSexo) {
		this.comboSexo = comboSexo;
	}

	public ProcedimentoModel getProcedimentoModel() {
		return procedimentoModel;
	}

	public void setProcedimentoModel(ProcedimentoModel procedimentoModel) {
		this.procedimentoModel = procedimentoModel;
	}

	public CronExameModel getCronExameModel() {
		return cronExameModel;
	}

	public void setCronExameModel(CronExameModel cronExameModel) {
		this.cronExameModel = cronExameModel;
	}

}
