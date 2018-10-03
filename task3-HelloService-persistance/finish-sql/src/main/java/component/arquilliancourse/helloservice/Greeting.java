package component.arquilliancourse.helloservice;

import javax.json.bind.JsonbBuilder;
import java.util.Objects;

public class Greeting {
    private String greeting;
    private String greetingLanguage;

    public Greeting () {
    }

    public Greeting ( String greeting, String greetingLanguage ) {
        this.greeting = greeting;
        this.greetingLanguage = greetingLanguage;
    }

    public String getGreeting () {
        return greeting;
    }

    public void setGreeting ( String greeting ) {
        this.greeting = greeting;
    }

    public String getGreetingLanguage () {
        return greetingLanguage;
    }

    public void setGreetingLanguage ( String greetingLanguage ) {
        this.greetingLanguage = greetingLanguage;
    }

    public String toJson(){
        return JsonbBuilder.create().toJson( this );
    }

    public static Greeting fromJson(String json){
        return JsonbBuilder.create().fromJson( json, Greeting.class );
    }

    @Override
    public int hashCode () {
        return Objects.hash( greeting, greetingLanguage );
    }

    @Override
    public boolean equals ( Object obj ) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        final Greeting greeting = (Greeting) obj;
        return Objects.equals(greeting.greeting, this.greeting)
                && Objects.equals( greeting.greetingLanguage, this.greetingLanguage );
    }

    @Override
    public String toString () {
        return new StringBuilder("Greeting{").
                append( "greeting='" ).append( greeting ).append( '\'' ).
                append( "greetingLanguage='" ).append( greetingLanguage ).append( '\'' ).
                append( "}" ).toString();
    }
}
