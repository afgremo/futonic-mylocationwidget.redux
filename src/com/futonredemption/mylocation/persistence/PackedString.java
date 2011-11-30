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
		String [] unpacked = splitPattern.split(packedString);
		if(packedString.charAt(packedString.length() - 1) == SEPARATOR) {
			String [] copied = new String[unpacked.length + 1];
			int i;
			int len = unpacked.length;
			for(i = 0; i < len; i++) {
				copied[i] = unpacked[i];
			}
			copied[len] = "";
			unpacked = copied;
		}
		return unpacked;
	}
}
