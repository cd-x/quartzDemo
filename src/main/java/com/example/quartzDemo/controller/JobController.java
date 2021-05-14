package com.example.quartzDemo.controller;


import java.util.Arrays;
import java.util.List;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.example.quartzDemo.dao.JokeRepository;
import com.example.quartzDemo.dao.JokeTable;
import com.example.quartzDemo.dao.QuoteTable;
import com.example.quartzDemo.dao.QuoteRepository;
import com.example.quartzDemo.info.TimerInfo;
import com.example.quartzDemo.service.SchedulerService;

@Controller
public class JobController {

	private static final Logger log = LoggerFactory.getLogger(JobController.class);
	private final SchedulerService service;

	private final QuoteRepository quoteRepository;

	private final JokeRepository jokeRepository;
	
	public JobController(final SchedulerService service,
						 final QuoteRepository quoteRepository,
						 final JokeRepository jokeRepository) {
		super();
		this.service = service;
		this.quoteRepository = quoteRepository;
		this.jokeRepository = jokeRepository;
	}

	
	@RequestMapping("/")
	public String homepage(Model model) {
		model.addAttribute("timer", new TimerInfo());

		List<String> jobList = Arrays.asList("Quote", "DadJoke");
        model.addAttribute("jobList", jobList);
        return "home";
	}
	
	
	
	@RequestMapping("/runTimer")
	public String runTimer(@ModelAttribute("timer") TimerInfo info){
		log.info(info.toString());
		String jobPackage = "com.example.quartzDemo.job.";
		Class jobName = null;
		
		try {
			jobName = Class.forName(jobPackage + info.getJobName());
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage(), e);
		}
		try{
			service.scheduleJob(jobName,info);
		}catch (SchedulerException e) {
			log.error(e.getMessage(), e);
		}
//		ModelAndView mv = new ModelAndView();
//		mv.addObject("");
//		mv.setViewName("home");
		return "redirect:/";
	}


	@RequestMapping("/getAllRunningJobs")
	public ModelAndView getAllRunningJobs(ModelAndView modelAndView){
		List<TimerInfo> allRunningJobDetails = service.getAllRunningJobs();
		modelAndView.addObject("allRunningJobDetails",allRunningJobDetails);
		modelAndView.setViewName("allRunningJobs");
		return  modelAndView;
	}


	@RequestMapping("/actions")
    public ModelAndView actions( ModelAndView modelAndView){
        List<TimerInfo> jobList = service.getAllRunningJobs();
	    modelAndView.addObject("jobList",jobList);
	    modelAndView.addObject("noaction",true);
	    modelAndView.addObject("timer",new TimerInfo());
	    modelAndView.setViewName("actions");
	    return modelAndView;
    }

    @RequestMapping(value="/action/{jobKey}", method= RequestMethod.POST, params="action=pause")
    public ModelAndView pause(@PathVariable String jobKey,ModelAndView modelAndView){
		boolean paused = service.pauseJob(jobKey);
        List<TimerInfo> jobList = service.getAllRunningJobs();
        modelAndView.addObject("jobList",jobList);
        modelAndView.addObject("status",paused);
        modelAndView.addObject("message","pause");
	    if(paused)
    	    log.info("[Paused] : "+jobKey);
	    else
	        log.error("[Paused Failed] : "+jobKey +" already in paused state.");

	    modelAndView.setViewName("actions");
	    return modelAndView;
    }

    @RequestMapping(value="/action/{jobKey}", method= RequestMethod.POST, params="action=resume")
    public ModelAndView resume(@PathVariable String jobKey,ModelAndView modelAndView){
        boolean resumed = service.resumeJob(jobKey);
        List<TimerInfo> jobList = service.getAllRunningJobs();
        modelAndView.addObject("jobList",jobList);
		modelAndView.addObject("status",resumed);
		modelAndView.addObject("message","resume");
        if(resumed)
            log.info("[Resumed] : "+jobKey);
        else
            log.error("[Resumed Failed] : "+jobKey +" already in running state.");

        modelAndView.setViewName("actions");
        return modelAndView;
    }

    @RequestMapping(value="/action/{jobKey}", method= RequestMethod.POST, params="action=delete")
    public ModelAndView delete(@PathVariable String jobKey,ModelAndView modelAndView){
        boolean deleted = service.deleteJob(jobKey);
        List<TimerInfo> jobList = service.getAllRunningJobs();
        modelAndView.addObject("jobList",jobList);
		modelAndView.addObject("status",deleted);
		modelAndView.addObject("message","delete");
        if(deleted)
            log.info("[Deleted] : "+jobKey);
        else
            log.error("[Delete Failed] : "+jobKey +" doesn't exist.");

        modelAndView.setViewName("actions");
        return modelAndView;
    }

    @RequestMapping(value="/action/{jobKey}",method = RequestMethod.POST,params = "action=update")
    public ModelAndView update(@PathVariable String jobKey,
                               @ModelAttribute("timer") TimerInfo timerInfo,
                               ModelAndView modelAndView){
		timerInfo.setJobName(jobKey);
		boolean updated = service.updateJob(jobKey,timerInfo);
		List<TimerInfo> jobList = service.getAllRunningJobs();
		modelAndView.addObject("jobList",jobList);
		modelAndView.addObject("status",updated);
		modelAndView.addObject("message","update");

		if(updated)
			log.info("[Updated] : "+jobKey);
		else
			log.error("[Update Failed] : "+jobKey +" doesn't exist.");

		modelAndView.setViewName("actions");
		return modelAndView;
    }

	@RequestMapping("/getAQuote")
	public ModelAndView getAQuote() {
		ModelAndView mv = new ModelAndView();
		QuoteTable quote = quoteRepository.findById(1)
				.orElse(new QuoteTable(1,"Your Quote will appear here.","-Author"));
		
		log.info("Database content with id 1:"+ quote.toString());
		
		mv.addObject("quote", quote.getQuote());
		mv.addObject("author", quote.getAuthor());
		mv.setViewName("developerQuote");
		return mv;
	}
	
	@RequestMapping("/getAJoke")
	public ModelAndView getAJoke() {
		ModelAndView mv = new ModelAndView();
		JokeTable joke = jokeRepository.findById(1)
				.orElse(new JokeTable(1,"Your joke will appear here"));
		
		log.info("From joke table with id as 1 :"+ joke.getContent());
		
		mv.addObject("joke", joke.getContent());
		mv.setViewName("joke");
		return mv;
	}
}
