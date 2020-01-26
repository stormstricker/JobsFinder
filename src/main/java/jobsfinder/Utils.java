package jobsfinder;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;

public class Utils {
    public static void removeDuplicates(Set<Job> allJobs,
                                        List<List<Job>> jobsLists)  {
        for (List<Job> jobs: jobsLists)  {
            jobs.removeIf(item -> allJobs.contains(item));
        }
    }

    public static void removeDuplicates(List<Job> allJobs,
                                        List<List<Job>> jobsLists)  {
        for (List<Job> jobs: jobsLists)  {
            jobs.removeIf(item -> allJobs.contains(item));
        }
    }

    public static String listToString(List<?> list, String separator)  {
        String result = "";

        for (Object e: list)  {
            result += e.toString() + separator;
        }

        return result == "" ? result : result.substring(0, result.lastIndexOf(separator));
    }

    public static String getFirstLineFromFileFromPath(String packageName, String filename)  {

        try  {
            BufferedReader br = new BufferedReader(new FileReader(Paths.get(packageName, filename).toFile()));
            String result = br.readLine();
            br.close();

            return result;
        }
        catch (Exception e)  {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean addLineToFileFromPath(String packageName, String filename, String line) {
        try {
            Writer output;
            System.out.println(Paths.get("").toString());
            output = new BufferedWriter(new FileWriter(Paths.get(packageName, filename).toFile(), true));
            output.append(line + System.lineSeparator());
            output.close();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean clearFileFromPath(String packageName, String filename)  {
        try {
            Writer output;
            System.out.println(Paths.get("").toString());
            output = new BufferedWriter(new FileWriter(Paths.get(packageName, filename).toFile()));

            output.close();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<String> getAllLinesFromFileFromPath(String packageName, String filename)  {
        try  {
            BufferedReader br = new BufferedReader(new FileReader(Paths.get(packageName, filename).toFile()));
            List<String> result = new ArrayList<>();
            String line;
            while ((line = br.readLine())!=null)  {
                result.add(line);
            }
            br.close();

            System.out.println("result size: " + result.size());
            return result;
        }
        catch (Exception e)  {
            e.printStackTrace();
            return null;
        }
    }


}
