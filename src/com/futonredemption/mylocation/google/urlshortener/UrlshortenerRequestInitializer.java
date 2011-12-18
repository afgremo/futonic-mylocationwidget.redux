package com.futonredemption.mylocation.google.urlshortener;

import com.google.api.client.http.json.JsonHttpRequest;
import com.google.api.client.http.json.JsonHttpRequestInitializer;
import com.google.api.services.urlshortener.UrlshortenerRequest;

class UrlshortenerRequestInitializer implements JsonHttpRequestInitializer {
	private static final String APIKEY = "AIzaSyDKWzASbbywLp8IqesyaOdPRLkSyiQfWGQ";
	
    public void initialize(JsonHttpRequest request) {
      UrlshortenerRequest urlshortenerRequest = (UrlshortenerRequest)request;
      urlshortenerRequest.setPrettyPrint(true);
      urlshortenerRequest.setKey(APIKEY);
  }
}
