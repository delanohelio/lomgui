package com.nanuvem.lom.lomgui.resources;

import java.util.Set;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class LomAttributesExclusionStrategy implements ExclusionStrategy {
	
	private Class<?> classToFilter;
	private Set<String> excludeAttributesName;
	
	public LomAttributesExclusionStrategy(Class<?> clazz, Set<String> attributesName) {
		super();
		this.classToFilter = clazz;
		this.excludeAttributesName = attributesName;
	}
	
	public LomAttributesExclusionStrategy(Set<String> attributesName) {
		super();
		this.excludeAttributesName = attributesName;
	}

	@Override
	public boolean shouldSkipClass(Class<?> clazz) {
		return false;
	}

	@Override
	public boolean shouldSkipField(FieldAttributes attribute) {
		if(classToFilter != null && attribute.getDeclaringClass() != classToFilter)
			return false;
		if(excludeAttributesName.contains(attribute.getName()))
			return true;
		return false;
	}

}
