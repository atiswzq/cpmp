package cn.cofco.cpmp.mdm.entity;

import java.util.List;

/**
 * 
 * Created by xsmiler on 2017/6/10.
 */
public class MdmSplr {
	private List<MdmPartner> mdmPartners;

	public List<MdmPartner> getMdmPartners() {
		return mdmPartners;
	}

	public void setMdmPartners(List<MdmPartner> mdmPartners) {
		this.mdmPartners = mdmPartners;
	}

	@Override
	public String toString() {
		return "MdmSplr [mdmPartners=" + mdmPartners + "]";
	}
	
	
}
