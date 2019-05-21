package se.fk.hack.mft.vo;

public class Ledighet {
    private String id;
    private String from;
    private String tom;
    private Ledighetstyp typ;
    private boolean godkand;

    public Ledighet() {}

    public Ledighet(String id, String from, String tom, Ledighetstyp typ, boolean godkand) {
        this.id = id;
        this.from = from;
        this.tom = tom;
        this.typ = typ;
        this.godkand = godkand;
    }

    public Ledighetstyp matches(String date) {
        if (from.compareTo(date) <= 0 && tom.compareTo(date) >= 0) {
            return typ;
        } else {
            return null;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public boolean isGodkand() {
        return godkand;
    }

    public void setGodkand(boolean godkand) {
        this.godkand = godkand;
    }

    public Ledighetstyp getTyp() {
        return typ;
    }

    public void setTyp(Ledighetstyp typ) {
        this.typ = typ;
    }
}
