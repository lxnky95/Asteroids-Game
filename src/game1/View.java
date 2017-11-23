package game1;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

import static game1.Sprite.GAMEOVER1;

/**
 * Created by jlanke on 20/01/2017.
 */
public class View extends JComponent {
    public static final Color BG_COLOR = Color.black;

    Action action;

    private Game game;
    Image im = Constants.SPACE;
    AffineTransform bgTransf;


    public View(Game game){
        action = new Action();
        this.game = game;
        double imWidth = im.getWidth(null);
        double imHeight = im.getHeight(null);
        double stretchx = (imWidth > Constants.FRAME_WIDTH? 1 :
                Constants.FRAME_WIDTH/imWidth);
        double stretchy = (imHeight > Constants.FRAME_HEIGHT? 1 :
                Constants.FRAME_HEIGHT/imHeight);
        bgTransf = new AffineTransform();
        bgTransf.scale(stretchx, stretchy);
    }

    public Action action() {
        // this is defined to compl
        // y with the standard interface
        return action;
    }


    @Override
    public void paintComponent(Graphics g0){
        Graphics2D g = (Graphics2D) g0;
        g.drawImage(im,bgTransf,null);
        Graphics2D g2 = (Graphics2D) g0;
        g2.setColor(Color.WHITE);
        g2.drawString("Score :"+ " " +game.score,550,15);
        g2.drawString("Lives :"+ " " +game.getShipLives(),20,15);
        g2.drawString("Shield Fuel :"  + Ship.ShieldCounter,250,15);
        if(Game.safety > 0){
            g2.drawString("You are safe!", 100,15);
        }
        if(game.getShipLives() <= 0){
            g.drawImage(GAMEOVER1,(Constants.FRAME_WIDTH/2 - GAMEOVER1.getWidth(null)/2), Constants.FRAME_HEIGHT/2 - GAMEOVER1.getHeight(null)/2,null);

        }
        synchronized (Game.class) {
            for (GameObject object : game.objects) {
                object.draw(g);
            }
        }



    }

    @Override
    public Dimension getPreferredSize(){
        return Constants.FRAME_SIZE;
    }
}
