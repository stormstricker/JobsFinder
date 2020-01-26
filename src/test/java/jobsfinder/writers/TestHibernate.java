package jobsfinder.writers;

import jobsfinder.Job;
import jobsfinder.config.Config;
import jobsfinder.scrapers.IndeedScraper;
import jobsfinder.writers.database.DatabaseWriter;

import java.util.List;

public class TestHibernate {
    public static void main(String[] args) throws Exception {
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

        DatabaseWriter databaseWriter = new DatabaseWriter();
        databaseWriter.write(job);
        databaseWriter.write(job1);

        Job resultJob = databaseWriter.getJobByUrl(job1.getUrl());
        System.out.println();
        List<String> tags = resultJob.getTags();
        List<String> notes = resultJob.getNotes();
        System.out.println();

        IndeedScraper indeedScraper = new IndeedScraper(Config.getInstance());
        List<Job> jobs = indeedScraper.scrape();
        System.out.println("size: " + jobs.size());
        databaseWriter.append(jobs);
    }
}
