package com.nanuvem.lom.lomgui;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.JsonObject;
import com.nanuvem.lom.lomgui.resources.Renderer;
import com.nanuvem.lom.lomgui.resources.Widget;

@Path("/widget")
public class WidgetService {
	
	@Context
	private HttpServletRequest servletRequest;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{renderer}/{hook}")
	public Response getWidget(@PathParam("renderer") String rendererID, @PathParam("hook") String hook) {

		Renderer renderer = GUIStoreFacade.getInstance()
				.getRendererFromName(rendererID);
		if(renderer != null) {
			Widget widget = renderer.getHook(hook);
			if(widget != null) {
				String result = FileSystemUtil.getFileScript(servletRequest, "widgets",
						widget.getFilename());
				JsonObject json = new JsonObject();
				json.addProperty("id", widget.getName());
				json.addProperty("script", result);
				return Response.ok(json.toString()).build();
			}
		}
		return Response.notAcceptable(null).build();
	}
	
}
