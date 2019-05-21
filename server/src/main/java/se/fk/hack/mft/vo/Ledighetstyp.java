package se.fk.hack.mft.vo;

public class Ledighetstyp {
    private String id;
    private String namn;

    public Ledighetstyp() {}

    public Ledighetstyp(String id, String namn) {
        this.id = id;
        this.namn = namn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamn() {
        return namn;
    }

    public void setNamn(String namn) {
        this.namn = namn;
    }
}
