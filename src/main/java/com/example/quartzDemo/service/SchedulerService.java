package com.example.quartzDemo.service;



import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.example.quartzDemo.info.CronJobInfo;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.CronTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.quartzDemo.info.TimerInfo;
import com.example.quartzDemo.util.QuartzJobUtil;

@Service
public class SchedulerService {

	private static final Logger log = LoggerFactory.getLogger(SchedulerService.class);

	private  Scheduler scheduler;

	@Autowired
	public SchedulerService(Scheduler scheduler) {
		super();
		this.scheduler = scheduler;
	}



	public void schedule(final Class jobClass,final TimerInfo info){
		final JobDetail jobDetail = QuartzJobUtil.buildJobDetail(jobClass, info);
		final Trigger trigger = QuartzJobUtil.buildTrigger(jobClass, info);

		try {
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			log.error(e.getMessage(),e);
		}
	}

	public void scheduleCronJOb(final Class jobClass, final CronJobInfo cronJobInfo){
		final JobDetail jobDetail = QuartzJobUtil.buildCronJobDetail(jobClass,cronJobInfo);
		final Trigger trigger = (Trigger) QuartzJobUtil.buildCronTrigger(cronJobInfo, CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);
		try {
			scheduler.scheduleJob(jobDetail,trigger);
		}
		catch (SchedulerException e){
			log.error(e.getMessage(),e);
		}
	}

	@PostConstruct
	public void init() {
		try {
			scheduler.start();
		} catch (SchedulerException e) {
			log.error(e.getMessage(), e);
		}
	}

	@PreDestroy
	public void preDestroy() {
		try {
			scheduler.shutdown();
		} catch (SchedulerException e) {
			log.error(e.getMessage());
		}
	}
}
