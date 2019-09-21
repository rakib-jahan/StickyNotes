package stickynotes;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FXMLDocumentController implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private Label label;

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException, SQLException {
        DatabaseManager db = new DatabaseManager();
        int id = db.addNote("");
        noteInitializer(id, "");
    }

    private void noteInitializer(int id, String note) {
        try {

            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("FXMLNote.fxml").openStream());
            stage.initStyle(StageStyle.TRANSPARENT);

            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });

            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    stage.setX(event.getScreenX() - xOffset);
                    stage.setY(event.getScreenY() - yOffset);
                }
            });

            FXMLNoteController noteController = (FXMLNoteController) loader.getController();
            noteController.initNote(id, note);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            DatabaseManager db = new DatabaseManager();
            List<Note> notes = new ArrayList<>();
            notes = db.getNotes();

            if (notes.size() > 0) {
                for (Note note : notes) {
                    noteInitializer(note.id, note.note);
                }
            } else {
                int id = db.addNote("");
                noteInitializer(id, "");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}