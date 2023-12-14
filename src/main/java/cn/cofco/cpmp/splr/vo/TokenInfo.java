package cn.cofco.cpmp.splr.vo;

import java.sql.Timestamp;

public class TokenInfo {
	
	private String access_token;
	
	private Timestamp token_exp;
	
    private Long acntId;

    private Long splrId;

    private String acntTyp;
    
    private String usrNam;
    
    private String fullNam;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public Timestamp getToken_exp() {
		return token_exp;
	}

	public void setToken_exp(Timestamp token_exp) {
		this.token_exp = token_exp;
	}

	public Long getAcntId() {
		return acntId;
	}

	public void setAcntId(Long acntId) {
		this.acntId = acntId;
	}

	public Long getSplrId() {
		return splrId;
	}

	public void setSplrId(Long splrId) {
		this.splrId = splrId;
	}

	public String getAcntTyp() {
		return acntTyp;
	}

	public void setAcntTyp(String acntTyp) {
		this.acntTyp = acntTyp;
	}

	public String getUsrNam() {
		return usrNam;
	}

	public void setUsrNam(String usrNam) {
		this.usrNam = usrNam;
	}

	public String getFullNam() {
		return fullNam;
	}

	public void setFullNam(String fullNam) {
		this.fullNam = fullNam;
	}
    
    
}
