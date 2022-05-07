package hu.unideb.hu;

public class Feladatok {
    String nevek;
    String feladat;
    String ceg_id;

    public Feladatok(String nevek, String feladat, String ceg_id) {
        this.nevek = nevek;
        this.feladat = feladat;
        this.ceg_id = ceg_id;
    }

    public String getNevek() {
        return nevek;
    }


    public String getFeladat() {
        return feladat;
    }


    public String getCeg_id() {
        return ceg_id;
    }


}
