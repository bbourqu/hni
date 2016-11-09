package org.hni.admin.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageTimerFilter implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(PageTimerFilter.class);
	
	@Override
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		long start = System.currentTimeMillis();

		try {
			chain.doFilter(request, response);
		} finally {
			String url = ((HttpServletRequest)request).getRequestURL().toString();					
			String queryString = (null == ((HttpServletRequest)request).getQueryString()) ? "" : "?"+((HttpServletRequest)request).getQueryString();	
			logger.info(String.format("[%d ms] - %s%s", System.currentTimeMillis()-start,url, queryString));
		}
	}
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
