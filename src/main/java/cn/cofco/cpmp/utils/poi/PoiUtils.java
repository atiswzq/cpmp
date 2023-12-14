package cn.cofco.cpmp.utils.poi;

import cn.cofco.cpmp.dto.MatInfIoDto;
import cn.cofco.cpmp.entity.BidProjOnMatDtl;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.utils.ReflcUtils;
import cn.cofco.cpmp.utils.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by xsmiler on 2017/7/18.
 * Modified by xujy on 2017/10/29
 */
public class PoiUtils {

    private static Logger LOG = LoggerManager.getSysLog();

    /**
     *
     * @param fileName 文件名
     * @param sheetName sheet名字
     * @param title 主题名
     * @param objs Excel文件对象
     * @throws Exception
     */
    public static XSSFWorkbook exportExcel(String fileName, String sheetName, String title, List<List<List<String>>> objs) throws Exception{

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet(sheetName);


        // 行数
        int rowNum = 1;
        // 设置cell风格
        XSSFCellStyle titleCellStyle = workbook.createCellStyle();
        titleCellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        titleCellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        titleCellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
        titleCellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        titleCellStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);
        titleCellStyle.setWrapText(true);

        // 设置字体
        XSSFFont titleCellFont = workbook.createFont();
        titleCellFont.setFontName("黑体");
        titleCellFont.setFontHeightInPoints((short) 12);
        titleCellStyle.setFont(titleCellFont);

        // 表头内容
        List<List<String>> titleObjs = new ArrayList<>();
        List<String> titleRowObj1 = Arrays.asList("序号", "评价指标", "", "", "评审意见", "", "", "", "综合得分");
        List<String> titleRowObj2 = Arrays.asList("", "一级指标", "序号", "二级指标", "环境安全部", "使用部门", "物流总库", "辅料采购部", "");
        titleObjs.add(titleRowObj1);
        titleObjs.add(titleRowObj2);

        for(List<String> titleRowObj : titleObjs) {
            XSSFRow titleRow = spreadsheet.createRow(rowNum++);
            titleRow.setHeight((short) 800);
            int cellNum = 1;
            for (String titleCell : titleRowObj) {
                XSSFCell cell = (XSSFCell) titleRow.createCell(cellNum++);
                cell.setCellValue(titleCell);
                cell.setCellStyle(titleCellStyle);
            }
        }

        // 合并单元格
        spreadsheet.addMergedRegion(new CellRangeAddress(1,2,1,1));
        spreadsheet.addMergedRegion(new CellRangeAddress(1,1,2,4));
        spreadsheet.addMergedRegion(new CellRangeAddress(1,1,5,8));
        spreadsheet.addMergedRegion(new CellRangeAddress(1,2,9,9));

        // 考核内容
        for(List<List<String>> kpiTypeObj : objs) {
            int kpiInitRowNum = rowNum;

            for(List<String> kpiObj : kpiTypeObj) {
                XSSFRow titleRow = spreadsheet.createRow(rowNum++);
                titleRow.setHeight((short) 600);
                int cellNum = 1;
                for(String value : kpiObj) {
                    XSSFCell cell = (XSSFCell) titleRow.createCell(cellNum++);
                    cell.setCellValue(value);
                    cell.setCellStyle(titleCellStyle);
                }
            }
            spreadsheet.addMergedRegion(new CellRangeAddress(kpiInitRowNum,kpiInitRowNum + kpiTypeObj.size() - 1,1,1));
            spreadsheet.addMergedRegion(new CellRangeAddress(kpiInitRowNum,kpiInitRowNum + kpiTypeObj.size() - 1,2,2));
        }

        // 表头内容
        List<List<String>> footObjs = new ArrayList<>();
        List<String> footRowObj1 = Arrays.asList("合计", "", "", "", "", "", "", "", "");
        List<String> footRowObj2 = Arrays.asList("评价等级及结论", "", "", "", "", "", "", "", "");
        List<String> footRowObj3 = Arrays.asList("评价意见", "", "环境安全部意见", "", "", "", "", "", "");
        List<String> footRowObj4 = Arrays.asList("", "", "使用部门意见", "", "", "", "", "", "");
        List<String> footRowObj5 = Arrays.asList("", "", "物料总库意见", "", "", "", "", "", "");
        List<String> footRowObj6 = Arrays.asList("", "", "辅料采购部意见", "", "", "", "", "", "");
        footObjs.add(footRowObj1);
        footObjs.add(footRowObj2);
        footObjs.add(footRowObj3);
        footObjs.add(footRowObj4);
        footObjs.add(footRowObj5);
        footObjs.add(footRowObj6);

        int footBeginRowNum = rowNum;
        for(List<String> footRowObj : footObjs) {
            XSSFRow footRow = spreadsheet.createRow(rowNum++);
            footRow.setHeight((short) 600);
            int cellNum = 1;
            for (String titleCell : footRowObj) {
                XSSFCell cell = (XSSFCell) footRow.createCell(cellNum++);
                cell.setCellValue(titleCell);
                cell.setCellStyle(titleCellStyle);
            }
        }

        // 设置合并脚单元格
        spreadsheet.addMergedRegion(new CellRangeAddress(footBeginRowNum,footBeginRowNum,1,4));
        footBeginRowNum++;
        spreadsheet.addMergedRegion(new CellRangeAddress(footBeginRowNum,footBeginRowNum,1,4));
        spreadsheet.addMergedRegion(new CellRangeAddress(footBeginRowNum,footBeginRowNum,5,9));
        footBeginRowNum++;
        spreadsheet.addMergedRegion(new CellRangeAddress(footBeginRowNum,footBeginRowNum + 3,1,2));
        spreadsheet.addMergedRegion(new CellRangeAddress(footBeginRowNum,footBeginRowNum,3,5));
        spreadsheet.addMergedRegion(new CellRangeAddress(footBeginRowNum,footBeginRowNum,6,9));
        footBeginRowNum++;
        spreadsheet.addMergedRegion(new CellRangeAddress(footBeginRowNum,footBeginRowNum,3,5));
        spreadsheet.addMergedRegion(new CellRangeAddress(footBeginRowNum,footBeginRowNum,6,9));
        footBeginRowNum++;
        spreadsheet.addMergedRegion(new CellRangeAddress(footBeginRowNum,footBeginRowNum,3,5));
        spreadsheet.addMergedRegion(new CellRangeAddress(footBeginRowNum,footBeginRowNum,6,9));
        footBeginRowNum++;
        spreadsheet.addMergedRegion(new CellRangeAddress(footBeginRowNum,footBeginRowNum,3,5));
        spreadsheet.addMergedRegion(new CellRangeAddress(footBeginRowNum,footBeginRowNum,6,9));

        // 单元格内容自动换行
        spreadsheet.autoSizeColumn(1,true);

        // 设置列宽度
        spreadsheet.setColumnWidth(0, 2000);
        spreadsheet.setColumnWidth(1, 1000);
        spreadsheet.setColumnWidth(2, 3000);
        spreadsheet.setColumnWidth(3, 1000);
        spreadsheet.setColumnWidth(4, 8000);
        spreadsheet.setColumnWidth(5, 2000);
        spreadsheet.setColumnWidth(6, 2000);
        spreadsheet.setColumnWidth(7, 2000);
        spreadsheet.setColumnWidth(8, 2000);
        spreadsheet.setColumnWidth(9, 1000);
//        FileOutputStream out = new FileOutputStream(
//                new File(fileName.concat(".xlsx")));
//        workbook.write(out);
//        out.close();
//        System.out.print(new File(fileName.concat(".xlsx")).getAbsolutePath());
//        System.out.println("导出成功！");
        return workbook;
    }

    /**
     * 导入考察表模板
     * @param file xlsx文件
     * @return
     * @throws Exception
     */
    public static List<List<String>> importExcel(MultipartFile file) throws Exception{

        InputStream input = file.getInputStream();
        XSSFWorkbook wb = new XSSFWorkbook(input);

        List<List<String>> resultList = new ArrayList<>();

        for(int sheetNum = 0; sheetNum < wb.getNumberOfSheets(); sheetNum++) {
            XSSFSheet xssfSheet = wb.getSheetAt(sheetNum);
            if(xssfSheet == null) {
                continue;
            }
            int totalRows = xssfSheet.getLastRowNum();

            for(int rowNum = 3; rowNum < 27; rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                List<String> rowResultList = new ArrayList<>();
                if(xssfRow != null) {
                    int totalCells = xssfRow.getLastCellNum();
                    for(int cellNum = 0; cellNum <= totalCells + 1; cellNum++) {
                        XSSFCell cell = xssfRow.getCell(cellNum);
                        if (null == cell) {
                            continue;
                        }
                        if(cellNum >= 3) {
                            if (cellNum == 9) {
                                rowResultList.add(String.valueOf(cell.getNumericCellValue()).substring(0, String.valueOf(cell.getNumericCellValue()).lastIndexOf(".")));
                            } else {
                                rowResultList.add(cell.getStringCellValue());
                            }
                        }
                    }
                }
                if (rowNum >= 3 && rowNum < 27) {
                    resultList.add(rowResultList);
                }
            }

        }
        return resultList;
    }


    /**
     * 从Excel中导入，生成对象列表，支持xls，xlsx两种格式
     * @param file 上送文件
     * @param fields 文件对应DTO字段列表，必须按序排列
     * @param cls 生成的对象列表中的对象类型
     * @return
     * @throws Exception
     */
    public static List importObjFromExcel(MultipartFile file, List<String> fields, Class cls) throws Exception{
        String fileName = file.getOriginalFilename();
        InputStream input = file.getInputStream();

        // for test -------------
//        File fileTest = new File("C:\\Users\\Cherry\\Desktop\\test.xlsx");
//        String fileName = fileTest.getName();
//        System.out.println(fileName);
//        InputStream input = new FileInputStream(fileTest);
        // -------------------

        List list = new ArrayList();
       if (fileName.endsWith("xlsx")) {
            XSSFWorkbook workbook = new XSSFWorkbook(input);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int totalRows = sheet.getLastRowNum();

            for (int rowNum = 1; rowNum <= totalRows; rowNum++) {
                XSSFRow row = sheet.getRow(rowNum);
                Object obj = cls.newInstance();
                boolean blankFlg =true;
                for(int i=0;i<fields.size();i++){
                    if(row==null){
                        break;
                    }
                    if(CheckRowNull(row)){
                        break;
                    }
                    String value = "";
                    XSSFCell cell = row.getCell(i);
                    if(cell!=null){
                        value=replaceBlank(getStringCellValue(cell));
                    }
                    if(!StringUtils.isEmpty(value)){
                        blankFlg=false;
                    }
                    ReflcUtils.setFieldValueByName(fields.get(i), value, obj);
                }
                if (!blankFlg) {
                    list.add(obj);
                }
            }
        } else if (fileName.endsWith("xls")) {
            HSSFWorkbook workbook = new HSSFWorkbook(input);
            HSSFSheet sheet = workbook.getSheetAt(0);

            int totalRows = sheet.getLastRowNum();

            for (int rowNum = 1; rowNum <= totalRows; rowNum++) {
                HSSFRow row = sheet.getRow(rowNum);
                Object obj = cls.newInstance();
                boolean blankFlg = true;
                for (int i = 0; i < fields.size(); ++i) {
                    if(row==null){
                        break;
                    }
                    if(CheckRowNull(row)){
                        break;
                    }
                    String value = "";
                    HSSFCell cell = row.getCell(i);
                    if (cell != null) {
                        value=replaceBlank(getStringCellValue(cell));
                    }
                    if (!StringUtils.isEmpty(value)) {
                        blankFlg = false;
                    }
                    ReflcUtils.setFieldValueByName(fields.get(i), value, obj);
                }
                if (!blankFlg) {
                    list.add(obj);
                }
            }
        } else {
            String errMsg = "上送文件类型不支持，fileName[" + fileName + "]";
            LOG.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        return list;
    }
    private static  Boolean CheckRowNull(Row row){

        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK){
                return  false;
            }
        }
        return true;
    }
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            //空格\t、回车\n、换行符\r、制表符\t
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
    public static String getStringCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        String strCell = "";
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                strCell = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                strCell = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                strCell = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_BLANK:
                strCell = "";
                break;
            default:
                strCell = "";
                break;
        }
        if (StringUtils.isEmpty(strCell)) {
            return "";
        }
        return strCell;
    }

    public static void main(String[] args) throws Exception{
        String[] fieldNames = {
                "matCod",   // 物料编码
                "matNam",   // 物料名称
                "matUnt",   // 物料单位
                "pchsNum",  // 采购数量
                "techServ", // 技术与服务指标
                "memo"      // 备注
        };
        List list = importObjFromExcel(null, Arrays.asList(fieldNames), BidProjOnMatDtl.class);
        list.stream().forEach(item -> {
            System.out.println(item);
        });




//        List<List<List<String>>> objs = new ArrayList<>();
//        List<List<String>> kpiTypeObj = new ArrayList<>();
//        List<String> kpiObj1 = Arrays.asList("一", "资质（10分）", "1", "是否符合行业或国家要求（4分）", "", "", "", "", "1");
//        List<String> kpiObj2 = Arrays.asList("", "", "2", "是否我工厂要求（3分）", "", "", "", "", "2");
//        List<String> kpiObj3 = Arrays.asList("", "", "3", "其他（3分）", "", "", "", "", "3");
//        kpiTypeObj.add(kpiObj1);
//        kpiTypeObj.add(kpiObj2);
//        kpiTypeObj.add(kpiObj3);
//        objs.add(kpiTypeObj);
//
//        List<List<String>> kpiTypeObj1 = new ArrayList<>();
//        List<String> kpiObj4 = Arrays.asList("一", "资质（10分）", "4", "是否符合行业或国家要求（4分）", "", "", "", "", "4");
//        List<String> kpiObj5 = Arrays.asList("", "", "5", "是否我工厂要求（3分）", "", "", "", "", "5");
//        List<String> kpiObj6 = Arrays.asList("", "", "6", "其他（3分）", "", "", "", "", "6");
//        kpiTypeObj1.add(kpiObj4);
//        kpiTypeObj1.add(kpiObj5);
//        kpiTypeObj1.add(kpiObj6);
//        objs.add(kpiTypeObj1);
//
//        List<List<String>> kpiTypeObj3 = new ArrayList<>();
//        List<String> kpiObj7 = Arrays.asList("一", "资质（10分）", "7", "是否符合行业或国家要求（4分）", "", "", "", "", "7");
//        List<String> kpiObj8 = Arrays.asList("", "", "8", "是否我工厂要求（3分）", "", "", "", "", "8");
//        List<String> kpiObj9 = Arrays.asList("", "", "9", "其他（3分）", "", "", "", "", "9");
//        List<String> kpiObj10 = Arrays.asList("", "", "10", "其他（3分）", "", "", "", "", "10");
//        kpiTypeObj3.add(kpiObj7);
//        kpiTypeObj3.add(kpiObj8);
//        kpiTypeObj3.add(kpiObj9);
//        kpiTypeObj3.add(kpiObj10);
//        objs.add(kpiTypeObj3);
//
//        PoiUtils.exportExcel("供应商开发申请","供应商开发申请", "开发申请考察表", objs);

//        List<List<String>> result = PoiUtils.inportExcel("供应商开发申请.xlsx");
//        System.out.print(result);
    }

}
