package se.fk.hack.mft.vo;

public class UserIdRequest {
    private String kortid;

    public UserIdRequest() {}

    public UserIdRequest(String kortid) {
        this.kortid = kortid;
    }

    public String getKortid() {
        return kortid;
    }

    public void setKortid(String kortid) {
        this.kortid = kortid;
    }
}
