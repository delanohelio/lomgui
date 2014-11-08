package com.nanuvem.lom.lomgui.resources;

import java.lang.reflect.Type;

import com.google.common.collect.ImmutableSet;
import com.google.gson.ExclusionStrategy;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.nanuvem.lom.kernel.AttributeValue;

public class AttributeValueSerializer implements JsonSerializer<AttributeValue> {

	private ExclusionStrategy exclusionStrategy;
	
	public AttributeValueSerializer() {
		super();
	}
	
	public AttributeValueSerializer(ExclusionStrategy exclusionStrategy) {
		super();
		this.exclusionStrategy = exclusionStrategy;
	}
	
	@Override
	public JsonElement serialize(AttributeValue attributeValue, Type type,
			JsonSerializationContext context) {
		GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls();
		if(exclusionStrategy != null){
			gsonBuilder.addSerializationExclusionStrategy(exclusionStrategy);
		}else{
			gsonBuilder.addSerializationExclusionStrategy(new AttributesExclusionStrategy(ImmutableSet.of("instance")))
			.registerTypeAdapter(com.nanuvem.lom.kernel.Class.class, new ClassSerializer());
		}
		return gsonBuilder.create().toJsonTree(attributeValue);
	}

}
