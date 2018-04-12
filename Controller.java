package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb){


    }
    @FXML
    private void test(ActionEvent event)
    {
        System.out.println("Dziala");
        Runtime.getRuntime().exit(1);
    }

}
