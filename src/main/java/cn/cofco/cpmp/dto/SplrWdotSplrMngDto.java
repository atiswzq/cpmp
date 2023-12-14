package cn.cofco.cpmp.dto;

/**
 * 供应商淘汰：通过/拒绝DTO
 * 
 * 管理员直接淘汰
 * 
 * @author 李世涛
 * @date 2018年1月12日
 * 
 */
public class SplrWdotSplrMngDto {

	/**
	 * 供应商ID
	 * 
	 * 是否强制：是
	 */
	private Long splrId;

	/**
	 * 淘汰意见
	 * 
	 * 是否强制：否
	 */
	private String wdotMsg;

	public Long getSplrId() {
		return splrId;
	}

	public void setSplrId(Long splrId) {
		this.splrId = splrId;
	}

	public String getWdotMsg() {
		return wdotMsg;
	}

	public void setWdotMsg(String wdotMsg) {
		this.wdotMsg = wdotMsg;
	}

}
