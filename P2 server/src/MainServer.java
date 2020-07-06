import pojo.EchipamentMedical;
import pojo.Main;
import pojo.Medic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class MainServer {

    private Connection c;
    private boolean serverActiv = true;
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");


    private void init() throws Exception{
        c = DriverManager.getConnection("jdbc:sqlite:medic.db");
        DatabaseMetaData metaData = c.getMetaData();
        try(ResultSet r = metaData.getTables(null,null,"MEDIC",new String[]{"TABLE"})){
//
            if (!r.next()){
                try(Statement s = c.createStatement()){
                    String comandaCreare = "create table MEDIC (idMedic varchar(10),nume varchar(50),adresa varchar(30),"+
                            "data_nastere Date,pacienti varchar(200),salariu double,echipamente varchar(100),"+
                            "specializare varchar(30),data_salariuIncasat varchar(10))";
                    s.executeUpdate(comandaCreare);
                }
            } else {
                try(Statement s = c.createStatement()){
                    s.executeUpdate("delete from MEDIC");
                }
            }
        }

        Main p1= new Main();
        List<Medic> lista = p1.citire("medic.csv");
        try(Statement s = c.createStatement()){
            for (Medic medic:lista){
                StringBuilder sbInsertPaicienti = new StringBuilder();
                String[] pacienti = medic.getPacienti();
                int n = pacienti.length;
                for (int i=0;i<n-1;i++){
                    sbInsertPaicienti.append(pacienti[i]).append(",");
                }
                sbInsertPaicienti.append(pacienti[n-1]);

                StringBuilder sbEchipamente = new StringBuilder();
                EchipamentMedical[] echipamentMedical = medic.getEchipamentMedical();
                n = echipamentMedical.length;
                for (int i=0;i<n-1;i++){
                    sbEchipamente.append(echipamentMedical[i].toString()).append(",");
                }
                sbEchipamente.append(echipamentMedical[n-1]);

                String specializare = medic.getSpecializare()==null?"":medic.getSpecializare().toString();
                String dataIncasatSalariu = medic.getDataIncasatSalariu()==null?"":df.format(medic.getDataIncasatSalariu());
                String comandaInserare = "insert into MEDIC values('"+medic.getCodIdentificare()+"','"+medic.getNume()+"','"+
                        medic.getAdresa()+"',date('"+df.format(medic.getDataNasterii())+"'),'"+sbInsertPaicienti+"',"+
                        medic.getSalariu()+",'"+sbEchipamente+"','"+specializare+"','"
                        +dataIncasatSalariu+"')";

                System.out.println(comandaInserare);
                s.executeUpdate(comandaInserare);
            }
        }
    }

    private List<String> interogare(String comanda) throws Exception{
        List<String> raspuns = new ArrayList<>();
        try(Statement s = c.createStatement();ResultSet r = s.executeQuery(comanda)){
            while (r.next()){
                StringBuilder linie = new StringBuilder();
                linie.append(r.getString(1)).append(";");
                linie.append(r.getString(2)).append(";");
                linie.append(r.getString(3)).append(";");
                linie.append(r.getString(4)).append(";");
                linie.append(r.getString(5)).append(";");
                linie.append(r.getDouble(6)).append(";");
                linie.append(r.getString(7)).append(";");
                linie.append(r.getInt(8)).append(";");
                linie.append(r.getString(9));
                raspuns.add(linie.toString());
            }
        }
        return raspuns;
    }



    public void comunicare(Socket socket){
        try(ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())){
            String mesaj = in.readObject().toString();
            switch (mesaj) {
                case "stop":
                    serverActiv=false;
                    System.out.println("Server oprit!");
                    out.writeObject("Server oprit!");
                    break;
                case "nume":
                case "idMedic":
                case "pacienti":
                    String valoare = in.readObject().toString();
                    String comanda = "select* from MEDIC where "+mesaj+" like '%"+valoare+"%'";
                    try{
                        List<String> rezultat = interogare(comanda);
                        out.writeObject("succes");
                        out.writeObject(rezultat.size());
                        for (String linie:rezultat){
                            out.writeObject(linie);
                        }
                    }
                    catch (Exception ex){
                        out.writeObject("err");
                        out.writeObject(ex.toString());
                    }
            }
        }
        catch (Exception ex){
            System.err.println(ex);
        }

    }


    public void start() throws Exception{
        try(ServerSocket serverSocket = new ServerSocket(2000)){
            serverSocket.setSoTimeout(5000);
            while (serverActiv){
                try{
                    Socket socket = serverSocket.accept();
                    Thread firComunicare = new Thread( () -> comunicare(socket) );
                    firComunicare.start();
                }
                catch (Exception ex){}
            }
        }
    }

    public static void main(String[] args) {
        MainServer app = new MainServer();
        try{
            app.init();
            app.start();
        }
        catch (Exception ex){
            System.err.println(ex);
        }
        finally {
            try{
                app.c.close();
            }
            catch (Exception ex){System.err.println(ex);}
        }
    }
}
