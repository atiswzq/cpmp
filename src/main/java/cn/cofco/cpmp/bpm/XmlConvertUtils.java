package cn.cofco.cpmp.bpm;

import cn.cofco.cpmp.entity.Splr;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xsmiler on 2017/7/9.
 */
public class XmlConvertUtils {

    public static String beanToXml(Object obj, Class<?> load) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(load);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        StringWriter writer = new StringWriter();
        marshaller.marshal(obj, writer);
        return writer.toString();
    }

    /**
     * BPM报文组装：
     * 参数格式：(Object 主报文对象，Map<subFormID, List<Object> 表单对象列表>)
     * @param base
     * @param subFormObjs
     * @return
     * @throws Exception
     */
    public static String beanConvertXml(Object base, Map<String, List<Object>> subFormObjs)  throws Exception {

        // summary
        Summary summary = new Summary();

        // 主报文解析
        List<Colum> values = XmlConvertUtils.objToColums(base);

        // subForm设置
        List<SubForm> subForms = new ArrayList<>();
        for(Map.Entry<String, List<Object>> entry: subFormObjs.entrySet()) {
            SubForm subForm = new SubForm();
            // 设置SubForm ID
            subForm.setName(entry.getKey());
            List<Row> rows = new ArrayList<>();
            for(Object object: entry.getValue()) {
                Row row = new Row();
                List<Colum> subColums = XmlConvertUtils.objToColums(object);
                row.setColumns(subColums);
                rows.add(row);
            }
            subForm.setValues(rows);
            subForms.add(subForm);
        }

        // 设置报文
        FormExport formExport = new FormExport();
        formExport.setSummary(summary);
        formExport.setValues(values);
        formExport.setSubForms(subForms);

        String result;
        try {
            result = XmlConvertUtils.beanToXml(formExport, FormExport.class);

            // 去掉 XML声明
            int i = result.indexOf("\n");
            result = result.substring(i + 1);
        } catch (Exception e) {
            result = null;
        }
        return result;
    }


    public static List<Colum> objToColums(Object object) throws Exception{
        List<Colum> values = new ArrayList<>();
        Field[] fields = object.getClass().getDeclaredFields();
        for(Field field : fields) {

            Colum colum = new Colum();
            String name = field.getName();
            field.setAccessible(true);

            // 设置属性名
            colum.setName(name);
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            String type = field.getGenericType().toString();
            Method m = object.getClass().getMethod("get" + name);
            String value = null;
            if (type.equals("class java.lang.String")){
                value = (String)m.invoke(object);
            }
            if (type.equals("class java.lang.Integer")){
                Integer tmpValue = (Integer)m.invoke(object);
                value = String.valueOf(tmpValue);
            }
            if (type.equals("class java.lang.Short")){
                Short tmpValue = (Short)m.invoke(object);
                value = String.valueOf(tmpValue);
            }
            if (type.equals("class java.lang.Double")){
                Double tmpValue = (Double)m.invoke(object);
                value = String.valueOf(tmpValue);
            }
            if (type.equals("class java.lang.Boolean")){
                Boolean tmpValue = (Boolean)m.invoke(object);
                value = String.valueOf(tmpValue);
            }
            if (type.equals("class java.util.Date")){
                Date tmpValue = (Date)m.invoke(object);
                value = String.valueOf(tmpValue);
            }

            // 设置属性值
            colum.setValue(value == null ? "" : value);
            values.add(colum);
        }
        return values;
    }

    public static void main(String[] args) throws Exception{

        Splr splr = new Splr();
        splr.setCity("12");
        splr.setCountry("12");

        Map<String, List<Object>> subFormObjs = new ConcurrentHashMap<>();
        List<Object> subForms = new ArrayList<>();
        subForms.add(splr);
        subFormObjs.put("formson_3223", subForms);

        String str = XmlConvertUtils.beanConvertXml(splr, subFormObjs);
        System.out.print(str);
    }
}
