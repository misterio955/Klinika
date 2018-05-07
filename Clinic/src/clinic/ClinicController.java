/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinic;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author student
 */
public class ClinicController implements Initializable {

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
     @FXML
     private void quit(ActionEvent e){
      
      Runtime.getRuntime().exit(1);
     }
     
     


     @FXML
     private void login(ActionEvent e){
        
            
          try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RegisterWindow.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));  
                stage.show();
                ((Node)(e.getSource())).getScene().getWindow().hide();  
        } catch(IOException eL) {
           eL.printStackTrace();
          }
      
     }
    
}
