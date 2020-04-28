package Database;

import Objects.Message;
import Objects.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class UserDbAction {
    
    
    /*
        EJEMPLO CON INSERT
    */
    public static int sendMessage(String message, int author, Date date){
        try{
            // "hola",  1
            try (Connection connection = DbConnection.getConnection()) {
                                                     // "hola",  1
                String query = "Insert into messages (message, author, date) values(?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query) ;
                statement.setString(1, message);
                statement.setInt(2, author);
                statement.setDate(3, date);
                // 0 if didn't work, 1 if successful
                return statement.executeUpdate();
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        //0 if failed
        return 0;
    }
    
    /*
        EJEMPLOS CON SELECT
    */
    
    public static User getUsername(int id){
        User user = new User();
        try{
            
            try (Connection connection = DbConnection.getConnection()) {
                String query = "Select username from users where id = ?";
                PreparedStatement prepared_statement = connection.prepareStatement(query);
                prepared_statement.setInt(1, id);
                
                ResultSet rs = prepared_statement.executeQuery();
                if(rs.next())
                    user.setUsername(rs.getString(0));
                //if there isn't data
                else
                    return null;
            }             
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        return user;
    }
    
    public static ArrayList<Message> getMessages(int id){
        ArrayList<Message> messages = new ArrayList();
        try{
            try (Connection connection = DbConnection.getConnection()) {
                
                String query = "Select message, date, user_id from messages where id = ?";
                PreparedStatement prepared_statement = connection.prepareStatement(query);
                prepared_statement.setInt(1, id);
                
                ResultSet rs = prepared_statement.executeQuery();
                
                while(rs.next()){
                    Message message = new Message();
                    
                    message.setMessage(rs.getString(0));
                    message.setDate(rs.getDate(1));
                    message.setUser(rs.getInt(2));
                    
                    messages.add(message);
                }
            }             
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        return messages;
    }
    
    /*
        EJEMPLOS CON DELETE
    */
    
    public static int DeleteUser (int user_id){
        try{
            try (Connection con = DbConnection.getConnection()) {
                String Q = "Delete FROM users where idCuenta=?";
                PreparedStatement ps = con.prepareStatement(Q);
                ps.setInt(1, user_id);
                // 1 if successful, 0 if failed
                return ps.executeUpdate();
            }
        }catch(SQLException d){
            System.out.println("Error al comprobar cuenta");
            System.out.println(d.getMessage());
            System.out.println(Arrays.toString(d.getStackTrace()));
        }       
        //if failed
        return 0;
    }
    
    /*
        EJEMPLOS CON UPDATE
    */
    
    public static int updateUsername(int id_user, String user){
        try{
            try (Connection con = DbConnection.getConnection()) {
                String Q="update users set username = ? where id_user = ?";
                PreparedStatement ps = con.prepareStatement(Q);
                ps.setString(1, user);
                ps.setInt(2, id_user);
                // 1 if successful, 0 if failed
                return ps.executeUpdate();
            }
        }catch(SQLException d){
            System.out.println("Hubo un error al Desactivar el comentario");
            System.out.println(Arrays.toString(d.getStackTrace()));
            System.out.println(d.getMessage());
        }
        return 0;
    }
    
}
