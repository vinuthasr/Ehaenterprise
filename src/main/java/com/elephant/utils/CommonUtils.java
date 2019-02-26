package com.elephant.utils;
import java.io.IOException;
import java.security.MessageDigest;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.aspectj.apache.bcel.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.elephant.constant.StatusCode;
import com.elephant.response.ErrorObject;
import com.elephant.response.Response;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


public class CommonUtils implements Constants {

	static ObjectMapper mapper = new ObjectMapper();
	private static String addStr = "globalPORTALAccessKEY";
	private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);
	private static HttpSession session = null;

	public static String encriptString(String strToEncript) {

		String returnString = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(strToEncript.getBytes());
			byte[] bytes = md.digest();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			returnString = sb.toString();
			return returnString;
		} catch (Exception ex) {
			ex.printStackTrace();
			return returnString;
		}
	}
	
	

	public static String encriptURL(String urlString) {

		Hashtable<String, String> map = new Hashtable<String, String>();
		map.put("url", urlString);
		map.put("accessKey", addStr);

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(map.toString().getBytes());
			byte[] bytes = md.digest();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			return sb.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static String generateRandomId() {
		return UUID.randomUUID().toString();
	}

	public static String getJson(Object obj) {
		try {
			mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			logger.error("getJsonResponse:Error in json processing: ", e);
		}
		return "";
	}

	public static Object getObject(String str) throws IOException {
		try {
			return mapper.readValue(str, Object.class);
		} catch (JsonProcessingException e) {
			logger.error("getJsonResponse:Error in json processing: ", e);
		}
		return "";
	}

	public static Response getResponseObject(String message) {
		Response response = new Response();
		response.setStatus(StatusCode.SUCCESS.name());
		response.setMessage(message);
		return response;
	}

	public static ErrorObject getErrorResponse(String title, String detail) {
		ErrorObject errorObject = new ErrorObject();
		errorObject.setTitle(title);
		errorObject.setDetail(detail);
		return errorObject;
	}

	public static <K, V extends Comparable<? super V>> List<Entry<K, V>> entriesSortedByValues(Map<K, V> map) {
		List<Entry<K, V>> sortedEntries = new ArrayList<Entry<K, V>>(map.entrySet());
		Collections.sort(sortedEntries, new Comparator<Entry<K, V>>() {
			public int compare(Entry<K, V> e1, Entry<K, V> e2) {
				return e2.getValue().compareTo(e1.getValue());
			}
		});
		return sortedEntries;
	}

	public static List<Integer> getMonthYearSeries(int start, int end) {
		int series = start;
		List<Integer> list = new LinkedList<Integer>();
		while (!(series == end)) {
			list.add(series);
			if (series / 10000 == 12) {
				series = series + 1;
				series = series - 110000;
			} else {
				series = series + 10000;
			}
		}
		list.add(end);
		return list;
	}

	public static HttpSession geSsession() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		if (session == null)
			session = attr.getRequest().getSession(true);
		return session;
	}

	public static String getUserId(String userName) {
		HttpSession session = CommonUtils.geSsession();
		return (String) session.getAttribute(userName);
	}

	public static String getBlobData(Blob blob) {
		byte[] bdata = null;
		try {
			bdata = blob.getBytes(1, (int) blob.length());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String(bdata);
	}

}
