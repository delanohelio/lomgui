package com.nanuvem.lom.lomgui.resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nanuvem.lom.kernel.AttributeValue;

public class LomGsonFactory {
	
	private Gson classGson;
	private Gson attributeGson;
	private Gson instanceGson;
	
	public Gson getClassGson() {
		if(classGson == null) {
			classGson = new GsonBuilder()
	        .registerTypeAdapter(com.nanuvem.lom.kernel.Class.class, new ClassSerializer())
	        .create();
		}
		return classGson;
	}
	public Gson getAttributeGson() {
		if(attributeGson == null) {
			attributeGson = new GsonBuilder()
	        .registerTypeAdapter(com.nanuvem.lom.kernel.Class.class, new ClassSerializer())
	        .serializeNulls()
	        .create();
		}
		return attributeGson;
	}
	public Gson getInstanceGson() {
		if(instanceGson == null) {
			instanceGson = new GsonBuilder()
			.registerTypeAdapter(com.nanuvem.lom.kernel.Class.class, new ClassSerializer())
			.registerTypeAdapter(AttributeValue.class, new AttributeValueSerializer())
	        .create();
		}
		return instanceGson;
	}
	
}
