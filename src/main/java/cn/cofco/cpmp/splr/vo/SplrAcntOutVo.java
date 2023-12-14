package cn.cofco.cpmp.splr.vo;


public class SplrAcntOutVo {

	private Long acntId;
	
    private String usrNam;
    
    private String acntTyp;

    private String passwd;

    private String nam;
    
    private String mob;

	public Long getAcntId() {
		return acntId;
	}

	public void setAcntId(Long acntId) {
		this.acntId = acntId;
	}

	public String getUsrNam() {
		return usrNam;
	}
	

	public String getAcntTyp() {
		return acntTyp;
	}


	public void setAcntTyp(String acntTyp) {
		this.acntTyp = acntTyp;
	}


	public void setUsrNam(String usrNam) {
		this.usrNam = usrNam;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getNam() {
		return nam;
	}

	public void setNam(String nam) {
		this.nam = nam;
	}

	public String getMob() {
		return mob;
	}

	public void setMob(String mob) {
		this.mob = mob;
	}
    
}
