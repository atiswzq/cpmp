package cn.cofco.cpmp.mdm.entity;

/**
 * 客商主数据审批输出信息
 * @author xu
 *
 */
public class MdmApvlRes {
	private String status;
	private String message;
	
	public String getStatus() {
		return status;
	}
	public String getMessage() {
		return message;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "MdmApvlRes [status=" + status + ", message=" + message + "]";
	}
	
	
}
