package br.com.restaurante.jobs;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.restaurante.dao.CronDAO;
import br.com.restaurante.dao.ExecucaoPLSQLDAO;
import br.com.restaurante.model.CronModel;
import br.com.restaurante.util.Constantes;
import br.com.topsys.exception.TSApplicationException;

public class SistemaJob implements Job {
	
	public static final String CRON_ID = "cronId";

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		JobDetail job = arg0.getJobDetail();
		
		JobDataMap jdm = job.getJobDataMap();
		
		Long cronId = (Long)jdm.get(CRON_ID);
        
        CronModel cronModel = new CronDAO().obterCrudModel(new CronModel(cronId));
        
        try {
		
        	if(Constantes.TIPO_CRON_MARCACAO_EXAME.equals(cronModel.getTipoCronModel().getId())){
        		
        		new CronDAO().agendarExames(cronModel);
        		
        	} else if(Constantes.TIPO_CRON_SQL.equals(cronModel.getTipoCronModel().getId())){
        		
        		new ExecucaoPLSQLDAO().executarPLSQL(cronModel.getQuery(), null, null, null, null);
        		
        	}
        	
		} catch (TSApplicationException e) {
			
			e.printStackTrace();
			
		}
            
	}

}
