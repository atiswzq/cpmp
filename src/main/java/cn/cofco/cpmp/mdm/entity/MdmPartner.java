package cn.cofco.cpmp.mdm.entity;

import java.util.List;

/**
 * 客商基本信息
 * @author xu
 *
 */
public class MdmPartner {
	
	private String partner_number;
	private String account_group;
	private String account_group_desc;
	private String bp_class;
	private String bp_class_desc;
	private String full_name;
	private String sec_name;
	private String short_name;
	private String address;
	private String post_code;
	private String country;
	private String country_desc;
	private String region;
	private String region_gb;
	private String region_desc;
	private String city;
	private String city_desc;
	private String district;
	private String district_desc;
	private String telephone;
	private String tel_ex;
	private String mobile_phone;
	private String fax;
	private String fax_ex;
	private String email;
	private String has_credit_code;
	private String tax_number;
	private String business_license;
	private String organization_code;
	private String credit_code;
	private String trading_partner;
	private String is_key_account;
	private String superior_group;
	private String superior_group_desc;
	private String parent_company;
	private String parent_company_desc;
	private String controller;
	private String old_number;
	private String classification;
	private String classification_desc;
	private String industry1;
	private String industry1_desc;
	private String industry2;
	private String industry2_desc;
	private String industry;
	private String business_scope;
	private String registration_capital;
	private String registration_date;
	private String enterprise_nature;
	private String enterprise_nature_desc;
	private String legal_representative;
	private String legal_representative_id;
	private String black_list;
	private String black_list_desc;
	private String create_user;
	private String create_user_co;
	private String company;
	private String co_partner_type;
	private String partner_id;
	private String delete;
	
	private List<MdmBnkInfo> bank_info;
	private List<MdmCoInfo> co_info;
	private List<SystemInfo> system_info;
	public String getPartner_number() {
		return partner_number;
	}
	public String getAccount_group() {
		return account_group;
	}
	public String getAccount_group_desc() {
		return account_group_desc;
	}
	public String getBp_class() {
		return bp_class;
	}
	public String getBp_class_desc() {
		return bp_class_desc;
	}
	public String getFull_name() {
		return full_name;
	}
	public String getShort_name() {
		return short_name;
	}
	public String getAddress() {
		return address;
	}
	public String getPost_code() {
		return post_code;
	}
	public String getCountry() {
		return country;
	}
	public String getCountry_desc() {
		return country_desc;
	}
	public String getRegion() {
		return region;
	}
	public String getRegion_desc() {
		return region_desc;
	}
	public String getCity() {
		return city;
	}
	public String getCity_desc() {
		return city_desc;
	}
	public String getDistrict() {
		return district;
	}
	public String getDistrict_desc() {
		return district_desc;
	}
	public String getTelephone() {
		return telephone;
	}
	public String getTel_ex() {
		return tel_ex;
	}
	public String getMobile_phone() {
		return mobile_phone;
	}
	public String getFax() {
		return fax;
	}
	public String getFax_ex() {
		return fax_ex;
	}
	public String getEmail() {
		return email;
	}
	public String getHas_credit_code() {
		return has_credit_code;
	}
	public String getTax_number() {
		return tax_number;
	}
	public String getBusiness_license() {
		return business_license;
	}
	public String getOrganization_code() {
		return organization_code;
	}
	public String getCredit_code() {
		return credit_code;
	}
	public String getTrading_partner() {
		return trading_partner;
	}
	public String getIs_key_account() {
		return is_key_account;
	}
	public String getSuperior_group() {
		return superior_group;
	}
	public String getSuperior_group_desc() {
		return superior_group_desc;
	}
	public String getParent_company() {
		return parent_company;
	}
	public String getParent_company_desc() {
		return parent_company_desc;
	}
	public String getController() {
		return controller;
	}
	public String getOld_number() {
		return old_number;
	}
	public String getClassification() {
		return classification;
	}
	public String getClassification_desc() {
		return classification_desc;
	}
	public String getIndustry1() {
		return industry1;
	}
	public String getIndustry1_desc() {
		return industry1_desc;
	}
	public String getIndustry2() {
		return industry2;
	}
	public String getIndustry2_desc() {
		return industry2_desc;
	}
	public String getIndustry() {
		return industry;
	}
	public String getBusiness_scope() {
		return business_scope;
	}
	public String getRegistration_capital() {
		return registration_capital;
	}
	public String getRegistration_date() {
		return registration_date;
	}
	public String getEnterprise_nature() {
		return enterprise_nature;
	}
	public String getEnterprise_nature_desc() {
		return enterprise_nature_desc;
	}
	public String getLegal_representative() {
		return legal_representative;
	}
	public String getLegal_representative_id() {
		return legal_representative_id;
	}
	public String getBlack_list() {
		return black_list;
	}
	public String getBlack_list_desc() {
		return black_list_desc;
	}
	public String getCreate_user() {
		return create_user;
	}
	public String getCreate_user_co() {
		return create_user_co;
	}
	public String getCompany() {
		return company;
	}
	public String getCo_partner_type() {
		return co_partner_type;
	}
	public String getPartner_id() {
		return partner_id;
	}
	public List<MdmBnkInfo> getBank_info() {
		return bank_info;
	}
	public List<MdmCoInfo> getCo_info() {
		return co_info;
	}
	public List<SystemInfo> getSystem_info() {
		return system_info;
	}
	public void setPartner_number(String partner_number) {
		this.partner_number = partner_number;
	}
	public void setAccount_group(String account_group) {
		this.account_group = account_group;
	}
	public void setAccount_group_desc(String account_group_desc) {
		this.account_group_desc = account_group_desc;
	}
	public void setBp_class(String bp_class) {
		this.bp_class = bp_class;
	}
	public void setBp_class_desc(String bp_class_desc) {
		this.bp_class_desc = bp_class_desc;
	}
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	public void setShort_name(String short_name) {
		this.short_name = short_name;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setPost_code(String post_code) {
		this.post_code = post_code;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public void setCountry_desc(String country_desc) {
		this.country_desc = country_desc;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public void setRegion_desc(String region_desc) {
		this.region_desc = region_desc;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setCity_desc(String city_desc) {
		this.city_desc = city_desc;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public void setDistrict_desc(String district_desc) {
		this.district_desc = district_desc;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public void setTel_ex(String tel_ex) {
		this.tel_ex = tel_ex;
	}
	public void setMobile_phone(String mobile_phone) {
		this.mobile_phone = mobile_phone;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public void setFax_ex(String fax_ex) {
		this.fax_ex = fax_ex;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setHas_credit_code(String has_credit_code) {
		this.has_credit_code = has_credit_code;
	}
	public void setTax_number(String tax_number) {
		this.tax_number = tax_number;
	}
	public void setBusiness_license(String business_license) {
		this.business_license = business_license;
	}
	public void setOrganization_code(String organization_code) {
		this.organization_code = organization_code;
	}
	public void setCredit_code(String credit_code) {
		this.credit_code = credit_code;
	}
	public void setTrading_partner(String trading_partner) {
		this.trading_partner = trading_partner;
	}
	public void setIs_key_account(String is_key_account) {
		this.is_key_account = is_key_account;
	}
	public void setSuperior_group(String superior_group) {
		this.superior_group = superior_group;
	}
	public void setSuperior_group_desc(String superior_group_desc) {
		this.superior_group_desc = superior_group_desc;
	}
	public void setParent_company(String parent_company) {
		this.parent_company = parent_company;
	}
	public void setParent_company_desc(String parent_company_desc) {
		this.parent_company_desc = parent_company_desc;
	}
	public void setController(String controller) {
		this.controller = controller;
	}
	public void setOld_number(String old_number) {
		this.old_number = old_number;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	public void setClassification_desc(String classification_desc) {
		this.classification_desc = classification_desc;
	}
	public void setIndustry1(String industry1) {
		this.industry1 = industry1;
	}
	public void setIndustry1_desc(String industry1_desc) {
		this.industry1_desc = industry1_desc;
	}
	public void setIndustry2(String industry2) {
		this.industry2 = industry2;
	}
	public void setIndustry2_desc(String industry2_desc) {
		this.industry2_desc = industry2_desc;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public void setBusiness_scope(String business_scope) {
		this.business_scope = business_scope;
	}
	public void setRegistration_capital(String registration_capital) {
		this.registration_capital = registration_capital;
	}
	public void setRegistration_date(String registration_date) {
		this.registration_date = registration_date;
	}
	public void setEnterprise_nature(String enterprise_nature) {
		this.enterprise_nature = enterprise_nature;
	}
	public void setEnterprise_nature_desc(String enterprise_nature_desc) {
		this.enterprise_nature_desc = enterprise_nature_desc;
	}
	public void setLegal_representative(String legal_representative) {
		this.legal_representative = legal_representative;
	}
	public void setLegal_representative_id(String legal_representative_id) {
		this.legal_representative_id = legal_representative_id;
	}
	public void setBlack_list(String black_list) {
		this.black_list = black_list;
	}
	public void setBlack_list_desc(String black_list_desc) {
		this.black_list_desc = black_list_desc;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public void setCreate_user_co(String create_user_co) {
		this.create_user_co = create_user_co;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public void setCo_partner_type(String co_partner_type) {
		this.co_partner_type = co_partner_type;
	}
	public void setPartner_id(String partner_id) {
		this.partner_id = partner_id;
	}
	public void setBank_info(List<MdmBnkInfo> bank_info) {
		this.bank_info = bank_info;
	}
	public void setCo_info(List<MdmCoInfo> co_info) {
		this.co_info = co_info;
	}
	public void setSystem_info(List<SystemInfo> system_info) {
		this.system_info = system_info;
	}

	public String getSec_name() {
		return sec_name;
	}

	public void setSec_name(String sec_name) {
		this.sec_name = sec_name;
	}

	public String getRegion_gb() {
		return region_gb;
	}

	public void setRegion_gb(String region_gb) {
		this.region_gb = region_gb;
	}

	public String getDelete() {
		return delete;
	}

	public void setDelete(String delete) {
		this.delete = delete;
	}

	@Override
	public String toString() {
		return "MdmPartner{" +
				"partner_number='" + partner_number + '\'' +
				", account_group='" + account_group + '\'' +
				", account_group_desc='" + account_group_desc + '\'' +
				", bp_class='" + bp_class + '\'' +
				", bp_class_desc='" + bp_class_desc + '\'' +
				", full_name='" + full_name + '\'' +
				", sec_name='" + sec_name + '\'' +
				", short_name='" + short_name + '\'' +
				", address='" + address + '\'' +
				", post_code='" + post_code + '\'' +
				", country='" + country + '\'' +
				", country_desc='" + country_desc + '\'' +
				", region='" + region + '\'' +
				", region_gb='" + region_gb + '\'' +
				", region_desc='" + region_desc + '\'' +
				", city='" + city + '\'' +
				", city_desc='" + city_desc + '\'' +
				", district='" + district + '\'' +
				", district_desc='" + district_desc + '\'' +
				", telephone='" + telephone + '\'' +
				", tel_ex='" + tel_ex + '\'' +
				", mobile_phone='" + mobile_phone + '\'' +
				", fax='" + fax + '\'' +
				", fax_ex='" + fax_ex + '\'' +
				", email='" + email + '\'' +
				", has_credit_code='" + has_credit_code + '\'' +
				", tax_number='" + tax_number + '\'' +
				", business_license='" + business_license + '\'' +
				", organization_code='" + organization_code + '\'' +
				", credit_code='" + credit_code + '\'' +
				", trading_partner='" + trading_partner + '\'' +
				", is_key_account='" + is_key_account + '\'' +
				", superior_group='" + superior_group + '\'' +
				", superior_group_desc='" + superior_group_desc + '\'' +
				", parent_company='" + parent_company + '\'' +
				", parent_company_desc='" + parent_company_desc + '\'' +
				", controller='" + controller + '\'' +
				", old_number='" + old_number + '\'' +
				", classification='" + classification + '\'' +
				", classification_desc='" + classification_desc + '\'' +
				", industry1='" + industry1 + '\'' +
				", industry1_desc='" + industry1_desc + '\'' +
				", industry2='" + industry2 + '\'' +
				", industry2_desc='" + industry2_desc + '\'' +
				", industry='" + industry + '\'' +
				", business_scope='" + business_scope + '\'' +
				", registration_capital='" + registration_capital + '\'' +
				", registration_date='" + registration_date + '\'' +
				", enterprise_nature='" + enterprise_nature + '\'' +
				", enterprise_nature_desc='" + enterprise_nature_desc + '\'' +
				", legal_representative='" + legal_representative + '\'' +
				", legal_representative_id='" + legal_representative_id + '\'' +
				", black_list='" + black_list + '\'' +
				", black_list_desc='" + black_list_desc + '\'' +
				", create_user='" + create_user + '\'' +
				", create_user_co='" + create_user_co + '\'' +
				", company='" + company + '\'' +
				", co_partner_type='" + co_partner_type + '\'' +
				", partner_id='" + partner_id + '\'' +
				", delete='" + delete + '\'' +
				", bank_info=" + bank_info +
				", co_info=" + co_info +
				", system_info=" + system_info +
				'}';
	}
}
