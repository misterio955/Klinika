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
            dbConn = new DatabaseConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/klinika", "root", "");

            System.out.println("polaczono");
            dbConn.setDoctorsList();
            dbConn.setPatientsList();
            dbConn.setVisitsList();
            dbConn.updateCopyLists();
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
    private void registerButton(ActionEvent event) {
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
        String sumString = textFindPeselP.getText();

        //Sprawdzenie czy pesel ma 11 znakow i czy jest w bazie
        if (textFindPeselP.getLength() == 11 && dbConn.getPatientByPESEL(textFindPeselP.getText()) != null && isNumbersOnly(sumString) == true) {
            Patient patient = dbConn.getPatientByPESEL(textFindPeselP.getText());

            tableListP.getItems().add(new Patient(patient.getID(), patient.getPesel(), patient.getImie(), patient.getNazwisko(), patient.getEmail()));

            columnTablePeselP.setCellValueFactory(new PropertyValueFactory<>("Pesel"));
            columnTableNameP.setCellValueFactory(new PropertyValueFactory<>("Imie"));
            columnTableSurnameP.setCellValueFactory(new PropertyValueFactory<>("Nazwisko"));
            columnTableEmailP.setCellValueFactory(new PropertyValueFactory<>("Email"));
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

        String sumString = textFindNameP.getText().toLowerCase() + textFindSurnameP.getText().toLowerCase();

        if (dbConn.getPatientByName(textFindNameP.getText(), textFindSurnameP.getText()) != null && isLetterOnly(sumString) == true) {
            List<Patient> patient = dbConn.getPatientByName(textFindNameP.getText(), textFindSurnameP.getText());

            for (Patient patientTab : patient) {
                tableListP.getItems().add(new Patient(patientTab.getID(), patientTab.getPesel(), patientTab.getImie(), patientTab.getNazwisko(), patientTab.getEmail()));
            }

            columnTablePeselP.setCellValueFactory(new PropertyValueFactory<>("Pesel"));
            columnTableNameP.setCellValueFactory(new PropertyValueFactory<>("Imie"));
            columnTableSurnameP.setCellValueFactory(new PropertyValueFactory<>("Nazwisko"));
            columnTableEmailP.setCellValueFactory(new PropertyValueFactory<>("Email"));
        } else {
            alert.setTitle("Uwaga!");
            alert.setHeaderText("Błąd w polu imie lub nazwisko");
            alert.setContentText("Podane imie lub naziwsko zawiera błąd lub nie ma go w bazie");
            alert.showAndWait();
        }

    }

    //LIST DOCTOR
    @FXML
    TextField textFindNameD;
    @FXML
    TextField textFindSurnameD;
    @FXML
    TextField textFindSpecializationD;
    @FXML
    TextField textFindNrRoomD;

    @FXML
    private TableView<Doctor> tableListD;
    @FXML
    private TableColumn<Doctor, String> columnTableSpecializationD;
    @FXML
    private TableColumn<Doctor, String> columnTableNameD;
    @FXML
    private TableColumn<Doctor, String> columnTableSurnameD;
    @FXML
    private TableColumn<Doctor, String> columnTablePhoneD;
    @FXML
    private TableColumn<Doctor, String> columnTableNrRoomD;

    @FXML
    private void findInciationD(ActionEvent event) {
        tableListD.getItems().clear();
        String sumText = textFindNameD.getText().toLowerCase() + textFindSurnameD.getText().toLowerCase();
        //Sprawdzanie czy jest taki Lekarz w bazie i uzupelnianie tabeli
        if (dbConn.getDoctorByName(textFindNameD.getText(), textFindSurnameD.getText()) != null && isLetterOnly(sumText) == true) {
            List<Doctor> doctors = dbConn.getDoctorByName(textFindNameD.getText(), textFindSurnameD.getText());

            for (Doctor doctorTab : doctors) {
                tableListD.getItems().add(new Doctor(doctorTab.getID(), doctorTab.getImie(), doctorTab.getNazwisko(), doctorTab.getSpec(), doctorTab.getEmail(), doctorTab.getRoom()));
            }

            columnTableNameD.setCellValueFactory(new PropertyValueFactory<>("Imie"));
            columnTableSurnameD.setCellValueFactory(new PropertyValueFactory<>("Nazwisko"));
            columnTableSpecializationD.setCellValueFactory(new PropertyValueFactory<>("Spec"));
            columnTableNrRoomD.setCellValueFactory(new PropertyValueFactory<>("Room"));
            columnTablePhoneD.setCellValueFactory(new PropertyValueFactory<>("Email"));
        } else {
            alert.setTitle("Uwaga!");
            alert.setHeaderText("Błąd w polu imie lub nazwisko");
            alert.setContentText("Podane imie lub naziwsko zawiera błąd lub nie ma go w bazie");
            alert.showAndWait();
        }
    }

    @FXML
    private void findSpecializationD(ActionEvent event) {
        tableListD.getItems().clear();

        String sumText = textFindSpecializationD.getText().toLowerCase();

        if (dbConn.getDoctorBySpec(textFindSpecializationD.getText()) != null && isLetterOnly(sumText) == true) {
            List<Doctor> doctors = dbConn.getDoctorBySpec(textFindSpecializationD.getText());

            for (Doctor doctorTab : doctors) {
                tableListD.getItems().add(new Doctor(doctorTab.getID(), doctorTab.getImie(), doctorTab.getNazwisko(), doctorTab.getSpec(), doctorTab.getEmail(), doctorTab.getRoom()));
            }

            columnTableNameD.setCellValueFactory(new PropertyValueFactory<>("Imie"));
            columnTableSurnameD.setCellValueFactory(new PropertyValueFactory<>("Nazwisko"));
            columnTableSpecializationD.setCellValueFactory(new PropertyValueFactory<>("Spec"));
            columnTableNrRoomD.setCellValueFactory(new PropertyValueFactory<>("Room"));
            columnTablePhoneD.setCellValueFactory(new PropertyValueFactory<>("Email"));
        } else {
            alert.setTitle("Uwaga!");
            alert.setHeaderText("Błąd w polu Specjalizacja");
            alert.setContentText("Podana specjalizacja jest błędna lub nie ma jej bazie");
            alert.showAndWait();
        }

    }

    @FXML
    private void findNrRoomD(ActionEvent event) {
        tableListD.getItems().clear();

        String sumText = textFindNrRoomD.getText().toLowerCase();
        System.out.println(isNumbersOnly(sumText));
        if (dbConn.getDoctorByRoom(textFindNrRoomD.getText()) != null && isNumbersOnly(sumText) == true) {
            List<Doctor> doctors = dbConn.getDoctorByRoom(textFindNrRoomD.getText());

            for (Doctor doctorTab : doctors) {
                tableListD.getItems().add(new Doctor(doctorTab.getID(), doctorTab.getImie(), doctorTab.getNazwisko(), doctorTab.getSpec(), doctorTab.getEmail(), doctorTab.getRoom()));
            }

            columnTableNameD.setCellValueFactory(new PropertyValueFactory<>("Imie"));
            columnTableSurnameD.setCellValueFactory(new PropertyValueFactory<>("Nazwisko"));
            columnTableSpecializationD.setCellValueFactory(new PropertyValueFactory<>("Spec"));
            columnTableNrRoomD.setCellValueFactory(new PropertyValueFactory<>("Room"));
            columnTablePhoneD.setCellValueFactory(new PropertyValueFactory<>("Email"));
        } else {
            alert.setTitle("Uwaga!");
            alert.setHeaderText("Błąd w polu Specjalizacja");
            alert.setContentText("Podana specjalizacja jest błędna lub nie ma jej bazie");
            alert.showAndWait();
        }

    }

    //LIST VISIT
    //DATA VISIT CHANGE
    //METHODS
    //Sprawdzanie czy są same litery
    private boolean isLetterOnly(String text1) {
        boolean isLetter = false;
        text1.toLowerCase();
        char[] LetterOnly = text1.toCharArray();
        for (int i = 0; i < LetterOnly.length; i++) {
            if (LetterOnly[i] >= 'a' && LetterOnly[i] <= 'z') {
                isLetter = true;
            } else {
                isLetter = false;
                break;
            }
        }
        return isLetter;
    }

    //Sprawdzanie czy są same cyfy
    private boolean isNumbersOnly(String text1) {
        boolean isNumber = false;
        char[] NumbersOnly = text1.toCharArray();
        for (int i = 0; i < NumbersOnly.length; i++) {
            if (NumbersOnly[i] >= '0' && NumbersOnly[i] <= '9') {
                isNumber = true;
            } else {
                isNumber = false;
                break;
            }
        }
        return isNumber;
    }

}
