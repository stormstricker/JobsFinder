package jobsfinder.writers.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import jobsfinder.Job;
import jobsfinder.Utils;
import jobsfinder.writers.JobsWriter;

import java.util.List;

public class JsonWriter implements JobsWriter {
    private Gson gson = new Gson();
    private Gson prettyJson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public String toString()  {
        return "JsonWriter";
    }

    @Override
    public void write(Job job)  {
        Utils.clearFileFromPath("", "jobs.json");
        Utils.addLineToFileFromPath("", "jobs.json", getPrettyJson(job));
    }

    @Override
    public void append(Job job)  {
        Gson gson = new Gson();
        String jsonJob = gson.toJson(job);

        Utils.addLineToFileFromPath("", "jobs.json", getPrettyJson(job));
    }

    public void append(List<Job> jobs)  {
        for (Job job: jobs)  {
            append(job);
        }
    }

    public void write(List<Job> jobs)  {
        Utils.clearFileFromPath("", "jobs.json");

        for (Job job: jobs)  {
            append(job);
        }
    }

    public String getPrettyJson(Job job)  {
        String jsonJob = gson.toJson(job);
        JsonElement jsonElement = new JsonParser().parse(jsonJob);
        return prettyJson.toJson(jsonElement);
    }
}
