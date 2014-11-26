package com.nanuvem.lom.lomgui.resources;

import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nanuvem.lom.api.AttributeValue;
import com.nanuvem.lom.api.Entity;

public class LomGsonFactory {

	private Gson entityGson;
	private Gson attributeGson;
	private Gson instanceGson;
	private Gson simpleInstanceGson;

	public Gson getEntityGson() {
		if (entityGson == null) {
			entityGson = new GsonBuilder().registerTypeAdapter(
					Entity.class, new EntitySerializer())
					.create();
		}
		return entityGson;
	}

	public Gson getAttributeGson() {
		if (attributeGson == null) {
			attributeGson = new GsonBuilder()
					.registerTypeAdapter(Entity.class,
							new EntitySerializer()).serializeNulls().create();
		}
		return attributeGson;
	}

	public Gson getInstanceGson() {
		if (instanceGson == null) {
			instanceGson = new GsonBuilder()
					.registerTypeAdapter(Entity.class,
							new EntitySerializer())
					.registerTypeAdapter(AttributeValue.class,
							new AttributeValueSerializer()).create();
		}
		return instanceGson;
	}

	public Gson getSimpleInstanceGson() {
		if (simpleInstanceGson == null) {
			simpleInstanceGson = new GsonBuilder()
					.registerTypeAdapter(Entity.class,
							new EntitySerializer())
					.registerTypeAdapter(
							AttributeValue.class,
							new AttributeValueSerializer(
									new AttributesExclusionStrategy(
											ImmutableSet
													.of("instance", "entity"))))
					.create();
		}
		return simpleInstanceGson;
	}

}
