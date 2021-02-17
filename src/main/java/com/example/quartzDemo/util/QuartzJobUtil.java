package com.example.quartzDemo.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;


import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.quartzDemo.info.TimerInfo;
import com.example.quartzDemo.job.DadJoke;

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
				.withIntervalInMilliseconds(info.getRepeatIntervalMs());

		if(info.isRunForever()) {
			builder = builder.repeatForever();
		}
		else {
			builder = builder.withRepeatCount(info.getTotalFireCount()-1);
		}

		Date startTime = info.getStartDate();
		Date endTime = info.getEndDate();

		log.info("system time: "+ LocalDateTime.now() + " collected start time: "+startTime);

		return TriggerBuilder
				.newTrigger()
				.withIdentity(jobClass.getSimpleName()).forJob(jobClass.getSimpleName())
				.withSchedule(builder)
				.startAt(startTime).endAt(endTime)
				.build();
	}
}
