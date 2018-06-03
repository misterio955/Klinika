package clinic;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DoctrorWindowController implements Initializable {
    private DatabaseConnection dbConn;
    private Alert alert = new Alert(Alert.AlertType.WARNING);
    private boolean cantRefresh = false;
    private static int currentIdDoctor;

    //cj
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
        defaultDate();
        System.out.println(currentIdDoctor);

    }

    //TABLE_VIEW
    @FXML
    private TableView<Visit> tableListP;

    @FXML
    private TableColumn<Visit, String> columnTableLp;

    @FXML
    private TableColumn<Visit, String> columnTableNameP;

    @FXML
    private TableColumn<Visit, String> columnTableSurnameP;

    @FXML
    private TableColumn<Visit, String> columnTableStatusP;

    //Button odśwież
    @FXML
    private void refresh(ActionEvent event) {
        tableListP.getItems().clear();

        if (cantRefresh == true) {
            showTableList(dbConn.getVisitByDate(datePicker.getValue().toString()));
        }
    }


    //Button Wizyta zakończona
    @FXML
    private void visitIsEnd(ActionEvent event) {
        if (!tableListP.getSelectionModel().isEmpty()) {
            List<Visit> list = dbConn.getVisitsList();
            for (Visit visit : list) {
                if (tableListP.getSelectionModel().getSelectedItem().getID() == visit.getID()) {
                    visit.setStatus("Zakonczona");
                    dbConn.compareLists();
                }
            }
        }

    }

    //Button Wizyty nie odbyła się
    @FXML
    private void visitDidNotTakePlace(ActionEvent event) {
        if (!tableListP.getSelectionModel().isEmpty()) {
            List<Visit> list = dbConn.getVisitsList();
            for (Visit visit : list) {
                if (tableListP.getSelectionModel().getSelectedItem().getID() == visit.getID()) {
                    visit.setStatus("Nie odbyła sie");
                    dbConn.compareLists();
                }
            }
        }
    }

    //Date picker
    @FXML
    DatePicker datePicker = new DatePicker();


    @FXML
    private void choseDateVisit(ActionEvent event) {
        tableListP.getItems().clear();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = datePicker.getValue();
        List<Visit> list = dbConn.getVisitByDate(formatter.format(date));
        List<Visit> listByDoctor = new ArrayList<Visit>();
        for (Visit visit: list){
            if(visit.getID_Doc().equals(Integer.toString(currentIdDoctor))) {
                listByDoctor.add(visit);
            }
        }
        if (date.compareTo(LocalDate.now()) >= 0) {
            showTableList(listByDoctor);
        } else {
            tableListP.setPlaceholder(new Label("Nie można wyszukać wcześniejszych wizyt"));
            alert.setTitle("Uwaga!");
            alert.setHeaderText("Zła data");
            alert.setContentText("Nie można wybrać daty wstecz");
            alert.showAndWait();
            cantRefresh = false;
        }
    }

    //Default value
    private void defaultDate() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<Visit> list = dbConn.getVisitByDate(LocalDate.now().toString());
        List<Visit> listByDoctor = new ArrayList<Visit>();

        for (Visit visit: list){
            if(visit.getID_Doc().equals(Integer.toString(currentIdDoctor)))
            listByDoctor.add(visit);
        }

        dbConn.showList(listByDoctor);
        if (!listByDoctor.isEmpty()) {
            showTableList(listByDoctor);
        } else {
            tableListP.setPlaceholder(new Label("Nie ma wizyt w dniu dzisiejszym"));
        }
    }

    //Method
    private void showTableList(List<Visit> list) {
        if (!list.isEmpty()) {
            int i = 1;
            for (Visit visit : list) {
                if (visit.getID_Doc().equals(Integer.toString(currentIdDoctor))) {
                    tableListP.getItems().add(new Visit(visit.getID(), Integer.toString(i), dbConn.getPatientByPESEL(visit.getPesel_Pat()).getImie(), dbConn.getPatientByPESEL(visit.getPesel_Pat()).getNazwisko(), visit.getDate(), visit.getStatus()));
                    i++;
                }
            }
            columnTableLp.setCellValueFactory(new PropertyValueFactory<>("Ilosc"));
            columnTableNameP.setCellValueFactory(new PropertyValueFactory<>("Imie"));
            columnTableSurnameP.setCellValueFactory(new PropertyValueFactory<>("Nazwisko"));
            columnTableStatusP.setCellValueFactory(new PropertyValueFactory<>("Status"));
            cantRefresh = true;
        } else {
            tableListP.setPlaceholder(new Label("Brak wyników z dnia " + datePicker.getValue().toString()));
            cantRefresh = false;
        }
    }

    public static void setIDDoctor(int Id) {
        currentIdDoctor = Id;

    }
}
