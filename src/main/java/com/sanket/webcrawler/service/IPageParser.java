package com.sanket.webcrawler.service;

import java.util.Map;

import com.sanket.webcrawler.models.Page;

public interface IPageParser {
	
	public Page parsePage(Page page, Map<String, Page> visitedPages);

}
