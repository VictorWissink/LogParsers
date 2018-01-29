import Model.Requirement;
import Model.TestRun;
import Model.Testcase;
import com.google.gson.*;
import jdk.nashorn.internal.parser.JSONParser;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;


public class Main {



    public static void main(String[] args) throws IOException {
        String path;
        TestRun testRun;
        //check of argumenten meegegeven zijn

        try {
            path = args[0];
            System.out.println(path);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Program has not found any arguments. Add a program argument that refers to a requirementslog in json");
            throw e;
        }

        try {

            File requirementLog = new File(path);
            String fileContents = FileUtils.readFileToString(requirementLog, "UTF-8");

            if (fileContents != null && fileContents.length() > 0 && fileContents.charAt(fileContents.length() - 3) == ',') {
                fileContents = fileContents.substring(0, fileContents.length() - 3);
            }

            String resultsJSONString = "[" + fileContents + ']';

            JsonElement jelement = new JsonParser().parse(resultsJSONString);

            JsonArray results = jelement.getAsJsonArray();

            Date date = new Date();

            testRun = new TestRun(date.toString());
            for (JsonElement obj : results ) {

            if(obj.getAsJsonObject().get("requirement") != null ) {

                String[] reqobject = obj.getAsJsonObject().get("requirement").getAsString().split(Pattern.quote("-"));
                String requirement = reqobject[0];

                Requirement reqObj;
                reqObj = testRun.getRequirementByName(requirement);


                //check of de testcase ID is meegegeven
                String testcase;
                if (reqobject.length < 2 ) {
                    testcase = "NOID";
                } else {
                    testcase = reqobject[1];
                }

                //maak de testcase aan en voeg deze toe aan de requirement
                Testcase tc1 = new Testcase(testcase);
                if(obj.getAsJsonObject().get("exception") != null) {
                    tc1.addStacktrace(obj.getAsJsonObject().get("exception").toString());
                }
                 reqObj.addTestcase(tc1);

                //voeg de requirement toe aan de testrun
                testRun.addRequirement(reqObj);

                } else {
                        testRun.addUnlisted();
            }

            }

            Gson gson = new Gson();
            String json = gson.toJson(testRun);
            PrintWriter writer = new PrintWriter(path, "UTF-8");
            writer.print(json);
            writer.close();



        } catch (IOException e) {
            System.out.println("can't read JSON file");
            throw e;
        }

    }
}
