package com.exemplojsf.ws;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("wsPost")
public class ResultadoWS {

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON + ";charset=UTF-8" })
	@Path("/exemplo")
	public void postExemplo(String json) {

	}
}
