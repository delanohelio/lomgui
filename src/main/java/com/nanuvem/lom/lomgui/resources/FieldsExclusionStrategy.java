package com.nanuvem.lom.lomgui.resources;

import java.util.Map;
import java.util.Set;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class FieldsExclusionStrategy implements ExclusionStrategy {

	private Map<Class<?>, Set<String>> excludeFields;

	public FieldsExclusionStrategy(Map<Class<?>, Set<String>> map) {
		super();
		this.excludeFields = map;
	}

	@Override
	public boolean shouldSkipField(FieldAttributes f) {
		if(excludeFields.containsKey(f.getDeclaringClass()))
			if(excludeFields.get(f.getDeclaringClass()).contains(f.getName()))
				return true;
		return false;
	}

	@Override
	public boolean shouldSkipClass(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

}
