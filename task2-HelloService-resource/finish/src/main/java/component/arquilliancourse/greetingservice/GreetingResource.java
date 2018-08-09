package component.arquilliancourse.greetingservice;


import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path( "greetings" )
public class GreetingResource {

    @GET
    @Produces( MediaType.APPLICATION_JSON )
    public Response shouldReturnRandomGreeting(){
        Jsonb jsonb = JsonbBuilder.newBuilder().build();

        Greeting greeting = new Greeting( "Hello", "en" );
        return Response.ok(jsonb.toJson( greeting )).build();
    }
}
