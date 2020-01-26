package jobsfinder;

import jobsfinder.config.Config;
import jobsfinder.scrapers.*;
import jobsfinder.writers.JobsWriter;
import jobsfinder.writers.database.DatabaseWriter;
import jobsfinder.writers.excel.ExcelWriter;
import jobsfinder.writers.json.JsonWriter;

import java.util.*;
import java.util.concurrent.*;

public class JobsFinder {
    private Config config;
    private int frequencySeconds;
    private List<JobsScraper> scrapers;
    private List<JobsWriter> writers;

    public JobsFinder() throws Exception {
        config = Config.getInstance();
        frequencySeconds = config.getFrequency().getSeconds() +
                config.getFrequency().getMinutes() * 60 +
                config.getFrequency().getHours() * 60 * 60;

        scrapers = Utils.getScrapers(config);

        writers = Arrays.asList(new DatabaseWriter(), new JsonWriter(), new ExcelWriter());
    }

    private ScheduledExecutorService MAIN_EXECUTOR = Executors
            .newScheduledThreadPool(5);
    private Queue<List<Job>> uniqueJobs = new LinkedBlockingDeque<>();
    private Set<Job> allJobs = new HashSet<>(DatabaseWriter.getAllJobs());

    public void start()  {
        System.out.println("Starting the runs with " + frequencySeconds + "-second frequency");

        MAIN_EXECUTOR.scheduleAtFixedRate(scrapersTask,
                0, frequencySeconds, TimeUnit.SECONDS);
        MAIN_EXECUTOR.scheduleAtFixedRate(writersTask,
                60, 5, TimeUnit.SECONDS);
    }


    private Runnable scrapersTask = new Runnable()  {
        @Override
        public void run()  {
            try  {
                System.out.println("--> Inside scrapersTask");

                final Map<JobsScraper, List<Job>> scrapedJobsScrapersMap =
                        Collections.synchronizedMap(new HashMap<>());
                for (JobsScraper scraper: scrapers)  {
                    System.out.println("---> starting " + scraper);

                    final List<Job> jobs = scraper.scrape();
                    scrapedJobsScrapersMap.put(scraper, jobs);
                    System.out.println("----> scraped " + jobs.size() + " jobs");
                }

                List<Job> uniqueScrapedJobs = Collections.synchronizedList(new ArrayList<>());
                for (JobsScraper scraper: scrapedJobsScrapersMap.keySet())  {
                    List<Job> scrapedJobList = scrapedJobsScrapersMap.get(scraper);

                    Utils.removeDuplicates(allJobs, Arrays.asList(scrapedJobList));
                    System.out.println(scraper +
                            ", new jobs: " + scrapedJobList.size());
                    System.out.println("-------> scraping details for unique jobs");
                    scraper.scrapeDetails(scrapedJobList);
                    uniqueScrapedJobs.addAll(scrapedJobList);
                    allJobs.addAll(scrapedJobList);
                }

                if (uniqueScrapedJobs.size() > 0)  {
                    uniqueJobs.add(uniqueScrapedJobs);
                }
            }
            catch (Exception e)  {
                e.printStackTrace();
            }
        }
    };

    private Runnable writersTask = new Runnable()  {
        @Override
        public void run()  {
            try  {
                //System.out.println("--> Inside writersTask");

                List<Job> jobs = uniqueJobs.poll();
                while (jobs != null)  {
                    for (JobsWriter writer: writers)  {
                        System.out.println("----> starting " + writer);
                        writer.append(jobs);
                    }

                    jobs = uniqueJobs.poll();
                }
            }
            catch (Exception e)  {
                e.printStackTrace();
            }
        }
    };

    public static void main(String[] args) throws  Exception  {
        JobsFinder jobsFinder = new JobsFinder();
        jobsFinder.start();
    }
}
