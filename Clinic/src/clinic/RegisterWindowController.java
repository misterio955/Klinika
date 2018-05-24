package clinic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RegisterWindowController implements Initializable {
    private Alert alert = new Alert(Alert.AlertType.WARNING);
    private DatabaseConnection dbConn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            dbConn = new DatabaseConnection("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/baza_klinika?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");

            System.out.println("polaczono");
            dbConn.setDoctorsList();
            dbConn.setPatientsList();
            dbConn.setVisitsList();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(RegisterWindowController.class.getName()).log(Level.SEVERE, null, ex);
       }
    }

    //    REGISTER
    @FXML
    private TextField textNameR;

    @FXML
    private TextField textSurnameR;

    @FXML
    private TextField textPeselR;

    @FXML
    private TextField textEmailR;

    @FXML
    private void registerButton(ActionEvent e) {
        if (textNameR.getLength() <= 3) {
            System.out.println("Imie jest za któtkie");
            alert.setTitle("Bląd");
            alert.setContentText("Imie jest za krótkie");
            alert.showAndWait();
        } else {
            System.out.println(textNameR.getText());
            System.out.println(textSurnameR.getText());
            System.out.println(textPeselR.getText());
            System.out.println(textEmailR.getText());
        }
    }

    //LIST PATIENT
    @FXML
    private TextField textFindPeselP;
    @FXML
    private TextField textFindNameP;
    @FXML
    private TextField textFindSurnameP;

    @FXML
    private TableView<Patient> tableListP;
    @FXML
    private TableColumn<Patient,String> columnTablePeselP;
    @FXML
    private TableColumn<Patient,String> columnTableNameP;
    @FXML
    private TableColumn<Patient,String> columnTableSurnameP;
    @FXML
    private TableColumn<Patient,String> columnTableEmailP;
    @FXML
    private TableColumn<Patient,String> columnTableIDP;

    @FXML
    private void findPeselP(ActionEvent event) {
        List<Patient> x = dbConn.getPatientsList();
        for(Patient patient: x)
        {
//            System.out.println(patient.getPesel());
            tableListP.getItems().add(new Patient(patient.getID(),patient.getPesel(),patient.getImie(),patient.getNazwisko(),patient.getTelefon()));
        }

        columnTableIDP.setCellValueFactory(new PropertyValueFactory<>("ID"));
        columnTableIDP.setVisible(false);
        columnTablePeselP.setCellValueFactory(new PropertyValueFactory<>("Pesel"));
        columnTableNameP.setCellValueFactory(new PropertyValueFactory<>("Imie"));
        columnTableSurnameP.setCellValueFactory(new PropertyValueFactory<>("Nazwisko"));
        columnTableEmailP.setCellValueFactory(new PropertyValueFactory<>("Telefon"));

    }

    @FXML
    private void findInicialP(ActionEvent event) {

    }
    //LIST DOCTOR

    //LIST VISIT

    //DATA VISIT CHANGE

}
