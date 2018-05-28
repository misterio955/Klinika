package clinic;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class Patient implements IComponent{

    private final StringProperty ID = new SimpleStringProperty(this, "ID");

    public Patient(Patient patient) {
        setID(patient.getID());
        setImie(patient.getImie());
        setNazwisko(patient.getNazwisko());
        setPesel(patient.getPesel());
        setEmail(patient.getEmail());
    }
    
    public StringProperty idProperty() {
        return ID;
    }

    public final String getID() {
        return idProperty().get();
    }

    public final void setID(String ID) {
        idProperty().set(ID);
    }
    
     private final StringProperty pesel = new SimpleStringProperty(this, "Pesel");

    public StringProperty peselProperty() {
        return pesel;
    }

    public final String getPesel() {
        return peselProperty().get();
    }

    public final void setPesel(String pesel) {
        peselProperty().set(pesel);
    }

    private final StringProperty imie = new SimpleStringProperty(this, "Imie");

    public StringProperty imieProperty() {
        return imie;
    }

    public final String getImie() {
        return imieProperty().get();
    }

    public final void setImie(String firstName) {
        imieProperty().set(firstName);
    }

    private final StringProperty nazwisko = new SimpleStringProperty(this, "Nazwisko");

    public StringProperty nazwiskoProperty() {
        return nazwisko;
    }

    public final String getNazwisko() {
        return nazwiskoProperty().get();
    }

    public final void setNazwisko(String lastName) {
        nazwiskoProperty().set(lastName);
    }

 
    private final StringProperty email = new SimpleStringProperty(this, "Email");

    public StringProperty emailProperty() {
        return email;
    }

    public final String getEmail() {
        return emailProperty().get();
    }

    public final void setEmail(String email) {
        emailProperty().set(email);
    }

    public Patient(String ID, String pesel, String firstName, String lastName,  String email) {
        setID(ID);
        setImie(firstName);
        setNazwisko(lastName);
        setPesel(pesel);
        setEmail(email);
    }

    public String print() {
        return "ID=" + getID() + ", pesel=" + getPesel() + ", imie=" + getImie() + ", nazwisko=" + getNazwisko() +  ", telefon=" + getEmail();
    }

}
