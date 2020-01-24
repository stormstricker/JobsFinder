package jobsfinder.scrapers;

import jobsfinder.Job;
import jobsfinder.config.Config;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class GithubScraper extends JobsScraper {
    public GithubScraper(Config config)  {
        super(config);
        setUrl("https://jobs.github.com/positions?utf8=%E2%9C%93&description=" +
            config.getSearchQuery() + "&location=" + config.getLocation());
    }

    public List<Job> scrape(Document doc)  {
        List<Job> result = new ArrayList<>();

        try {
            Elements jobElements = doc.select(".job");

            for (Element jobElement : jobElements) {
                Job job = new Job();

                Element titleElement = jobElement.selectFirst(".title");
                job.setTitle(titleElement != null ? titleElement.text() : "");

                Element urlElement = titleElement.selectFirst("a");
                job.setUrl(urlElement != null ? urlElement.attr("href") : "");

                Element companyElement = jobElement.selectFirst(".company");
                job.setCompany(companyElement != null ? companyElement.text() : "");

                Element scheduleElement = jobElement.selectFirst(".fulltime");
                job.setSchedule(scheduleElement != null ? scheduleElement.text() : "");

                Element locationElement = jobElement.selectFirst(".location");
                job.setLocation(locationElement != null ? locationElement.text() : "");

                Element timeElement = jobElement.selectFirst(".when.relatize.relatized");
                job.setDate(timeElement != null ? timeElement.text() : "");

                result.add(job);
            }
        }
        catch (Exception e)  {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public List<Job> scrape()  {
        List<Job> result = new ArrayList<>();

        try {
            String url = getUrl();
            boolean stop = false;
            do  {
                Document doc = Jsoup.connect(url).get();
                Element paginationElement = doc.selectFirst(".pagination");

                List<Job> subResult = scrape(doc);
                result.addAll(subResult);


                if (paginationElement == null ||
            !paginationElement.text().toLowerCase().contains("more awesome jobs") ||
            subResult.size() == 0) {
                    stop = true;
                    System.out.println("No next button or something else, stopping");
                }
                else if (paginationElement != null) {
                    Element linkPaginationElement = paginationElement.selectFirst("a");
                    url = "https://jobs.github.com" +
                            linkPaginationElement.attr("href");
                    System.out.println("Next page URL: " + url);
                }
            } while (!stop);
        }
        catch (Exception e)  {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public void scrapeDetails(List<Job> jobs)  {
        for (Job job: jobs)  {
            try {
                Document doc = Jsoup.connect(job.getUrl()).get();
                Element descriptionElement = doc.selectFirst(".column.main");
                job.setDescription(descriptionElement != null ?
                        descriptionElement.text() : "");
            }
            catch (Exception e)  {
                e.printStackTrace();
            }
        }
    }
}
