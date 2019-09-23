package stickynotes;

import java.awt.Dimension;
import java.awt.Toolkit;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StickyNotes extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("FXMLDocument.fxml").openStream());
        
//        FXMLDocumentController docController =
//                        (FXMLDocumentController) loader.getController();
        
        Dimension d= Toolkit.getDefaultToolkit().getScreenSize();
        stage.setX(d.width/2-(300/2));
        stage.setY(d.height/2-(300/2));        
        
        //docController.initController(d.width/2-(300/2), d.height/2-(300/2));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Sticky Notes");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
