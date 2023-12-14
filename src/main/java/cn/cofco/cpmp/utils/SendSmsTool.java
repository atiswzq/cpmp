package cn.cofco.cpmp.utils;


import cn.cofco.cpmp.holder.SysParmHolder;
import cn.cofco.cpmp.log.LoggerManager;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 杨宏毅 on 2017/7/18.
 * Updated by 徐家园 on 2017/9/5
 * 发送短信
 */
public class SendSmsTool {
//    private static String accessKeyId = "LTAIBlKokDR810KS";
//    private static String accessKeySecret = "rW74wHSeMfZPLMFJPinC6OrVel04t3";
//    private static String signName = "阿里云短信测试专用";


    private static Logger logger = LoggerManager.getBusiLog();

    /**
     * 发送短信
     *
     * @param smsTemplate 短信模板
     * @param phoneNum    手机号
     * @param param       短信参数的json对象
     * @param lastError   报错信息 如果有的话
     */
    public static void sendSms(String smsTemplate, String phoneNum, JSONObject param, LastError lastError) {
        try {
            sendSms_inner(smsTemplate, phoneNum, param, lastError);
        } catch (Exception e) {
            LoggerFactory.getLogger(SendSmsTool.class).error("error -> ", ExceptionUtils.getFullStackTrace(e));
            if (!lastError.isError()) {
                lastError.setErrMsg("发送错误");
            }
        }
    }

    /**
     * 发送短信
     *
     * @param smsTemplate 短信模板
     * @param phoneNum    手机号
     * @param param       短信参数的json对象
     * @param lastError   报错信息 如果有的话
     */
    private static void sendSms_inner(String smsTemplate, String phoneNum, JSONObject param, LastError lastError) throws Exception {
        //设置超时时间-可自行调整
        String timeoutConn = SysParmHolder.getByParmTypAndParmCod("SMS", "CONN_TIMEOUT").getParmVal();
        System.setProperty("sun.net.client.defaultConnectTimeout", timeoutConn);
        String timeoutRead = SysParmHolder.getByParmTypAndParmCod("SMS", "SO_TIMEOUT").getParmVal();
        System.setProperty("sun.net.client.defaultReadTimeout", timeoutRead);
        //初始化ascClient需要的几个参数
        //短信API产品名称
//        final String product = "Dysmsapi";
        final String product = SysParmHolder.getByParmTypAndParmCod("SMS", "SMS_API_PROD_NAME").getParmVal();
        //短信API产品域名
//        final String domain = "dysmsapi.aliyuncs.com";
        final String domain = SysParmHolder.getByParmTypAndParmCod("SMS", "SMS_API_PROD_DOMAIN").getParmVal();

        //替换成你的AK
        //初始化ascClient,暂时不支持多region
        final String regionId = SysParmHolder.getByParmTypAndParmCod("SMS", "REGION_ID").getParmVal();
        final String endPointName = SysParmHolder.getByParmTypAndParmCod("SMS", "END_POINT_NAME").getParmVal();

        final String accessKeyId = SysParmHolder.getByParmTypAndParmCod("SMS", "ACCESS_KEY_ID").getParmVal();
        final String accessKeySecret = SysParmHolder.getByParmTypAndParmCod("SMS", "ACCESS_KEY_SECRET").getParmVal();
        final String signName = SysParmHolder.getByParmTypAndParmCod("SMS", "SIGN_NAME").getParmVal();

        IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId,
                accessKeySecret);
        DefaultProfile.addEndpoint(endPointName, regionId, product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为20个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
        request.setPhoneNumbers(phoneNum);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(smsTemplate);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam(param.toString());
//        System.out.println(param.toString());
        //可选-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
//        request.setOutId("yourOutId");
        //请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        logger.info("发送短信: phoneNbrs[{}], signName[{}], templateCode[{}], templateParam[{}]", phoneNum, signName, smsTemplate, param.toString());
        if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            //请求成功
            logger.info("发送短信成功 - requestId[{}], code[{}], message: [{}]", sendSmsResponse.getRequestId(), sendSmsResponse.getCode(), sendSmsResponse.getMessage());
        } else {
            lastError.setErrMsg(sendSmsResponse.getMessage());
            logger.error("发送短信异常 - requestId[{}], code[{}], message: [{}]", sendSmsResponse.getRequestId(), sendSmsResponse.getCode(), sendSmsResponse.getMessage());
        }
    }

}
