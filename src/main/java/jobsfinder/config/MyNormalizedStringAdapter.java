package jobsfinder.config;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class MyNormalizedStringAdapter extends XmlAdapter<String, String> {
    @Override
    public String marshal(String text) {
        return text.trim();
    }

    @Override
    public String unmarshal(String v) throws Exception {
        return v.trim();
    }
}