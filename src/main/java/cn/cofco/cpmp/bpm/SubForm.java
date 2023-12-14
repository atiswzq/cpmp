package cn.cofco.cpmp.bpm;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

/**
 * Created by xsmiler on 2017/7/9.
 */
public class SubForm {

    String name;
    List<Row> values;

    @XmlAttribute(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElementWrapper(name = "values")
    @XmlElement(name = "row")
    public List<Row> getValues() {
        return values;
    }

    public void setValues(List<Row> values) {
        this.values = values;
    }
}
