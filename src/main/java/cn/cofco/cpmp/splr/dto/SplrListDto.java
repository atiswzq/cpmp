package cn.cofco.cpmp.splr.dto;

/**
 * 供应商列表信息
 * 
 * 服务于采购员页面
 * 
 * @author 李世涛
 * @date 2018年1月10日
 * 
 */
public class SplrListDto {

	private Long aplyId;
	private Long splrId;
	private String splrFullName;
	private String splrShortName;
	private String splrLevel;
	private String slprSts;
	private String aplySts;
	// 淘汰申请理由
	private String wdotCtt;

	public Long getAplyId() {
		return aplyId;
	}

	public void setAplyId(Long aplyId) {
		this.aplyId = aplyId;
	}

	public Long getSplrId() {
		return splrId;
	}

	public void setSplrId(Long splrId) {
		this.splrId = splrId;
	}

	public String getSplrFullName() {
		return splrFullName;
	}

	public void setSplrFullName(String splrFullName) {
		this.splrFullName = splrFullName;
	}

	public String getSplrShortName() {
		return splrShortName;
	}

	public void setSplrShortName(String splrShortName) {
		this.splrShortName = splrShortName;
	}

	public String getSplrLevel() {
		return splrLevel;
	}

	public void setSplrLevel(String splrLevel) {
		this.splrLevel = splrLevel;
	}

	public String getSlprSts() {
		return slprSts;
	}

	public void setSlprSts(String slprSts) {
		this.slprSts = slprSts;
	}

	public String getAplySts() {
		return aplySts;
	}

	public void setAplySts(String aplySts) {
		this.aplySts = aplySts;
	}

	public String getWdotCtt() {
		return wdotCtt;
	}

	public void setWdotCtt(String wdotCtt) {
		this.wdotCtt = wdotCtt;
	}

}
