package clinic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;
import javax.swing.JOptionPane;

public class DatabaseConnection {

    private List<Patient> patientsList;
    private final List<Patient> patientsListCopy = new ArrayList<>();
    private List<Doctor> doctorsList;
    private final List<Doctor> doctorsListCopy = new ArrayList<>();
    private List<Visit> visitsList;
    private final List<Visit> visitsListCopy = new ArrayList<>();
    private final List<Date> datesList = new ArrayList<>();
    private final Connection connection;

    public DatabaseConnection(String driverClassName, String dbURL, String user, String password) throws SQLException, ClassNotFoundException {
        Class.forName(driverClassName);
        connection = DriverManager.getConnection(dbURL, user, password);
    }

    public void shutdown() throws SQLException {
        if (connection != null) {
            connection.close();
            System.out.println("rozlaczono");
        }
    }

    public void showList(List list) {
        List<IComponent> lista = list;
        for (IComponent component : lista) {
            System.out.println(component.print());
        }
    }

    public void updateCopyLists() {

        for (Patient patient : patientsList) {
            Patient copyPatient = new Patient(patient);
            patientsListCopy.add(copyPatient);
        }

        for (Doctor doctor : doctorsList) {
            Doctor copyDoctor = new Doctor(doctor);
            doctorsListCopy.add(copyDoctor);
        }
        for (Visit visit : visitsList) {
            Visit copyVisit = new Visit(visit);
            visitsListCopy.add(copyVisit);
        }
    }

    public void compareLists() {
        comparePatients();
        compareDoctors();
        compareVisits();
        updateCopyLists();
    }

    public void setDatePicker(DatePicker datePicker) {
        final Callback<DatePicker, DateCell> dayCellFactory
                = new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item.isBefore(LocalDate.now())) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                                }
                                for (Date date : getDatesList()) {
                                    if (item.toString().equals(date.getDay()) && date.getHoursBusy().size() == 32) {
                                        setDisable(true);
                                        setStyle("-fx-background-color: #ffc0cb;");
                                    }
                                }
                            }
                        };
                    }
                };
        datePicker.setDayCellFactory(dayCellFactory);
    }

// PATIENTS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public void setPatientsList() throws SQLException {
        try (
                Statement stmnt = connection.createStatement();
                ResultSet rs = stmnt.executeQuery("select * from pacjenci");) {
            List<Patient> patientList = new ArrayList<>();
            while (rs.next()) {
                String ID = rs.getString("ID");
                String Pesel = rs.getString("Pesel");
                String Imie = rs.getString("Imie");
                String Nazwisko = rs.getString("Nazwisko");
                String Email = rs.getString("Email");
                Patient patient = new Patient(ID, Pesel, Imie, Nazwisko, Email);
                patientList.add(patient);
            }
            this.patientsList = patientList;
        }
    }

    public List<Patient> getPatientsList() {

        return patientsList;

    }

    public Patient getPatientByPESEL(String pesel) {

        Patient score = null;

        for (Patient patient : patientsList) {

            if (patient.getPesel().equals(pesel)) {
                score = patient;
            }
        }
        return score;
    }

    public List<Patient> getPatientByName(String firstName, String lastName) {

        List<Patient> score = new ArrayList<>();

        for (Patient patient : patientsList) {

            if (patient.getImie().equals(firstName) || patient.getNazwisko().equals(lastName)) {
                score.add(patient);
            }
        }
        return score;
    }

    public void registerPatient(String pesel, String firstName, String lastName, String email) {
        Patient patient = new Patient(String.valueOf(patientsList.size() + 1), pesel, firstName, lastName, email);
        patientsList.add(patient);
    }

    public void comparePatients() {
        for (int i = 0; i < patientsList.size(); i++) {

            if (i < patientsListCopy.size()) {
                if (!patientsList.get(i).print().equals(patientsListCopy.get(i).print())) {

                    try {
                        String query = "UPDATE pacjenci SET ID = '" + patientsList.get(i).getID() + "', Pesel = '" + patientsList.get(i).getPesel()
                                + "', Imie = '" + patientsList.get(i).getImie() + "', Nazwisko = '" + patientsList.get(i).getNazwisko() + "', Email = '" + patientsList.get(i).getEmail()
                                + "' WHERE ID = '" + patientsList.get(i).getID() + "';";
                        Statement stmnt = connection.createStatement();
                        int rs = stmnt.executeUpdate(query);
                        if (rs > 0) {
                            JOptionPane.showMessageDialog(null, "Zmodyfikowano termin wizyty." + rs);
                        } else {
                            JOptionPane.showMessageDialog(null, "Wystąpił błąd podczas modyfikowania wizyty.");
                        }
                        System.out.println(query);
                    } catch (SQLException ex) {
                        Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            } else {
                try {
                    String query = "INSERT INTO Pacjenci VALUES ('" + patientsList.get(i).getID() + "', '" + patientsList.get(i).getPesel() + "', '" + patientsList.get(i).getImie() + "', '" + patientsList.get(i).getNazwisko() + "', '" + patientsList.get(i).getEmail() + "');";
                    Statement stmnt = connection.createStatement();
                    int rs = stmnt.executeUpdate(query);
                    if (rs > 0) {
                        //JOptionPane.showMessageDialog(null, "Zarejestrowano pacjenta pomyślnie.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Wystąpił błąd podczas dodawania rekordu.");

                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

//  DOCTORS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public void setDoctorsList() throws SQLException {
        try (
                Statement stmnt = connection.createStatement();
                ResultSet rs = stmnt.executeQuery("select * from lekarze");) {
            List<Doctor> doctorList = new ArrayList<>();
            while (rs.next()) {
                String ID = rs.getString("ID");

                String Imie = rs.getString("Imie");
                String Nazwisko = rs.getString("Nazwisko");
                String Password = rs.getString("Haslo");
                String Spec = rs.getString("Specjalizacja");
                String Email = rs.getString("Email");
                String Sala = rs.getString("Sala");
                Doctor doctor = new Doctor(ID, Imie, Nazwisko, Password, Spec, Email, Sala);
                doctorList.add(doctor);
                // System.out.println(doctor.print());
            }
            this.doctorsList = doctorList;
        }
    }

    public List<Doctor> getDoctorsList() {

        return doctorsList;
    }

    public List<Doctor> getDoctorByName(String firstName, String lastName) {

        List<Doctor> score = new ArrayList<>();

        for (Doctor doctor : doctorsList) {

            if (doctor.getImie().equals(firstName) || doctor.getNazwisko().equals(lastName)) {
                score.add(doctor);
            }
        }
        return score;
    }

    public List<Doctor> getDoctorBySpec(String spec) {

        List<Doctor> score = new ArrayList<>();

        for (Doctor doctor : doctorsList) {

            if (doctor.getSpec().equals(spec)) {
                score.add(doctor);
            }
        }
        return score;
    }

    public Doctor getDoctorByID(String id) {

        Doctor score = null;

        for (Doctor doctor : doctorsList) {

            if (doctor.getID().equals(id)) {
                score = doctor;
            }
        }
        return score;
    }

    public List<Doctor> getDoctorByRoom(String room) {

        List<Doctor> score = new ArrayList<>();

        for (Doctor doctor : doctorsList) {

            if (doctor.getRoom().equals(room)) {
                score.add(doctor);
            }
        }
        return score;
    }

    public void registerDoctor(String firstName, String lastName, String password, String spec, String email, String room) {
        Doctor doctor = new Doctor(String.valueOf(doctorsList.size() + 1), firstName, lastName, password, spec, email, room);
        doctorsList.add(doctor);
    }

    public void compareDoctors() {
        for (int i = 0; i < doctorsList.size(); i++) {

            if (i < doctorsListCopy.size()) {
                if (!doctorsList.get(i).print().equals(doctorsListCopy.get(i).print())) {

                    try {
                        String query = "UPDATE lekarze SET ID = '" + doctorsList.get(i).getID() + "', Imie = '" + doctorsList.get(i).getImie()
                                + "', Nazwisko = '" + doctorsList.get(i).getNazwisko() + "', Haslo = '" + doctorsList.get(i).getPassword() + "', Specjalizacja = '" + doctorsList.get(i).getSpec() + "', Email = '" + doctorsList.get(i).getEmail()
                                + "', Sala = '" + doctorsList.get(i).getRoom() + "' WHERE ID = '" + doctorsList.get(i).getID() + "';";
                        Statement stmnt = connection.createStatement();
                        int rs = stmnt.executeUpdate(query);
                        if (rs > 0) {
                            JOptionPane.showMessageDialog(null, "Zmodyfikowano termin wizyty." + rs);
                        } else {
                            JOptionPane.showMessageDialog(null, "Wystąpił błąd podczas modyfikowania wizyty.");
                        }
                        // System.out.println(query);
                    } catch (SQLException ex) {
                        Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            } else {
                try {
                    String query = "INSERT INTO Lekarze VALUES ('" + doctorsList.get(i).getID() + "', '" + doctorsList.get(i).getImie() + "', '"
                            + doctorsList.get(i).getNazwisko() + "', '" + doctorsList.get(i).getPassword() + "', '" + doctorsList.get(i).getSpec() + "', '" + doctorsList.get(i).getEmail() + "', '" + doctorsList.get(i).getRoom() + "');";
                    Statement stmnt = connection.createStatement();
                    int rs = stmnt.executeUpdate(query);
                    if (rs > 0) {
                        //JOptionPane.showMessageDialog(null, "Zarejestrowano pacjenta pomyślnie.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Wystąpił błąd podczas dodawania rekordu.");

                    }
                    // System.out.println(query);
                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

// VISISTS  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~  
    public void setVisitsList() throws SQLException {
        try (
                Statement stmnt = connection.createStatement();
                ResultSet rs = stmnt.executeQuery("select * from wizyty");) {
            List<Visit> visitList = new ArrayList<>();
            List<Date> dateList = new ArrayList<>();
            while (rs.next()) {
                String ID = rs.getString("ID");
                String ID_Doc = rs.getString("ID_Lekarza");
                String ID_Pat = rs.getString("ID_Pacjenta");
                String Date = rs.getString("Data_Wizyty");
                String Status = rs.getString("Status_wizyty");
                Visit visit = new Visit(ID, ID_Doc, ID_Pat, Date, Status);
                addDate(Date, getDoctorByID(ID_Doc));
                visitList.add(visit);
            }
            this.visitsList = visitList;
        }
    }

    public List<Visit> getVisitsList() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~a");
        showList(visitsListCopy);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~b");
        return visitsList;
    }

    public List<Visit> getVisitByDate(String date) {

        List<Visit> score = new ArrayList<>();

        for (Visit visit : visitsList) {

            if (visit.getDate().substring(0, 10).equals(date)) {
                score.add(visit);
            }
        }
        return score;
    }

    public List<Visit> getVisitByPatient(Patient patient) {
        List<Visit> score = new ArrayList<>();
        for (Visit visit : visitsList) {

            if (visit.getPesel_Pat().equals(patient.getID())) {
                score.add(visit);
            }
        }
        return score;
    }

    public void createVisit(Doctor doctor, Patient patient, String date) {

        Visit visit = new Visit(String.valueOf(visitsList.size() + 1), doctor.getID(), patient.getID(), date, "Oczekiwana");
        visitsList.add(visit);
        addDate(date, doctor);

    }

    public Visit getVisitByID(String id) {

        Visit score = null;

        for (Visit visit : visitsList) {

            if (visit.getID().equals(id)) {
                score = visit;
            }
        }
        return score;
    }

    public void changeVisitDate(Visit visit, String date) {
        visit.setDate(date);
        visit.setStatus("Przelozona");
    }

    public void compareVisits() {
        for (int i = 0; i < visitsList.size(); i++) {

            if (i < visitsListCopy.size()) {
                if (!visitsList.get(i).print().equals(visitsListCopy.get(i).print())) {

                    try {
                        String query = "UPDATE wizyty SET ID = '" + visitsList.get(i).getID() + "', ID_Lekarza= '" + visitsList.get(i).getID_Doc()
                                + "', Pesel_pacjenta = '" + visitsList.get(i).getPesel_Pat()+ "', Data_Wizyty = '" + visitsList.get(i).getDate() + "', Status_Wizyty = '" + visitsList.get(i).getStatus()
                                + "' WHERE ID = '" + visitsList.get(i).getID() + "';";
                        Statement stmnt = connection.createStatement();
                        int rs = stmnt.executeUpdate(query);
                        if (rs > 0) {
                            JOptionPane.showMessageDialog(null, "Zmodyfikowano termin wizyty." + rs);
                        } else {
                            JOptionPane.showMessageDialog(null, "Wystąpił błąd podczas modyfikowania wizyty.");
                        }
                        // System.out.println(query);
                    } catch (SQLException ex) {
                        Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            } else {
                try {
                    String query = "INSERT INTO Wizyty VALUES ('" + visitsList.get(i).getID() + "', '"
                            + visitsList.get(i).getID_Doc() + "', '" + visitsList.get(i).getPesel_Pat()+ "', '"
                            + visitsList.get(i).getDate() + "', '" + visitsList.get(i).getStatus() + "');";
                    Statement stmnt = connection.createStatement();
                    int rs = stmnt.executeUpdate(query);

                    if (rs > 0) {
                        //JOptionPane.showMessageDialog(null, "Zarejestrowano pacjenta pomyślnie.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Wystąpił błąd podczas dodawania rekordu.");

                    }
                    //System.out.println(query);
                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

//DATES
    public List<Date> getDatesList() {

        return datesList;
    }

    public void addDate(String date, Doctor doctor) {

        Date newDate = new Date(date, doctor);
        int i = 0;
        for (Date aDate : datesList) {
            if (aDate.getDay().equals(date.substring(0, 10)) && aDate.getDoctor().equals(doctor)) {
                addHourToDate(datesList.get(i), date.substring(11, 19));
                i--;
                break;
            } else {
                i++;
            }
        }
        if (i == datesList.size()) {
            datesList.add(newDate);
            addHourToDate(newDate, date.substring(11, 19));
        }
    }

    public void addHourToDate(Date date, String hour) {
        date.addBusyHour(hour);

    }

    public List<String> getBusyHoursFromDate(Date date) {
        return date.getHoursBusy();
    }

    public List<String> getFreeHoursFromDate(Date date) {
        return date.getHoursFree();
    }

    public Date getDateByDay(String day) {
        Date score = null;

        for (Date date : datesList) {

            if (date.getDay().equals(day)) {
                score = date;
            }
        }
        return score;
    }

}
