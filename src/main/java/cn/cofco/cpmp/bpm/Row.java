package cn.cofco.cpmp.bpm;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by xsmiler on 2017/7/9.
 */
@XmlRootElement(name = "row")
public class Row {

    List<Colum> columns;

    @XmlElement(name = "column")
    public List<Colum> getColumns() {
        return columns;
    }

    public void setColumns(List<Colum> columns) {
        this.columns = columns;
    }
}
