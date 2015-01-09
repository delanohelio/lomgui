package com.nanuvem.lom.lomgui.business;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.nanuvem.lom.api.Attribute;
import com.nanuvem.lom.api.Entity;
import com.nanuvem.lom.api.Instance;
import com.nanuvem.lom.api.MetadataException;
import com.nanuvem.lom.lomgui.resources.LomGsonFactory;
import com.sun.jersey.core.spi.factory.ResponseBuilderImpl;

@Path("data")
public class BusinessServiceAdapter {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/entity")
	public String getEntities() {
		Gson gson = new LomGsonFactory().getEntityGson();
		return gson.toJson(LomBusinessFacade.getInstance().getAllEntityes());
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/entity")
	public Response addEntity(String json) {
		try {
			Gson gson = new Gson();
			Entity entity = gson.fromJson(json, Entity.class);
			entity = LomBusinessFacade.getInstance().addEntity(entity);
			ResponseBuilderImpl builder = new ResponseBuilderImpl();
			builder.status(201);
			builder.entity(gson.toJson(entity));
			return builder.build();
		} catch (Exception e) {
			return Response.notAcceptable(null).build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/entity/{id}")
	public String getEntity(@PathParam("id") Long id) {
		Gson gson = new LomGsonFactory().getEntityGson();
		return gson.toJson(LomBusinessFacade.getInstance().getEntity(id));
	}

	@DELETE
	@Path("/entity/{id}")
	public Response deleteEntity(@PathParam("id") Long id) {
		try {
			LomBusinessFacade.getInstance().removeEntity(id);
			return Response.ok().build();
		} catch (MetadataException e) {
			return Response.notAcceptable(null).build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/entity/{fullName}/attributes")
	public String getAttributesFromEntity(@PathParam("fullName") String fullName) {
		Gson gson = new LomGsonFactory().getAttributeGson();
		return gson.toJson(LomBusinessFacade.getInstance()
				.getAttributesByEntityFullName(fullName));
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/entity/{fullName}/attributes")
	public Response addAttributeToEntity(@PathParam("fullName") String fullName,
			String json) {
		Entity entity = LomBusinessFacade.getInstance().getEntity(fullName);
		if (entity != null) {
			try {
				Gson gson = new LomGsonFactory().getAttributeGson();
				Attribute attribute = gson.fromJson(json, Attribute.class);
				attribute.setEntity(entity);
				attribute = LomBusinessFacade.getInstance().addAttribute(
						attribute);
				ResponseBuilderImpl builder = new ResponseBuilderImpl();
				builder.status(201);
				builder.entity(gson.toJson(attribute));
				return builder.build();
			} catch (Exception e) {
				e.printStackTrace();
				return Response.notAcceptable(null).build();
			}
		}
		System.out.println("Opa");
		return Response.notAcceptable(null).build();
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/entity/{fullName}/attributes/{id}")
	public Response updateAttribute(@PathParam("fullName") String fullName, @PathParam("id") Long id, String json) {
		Entity entity = LomBusinessFacade.getInstance().getEntity(fullName);
		if (entity != null) {
			try {
				Gson gson = new LomGsonFactory().getAttributeGson();
				Attribute attribute = gson.fromJson(json, Attribute.class);
				attribute.setEntity(entity);
				attribute = LomBusinessFacade.getInstance().updateAttribute(
						attribute);
				ResponseBuilderImpl builder = new ResponseBuilderImpl();
				builder.status(200);
				builder.entity(gson.toJson(attribute));
				return builder.build();
			} catch (Exception e) {
				return Response.notAcceptable(null).build();
			}
		}
		return Response.notAcceptable(null).build();
	}
	
	@DELETE
	@Path("/entity/{fullName}/attributes/{id}")
	public Response deleteAttribute(@PathParam("fullName") String fullName, @PathParam("id") Long id) {
		if (LomBusinessFacade.getInstance().removeAttribute(id))
			return Response.ok().build();
		return Response.notAcceptable(null).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/entity/{fullName}/instances")
	public String getInstances(@PathParam("fullName") String fullName) {
		Gson gson = new LomGsonFactory().getInstanceGson();
		return gson.toJson(LomBusinessFacade.getInstance()
				.getInstancesByEntityFullName(fullName));
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/entity/{fullName}/instances")
	public Response addInstanceToEntity(@PathParam("fullName") String fullName,
			String json) {
		Entity entity = LomBusinessFacade.getInstance().getEntity(fullName);
		if (entity != null) {
			try {
				Gson gson = new LomGsonFactory().getInstanceGson();
				Instance instance = gson.fromJson(json, Instance.class);
				instance.setEntity(entity);
				instance = LomBusinessFacade.getInstance()
						.addInstance(instance);
				ResponseBuilderImpl builder = new ResponseBuilderImpl();
				builder.status(201);
				builder.entity(gson.toJson(instance));
				return builder.build();
			} catch (Exception e) {
				e.printStackTrace();
				return Response.notAcceptable(null).build();
			}
		}
		return Response.notAcceptable(null).build();
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/entity/{fullName}/instances/{id}")
	public Response updateInstance(@PathParam("fullName") String fullName, @PathParam("id") Long id, String json) {
		Entity entity = LomBusinessFacade.getInstance().getEntity(fullName);
		if (entity != null) {
			try {
				Gson gson = new LomGsonFactory().getInstanceGson();
				Instance instance = gson.fromJson(json, Instance.class);
				instance.setEntity(entity);
				instance = LomBusinessFacade.getInstance().updateInstance(
						instance);
				ResponseBuilderImpl builder = new ResponseBuilderImpl();
				builder.status(200);
				builder.entity(gson.toJson(instance));
				return builder.build();
			} catch (Exception e) {
				return Response.notAcceptable(null).build();
			}
		}
		return Response.notAcceptable(null).build();
	}

	@DELETE
	@Path("/entity/{fullName}/instances/{id}")
	public Response deleteInstance(@PathParam("fullName") String fullName, @PathParam("id") Long id) {
		if (LomBusinessFacade.getInstance().removeInstance(id))
			return Response.ok().build();
		return Response.notAcceptable(null).build();
	}

}
