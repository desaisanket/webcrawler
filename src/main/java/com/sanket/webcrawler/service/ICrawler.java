package com.sanket.webcrawler.service;

import com.sanket.webcrawler.models.Page;

public interface ICrawler {

	public Page initiateCrawling(String domain);
}
