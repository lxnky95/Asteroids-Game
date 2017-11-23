package game1;

import utilities.Vector2D;

import java.awt.*;

import static game1.Constants.DT;
import static game1.Constants.FRAME_HEIGHT;
import static game1.Constants.FRAME_WIDTH;
import static game1.Sprite.HEART1;


/**
 * Created by JLankester on 23/03/2017.
 */
public class GoodBonuses extends GameObject {

    public static final double MAX_SPEED = 100;
    public static final double SIZE = 10;
    public int goodttl = 5000;



    public GoodBonuses (double x, double y, double vx, double vy,double rad) {
        position = new Vector2D(x, y);
        velocity = new Vector2D(vx, vy);
        this.dead = false;
        this.radius = rad;
    }

    public void update() {
        position.x += velocity.x * DT;
        position.y += velocity.y * DT;
        position.x =(position.x + FRAME_WIDTH) % FRAME_WIDTH;
        position.y =(position.y + FRAME_HEIGHT) % FRAME_HEIGHT;

        if(goodttl > 0){
            position.addScaled(velocity,DT);
            position.wrap(FRAME_WIDTH,FRAME_HEIGHT);
            goodttl -= 10;
        }
        else{
            this.dead = true;
        }
    }

    public void draw(Graphics2D g0) {

        g0.drawImage(HEART1,(int) ( position.x - this.radius), (int) (position.y - this.radius),(int) ( 2 * this.radius),(int) (2 * this.radius),null);
    }

    public static GoodBonuses makeRandomGoodBonus() {
        double x = Math.random()  * FRAME_WIDTH;
        double y = Math.random() * FRAME_HEIGHT;
        double vx = ((Math.random() * 2) - 1 ) * MAX_SPEED;
        double vy = ((Math.random() * 2 ) -1 ) * MAX_SPEED;
        GoodBonuses randomGoodBonus = new GoodBonuses(x,y,vx,vy,SIZE);

        return randomGoodBonus;
    }


    @Override
    public void collisionHandling(GameObject other) {
        if (this.getClass() != other.getClass() && this.overlap(other) && !Asteroid.class.isInstance(other) && !BadBonuses.class.isInstance(other)) {
            if (Ship.class.isInstance(other)) {
                Ship.goodbonushit = true;
                this.hit();
                other.hit();
                Ship.goodbonushit = false;
            } else {
                this.hit();
                other.hit();
            }
        }


    }
}
