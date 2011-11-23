package com.futonredemption.mylocation.persistence;

import java.util.regex.Pattern;

public class PackedString {

	final static char SEPARATOR = 30;
	final static Pattern splitPattern;
	static {
		splitPattern = Pattern.compile("\u001e");
	}

	final StringBuilder sb = new StringBuilder();
	
	public void put(String field) {
		if(sb.length() > 0) {
			sb.append(SEPARATOR);
		}
		sb.append(field);
	}
	
	@Override
	public String toString() {
		return sb.toString();
	}
	
	public static String[] unpack(String packedString) {
		return splitPattern.split(packedString);
	}
}
