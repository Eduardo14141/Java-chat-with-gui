package Objects;

import java.sql.Date;

public class Message {
    
    private int id;
    private String message;
    private Date date;
    private int user_id;
    
    public Message(){ }

    public Message(int id, String message, Date date, int user_id) {
        this.id = id;
        this.message = message;
        this.date = date;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getUser() {
        return user_id;
    }

    public void setUser(int author) {
        this.user_id = author;
    }
        
}
