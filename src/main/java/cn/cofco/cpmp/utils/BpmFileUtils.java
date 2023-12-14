package cn.cofco.cpmp.utils;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.holder.SysParmHolder;
import cn.cofco.cpmp.log.LoggerManager;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONObject;
import org.slf4j.Logger;

import java.io.*;
import java.net.*;

/**
 * Created by Xujy on 2017/9/25.
 * for [BPM附件工具类] in cpmp
 */
public class BpmFileUtils {


    private static Logger LOG = LoggerManager.getSysLog();

    public static void main(String args[]) {
        String abc = "-6020181882614822165|";
        String fileId[] = CharacterProcessTool.split(abc, '|');
        System.out.println(fileId[0]);

    }

    /**
     * 下载附件
     *
     * @param downloadServiceUrl
     * @param fileId
     * @param token
     * @param fileName
     * @param exportDataPath
     * @return
     */
    public static boolean downloadAttachment(String downloadServiceUrl, String fileId, String token, String fileName, String exportDataPath) {

        StringBuffer parameters = new StringBuffer();

        parameters.append("fileId=" + fileId);

        URL preUrl = null;

        parameters.append("&token=" + token);

        try {
            preUrl = new URL(downloadServiceUrl);
            //URL preUrl = new URL("http://images.sohu.com/bill/s2009/jiaren/dongfeng/307/1601850918.jpg");
            String s = parameters.toString();

            URLConnection uc = preUrl.openConnection();
            uc.setDoOutput(true);
            uc.setUseCaches(false);
            uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            uc.setRequestProperty("Content-Length", "" + s.length());

            HttpURLConnection hc = (HttpURLConnection) uc;
            hc.setRequestMethod("POST");

            //输出内容
            OutputStream os = hc.getOutputStream();

            DataOutputStream dos = new DataOutputStream(os);

            dos.writeBytes(s);

            dos.flush();

            dos.close();

            //获取NC对凭证的验证结果
            FileOutputStream file = new FileOutputStream(exportDataPath + "/" + fileName);

            InputStream is = hc.getInputStream();

            //String returnFlag = "";
            int ch;

            while ((ch = is.read()) != -1) {
                //returnFlag += String.valueOf((char) ch);
                file.write(ch);
            }

            if (os != null)
                os.close(); // 关闭流
            if (is != null)
                is.close();    // 关闭流
            return true;
        } catch (Exception e) {
            LOG.error("上傳文件異常： " + ExceptionUtils.getStackTrace(e));
            return false;
        }
    }

    /**
     * 上传附件
     *
     * @param loginName
     * @param token
     * @param uploadPath
     * @param fileName
     * @return
     */
    public static Long uploadAttachment(String loginName, String token, String uploadPath, String fileName) {

        String baseUrl = SysParmHolder.getByParmTypAndParmCod(Constants.SYS_PARM_TYP_BPM, Constants.SYS_PARM_KEY_BPM_UPLOAD_URL).getParmVal();

        String uploadURL = baseUrl + "?method=processUploadService"
                + "&senderLoginName=" + loginName
                + "&token=" + token;
        URL preUrl;
        URLConnection uc;
        String uploadfildID;
        String fileId[] = null;
        try {
            preUrl = new URL(uploadURL);
            uc = preUrl.openConnection();
            HttpURLConnection hc = (HttpURLConnection) uc;
            hc.setDoOutput(true);
            hc.setUseCaches(false);
            hc.setRequestProperty("contentType", "charset=utf-8");
            hc.setRequestMethod("POST");
            // 本地要进行上传的路径地址
            BufferedInputStream input = new BufferedInputStream(new FileInputStream(uploadPath));
            String BOUNDARY = "---------------------------7d4a6d158c9"; // 分隔符
            // 上传文件名称
//				String fileName="BOOTLOG.TXT";
            StringBuffer sb = new StringBuffer();
            sb.append("--");
            sb.append(BOUNDARY);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data; \r\n name=\"1\"; filename=\"" + fileName + "\"\r\n");
            sb.append("Content-Type: application/octet-stream\r\n\r\n");
            hc.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + "---------------------------7d4a6d158c9");
            byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            DataOutputStream dos = new DataOutputStream(hc.getOutputStream());
            dos.write(sb.toString().getBytes("utf-8"));
            int cc = 0;
            while ((cc = input.read()) != -1) {
                dos.write(cc);
            }
            dos.write(end_data);
            dos.flush();
            dos.close();
            // 获取上传ID号
            InputStream in = hc.getInputStream();   //取返回值
            StringBuffer buffer = new StringBuffer();
            int i = 0;
            while (i != -1) {
                i = in.read();
                if (i != -1) {
                    buffer.append((char) i);
                }
            }
            in.close();
            uploadfildID = new String(buffer.toString().getBytes("iso-8859-1"), "UTF-8");
            fileId = CharacterProcessTool.split(uploadfildID, '|');
        } catch (Exception e) {
            LOG.error("上傳文件異常： " + ExceptionUtils.getStackTrace(e));
            throw new RuntimeException("上傳文件異常： " + e.getMessage());
        }

        if (fileId[0] != null) {
            try {
                Long id = Long.valueOf(fileId[0]);
                return id ;
            } catch (NumberFormatException e) {
                LOG.error("上傳文件異常： " + ExceptionUtils.getStackTrace(e));
                throw new RuntimeException("上傳文件異常： " + e.getMessage());
            }
        } else {
            String errMsg = "上傳文件異常： fileId返回为空";
            LOG.error(errMsg);
            throw new RuntimeException(errMsg);
        }
    }


    /**
     * 删除文件
     *
     * @param file
     */
    public static void deleteFiles(File file) {

        File[] files = file.listFiles();

        if (files == null || files.length == 0) {

            file.delete();

            return;
        }

        for (File fil : files) {

            deleteFiles(fil);

        }

    }


    public static String getToken(String loginName) {
        String baseUrl = SysParmHolder.getByParmTypAndParmCod(Constants.SYS_PARM_TYP_BPM, Constants.SYS_PARM_KEY_BPM_TOKEN_URL).getParmVal();
        String url = baseUrl + "?loginName=" + loginName;
        try {
            String resp = HttpUtil.sendHttpGetJson(url);
            LOG.info("GET-TOKEN, url[{}], response[{}]", url, resp);
            JSONObject respData = new JSONObject(resp);
            if (respData.has("id")) {
                return respData.getString("id");
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

}
