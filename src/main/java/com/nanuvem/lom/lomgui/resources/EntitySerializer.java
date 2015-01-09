package com.nanuvem.lom.lomgui.resources;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.nanuvem.lom.api.Attribute;
import com.nanuvem.lom.api.AttributeValue;
import com.nanuvem.lom.api.Entity;
import com.nanuvem.lom.api.Instance;

public class EntitySerializer implements JsonSerializer<Entity> {

	@Override
	public JsonElement serialize(Entity entity, Type type,
			JsonSerializationContext context) {
		Map<Class<?>, Set<String>> map = new HashMap<Class<?>, Set<String>>();
		map.put(Attribute.class, ImmutableSet.of("entity"));
		map.put(Instance.class, ImmutableSet.of("entity"));
		map.put(AttributeValue.class, ImmutableSet.of("entity", "instance"));
		Gson gson = new GsonBuilder()
		.addSerializationExclusionStrategy(new FieldsExclusionStrategy(map))
								.serializeNulls().create();
		JsonElement jsonElement = gson.toJsonTree(entity);
		jsonElement.getAsJsonObject().addProperty("fullName",
				entity.getFullName());
		return jsonElement;
	}
	
}
