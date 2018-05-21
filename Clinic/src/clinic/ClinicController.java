
package clinic;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClinicController implements Initializable {

    DatabaseConnection dbConn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void connect() {
        try {
            dbConn = new DatabaseConnection("com.mysql.jdbc.Driver", "jdbc:mysql://127.0.0.1:3306/klinika", "root", "");
            System.out.println("polaczono");
            dbConn.setDoctorsList();
            dbConn.setPatientsList();
            dbConn.setVisitsList();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ClinicController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void showPatients() {

        try {
            connect();
           // dbConn.showList(dbConn.getPatientsList());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");  
           // dbConn.showList(dbConn.getDoctorsList());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"); 
            //dbConn.showList(dbConn.getVisitsList());
            dbConn.showList(dbConn.getPatientByPESEL("84112300687"));
            dbConn.shutdown();
        } catch (SQLException ex) {
            Logger.getLogger(ClinicController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void quit(ActionEvent e) {

        Runtime.getRuntime().exit(1);
    }

    @FXML
    private void login(ActionEvent e) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RegisterWindow.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
            ((Node) (e.getSource())).getScene().getWindow().hide();
        } catch (IOException eL) {
            eL.printStackTrace();
        }

    }

}
