package clinic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import java.util.List;
import java.util.ArrayList;

public class DatabaseConnection {

    private List<Patient> patientsList;
    private List<Doctor> doctorsList;
    private List<Visit> visitsList;
    //private List<Patient> wynik = new ArrayList<>();
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

    public List<Patient> getPatientsList() {

        return patientsList;
    }

    public List<Doctor> getDoctorsList() {

        return doctorsList;
    }

    public List<Visit> getVisitsList() {

        return visitsList;
    }

    public List<Patient> getPatientByPESEL(String pesel) {

        List<Patient> score = new ArrayList<>();

        for (Patient patient : patientsList) {

            if (patient.getPesel().equals(pesel)) {
                score.add(patient);
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
            System.out.println(doctor.getSpec() + " == " + spec);
            if (doctor.getSpec().equals(spec));
            {
                score.add(doctor);
            }
        }
        return score;
    }
    
    public List<Doctor> getDoctorByRoom(String room) {

        List<Doctor> score = new ArrayList<>();

        for (Doctor doctor : doctorsList) {
            System.out.println(doctor.getRoom() + " == " + room);
            if (doctor.getRoom().equals(room));
            {
                score.add(doctor);
            }
        }
        return score;
    }
    
    public List<Visit> getVisitByDate(String date) {

        List<Visit> score = new ArrayList<>();

        for (Visit visit : visitsList) {
            
            if (visit.getDate().equals(date));
            {
                score.add(visit);
            }
        }
        return score;
    }

    public void showList(List list) {
        List<IComponent> lista = list;
        for (IComponent component : lista) {
            System.out.println(component.print());
        }
    }

}
