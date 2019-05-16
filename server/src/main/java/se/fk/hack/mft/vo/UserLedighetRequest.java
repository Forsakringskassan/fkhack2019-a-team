package se.fk.hack.mft.vo;

public class UserLedighetRequest {
    public String kortid;
    public String from;
    public String tom;
    public String ledighetstyp;

    public String getKortid() {
        return kortid;
    }

    public void setKortid(String kortid) {
        this.kortid = kortid;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTom() {
        return tom;
    }

    public void setTom(String tom) {
        this.tom = tom;
    }

    public String getLedighetstyp() {
        return ledighetstyp;
    }

    public void setLedighetstyp(String ledighetstyp) {
        this.ledighetstyp = ledighetstyp;
    }
}
