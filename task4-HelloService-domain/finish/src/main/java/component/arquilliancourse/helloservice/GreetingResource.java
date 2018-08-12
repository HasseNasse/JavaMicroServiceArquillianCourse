package component.arquilliancourse.helloservice;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;

@Path( "greetings" )
@ApplicationScoped
public class GreetingResource {

    @Inject
    GreetingDAO greetingDAO;

    @Inject
    GreetingService greetingService;

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
        try{
            return Response.ok(greetingService.doInsertion( greeting )).build();
        }catch ( Exception e ){
            return Response.status( 403 ).entity( e.getMessage() ).build();
        }
    }

}
