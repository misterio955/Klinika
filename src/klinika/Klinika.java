/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klinika;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author student
 */
public class Klinika extends Application {
    
    @Override
    public void start(Stage Stage) throws IOException {
       Parent root = FXMLLoader.load(getClass().getResource("Logowanie.fxml"));
       Scene scene = new Scene(root);
       Stage.setScene(scene);
       Stage.setTitle("OKNO LOGOWANIA");
       Stage.show();
       
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
