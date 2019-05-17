package se.fk.hack.mft.rest;


import se.fk.hack.mft.db.Neo4j;
import se.fk.hack.mft.vo.MonthRequest;
import se.fk.hack.mft.vo.User;
import se.fk.hack.mft.vo.UserIdRequest;
import se.fk.hack.mft.vo.UserLedighetRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


@Path("mft")
public class MFTEndpoint {

	@POST
	@Path("user")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(UserIdRequest request) {
		return Response.ok().entity(Neo4j.getUser(request)).build();
	}

	@POST
	@Path("medarbetare")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMedarbetare(UserIdRequest request) {
		return Response.ok().entity(Neo4j.getMedarbetare(request)).build();
	}

	@POST
	@Path("collegues")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCollegues(UserIdRequest request) {
		return Response.ok().entity(Neo4j.getCollegues(request)).build();
	}

	@POST
	@Path("is-manager")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response isManager(UserIdRequest request) {
		return Response.ok().entity(Neo4j.isManager(request)).build();
	}

	@POST
	@Path("user/ledighet")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response userLedighet(UserIdRequest request) {
		return Response.ok().entity(Neo4j.userGetLedighet(request)).build();
	}

	@POST
	@Path("user/ledighet/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response userCreateLedighet(UserLedighetRequest request) {
		return Response.ok().entity(Neo4j.userCreateLedighet(request)).build();
	}

	@POST
	@Path("ledighetstyper")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response ledighetstyper() {
		return Response.ok().entity(Neo4j.ledighetstyper()).build();
	}

	@POST
	@Path("enhetsvy")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response enhetsvy(MonthRequest request) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		// Figure out last day of month.
		Calendar monthEnd = Calendar.getInstance();
		monthEnd.setTime(format.parse(request.getDate()));
		monthEnd.add(Calendar.MONTH, 1);
		monthEnd.add(Calendar.DATE, -1);

		// Get people in your unit.
		List<String> kortidn = new ArrayList<>();
		kortidn.add(request.getKortid());
		Neo4j.getCollegues(new UserIdRequest(request.getKortid())).stream().map(User::getKortid).forEach(kortidn::add);

		return Response.ok().entity(Neo4j.userLedighetRange(kortidn, request.getDate(), format.format(monthEnd.getTime()))).build();
	}

	@POST
	@Path("test")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response test() throws ParseException {
		List<String> kortidn = new ArrayList<>();
		kortidn.add("88880001");
		kortidn.add("88880002");
		kortidn.add("88880003");
		return Response.ok().entity(Neo4j.userLedighetRange(kortidn, "2020-10-01", "2020-11-30")).build();
	}
}
