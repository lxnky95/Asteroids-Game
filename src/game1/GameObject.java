package game1;

import utilities.Vector2D;
import utilities.SoundManager;

import java.awt.*;

import static game1.Constants.*;


/**
 * Created by jlanke on 17/02/2017.
 */
public abstract class GameObject {

    public Vector2D position;
    public Vector2D velocity;
    public boolean dead;
    public double radius;
    public int lives;


    public GameObject(Vector2D pos, Vector2D vel, double rad){
        this.position = pos;
        this.velocity = vel;
        this.radius = rad;
        this.dead = false;
        lives = 1;
    }

    public GameObject(Vector2D pos, Vector2D vel, double rad,int lives){
        this.position = pos;
        this.velocity = vel;
        this.radius = rad;
        this.dead = false;
        this.lives = lives;
    }


    public void hit(){
        if(Ship.goodbonushit){
            if (this instanceof Ship) {
                this.lives ++;
            }
            if(this instanceof GoodBonuses){
                this.dead = true;

            }
        } else if(Ship.badbonushit){
            if(this instanceof BadBonuses){
            }
        } else{
            lives--;
            if(lives < 1) {
                this.dead = true;
                SoundManager.extraShip();
            }
        }
    }



    public GameObject() {}

    public boolean overlap(GameObject other){
        boolean overlap = this.position.dist(other.position) < this.radius + other.radius;
        return overlap;
   }


    public void collisionHandling(GameObject other) {
        if (this.getClass() != other.getClass() && this.overlap(other)) {
           this.hit();
            other.hit();
       }  }


    public void update(){
        position.addScaled(velocity,DT);
        position.wrap(FRAME_WIDTH,FRAME_HEIGHT);
    }

    public abstract void draw(Graphics2D xd);



}
