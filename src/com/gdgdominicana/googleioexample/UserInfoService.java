package com.gdgdominicana.googleioexample;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.http.ContentCodingType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.util.Log;

public class UserInfoService {
	private static final String BASE_URL = "http://userinforestfullconsumer.herokuapp.com";
	
	private static final String TAG = UserInfoService.class.getSimpleName();
	
	public static List<User> getUsers(){
		final String url = BASE_URL + "/users.json";
		
		RestTemplate restTemplate = new RestTemplate();

		restTemplate.getMessageConverters().add(
				new MappingJackson2HttpMessageConverter());

		
		try {
			Log.d(TAG, "Invoking " + url);
			ResponseEntity<User[]> responseEntity = restTemplate.exchange( url, HttpMethod.GET, getRequestEntity(null), User[].class);

			Log.d(TAG, responseEntity.getBody().toString());
			
			return Arrays.asList(responseEntity.getBody());

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static User addUser(User user){
		final String url = BASE_URL + "/users.json";
		
		RestTemplate restTemplate = new RestTemplate();

		restTemplate.getMessageConverters().add(
				new MappingJackson2HttpMessageConverter());

		HttpHeaders httpHeaders = getRequestEntity(MediaType.APPLICATION_JSON).getHeaders();
		HttpEntity<User> requestEntity = new HttpEntity<User>(user, httpHeaders);		
		
		
		try {
			Log.d(TAG, "Invoking " + url);
			ResponseEntity<User> responseEntity = restTemplate.exchange( url, HttpMethod.POST, requestEntity, User.class);

			Log.d(TAG, responseEntity.getBody().toString());
			
			return responseEntity.getBody();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static boolean removeUser(User user){
		final String url = BASE_URL + "/users/{userId}.json";
		
		RestTemplate restTemplate = new RestTemplate();

		restTemplate.getMessageConverters().add(
				new MappingJackson2HttpMessageConverter());

		
		try {
			Log.d(TAG, "Invoking " + url);
			ResponseEntity<Void> responseEntity = restTemplate.exchange( url, HttpMethod.DELETE, getRequestEntity(null), Void.class, user.getId());

			Log.d(TAG, responseEntity.getHeaders().toString());
			Log.d(TAG, responseEntity.getBody().toString());
			
			return responseEntity.getStatusCode() == HttpStatus.NO_CONTENT;
			

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	
	private static HttpEntity<?> getRequestEntity(MediaType contentType) {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAcceptEncoding(ContentCodingType.GZIP);
		Charset utf8 = Charset.forName("UTF-8");
		
		requestHeaders.setAccept(Collections.singletonList(new MediaType(
				"application", "json", utf8)));
		
		
		if(null != contentType){
			requestHeaders.setContentType(contentType);
		}

		return new HttpEntity<Object>(requestHeaders);
	}
	
	
	
}
