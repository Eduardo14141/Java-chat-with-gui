package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbConnection {
    
    protected static Connection getConnection(){
        String url = "jdbc:mysql://localhost/database_name";
        Connection connection = null;
        
        Properties connection_properties = new Properties();
        
        connection_properties.setProperty("user", "root");
        connection_properties.setProperty("password", "123456");
        connection_properties.setProperty("charSet", "ISO-8859-1");
        
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(url, connection_properties);
            
            System.out.println("Succesfull connection to mysql");
        }catch (ClassNotFoundException | IllegalAccessException | InstantiationException e){
            System.out.println("Connection to Mysql failed at Source Packages/Database/DbConnection.java");
            System.out.println(e.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        
        return connection;
    }
    
}