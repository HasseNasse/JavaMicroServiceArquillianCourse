package net.nazarsit.javamicroservicearquilliancourse.HelloService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Singleton
@Path( "/greetings" )
public class HelloResource {

    @Inject
    private GreetingDAO greetingDAO;

    @Inject
    private GreetingService greetingService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGreeting (){
        return Response.ok(greetingDAO.findAll(), MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addGreeting( Greeting greeting ){
        return greetingService.validateAndSaveGreeting( greeting ) ?
            Response.ok().build() :
            Response.status( 400 ).build();
    }

}
