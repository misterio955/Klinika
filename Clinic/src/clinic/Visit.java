
package clinic;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Visit implements IComponent {

    private final StringProperty ID = new SimpleStringProperty(this, "ID");

    public Visit(Visit visit) {
        setID(visit.getID());
        setID_Doc(visit.getID_Doc());
        setPesel_Pat(visit.getPesel_Pat());
        setDate(visit.getDate());
        setStatus(visit.getStatus());
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

    private final StringProperty ID_Doc = new SimpleStringProperty(this, "ID_Lekarza");

    public StringProperty ID_DocProperty() {
        return ID_Doc;
    }

    public final String getID_Doc() {
        return ID_DocProperty().get();
    }

    public final void setID_Doc(String ID) {
        ID_DocProperty().set(ID);
    }

    //-----------------------------------------------------------------------
    private final StringProperty Pesel_Pat = new SimpleStringProperty(this, "Pesel_Pacjenta");

    public StringProperty Pesel_PatProperty() {
        return Pesel_Pat;
    }

    public final String getPesel_Pat() {
        return Pesel_PatProperty().get();
    }

    public final void setPesel_Pat(String Pesel) {
        Pesel_PatProperty().set(Pesel);
    }

    //--------------------------------------------------
    private final StringProperty date = new SimpleStringProperty(this, "Data_Wizyty");

    public StringProperty dateProperty() {
        return date;
    }

    public final String getDate() {
        return dateProperty().get();
    }

    public final void setDate(String date) {
        dateProperty().set(date);
    }

    private final StringProperty status = new SimpleStringProperty(this, "Status_wizyty");

    public StringProperty statusProperty() {
        return status;
    }

    public final String getStatus() {
        return statusProperty().get();
    }

    public final void setStatus(String status) {
        statusProperty().set(status);
    }

    //----------------------------------------------------------------------------------------
    private final StringProperty imie = new SimpleStringProperty(this, "Imie");

    public final StringProperty imieProperty() {
        return imie;
    }

    public final String getImie() {
        return imieProperty().get();
    }

    public final void setImie(String imie) {
        imieProperty().set(imie);
    }


    private final StringProperty nazwisko = new SimpleStringProperty(this, "Nazwisko");

    public StringProperty nazwiskoProperty() {
        return nazwisko;
    }

    public final String getNazwisko() {
        return nazwiskoProperty().get();
    }

    public final void setNazwisko(String nazwisko) {
        nazwiskoProperty().set(nazwisko);
    }


    private final StringProperty ilosc = new SimpleStringProperty(this, "Ilosc");

    public StringProperty iloscProperty() {
        return ilosc;
    }

    public final String getIlosc() {
        return iloscProperty().get();
    }

    public final void setIlosc(String ilosc) {
        iloscProperty().set(ilosc);
    }


    public Visit(String ID, String ID_Doc, String Pesel_Pat, String Date, String status) {
        setID(ID);
        setID_Doc(ID_Doc);
        setPesel_Pat(Pesel_Pat);
        setDate(Date);
        setStatus(status);
    }

    public Visit(String ID,String ilosc, String imie, String nazwisko,String date, String status) {
        setID(ID);
        setIlosc(ilosc);
        setImie(imie);
        setNazwisko(nazwisko);
        setStatus(status);
        setDate(date);
    }


    public String print() {
        return "ID=" + getID() + ", ID_Doc=" + getID_Doc() + ", Pesel_Pat=" + getPesel_Pat() + ", date=" + getDate() + ", status=" + getStatus();
    }

}
