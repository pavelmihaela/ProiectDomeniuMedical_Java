package pojo;

import java.util.Arrays;
import java.util.Date;

public class Medic extends AngajatSpital implements Operatiuni{

    private String[] pacienti;
    private Specializare specializare;
    private Date dataIncasatSalariu;


    public Medic() {
    }

    public Medic(String nume, String adresa, String codIdentificare, Date dataNasterii, double salariu, EchipamentMedical[] echipamentMedical, String[] pacienti) {
        super(nume, adresa, codIdentificare, dataNasterii, salariu, echipamentMedical);
        this.pacienti = pacienti;
    }

    /*
    public Medic(String nume, String adresa, String codIdentificare, Date dataNasterii, double salariu, EchipamentMedical[] echipamentMedical, String[] pacienti, Specializare specializare, Date dataIncasatSalariu) {
        super(nume, adresa, codIdentificare, dataNasterii, salariu, echipamentMedical);
        this.pacienti = pacienti;

        this.specializare = specializare;
        this.dataIncasatSalariu = dataIncasatSalariu;
    }

     */

    public String[] getPacienti() {
        return pacienti;
    }

    public void setPacienti(String[] pacienti) {
        this.pacienti = pacienti;
    }


    public Specializare getSpecializare() {
        return specializare;
    }

    public void setSpecializare(Specializare specializare) {
        this.specializare = specializare;
    }

    public Date getDataIncasatSalariu() {
        return dataIncasatSalariu;
    }

    public void setDataIncasatSalariu(Date dataIncasatSalariu) {
        this.dataIncasatSalariu = dataIncasatSalariu;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Medic clona= (Medic) super.clone();

        String[] pacienti= this.pacienti.clone();
        clona.setPacienti(pacienti);

        clona.setSpecializare(this.getSpecializare());
        clona.setDataIncasatSalariu(new Date(getDataIncasatSalariu().getTime()));

        return clona;

    }

    @Override
    public String toString() {
        return  super.toString() + "\n" +
                  Arrays.toString(pacienti) +","+

                "," + specializare +
                ", " + dataIncasatSalariu ;

    }



    @Override
    public void atribuieSpecializare(Specializare numeSpecializare) {

        specializare=numeSpecializare;
    }
}
