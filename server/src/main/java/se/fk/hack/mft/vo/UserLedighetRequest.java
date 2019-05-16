package se.fk.hack.mft.vo;

public class UserLedighetRequest {
    public String kortid;
    public Ledighet ledighet;

    public String getKortid() {
        return kortid;
    }

    public void setKortid(String kortid) {
        this.kortid = kortid;
    }

    public Ledighet getLedighet() {
        return ledighet;
    }

    public void setLedighet(Ledighet ledighet) {
        this.ledighet = ledighet;
    }
}
