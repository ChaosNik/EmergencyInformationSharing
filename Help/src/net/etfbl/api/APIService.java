package net.etfbl.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import net.etfbl.model.User;

@Path("/emergencyHelp/")
public class APIService {

	EmergencyHelpService service;

	public APIService() {
		service = new EmergencyHelpService();
	}
	
	@GET
	@Path("/rss")
	@Produces(MediaType.TEXT_PLAIN)
	public String getRss() {
		return service.getRss();
	}
	
	@DELETE
	@Path("delete/{id}")
	public Response deleteEmergencyHelpPost(@PathParam("id") int id) {
		if (service.deleteEmergencyHelpPost(id)) {
			return Response.status(200).build();
		}
		return Response.status(404).build();
	}
}
