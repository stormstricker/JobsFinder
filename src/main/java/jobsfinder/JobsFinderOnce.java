package jobsfinder;

import jobsfinder.config.Config;
import jobsfinder.scrapers.GithubScraper;
import jobsfinder.scrapers.IndeedScraper;
import jobsfinder.scrapers.MonsterScraper;
import jobsfinder.scrapers.StackoverflowScraper;
import jobsfinder.writers.JobsWriter;
import jobsfinder.writers.database.DatabaseWriter;
import jobsfinder.writers.excel.ExcelWriter;
import jobsfinder.writers.json.JsonWriter;

import javax.xml.bind.util.JAXBSource;
import java.util.*;

public class JobsFinderOnce {
    public static void main(String[] args) throws Exception{
        Config config = Config.getInstance();
        IndeedScraper indeedScraper = new IndeedScraper(config);
        MonsterScraper monsterScraper = new MonsterScraper(config);
        GithubScraper githubScraper = new GithubScraper(config);
        StackoverflowScraper stackoverflowScraper = new StackoverflowScraper(config);

        List<JobsWriter> writers = Arrays.asList(new ExcelWriter(), new JsonWriter(),
                new DatabaseWriter());

        HashSet<Job> allJobs = new HashSet<>(DatabaseWriter.getAllJobs());

        List<Job> indeedJobs = indeedScraper.scrape();
        List<Job> monsterJobs = monsterScraper.scrape();
        List<Job> githubJobs = githubScraper.scrape();
        List<Job> stackoverflowJobs = stackoverflowScraper.scrape();

        Utils.removeDuplicates(allJobs,
                Arrays.asList(indeedJobs, monsterJobs, githubJobs, stackoverflowJobs));

        indeedScraper.scrapeDetails(indeedJobs);
        monsterScraper.scrapeDetails(monsterJobs);
        githubScraper.scrapeDetails(githubJobs);
        stackoverflowScraper.scrapeDetails(stackoverflowJobs);

        List<Job> uniqueJobs = new ArrayList<>();
        uniqueJobs.addAll(indeedJobs);
        uniqueJobs.addAll(monsterJobs);
        uniqueJobs.addAll(githubJobs);
        uniqueJobs.addAll(stackoverflowJobs);

        allJobs.addAll(uniqueJobs);

        for (JobsWriter writer: writers) {
            writer.append(uniqueJobs);
        }

        System.out.println("count: new jobs: " + uniqueJobs.size());
        System.out.println();

    }
}
