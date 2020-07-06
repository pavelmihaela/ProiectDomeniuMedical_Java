package pojo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");

    public static void main(String[] args) {
        Main app = new Main();

        List<Medic> lista = app.citire("medic.csv");
        app.afisareLista(lista, "Lista medici:");

    }



    private void afisareLista(List<?> lista, String mesaj) {
        System.out.println(mesaj);
        for (Object o : lista) {
            System.out.println(o);
        }
    }


    public List<Medic> citire(String numeFisier) {
        List<Medic> lista = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(numeFisier))) {
            String linie;
            while ((linie = in.readLine()) != null) {
               Medic medic = new Medic();
                String[] t = linie.split(",");
               medic.setCodIdentificare(t[0].trim());
                medic.setNume(t[1].trim());
                medic.setAdresa(t[2].trim());
                medic.setDataNasterii(df.parse(t[3].trim()));
                medic.setSalariu(Double.parseDouble(t[4].trim()));

                t = in.readLine().split(",");
                EchipamentMedical[] echipament = new EchipamentMedical[t.length];

                for (int i = 0; i < t.length; i++) {

                    echipament[i]= EchipamentMedical.valueOf(t[i].trim().toUpperCase());
                }
                medic.setEchipamentMedical(echipament);
                medic.setPacienti(in.readLine().split(","));
                lista.add(medic);
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
        return lista;
    }

}
