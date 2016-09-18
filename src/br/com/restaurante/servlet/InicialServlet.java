package br.com.restaurante.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import br.com.restaurante.dao.CronDAO;
import br.com.restaurante.jobs.SistemaJob;
import br.com.restaurante.model.CronModel;
import br.com.restaurante.util.Constantes;
import br.com.topsys.util.TSUtil;

@WebServlet(value = "/InicialServlet", loadOnStartup = 1)
public class InicialServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public InicialServlet() {

		super();

		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched;

		try {

			List<CronModel> agendamentos = new CronDAO().pesquisarAtivos();

			for (CronModel cron : agendamentos) {

				sched = sf.getScheduler();

				JobDetail job = JobBuilder.newJob(SistemaJob.class).withIdentity(Constantes.CRON_JOB + cron.getId(), Constantes.CRON_GROUP).usingJobData(SistemaJob.CRON_ID, cron.getId()).build();

				StringBuilder configuracao = new StringBuilder();

				configuracao.append(cron.getSegundos()).append(" ");
				configuracao.append(cron.getMinutos()).append(" ");
				configuracao.append(cron.getHoras()).append(" ");
				configuracao.append(cron.getDiaMes()).append(" ");
				configuracao.append(cron.getMes()).append(" ");
				configuracao.append(cron.getDiaSemana());

				if (!TSUtil.isEmpty(cron.getAno())) {
					configuracao.append(" ").append(cron.getAno());
				}

				CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(Constantes.CRON_TRIGGER + cron.getId(), Constantes.CRON_GROUP).withSchedule(CronScheduleBuilder.cronSchedule(configuracao.toString())).build();

				sched.scheduleJob(job, trigger);

				sched.start();

			}

		} catch (SchedulerException e) {

			e.printStackTrace();

		}

	}

	@Override
	public void init() throws ServletException {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
