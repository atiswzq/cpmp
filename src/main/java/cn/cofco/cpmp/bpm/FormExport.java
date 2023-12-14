package cn.cofco.cpmp.bpm;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by xsmiler on 2017/7/9.
 */
@XmlRootElement(name = "formExport")
@XmlType(propOrder = {"version","summary","values","subForms"})
public class FormExport {

    String version = "2.0";
    Summary summary;
    List<Colum> values;
    List<SubForm> subForms;

    @XmlAttribute(name = "version")
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @XmlElement(name = "summary")
    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    @XmlElementWrapper(name = "values")
    @XmlElement(name = "column")
    public List<Colum> getValues() {
        return values;
    }

    public void setValues(List<Colum> values) {
        this.values = values;
    }

    @XmlElementWrapper(name = "subForms")
    @XmlElement(name = "subForm")
    public List<SubForm> getSubForms() {
        return subForms;
    }

    public void setSubForms(List<SubForm> subForms) {
        this.subForms = subForms;
    }
}
