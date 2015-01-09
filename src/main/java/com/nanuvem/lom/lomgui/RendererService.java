package com.nanuvem.lom.lomgui;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nanuvem.lom.lomgui.resources.Renderer;

@Path("/renderer")
public class RendererService {

	@Context
	private HttpServletRequest servletRequest;
/*
	@GET
	@Produces("text/plain; charset=utf-8")
	@Path("/root")
	public Response getRootRenderer() {

		String rendererFilename = GUIStoreFacade.getInstance()
				.getRendererFromTarget("root").getFilename();
		String result = FileSystemUtil.getFileScript(servletRequest, "renderers",
				rendererFilename);
		return Response.ok(result).build();
	}
*/
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/root")
	public Response getRootRenderer() {

		Renderer renderer = GUIStoreFacade.getInstance()
				.getRendererFromTarget("root");
		String result = FileSystemUtil.getFileScript(servletRequest, "renderers",
				renderer.getFilename());
		JsonObject json = new JsonObject();
		json.addProperty("id", renderer.getName());
		json.addProperty("script", result);
		return Response.ok(json.toString()).build();
	}
	
	@POST
	@Path("/root")
	public Response setRootRenderer(String json, @Context UriInfo uriInfo) {
		try {
			Gson gson = new Gson();
			Renderer renderer = gson.fromJson(json, Renderer.class);
			renderer = GUIStoreFacade.getInstance().getRendererFromName(
					renderer.getName());
			saveRendererByPath(uriInfo.getPath(), renderer);
			return Response.ok().build();
		} catch (Exception e) {
			return Response.notAcceptable(null).build();
		}

	}

	@GET
	@Produces("text/plain; charset=utf-8")
	@Path("/entity")
	public Response getDefaultClassRenderer() {
		Renderer renderer = GUIStoreFacade.getInstance().getRendererFromTarget(
				"entity");
		String result = FileSystemUtil.getFileScript(servletRequest, "renderers",
				renderer.getFilename());
		return Response.ok(result).build();
	}

	@POST
	@Path("/entity")
	public Response setDefaultClassRenderer(String json, @Context UriInfo uriInfo) {
		try {
			Gson gson = new Gson();
			Renderer renderer = gson.fromJson(json, Renderer.class);
			renderer = GUIStoreFacade.getInstance().getRendererFromName(
					renderer.getName());
			saveRendererByPath(uriInfo.getPath(), renderer);
			return Response.ok().build();
		} catch (Exception e) {
			return Response.notAcceptable(null).build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/entity/{fullName}")
	public Response getClassRenderer(@PathParam("fullName") String fullName) {
		Renderer renderer = GUIStoreFacade.getInstance().getRendererFromTarget(
				"entity." + fullName);
		if (renderer == null) {
			renderer = GUIStoreFacade.getInstance().getRendererFromTarget(
					"entity");
		}
		String result = FileSystemUtil.getFileScript(servletRequest, "renderers",
				renderer.getFilename());
		JsonObject json = new JsonObject();
		json.addProperty("id", renderer.getName());
		json.addProperty("script", result);
		return Response.ok(json.toString()).build();
	}

	@POST
	@Path("/entity/{fullName}")
	public Response setClassRenderer(@PathParam("fullName") String fullName,
			String json, @Context UriInfo uriInfo) {
		try {
			Gson gson = new Gson();
			Renderer renderer = gson.fromJson(json, Renderer.class);
			renderer = GUIStoreFacade.getInstance().getRendererFromName(
					renderer.getName());
			saveRendererByPath(uriInfo.getPath(), renderer);
			return Response.ok().build();
		} catch (Exception e) {
			return Response.notAcceptable(null).build();
		}
	}

	@GET
	@Produces("text/plain; charset=utf-8")
	@Path("/entity/{fullName}/{attributeName}")
	public Response getAttributeRenderer(@PathParam("fullName") String fullName,
			@PathParam("attributeName") String attributeName) {
		Renderer renderer = GUIStoreFacade.getInstance().getRendererFromTarget(
				"entity." + fullName + "." + attributeName);
		if (renderer == null) {
			renderer = GUIStoreFacade.getInstance().getRendererFromTarget(
					"attribute");
		}
		String result = FileSystemUtil.getFileScript(servletRequest, "renderers",
				renderer.getFilename());
		return Response.ok(result).build();
	}

	@POST
	@Path("/entity/{fullName}/{attributeName}")
	public Response setAttributeRenderer(@PathParam("fullName") String fullName,
			@PathParam("attributeName") String attributeName, String json,
			@Context UriInfo uriInfo) {
		try {
			Gson gson = new Gson();
			Renderer renderer = gson.fromJson(json, Renderer.class);
			renderer = GUIStoreFacade.getInstance().getRendererFromName(
					renderer.getName());
			saveRendererByPath(uriInfo.getPath(), renderer);
			return Response.ok().build();
		} catch (Exception e) {
			return Response.notAcceptable(null).build();
		}
	}

	private void saveRendererByPath(String path, Renderer renderer) {
		String[] pathComponents = path.split("/");
		String target = null;
		if (pathComponents.length < 2) {
			return;
		}

		else if (pathComponents[1].equals("root")) {
			target = "root";
		}

		else if (pathComponents[1].equals("entity")) {

			target = "entity";

			if (pathComponents.length >= 3) {
				target += "." + pathComponents[2];
			}

			if (pathComponents.length >= 4) {
				target += "." + pathComponents[3];
			}

		}

		GUIStoreFacade.getInstance().setRendererToTarget(target, renderer);
	}

}