package com.example.quartzDemo.job;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.example.quartzDemo.dao.QuoteResponse;
import com.example.quartzDemo.dao.QuoteTable;
import com.example.quartzDemo.service.SchedulerService;
import com.example.quartzDemo.dao.QuoteRepository;

public class Quote implements Job{
	private static final Logger log = LoggerFactory.getLogger(Quote.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private QuoteRepository quoteRepository;
	

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		try {
			
			String url="http://quotes.stormconsultancy.co.uk/random.json"; 
			//https://api.quotable.io/random   https://type.fit/api/quotes
			//https://api.whatdoestrumpthink.com/api/v1/quotes/random
			QuoteResponse quote = restTemplate.getForObject(url, QuoteResponse.class);
			QuoteTable quoteToBeUpdated = quoteRepository.findById(1).orElse(new QuoteTable(1,"Your quote will appear here","rishi"));
			quoteToBeUpdated.setQuote(quote.getQuote());
			quoteToBeUpdated.setAuthor(quote.getAuthor());
			quoteRepository.save(quoteToBeUpdated);
			log.info("Received quote API response : "+quote.toString());
			
		} catch (Exception e) {
			log.error("Unable to call Quote API : "+e.getMessage() , e);
		}
	
	}
}
