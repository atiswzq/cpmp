package cn.cofco.cpmp.utils;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.wsclient.bpm.*;
import org.slf4j.Logger;

import javax.xml.bind.JAXBElement;
import javax.xml.ws.BindingProvider;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xujy on 2017/7/15.
 * for [BPM工具类] in cpmp
 */
public class BpmUtils {

    private static Logger logger = LoggerManager.getSysLog();

    /**
     * 获取BPM序列号
     * @param typ 类型
     * @return String
     */
    public static String getBpmSeqNo(String typ) {

        StringBuilder sb = new StringBuilder();
        String date = DateUtils.date2String(DateUtils.getDate(), "YYYYMMdd");

        sb.append(typ).append("-")
                .append(date).append("-")
                .append(DateUtils.currentTimeMillis()).append("-")
                .append(CryptUtils.getRandNum(6));

        return sb.toString();
    }


    /**
     * 提交BPM审核申请
     */
    public static boolean subApp(String bpmBody, String templateCode, String subject, String param, List<Long> atchs) {

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);

        logger.info("提交BPM审批 - 主题：[{}], 模板编号：[{}], 上送报文：[{}], 上传附件Ids: [{}]",
                subject, templateCode, bpmBody, atchs);
        SILYT2BPMAPPRVOUTService service = new SILYT2BPMAPPRVOUTService();
        SILYT2BPMAPPRVOUT silyt2BPMAPPRVOUT = service.getHTTPPort();
        BindingProvider bp = (BindingProvider) silyt2BPMAPPRVOUT;
        bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, Constants.BPM_USER);
        bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, Constants.BPM_USER_PASSWD);

        ObjectFactory objectFactory = new ObjectFactory();
        LaunchFormCollaboration4Sap launchFormCollaboration4Sap = objectFactory.createLaunchFormCollaboration4Sap();
        String data = "<![CDATA[" + bpmBody + "]]>";
        launchFormCollaboration4Sap.setRelateDoc(objectFactory.createLaunchFormCollaboration4SapRelateDoc(""));
        launchFormCollaboration4Sap.setToken(objectFactory.createLaunchFormCollaboration4SapToken(""));
        launchFormCollaboration4Sap.setData(objectFactory.createLaunchFormCollaboration4SapData(data));
        launchFormCollaboration4Sap.setSenderLoginName(objectFactory.createLaunchFormCollaboration4SapSenderLoginName(userinfo.getUsername()));
        launchFormCollaboration4Sap.setTemplateCode(objectFactory.createLaunchFormCollaboration4SapTemplateCode(templateCode));
        launchFormCollaboration4Sap.setSubject(objectFactory.createLaunchFormCollaboration4SapSubject(subject));
        launchFormCollaboration4Sap.setParam(objectFactory.createLaunchFormCollaboration4SapParam(param));
        if (atchs == null || atchs.isEmpty()) {
            atchs = new ArrayList<>();
            atchs.add(0L);
            launchFormCollaboration4Sap.setAttachments(atchs);
        } else {
            launchFormCollaboration4Sap.setAttachments(atchs);
        }

        launchFormCollaboration4Sap.setRelateDoc(objectFactory.createLaunchFormCollaboration4SapRelateDoc("AD"));

        LaunchFormCollaboration4SapResponse launchFormCollaboration4SapResponse = silyt2BPMAPPRVOUT.siLYT2BPMAPPRVOUT(launchFormCollaboration4Sap);
        JAXBElement<ServiceResponse> serviceResponseJAXBElement = launchFormCollaboration4SapResponse.getReturn();
        ServiceResponse response = serviceResponseJAXBElement.getValue();
        logger.info(subject + "- BPM响应报文: errorNumber: [{}], errorMessage: [{}], result: [{}]", response.getErrorNumber(), response.getErrorMessage().getValue(), response.getResult());

        if (!Constants.BPM_SUC.equals(response.getErrorNumber())) {
            throw new RuntimeException("提交BPM失败 - errorNumber: [" + response.getErrorNumber() + "], errorMessage: [" + response.getErrorMessage().getValue() + "]" );
        }
        return true;
    }

}
