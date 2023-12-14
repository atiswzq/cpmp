package cn.cofco.cpmp.service.impl;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dao.ChkDefMapper;
import cn.cofco.cpmp.dao.SplrAdmtChkMapper;
import cn.cofco.cpmp.dao.SplrChkItemMapper;
import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.entity.ChkDef;
import cn.cofco.cpmp.entity.ChkTempItem;
import cn.cofco.cpmp.entity.SplrAdmtChk;
import cn.cofco.cpmp.entity.SplrChkItem;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.holder.SysParmHolder;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.IChkTabService;
import cn.cofco.cpmp.support.OutputDtoUtil;
import cn.cofco.cpmp.utils.ContextTools;
import cn.cofco.cpmp.utils.CurrentUserInfo;
import cn.cofco.cpmp.utils.poi.PoiUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xsmiler on 2017/7/19.
 */
@Service
public class ChkTabServiceImpl implements IChkTabService{
    private Logger logger = LoggerManager.getSplrLog();

    @Resource
    private ChkDefMapper chkDefMapper;

    @Resource
    private SplrAdmtChkMapper splrAdmtChkMapper;

    @Resource
    private SplrChkItemMapper splrChkItemMapper;


    private String checkAuth() {
        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        Integer userId = userinfo.getUserid();

        if (userId == null || userId == 0) {
            return "鉴权失败 - userId为空";
        }

        return "";
    }

    @Override
    public void downloadTempById(HttpServletResponse response, Long id) throws Exception {

        List<ChkTempItem> chkTempItems = chkDefMapper.selectChkTempById(id);
        Map<Long, List<ChkTempItem>> chkTempItemMap = new ConcurrentHashMap<>();
        for(ChkTempItem chkTempItem : chkTempItems) {
            List<ChkTempItem> chkTempItemList = chkTempItemMap.get(chkTempItem.getKpiTypOrder());
            if (null == chkTempItemList) {
                chkTempItemList = new ArrayList<>();
                chkTempItemMap.put(chkTempItem.getKpiTypOrder(), chkTempItemList);
            }
            chkTempItemList.add(chkTempItem);
        }

        List<List<List<String>>> objs = new ArrayList<>();

        String chkNam = chkTempItems.get(0).getChkNam();
        for(Map.Entry<Long, List<ChkTempItem>> entry : chkTempItemMap.entrySet()) {
            List<List<String>> kpiTypeObj = new ArrayList<>();
            List<ChkTempItem> chkTempItemList = entry.getValue();
            for(ChkTempItem chkTempItem : chkTempItemList) {
                List<String> kpiObj = new ArrayList<>();
                kpiObj.add(String.valueOf(entry.getKey()));
                kpiObj.add(chkTempItem.getKpiTypNam().concat("(").concat(String.valueOf(chkTempItem.getKpiTypWgt())).concat("分)"));
                kpiObj.add(String.valueOf(chkTempItem.getDspyOrder()));
                kpiObj.add(chkTempItem.getKpiNam().concat("(").concat(String.valueOf(chkTempItem.getKpiWgt())).concat("分)"));
                kpiObj.add("");
                kpiObj.add("");
                kpiObj.add("");
                kpiObj.add("");
                kpiObj.add("");
                kpiTypeObj.add(kpiObj);
            }
            objs.add(kpiTypeObj);
        }

        XSSFWorkbook workbook = PoiUtils.exportExcel(chkNam,chkNam, chkNam, objs);
        response.setContentType("application/x-excel");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename="+ URLEncoder.encode(chkNam.concat(".xlsx"),"utf-8"));
        OutputStream out = response.getOutputStream();
        workbook.write(out);
        workbook.close();
    }

    /*@Override
    public OutputDto uploadChkTab(HttpServletRequest request, String chkTyp, Long id, Long splrId, MultipartFile file) throws Exception {

        // 0. 权限校验
        if (!"".equals(checkAuth())) {
            return OutputDtoUtil.setOutputDto(false, RtnEnum.NO_OPRT_AUTH);
        }
        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);

        String rootPath = request.getServletContext().getRealPath("/");
        String filePath = new File(rootPath + "..").getCanonicalPath();

        String fileName = writeFile(filePath + File.separator + chkTyp, file);
        String path = chkTyp + "/" + fileName;

        // 查询对应id下的考察表内容
        ChkDef chkDef = chkDefMapper.selectByPrimaryKey(id);

        // 插入供应商准入考察表
        SplrAdmtChk splrAdmtChk = new SplrAdmtChk();
        splrAdmtChk.setChkDefId(id);
        splrAdmtChk.setChkFileNam(path);
        splrAdmtChk.setSplrId(splrId);
        splrAdmtChk.setChkNam(chkDef.getChkNam());
        splrAdmtChk.setChkExpn(chkDef.getChkExpn());
        splrAdmtChk.setUsrId(Long.valueOf(userinfo.getUserid()));
        splrAdmtChk.setUsrNam(userinfo.getUsername());
        splrAdmtChk.setTim(new Timestamp(System.currentTimeMillis()));


        splrAdmtChkMapper.insertSelective(splrAdmtChk);

        Long admtChkId = splrAdmtChk.getAdmtChkId();

        List<List<String>> chkContent = PoiUtils.importExcel(file);

        List<ChkTempItem> chkTempItems = chkDefMapper.selectChkTempById(id);
        List<SplrChkItem> splrChkItems = new ArrayList<>();

        Long order = 1L;
        for (ChkTempItem chkTempItem : chkTempItems) {
            SplrChkItem splrChkItem = new SplrChkItem();
            splrChkItem.setAdmtChkId(admtChkId);
            splrChkItem.setChkDefId(id);
            splrChkItem.setKpiId(chkTempItem.getKpiId());
            splrChkItem.setKpiNam(chkTempItem.getKpiNam());
            splrChkItem.setOrder(Long.valueOf(order));
            if (null != chkContent.get((int) (order - 1)).get(6)) {
                splrChkItem.setScore(Long.valueOf(chkContent.get((int) (order - 1)).get(6)));
            }
            order++;
            splrChkItems.add(splrChkItem);
        }

        splrChkItemMapper.inserts(splrChkItems);

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
                RtnEnum.SUC_OPR, RtnEnum.SUC_OPR.getDesc(), admtChkId);
    }
*/
    /**
     * 写文件
     * @param path
     * @param file
     * @return
     * @throws IOException
     */
    private String writeFile(String path, MultipartFile file) throws IOException {


        String fileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")) + System.currentTimeMillis() + "" + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        System.out.print(fileName);
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        System.out.println(folder.getAbsolutePath());

        String filePath = path+ File.separator
                + fileName;
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.createNewFile();
        }
        BufferedOutputStream buffStream = new BufferedOutputStream(
                new FileOutputStream(targetFile));
        buffStream.write(file.getBytes());
        buffStream.close();
        return fileName;
    }
}
