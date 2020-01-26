package jobsfinder.writers;

import jobsfinder.Job;
import jobsfinder.TestUtils;
import jobsfinder.config.Config;
import jobsfinder.scrapers.StackoverflowScraper;
import jobsfinder.writers.json.JsonWriter;

import java.util.List;

public class TestJson {
    public static void main(String[] args) throws  Exception  {
        JsonWriter jsonWriter = new JsonWriter();
        List<Job> testJobs = TestUtils.getTestJobs();
        jsonWriter.write(testJobs.get(0));
        jsonWriter.append(testJobs.get(1));

        StackoverflowScraper stackoverflowScraper = new StackoverflowScraper(Config.getInstance());
        List<Job> jobs = stackoverflowScraper.scrape();
        jsonWriter.write(jobs);
    }
}
