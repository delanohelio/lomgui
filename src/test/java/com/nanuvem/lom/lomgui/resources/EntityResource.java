package com.nanuvem.lom.lomgui.resources;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nanuvem.lom.api.Entity;
import com.nanuvem.restest.TypedResource;

public class EntityResource extends TypedResource<Entity> {

	private static final String ENTITIES = "http://localhost:8080/lomgui/api/data/entity";
	private Gson gson;
	

	public EntityResource() {
		super(ENTITIES);
		gson = new LomGsonFactory().getEntityGson();
	}

	@Override
	protected String toJson(Entity entity) {
		return gson.toJson(entity);
	}

	@Override
	protected List<Entity> toList(String json) {
		Type collectionType = new TypeToken<List<Entity>>(){}.getType();
		return gson.fromJson(json, collectionType);
	}

	@Override
	protected Entity toObject(String json) {
		return gson.fromJson(json, Entity.class);
	}

}
