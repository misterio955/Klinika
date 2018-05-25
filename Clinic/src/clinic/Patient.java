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
        setTelefon(patient.getTelefon());
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

 
    private final StringProperty telefon = new SimpleStringProperty(this, "Telefon");

    public StringProperty telefonProperty() {
        return telefon;
    }

    public final String getTelefon() {
        return telefonProperty().get();
    }

    public final void setTelefon(String phone) {
        telefonProperty().set(phone);
    }

    public Patient(String ID, String pesel, String firstName, String lastName,  String telefon) {
        setID(ID);
        setImie(firstName);
        setNazwisko(lastName);
        setPesel(pesel);
        setTelefon(telefon);
    }

    public String print() {
        return "ID=" + getID() + ", pesel=" + getPesel() + ", imie=" + getImie() + ", nazwisko=" + getNazwisko() +  ", telefon=" + getTelefon();
    }

}
