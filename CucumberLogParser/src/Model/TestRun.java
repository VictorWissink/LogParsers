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
        for (Requirement r : requirements) {
            if (r.getName().equals(req.getName())) {
                //place the requirement back in the array if it's found
                r = req;
                return;

            }
        }

        requirements.add(req);


    }
}
