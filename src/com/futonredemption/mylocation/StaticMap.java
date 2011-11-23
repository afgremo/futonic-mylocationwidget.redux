package com.futonredemption.mylocation;

public class StaticMap {

	private String url;
	private String filePath;
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getFilePath() { 
		return filePath;
	}
	
	public String getUrl() {
		return url;
	}
}
