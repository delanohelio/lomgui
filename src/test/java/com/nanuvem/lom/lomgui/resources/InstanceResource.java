package com.nanuvem.lom.lomgui.resources;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nanuvem.lom.kernel.Instance;
import com.nanuvem.restest.TypedSubResource;

public class InstanceResource extends TypedSubResource<Instance> {
	
	private static final String ROOTURL = "http://localhost:8080/lomgui/api/data/class";
	private static final String INSTANCESURL = "instances";
	private Gson gson;
	
	public InstanceResource() {
		super(ROOTURL, INSTANCESURL);
		gson = new LomGsonFactory().getInstanceGson();
	}

	@Override
	protected String toJson(Instance instance) {
		String json = gson.toJson(instance);
		return json;
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
