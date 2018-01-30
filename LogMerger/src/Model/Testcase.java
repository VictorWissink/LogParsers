package Model;

public class Testcase {
    private String name, stacktrace;

    public Testcase(String name) {
        this.name = name;

    }
    public String getName() {
        return this.name;
    }
    public void addStacktrace(String stacktrace) {
        this.stacktrace = stacktrace;
    }
    public String getStacktrace() {
        return this.stacktrace;
    }


}
