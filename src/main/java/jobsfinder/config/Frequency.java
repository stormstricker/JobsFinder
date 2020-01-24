package jobsfinder.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

 public class Frequency {
    private int hours;
    private int minutes;
    private int seconds;

     @XmlElement
     public int getHours() {
        return hours;
    }

     @XmlElement
     public int getMinutes() {
        return minutes;
    }

     @XmlElement
     public int getSeconds() {
        return seconds;
    }

    //setters
    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
