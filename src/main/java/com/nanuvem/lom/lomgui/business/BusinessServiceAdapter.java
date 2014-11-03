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
import com.nanuvem.lom.kernel.Attribute;
import com.nanuvem.lom.kernel.Class;
import com.nanuvem.lom.kernel.Instance;
import com.nanuvem.lom.lomgui.resources.LomGsonFactory;
import com.sun.jersey.core.spi.factory.ResponseBuilderImpl;

@Path("data")
public class BusinessServiceAdapter {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/class")
	public String getClasses() {
		Gson gson = new LomGsonFactory().getClassGson();
		return gson.toJson(LomBusinessFacade.getInstance().getAllClasses());
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
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
		Gson gson = new LomGsonFactory().getClassGson();
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
		Gson gson = new LomGsonFactory().getAttributeGson();
		return gson.toJson(LomBusinessFacade.getInstance()
				.getAttributesByClassFullName(fullName));
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/class/{fullName}/attributes")
	public Response addAttributeToClass(@PathParam("fullName") String fullName,
			String json) {
		Class clazz = LomBusinessFacade.getInstance().getClass(fullName);
		if (clazz != null) {
			try {
				Gson gson = new LomGsonFactory().getAttributeGson();
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
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/class/{fullName}/attributes/{id}")
	public Response updateAttribute(@PathParam("fullName") String fullName, @PathParam("id") Long id, String json) {
		Class clazz = LomBusinessFacade.getInstance().getClass(fullName);
		if (clazz != null) {
			//TODO check if attribute exist in class
			try {
				Gson gson = new LomGsonFactory().getAttributeGson();
				Attribute attribute = gson.fromJson(json, Attribute.class);
				attribute.setClazz(clazz);
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
	@Path("/class/{fullName}/attributes/{id}")
	public Response deleteAttribute(@PathParam("fullName") String fullName, @PathParam("id") Long id) {
		if (LomBusinessFacade.getInstance().removeAttribute(id))
			return Response.ok().build();
		return Response.notAcceptable(null).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/class/{fullName}/instances")
	public String getInstances(@PathParam("fullName") String fullName) {
		Gson gson = new LomGsonFactory().getInstanceGson();
		return gson.toJson(LomBusinessFacade.getInstance()
				.getInstancesByClassFullName(fullName));
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/class/{fullName}/instances")
	public Response addInstanceToClass(@PathParam("fullName") String fullName,
			String json) {
		Class clazz = LomBusinessFacade.getInstance().getClass(fullName);
		if (clazz != null) {
			try {
				Gson gson = new LomGsonFactory().getInstanceGson();
				Instance instance = gson.fromJson(json, Instance.class);
				instance.setClazz(clazz);
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

	@DELETE
	@Path("/class/{fullName}/instances/{id}")
	public Response deleteInstance(@PathParam("fullName") String fullName, @PathParam("id") Long id) {
		if (LomBusinessFacade.getInstance().removeInstance(id))
			return Response.ok().build();
		return Response.notAcceptable(null).build();
	}

}
