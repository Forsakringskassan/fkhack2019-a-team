package se.fk.hack.mft.rest;


import se.fk.hack.mft.db.Neo4j;
import se.fk.hack.mft.vo.UserIdRequest;
import se.fk.hack.mft.vo.UserLedighetRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("mft")
public class MFTEndpoint {

	private final ResponseFactory rf = new ResponseFactory();

	@POST
	@Path("user")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(UserIdRequest request) {
		return rf.get().entity(Neo4j.getUser(request)).build();
	}

	@POST
	@Path("collegues")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCollegues(UserIdRequest request) {
		return rf.get().entity(Neo4j.getCollegues(request)).build();
	}

	@POST
	@Path("is-manager")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response isManager(UserIdRequest request) {
		return rf.get().entity(Neo4j.isManager(request)).build();
	}

	@POST
	@Path("user/ledighet")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response userLedighet(UserIdRequest request) {
		return rf.get().entity(Neo4j.userGetLedighet(request)).build();
	}

	@POST
	@Path("user/ledighet/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response userCreateLedighet(UserLedighetRequest request) {
		return rf.get().entity(Neo4j.userCreateLedighet(request)).build();
	}

	public class ResponseFactory {
		public Response.ResponseBuilder get() {
			return Response
					.ok()
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Headers",
							"origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods",
							"GET, POST, PUT, DELETE, OPTIONS, HEAD");
		}
	}
}
