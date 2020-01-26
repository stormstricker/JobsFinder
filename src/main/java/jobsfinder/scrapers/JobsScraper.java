package jobsfinder.scrapers;

import jobsfinder.Job;
import jobsfinder.config.Config;

import java.util.List;

public abstract class JobsScraper {
    protected String url;
    protected Config config;

    public JobsScraper(Config config)  {
        this.config = config;
    }

    public abstract List<Job> scrape();

    public abstract String toString();

    public void scrapeDetails(List<Job> jobs)  {}

    //getters
    public String getUrl() {
        return url;
    }

    public Config getConfig()  {return config;}

    //setters
    public void setUrl(String url) {
        this.url = url;
    }

    public void setConfig(Config config)  {
        this.config = config;
    }
}
