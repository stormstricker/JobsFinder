package jobsfinder;

import jobsfinder.scrapers.MonsterJobJson;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "jobs")
public class Job {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "title", columnDefinition="TEXT")
    private String title = "";
    @Column(name = "company", columnDefinition="TEXT")
    private String company = "";

    @Column(name = "summary", columnDefinition="TEXT")
    private String summary = "";
    @Column(name = "description", columnDefinition="TEXT")
    private String description = "";
    @Column(name = "location", columnDefinition="TEXT")
    private String location = "";
    private String date = "";

    @Column(name = "url", columnDefinition="TEXT")
    private String url = "";
    private String rating = "";
    private String reviewsCount = "";
    private String salary = "";

    private String schedule = "";

    @Transient
    private List<String> tags = new ArrayList<>();
    @Transient
    private List<String> notes = new ArrayList<>();

    @Column(name = "tagsValue", columnDefinition="TEXT")
    private String tagsValue = "";
    @Column(name = "notesValue", columnDefinition="TEXT")
    private String notesValue = "";

    private String experience = "";

    @Basic
    private Instant added = Instant.now();

    public Job()  {}

    public Job(MonsterJobJson monsterJobJson)  {
        setTitle(monsterJobJson.getTitle());
        setCompany(monsterJobJson.getCompany().getName());
        setLocation(monsterJobJson.getLocationText());
        setDate(monsterJobJson.getDatePostedText());
        setUrl(monsterJobJson.getJobViewUrl());
    }
    @Override
    public int hashCode() {
        return url.hashCode();
    }

    @Override
    public boolean equals(Object o)  {
        if (!(o instanceof Job))  {
            return false;
        }

        Job oj = (Job) o;
        boolean sameUrls = url.equalsIgnoreCase(oj.getUrl());
        boolean sameTitleCompanyDescriptionLocation =
                (title.equalsIgnoreCase(oj.getTitle()) &&
                    company.equalsIgnoreCase(oj.getCompany()) &&
                    description.equalsIgnoreCase(oj.getDescription()) &&
                    location.equalsIgnoreCase(oj.getLocation()));
        return sameUrls || sameTitleCompanyDescriptionLocation;
    }

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
        if (tags.size() == 0 &&
                !tagsValue.equalsIgnoreCase(""))  {
            tags = new ArrayList<>(
                    Arrays.asList(tagsValue.split("\n")));
        }

        return tags;
    }

    public List<String> getNotes()  {
        if (notes.size() == 0 &&
                !notesValue.equalsIgnoreCase(""))  {
            notes = new ArrayList<>(
                    Arrays.asList(notesValue.split("\n")));
        }


        return notes;
    }

    public String getExperience()  {
        return experience;
    }

    public int getId()  {
        return id;
    }

    public String getTagsValue() {
        return tagsValue;
    }

    public String getNotesValue() {
        return notesValue;
    }

    public Instant getAdded()  {return added;}

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
        if (url.startsWith("https://stackoverflow.com/jobs/"))  {
            url = url.replace("https://stackoverflow.com/jobs/", "");
            this.url = "https://stackoverflow.com/jobs/" + url.substring(0, url.indexOf("/"));
            System.out.println();
        }
        else {
            this.url = url;
        }
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
        tagsValue = Utils.listToString(tags, "\n");
    }

    public void addTag(String tag)  {
        this.tags.add(tag);
        tagsValue = Utils.listToString(tags, "\n");
    }

    public void setNotes(List<String> notes)  {
        this.notes = notes;
        notesValue = notes.toString();
        notesValue = Utils.listToString(notes, "\n");
    }

    public void addNote(String note) {
        this.notes.add(note);
        notesValue = notes.toString();
        notesValue = Utils.listToString(notes, "\n");
    }

    public void setExperience(String experience)  {
        this.experience = experience;
    }

    public void setId(int id)  {
        this.id = id;
    }

    public void setTagsValue(String tagsValue) {
        this.tagsValue = tagsValue;

        tags = new ArrayList<>(
                Arrays.asList(tagsValue.split("\n")));
    }

    public void setNotesValue(String notesValue) {
        this.notesValue = notesValue;

        notes = new ArrayList<>(
                Arrays.asList(notesValue.split("\n")));
    }

    public void setAdded(Instant added)  {
        this.added =added;
    }
}

