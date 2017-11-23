package game1;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/**
 * Created by jlanke on 03/02/2017.
 */
public class Keys extends KeyAdapter implements Controller {

    Action action;
    public Keys(){
        action = new Action();
    }



    public Action action() {
        // this is defined to compl
        // y with the standard interface
        return action;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP:
                action.thrust = 1;
                break;
            case KeyEvent.VK_LEFT:
                action.turn = -1;
                break;
            case KeyEvent.VK_RIGHT:
                action.turn = +1;
                break;
            case KeyEvent.VK_SPACE:
                action.shoot = true;
                break;
            case KeyEvent.VK_X:
                action.shield = true;
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_UP:
                action.thrust = 0;
                break;
            case KeyEvent.VK_LEFT:
                action.turn = 0;
                break;
            case KeyEvent.VK_RIGHT:
                action.turn = 0;
                break;
            case KeyEvent.VK_SPACE:
                action.shoot = false;
                break;
            case KeyEvent.VK_X:
                action.shield = false;
                break;


        }

        // please add appropriate event handling code
    }
}