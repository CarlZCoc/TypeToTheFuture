package main;

import javax.swing.*;
import java.awt.*;

public class Login {

    private GameFrame gFrame;

    private Font f1 = new Font("Arial", Font.BOLD, 100);
    private Font f2 = new Font("Arial", Font.BOLD, 10);

    private String title;
    private String usernameString;
    private String passwordString;

    private JTextField username;
    private JPasswordField password;

    private Rectangle enter;
    private Rectangle back;

    public Login(GameFrame gf) {
        this.gFrame = gf;

        title = "Login";
        usernameString = "Username: ";
        passwordString = "Password: ";

        username = new JTextField(10);
        username.setBounds(0, 100, 100, 50);
        username.setVisible(true);

        password = new JPasswordField(10);
        password.setBounds(0, 200, 100, 50);
        password.setVisible(true);

        back = new Rectangle(0, 300, 100, 50);
        enter = new Rectangle(0, 400, 100, 50);

    }//end constructor Login()

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.BLACK);

        g2d.setFont(f1);
        g2d.drawString(title, gFrame.getWidth()/5, gFrame.getHeight()/5);

        g2d.setFont(f2);
        g2d.drawString(usernameString, gFrame.getWidth()/5, gFrame.getHeight()/5);
        g2d.drawString(passwordString, gFrame.getWidth()*2/5, gFrame.getHeight()*2/5);
        g2d.fill(back);
        g2d.fill(enter);


    }//end render(Graphics g)

    public JTextField getUsername() {
        return username;

    }//end getUsername()

    public JTextField getPassword() {
        return password;

    }//end getPassword

    public Rectangle getBack() {
        return back;

    }//end getBack()

    public Rectangle getEnter() {
        return enter;

    }//end getEnter()

}//end class Login
