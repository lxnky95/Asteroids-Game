package game1;

import utilities.Vector2D;

import java.awt.*;

import static game1.Constants.*;

/**
 * Created by jlanke on 24/02/2017.
 */
public class Bullet extends GameObject{
    public static final int RADIUS = 5;
    public int ttl = 1000;


    public Bullet(Vector2D position, Vector2D velocity) {
        this.position = position;
        this.velocity = velocity;
        this.dead = false;
        this.radius = RADIUS;
    }

    public void draw(Graphics2D g){
        g.setColor(Color.GREEN);
        g.fillOval((int) position.x - RADIUS, (int) position.y - RADIUS, 2 * RADIUS, 2 * RADIUS);
    }
    @Override
    public void update(){
        if(ttl > 0){
            position.addScaled(velocity,DT);
            position.wrap(FRAME_WIDTH,FRAME_HEIGHT);
            ttl -= 10;
        }
        else{
            this.dead = true;
        }

    }



}
