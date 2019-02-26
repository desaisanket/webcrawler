package com.sanket.webcrawler.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sanket.webcrawler.service.IPageParser;
import com.sanket.webcrawler.service.impl.PageParserImpl;

public class Page {
	
	private String path;
	
	@JsonIgnore
	private String domain;
	
	private String title;
	
	@JsonIgnore
	private Map<String, Page> childPages = new TreeMap<String, Page>();
	
	@JsonIgnore
	private Page parent;
	
	private List<String> files = new ArrayList<>();
	
	private List<String> images = new ArrayList<>();
	
	private List<String> internalLinks = new ArrayList<>();
	
	private List<String> externalLinks = new ArrayList<>();
	
	public Page(String path, String domain) {
		super();
		this.path = path;
		this.domain = domain;
	}

	public Page() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Map<String, Page> getChildPages() {
		return childPages;
	}

	public void setChildPages(Map<String, Page> childPages) {
		this.childPages = childPages;
	}

	public List<String> getFiles() {
		return files;
	}

	public void setFiles(List<String> files) {
		this.files = files;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public Page getParent() {
		return parent;
	}

	public void setParent(Page parent) {
		this.parent = parent;
	}
	
	public void addFile(String fileName) {
		this.files.add(fileName);
	}
	
	public void addImage(String imageName) {
		this.images.add(imageName);
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getInternalLinks() {
		return internalLinks;
	}

	public void setInternalLinks(List<String> internalLinks) {
		this.internalLinks = internalLinks;
	}

	public List<String> getExternalLinks() {
		return externalLinks;
	}

	public void setExternalLinks(List<String> externalLinks) {
		this.externalLinks = externalLinks;
	}

	public void crawl(Map<String, Page> visitedPages) {
		IPageParser pageParser = new PageParserImpl();
		pageParser.parsePage(this, visitedPages);		
		for(Page child : childPages.values()) {
			child.crawl(visitedPages);
		}
	}
	
}
