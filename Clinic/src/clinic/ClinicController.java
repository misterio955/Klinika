package clinic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClinicController implements Initializable {

    DatabaseConnection dbConn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

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
            Logger.getLogger(ClinicController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private DatePicker datePicker = new DatePicker();

    @FXML
    private void showPatients() {

        try {
            connect();
            //dbConn.setHours();
            //dbConn.showList(dbConn.getDatesList());
            //  dbConn.addHourToDate(dbConn.getDates().get(1), "10:00:00");
            //System.out.println(dbConn.getBusyHoursFromDate(dbConn.getDateByDay("2019-06-19")));
            //  System.out.println(dbConn.getFreeHoursFromDate(dbConn.getDates().get(1)));
            //dbConn.createVisit(dbConn.getDoctorByID("1"), dbConn.getPatientByPESEL("78031541367"), "2018-04-16 15:00:00");
            //dbConn.createVisit(dbConn.getDoctorByID("2"), dbConn.getPatientByPESEL("78031541367"), "2018-04-16 13:00:00");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            //dbConn.showList(dbConn.getDatesList());
            //dbConn.changeVisitDate(dbConn.getVisitByID("2"), "2018-04-25 33:30:00.0");
            //dbConn.createVisit(dbConn.getDoctorByID("2"), dbConn.getPatientByPESEL("78031541367"), "2018-06-19 11:00:00");
           // System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            //dbConn.showList(dbConn.getVisitsList());

            //dbConn.showList(dbConn.getDates());

            dbConn.shutdown();
        } catch (SQLException ex) {
            Logger.getLogger(ClinicController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void quit(ActionEvent e) {

    }

    @FXML
    private void getDay(ActionEvent e) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate date = datePicker.getValue();
        if (date != null) {
            System.out.println(formatter.format(date));
        } else {
            System.out.println("nie wybrano daty");
        }
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
        }

    }

}
