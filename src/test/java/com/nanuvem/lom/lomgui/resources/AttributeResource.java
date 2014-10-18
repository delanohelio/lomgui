package com.nanuvem.lom.lomgui.resources;

import java.lang.reflect.Type;
import java.util.List;

import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.nanuvem.lom.kernel.Attribute;
import com.nanuvem.restest.TypedSubResource;

public class AttributeResource extends TypedSubResource<Attribute> {

	private static final String ROOTURL = "http://localhost:8080/lomgui/api/data/class";
	private static final String ATTRIBUTESURL = "attributes";
	private Gson gson;


	public AttributeResource() {
		super(ROOTURL, ATTRIBUTESURL);
		gson = new GsonBuilder()
        .setExclusionStrategies(new LomAttributesExclusionStrategy(ImmutableSet.of("clazz")))
        .serializeNulls()
        .create();
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
