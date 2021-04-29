package com.example.quartzDemo.controller;

import com.example.quartzDemo.dao.JokeRepository;
import com.example.quartzDemo.dao.JokeTable;
import com.example.quartzDemo.dao.QuoteRepository;
import com.example.quartzDemo.dao.QuoteTable;
import com.example.quartzDemo.info.TimerInfo;
import com.example.quartzDemo.service.SchedulerService;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class JobRestController {

    private  static  final Logger log = LoggerFactory.getLogger(JobRestController.class);
    private final JokeRepository jokeRepository;
    private final QuoteRepository quoteRepository;
    private final SchedulerService service;

    public JobRestController(final JokeRepository jokeRepository,
                             final QuoteRepository quoteRepository,
                             final SchedulerService service) {

        this.jokeRepository = jokeRepository;
        this.quoteRepository = quoteRepository;
        this.service = service;
    }

    @PostMapping("/startNewJob")
    public String startNewJob(@RequestBody TimerInfo info){
        log.info(info.toString());
        String jobPackage = "com.example.quartzDemo.job.";
        Class jobName = null;

        try {
            jobName = Class.forName(jobPackage + info.getJobName());
            service.scheduleJob(jobName,info);
            return "Scheduled";
        } catch (ClassNotFoundException | SchedulerException e) {
            log.error(e.getMessage(), e);
            return  "Failed to schedule";
        }
    }

    @GetMapping("/getAJoke")
    public String getAJoke(){
        JokeTable joke = jokeRepository.findById(1)
                .orElse(new JokeTable(1,"Your joke will appear here"));

        log.info("From joke table with id as 1 :"+ joke.getContent());
        return joke.toString();
    }

    @GetMapping("/getAQuote")
    public String getAQuote(){
        QuoteTable quote = quoteRepository.findById(1)
                .orElse(new QuoteTable(1,"Your Quote will appear here","- Author"));
        log.info("From Quote Table with id 1 is: "+quote.toString());
        return quote.toString();
    }

    @GetMapping("/getAllRunningJobs")
    public List<TimerInfo> getAllRunningJobs(){
        return service.getAllRunningJobs();
    }

    @GetMapping("/getRunningJob/{jobKey}")
    public TimerInfo getRunningJob(@PathVariable String jobKey){
        return service.getRunningJob(jobKey);
    }

    @PutMapping("/updateJob/{jobKey}")
    public boolean updateJob(@PathVariable final String jobKey,@RequestBody TimerInfo newJobDetail){
        return service.updateJob(jobKey,newJobDetail);
    }

    @DeleteMapping("/deleteJob/{jobKey}")
    public boolean deleteJob(@PathVariable String jobKey){
        return service.deleteJob(jobKey);
    }

    @PutMapping("/pauseJob/{jobKey}")
    public boolean pauseJob(@PathVariable String jobKey){
        return service.pauseJob(jobKey);
    }

    @PutMapping("/resumeJob/{jobKey}")
    public boolean resumeJob(@PathVariable String jobKey){
        return service.resumeJob(jobKey);
    }

}
