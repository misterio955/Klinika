package clinic;

import java.sql.Connection ;
import java.sql.DriverManager ;
import java.sql.SQLException ;
import java.sql.Statement ;
import java.sql.ResultSet ;

import java.util.List ;
import java.util.ArrayList ;

public class PersonDataAccessor {

    // in real life, use a connection pool....
    private Connection connection ;

    public PersonDataAccessor(String driverClassName, String dbURL, String user, String password) throws SQLException, ClassNotFoundException {
        Class.forName(driverClassName);
        connection = DriverManager.getConnection(dbURL, user, password);
    }

    public void shutdown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public List<Patient> getPersonList() throws SQLException {
        try (
            Statement stmnt = connection.createStatement();
            ResultSet rs = stmnt.executeQuery("select * from person");
        ){
            List<Patient> patientList = new ArrayList<>();
            while (rs.next()) {
                String ID = rs.getString("ID");
                String Imie = rs.getString("Imie");
                String Nazwisko = rs.getString("Nazwisko");
                String Pesel = rs.getString("Pesel");
                String Telefon = rs.getString("telefon");
                Patient patient= new Patient(ID, Imie, Nazwisko, Pesel, Telefon);
                patientList.add(patient);
            }
            return patientList ;
        } 
    }

}