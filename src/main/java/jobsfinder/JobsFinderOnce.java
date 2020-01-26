package jobsfinder;

import jobsfinder.config.Config;
import jobsfinder.scrapers.*;
import jobsfinder.writers.JobsWriter;
import jobsfinder.writers.database.DatabaseWriter;
import jobsfinder.writers.excel.ExcelWriter;
import jobsfinder.writers.json.JsonWriter;

import javax.xml.bind.util.JAXBSource;
import java.util.*;

public class JobsFinderOnce {
    public static void main(String[] args) throws Exception{
        Config config = Config.getInstance();

        List<JobsScraper> scrapers = Utils.getScrapers(config);
        List<JobsWriter> writers = Arrays.asList(new ExcelWriter(), new JsonWriter(),
                new DatabaseWriter());

        HashSet<Job> allJobs = new HashSet<>(DatabaseWriter.getAllJobs());

        Map<JobsScraper, List<Job>> jobsMap = new HashMap<>();
        List<Job> uniqueJobs = new ArrayList<>();
        for (JobsScraper scraper: scrapers)  {
            jobsMap.put(scraper, scraper.scrape());
            Utils.removeDuplicates(allJobs,
                    Arrays.asList(jobsMap.get(scraper)));
            scraper.scrapeDetails(jobsMap.get(scraper));
            uniqueJobs.addAll(jobsMap.get(scraper));
        }

        allJobs.addAll(uniqueJobs);
        for (JobsWriter writer: writers) {
            writer.append(uniqueJobs);
        }

        System.out.println("count: new jobs: " + uniqueJobs.size());
        System.out.println();

    }
}
