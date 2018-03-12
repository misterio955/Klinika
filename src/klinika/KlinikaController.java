/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klinika;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * FXML Controller class
 *
 * @author student
 */
public class KlinikaController implements Initializable {

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
     @FXML
     private void wyloguj(ActionEvent e){
      
      Runtime.getRuntime().exit(1);
     }
     
     @FXML
     private void zaloguj(ActionEvent e){
      System.out.println("Zalogowano");
     }
    
}
