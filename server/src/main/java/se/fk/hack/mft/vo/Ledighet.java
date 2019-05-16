package se.fk.hack.mft.vo;

public class Ledighet {
    String from;
    String tom;
    boolean godkand;

    public Ledighet(String from, String tom, boolean godkand) {
        this.from = from;
        this.tom = tom;
        this.godkand = godkand;
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
}
