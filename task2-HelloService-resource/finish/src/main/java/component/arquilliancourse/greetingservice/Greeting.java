package component.arquilliancourse.greetingservice;

public class Greeting {
    private String greeting;
    private String greetingLanguage;

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
}
