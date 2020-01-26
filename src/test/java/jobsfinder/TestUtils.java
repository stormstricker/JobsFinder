package jobsfinder;

import java.util.ArrayList;
import java.util.List;

public class TestUtils {
    public static List<Job> getTestJobs()  {
        List<Job> result = new ArrayList<>();

        Job job = new Job();
        job.setTitle("test");
        job.addTag("some tag");
        job.addNote("some note");

        Job job1 = new Job();
        job1.setTitle("test2");
        job1.setSchedule("full-time");
        job1.setSummary("test summary");
        job1.addNote("some note1");
        job1.addNote("some note2");
        job1.setUrl("https://google.com");


        result.add(job);
        result.add(job1);
        return result;
    }
}
