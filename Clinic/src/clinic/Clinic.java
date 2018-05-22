/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinic;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Clinic extends Application {
    
    @Override
    public void start(Stage Stage) throws IOException {
       Parent root = FXMLLoader.load(getClass().getResource("RegisterWindow.fxml"));
       Scene scene = new Scene(root);
       Stage.setScene(scene);
       Stage.setTitle("OKNO LOGOWANIA");
       Stage.show(); 
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
