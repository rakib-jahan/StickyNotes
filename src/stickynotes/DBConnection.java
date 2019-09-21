package stickynotes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    
    static Connection connection = null;
    
    public static Connection getDBConnection() throws SQLException {
        try {
            if(connection == null)
            {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:temp.db");
            }            
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
	}
        return connection;
    }    
}
