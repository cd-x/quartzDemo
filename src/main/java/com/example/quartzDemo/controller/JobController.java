package com.example.quartzDemo.controller;


import java.util.Arrays;
import java.util.List;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
        return "home2";
	}
	
	
	
	@RequestMapping("/runTimer")
	public ModelAndView runTimer(@ModelAttribute("timer") TimerInfo info){
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
		ModelAndView mv = new ModelAndView();
		mv.setViewName("success");
		return mv;
	}


	@RequestMapping("/getAllRunningJobs")
	public ModelAndView getAllRunningJobs(ModelAndView modelAndView){
		List<TimerInfo> allRunningJobDetails = service.getAllRunningJobs();
		modelAndView.addObject("allRunningJobDetails",allRunningJobDetails);
		modelAndView.setViewName("allRunningJobs");
		return  modelAndView;
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
