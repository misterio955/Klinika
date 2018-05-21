package clinic;

import java.sql.Connection ;
import java.sql.DriverManager ;
import java.sql.SQLException ;
import java.sql.Statement ;
import java.sql.ResultSet ;

import java.util.List ;
import java.util.ArrayList ;


public class DatabaseConnection {

   
    
    private List<Patient> patientsList;
    private List<Doctor> doctorsList;
    private List<Visit> visitsList;
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

    public void setPatientsList() throws SQLException {
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
            }
             this.patientsList = patientList;
        } 
    }
    
    public void setDoctorsList() throws SQLException {
        try (
            Statement stmnt = connection.createStatement();
            ResultSet rs = stmnt.executeQuery("select * from lekarze");
        ){
            List<Doctor> doctorList = new ArrayList<>();
            while (rs.next()) {
                String ID = rs.getString("ID");
                
                String Imie = rs.getString("Imie");
                String Nazwisko = rs.getString("Nazwisko");
                String Password = rs.getString("Haslo");
                String Spec = rs.getString("Specjalizacja");
                String Telefon = rs.getString("Telefon");
                String Sala = rs.getString("Sala");
                Doctor doctor = new Doctor(ID, Imie, Nazwisko,Password,Spec, Telefon, Sala);
                doctorList.add(doctor);
               // System.out.println(doctor.print());
            }
            this.doctorsList = doctorList;
        } 
    }
    
     public void setVisitsList() throws SQLException {
        try (
            Statement stmnt = connection.createStatement();
            ResultSet rs = stmnt.executeQuery("select * from wizyty");
        ){
            List<Visit> visitList = new ArrayList<>();
            while (rs.next()) {
                String ID = rs.getString("ID");
                String ID_Doc = rs.getString("ID_Lekarza");
                String ID_Pat = rs.getString("ID_Pacjenta");
                String Date = rs.getString("Data_Wizyty");
                String Status = rs.getString("Status_wizyty");
                Visit visit = new Visit(ID, ID_Doc, ID_Pat,Date,Status);
                visitList.add(visit);
              //  System.out.println(visit.print());
            }
            this.visitsList = visitList;
        } 
    }
     
      public List<Patient> getPatientsList(){
       
        return patientsList;
     }
       public List<Doctor> getDoctorsList(){
       
        
        return doctorsList;
       }
     public List<Visit> getVisitsList(){
       
         
         return visitsList;
     }
     
     public List<Patient> getPatientByPESEL(String pesel){
        
        List<Patient> list = null;
        for (Patient patient: patientsList) {
            if(patient.getPesel()==pesel) {
                list.add(patient);
            }
        }
      
        return list;
     }
     
     public void showList(List list){
        List<IComponent> lista = list;
        for (IComponent component: lista) {
            System.out.println(component.print());
        } 
     }


}