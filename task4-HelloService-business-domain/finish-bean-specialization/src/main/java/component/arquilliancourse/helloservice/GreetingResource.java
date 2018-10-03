package component.arquilliancourse.helloservice;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Variant;
import java.util.Arrays;

@Path( "greetings" )
@ApplicationScoped
public class GreetingResource {

    @Inject
    GreetingService greetingService;

    @Inject
    GreetingDAO greetingDAO;

    @GET
    @Produces( MediaType.APPLICATION_JSON )
    public Response getGreetings( @QueryParam( "locale" ) String locale ){
        if ( locale != null )
            return Response.ok(greetingDAO.findByLanguage( locale )).build();
        return Response.ok(greetingDAO.findAll(), MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path( "{id}" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response getGreetingById( @PathParam( "id" ) String id ){
        return Response.ok(
                greetingDAO.findByID( id ), MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addGreeting( Greeting greeting ){
        if(greetingService.attemptInsertion( greeting ))
            return Response.ok().build();
        return Response.status( 403 ).build();
    }

}
