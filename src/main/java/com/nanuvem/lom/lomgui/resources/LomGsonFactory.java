package com.nanuvem.lom.lomgui.resources;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nanuvem.lom.api.Attribute;
import com.nanuvem.lom.api.AttributeValue;
import com.nanuvem.lom.api.Entity;
import com.nanuvem.lom.api.Instance;

public class LomGsonFactory {

	private Gson entityGson;
	private Gson attributeGson;
	private Gson instanceGson;

	public Gson getEntityGson() {
		if (entityGson == null) {
			entityGson = new GsonBuilder().registerTypeAdapter(Entity.class,
					new EntitySerializer()).create();
		}
		return entityGson;
	}

	public Gson getAttributeGson() {
		if (attributeGson == null) {
			Map<Class<?>, Set<String>> map = new HashMap<Class<?>, Set<String>>();
			map.put(Attribute.class, ImmutableSet.of("entity"));
			attributeGson = new GsonBuilder()
					.addSerializationExclusionStrategy(new FieldsExclusionStrategy(map))
					.serializeNulls().create();
		}
		return attributeGson;
	}

	public Gson getInstanceGson() {
		if (instanceGson == null) {
			Map<Class<?>, Set<String>> map = new HashMap<Class<?>, Set<String>>();
			map.put(Instance.class, ImmutableSet.of("entity"));
			map.put(Attribute.class, ImmutableSet.of("entity"));
			map.put(AttributeValue.class,
					ImmutableSet.of("entity", "instance"));
			instanceGson = new GsonBuilder()
					.addSerializationExclusionStrategy(new FieldsExclusionStrategy(map))
					.serializeNulls().create();
		}
		return instanceGson;
	}
/*
	public static void main(String[] args) {
		Entity entity = new Entity();
		entity.setName("Teste");
		Attribute attribute = new Attribute();
		attribute.setEntity(entity);
		attribute.setName("field");
		entity.setAttributes(Arrays.asList(attribute));
		Instance instance = new Instance();
		instance.setEntity(entity);
		entity.setInstances(Arrays.asList(instance));
		AttributeValue attributeValue = new AttributeValue();
		attributeValue.setAttribute(attribute);
		attributeValue.setInstance(instance);
		attributeValue.setValue("Foo");
		instance.setValues(Arrays.asList(attributeValue));

		Gson gson = new LomGsonFactory().getAttributeGson();
		System.out.println(gson.toJson(attribute));
		gson = new LomGsonFactory().getInstanceGson();
		System.out.println(gson.toJson(instance));
		 gson = new LomGsonFactory().getEntityGson();
		 System.out.println(gson.toJson(entity));

	}
*/
}
