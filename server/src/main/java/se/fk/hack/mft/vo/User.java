package se.fk.hack.mft.vo;

public class User {
    private String kortid;
    private String namn;

    public User(String kortid, String namn) {
        this.kortid = kortid;
        this.namn = namn;
    }

    public String getKortid() {
        return kortid;
    }

    public void setKortid(String kortid) {
        this.kortid = kortid;
    }

    public String getNamn() {
        return namn;
    }

    public void setNamn(String namn) {
        this.namn = namn;
    }
}
