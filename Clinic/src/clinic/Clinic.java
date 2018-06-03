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
    static Stage window;
    @Override
    public void start(Stage stage) throws IOException {
        window = stage;
        Parent root = FXMLLoader.load(getClass().getResource("LoginWindow.fxml"));
        Scene scene = new Scene(root);
        window.setScene(scene);
        //Stage.setTitle("OKNO LOGOWANIA");
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
