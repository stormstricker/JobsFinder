
package jobsfinder.scrapers;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Company {

    @SerializedName("HasCompanyAddress")
    private Boolean hasCompanyAddress;
    @SerializedName("LogoLink")
    private String logoLink;
    @SerializedName("Name")
    private String name;

    public Boolean getHasCompanyAddress() {
        return hasCompanyAddress;
    }

    public void setHasCompanyAddress(Boolean hasCompanyAddress) {
        this.hasCompanyAddress = hasCompanyAddress;
    }

    public String getLogoLink() {
        return logoLink;
    }

    public void setLogoLink(String logoLink) {
        this.logoLink = logoLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
