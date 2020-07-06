package pojo;

import javax.swing.event.CaretListener;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

public class AngajatSpital  implements Comparable<AngajatSpital>, Cloneable, Serializable {

    private String nume, adresa, codIdentificare;
    private Date dataNasterii;
    private double salariu;
    private EchipamentMedical[] echipamentMedical;


    public AngajatSpital() {
    }

    public AngajatSpital(String nume, String adresa, String codIdentificare, Date dataNasterii, double salariu,EchipamentMedical[] echipamentMedical ) {
        this.nume = nume;
        this.adresa = adresa;
        this.codIdentificare = codIdentificare;
        this.dataNasterii = dataNasterii;
        this.salariu = salariu;
        this.echipamentMedical=echipamentMedical;

    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getCodIdentificare() {
        return codIdentificare;
    }

    public void setCodIdentificare(String codIdentificare) {
        this.codIdentificare = codIdentificare;
    }

    public Date getDataNasterii() {
        return dataNasterii;
    }

    public void setDataNasterii(Date dataNasterii) {
        this.dataNasterii = dataNasterii;
    }

    public double getSalariu() {
        return salariu;
    }

    public void setSalariu(double salariu) {
        this.salariu = salariu;
    }

    public EchipamentMedical[] getEchipamentMedical() {
        return echipamentMedical;
    }

    public void setEchipamentMedical(EchipamentMedical[] echipamentMedical) {
        this.echipamentMedical = echipamentMedical;
    }

    @Override
    public String toString() {
        return
                 nume + "," +
                 adresa + ","+ codIdentificare +
               "," + dataNasterii +
                "," + salariu +
                "," + "\n" +
                         Arrays.toString(echipamentMedical) ;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AngajatSpital that = (AngajatSpital) o;

       return codIdentificare.equals(that.codIdentificare);
    }

    @Override
    public int hashCode() {
        return codIdentificare.hashCode();
    }

    @Override
    public int compareTo(AngajatSpital angajatSpital) {
        return dataNasterii.compareTo(angajatSpital.getDataNasterii());}

}
