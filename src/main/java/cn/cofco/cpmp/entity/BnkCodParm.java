package cn.cofco.cpmp.entity;

import java.util.Date;

public class BnkCodParm {
    private String bnkCod;

    private String bnkNam;

    private String bnkCnty;

    private Date crtTim;

    public String getBnkCod() {
        return bnkCod;
    }

    public void setBnkCod(String bnkCod) {
        this.bnkCod = bnkCod;
    }

    public String getBnkNam() {
        return bnkNam;
    }

    public void setBnkNam(String bnkNam) {
        this.bnkNam = bnkNam;
    }

    public String getBnkCnty() {
        return bnkCnty;
    }

    public void setBnkCnty(String bnkCnty) {
        this.bnkCnty = bnkCnty;
    }

    public Date getCrtTim() {
        return crtTim;
    }

    public void setCrtTim(Date crtTim) {
        this.crtTim = crtTim;
    }
}