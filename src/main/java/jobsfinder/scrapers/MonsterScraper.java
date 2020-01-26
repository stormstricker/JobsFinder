package jobsfinder.scrapers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jobsfinder.Job;
import jobsfinder.config.Config;
import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MonsterScraper extends JobsScraper {
    protected String jsonUrl;

    public MonsterScraper(Config config)  {
        super(config);
        setUrl("https://www.monster.com/jobs/search/?q=" +
                config.getSearchQuery() + "&where="  +
                config.getLocation() + "&intcid=skr_navigation_nhpso_searchMain");

        jsonUrl = "https://www.monster.com/jobs/search/pagination/?q=" +
                config.getSearchQuery() + "&where=" +
                config.getLocation() + "&intcid=skr_navigation_nhpso_searchMain&isDynamicPage=true" +
                "&isMKPagination=true";
    }

    @Override
    public String toString()  {
        return "MonsterScraper";
    }

    @Override
    public List<Job> scrape()  {
        List<Job> result = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(getUrl()).userAgent("Chrome browser").get();
            Element resultsElement = doc.getElementById("ResultsScrollable");
            Element muxSearchElement = resultsElement.selectFirst(".mux-search-results");
            int dataResultsTotal = Integer.valueOf(muxSearchElement.attr("data-results-total"));

            int count = 1;
            while (result.size() < dataResultsTotal) {
                HttpUrl.Builder urlBuilder = HttpUrl.parse(jsonUrl).newBuilder();
                urlBuilder.addQueryParameter("page", String.valueOf(count++));
                String url = urlBuilder.build().toString();

                //System.out.println("Getting " + url);
                Request request = new Request.Builder()
                        .url(url)
                        .addHeader("Content-Type", "application/json; charset=utf-8")
                        .addHeader("User-Agent", "Chrome browser")
                        .addHeader("Accept-Language", Locale.US.getLanguage()).get()
                        .build();

                OkHttpClient client = new OkHttpClient();
                Call call = client.newCall(request);
                Response response = call.execute();
                String responseBody = response.body().string();
                //System.out.println(responseBody);

                Gson gson = new Gson();
                List<MonsterJobJson> resultMonster = gson.fromJson(
                        responseBody, new TypeToken<List<MonsterJobJson>>() {
                        }.getType());

                for (MonsterJobJson monsterJobJson: resultMonster)  {
                    if (monsterJobJson.getTitle() == null)  {
                        //it is an ad, going to the next job
                        continue;
                    }

                    /*if (monsterJobJson.getIsAggregated().equalsIgnoreCase("false"))  {
                        System.err.println("it is possibly an ad");
                        continue;
                    }*/

                    result.add(new Job(monsterJobJson));
                }

            }
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
            try  {
                System.out.println("sleeping...");
                Thread.sleep(random.nextInt(1500));

                Document doc = Jsoup.connect(job.getUrl()).userAgent("Chrome browser").get();
                Element detailsElement = doc.selectFirst(".details-content");
                job.setDescription(detailsElement != null ? detailsElement.text() : "");
            }
            catch (Exception e)  {
                job.setDescription("unavailable");
                e.printStackTrace();
            }
        }
    }
}
