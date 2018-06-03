package clinic;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterWindowController implements Initializable {

    private Alert alert = new Alert(Alert.AlertType.WARNING);
    private DatabaseConnection dbConn;

    //cj.
    //?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            dbConn = new DatabaseConnection("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/klinika?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");

            System.out.println("polaczono");
            dbConn.setDoctorsList();
            dbConn.setPatientsList();
            dbConn.setVisitsList();
            dbConn.updateCopyLists();
            dbConn.setDatePicker(datePicker);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(RegisterWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tableListP.setPlaceholder(new Label("Brak wynikow"));
        tableListD.setPlaceholder(new Label("Brak wynikow"));
        tableListVisit.setPlaceholder(new Label("Brak wynikow"));
        tableHoursAndMinutes.setPlaceholder(new Label("Brak wynikow"));
        dbConn.showList(dbConn.getPatientsList());
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
    //Lekarz
    @FXML
    private TextField textNameRD;

    @FXML
    private TextField textSurnameRD;

    @FXML
    private TextField textSpecRD;

    @FXML
    private TextField textEmailRD;

    @FXML
    private TextField textRoomRD;
    @FXML
    private TextField textPasswordRD;

    @FXML
    private void registerButtonP(ActionEvent event) {
        if (isLetterOnly(textNameRP.getText())) {
            if (isLetterOnly(textSurnameRP.getText())) {
                if (isNumbersOnly(textPeselRP.getText()) && textPeselRP.getLength() == 11) {
                    if (isEmail(textEmailRP.getText())) {
                        dbConn.registerPatient(textPeselRP.getText(), textNameRP.getText(), textSurnameRP.getText(), textEmailRP.getText());
                        //dbConn.comparePatients();
                    } else {
                        alert.setTitle("Uwaga!");
                        alert.setHeaderText("Pole email");
                        alert.setContentText("E-mail jest błędny");
                        alert.showAndWait();
                    }
                } else {
                    alert.setTitle("Uwaga!");
                    alert.setHeaderText("Pole pesel");
                    alert.setContentText("Pesel jest błędny prosze o podanie prawidłowego peselu");
                    alert.showAndWait();
                }
            } else {
                alert.setTitle("Uwaga!");
                alert.setHeaderText("Pole nazwisko");
                alert.setContentText("W polu nazwisko można używac tylko liter");
                alert.showAndWait();
            }
        } else {
            alert.setTitle("Uwaga!");
            alert.setHeaderText("Pole imie");
            alert.setContentText("W polu imie można używac tylko liter");
            alert.showAndWait();
        }
    }

    @FXML
    private void registerButtonD(ActionEvent event) {
        if (isLetterOnly(textNameRD.getText())) {
            if (isLetterOnly(textSurnameRD.getText())) {
                if (isLetterOnly(textSpecRD.getText())) {
                    if (isEmail(textEmailRD.getText())) {
                        if (isNumbersOnly(textRoomRD.getText())) {
                            if (textPasswordRD.getLength() >= 7) {
                                dbConn.registerDoctor(textNameRD.getText(), textSurnameRD.getText(), textPasswordRD.getText(), textSpecRD.getText(), textEmailRD.getText(), textRoomRD.getText());
                                //dbConn.compareDoctors();
                            } else {
                                alert.setTitle("Uwaga!");
                                alert.setHeaderText("Pole Hasło");
                                alert.setContentText("Hasło jest za któtkie");
                                alert.showAndWait();
                            }
                        } else {
                            alert.setTitle("Uwaga!");
                            alert.setHeaderText("Pole nr sali");
                            alert.setContentText("W polu nr sali prosze użyc tylko liter");
                            alert.showAndWait();
                        }

                    } else {
                        alert.setTitle("Uwaga!");
                        alert.setHeaderText("Pole email");
                        alert.setContentText("E-mail jest błędny");
                        alert.showAndWait();
                    }
                } else {
                    alert.setTitle("Uwaga!");
                    alert.setHeaderText("Pole Specjalizaca");
                    alert.setContentText("W polu specjalizacja można używac tylko liter");
                    alert.showAndWait();
                }
            } else {
                alert.setTitle("Uwaga!");
                alert.setHeaderText("Pole nazwisko");
                alert.setContentText("W polu nazwisko można używac tylko liter");
                alert.showAndWait();
            }
        } else {
            alert.setTitle("Uwaga!");
            alert.setHeaderText("Pole imie");
            alert.setContentText("W polu imie można używac tylko liter");
            alert.showAndWait();
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

    //VISIT
    @FXML
    private Label informationP;
    @FXML
    private Label informationD;
    @FXML
    private DatePicker datePicker;

    //Godziny
    @FXML
    private TableView<String> tableHoursAndMinutes;
    @FXML
    private TableColumn<String, String> columnTableHours;

    //Wizyty table
    @FXML
    private TableView<Visit> tableListVisit;
    @FXML
    private TableColumn<Visit, String> columnVisitLp;
    @FXML
    private TableColumn<Visit, String> columnVisitName;
    @FXML
    private TableColumn<Visit, String> columnVisitSurname;
    @FXML
    private TableColumn<Visit, String> columnVisitDate;

    //Wypisanie wszystkich wizyt pacjenta
    @FXML
    private void visitListPatient() {

        listForPatient();
        if (!tableListD.getSelectionModel().isEmpty())
            informationD.setText(tableListD.getSelectionModel().getSelectedItem().getImie() + " " + tableListD.getSelectionModel().getSelectedItem().getNazwisko() + " " + tableListD.getSelectionModel().getSelectedItem().getSpec());
    }

    //Wbyieranie daty
    @FXML
    private void choseData(ActionEvent event) {
        doListForTime();
    }

    //Dodanie wizyty
    @FXML
    private void AddVisit(ActionEvent event) {
        String str = datePicker.getValue().toString() + " " + tableHoursAndMinutes.getSelectionModel().getSelectedItem();
        if (!tableHoursAndMinutes.getSelectionModel().getSelectedItem().isEmpty() && !tableListD.getSelectionModel().isEmpty() && !tableListP.getSelectionModel().isEmpty()) {
            dbConn.createVisit(dbConn.getDoctorByID(tableListD.getSelectionModel().getSelectedItem().getID()), dbConn.getPatientByPESEL(tableListP.getSelectionModel().getSelectedItem().getPesel()), str);
            doListForTime();
            listForPatient();
            dbConn.compareLists();
        } else {
            alert.setTitle("Uwaga!");
            alert.setHeaderText("Nie dokonano wyboru");
            alert.setContentText("Prosze o sprawdzenie czy napewno został wybrany lekarz bądż pacjent");
            alert.showAndWait();
        }


    }

    //Zmiana terminu wizyty
    @FXML
    private void changeDateVisit(ActionEvent event) {
        String str = datePicker.getValue().toString() + " " + tableHoursAndMinutes.getSelectionModel().getSelectedItem();
        if (!tableHoursAndMinutes.getSelectionModel().getSelectedItem().isEmpty() && !tableListD.getSelectionModel().isEmpty() && !tableListP.getSelectionModel().isEmpty()) {
            dbConn.changeVisitDate(dbConn.getVisitByID(tableListVisit.getSelectionModel().getSelectedItem().getID()), str);
            doListForTime();
            listForPatient();
            dbConn.compareLists();
        } else {
            alert.setTitle("Uwaga!");
            alert.setHeaderText("Nie dokonano wyboru");
            alert.setContentText("Prosze o sprawdzenie czy napewno został wybrany lekarz bądż pacjent");
            alert.showAndWait();
        }
    }

    //METHODS
    //Sprawdzanie czy są same litery
    private boolean isLetterOnly(String text1) {
        boolean isLetter = false;
        char[] LetterOnly = text1.toLowerCase().toCharArray();
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

    //Walidacja e-mail
    private boolean isEmail(String text) {
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher m = p.matcher(text);

        boolean matchFound = m.matches();
        return matchFound;
    }

    //Lista dla pacjentow do uzycia
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

    //Lista dotyczaca Godzin
    private void listForTime(List<String> list) {

        for (String dateList : list) {
            tableHoursAndMinutes.getItems().add(dateList);
        }
        columnTableHours.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
    }

    //Metoda która wykonuje listForTime z odpowienimi parametrami i w odpowiedni sposób
    private void doListForTime() {

        tableHoursAndMinutes.getItems().clear();
        columnTableHours.setStyle("-fx-alignment: CENTER;");
        if (!tableListD.getSelectionModel().isEmpty() && datePicker.getValue().compareTo(LocalDate.now()) >= 0) {
            if (dbConn.getDateByDay(datePicker.getValue().toString(), dbConn.getDoctorByID(tableListD.getSelectionModel().getSelectedItem().getID())) == null) {
                List<String> listTest = dbConn.getDatesList().get(1).getHours();
                listForTime(listTest);
            } else {
                System.out.println(dbConn.getDoctorByID(tableListD.getSelectionModel().getSelectedItem().getID()).getID());
                List<String> listTest = dbConn.getFreeHoursFromDate(dbConn.getDateByDay(datePicker.getValue().toString(), dbConn.getDoctorByID(tableListD.getSelectionModel().getSelectedItem().getID())));
                listForTime(listTest);
            }
        }
    }

    //Metoda w której uzywamy listForPatient w odpowiedni sposób
    private void listForPatient() {
        tableListVisit.getItems().clear();
        if (!tableListP.getSelectionModel().isEmpty()) {
            informationP.setText(tableListP.getSelectionModel().getSelectedItem().getImie() + " " + tableListP.getSelectionModel().getSelectedItem().getNazwisko());
            // List<Visit> list2 = dbConn.getVisitByPatient(dbConn.getPatientByPESEL("84112300687"));
            List<Visit> list = dbConn.getVisitByPatient(dbConn.getPatientByPESEL(tableListP.getSelectionModel().getSelectedItem().getPesel()));

            if (!list.isEmpty()) {

                int i = 1;
                for (Visit visit : list) {
                    if (visit.getID_Doc().equals(tableListD.getSelectionModel().getSelectedItem().getID())) {
                        tableListVisit.getItems().add(new Visit(visit.getID(), Integer.toString(i), dbConn.getPatientByPESEL(visit.getPesel_Pat()).getImie(), dbConn.getPatientByPESEL(visit.getPesel_Pat()).getNazwisko(), visit.getDate(), visit.getStatus()));
                        i++;
                    }

                }
                columnVisitLp.setCellValueFactory(new PropertyValueFactory<>("Ilosc"));
                columnVisitName.setCellValueFactory(new PropertyValueFactory<>("Imie"));
                columnVisitDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
                columnVisitSurname.setCellValueFactory(new PropertyValueFactory<>("Nazwisko"));

            } else {
                tableListVisit.setPlaceholder(new Label("Brak wyników z dla " + tableListP.getSelectionModel().getSelectedItem().getImie() + " " + tableListP.getSelectionModel().getSelectedItem().getNazwisko()));
            }


        }
    }
}
