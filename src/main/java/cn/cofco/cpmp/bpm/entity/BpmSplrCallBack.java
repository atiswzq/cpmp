package cn.cofco.cpmp.bpm.entity;

public class BpmSplrCallBack {
	
	private String bpmSeqNo;
	private String status;
	private String msg;
	
	public String getBpmSeqNo() {
		return bpmSeqNo;
	}
	public String getStatus() {
		return status;
	}
	public String getMsg() {
		return msg;
	}
	public void setBpmSeqNo(String bpmSeqNo) {
		this.bpmSeqNo = bpmSeqNo;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	@Override
	public String toString() {
		return "BpmSplrCallBack [bpmSeqNo=" + bpmSeqNo + ", status=" + status
				+ ", msg=" + msg + "]";
	}
	
}
