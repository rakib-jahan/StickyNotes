package stickynotes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DatabaseManager {

    Connection connection;

    public DatabaseManager() throws SQLException {
        connection = DBConnection.getDBConnection();
    }

    public List<Note> getNotes() throws SQLException {

        List<Note> notes = new ArrayList<Note>();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String query = "SELECT Id, Note, CreatedDate, ModifiedDate, IsActive FROM Notes WHERE IsActive = 1";

        try {

            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                notes.add(new Note(resultSet.getInt(1), resultSet.getString(2)));
            }

            return notes;

        } catch (SQLException e) {

        }

        return notes;
    }
    
    public int addNote(String note) throws SQLException {
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        String query = "INSERT INTO Notes (Note, CreatedDate, ModifiedDate, IsActive) VALUES(?,?,?,1)";

        try {

            String date = getCurrentDateTime();

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, note);
            preparedStatement.setString(2, date);
            preparedStatement.setString(3, date);
            preparedStatement.executeUpdate();
            rs = preparedStatement.getGeneratedKeys();
            if(rs != null && rs.next()){
                return rs.getInt(1);
            }

        } catch (SQLException e) {

        }
        return 0;
    }

    public void updateNote(int id, String note) throws SQLException {

        PreparedStatement preparedStatement = null;
        String query = "UPDATE Notes SET Note = ?, ModifiedDate = ? WHERE Id = ?";

        try {

            String date = getCurrentDateTime();

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, note);
            preparedStatement.setString(2, date);
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {

        }
    }

    public void deleteNote(int id) throws SQLException {

        PreparedStatement preparedStatement = null;
        String query = "UPDATE Notes SET IsActive = 0 WHERE Id = ?";

        try {

            String date = getCurrentDateTime();

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {

        }
    }

    private String getCurrentDateTime() {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        return dateFormat.format(date);
    }
}
