package jobsfinder.writers.excel;

import com.sun.webkit.network.Util;
import jobsfinder.Job;
import jobsfinder.Utils;
import jobsfinder.writers.JobsWriter;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExcelWriter implements JobsWriter {
    private String sheetName = "jobs";
    private ExcelKing excelKing = new ExcelKing("jobs.xlsx",
            sheetName);
    List<String> columns = Arrays.asList("title", "company", "summary",
            "description", "location", "date", "url", "rating", "reviewsCount",
            "salary", "schedule", "tags", "notes", "experience", "added");
    CellStyle headerStyle = excelKing.getWorkbook().createCellStyle();
    {
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFillForegroundColor(IndexedColors.ORANGE.getIndex());

        if (excelKing.isWorkbookNew())  {
            writeHeader();
        }
    }

    @Override
    public String toString()  {
        return "ExcelWriter";
    }

    public void write(List<Job> jobs)  {
        writeHeader();
        excelKing.append(jobsToRow(jobs), sheetName);
    }

    public void append(List<Job> jobs)  {
        excelKing.append(jobsToRow(jobs), sheetName);
    }

    @Override
    public void append(Job job)  {
        excelKing.append(Arrays.asList(jobToRow(job)),
                sheetName);
    }

    @Override
    public void write(Job job)  {
        writeHeader();
        excelKing.append(Arrays.asList(
                jobToRow(job)), sheetName);
    }

    public List<ExcelRow> jobsToRow(List<Job> jobs)  {
        List<ExcelRow> result = new ArrayList<>();
        for (Job job: jobs)  {
            result.add(jobToRow(job));
        }
        return result;
    }

    public ExcelRow jobToRow(Job job)  {
        return new ExcelRow(Arrays.asList(
            job.getTitle(), job.getCompany(), job.getSummary(),
            job.getDescription(), job.getLocation(), job.getDate(),
            job.getUrl(), job.getRating(), job.getReviewsCount(),
            job.getSalary(), job.getSchedule(),
            Utils.listToString(job.getTags(), "\n"),
            Utils.listToString(job.getNotes(), "\n"),
            job.getExperience(), job.getAdded().toString()));
    }

    public ExcelKing getExcelKing()  {
        return excelKing;
    }

    public String getSheetName()  {
        return sheetName;
    }

    public void writeHeader()  {
        excelKing.write(Arrays.asList(
                new ExcelRow(columns, headerStyle)), sheetName);
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