package com.sanket.webcrawler.service.impl;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sanket.webcrawler.models.Page;
import com.sanket.webcrawler.service.IPageParser;

@Service
public class PageParserImpl implements IPageParser {

	private final Logger logger = LoggerFactory.getLogger(PageParserImpl.class);
	
	@Override
	public Page parsePage(Page page, Map<String, Page> visitedPages) {
		// TODO Auto-generated method stub
		Document document = null;
		try {
			if (visitedPages.containsKey(page.getPath())) {
				logger.info("Already visited path: " + page.getPath());
				return page;
			}
			logger.info("Visting Path: " + page.getPath());
			visitedPages.put(page.getPath(), page);
			document = Jsoup.connect(page.getDomain() + page.getPath()).get();
			if (null != document) {
				Elements title = document.getElementsByTag("title");
				if (null != title)
					page.setTitle(title.get(0).text());

				extractLinks(page, document, visitedPages);
				extractImages(page, document);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return page;
	}

	public void extractLinks(Page page, Document document, Map<String, Page> visitedPages) {
		logger.info("Extracting Links for path: " + page.getPath());
		if (null != document) {
			Elements links = document.getElementsByTag("a");
			for (Element link : links) {
				String href = link.attr("href");
				if (!StringUtils.isBlank(href)) {
					if (href.contains(".pdf") || href.contains(".PDF") || href.contains(".doc")
							|| href.contains(".docx") || href.contains(".xls") || href.contains(".xlsx")
							|| href.contains(".ppt") || href.contains(".pptx") || href.contains(".zip") || href.contains(".ics")) {
						extractFileName(page, href);
					} else if ("/".equals(href.charAt(0) + "")) {
						if (!visitedPages.containsKey(href)) {
							Page childPage = new Page(href, page.getDomain());
							childPage.setParent(page);
							page.getChildPages().put(href, childPage);
							page.getInternalLinks().add(href);
						}else {
							page.getInternalLinks().add(href);
						}
					} else if(href.contains("www") || href.contains("http")) {
						page.getExternalLinks().add(href);
					} else {					
						logger.info("Ignoring : " + href);
					}
				}
			}
		}
	}

	public void extractImages(Page page, Document document) {
		logger.info("Extracting images for path: " + page.getPath());
		if (null != document) {
			Elements images = document.getElementsByTag("img");
			for (Element image : images) {
				String source = image.attr("src");
				if (!StringUtils.isBlank(source)) {
					extractImageName(page, source);
				}
			}
		}
	}

	public void extractFileName(Page page, String href) {
		logger.info("Extracting file from path: " + href);
		String fileName = "";
		if (href.contains(".pdf")) {
			fileName = href.substring(href.lastIndexOf("/") + 1, href.lastIndexOf(".pdf") + 4);
		} else if (href.contains(".PDF")) {
			fileName = href.substring(href.lastIndexOf("/") + 1, href.lastIndexOf(".PDF") + 4);
		} else if (href.contains(".doc")) {
			fileName = href.substring(href.lastIndexOf("/") + 1, href.lastIndexOf(".doc") + 4);
		} else if (href.contains(".docx")) {
			fileName = href.substring(href.lastIndexOf("/") + 1, href.lastIndexOf(".docx") + 5);
		} else if (href.contains(".xls")) {
			fileName = href.substring(href.lastIndexOf("/") + 1, href.lastIndexOf(".xls") + 4);
		} else if (href.contains(".xlsx")) {
			fileName = href.substring(href.lastIndexOf("/") + 1, href.lastIndexOf(".xlsx") + 5);
		} else if (href.contains(".zip")) {
			fileName = href.substring(href.lastIndexOf("/") + 1, href.lastIndexOf(".zip") + 4);
		} else if (href.contains(".ppt")) {
			fileName = href.substring(href.lastIndexOf("/") + 1, href.lastIndexOf(".ppt") + 4);
		} else if (href.contains(".pptx")) {
			fileName = href.substring(href.lastIndexOf("/") + 1, href.lastIndexOf(".pptx") + 5);
		}

		if (!StringUtils.isBlank(fileName)) {
			page.addFile(fileName);
		}
	}

	public void extractImageName(Page page, String source) {
		logger.info("Extracting image from path: " + source);
		String imageName = "";
		if (source.contains(".jpg")) {
			imageName = source.substring(source.lastIndexOf("/") + 1, source.lastIndexOf(".jpg") + 4);
		} else if (source.contains(".jpeg")) {
			imageName = source.substring(source.lastIndexOf("/") + 1, source.lastIndexOf(".jpeg") + 5);
		} else if (source.contains(".png")) {
			imageName = source.substring(source.lastIndexOf("/") + 1, source.lastIndexOf(".png") + 4);
		}

		if (!StringUtils.isBlank(imageName)) {
			page.addImage(imageName);
		}
	}
}
