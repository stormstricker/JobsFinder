package jobsfinder.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

@XmlRootElement(name = "sites")
public class Sites {
    private List<String> sites;

    @XmlElement(name = "site")
    @XmlJavaTypeAdapter(MyNormalizedStringAdapter.class)
    public List<String> getSites() {
        return sites;
    }

    public void setSites(List<String> sites) {
        this.sites = sites;
    }
}
