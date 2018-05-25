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
    private TableColumn<Patient, String> columnTablePeselP;
    @FXML
    private TableColumn<Patient, String> columnTableNameP;
    @FXML
    private TableColumn<Patient, String> columnTableSurnameP;
    @FXML
    private TableColumn<Patient, String> columnTableEmailP;

    @FXML
    private void findPeselP(ActionEvent event) {
        tableListP.getItems().clear();

        boolean isNumbers = false;
        char[] isNumbersOnly = textFindPeselP.getText().toCharArray();

        //Sprawdzanie czy pesel posiada same liczby
        for (int i = 0; i < isNumbersOnly.length; i++) {
            if (isNumbersOnly[i] >= '0' && isNumbersOnly[i] <= '9') {
                isNumbers = true;
            } else {
                isNumbers = false;
                break;
            }
        }

        //Sprawdzenie czy pesel ma 11 znakow i czy jest w bazie
        if (textFindPeselP.getLength() == 11 && dbConn.getPatientByPESEL(textFindPeselP.getText()) != null && isNumbers == true) {
            Patient patient = dbConn.getPatientByPESEL(textFindPeselP.getText());

            tableListP.getItems().add(new Patient(patient.getID(), patient.getPesel(), patient.getImie(), patient.getNazwisko(), patient.getTelefon()));

            columnTablePeselP.setCellValueFactory(new PropertyValueFactory<>("Pesel"));
            columnTableNameP.setCellValueFactory(new PropertyValueFactory<>("Imie"));
            columnTableSurnameP.setCellValueFactory(new PropertyValueFactory<>("Nazwisko"));
            columnTableEmailP.setCellValueFactory(new PropertyValueFactory<>("Telefon"));
        } else {
            alert.setTitle("Nieprawidłowa długość peselu");
            alert.setHeaderText("Błąd w polu pesel");
            alert.setContentText("Podany pesel jest błędny lub nie ma takiego pacjenta w bazie");
            alert.showAndWait();

        }

    }

    @FXML
    private void findInicialP(ActionEvent event) {
        tableListP.getItems().clear();

        String Help = textFindNameP.getText().toLowerCase() + textFindSurnameP.getText().toLowerCase();
        char[] isLetterOnly = Help.toCharArray();
        boolean isLetter = false;
        //Sprawdzanie czy imie i nazwisko posiada same litery
        for (int i = 0; i < isLetterOnly.length; i++) {
            if (isLetterOnly[i] >= 'a' && isLetterOnly[i] <= 'z') {
                isLetter = true;
            } else {
                isLetter = false;
                break;
            }
        }

        if (dbConn.getPatientByName(textFindNameP.getText(), textFindSurnameP.getText()) != null && isLetter == true) {
            List<Patient> patient = dbConn.getPatientByName(textFindNameP.getText(), textFindSurnameP.getText());

            for (Patient patientTab : patient) {
                tableListP.getItems().add(new Patient(patientTab.getID(), patientTab.getPesel(), patientTab.getImie(), patientTab.getNazwisko(), patientTab.getTelefon()));
            }

            columnTablePeselP.setCellValueFactory(new PropertyValueFactory<>("Pesel"));
            columnTableNameP.setCellValueFactory(new PropertyValueFactory<>("Imie"));
            columnTableSurnameP.setCellValueFactory(new PropertyValueFactory<>("Nazwisko"));
            columnTableEmailP.setCellValueFactory(new PropertyValueFactory<>("Telefon"));
        } else {
            alert.setTitle("Uwaga!");
            alert.setHeaderText("Błąd w polu imie lub nazwisko");
            alert.setContentText("Podane imie lub naziwsko zawiera błąd lub nie ma go w bazie");
            alert.showAndWait();
        }

    }

    //LIST DOCTOR

    //LIST VISIT

    //DATA VISIT CHANGE

}
