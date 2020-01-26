package jobsfinder;

import jobsfinder.writers.database.DatabaseWriter;

import java.util.HashSet;
import java.util.Set;

public class TestJob {
    public static void main(String[] args) {
        Job job1 = new Job();
        job1.setUrl("https://jobs.github.com/positions/4cd29974-e48b-11e8-8478-fb9810f86b79");

        Job job2 = new Job();
        job2.setUrl("https://jobs.github.com/positions/4cd29974-e48b-11e8-8478-fb9810f86b79");
        System.out.println(job1.equals(job2));

        Set<Job> allJobs = new HashSet<>(DatabaseWriter.getAllJobs());
        System.out.println(allJobs.contains(job1));
    }
}
