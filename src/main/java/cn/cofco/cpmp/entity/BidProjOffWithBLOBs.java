package cn.cofco.cpmp.entity;

public class BidProjOffWithBLOBs extends BidProjOff {
    private String bidBok;

    private String awdInfo;

    public String getBidBok() {
        return bidBok;
    }

    public void setBidBok(String bidBok) {
        this.bidBok = bidBok;
    }

    public String getAwdInfo() {
        return awdInfo;
    }

    public void setAwdInfo(String awdInfo) {
        this.awdInfo = awdInfo;
    }
}