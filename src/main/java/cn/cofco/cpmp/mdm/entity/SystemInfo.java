package cn.cofco.cpmp.mdm.entity;

/**
 * 分发系统信息
 * @author xsmiler
 *
 */
public class SystemInfo {
	private String business_segment;
	private String target_system;
	
	public String getBusiness_segment() {
		return business_segment;
	}
	public void setBusiness_segment(String business_segment) {
		this.business_segment = business_segment;
	}
	public String getTarget_system() {
		return target_system;
	}
	public void setTarget_system(String target_system) {
		this.target_system = target_system;
	}
	
	@Override
	public String toString() {
		return "SystemInfo [business_segment=" + business_segment
				+ ", target_system=" + target_system + "]";
	}
	
}
