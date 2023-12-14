package cn.cofco.cpmp.splr.vo;

public class SplrOnlineAnsrVo {

	private Long splrId;// 供应商id

	private String splrNam;// 提问者

	private Long projId;// 项目id

	private String projLeader;// 答疑者(项目负责人)

	private String ansrContent;// 回复内容

	private Long leaderId = 0l;// 负责人Id

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

	public String getAnsrContent() {
		return ansrContent;
	}

	public void setAnsrContent(String ansrContent) {
		this.ansrContent = ansrContent;
	}

	public Long getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(Long leaderId) {
		this.leaderId = leaderId;
	}

}
