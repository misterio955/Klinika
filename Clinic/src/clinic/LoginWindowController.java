package clinic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginWindowController extends Clinic implements Initializable {
    private Alert alert = new Alert(Alert.AlertType.WARNING);
    DatabaseConnection dbConn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connect();
    }

    public void connect() {
        try {
            dbConn = new DatabaseConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/klinika", "root", "");
            System.out.println("polaczono");
            dbConn.setDoctorsList();
            dbConn.setPatientsList();
            dbConn.setVisitsList();
            dbConn.updateCopyLists();

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(LoginWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    
    private void showPatients() {

        try {
            connect();
           dbConn.showList(dbConn.getVisitsList()); 
            //dbConn.makeXVisitsPerMonth(50);
            //dbConn.createVisit(dbConn.getDoctorByID("2"), dbConn.getPatientByPESEL("80012236149"), "2018-06-19 14:15:00");
            //dbConn.changeVisitDate(dbConn.getVisitByID("8"), "2018-06-19 12:45:00");
            System.out.println("--------------------------");
            dbConn.showList(dbConn.getVisitsList()); 
           dbConn.compareLists();

            dbConn.shutdown();
        } catch (SQLException ex) {
            Logger.getLogger(LoginWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void quit(ActionEvent e) {
        Clinic.window.close();
    }

    @FXML
    private TextField loginAdmin;
    @FXML
    private TextField passwordAdmin;

    @FXML
    private void loginAdmin(ActionEvent e) {
        
        if (loginAdmin.getText().equals("admin") && passwordAdmin.getText().equals("admin")) {
            try {
               // showPatientas();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RegisterWindow.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.show();
                Clinic.window.hide();
                ((Node) (e.getSource())).getScene().getWindow().hide();
            } catch (IOException eL) {
            }
        } else {
            alert.setTitle("Uwaga!");
            alert.setHeaderText("Złe dane logowania");
            alert.setContentText("Nie znaleziono takiego użytkownika lub hasło sie nie poprawne");
            alert.showAndWait();
        }
    }

    //Logowanie doktora
    @FXML
    private TextField loginDoctor;
    @FXML
    private TextField passwordDoctor;

    @FXML
    private void loginDoctor(ActionEvent event) {


        if (dbConn.getDoctorByID(loginDoctor.getText()) != null && dbConn.getDoctorByID(loginDoctor.getText()).getPassword().equals(passwordDoctor.getText())) {
            DoctorWindowController.setIDDoctor(Integer.valueOf(dbConn.getDoctorByID(loginDoctor.getText()).getID()));
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DoctorWindow.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.show();
                Clinic.window.hide();
                ((Node) (event.getSource())).getScene().getWindow().hide();

            } catch (IOException eL) {
            }
        } else {
            alert.setTitle("Uwaga!");
            alert.setHeaderText("Złe dane logowania");
            alert.setContentText("Nie znaleziono takiego użytkownika lub hasło sie nie poprawne");
            alert.showAndWait();
        }


    }


}
