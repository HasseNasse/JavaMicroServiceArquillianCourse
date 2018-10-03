package component.arquilliancourse.helloservice;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;

@Path( "greetings" )
public class GreetingResource {

    @GET
    @Produces( MediaType.APPLICATION_JSON )
    public Response getGreetings( @QueryParam( "locale" ) String locale ){
        if ( locale != null )
            return Response.ok(new Greeting( "hello", locale )).build();
        return Response.ok(Arrays.asList(
                new Greeting("salam", "ar"),
                new Greeting("hello", "en"),
                new Greeting("hej", "sv"),
                new Greeting("hola", "sp")
        ), MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path( "{id}" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response getGreetingById( @PathParam( "id" ) String id ){
        return Response.ok(
                new Greeting(id, "sp"), MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addGreeting( Greeting greeting ){
        try {
            return Response.ok().build();
        }catch ( ValueException ex ){
            ex.printStackTrace();
            return Response.status( 400 ).build();
        }
    }

}
