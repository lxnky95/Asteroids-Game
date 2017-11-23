package game1;

/**
 * Created by jlanke on 20/01/2017.
 */

import utilities.SoundManager;
import utilities.Vector2D;

import java.awt.Graphics2D;

import static game1.Constants.*;

public class Asteroid extends GameObject{
    public static final double MAX_SPEED = 100;
    public static final double SMALL = 10;
    public static final double MEDIUM = 15;
    public static final double LARGE = 20;
    public int lifeSpan;

    public static Asteroid ast1 = null;
    public static Asteroid ast2 = null;
    public static Asteroid ast3 = null;

    @Deprecated
    private double x, y;
    @Deprecated
    private double vx, vy;

    public Asteroid(double x, double y, double vx, double vy,double rad, int life) {
        position = new Vector2D(x, y);
        velocity = new Vector2D(vx, vy);
        this.dead = false;
        this.radius = rad;
        this.lifeSpan = life;
    }


	public static Asteroid makeRandomAsteroid() {
	    double x = Math.random()  * FRAME_WIDTH;
        double y = Math.random() * FRAME_HEIGHT;
        double vx = ((Math.random() * 2) - 1 ) * MAX_SPEED;
        double vy = ((Math.random() * 2 ) -1 ) * MAX_SPEED;
	    Asteroid randomAsteroid = new Asteroid(x,y,vx,vy,LARGE,0);

        return randomAsteroid;
    }


    public void update() {
        position.x += velocity.x * DT;
        position.y += velocity.y * DT;
        position.x =(position.x + FRAME_WIDTH) % FRAME_WIDTH;
        position.y =(position.y + FRAME_HEIGHT) % FRAME_HEIGHT;
    }

    public void draw(Graphics2D g) {
        //g.setColor(Color.red);
        //g.fillOval((int) ( position.x - this.radius), (int) (position.y - this.radius),(int) ( 2 * this.radius),(int) (2 * this.radius));
        g.drawImage(ASTEROIDIMG,(int) ( position.x - this.radius), (int) (position.y - this.radius),(int) ( 2 * this.radius),(int) (2 * this.radius),null);
    }

    @Override
    public void hit(){
        this.dead = true;

        if(lifeSpan == 0){
            this.ast1 = new Asteroid(this.position.x,this.position.y,-this.velocity.x,this.velocity.y,MEDIUM,this.lifeSpan);
            this.ast2 = new Asteroid(this.position.x,this.position.y,this.velocity.x,-this.velocity.y,MEDIUM,this.lifeSpan);
            this.ast3 = new Asteroid(this.position.x,this.position.y,-this.velocity.x,-this.velocity.y,MEDIUM,this.lifeSpan);
            ast1.lifeSpan++;
            ast2.lifeSpan++;
            ast3.lifeSpan++;
            SoundManager.asteroidLarge();
        }
        else if(lifeSpan == 1){
            this.ast1 = new Asteroid(this.position.x,this.position.y,-this.velocity.x,this.velocity.y,SMALL,this.lifeSpan);
            this.ast2 = new Asteroid(this.position.x,this.position.y,this.velocity.x,-this.velocity.y,SMALL,this.lifeSpan);
            this.ast3 = new Asteroid(this.position.x,this.position.y,-this.velocity.x,-this.velocity.y,SMALL,this.lifeSpan);
            ast1.lifeSpan++;
            ast2.lifeSpan++;
            ast3.lifeSpan++;
            SoundManager.asteroids();
        }
        else{
            SoundManager.asteroidSmall();
        }

    }
}