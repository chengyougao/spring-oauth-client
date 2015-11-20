package com.andaily.springoauth.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;

import org.apache.commons.lang.StringUtils;

/**
 * @author Shengzhao Li
 */
public abstract class WebUtils {

	private WebUtils() {
	}

	/*
	 * Save state to ServletContext, key = value = state
	 */
	public static void saveState(HttpServletRequest request, String state) {
		final ServletContext servletContext = request.getSession().getServletContext();
		servletContext.setAttribute(state, state);
	}

	/*
	 * Validate state when callback from Oauth Server. If validation successful,
	 * will remove it from ServletContext.
	 */
	public static boolean validateState(HttpServletRequest request, String state) {
		if (StringUtils.isEmpty(state)) {
			return false;
		}
		final ServletContext servletContext = request.getSession().getServletContext();
		final Object value = servletContext.getAttribute(state);

		if (value != null) {
			servletContext.removeAttribute(state);
			return true;
		}
		return false;
	}

	public static void writeJson(HttpServletResponse response, JSON json) {
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter writer = response.getWriter();
			json.write(writer);
			writer.flush();
		} catch (IOException e) {
			throw new IllegalStateException("Write json to response error", e);
		}

	}

	/*
	 * 链接url
	 */
	public static void loadUrl(String url) {
		URL urlObject = null;
		try {
			urlObject = new URL(url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			URLConnection uc = urlObject.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * 获得url返回数据
	 */
	public static String loadJson(String url) {
		StringBuilder json = new StringBuilder();
		try {
			URL urlObject = new URL(url);
			URLConnection uc = urlObject.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
			String inputLine = null;
			while ((inputLine = in.readLine()) != null) {
				json.append(inputLine);
			}
			in.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json.toString();
	}

}
