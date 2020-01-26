package jobsfinder.scrapers;

import jobsfinder.Job;
import jobsfinder.config.Config;
import jobsfinder.scrapers.GithubScraper;

import java.util.List;

public class TestGithub {
    public static void main(String[] args) throws Exception {
        GithubScraper githubScraper = new GithubScraper(Config.getInstance());
        List<Job> jobs = githubScraper.scrape();
        System.out.println();
        githubScraper.scrapeDetails(jobs);
        System.out.println();
    }
}
