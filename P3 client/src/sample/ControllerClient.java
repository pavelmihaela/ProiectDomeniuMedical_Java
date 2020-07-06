package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ControllerClient {

    @FXML
    private TextArea txtOut;
    @FXML
    private TextField txtInput;

    @FXML
    private void stop() {
        try (Socket socket = new Socket("localhost", 2000)) {
            try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
                out.writeObject("stop");
                txtOut.appendText(in.readObject() + "\n");
            }
        } catch (Exception ex) {
            Alert err = new Alert(Alert.AlertType.ERROR, ex.toString());
            err.showAndWait();
        }
    }

    private void procesareRaspuns(ObjectInputStream in) throws Exception {
        String raspuns = in.readObject().toString();
        if (raspuns.equals("err")){
            txtOut.appendText(in.readObject()+"\n");
        } else {
            txtOut.appendText("\n");
            int n = (Integer) in.readObject();
            for (int i=0;i<n;i++){
                txtOut.appendText(in.readObject()+"\n");
            }
        }
    }

    @FXML
    private void idMedic() {
        try (Socket socket = new Socket("localhost", 2000)) {
            try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
                out.writeObject("idMedic");
                out.writeObject(txtInput.getText().trim());
                procesareRaspuns(in);
            }
        } catch (Exception ex) {
            Alert err = new Alert(Alert.AlertType.ERROR, ex.toString());
            err.showAndWait();
        }

    }

    @FXML
    private void nume() {
        try (Socket socket = new Socket("localhost", 2000)) {
            try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
                out.writeObject("nume");
                out.writeObject(txtInput.getText().trim());
                procesareRaspuns(in);
            }
        } catch (Exception ex) {
            Alert err = new Alert(Alert.AlertType.ERROR, ex.toString());
            err.showAndWait();
        }

    }

    @FXML
    private void pacient() {
        try (Socket socket = new Socket("localhost", 2000)) {
            try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
                out.writeObject("pacienti");
                out.writeObject(txtInput.getText().trim());
                procesareRaspuns(in);
            }
        } catch (Exception ex) {
            Alert err = new Alert(Alert.AlertType.ERROR, ex.toString());
            err.showAndWait();
        }

    }



}
