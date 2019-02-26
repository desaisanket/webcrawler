package com.sanket.webcrawler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sanket.webcrawler.service.ICrawler;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebcrawlerApplicationTests {

	@Autowired
	private ICrawler crawler;
	
	@Test
	public void iCrawlerTest() {
		crawler.initiateCrawling("https://www.prudential.co.uk");	
	}
	
	
	

}
