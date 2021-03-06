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
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterWindowController implements Initializable {

    private Alert alert = new Alert(Alert.AlertType.WARNING);
    private Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
    private DatabaseConnection dbConn;
    private List<String> listOfSpecDoctor = new ArrayList<>();

    //cj.
    //?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            dbConn = new DatabaseConnection("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/klinika?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");

            dbConn.setDoctorsList();
            dbConn.setPatientsList();
            dbConn.setVisitsList();
            dbConn.updateCopyLists();
            dbConn.setDatePicker(datePicker);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(RegisterWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tableListVisit.setPlaceholder(new Label("Brak wynikow"));
        tableHoursAndMinutes.setPlaceholder(new Label("Brak wynikow"));
        valueListSpec();
        comboBoxValue();
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
    private ComboBox<String> comboBoxSpecDR;

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
                        alert2.setTitle("Informacja");
                        alert2.setHeaderText("Zarejestrowano użytkownika pomyślnie");
                        alert2.setContentText("Wcisnij OK, aby kontynuować");
                        alert2.showAndWait();
                        textNameRP.setText("");
                        textSurnameRP.setText("");
                        textPeselRP.setText("");
                        textEmailRP.setText("");
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
                if (!comboBoxSpecDR.getSelectionModel().isEmpty()) {
                    if (isEmail(textEmailRD.getText())) {
                        if (isNumbersOnly(textRoomRD.getText())) {
                            if (textPasswordRD.getLength() >= 7) {
                                dbConn.registerDoctor(textNameRD.getText(), textSurnameRD.getText(), textPasswordRD.getText(), comboBoxSpecDF.getSelectionModel().getSelectedItem(), textEmailRD.getText(), textRoomRD.getText());
                                alert2.setTitle("Informacja");
                                alert2.setHeaderText("Zarejestrowano lekarza pomyślnie");
                                alert2.setContentText("Wcisnij OK, aby kontynuować");
                                alert2.showAndWait();
                                textNameRD.setText("");
                                textSurnameRD.setText("");
                                textPasswordRD.setText("");
                                comboBoxSpecDF.getSelectionModel().clearSelection();
                                textRoomRD.setText("");
                                textEmailRD.setText("");
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
                    alert.setContentText("Prosze wybrac specjalizacje");
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
    private void findButton(ActionEvent event) {
        tableListP.getItems().clear();
        String sumLetter = textFindNameP.getText() + textFindSurnameP.getText();
        if (isNumbersOnly(textFindPeselP.getText()) || textFindPeselP.getText().isEmpty()) {
            if (isLetterOnly(sumLetter) || sumLetter.isEmpty()) {
                findPacient(dbConn.getPatientByPESELList(textFindPeselP.getText()), dbConn.getPatientByName(textFindNameP.getText(), textFindSurnameP.getText()));
            } else {
                alert.setTitle("Uwaga!");
                alert.setHeaderText("Błąd w polu imie lub nazwisko");
                alert.setContentText("Podane imie lub naziwsko zawiera błąd lub nie ma go w bazie");
                alert.showAndWait();
                tableListP.setPlaceholder(new Label("Brak wynikow"));
            }
        } else {
            alert.setTitle("Nieprawidłowa długość peselu");
            alert.setHeaderText("Błąd w polu pesel");
            alert.setContentText("Podany pesel jest błędny lub nie ma takiego pacjenta w bazie");
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
    ComboBox<String> comboBoxSpecDF;
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
    private void findButtonDoctor(ActionEvent event) {
        tableListD.getItems().clear();
        String sumText = textFindNameD.getText().toLowerCase() + textFindSurnameD.getText().toLowerCase();
        if (isNumbersOnly(textFindNrRoomD.getText()) || textFindNrRoomD.getText().isEmpty()) {
            if (isLetterOnly(sumText) || sumText.isEmpty()) {
                if (!comboBoxSpecDF.getSelectionModel().isEmpty()) {
                    findDoctor(dbConn.getDoctorByName(textFindNameD.getText(), textFindSurnameD.getText()),
                            dbConn.getDoctorBySpec(comboBoxSpecDF.getSelectionModel().getSelectedItem()), dbConn.getDoctorByRoom(textFindNrRoomD.getText()));
                } else {
                    findDoctor(dbConn.getDoctorByName(textFindNameD.getText(), textFindSurnameD.getText()),
                            dbConn.getDoctorBySpec(" "), dbConn.getDoctorByRoom(textFindNrRoomD.getText()));
                }
            } else {
                alert.setTitle("Uwaga!");
                alert.setHeaderText("Błąd w polu nr pokoju");
                alert.setContentText("Podana nr jest błedny");
                alert.showAndWait();
                tableListD.setPlaceholder(new Label("Brak wynikow"));
            }
        } else {
            alert.setTitle("Uwaga!");
            alert.setHeaderText("Błąd w polu imie lub nazwisko");
            alert.setContentText("Podane imie lub naziwsko zawiera błąd");
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

        listForPatientVisit();
        if (!tableListD.getSelectionModel().isEmpty()) {
            informationD.setText(tableListD.getSelectionModel().getSelectedItem().getImie() + " " + tableListD.getSelectionModel().getSelectedItem().getNazwisko() + " " + tableListD.getSelectionModel().getSelectedItem().getSpec());
        } else {
            informationD.setText(" ");
        }
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
            listForPatientVisit();

            dbConn.compareLists();
            alert2.setTitle("Informacja");
            alert2.setHeaderText("Dodano wizytę pomyślnie");
            alert2.setContentText("Wcisnij OK, aby kontynuować");
            alert2.showAndWait();
            datePicker.setValue(LocalDate.now());
            tableHoursAndMinutes.getSelectionModel().clearSelection();
            
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
            listForPatientVisit();
            dbConn.compareLists();
            alert2.setTitle("Informacja");
            alert2.setHeaderText("Zmieniono wizytę pomyślnie");
            alert2.setContentText("Wcisnij OK, aby kontynuować");
            alert2.showAndWait();
            datePicker.setValue(LocalDate.now());
            tableHoursAndMinutes.getSelectionModel().clearSelection();
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
        if (!list.isEmpty()) {
            for (Patient patientTab : list) {
                tableListP.getItems().add(new Patient(patientTab.getID(), patientTab.getPesel(), patientTab.getImie(), patientTab.getNazwisko(), patientTab.getEmail()));
            }

            columnTablePeselP.setCellValueFactory(new PropertyValueFactory<>("Pesel"));
            columnTableNameP.setCellValueFactory(new PropertyValueFactory<>("Imie"));
            columnTableSurnameP.setCellValueFactory(new PropertyValueFactory<>("Nazwisko"));
            columnTableEmailP.setCellValueFactory(new PropertyValueFactory<>("Email"));
        } else {
            tableListP.setPlaceholder(new Label("Brak wyników"));
        }
    }

    //Lista dla lekarzy
    private void listForDoctor(List<Doctor> list) {
        if (!list.isEmpty()) {
            for (Doctor doctorTab : list) {
                tableListD.getItems().add(new Doctor(doctorTab.getID(), doctorTab.getImie(), doctorTab.getNazwisko(), doctorTab.getSpec(), doctorTab.getEmail(), doctorTab.getRoom()));
            }

            columnTableIDD.setCellValueFactory(new PropertyValueFactory<>("ID"));
            columnTableNameD.setCellValueFactory(new PropertyValueFactory<>("Imie"));
            columnTableSurnameD.setCellValueFactory(new PropertyValueFactory<>("Nazwisko"));
            columnTableSpecializationD.setCellValueFactory(new PropertyValueFactory<>("Spec"));
            columnTableNrRoomD.setCellValueFactory(new PropertyValueFactory<>("Room"));
            columnTablePhoneD.setCellValueFactory(new PropertyValueFactory<>("Email"));
        } else {
            tableListD.setPlaceholder(new Label("Brak wyników"));
        }
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
    private void listForPatientVisit() {
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

        }else {
            informationP.setText("");
        }
    }

    @FXML
    private void listAllPatients() {
        if (tableListP.getItems().isEmpty()) {
            listForPatient(dbConn.getPatientsList());
        }
    }

    @FXML
    private void listAllDoctors() {
        if (tableListD.getItems().isEmpty()) {
            listForDoctor(dbConn.getDoctorsList());
        }
    }

    private void findPacient(List<Patient> listPatientPesel, List<Patient> listPatientName) {
        List<Patient> listPatient = new ArrayList<>();
        Set<Patient> listPatientCollection = new HashSet<>();

        listPatientCollection.addAll(listPatientPesel);
        listPatientCollection.addAll(listPatientName);
        for (Patient list : listPatientCollection) {
            if (list.getPesel().contains(textFindPeselP.getText()) && list.getImie().contains(textFindNameP.getText()) && list.getNazwisko().contains(textFindSurnameP.getText())) {
                listPatient.add(list);
            }
        }
        listForPatient(listPatient);

    }

    private void findDoctor(List<Doctor> listDoctorName, List<Doctor> listDoctorSpec, List<Doctor> listDoctorRoom) {
        List<Doctor> listDoctor = new ArrayList<>();
        Set<Doctor> listDoctorCollection = new HashSet<>();

        listDoctorCollection.addAll(listDoctorName);
        listDoctorCollection.addAll(listDoctorSpec);
        listDoctorCollection.addAll(listDoctorRoom);
        for (Doctor list : listDoctorCollection) {
            if (!comboBoxSpecDF.getSelectionModel().isEmpty()) {
                if (list.getSpec().contains(comboBoxSpecDF.getSelectionModel().getSelectedItem()) && list.getImie().toLowerCase().contains(textFindNameD.getText().toLowerCase())
                        && list.getNazwisko().toLowerCase().contains(textFindSurnameD.getText().toLowerCase()) && list.getRoom().contains(textFindNrRoomD.getText())) {
                    listDoctor.add(list);
                }
            } else {
                if (list.getImie().toLowerCase().contains(textFindNameD.getText().toLowerCase())
                        && list.getNazwisko().toLowerCase().contains(textFindSurnameD.getText().toLowerCase()) && list.getRoom().contains(textFindNrRoomD.getText())) {
                    listDoctor.add(list);
                }
            }
        }
        listForDoctor(listDoctor);
    }

    private void valueListSpec() {
        listOfSpecDoctor.add("Okulista");
        listOfSpecDoctor.add("Ginekolog");
        listOfSpecDoctor.add("Dentysta");
        listOfSpecDoctor.add("Ortopeda");
        listOfSpecDoctor.add("Dermatolog");
    }

    private void comboBoxValue() {
        for (String list : listOfSpecDoctor) {
            comboBoxSpecDF.getItems().add(list);
            comboBoxSpecDR.getItems().add(list);
        }

    }
}
