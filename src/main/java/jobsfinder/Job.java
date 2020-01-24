package jobsfinder;

import java.util.ArrayList;
import java.util.List;

public class Job {
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


    public Job()  {}



    //getters
    public String getTitle() {
        return title;
    }

    public String getCompany() {
        return company;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public String getUrl()  {
        return url;
    }

    public String getSummary()  {
        return summary;
    }

    public String getRating() {
        return rating;
    }

    public String getReviewsCount() {
        return reviewsCount;
    }

    public String getSalary()  {
        return salary;
    }

    public String getSchedule()  {
        return schedule;
    }

    public List<String> getTags()  {
        return tags;
    }

    public List<String> getNotes()  {
        return notes;
    }

    public String getExperience()  {
        return experience;
    }

    //private setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setSummary(String summary)  {
        this.summary = summary;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setReviewsCount(String reviewsCount) {
        this.reviewsCount = reviewsCount;
    }

    public void setSalary(String salary)  {
        this.salary = salary;
    }

    public void setSchedule(String schedule)  {
        this.schedule = schedule;
    }

    public void setTags(List<String> tags)  {
        this.tags = tags;
    }

    public void addTag(String tag)  {
        this.tags.add(tag);
    }

    public void setNotes(List<String> notes)  {
        this.notes = notes;
    }

    public void addNote(String note) {
        this.notes.add(note);
    }

    public void setExperience(String experience)  {
        this.experience = experience;
    }

    public static Builder builder()  {
        return new Job().new Builder();
    }

    public class Builder {
        private Builder()  {}

        public Job build()  {return Job.this;}

        //setters
        public Builder setTitle(String title) {
            Job.this.title = title;
            return this;
        }

        public Builder setCompany(String company) {
            Job.this.company = company;
            return this;
        }

        public Builder setDescription(String description) {
            Job.this.description = description;
            return this;
        }

        public Builder setLocation(String location) {
            Job.this.location = location;
            return this;
        }

        public Builder setDate(String date) {
            Job.this.date = date;
            return this;
        }

        public Builder setUrl(String url) {
            Job.this.url = url;
            return this;
        }

        public Builder setSummary(String summary)  {
            Job.this.summary = summary;
            return this;
        }

        public Builder setRating(String rating)  {
            Job.this.rating = rating;
            return this;
        }

        public Builder setReviewsCount(String reviewsCount)  {
            Job.this.reviewsCount = reviewsCount;
            return this;
        }

        public Builder setSalary(String salary)  {
            Job.this.salary = salary;
            return this;
        }

        public Builder setSchedule(String schedule)  {
            Job.this.schedule = schedule;
            return this;
        }

        public Builder setTags(List<String> tags)  {
            Job.this.tags = tags;
            return this;
        }

        public Builder setNotes(List<String> notes)  {
            Job.this.notes = notes;
            return this;
        }

        public Builder setExperience(String experience)  {
            Job.this.experience = experience;
            return this;
        }
    }
}
