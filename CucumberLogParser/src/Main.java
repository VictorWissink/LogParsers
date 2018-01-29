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


            JsonElement jelement = new JsonParser().parse(fileContents);


            JsonArray features = jelement.getAsJsonArray();
//            JsonArray results = jelement.getAsJsonArray();
//
            Date date = new Date();
//
            testRun = new TestRun(date.toString());

            for (JsonElement obj : features) {

                JsonArray scenarios = obj.getAsJsonObject().get("elements").getAsJsonArray();

                for (JsonElement scenarioObj : scenarios) {
                    if (scenarioObj.getAsJsonObject().has("tags")) {

                        JsonArray tags = scenarioObj.getAsJsonObject().get("tags").getAsJsonArray();
                        String[] reqobject = new String[0];

                        //get requirement tag
                        for (JsonElement tagObj : tags) {
                            //ZOEK HIER NAAR DE ANNOTATIE MET REQUIREMENT
                            String tagname = tagObj.getAsJsonObject().get("name").getAsString();
                            tagname = tagname.toUpperCase();
                            if (tagname.startsWith("@REQ-")) {
                                //REQUIREMENTCODE GEVONDEN

                                reqobject = tagname.split(Pattern.quote("-"));
                            }
                        }

                        if (reqobject.length == 0) {
                            //unlisted ++
                            testRun.addUnlisted();
                        } else {

                            String requirement = reqobject[1];

                            Requirement reqObj;
                            reqObj = testRun.getRequirementByName(requirement);


                            //check of de testcase ID is meegegeven
                            String testcase;
                            if (reqobject.length < 3) {
                                testcase = "NOID";
                            } else {
                                testcase = reqobject[2];
                            }


                            //maak de testcase aan en voeg deze toe aan de requirement

                            Testcase tc1 = new Testcase(testcase);

                            //TODO
                            //check of deze ook een stacktrace bevat
                            String stacktrace = "";
                            JsonArray stepsArr = scenarioObj.getAsJsonObject().get("steps").getAsJsonArray();
                            for (JsonElement step : stepsArr) {
                                JsonObject result = step.getAsJsonObject().get("result").getAsJsonObject();

                                    if (!result.get("status").getAsString().equals("passed")) {
                                        //stap is niet gelukt, stacktrace kan worden toegevoegd
                                        //check of de stacktrace aanwezig is

                                        if (result.has("error_message")) {
                                            stacktrace = result.get("error_message").getAsString();
                                        } else {
                                            stacktrace = "stacktrace could not be found";
                                        }



                                }

                            }

                            if(stacktrace.length() > 0) {
                                tc1.addStacktrace(stacktrace);
                            }
                            reqObj.addTestcase(tc1);

                            //voeg de requirement toe aan de testrun
                            testRun.addRequirement(reqObj);

                        }


                    } else {
                        testRun.addUnlisted();
                    }
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
