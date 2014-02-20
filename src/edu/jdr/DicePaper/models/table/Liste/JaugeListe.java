package edu.jdr.DicePaper.models.table.Liste;

/**
 * Created by mario on 30/01/14.
 */
public class JaugeListe extends MasterListe{
    private int min;
    private int max;

    public JaugeListe(String nom, String nomUnivers, int min, int max) {
        super(nom,nomUnivers);
        this.min = min;
        this.max = max;
    }

    public JaugeListe(int jaugeListeId, String nom, String nomUnivers, int min, int max) {
        super(jaugeListeId,nom,nomUnivers);
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    @Override
    public String toString(){
        return nom+" (de "+min+" à "+max+")";
    }
}
