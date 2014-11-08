package com.nanuvem.lom.lomgui.resources;

import java.util.Set;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class AttributesExclusionStrategy implements ExclusionStrategy {
	
	private Set<String> excludeAttributesName;
	
	public AttributesExclusionStrategy(Set<String> attributesName) {
		super();
		this.excludeAttributesName = attributesName;
	}

	@Override
	public boolean shouldSkipClass(Class<?> clazz) {
		return false;
	}

	@Override
	public boolean shouldSkipField(FieldAttributes attribute) {
		if(excludeAttributesName.contains(attribute.getName()))
			return true;
		return false;
	}

}
