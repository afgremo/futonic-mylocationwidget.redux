package com.futonredemption.mylocation.google.urlshortener;

import java.io.IOException;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.urlshortener.Urlshortener;
import com.google.api.services.urlshortener.Urlshortener.Url.Insert;
import com.google.api.services.urlshortener.model.Url;

public class GooglShortener {

	private Urlshortener createShortener() {
		Urlshortener.Builder builder;
		HttpTransport httpTransport = new NetHttpTransport();
		JsonFactory jsonFactory = new GsonFactory();
		builder = Urlshortener.builder(httpTransport, jsonFactory);
		builder.setApplicationName("My Location");
		builder.setJsonHttpRequestInitializer(new UrlshortenerRequestInitializer());
		return builder.build();
	}
	
	public String shortenUrl(String longUrl) throws IOException {
		
		Urlshortener shortener = createShortener();
		Url url = new Url();
		url.setLongUrl(longUrl);
		
		Insert ins = shortener.url().insert(url);
		Url newUrl = ins.execute();
		String shortUrl = newUrl.getId();
		return shortUrl;
	}
}
