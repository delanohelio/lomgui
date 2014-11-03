package com.nanuvem.lom.lomgui.resources;

import java.lang.reflect.Type;

import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.nanuvem.lom.kernel.AttributeValue;

public class AttributeValueSerializer implements JsonSerializer<AttributeValue> {

	@Override
	public JsonElement serialize(AttributeValue attributeValue, Type type,
			JsonSerializationContext context) {
		Gson gson = new GsonBuilder()
		.addSerializationExclusionStrategy(new LomAttributesExclusionStrategy(ImmutableSet.of("instance")))
		.registerTypeAdapter(com.nanuvem.lom.kernel.Class.class, new ClassSerializer())
        .serializeNulls()
        .create();
		return gson.toJsonTree(attributeValue);
	}

}
