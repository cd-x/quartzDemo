package com.example.quartzDemo.job;

import java.util.Collections;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.quartzDemo.dao.JokeResponse;
import com.example.quartzDemo.dao.JokeRepository;
import com.example.quartzDemo.dao.JokeTable;
import com.example.quartzDemo.service.SchedulerService;

public class DadJoke implements Job{
	private static final Logger log = LoggerFactory.getLogger(DadJoke.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private HttpHeaders headers;

	@Autowired
	private JokeRepository repo;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		try {


			//https://www.randomjoke.com/topic/nerd.php   https://icanhazdadjoke.com/
			String url = "https://icanhazdadjoke.com/";
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			HttpEntity<String> entity = new HttpEntity<>("body", headers);

			JokeResponse pageContent = restTemplate.exchange(url, HttpMethod.GET, entity, JokeResponse.class).getBody();

			log.info("Received joke api Response : "+pageContent);

			JokeTable tableData = repo.findById(1).orElse(new JokeTable(1,"your joke will appear here"));
			tableData.setContent(pageContent.getJoke());
			repo.save(tableData);


		} catch (Exception e) {
			log.error("Unable to call joke API : "+e.getMessage(), e);
			throw e;
		}

	}
}
