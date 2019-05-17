package se.fk.hack.mft.vo;

import java.util.ArrayList;
import java.util.List;

public class UserLedighetRange {
    private String kortid;
    private String namn;
    private String from;
    private String tom;
    private List<Ledighet> ledigheter = new ArrayList<>();
    private List<String> dagar = new ArrayList<>();

    public void handleDate(String date) {
        if (ledigheter.isEmpty()) {
            dagar.add(null);
        } else {
            boolean ledighetFound = false;
            for (Ledighet l : ledigheter) {
                String s = l.matches(date);

                if (s != null) {
                    dagar.add(s);
                    ledighetFound = true;
                    break;
                }
            }

            if (!ledighetFound) {
                dagar.add(null);
            }
        }
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

    public List<Ledighet> getLedigheter() {
        return ledigheter;
    }

    public void setLedigheter(List<Ledighet> ledigheter) {
        this.ledigheter = ledigheter;
    }

    public void addLedighet(Ledighet ledighet) {
        this.ledigheter.add(ledighet);
    }

    public List<String> getDagar() {
        return dagar;
    }

    public void setDagar(List<String> dagar) {
        this.dagar = dagar;
    }
}
