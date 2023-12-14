package cn.cofco.cpmp.splr.vo;

import java.util.List;
import java.util.Map;

public class ChrmListVo {
	
	private Long splrId;

    private String splrNam;
    
    private List<Map<String, String>> iconItems;

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

	public List<Map<String, String>> getIconItems() {
		return iconItems;
	}

	public void setIconItems(List<Map<String, String>> iconItems) {
		this.iconItems = iconItems;
	}
    
    
}
