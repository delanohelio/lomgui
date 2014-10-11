package com.nanuvem.lom.lomgui.resources;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nanuvem.lom.kernel.Instance;
import com.nanuvem.restest.TypedResource;

public class InstanceResource extends TypedResource<Instance> {
	
	private static final String INSTANCES = "http://localhost:8080/lomgui/api/data/instance";
	private Gson gson;
	
	public InstanceResource() {
		super(INSTANCES);
	}

	@Override
	protected String toJson(Instance instance) {
		return gson.toJson(instance);
	}

	@Override
	protected List<Instance> toList(String json) {
		Type collectionType = new TypeToken<List<Instance>>(){}.getType();
		return gson.fromJson(json, collectionType);
	}

	@Override
	protected Instance toObject(String json) {
		return gson.fromJson(json, Instance.class);
	}

}
