package jobsfinder.scrapers;

import jobsfinder.Job;
import jobsfinder.config.Config;
import jobsfinder.scrapers.IndeedScraper;

import java.util.List;

public class TestIndeed {
    public static void main(String[] args) throws Exception {
        IndeedScraper indeedScraper = new IndeedScraper(Config.getInstance());
        List<Job> jobs = indeedScraper.scrape();

        System.out.println();
        //test selenium
      //  List<Job> seleniumJobs = indeedScraper.scrapeSelenium();
        //System.out.println();

    }
}
