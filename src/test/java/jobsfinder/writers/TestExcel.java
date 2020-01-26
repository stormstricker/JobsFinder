package jobsfinder.writers;

import jobsfinder.Job;
import jobsfinder.config.Config;
import jobsfinder.scrapers.MonsterScraper;
import jobsfinder.writers.excel.ExcelKing;
import jobsfinder.writers.excel.ExcelWriter;

import java.util.List;

public class TestExcel {
    public static void main(String[] args) throws Exception  {
        ExcelWriter excelWriter = new ExcelWriter();
        /*Job job = new Job();
        job.setTitle("test");
        excelWriter.append(job);

        Job job1 = new Job();
        job1.setTitle("test2");
        job1.setSchedule("full-time");
        job1.setSummary("test summary");
        excelWriter.append(job1);

        MonsterScraper monsterScraper = new MonsterScraper(Config.getInstance());
        List<Job> monsterJobs = monsterScraper.scrape();
        excelWriter.writeHeader();
        excelWriter.write(monsterJobs);*/

        //excelWriter.writeHeader();

        System.out.println(excelWriter.getExcelKing().getLastRow("jobs"));
    }
}


/*
private String title;
private String company;
private String summary;
private String description;
private String location;
private String date;
private String url;
private String rating;
private String reviewsCount;
private String salary;

private String schedule;

private List<String> tags = new ArrayList<>();
private List<String> notes = new ArrayList<>();

private String experience;
 */