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

    public ArrayList<Testcase> getTests() {
        return this.tests;
    }
    public boolean hasTest(String name) {
        for(Testcase test : tests) {
            if(test.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public String getName() {
        return name;
    }




}
