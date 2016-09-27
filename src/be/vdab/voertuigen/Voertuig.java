package be.vdab.voertuigen;

import be.vdab.util.Datum;
import be.vdab.util.mens.Mens;
import be.vdab.util.mens.MensException;
import be.vdab.util.mens.Rijbewijs;
import be.vdab.voertuigen.div.DIV;
import be.vdab.voertuigen.div.Nummerplaat;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public abstract class Voertuig implements Serializable, Comparable<Voertuig> {

    final Nummerplaat NUMMERPLAAT;
    String merk;
    Datum DatumEersteIngebruikname;
    int aankoopprijs;
    int zitplaatsen;
    Mens bestuurder;
    Set<Mens> inzittenden = new TreeSet<>();

    public Voertuig(String merk, Datum DatumEersteIngebruikname, int aankoopprijs, int zitplaatsen, Mens bestuurder, Mens... personen) throws MensException {
        NUMMERPLAAT = DIV.INSTANCE.getNummerplaat();
        this.merk = merk;
        this.DatumEersteIngebruikname = DatumEersteIngebruikname;
        this.aankoopprijs = aankoopprijs;
        
        if (zitplaatsen < 1) throw new IllegalArgumentException();
        this.zitplaatsen = zitplaatsen;

        setBestuurder(bestuurder);

        for (Mens mens : personen) {
            addIngezetene(mens);
        }
    }

    public Nummerplaat getNummerplaat() {
        return NUMMERPLAAT;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public Datum getDatumEersteIngebruikname() {
        return DatumEersteIngebruikname;
    }

    public void setDatumEersteIngebruikname(Datum DatumEersteIngebruikname) {
        this.DatumEersteIngebruikname = DatumEersteIngebruikname;
    }

    public int getAankoopprijs() {
        return aankoopprijs;
    }

    public void setAankoopprijs(int aankoopprijs) {
        this.aankoopprijs = aankoopprijs;
    }

    public int getZitplaatsen() {
        return zitplaatsen;
    }

    public void setZitplaatsen(int zitplaatsen) {
        this.zitplaatsen = zitplaatsen;
    }

    public Mens getBestuurder() {
        return bestuurder;
    }

    public void setBestuurder(Mens bestuurder) throws MensException {
        if (inzittenden.contains(bestuurder) || inzittenden.size() < zitplaatsen) {
            List<Rijbewijs> rijbewijzen = Arrays.asList(bestuurder.getRijbewijs());
            List<Rijbewijs> nodigeRijbewijzen = Arrays.asList(getToegestaneRijbewijzen());
            if (CollectionUtils.containsAny(rijbewijzen, nodigeRijbewijzen)) {
                this.bestuurder = bestuurder;
                inzittenden.add(bestuurder);
            } else {
                throw new MensException();
            }
        } else {
            throw new MensException();
        }
    }

    public Set<Mens> getIngezetenen() {
        return inzittenden;
    }

    public void addIngezetene(Mens mens) throws MensException {
        if (inzittenden.size() < zitplaatsen || inzittenden.contains(mens)) {
            inzittenden.add(mens);
        } else {
            throw new MensException();
        }
    }

    public boolean isIngezetene(Mens mens) {
        return inzittenden.contains(mens);
    }

    public Set<Mens> getIngezeteneExclusiefBestuurder() {
        Set<Mens> ingezeteneExclusiefBestuurder = new TreeSet<>(inzittenden);
        ingezeteneExclusiefBestuurder.remove(bestuurder);
        return ingezeteneExclusiefBestuurder;
    }

    protected abstract Rijbewijs[] getToegestaneRijbewijzen();

    protected abstract int getMAX_ZITPLAATSEN();

    public interface SerializableComparator<T> extends Serializable, Comparator<T> {
    }

    public static SerializableComparator<Voertuig> getMerkComparator() {
        return new SerializableComparator<Voertuig>() {
            @Override
            public int compare(Voertuig v1, Voertuig v2) {
                return v1.getMerk().compareTo(v2.getMerk());
            }
        };
    }

    public static SerializableComparator<Voertuig> getAankoopprijsComparator() {
        return new SerializableComparator<Voertuig>() {
            @Override
            public int compare(Voertuig v1, Voertuig v2) {
                if (v1.getAankoopprijs() > v2.getAankoopprijs()) {
                    return 1;
                }
                if (v1.getAankoopprijs() < v2.getAankoopprijs()) {
                    return -1;
                }
                return 0;
            }
        };
    }

    @Override
    public String toString() {
        if (getIngezeteneExclusiefBestuurder().isEmpty()) {
            return NUMMERPLAAT + " " + merk + " " + DatumEersteIngebruikname + " " + aankoopprijs + " " + bestuurder;
        }
        return NUMMERPLAAT + " " + merk + " " + DatumEersteIngebruikname + " " + aankoopprijs + " " + bestuurder + " " + getIngezeteneExclusiefBestuurder();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(NUMMERPLAAT).toHashCode();
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
        final Voertuig other = (Voertuig) obj;
        return new EqualsBuilder().append(NUMMERPLAAT, other.NUMMERPLAAT).isEquals();
    }

    @Override
    public int compareTo(Voertuig voertuig) {
        return new CompareToBuilder().append(NUMMERPLAAT, voertuig.NUMMERPLAAT).toComparison();
    }
}
