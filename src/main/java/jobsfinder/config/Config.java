package jobsfinder.config;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.FileReader;
import java.util.List;

@XmlRootElement(name = "config")
public class Config {
    private String searchQuery;
    private String location;
    private Frequency frequency;
    private Sites sites;


    private Config()  {}

    public static Config getInstance() throws Exception  {
        JAXBContext context = JAXBContext.newInstance(Config.class);
        return (Config) context.createUnmarshaller()
                .unmarshal(new FileReader("./setups/config.xml"));
    }

    @XmlElement
    @XmlJavaTypeAdapter(MyNormalizedStringAdapter.class)
    public String getSearchQuery() {
        return searchQuery;
    }

    @XmlElement
    @XmlJavaTypeAdapter(MyNormalizedStringAdapter.class)
    public String getLocation() {
        return location;
    }

    @XmlElement
    public Frequency getFrequency() {
        return frequency;
    }

    @XmlElement(name = "sites")
    public Sites getSites() {
        return sites;
    }

    //setters
    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public void setSites(Sites sites) {
        this.sites = sites;
    }
}


