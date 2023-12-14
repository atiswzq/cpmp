package cn.cofco.cpmp.entity;

import java.sql.Timestamp;

/**
 * 供应商在线答疑
 * 
 * @author 李世涛
 * @date 2018年1月13日
 * 
 */
public class SplrOnlineAnsr {
	private Long mid;// 消息id

	private Long splrId;// 供应商id

	private String splrNam;// 提问者

	private Long projId;// 项目id

	private String projLeader;// 答疑者(项目负责人)

	private Long leaderId = 0l;// 负责人Id

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

	public String getProjLeader() {
		return projLeader;
	}

	public void setProjLeader(String projLeader) {
		this.projLeader = projLeader;
	}

	public Long getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(Long leaderId) {
		this.leaderId = leaderId;
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
