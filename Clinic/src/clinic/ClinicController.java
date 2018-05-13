/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinic;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    PersonDataAccessor pda;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    
    public void connect() {
        try {
            pda = new PersonDataAccessor("com.mysql.jdbc.Driver","jdbc:mysql://127.0.0.1:3306/klinika", "root", "");
            System.out.println("polaczono");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ClinicController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    @FXML
    private void showPatients(){
        
            try {
            connect();
            pda.getPersonList();
            pda.shutdown();
        } catch (SQLException ex) {
            Logger.getLogger(ClinicController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
