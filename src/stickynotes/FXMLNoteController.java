package stickynotes;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class FXMLNoteController implements Initializable {

    private int tempId;

    @FXML
    private TextArea textNote;

    @FXML
    private Label labelUpdate;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        textNote.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("textfield changed from " + oldValue + " to " + newValue);

            try {
                //            labelUpdate.setText("Updating ...");
                //            System.out.println("Updating ...");
                updateNote(tempId, newValue);
                //            labelUpdate.setText("Updated");
                //            System.out.println("Updated");
            } catch (SQLException ex) {
                Logger.getLogger(FXMLNoteController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @FXML
    void deleteNote(MouseEvent event) throws SQLException {
        Alert a = new Alert(AlertType.NONE,
                "Do you want to delete this note ?", ButtonType.APPLY);

        Optional<ButtonType> result = a.showAndWait();

        if (result.get() == ButtonType.APPLY) {
            DatabaseManager db = new DatabaseManager();
            db.deleteNote(tempId);

            final Node source = (Node) event.getSource();
            final Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }
    }

    private void updateNote(int id, String note) throws SQLException {
        DatabaseManager db = new DatabaseManager();
        db.updateNote(id, note);
    }

    void initNote(int id, String note) {
        tempId = id;
        textNote.setText(note);
        //labelUpdate.setText("Updated");
    }
}