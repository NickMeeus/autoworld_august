package be.vdab.voertuigen.div;

import java.io.Serializable;
import java.util.Objects;

public class Nummerplaat implements Serializable, Comparable<Nummerplaat> {
    private String plaat;

    protected Nummerplaat(String plaat) {
        this.plaat = plaat;
    }

    public String getPlaat() {
        return plaat;
    }

    @Override
    public String toString() {
        return plaat;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + Objects.hashCode(this.plaat);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Nummerplaat other = (Nummerplaat) obj;
        if (!Objects.equals(this.plaat, other.plaat)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Nummerplaat t) {
        return plaat.compareTo(t.plaat);
    }
}
