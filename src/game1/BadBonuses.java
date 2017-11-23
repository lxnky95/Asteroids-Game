package game1;

import utilities.Vector2D;

import static game1.Constants.*;
import static game1.Sprite.SKULL1;

import java.awt.Graphics2D;

/**
 * Created by JLankester on 23/03/2017.
 */
public class BadBonuses extends GameObject {

    public static final double MAX_SPEED = 100;
    public static final double SIZE = 10;
    public int badttl = 5000;



    public BadBonuses (double x, double y, double vx, double vy,double rad) {
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

        if(badttl > 0){
            position.addScaled(velocity,DT);
            position.wrap(FRAME_WIDTH,FRAME_HEIGHT);
            badttl -= 10;
        }
        else{
            this.dead = true;
        }
    }

    public void draw(Graphics2D g0) {

        g0.drawImage(SKULL1,(int) ( position.x - this.radius), (int) (position.y - this.radius),(int) ( 2 * this.radius),(int) (2 * this.radius),null);


    }

    public static BadBonuses makeRandomBadBonus() {
        double x = Math.random()  * FRAME_WIDTH;
        double y = Math.random() * FRAME_HEIGHT;
        double vx = ((Math.random() * 2) - 1 ) * MAX_SPEED;
        double vy = ((Math.random() * 2 ) -1 ) * MAX_SPEED;
        BadBonuses randomBonus = new BadBonuses(x,y,vx,vy,SIZE);

        return randomBonus;
    }

    @Override
    public void hit(){
        this.dead = true;
        Game.score -=1000;
    }

    @Override
    public void collisionHandling(GameObject other) {
        if (this.getClass() != other.getClass() && this.overlap(other) && !GoodBonuses.class.isInstance(other) && !Asteroid.class.isInstance(other)) {
            if (Ship.class.isInstance(other)) {
                Ship.badbonushit = true;
                this.hit();
                other.hit();
                Ship.badbonushit = false;
            } else {
                this.hit();
                other.hit();
            }
        }  }



}
