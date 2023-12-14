package cn.cofco.cpmp.mdm.entity;

/**
 * 客商主数据审批输入信息
 * @author xu
 *
 */
public class MdmApvlReq {
	private String uuid;
	private String partner_id;
	private String approve_status;
	private String approve_opinion;
	public String getUuid() {
		return uuid;
	}
	public String getPartner_id() {
		return partner_id;
	}
	public String getApprove_status() {
		return approve_status;
	}
	public String getApprove_opinion() {
		return approve_opinion;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public void setPartner_id(String partner_id) {
		this.partner_id = partner_id;
	}
	public void setApprove_status(String approve_status) {
		this.approve_status = approve_status;
	}
	public void setApprove_opinion(String approve_opinion) {
		this.approve_opinion = approve_opinion;
	}
	@Override
	public String toString() {
		return "MdmApvlReq [uuid=" + uuid + ", partner_id=" + partner_id
				+ ", approve_status=" + approve_status + ", approve_opinion="
				+ approve_opinion + "]";
	}
	
	
}
