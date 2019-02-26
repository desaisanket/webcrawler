package com.sanket.webcrawler.service.impl;

import java.io.FileOutputStream;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanket.webcrawler.models.Page;
import com.sanket.webcrawler.service.ICrawler;


@Service
public class CrawlerImpl implements ICrawler {

	private final Logger logger = LoggerFactory.getLogger(CrawlerImpl.class);
	 
	private Map<String, Page> visitedPages = new TreeMap<String, Page>();

	@Override
	public Page initiateCrawling(String domain) {
		// TODO Auto-generated method stub
		logger.info("Initiating crawling for: " + domain);
		
		Page root = new Page();
		root.setPath("/");
		root.setDomain(domain);
		root.crawl(visitedPages);

		ObjectMapper mapper = new ObjectMapper();
		try {
			FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/output.json");
			logger.info("Writing data to output.json");
			fileOutputStream.write(mapper.writeValueAsString(visitedPages.values()).getBytes());
			fileOutputStream.close();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return root;
	}

}
