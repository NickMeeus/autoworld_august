package be.vdab.util;

import java.io.Serializable;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Datum implements Serializable, Comparable<Datum> {
    private static final long serialVersionUID = 1L;

    private final int dag;
    private final int maand;
    private final int jaar;

    public Datum(int dag, int maand, int jaar) {
        if (jaar < 1583 || jaar > 4099 || maand < 1 || maand > 12 || dag < 1 || dag > 31) {
            throw new DatumException();
        }
        if (maand == 2) {
            if((isSchrikkeljaar(jaar) && dag > 29) || (!isSchrikkeljaar(jaar) && dag > 28)) {
                throw new DatumException();
            }
        }
        if ((maand == 2 || maand == 4 || maand == 6 || maand == 9 || maand == 11) && dag > 30) {
            throw new DatumException();
        }
        this.dag = dag;
        this.maand = maand;
        this.jaar = jaar;
    }
    
    private boolean isSchrikkeljaar(int jaar) {
        if (jaar % 4 == 0) {
            if (jaar % 100 == 0 && jaar != 1600 && jaar != 2000) {
                return false;
            }
            return true;
        }
        return false;
    }

    public int getDag() {
        return dag;
    }

    public int getMaand() {
        return maand;
    }

    public int getJaar() {
        return jaar;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(dag).append(maand).append(jaar).toHashCode();
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
        final Datum other = (Datum) obj;
        if (this.dag != other.dag) {
            return false;
        }
        if (this.maand != other.maand) {
            return false;
        }
        if (this.jaar != other.jaar) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (dag < 10) {
            sb.append("0");
        }
        sb.append(dag);
        sb.append("/");
        
        if (maand < 10) {
            sb.append("0");
        }
        sb.append(maand);
        sb.append("/");
        sb.append(jaar);

        return sb.toString();
    }

    @Override
    public int compareTo(Datum datum) {
        return new CompareToBuilder().append(this.jaar, datum.jaar).append(this.maand, datum.maand).append(this.dag, datum.dag).toComparison();
    }

}
