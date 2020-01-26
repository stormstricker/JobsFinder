package jobsfinder.scrapers;

import jobsfinder.Job;
import jobsfinder.Utils;
import jobsfinder.config.Config;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

import static java.util.concurrent.TimeUnit.SECONDS;

public class IndeedScraper extends JobsScraper {
    Connection.Response res;
    Map<String, String> cookies;


    public IndeedScraper(Config config)  {
        super(config);
        setUrl("https://www.indeed.com/jobs?q=" + config.getSearchQuery() +
                "&l=" + config.getLocation());

        try {
            res = Jsoup.connect(getUrl()).method(Connection.Method.GET).execute();
            cookies = res.cookies();
        }
        catch (Exception e)  {
            e.printStackTrace();
        }
    }

    @Override
    public String toString()  {
        return "IndeedScraper";
    }

    public List<Job> scrape(Document doc)  {
        List<Job> result = new ArrayList<>();

        try  {
            Elements jobElements  =
                    doc.select(".jobsearch-SerpJobCard.unifiedRow");

            for (Element jobElement: jobElements)  {
                Job job = new Job();

                Element titleElement = jobElement.selectFirst(".title");
                job.setTitle(titleElement.text());

                Element urlElement = titleElement.selectFirst("a");
                job.setUrl("https://www.indeed.com" +
                        urlElement.attr("href"));

                Element companyWrapper = jobElement.selectFirst(".sjcl");
                Element companyElement = companyWrapper.selectFirst(".company");
                Element ratingElement = companyWrapper.selectFirst(".ratingsContent");
                Element locationElement = companyWrapper.selectFirst(".location");

                job.setCompany(
                        (companyElement != null) ? companyElement.text() : "");
                job.setLocation((locationElement != null ? locationElement.text() : ""));
                job.setRating(ratingElement != null ? ratingElement.text() : "");


                Element salaryElement = jobElement.selectFirst(".salarySnippet.holisticSalary");
                job.setSalary(salaryElement != null ? salaryElement.text() : "");

                Element summaryElement = jobElement.selectFirst(".summary");
                job.setSummary(summaryElement != null ? summaryElement.text() : "");

                Element dateElement = jobElement.selectFirst(".date");
                job.setDate(dateElement != null ? dateElement.text() : "");

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
            int count = 0;
            boolean stop = false;
            do  {
                //System.out.println("Getting " + url);

                //System.out.println("cookies: " + cookies);
                Document doc = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) " +
                                "AppleWebKit/537.36 (KHTML, like Gecko) " +
                                "Chrome/79.0.3945.130 Safari/537.36")
                        .header("cookie",
            Utils.getFirstLineFromFileFromPath("setups", "IndeedCookie.txt")).get();
                Element paginationElement = doc.selectFirst(".pagination");

                List<Job> subResult = scrape(doc);
                //System.out.println("count: " + count);

                count += 10;
                url = getUrl() + "&start=" + count;
                result.addAll(subResult);

                if (paginationElement == null ||
                        !paginationElement.text().toLowerCase().contains("next") ||
                        subResult.size() == 0 || count > 990) {
                    stop = true;
                    System.out.println("No next button or something else, stopping");
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
        Random random = new Random();

        for (Job job: jobs)  {
            try {
                System.out.println("sleeping...");
                Thread.sleep(random.nextInt(300));

                Document jobDoc = Jsoup.connect(job.getUrl()).userAgent("Chrome browser").get();

                Element reviewsCount = jobDoc.selectFirst(".icl-Ratings-count");
                job.setReviewsCount(reviewsCount != null ? reviewsCount.text() : "");

                Element descriptionElement = jobDoc.selectFirst("#jobDescriptionText");
                job.setDescription(descriptionElement != null ?
                        descriptionElement.text() : "");
            }
            catch (Exception e)  {
                e.printStackTrace();
            }
        }
    }
}
