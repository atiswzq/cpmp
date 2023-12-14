package cn.cofco.cpmp.utils;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by xiaox on 2017/6/5.
 * 当前供应商的账号信息
 */
@Component
@Scope("request")
public class CurrentSplrUserInfo {
    private Long acntId;

    private Long splrId;

    private String acntTyp;

    private String usrNam;
    
    private String fullNam;

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

	public void clean() {
    	acntId = null;
    	splrId = null;
    	acntTyp = "";
    	usrNam = "";
    	fullNam = "";
    }
}
