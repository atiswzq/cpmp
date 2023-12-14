package cn.cofco.cpmp.mdm.entity;

/**
 * 物料描述
 * @author xsmiler
 *
 */
public class MaterielDesc {
	private String matdesc;
	private String langu;
	
	public String getMatdesc() {
		return matdesc;
	}
	public void setMatdesc(String matdesc) {
		this.matdesc = matdesc;
	}
	public String getLangu() {
		return langu;
	}
	public void setLangu(String langu) {
		this.langu = langu;
	}
	
	@Override
	public String toString() {
		return "MaterielDesc [matdesc=" + matdesc + ", langu=" + langu + "]";
	}
	
}
