package stickynotes;

import java.awt.Dimension;
import java.awt.Toolkit;
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

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FXMLDocumentController implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;

    private float tempX = 0;
    private float tempY = 0;

    private float tempXadd = 0;
    private float tempYadd = 0;

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException, SQLException {
        DatabaseManager db = new DatabaseManager();
        int id = db.addNote("");
        tempXadd -= 300;
        noteInitializer(id, "", tempXadd, tempYadd);
    }

    private void noteInitializer(int id, String note, float x, float y) {
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

            stage.setX(x);
            stage.setY(y);

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

            Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
            tempX = d.width / 2 - (300 / 2);
            tempY = d.height / 2 - (300 / 2);

            tempXadd = tempX;
            tempYadd = tempY;

            if (notes.size() > 0) {
                for (Note note : notes) {
                    tempX += 300;
                    noteInitializer(note.id, note.note, tempX, tempY);
                }
            } else {
                int id = db.addNote("");
                tempX += 300;
                noteInitializer(id, "", tempX, tempY);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void initController(float x, float y) {
        tempX = x;
        tempY = y;
    }
}
