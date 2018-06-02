package clinic;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegisterWindowController implements Initializable {

    private Alert alert = new Alert(Alert.AlertType.WARNING);
    private DatabaseConnection dbConn;

    //cj.
    //?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
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
        tableListP.setPlaceholder(new Label("Brak wynikow"));
        tableListD.setPlaceholder(new Label("Brak wynikow"));
        //tableListD.setPlaceholder(new Label("Brak wynikow"));
        dbConn.showList(dbConn.getDatesList());
    }

    //    REGISTER
    @FXML
    private TextField textNameRP;

    @FXML
    private TextField textSurnameRP;

    @FXML
    private TextField textPeselRP;

    @FXML
    private TextField textEmailRP;

    @FXML
    private void registerButtonP(ActionEvent event) {
        if (textNameRP.getLength() <= 3) {
            System.out.println("Imie jest za któtkie");
            alert.setTitle("Bląd");
            alert.setContentText("Imie jest za krótkie");
            alert.showAndWait();
        } else {
            System.out.println(textNameRP.getText());
            System.out.println(textSurnameRP.getText());
            System.out.println(textPeselRP.getText());
            System.out.println(textEmailRP.getText());
        }
    }

    @FXML
    private void registerButtonD(ActionEvent event) {
        if (textNameRP.getLength() <= 3) {
            System.out.println("Imie jest za któtkie");
            alert.setTitle("Bląd");
            alert.setContentText("Imie jest za krótkie");
            alert.showAndWait();
        } else {
            System.out.println(textNameRP.getText());
            System.out.println(textSurnameRP.getText());
            System.out.println(textPeselRP.getText());
            System.out.println(textEmailRP.getText());
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
        if (textFindPeselP.getLength() == 11 && dbConn.getPatientByPESEL(textFindPeselP.getText()) != null && isNumbersOnly(sumString)) {
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
            tableListP.setPlaceholder(new Label("Brak wynikow"));
        }

    }

    @FXML
    private void findInicialP(ActionEvent event) {
        tableListP.getItems().clear();

        String sumString = textFindNameP.getText().toLowerCase() + textFindSurnameP.getText().toLowerCase();

        if (dbConn.getPatientByName(textFindNameP.getText(), textFindSurnameP.getText()) != null && isLetterOnly(sumString) == true) {
            listForPatient(dbConn.getPatientByName(textFindNameP.getText(), textFindSurnameP.getText()));
        } else {
            alert.setTitle("Uwaga!");
            alert.setHeaderText("Błąd w polu imie lub nazwisko");
            alert.setContentText("Podane imie lub naziwsko zawiera błąd lub nie ma go w bazie");
            alert.showAndWait();
            tableListP.setPlaceholder(new Label("Brak wynikow"));
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
    private TableColumn<Doctor, String> columnTableIDD;
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
        if (dbConn.getDoctorByName(textFindNameD.getText(), textFindSurnameD.getText()) != null && isLetterOnly(sumText)) {
            listForDoctor(dbConn.getDoctorByName(textFindNameD.getText(), textFindSurnameD.getText()));
        } else {
            alert.setTitle("Uwaga!");
            alert.setHeaderText("Błąd w polu imie lub nazwisko");
            alert.setContentText("Podane imie lub naziwsko zawiera błąd lub nie ma go w bazie");
            alert.showAndWait();
            tableListD.setPlaceholder(new Label("Brak wynikow"));
        }
    }

    @FXML
    private void findSpecializationD(ActionEvent event) {
        tableListD.getItems().clear();

        String sumText = textFindSpecializationD.getText().toLowerCase();

        if (dbConn.getDoctorBySpec(textFindSpecializationD.getText()) != null && isLetterOnly(sumText)) {
            listForDoctor(dbConn.getDoctorBySpec(textFindSpecializationD.getText()));
        } else {
            alert.setTitle("Uwaga!");
            alert.setHeaderText("Błąd w polu Specjalizacja");
            alert.setContentText("Podana specjalizacja jest błędna lub nie ma jej bazie");
            alert.showAndWait();
            tableListD.setPlaceholder(new Label("Brak wynikow"));
        }

    }

    @FXML
    private void findNrRoomD(ActionEvent event) {
        tableListD.getItems().clear();

        String sumText = textFindNrRoomD.getText().toLowerCase();
        System.out.println(isNumbersOnly(sumText));
        if (dbConn.getDoctorByRoom(textFindNrRoomD.getText()) != null && isNumbersOnly(sumText)) {
            listForDoctor(dbConn.getDoctorByRoom(textFindNrRoomD.getText()));
        } else {
            alert.setTitle("Uwaga!");
            alert.setHeaderText("Błąd w polu Specjalizacja");
            alert.setContentText("Podana specjalizacja jest błędna lub nie ma jej bazie");
            alert.showAndWait();
            tableListD.setPlaceholder(new Label("Brak wynikow"));
        }

    }

    //DATA VISIT CHANGE
    @FXML
    private Label informationP;
    @FXML
    private Label informationD;
    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView<String> tableHoursAndMinutes;
    @FXML
    private TableColumn<String, String> columnTableHours;

    @FXML
    private void testL() {
        if (!tableListP.getSelectionModel().isEmpty())
            informationP.setText(tableListP.getSelectionModel().getSelectedItem().getImie() + " " + tableListP.getSelectionModel().getSelectedItem().getNazwisko());
        if (!tableListD.getSelectionModel().isEmpty())
            informationD.setText(tableListD.getSelectionModel().getSelectedItem().getImie() + " " + tableListD.getSelectionModel().getSelectedItem().getNazwisko());
    }

    @FXML
    private void choseData(ActionEvent event) {
        tableHoursAndMinutes.getItems().clear();
        if (!tableListD.getSelectionModel().isEmpty()) {
            //Doctor doctor = new Doctor(dbConn.getDoctorByID(tableListD.getSelectionModel().getSelectedItem().getID()));
            //Date date = new Date(datePicker.getValue().toString(), doctor);

            if (true/*/dbConn.getDateByDay(datePicker.getValue().toString()) != null*/) {
                List<String> listTest = dbConn.getFreeHoursFromDate(dbConn.getDateByDay(datePicker.getValue().toString()));
                // List<String> list = dbConn.getBusyHoursFromDate(dbConn.getDateByDay(date.getDay()));
                // List<String> list2 = dbConn.getFreeHoursFromDate(dbConn.getDateByDay(date.getDay()));
                for (String listS : listTest) {
                    System.out.println(listS);

                }
                listForTime(listTest);
            }
        }

    }


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

    //Lista dla pacjentow
    private void listForPatient(List<Patient> list) {
        for (Patient patientTab : list) {
            tableListP.getItems().add(new Patient(patientTab.getID(), patientTab.getPesel(), patientTab.getImie(), patientTab.getNazwisko(), patientTab.getEmail()));
        }

        columnTablePeselP.setCellValueFactory(new PropertyValueFactory<>("Pesel"));
        columnTableNameP.setCellValueFactory(new PropertyValueFactory<>("Imie"));
        columnTableSurnameP.setCellValueFactory(new PropertyValueFactory<>("Nazwisko"));
        columnTableEmailP.setCellValueFactory(new PropertyValueFactory<>("Email"));
    }

    //Lista dla lekarzy
    private void listForDoctor(List<Doctor> list) {
        for (Doctor doctorTab : list) {
            tableListD.getItems().add(new Doctor(doctorTab.getID(), doctorTab.getImie(), doctorTab.getNazwisko(), doctorTab.getSpec(), doctorTab.getEmail(), doctorTab.getRoom()));
        }

        columnTableIDD.setCellValueFactory(new PropertyValueFactory<>("ID"));
        columnTableNameD.setCellValueFactory(new PropertyValueFactory<>("Imie"));
        columnTableSurnameD.setCellValueFactory(new PropertyValueFactory<>("Nazwisko"));
        columnTableSpecializationD.setCellValueFactory(new PropertyValueFactory<>("Spec"));
        columnTableNrRoomD.setCellValueFactory(new PropertyValueFactory<>("Room"));
        columnTablePhoneD.setCellValueFactory(new PropertyValueFactory<>("Email"));
    }

    private void listForTime(List<String> list) {

        for (String dateList : list) {
            tableHoursAndMinutes.getItems().add(dateList);
        }
        columnTableHours.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));

    }

}
