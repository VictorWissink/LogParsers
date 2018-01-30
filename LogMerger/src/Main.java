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


    /**
     * This program merges two json testresult log files and saves it to the location of the first one
     * @param args ArrayList of paths to logfiles to be merged
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        String firstPath;
        String secondPath;
        TestRun testRun;
        //check of argumenten meegegeven zijn

        try {
            firstPath = args[0];
            System.out.println(firstPath);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Program has not found any arguments. Add a program argument that refers to a requirementslog in json");
            throw e;
        }
        try {
            secondPath = args[1];
            System.out.println(secondPath);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Program has not found a second argument. Add a second program argument that refers to a second requirementslog in json to be merged");
            throw e;
        }

        try {

            File requirementLog1 = new File(firstPath);
            File requirementLog2 = new File(secondPath);
            String fileContents1 = FileUtils.readFileToString(requirementLog1, "UTF-8");
            String fileContents2 = FileUtils.readFileToString(requirementLog2, "UTF-8");


            Gson gson = new Gson();

            TestRun testRun1 = gson.fromJson(fileContents1, TestRun.class);
            TestRun testRun2 = gson.fromJson(fileContents2, TestRun.class);

            for(Requirement req : testRun2.getRequirements()) {
                testRun1.addRequirement(req);
            }


            String json = gson.toJson(testRun1);
            PrintWriter writer = new PrintWriter(firstPath, "UTF-8");
            writer.print(json);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
