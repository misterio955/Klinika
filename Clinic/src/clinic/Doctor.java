/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinic;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Basian
 */
public class Doctor implements IComponent{
    
    private final StringProperty ID = new SimpleStringProperty(this, "ID");

    public StringProperty idProperty() {
        return ID;
    }

    public final String getID() {
        return idProperty().get();
    }

    public final void setID(String ID) {
        idProperty().set(ID);
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
    
    private final StringProperty haslo = new SimpleStringProperty(this, "Haslo");

    public StringProperty hasloProperty() {
        return haslo;
    }

    public final String getPassword() {
        return hasloProperty().get();
    }

    public final void setPassword(String password) {
        hasloProperty().set(password);
    }
    
    private final StringProperty specjalizacja = new SimpleStringProperty(this, "Specjalizacja");

    public StringProperty specjalizacjaProperty() {
        return specjalizacja;
    }

    public final String getSpec() {
        return specjalizacjaProperty().get();
    }

    public final void setSpec(String spec) {
        specjalizacjaProperty().set(spec);
    }
    
    private final StringProperty telefon = new SimpleStringProperty(this, "Telefon");

    public StringProperty telefonProperty() {
        return telefon;
    }

    public final String getPhone() {
        return telefonProperty().get();
    }

    public final void setPhone(String phone) {
        telefonProperty().set(phone);
    }

    private final StringProperty sala = new SimpleStringProperty(this, "Sala");

    public StringProperty salaProperty() {
        return sala;
    }

    public final String getRoom() {
        return salaProperty().get();
    }

    public final void setRoom(String sala) {
        salaProperty().set(sala);
    }

   public Doctor(String ID, String firstName, String lastName, String password, String spec,  String telefon, String room) {
        setID(ID);
        setImie(firstName);
        setNazwisko(lastName);
        setPassword(password);
        setSpec(spec);
        setPhone(telefon);
        setRoom(room);
    }public Doctor(String ID, String firstName, String lastName, String spec,  String telefon, String room) {
        setID(ID);
        setImie(firstName);
        setNazwisko(lastName);
        setSpec(spec);
        setPhone(telefon);
        setRoom(room);
    }

    
    public String print() {
        return "ID=" + getID() + ", imie=" + getImie() + ", nazwisko=" + getNazwisko() + ", haslo=" + getPassword() + ", specjalizacja=" + getSpec() + ", telefon=" + getPhone() + ", sala=" + getRoom();
    }
   
    
    
}
