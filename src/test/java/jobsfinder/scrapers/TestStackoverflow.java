package jobsfinder.scrapers;

import jobsfinder.Job;
import jobsfinder.config.Config;
import jobsfinder.scrapers.StackoverflowScraper;

import java.util.List;

public class TestStackoverflow {
    public static void main(String[] args) throws Exception  {
        StackoverflowScraper stackoverflowScraper =
                new StackoverflowScraper(Config.getInstance());
        List<Job> jobs = stackoverflowScraper.scrape();
        System.out.println();
        stackoverflowScraper.scrapeDetails(jobs);
        System.out.println();
    }
}
