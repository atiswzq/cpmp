package cn.cofco.cpmp.dto;

/**
 * 供应商淘汰审核：通过/拒绝DTO
 * 
 * @author 李世涛
 * @date 2018年1月12日
 * 
 */
public class SplrWdotAdtDto {

	/**
	 * 申请ID
	 * 
	 * 是否强制：是
	 */
	private Long aplyId;

	/**
	 * 审核意见
	 * 
	 * 是否强制：否
	 */
	private String adtMsg;

	/**
	 * 审核状态：01-审核中；02-审核通过；03-审核不通过
	 * 
	 * 是否强制：是
	 */
	private String adtSts;

	public Long getAplyId() {
		return aplyId;
	}

	public void setAplyId(Long aplyId) {
		this.aplyId = aplyId;
	}

	public String getAdtMsg() {
		return adtMsg;
	}

	public void setAdtMsg(String adtMsg) {
		this.adtMsg = adtMsg;
	}

	public String getAdtSts() {
		return adtSts;
	}

	public void setAdtSts(String adtSts) {
		this.adtSts = adtSts;
	}

}
