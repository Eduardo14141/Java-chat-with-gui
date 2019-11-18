package chat;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Chat_Server extends JFrame{
    
    private JButton return_btn,
            send_message_btn;
    private JTextArea chat_txt_area;
    private JTextField message_txt,
            text_area_txt;
    private JPanel main_panel;
    private JLabel contact_lbl;
    private Container container;
    private JScrollPane chat_scroll_pane;
    
    private ServerSocket socket_server;
    private Socket servers_socket;
    private BufferedReader reader_in;
    private PrintWriter print_out;
    
    protected Chat_Server(){
        initComponents();
        loadServer();
    }
    private void initComponents(){
        this.container = this.getContentPane();
        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Chat");
        this.setPreferredSize(new Dimension(900, 700));
        this.setMinimumSize(new Dimension(650, 550));
        this.setMaximumSize(new Dimension(1500, 1100));
        this.pack();
        this.setLocationRelativeTo(null);
        
        int width = container.getWidth();
        int height = container.getHeight();
        final Font font = new Font("Serif", Font.PLAIN, 16);
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.VERTICAL;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(15, 15, 15, 15);
        
        final EmptyBorder margin = new EmptyBorder(height/16, width/8, height/16, width/8);
        
        this.main_panel = new JPanel(new GridBagLayout());
        this.main_panel.setBorder(margin);
        initMainPanel(constraints, font);
        this.add(this.main_panel);
    }
    
    private void initMainPanel(GridBagConstraints constraints, Font font){
        constraints.gridx = 0;
        constraints.gridy = 0;
        
        initButtons(constraints, font);
        initContactUsername(constraints, font);
        initChatArea(constraints, font);
    }
    
    private void initButtons(GridBagConstraints constraints, Font font){
        //Inicializando el botón de regreso
        
        this.return_btn = new JButton();
        String return_icon_directory = "./media/return-icon.png";
        try {
            if(getClass().getResource(return_icon_directory) != null){
                Image return_icon = ImageIO.read(getClass().getResource(return_icon_directory));
                this.return_btn.setIcon(new ImageIcon(return_icon.getScaledInstance(35, 35, Image.SCALE_SMOOTH)));
                this.return_btn.setContentAreaFilled(false);
                this.return_btn.setBorderPainted(false);
                this.return_btn.setOpaque(false);
                this.return_btn.setFocusPainted(false);
                this.return_btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                this.return_btn.addActionListener(this::returnEventListener);
            }else{
                this.return_btn.setText("Regresar");
                this.return_btn.setFont(font);
            }                
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            System.out.println(Arrays.toString(ex.getStackTrace()));
        }
        this.main_panel.add(this.return_btn, constraints);
        
        constraints.gridx=3;
        constraints.gridy=6;
        
        this.send_message_btn = new JButton();
        try{
            String send_message_icon_directory = "./media/send-message-icon.png";
            if(getClass().getResource(send_message_icon_directory) != null){
                Image return_icon = ImageIO.read(getClass().getResource(send_message_icon_directory));
                this.send_message_btn.setIcon(new ImageIcon(return_icon.getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
                this.send_message_btn.setContentAreaFilled(false);
                this.send_message_btn.setBorderPainted(false);
                this.send_message_btn.setOpaque(false);
                this.send_message_btn.setFocusPainted(false);
                this.send_message_btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                this.send_message_btn.addActionListener(this::returnEventListener);
            }else{
                this.send_message_btn.setText("Enviar");
                this.send_message_btn.setFont(font);
            }
        }catch(IOException ex){
            System.out.println(ex.getMessage());
            System.out.println(Arrays.toString(ex.getStackTrace()));
        }
        this.main_panel.add(this.send_message_btn, constraints);
    }
    private void initContactUsername(GridBagConstraints constraints, Font font) {
        constraints.gridx = 1;
        constraints.gridy = 0;
        //constraints.gridwidth = 2;
        constraints.weightx = 1.;
        
        this.contact_lbl = new JLabel("Cliente");
        this.contact_lbl.setFont(font);
        this.main_panel.add(this.contact_lbl, constraints);
    }
    private void initChatArea(GridBagConstraints constraints, Font font) {
        constraints.weighty = 1.;
        constraints.gridwidth = 4;
        constraints.gridheight = 5;
        constraints.gridx = 0;
        constraints.gridy = 1;        
        this.chat_txt_area = new JTextArea(25, 15);
        this.chat_scroll_pane = new JScrollPane(this.chat_txt_area);
        this.main_panel.add(this.chat_scroll_pane, constraints);
        
        constraints.gridy=6;
        constraints.weightx = 1.;
        constraints.weighty = 0;
        constraints.gridwidth = 3;
        
        this.message_txt = new JTextField();
        this.message_txt.setFont(font);
        this.main_panel.add(this.message_txt, constraints);
    }
    private void returnEventListener(ActionEvent evt){
        String message = this.message_txt.getText();
        this.chat_txt_area.append("\nServidor: " + message);
        this.print_out.println(message);
        this.print_out.flush();
    }
    private void loadServer(){
        try {
            //Configurando sockets
            socket_server = new ServerSocket(8080);
            servers_socket = socket_server.accept();
            print_out = new PrintWriter(servers_socket.getOutputStream());
            reader_in = new BufferedReader(new InputStreamReader(servers_socket.getInputStream()));
            
            Thread read_data = new Thread(() -> {
                while(true){
                    String message;
                    try {
                        message = reader_in.readLine();
                        this.chat_txt_area.append("\nClient: " + message);
                    } catch (IOException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            read_data.start();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}