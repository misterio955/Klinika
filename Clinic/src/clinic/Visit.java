
package clinic;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Visit implements IComponent {
    
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
    
     private final StringProperty ID_Pat = new SimpleStringProperty(this, "ID_Pacjenta");

    public StringProperty ID_PatProperty() {
        return ID_Pat;
    }

    public final String getID_Pat() {
        return ID_PatProperty().get();
    }

    public final void setID_Pat(String ID) {
        ID_PatProperty().set(ID);
    }
    
    
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

    public Visit(String ID, String ID_Doc, String ID_Pat, String Date, String status) {
        setID(ID);
        setID_Doc(ID_Doc);
        setID_Pat(ID_Doc);
        setDate(Date);
        setStatus(status);
    }

    public String print() {
        return "ID=" + getID() + ", ID_Doc=" + getID_Doc() + ", ID_Pat=" + getID_Pat() + ", date=" + getDate() + ", status=" + getStatus();
    }
   
}
