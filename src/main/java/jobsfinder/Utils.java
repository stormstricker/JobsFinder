package gmail2excel;

import javax.mail.Address;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MINUTES;

public class Utils {
    public static List<String> splitInLines(String s)  {
        List<String> result = new ArrayList<>();

        int start=0;
        int end = 0;
        char[] chars = s.toCharArray();
        while (end<s.length()-1)  {
            if (chars[end]=='\n' || chars[end]=='\r')  {
                result.add(s.substring(start, end));

                start = end + 1;
                while (start < s.length() && (start == '\n' || start=='\r'))  {
                    start++;
                    end = start + 1;
                }
            }

        }

        return result;
    }

    public static List<String> getAllLinesFromFile(String filename)  {
        try  {
            BufferedReader br = new BufferedReader(new FileReader(Paths.get("setups", filename).toFile()));
            List<String> result = new ArrayList<>();
            String line;
            while ((line = br.readLine())!=null)  {
                result.add(line);
            }
            br.close();

            return result;
        }
        catch (Exception e)  {
            e.printStackTrace();
            return null;
        }
    }

    public static String arrayToString(Address[] array)  {
        String result = "";

        for (Address a: array)  {
            result += a.toString() + " ";
        }
        if (result.length()>2)  {
            result = result.substring(0, result.length() - 2);
        }

        return result;
    }
}
