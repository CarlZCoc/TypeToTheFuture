package main;

import java.awt.*;

public class SignIn {

    private GameFrame gFrame;

    private Font f1 = new Font("Arial", Font.BOLD, 100);
    private Font f2 = new Font("Arial", Font.BOLD, 10);

    private String title;
    private String message;

    private Rectangle login;
    private Rectangle create;

    public SignIn(GameFrame gf) {
        this.gFrame = gf;

        title = "Type to the Future";
        message = "Don't have an account?";

        login = new Rectangle(this.gFrame.getWidth()*2/5, this.gFrame.getHeight()*2/5, 50, 50);
        create = new Rectangle(this.gFrame.getWidth()*2/5, this.gFrame.getHeight()*3/5, 50, 50);

    }//end constructor SignIn()

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.BLACK);

        g2d.setFont(f1);
        g2d.drawString(title, gFrame.getWidth()/5, gFrame.getHeight()/5);

        g2d.setFont(f2);
        g2d.drawString(message, gFrame.getWidth()*2/5, gFrame.getHeight()*2/5);

        g2d.fill(login);
        g2d.fill(create);

    }//end render()

    public Rectangle getLogin() {
        return login;

    }//end getLogin()

    public Rectangle getCreate() {
        return create;

    }//end getCreate()

}//end class SignIn
