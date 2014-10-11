package com.nanuvem.lom.lomgui.resources;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nanuvem.lom.kernel.Attribute;
import com.nanuvem.restest.TypedResource;

public class AttributeResource extends TypedResource<Attribute>  {

	private static final String ATTRIBUTES = "http://localhost:8080/lomgui/api/data/attribute";
	private Gson gson;


	public AttributeResource() {
		super(ATTRIBUTES);
		gson = new Gson();
	}

	@Override
	protected String toJson(Attribute attribute) {
		return gson.toJson(attribute);
	}

	@Override
	protected List<Attribute> toList(String json) {
		Type collectionType = new TypeToken<List<Attribute>>(){}.getType();
		return gson.fromJson(json, collectionType);
	}

	@Override
	protected Attribute toObject(String json) {
		return gson.fromJson(json, Attribute.class);
	}

}
