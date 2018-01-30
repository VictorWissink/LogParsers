package Model;

import java.util.ArrayList;
import java.util.List;

public class TestRun {
    private String date;
    private int unlisted;
    private ArrayList<Requirement> requirements;

    public TestRun(String date) {
        this.date = date;
        requirements = new ArrayList<Requirement>();
    }

    public void addUnlisted() {
        unlisted++;
    }


    public Requirement getRequirementByName(String name) {
        for (Requirement r : requirements) {
            if (r.getName().equals(name)) {
                return r;
            }
        }
        return new Requirement(name);

    }


    public void addRequirement(Requirement req) {
        Requirement toRemove = new Requirement("");
        for (Requirement r : requirements) {
            if (r.getName().equals(req.getName())) {
                for(Testcase test : r.getTests()) {
                    //check of de test met deze naam al bestaat
                    if(!req.hasTest(test.getName())) {


                        Testcase newTest = new Testcase(test.getName());
                        newTest.addStacktrace(test.getStacktrace());


                        req.addTestcase(newTest);

                    }

                }
                toRemove = r;
            }
        }
        if(!toRemove.getName().equals("")) {
            requirements.remove(toRemove);
        }
        requirements.add(req);


    }

    public ArrayList<Requirement> getRequirements() {
        return this.requirements;
    }
}
