package clinic;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DoctorWindowController implements Initializable {

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

            dbConn.setDoctorsList();
            dbConn.setPatientsList();
            dbConn.setVisitsList();
            dbConn.updateCopyLists();
            dbConn.setDatePicker(datePicker);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(RegisterWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        defaultDate();

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
            generatePDF();
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
        for (Visit visit : list) {
            if (visit.getID_Doc().equals(Integer.toString(currentIdDoctor))) {
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

        List<Visit> list = dbConn.getVisitByDate(LocalDate.now().toString());
        List<Visit> listByDoctor = new ArrayList<Visit>();

        for (Visit visit : list) {
            if (visit.getID_Doc().equals(Integer.toString(currentIdDoctor))) {
                listByDoctor.add(visit);
            }
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
    @FXML
    private void generatePDF() {

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");

        if (!date.equals(datePicker.getValue())) {
            Alert alert3 = new Alert(Alert.AlertType.WARNING);
            alert3.setTitle("Informacja");
            alert3.setHeaderText("Możesz generować raport jedynie z obecnego dnia");
            alert3.setContentText("Wyświetl wizyty z dzisiejszego dnia i spróbuj ponownie");
            alert3.showAndWait();
        } else {
            if (LocalTime.now().getHour() != 15) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("UWAGA");
                alert.setHeaderText("Wskazane jest, aby generować raport maksymalnie godzinę przed końcem dyżuru");
                alert.setContentText("Czy na pewno chcesz kontynuować?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    Document doc = new Document();

                    try {

                        PdfWriter.getInstance(doc, new FileOutputStream("C:\\Klinika-Raporty\\" + date.toString() + ".pdf"));
                        doc.open();
                        PdfPTable ptable = new PdfPTable(4);
                        ptable.addCell("Lp");
                        ptable.addCell("Imie");
                        ptable.addCell("Nazwisko");
                        ptable.addCell("Status Wizyty");

                        int done = 0;
                        int canceled = 0;
                        int didntdone = 0;

                        for (int i = 0; i < tableListP.getItems().size(); i++) {

                            ptable.addCell(columnTableLp.getCellData(i));
                            ptable.addCell(columnTableNameP.getCellData(i));
                            ptable.addCell(columnTableSurnameP.getCellData(i));
                            ptable.addCell(columnTableStatusP.getCellData(i));

                            if (columnTableStatusP.getCellData(i).equals("Nie odbyla sie")) {
                                didntdone++;
                            } else if (columnTableStatusP.getCellData(i).equals("Anulowana")) {
                                canceled++;
                            } else {
                                done++;
                            }
                        }

                        Paragraph paragraph = new Paragraph("Raport zlozony przez:   ID lekarza:" + currentIdDoctor + "    Imie: " + dbConn.getDoctorByID(String.valueOf(currentIdDoctor)).getImie()
                                + "   Nazwisko: " + dbConn.getDoctorByID(String.valueOf(currentIdDoctor)).getNazwisko() + "   DATA:" + date);
                        paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
                        Paragraph paragraph1 = new Paragraph("LISTA DZISIEJSZYCH WIZYT:");
                        paragraph1.setAlignment(Element.ALIGN_CENTER);
                        Paragraph paragraph2 = new Paragraph("LICZBA WSZYSTKICH WIZYT: " + String.valueOf(didntdone + done + canceled));
                        Paragraph paragraph3 = new Paragraph("W TYM ZAKONCZONYCH: " + String.valueOf(done) + ";");
                        Paragraph paragraph4 = new Paragraph("NIEZAKONCZONYCH: " + String.valueOf(didntdone) + ";");
                        Paragraph paragraph5 = new Paragraph("ANULOWANYCH: " + String.valueOf(canceled) + ".");
                        doc.add(paragraph);
                        doc.add(Chunk.NEWLINE);
                        doc.add(paragraph1);
                        doc.add(Chunk.NEWLINE);
                        doc.add(ptable);
                        doc.add(Chunk.NEWLINE);
                        doc.add(paragraph2);
                        doc.add(Chunk.NEWLINE);
                        doc.add(paragraph3);
                        doc.add(Chunk.NEWLINE);
                        doc.add(paragraph4);
                        doc.add(Chunk.NEWLINE);
                        doc.add(paragraph5);

                    } catch (FileNotFoundException | DocumentException ex) {
                        Logger.getLogger(DoctorWindowController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    doc.close();
                } 

            }

        }
    }

}
