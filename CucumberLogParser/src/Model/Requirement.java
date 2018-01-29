package Model;

import java.util.ArrayList;

public class Requirement {
    private String name;
    private ArrayList<Testcase> tests = new ArrayList<Testcase>();

    public Requirement(String name) {
        this.name = name;

    }


    public void addTestcase(Testcase testcase) {
        tests.add(testcase);
        }


    public String getName() {
        return name;
    }




}
