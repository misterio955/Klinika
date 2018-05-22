package clinic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DatabaseConnection {

    private List<Patient> patientsList;
    private List<Doctor> doctorsList;
    private List<Visit> visitsList;
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
                String Telefon = rs.getString("telefon");
                Patient patient = new Patient(ID, Pesel, Imie, Nazwisko, Telefon);
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

    public void registerPatient(String pesel, String firstName, String lastName, String telefon) {
        String id = String.valueOf(patientsList.size() + 1);
        Patient patient = new Patient(id, pesel, firstName, lastName, telefon);
        patientsList.add(patient);
        try {
            String query = "INSERT INTO Pacjenci VALUES ('" + id + "', '" + pesel + "', '" + firstName + "', '" + lastName + "', '" + telefon + "');";
            Statement stmnt = connection.createStatement();
            int rs = stmnt.executeUpdate(query);
            if (rs > 0) {
                JOptionPane.showMessageDialog(null, "Zarejestrowano pacjenta pomyślnie.");
            } else {
                JOptionPane.showMessageDialog(null, "Wystąpił błąd podczas dodawania rekordu.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
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
                String Telefon = rs.getString("Telefon");
                String Sala = rs.getString("Sala");
                Doctor doctor = new Doctor(ID, Imie, Nazwisko, Password, Spec, Telefon, Sala);
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

    public void registerDoctor(String firstName, String lastName, String password, String spec, String telefon, String room) {
        String id = String.valueOf(doctorsList.size() + 1);
        Doctor doctor = new Doctor(id, firstName, lastName, password, spec, telefon, room);
        doctorsList.add(doctor);
        
         try {
            String query = "INSERT INTO Lekarze VALUES ('" + id + "', '" + firstName + "', '" + lastName + "', '" + password + "', '" + spec + "', '"+ telefon + "', '"+ room + "');";
            Statement stmnt = connection.createStatement();
            int rs = stmnt.executeUpdate(query);
            if (rs > 0) {
                JOptionPane.showMessageDialog(null, "Dodano nowego lekarza.");
            } else {
                JOptionPane.showMessageDialog(null, "Wystąpił błąd podczas dodawania rekordu.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

// VISISTS  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~  
    public void setVisitsList() throws SQLException {
        try (
                Statement stmnt = connection.createStatement();
                ResultSet rs = stmnt.executeQuery("select * from wizyty");) {
            List<Visit> visitList = new ArrayList<>();
            while (rs.next()) {
                String ID = rs.getString("ID");
                String ID_Doc = rs.getString("ID_Lekarza");
                String ID_Pat = rs.getString("ID_Pacjenta");
                String Date = rs.getString("Data_Wizyty");
                String Status = rs.getString("Status_wizyty");
                Visit visit = new Visit(ID, ID_Doc, ID_Pat, Date, Status);
                visitList.add(visit);
                //  System.out.println(visit.print());
            }
            this.visitsList = visitList;
        }
    }

    public List<Visit> getVisitsList() {

        return visitsList;
    }

    public List<Visit> getVisitByDate(String date) {

        List<Visit> score = new ArrayList<>();

        for (Visit visit : visitsList) {

            if (visit.getDate().equals(date)) {
                score.add(visit);
            }
        }
        return score;
    }

    public List<Visit> getVisitByPatient(Patient patient) {
        List<Visit> score = new ArrayList<>();
        for (Visit visit : visitsList) {

            if (visit.getID_Pat().equals(patient.getID())) {
                score.add(visit);
            }
        }
        return score;
    }

    public void createVisit(Doctor doctor, Patient patient, String date) {
        String id = String.valueOf(visitsList.size() + 1);
        Visit visit = new Visit(id, doctor.getID(), patient.getID(), date, "Oczekiwana");
        visitsList.add(visit);
        
         try {
            String query = "INSERT INTO Wizyty VALUES ('" + id + "', '" + doctor.getID() + "', '" + patient.getID() + "', '" + date + "', 'Oczekiwana');";
            Statement stmnt = connection.createStatement();
            int rs = stmnt.executeUpdate(query);
            if (rs > 0) {
                JOptionPane.showMessageDialog(null, "Dodano nową wizytę.");
            } else {
                JOptionPane.showMessageDialog(null, "Wystąpił błąd podczas dodawania rekordu.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        String id = visit.getID();
        
        try {
            String query = "UPDATE Wizyty SET Data_Wizyty = '"+ date + "', Status_wizyty = 'Oczekiwana' WHERE ID = '"+id+"';";
            Statement stmnt = connection.createStatement();
            int rs = stmnt.executeUpdate(query);
            if (rs > 0) {
                JOptionPane.showMessageDialog(null, "Zmodyfikowano termin wizyty.");
            } else {
                JOptionPane.showMessageDialog(null, "Wystąpił błąd podczas modyfikowania wizyty.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void changeVisitStatus(Visit visit, String status) {
        visit.setStatus(status);
        String id = visit.getID();
        
        try {
            String query = "UPDATE Wizyty SET Status_wizyty = '"+status+"' WHERE ID = '"+id+"';";
            Statement stmnt = connection.createStatement();
            int rs = stmnt.executeUpdate(query);
            if (rs > 0) {
                JOptionPane.showMessageDialog(null, "Zmodyfikowano termin wizyty.");
            } else {
                JOptionPane.showMessageDialog(null, "Wystąpił błąd podczas modyfikowania wizyty.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
