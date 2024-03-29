/**
 * 
 */
package database.dbcontext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;




/**
 * 
 */
public class DbContext {
	
    private static DbContext instance;
    private static String url = "jdbc:mysql://localhost:3306/hipotecas";
    private static String usuario = "root";
    private static String contraseña = "Boank0d!";
    private Connection db;
    private PreparedStatement Statement = null;
    
    
    /**
     * Constructor
     */
    public DbContext() {
    	try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Error al cargar el controlador JDBC: " + e.getMessage());
        }
    }

    /**
     * Instancia la clase si no lo está SINGLETON syncronized
     * @return void
     */
    public synchronized DbContext getInstance() {
        if (instance == null) {
            instance = new DbContext();
        }
        return instance;
    }
    
    /**
     * Conexión a la base de datos
     * @return Connection
     * @throws SQLException
     */
    public Connection connect() throws SQLException {
        if (db == null || db.isClosed()) {
            try {
                db = DriverManager.getConnection(url, usuario, contraseña);
            } catch (SQLException e) {
            	
                throw new SQLException("Error al conectar a la base de datos", e);
            }
        }
        return db;
    }

    /**
     * Cierra la conexión a la base de datos
     * @throws SQLException
     */
    public void closeConnection() throws SQLException {
        if (db != null && !db.isClosed()) {
            db.close();
        }
    }

}

