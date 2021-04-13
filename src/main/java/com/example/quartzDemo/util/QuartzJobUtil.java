package com.example.quartzDemo.util;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;


import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.quartzDemo.info.TimerInfo;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;

public class QuartzJobUtil {

	private static final Logger log = LoggerFactory.getLogger(QuartzJobUtil.class);

	@Autowired
	private QuartzJobUtil() {
		// TODO Auto-generated constructor stub
	}



	public static JobDetail buildJobDetail(final Class jobClass, final TimerInfo info) {

		final JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put(jobClass.getSimpleName(), info);

		return JobBuilder.newJob(jobClass)
				.withIdentity(jobClass.getSimpleName())
				.setJobData(jobDataMap)
				.build();
	}

	public static Trigger buildTrigger(final Class jobClass, final TimerInfo info){
		SimpleScheduleBuilder builder = SimpleScheduleBuilder
				.simpleSchedule().withMisfireHandlingInstructionFireNow()
				.withIntervalInSeconds(info.getRepeatInterval());

		if(info.isRunForever()) {
			builder = builder.repeatForever();
		}
		else {
			builder = builder.withRepeatCount(info.getTotalFireCount()-1);
		}

		Date startTime = info.getStartDate();
		Date endTime = info.getEndDate();

		log.info("system time: "+ LocalDateTime.now() + " collected start time: "+startTime);

		Trigger trigger;
		if(info.isRunForever()){
			trigger = TriggerBuilder
					.newTrigger()
					.withIdentity(jobClass.getSimpleName())
					.forJob(jobClass.getSimpleName())
					.withSchedule(builder)
					.startAt(startTime)
					.build();
		}
		else {
			trigger = TriggerBuilder
					.newTrigger()
					.withIdentity(jobClass.getSimpleName()).forJob(jobClass.getSimpleName())
					.withSchedule(builder)
					.startAt(startTime).endAt(endTime)
					.build();
		}
		return trigger;
	}


	public static CronTrigger buildCronTrigger(final TimerInfo cronJobInfo,int misfireInstruction){
		CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
		cronTriggerFactoryBean.setCronExpression(cronJobInfo.getCronExpression());
		cronTriggerFactoryBean.setStartTime(cronJobInfo.getStartDate());
		cronTriggerFactoryBean.setName(cronJobInfo.getJobName());
		cronTriggerFactoryBean.setMisfireInstruction(misfireInstruction);
		try{
			cronTriggerFactoryBean.afterPropertiesSet();
			log.info("CronTgrigger created.");
		}
		catch (ParseException e){
			log.error(e.getMessage(),e);
		}
		return (CronTrigger) cronTriggerFactoryBean.getObject();
	}
}
