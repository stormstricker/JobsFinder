package jobsfinder.scrapers;

import jobsfinder.Job;
import jobsfinder.config.Config;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StackoverflowScraper extends JobsScraper {
    public StackoverflowScraper(Config config)  {
        super(config);
        setUrl("https://stackoverflow.com/jobs?q=" +
                config.getSearchQuery() + "&l=" + config.getLocation() + "&d=20&u=Km");
    }

    @Override
    public String toString()  {
        return "StackoverflowScraper";
    }

    public List<Job> scrape(Document doc)  {
        List<Job> result = new ArrayList<>();

        try {
            Elements divElements = doc.select("div");
            List<Element> jobElements = new ArrayList<>();
            for (Element divElement: divElements)  {
                if (divElement.className().equalsIgnoreCase("bb bc-black-2 fc-black-500"))  {
                    System.out.println("--> You might be interested in these jobs div!");
                    break;
                }

                if (divElement.className().contains("-job js-result"))  {
                    jobElements.add(divElement);
                }
            }

            for (Element jobElement: jobElements)  {
                Job job = new Job();

                Element titleElement = jobElement.selectFirst(".mb4.fc-black-800");
                job.setTitle(titleElement != null ? titleElement.text() : "");

                Element urlElement = titleElement.selectFirst("a");
                job.setUrl("https://stackoverflow.com" +
                        (urlElement != null ? urlElement.attr("href") : ""));

                Element companyWrapper = jobElement.selectFirst(".fc-black-700.fs-body1.mb4");
                Elements companyElements =
                        companyWrapper == null ? null : companyWrapper.select("span");

                if (companyElements != null && companyElements.size() > 0) {
                    Element companyElement = companyElements.get(0);
                    job.setCompany(companyElement != null ? companyElement.text() : "");

                    if (companyElements.size() > 1) {
                        Element locationElement = companyElements.get(1);
                        job.setLocation(locationElement != null ? locationElement.text() : "");
                    }
                }

                Element tagsWrapperElement =
                        jobElement.selectFirst(".ps-relative.d-inline-block.z-selected");
                Elements tagElements =
                        tagsWrapperElement == null ? null : tagsWrapperElement.select("a");
                if (tagElements != null) {
                    for (Element tagElement : tagElements) {
                        job.addTag(tagElement == null ? "" : tagElement.text());
                    }
                }

                Element notesWrapper = jobElement.selectFirst(".mt2.fs-caption");
                Elements notesElements = notesWrapper.select(".grid--cell");
                for (int i = 0; i < notesElements.size(); i++)  {
                    Element noteElement = notesElements.get(i);
                    String noteText = noteElement != null ? noteElement.text() : "";

                    if (i == 0)  {
                        job.setDate(noteText);
                        continue;
                    }
                    else if (noteText.contains("$") ||
                            (noteText.contains("-") && noteText.contains("k")) ||
                            (noteText.contains("—") && noteText.contains("k"))  ||
                            (noteText.contains("–") && noteText.contains("k")))  {
                        job.setSalary(noteText);
                    }
                    else if (!noteText.equalsIgnoreCase("•")) {
                        job.addNote(noteText);
                    }
                }

                result.add(job);
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
                Thread.sleep(random.nextInt(5000));

                Document doc = Jsoup.connect(job.getUrl()).userAgent("Chrome browser")
                        .timeout(10000).validateTLSCertificates(false).get();

                Element dateElement = doc.selectFirst(".grid.mb24.fs-body1.fc-black-500");
                job.setDate(dateElement != null ? dateElement.text() : "");

                Element jobDetailsWrapper = doc.selectFirst(".grid.job-details--about");
                Elements detailsElements = jobDetailsWrapper.select(".mb8");
                for (Element detailElement: detailsElements)  {
                    if (detailElement != null)  {
                        Elements spanElements = detailElement.select("span");

                        if (spanElements == null || spanElements.size() < 2)  {
                            continue;
                        }

                        if (detailElement.text().toLowerCase().contains("job type"))  {
                            job.setSchedule(spanElements.get(1).text());
                        }
                        else if (detailElement.text().toLowerCase().contains("experience"))  {
                            job.setExperience(spanElements.get(1).text());
                        }
                    }
                }

                Elements mb32Elements = doc.select(".mb32");
                if (mb32Elements != null && mb32Elements.size() != 0)  {
                    for (Element mb32Element: mb32Elements)  {
                        if (mb32Element.text().toLowerCase().contains("job description"))  {
                            job.setDescription(mb32Element.text());
                        }
                    }
                }

            }
            catch (Exception e)  {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Job> scrape()  {
        List<Job> result = new ArrayList<>();
        Random random = new Random();

        try {
            String url = getUrl();
            boolean stop = false;
            do  {
                Thread.sleep(random.nextInt(5000));
                Document doc = Jsoup.connect(url).timeout(10000)
                        .userAgent("Chrome browser").validateTLSCertificates(false).get();
                System.out.println("Getting " + url);
                Element paginationElement = doc.selectFirst(".s-pagination");

                List<Job> subResult = scrape(doc);
                result.addAll(subResult);


                if (paginationElement == null ||
                        !paginationElement.text().toLowerCase().contains("next") ||
                        subResult.size() == 0) {
                    stop = true;
                    System.out.println("No next button or something else, stopping");
                }
                else if (paginationElement != null) {
                    Elements linkElements = paginationElement.select("a");
                    Element nextElement = linkElements.get(linkElements.size() - 1);
                    url = "https://stackoverflow.com" +
                            nextElement.attr("href");
                    System.out.println("Next page URL: " + url);
                }
            } while (!stop);
        }
        catch (Exception e)  {
            e.printStackTrace();
        }

        return result;
    }
}
