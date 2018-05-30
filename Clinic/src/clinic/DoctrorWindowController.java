package clinic;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DoctrorWindowController implements Initializable {
    private DatabaseConnection dbConn;
    //cj
    //?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            dbConn = new DatabaseConnection("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/klinika", "root", "");

            System.out.println("polaczono");
            dbConn.setDoctorsList();
            dbConn.setPatientsList();
            dbConn.setVisitsList();
            dbConn.updateCopyLists();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(RegisterWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }

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
        int i = 0;
        System.out.println("DZIALA?");

        tableListP.getItems().clear();

        List<Visit> list = dbConn.getVisitByDate("2018-04-16");
        for (Visit visit : list) {
            System.out.println(visit.print());
            System.out.println(dbConn.getPatientByPESEL(visit.getPesel_Pat()).print());
            tableListP.getItems().add(new Visit(visit.getID(), Integer.toString(i), dbConn.getPatientByPESEL(visit.getPesel_Pat()).getImie(), dbConn.getPatientByPESEL(visit.getPesel_Pat()).getNazwisko(), visit.getDate(), visit.getStatus()));
            i++;

        }
        columnTableLp.setCellValueFactory(new PropertyValueFactory<>("Ilosc"));
        columnTableNameP.setCellValueFactory(new PropertyValueFactory<>("Imie"));
        columnTableSurnameP.setCellValueFactory(new PropertyValueFactory<>("Nazwisko"));
        columnTableStatusP.setCellValueFactory(new PropertyValueFactory<>("Status"));

    }

    //Button Wizyta zakończona
    @FXML
    private void visitIsEnd(ActionEvent event) {
        List<Visit> list = dbConn.getVisitsList();
        System.out.println(tableListP.getSelectionModel().getSelectedItem().getID());
        for (Visit visit : list) {
            if(tableListP.getSelectionModel().getSelectedItem().getID() == visit.getID()){
                visit.setStatus("Zakonczona");
            }
        }

    }
}
