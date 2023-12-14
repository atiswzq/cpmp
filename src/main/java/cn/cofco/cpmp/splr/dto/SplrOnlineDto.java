package cn.cofco.cpmp.splr.dto;

import java.sql.Timestamp;

/**
 * 供应商答疑列表信息
 * 
 * 服务于供应商答疑页面
 * 
 * @author 李世涛
 * @date 2018年1月14日
 * 
 */
public class SplrOnlineDto {

	private Long mid;// 消息id

	private Long splrId;

	private String splrNam;// 提问者

	private Long projId;// 项目id

	private String queContent;// 提问内容

	private Timestamp queTime;// 提问时间

	private String ansrContent;// 回复内容

	private Timestamp ansrTime;// 回复时间

	private int openFlg;// 公开标识

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public Long getSplrId() {
		return splrId;
	}

	public void setSplrId(Long splrId) {
		this.splrId = splrId;
	}

	public String getSplrNam() {
		return splrNam;
	}

	public void setSplrNam(String splrNam) {
		this.splrNam = splrNam;
	}

	public Long getProjId() {
		return projId;
	}

	public void setProjId(Long projId) {
		this.projId = projId;
	}

	public String getQueContent() {
		return queContent;
	}

	public void setQueContent(String queContent) {
		this.queContent = queContent;
	}

	public Timestamp getQueTime() {
		return queTime;
	}

	public void setQueTime(Timestamp queTime) {
		this.queTime = queTime;
	}

	public String getAnsrContent() {
		return ansrContent;
	}

	public void setAnsrContent(String ansrContent) {
		this.ansrContent = ansrContent;
	}

	public Timestamp getAnsrTime() {
		return ansrTime;
	}

	public void setAnsrTime(Timestamp ansrTime) {
		this.ansrTime = ansrTime;
	}

	public int getOpenFlg() {
		return openFlg;
	}

	public void setOpenFlg(int openFlg) {
		this.openFlg = openFlg;
	}

}
