package cn.cofco.cpmp.mdm.entity;

import java.util.List;


public class MdmMat {
	
	private List<MdmMateriel> mdm_materiels;

	public List<MdmMateriel> getMdm_materiels() {
		return mdm_materiels;
	}

	public void setMdm_materiels(List<MdmMateriel> mdm_materiels) {
		this.mdm_materiels = mdm_materiels;
	}

	@Override
	public String toString() {
		return "MdmMat [mdm_materiels=" + mdm_materiels + "]";
	}

}
