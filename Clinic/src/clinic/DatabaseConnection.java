package clinic;

import java.sql.Connection ;
import java.sql.DriverManager ;
import java.sql.SQLException ;
import java.sql.Statement ;
import java.sql.ResultSet ;

import java.util.List ;
import java.util.ArrayList ;

public class DatabaseConnection {

    // in real life, use a connection pool....
    private final Connection connection ;

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

    public List<Patient> getPatientsList() throws SQLException {
        try (
            Statement stmnt = connection.createStatement();
            ResultSet rs = stmnt.executeQuery("select * from pacjenci");
        ){
            List<Patient> patientList = new ArrayList<>();
            while (rs.next()) {
                String ID = rs.getString("ID");
                String Pesel = rs.getString("Pesel");
                String Imie = rs.getString("Imie");
                String Nazwisko = rs.getString("Nazwisko");
                String Telefon = rs.getString("telefon");
                Patient patient= new Patient(ID, Pesel, Imie, Nazwisko,  Telefon);
                patientList.add(patient);
                System.out.println(patient.print());
            }
            return patientList ;
        } 
    }
    
    public List<Doctor> getDoctorsList() throws SQLException {
        try (
            Statement stmnt = connection.createStatement();
            ResultSet rs = stmnt.executeQuery("select * from lekarze");
        ){
            List<Doctor> doctorsList = new ArrayList<>();
            while (rs.next()) {
                String ID = rs.getString("ID");
                
                String Imie = rs.getString("Imie");
                String Nazwisko = rs.getString("Nazwisko");
                String Password = rs.getString("Haslo");
                String Spec = rs.getString("Specjalizacja");
                String Telefon = rs.getString("Telefon");
                String Sala = rs.getString("Sala");
                Doctor doctor = new Doctor(ID, Imie, Nazwisko,Password,Spec, Telefon, Sala);
                doctorsList.add(doctor);
                System.out.println(doctor.print());
            }
            return doctorsList ;
        } 
    }
    
     public List<Visit> getVisitsList() throws SQLException {
        try (
            Statement stmnt = connection.createStatement();
            ResultSet rs = stmnt.executeQuery("select * from wizyty");
        ){
            List<Visit> visitsList = new ArrayList<>();
            while (rs.next()) {
                String ID = rs.getString("ID");
                String ID_Doc = rs.getString("ID_Lekarza");
                String ID_Pat = rs.getString("ID_Pacjenta");
                String Date = rs.getString("Data_Wizyty");
                String Status = rs.getString("Status_wizyty");
                Visit visit = new Visit(ID, ID_Doc, ID_Pat,Date,Status);
                visitsList.add(visit);
                System.out.println(visit.print());
            }
            return visitsList ;
        } 
    }

}