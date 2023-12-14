package cn.cofco.cpmp.bpm;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by xsmiler on 2017/7/9.
 */
@XmlRootElement(name = "summary")
public class Summary {
    String name = "id=";

    @XmlAttribute(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
