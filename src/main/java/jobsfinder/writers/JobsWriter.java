package jobsfinder.writers;

import jobsfinder.Job;

import java.util.List;

public interface JobsWriter {
    void write(Job job);
    void append(Job job);
    void append(List<Job> jobs);
    String toString();
}
