package cn.thinkit.util;

import com.fasterxml.jackson.core.JsonFactory;

public class MyJsonFactory {

	private MyJsonFactory() {
		// null
	}
	public static final   JsonFactory factory = new JsonFactory().disable(JsonFactory.Feature.INTERN_FIELD_NAMES);
}
