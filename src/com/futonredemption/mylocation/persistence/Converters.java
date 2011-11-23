package com.futonredemption.mylocation.persistence;

import java.util.Locale;

public class Converters {

	public static Locale fromString(String localeString) {
		final String [] entries = PackedString.unpack(localeString);
		final String language = entries[0];
		final String country = entries[1];
		final String variant = entries[2];
		
		return new Locale(language, country, variant);
	}
	public static String toString(Locale locale) {
		
		PackedString result = new PackedString();
		result.put(locale.getLanguage());
		result.put(locale.getCountry());
		result.put(locale.getVariant());
		return result.toString();
	}
}
