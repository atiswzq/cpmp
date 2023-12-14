package cn.cofco.cpmp.service.impl;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.holder.SysParmHolder;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.IFileService;
import cn.cofco.cpmp.support.OutputDtoUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class FileServiceImpl implements IFileService {
	
	private Logger logger = LoggerManager.getSplrSelfMngLog();
	
	@Override
	public OutputDto uploadFile(String type, MultipartFile file) throws Exception {
		
		String[] fileTypes = Constants.FILE_TYPE;
		boolean chk = false;
		for (String fileType : fileTypes) {
			if (type.equalsIgnoreCase(fileType)) {
				chk = true;
			}
		}
		if (!chk) {
			return OutputDtoUtil
					.setOutputDto(Constants.SUC_FALSE, RtnEnum.FAIL_OPR, "文件类型不支持");
		}
		
		String fileName = writeFile(SysParmHolder.FILE_DIR + type, file);
		String path = type + "/" + fileName;
		logger.info("保存文件成功！路径为：[{}]", path);
		return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
				RtnEnum.SUC_OPR, RtnEnum.SUC_OPR.getDesc(), path);
	}
	
	/**
	 * 写文件
	 * @param path
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private String writeFile(String path, MultipartFile file) throws IOException {

		String fileName = System.currentTimeMillis() + "" + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

		File folder = new File(path);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		
		logger.info("folder.getAbsolutePath: " + folder.getAbsolutePath());

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

}
