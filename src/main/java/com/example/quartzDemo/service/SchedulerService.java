package com.example.quartzDemo.service;



import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.quartzDemo.info.TimerInfo;
import com.example.quartzDemo.util.QuartzJobUtil;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SchedulerService {

	private static final Logger log = LoggerFactory.getLogger(SchedulerService.class);

	final private  Scheduler scheduler;

	@Autowired
	public SchedulerService(Scheduler scheduler) {
		super();
		this.scheduler = scheduler;
	}


	public void scheduleJob(final Class jobClass,final TimerInfo info) throws SchedulerException{
		final JobDetail jobDetail = QuartzJobUtil.buildJobDetail(jobClass, info);
		final Trigger trigger;
		if(info.isCronJob())
			trigger = QuartzJobUtil.buildCronTrigger(info, CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);
		else
			trigger = QuartzJobUtil.buildTrigger(jobClass, info);

		try {
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			log.error(e.getMessage(),e);
			throw e;
		}
	}


	public List<TimerInfo> getAllRunningJobs(){
		try{
			return scheduler.getJobKeys(GroupMatcher.anyGroup())
					.stream().map(jobKey -> {
						try {
							final JobDetail jobDetail = scheduler.getJobDetail(jobKey);
							return (TimerInfo)jobDetail.getJobDataMap().get(jobKey.getName());
						}catch (SchedulerException e){
							log.error(e.getMessage(),e);
							return null;
						}
					})
					.filter(Objects::nonNull)
					.collect(Collectors.toList());
		}catch (SchedulerException e){
			log.error(e.getMessage(),e);
			return Collections.emptyList();
		}
	}

	public TimerInfo getRunningJob(final String jobKey){
		try{
			final JobDetail jobDetail = scheduler.getJobDetail(new JobKey(jobKey));
			if(jobDetail == null){
				log.error("Failed to find job with ID '{}'",jobKey);
				return null;
			}
			else{
				return (TimerInfo) jobDetail.getJobDataMap().get(jobKey);
			}
		}catch (SchedulerException e){
			log.error(e.getMessage(),e);
			return null;
		}
	}

	public boolean updateJob(final String jobKey,TimerInfo newJobDetail){
		try{
			final JobDetail jobDetail = scheduler.getJobDetail(new JobKey(jobKey));
			if (jobDetail == null) {
				log.error("[Action: update] Failed to find job with ID '{}'", jobKey);
				return false;
			}
			jobDetail.getJobDataMap().put(jobKey, newJobDetail);
			scheduler.addJob(jobDetail, true, true);
			scheduler.triggerJob(new JobKey(jobKey));
			return true;
		}catch (SchedulerException e){
			log.error("Update failed:: "+e.getMessage(),e);
			return  false;
		}
	}

	public boolean deleteJob(final String jobKey) {
		try {
			return scheduler.deleteJob(new JobKey(jobKey));
		} catch (SchedulerException e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}

	public boolean pauseJob(final String jobKey){
		try{
			final JobDetail jobDetail = scheduler.getJobDetail(new JobKey(jobKey));
			if(jobDetail != null){
				scheduler.pauseJob(new JobKey(jobKey));
				log.info("Job paused with id '{}'",jobKey);
				return true;
			}
			return  false;
		}catch (SchedulerException e){
			log.error("[Action: Pause] Failed , Job '{}' doesn't exist.",jobKey);
			return  false;
		}
	}

	public boolean resumeJob(final String jobKey) {
		try{
			final JobDetail jobDetail = scheduler.getJobDetail(new JobKey(jobKey));
			if(jobDetail != null){
				scheduler.resumeJob(new JobKey(jobKey));
				log.info("Job resumed with id '{}'",jobKey);
				return true;
			}
			else{
				return false;
			}
		}catch (SchedulerException e){
			log.error("[Action: resume] Failed , Job '{}' doesn't exist.",jobKey);
			return false;
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
