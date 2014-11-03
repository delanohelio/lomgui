package com.nanuvem.lom.lomgui.resources;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nanuvem.lom.kernel.Class;
import com.nanuvem.restest.TypedResource;

public class ClassResource extends TypedResource<Class> {

	private static final String CLASSES = "http://localhost:8080/lomgui/api/data/class";
	private Gson gson;
	

	public ClassResource() {
		super(CLASSES);
		gson = new LomGsonFactory().getClassGson();
	}

	@Override
	protected String toJson(Class clazz) {
		return gson.toJson(clazz);
	}

	@Override
	protected List<Class> toList(String json) {
		Type collectionType = new TypeToken<List<Class>>(){}.getType();
		return gson.fromJson(json, collectionType);
	}

	@Override
	protected Class toObject(String json) {
		return gson.fromJson(json, Class.class);
	}

}
