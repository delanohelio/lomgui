package com.nanuvem.lom.lomgui.business;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nanuvem.lom.kernel.Attribute;
import com.nanuvem.lom.kernel.Class;
import com.nanuvem.lom.kernel.Instance;
import com.nanuvem.lom.lomgui.resources.LomAttributesExclusionStrategy;
import com.nanuvem.lom.lomgui.resources.LomClassSerializer;
import com.sun.jersey.core.spi.factory.ResponseBuilderImpl;

@Path("data")
public class BusinessServiceAdapter {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/class")
	public String getClasses() {
		Gson gson = new GsonBuilder()
        .registerTypeAdapter(Class.class, new LomClassSerializer())
        .create();
		return gson.toJson(LomBusinessFacade.getInstance().getAllClasses());
	}

	@POST
	@Path("/class")
	public Response addClass(String json) {
		try {
			Gson gson = new Gson();
			Class clazz = gson.fromJson(json, Class.class);
			clazz = LomBusinessFacade.getInstance().addClass(clazz);
			ResponseBuilderImpl builder = new ResponseBuilderImpl();
			builder.status(201);
			builder.entity(gson.toJson(clazz));
			return builder.build();
		} catch (Exception e) {
			return Response.notAcceptable(null).build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/class/{id}")
	public String getClass(@PathParam("id") Long id) {
		Gson gson = new GsonBuilder()
        .registerTypeAdapter(Class.class, new LomClassSerializer())
        .create();
		return gson.toJson(LomBusinessFacade.getInstance().getClass(id));
	}

	@DELETE
	@Path("/class/{id}")
	public Response deleteClass(@PathParam("id") Long id) {
		if (LomBusinessFacade.getInstance().removeClass(id))
			return Response.ok().build();
		return Response.notAcceptable(null).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/class/{fullName}/attributes")
	public String getAttributesFromClass(@PathParam("fullName") String fullName) {
		Gson gson = new GsonBuilder()
        .setExclusionStrategies(new LomAttributesExclusionStrategy(ImmutableSet.of("clazz")))
        .serializeNulls()
        .create();
		return gson.toJson(LomBusinessFacade.getInstance()
				.getAttributesByClassFullName(fullName));
	}

	@POST
	@Path("/class/{fullName}/attributes")
	public Response addAttributeToClass(@PathParam("fullName") String fullName,
			String json) {
		Class clazz = LomBusinessFacade.getInstance().getClass(fullName);
		if (clazz != null) {
			try {
				Gson gson = new Gson();
				Attribute attribute = gson.fromJson(json, Attribute.class);
				attribute.setClazz(clazz);
				attribute = LomBusinessFacade.getInstance().addAttribute(
						attribute);
				ResponseBuilderImpl builder = new ResponseBuilderImpl();
				builder.status(201);
				builder.entity(gson.toJson(attribute));
				return builder.build();
			} catch (Exception e) {
				return Response.notAcceptable(null).build();
			}
		}
		return Response.notAcceptable(null).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/class/{fullName}/instances")
	public String getInstances(@PathParam("fullName") String fullName) {
		Gson gson = new GsonBuilder()
        .setExclusionStrategies(new LomAttributesExclusionStrategy(ImmutableSet.of("clazz", "values")))
        .serializeNulls()
        .create();
		return gson.toJson(LomBusinessFacade.getInstance()
				.getInstancesByClassFullName(fullName));
	}

	@POST
	@Path("/class/{fullName}/instances")
	public Response addInstanceToClass(@PathParam("fullName") String fullName,
			String json) {
		Class clazz = LomBusinessFacade.getInstance().getClass(fullName);
		if (clazz != null) {
			try {
				Gson gson = new Gson();
				Instance instance = gson.fromJson(json, Instance.class);
				instance.setClazz(clazz);
				instance = LomBusinessFacade.getInstance()
						.addInstance(instance);
				ResponseBuilderImpl builder = new ResponseBuilderImpl();
				builder.status(201);
				builder.entity(gson.toJson(instance));
				return builder.build();
			} catch (Exception e) {
				return Response.notAcceptable(null).build();
			}
		}
		return Response.notAcceptable(null).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/attribute")
	public Response addAttribute(String json) {
		try {
			Gson gson = new Gson();
			Attribute attribute = gson.fromJson(json, Attribute.class);
			attribute = LomBusinessFacade.getInstance().addAttribute(attribute);
			ResponseBuilderImpl builder = new ResponseBuilderImpl();
			builder.status(201);
			builder.entity(gson.toJson(attribute));
			return builder.build();
		} catch (Exception e) {
			return Response.notAcceptable(null).build();
		}
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/attribute/{id}")
	public Response deleteAttribute(@PathParam("id") Long id) {
		if (LomBusinessFacade.getInstance().removeAttribute(id))
			return Response.ok().build();
		return Response.notAcceptable(null).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/instance")
	public Response addInstance(String json) {
		try {
			Gson gson = new Gson();
			Instance instance = gson.fromJson(json, Instance.class);
			instance = LomBusinessFacade.getInstance().addInstance(instance);
			ResponseBuilderImpl builder = new ResponseBuilderImpl();
			builder.status(201);
			builder.entity(gson.toJson(instance));
			return builder.build();
		} catch (Exception e) {
			return Response.notAcceptable(null).build();
		}
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/instance/{id}")
	public Response deleteInstance(@PathParam("id") Long id) {
		if (LomBusinessFacade.getInstance().removeInstance(id))
			return Response.ok().build();
		return Response.notAcceptable(null).build();
	}

}
