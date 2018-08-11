package component.arquilliancourse.helloservice;

import java.util.Objects;
import javax.json.bind.JsonbBuilder;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Greeting greeting1 = (Greeting) o;
        return Objects.equals(greeting, greeting1.greeting) &&
            Objects.equals(greetingLanguage, greeting1.greetingLanguage);
    }

    @Override
    public int hashCode() {

        return Objects.hash(greeting, greetingLanguage);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Greeting{");
        sb.append("greeting='").append(greeting).append('\'');
        sb.append(", greetingLanguage='").append(greetingLanguage).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
