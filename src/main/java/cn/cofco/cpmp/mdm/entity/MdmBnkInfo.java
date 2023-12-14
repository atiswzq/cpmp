package cn.cofco.cpmp.mdm.entity;

/**
 * 银行账户信息
 * @author xu
 *
 */
public class MdmBnkInfo {
	private String bank_country;
	private String bank_code;
	private String bank_name;
	private String bank_account;
	private String account_holder;
	private String swift_code;
	private String default1;
	public String getBank_country() {
		return bank_country;
	}
	public void setBank_country(String bank_country) {
		this.bank_country = bank_country;
	}
	public String getBank_code() {
		return bank_code;
	}
	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	public String getBank_account() {
		return bank_account;
	}
	public void setBank_account(String bank_account) {
		this.bank_account = bank_account;
	}
	public String getAccount_holder() {
		return account_holder;
	}
	public void setAccount_holder(String account_holder) {
		this.account_holder = account_holder;
	}
	public String getSwift_code() {
		return swift_code;
	}
	public void setSwift_code(String swift_code) {
		this.swift_code = swift_code;
	}
	public String getDefault1() {
		return default1;
	}
	public void setDefault1(String default1) {
		this.default1 = default1;
	}
	@Override
	public String toString() {
		return "MdmBnkInfo [bank_country=" + bank_country + ", bank_code="
				+ bank_code + ", bank_name=" + bank_name + ", bank_account="
				+ bank_account + ", account_holder=" + account_holder
				+ ", swift_code=" + swift_code + ", default1=" + default1 + "]";
	}
	
	
}
