package cn.cofco.cpmp.mdm.entity;

/**
 * 专业公司视图
 * @author xu
 *
 */
public class MdmCoInfo {
	private String co_company;
	private String co_account_group;
	private String co_key_account_no;
	private String co_key_account;
	private String co_my_companycode;
	private String co_remark1;
	private String co_remark2;
	private String co_remark3;
	private String co_remark4;
	public String getCo_company() {
		return co_company;
	}
	public void setCo_company(String co_company) {
		this.co_company = co_company;
	}
	public String getCo_account_group() {
		return co_account_group;
	}
	public void setCo_account_group(String co_account_group) {
		this.co_account_group = co_account_group;
	}
	public String getCo_key_account_no() {
		return co_key_account_no;
	}
	public void setCo_key_account_no(String co_key_account_no) {
		this.co_key_account_no = co_key_account_no;
	}
	public String getCo_key_account() {
		return co_key_account;
	}
	public void setCo_key_account(String co_key_account) {
		this.co_key_account = co_key_account;
	}
	public String getCo_my_companycode() {
		return co_my_companycode;
	}
	public void setCo_my_companycode(String co_my_companycode) {
		this.co_my_companycode = co_my_companycode;
	}
	public String getCo_remark1() {
		return co_remark1;
	}
	public void setCo_remark1(String co_remark1) {
		this.co_remark1 = co_remark1;
	}
	public String getCo_remark2() {
		return co_remark2;
	}
	public void setCo_remark2(String co_remark2) {
		this.co_remark2 = co_remark2;
	}
	public String getCo_remark3() {
		return co_remark3;
	}
	public void setCo_remark3(String co_remark3) {
		this.co_remark3 = co_remark3;
	}
	public String getCo_remark4() {
		return co_remark4;
	}
	public void setCo_remark4(String co_remark4) {
		this.co_remark4 = co_remark4;
	}
	@Override
	public String toString() {
		return "MdmCoInfo [co_company=" + co_company + ", co_account_group="
				+ co_account_group + ", co_key_account_no=" + co_key_account_no
				+ ", co_key_account=" + co_key_account + ", co_my_companycode="
				+ co_my_companycode + ", co_remark1=" + co_remark1
				+ ", co_remark2=" + co_remark2 + ", co_remark3=" + co_remark3
				+ ", co_remark4=" + co_remark4 + "]";
	}
	
	
}
