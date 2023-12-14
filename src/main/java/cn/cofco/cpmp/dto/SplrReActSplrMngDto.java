package cn.cofco.cpmp.dto;

/**
 * 供应商重启用：通过/拒绝DTO
 * 
 * 管理员直接重启用
 * 
 * @author 李世涛
 * @date 2018年1月12日
 * 
 */
public class SplrReActSplrMngDto {

	/**
	 * 供应商ID
	 * 
	 * 是否强制：是
	 */
	private Long splrId;

	/**
	 * 重启用意见
	 * 
	 * 是否强制：否
	 */
	private String reActMsg;

	public Long getSplrId() {
		return splrId;
	}

	public void setSplrId(Long splrId) {
		this.splrId = splrId;
	}

	public String getReActMsg() {
		return reActMsg;
	}

	public void setReActMsg(String reActMsg) {
		this.reActMsg = reActMsg;
	}

}
