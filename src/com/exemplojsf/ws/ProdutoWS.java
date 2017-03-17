package com.exemplojsf.ws;

import java.io.UnsupportedEncodingException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;

@Path("wsGet")
public class ProdutoWS {
	@GET
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON + ";charset=UTF-8" })
	@Path("/exemplo")
	public String getExemplo() throws JsonProcessingException, UnsupportedEncodingException {
		return "Hello World!";
	}
}
