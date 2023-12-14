package cn.cofco.cpmp.constants;

public enum ConstantsEnum {
	
	SPLR_STS_RGST("0", "注册"),
	SPLR_STS_READY_SPLR("1", "预备供方"),
	SPLR_STS_ADMT_ADT_SPLR("2", "准入审核"),
	SPLR_STS_QULF_SPLR("3", "合格供方"),
	SPLR_STS_WDOT_SPLR("4", "淘汰供方"),
	SPLR_STS_BKLT_SPLR("5", "黑名单"),
	;
	private String code;
	private String name;
	
	private ConstantsEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
}
