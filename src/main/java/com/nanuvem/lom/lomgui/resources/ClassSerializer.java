package com.nanuvem.lom.lomgui.resources;

import java.lang.reflect.Type;

import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.nanuvem.lom.kernel.Class;

public class ClassSerializer implements JsonSerializer<Class> {

	@Override
	public JsonElement serialize(Class clazz, Type type,
			JsonSerializationContext context) {
		Gson gson = new GsonBuilder()
        .setExclusionStrategies(new AttributesExclusionStrategy(ImmutableSet.of("instances", "attributes")))
        .serializeNulls()
        .create();
		JsonElement jsonElement = gson.toJsonTree(clazz);
		jsonElement.getAsJsonObject().addProperty("fullName", clazz.getFullName());
		return jsonElement;
	}

}
