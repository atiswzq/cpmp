package cn.cofco.cpmp.splr.dto;

/**
 * 淘汰列表信息
 * 
 * 服务于采购员页面
 * 
 * @author 李世涛
 * @date 2018年1月10日
 * 
 */
public class SplrWdotDto {

	private Long aplyId;// 淘汰申请id
	private Long reActAplyId;// 重启用申请id
	private Long splrId;
	private String splrFullName;
	private String splrShortName;
	private String splrLevel;
	private String wdotSts;
	private String aplyCtt;
	private String wdotDeptAdtMsg;
	private String wdotDeptAdtSts;
	private String wdotSplrMngAdtMsg;
	private String wdotSplrMngAdtSts;
	private String reActiveCtt;
	private String reActiveDeptAdtMsg;
	private String reActDeptAdtSts;
	private String reActiveSplrMngAdtMsg;
	private String reActSplrMngAdtSts;
	private String deptNam;
	public Long getAplyId() {
		return aplyId;
	}

	public void setAplyId(Long aplyId) {
		this.aplyId = aplyId;
	}

	public Long getReActAplyId() {
		return reActAplyId;
	}

	public void setReActAplyId(Long reActAplyId) {
		this.reActAplyId = reActAplyId;
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

	public String getWdotSts() {
		return wdotSts;
	}

	public void setWdotSts(String wdotSts) {
		this.wdotSts = wdotSts;
	}

	public String getAplyCtt() {
		return aplyCtt;
	}

	public void setAplyCtt(String aplyCtt) {
		this.aplyCtt = aplyCtt;
	}

	public String getWdotDeptAdtMsg() {
		return wdotDeptAdtMsg;
	}

	public void setWdotDeptAdtMsg(String wdotDeptAdtMsg) {
		this.wdotDeptAdtMsg = wdotDeptAdtMsg;
	}

	public String getWdotSplrMngAdtMsg() {
		return wdotSplrMngAdtMsg;
	}

	public void setWdotSplrMngAdtMsg(String wdotSplrMngAdtMsg) {
		this.wdotSplrMngAdtMsg = wdotSplrMngAdtMsg;
	}

	public String getReActiveCtt() {
		return reActiveCtt;
	}

	public void setReActiveCtt(String reActiveCtt) {
		this.reActiveCtt = reActiveCtt;
	}

	public String getReActiveDeptAdtMsg() {
		return reActiveDeptAdtMsg;
	}

	public void setReActiveDeptAdtMsg(String reActiveDeptAdtMsg) {
		this.reActiveDeptAdtMsg = reActiveDeptAdtMsg;
	}

	public String getReActiveSplrMngAdtMsg() {
		return reActiveSplrMngAdtMsg;
	}

	public void setReActiveSplrMngAdtMsg(String reActiveSplrMngAdtMsg) {
		this.reActiveSplrMngAdtMsg = reActiveSplrMngAdtMsg;
	}

	public String getWdotDeptAdtSts() {
		return wdotDeptAdtSts;
	}

	public void setWdotDeptAdtSts(String wdotDeptAdtSts) {
		this.wdotDeptAdtSts = wdotDeptAdtSts;
	}

	public String getWdotSplrMngAdtSts() {
		return wdotSplrMngAdtSts;
	}

	public void setWdotSplrMngAdtSts(String wdotSplrMngAdtSts) {
		this.wdotSplrMngAdtSts = wdotSplrMngAdtSts;
	}

	public String getReActDeptAdtSts() {
		return reActDeptAdtSts;
	}

	public void setReActDeptAdtSts(String reActDeptAdtSts) {
		this.reActDeptAdtSts = reActDeptAdtSts;
	}

	public String getReActSplrMngAdtSts() {
		return reActSplrMngAdtSts;
	}

	public void setReActSplrMngAdtSts(String reActSplrMngAdtSts) {
		this.reActSplrMngAdtSts = reActSplrMngAdtSts;
	}

	public String getDeptNam() {
		return deptNam;
	}

	public void setDeptNam(String deptNam) {
		this.deptNam = deptNam;
	}
}
