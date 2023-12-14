package cn.cofco.cpmp.service;

import org.springframework.web.multipart.MultipartFile;

import cn.cofco.cpmp.dto.OutputDto;

public interface IFileService {
	
	/**
	 * 上传文件
	 * @param type 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	OutputDto uploadFile(String type, MultipartFile file) throws Exception;
}
