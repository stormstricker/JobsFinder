package jobsfinder.scrapers;

import jobsfinder.Job;
import jobsfinder.config.Config;
import jobsfinder.scrapers.MonsterScraper;

import java.util.List;

public class TestMonster {
    public static void main(String[] args) throws Exception  {
        MonsterScraper monsterScraper =
                new MonsterScraper(Config.getInstance());
        List<Job> jobs = monsterScraper.scrape();
        System.out.println();
        monsterScraper.scrapeDetails(jobs);
        System.out.println();
        System.out.println("done");
    }
}
