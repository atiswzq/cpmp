package cn.cofco.cpmp.utils;

import cn.cofco.cpmp.log.LoggerManager;
import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Xujy on 2017/9/28.
 * for [文件处理工具类] in cpmp
 */
public class FileUtils {

    private static Logger logger = LoggerManager.getSysLog();

    /**
     * 写文件
     * @param path
     * @param file
     * @return
     * @throws IOException
     */
    public static String writeFile(String path, MultipartFile file) throws IOException {

        String fileName = System.currentTimeMillis() + "" + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String filePath = path + File.separator
                + fileName;

        logger.info("filePath: " + filePath);

        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.createNewFile();
            logger.info("file did not exist, now created ...");
        }
        BufferedOutputStream buffStream = new BufferedOutputStream(new FileOutputStream(targetFile));
        buffStream.write(file.getBytes());
        buffStream.close();
        return fileName;
    }


    public static final byte[] readFileToByteArray(String path) throws Exception {
        File file = new File(path);
        if (!file.exists()) {
            logger.error("文件不存在, path:[{}]", path);
            return null;
        }

        return Files.readAllBytes(Paths.get(path));
    }
}
