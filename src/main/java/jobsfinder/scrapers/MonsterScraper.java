package jobsfinder.scrapers;

import jobsfinder.Job;
import jobsfinder.config.Config;

import java.util.ArrayList;
import java.util.List;

public class MonsterScraper extends JobsScraper {
    public MonsterScraper(Config config)  {
        super(config);
        setUrl("https://www.monster.com/jobs/search/?q=" + config.getSearchQuery() +
                "&where=" + config.getLocation() + "&intcid=skr_navigation_nhpso_searchMain");
    }

    public List<Job> scrape()  {
        List<Job> result = new ArrayList<>();

        return result;
    }
}
