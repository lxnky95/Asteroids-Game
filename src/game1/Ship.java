package game1;

import utilities.Vector2D;
import utilities.SoundManager;

import java.awt.*;
import java.awt.geom.AffineTransform;

import static game1.Constants.*;

/**
 * Created by jlanke on 10/02/2017.
 */
public class Ship extends GameObject{

    public static final int RADIUS = 8;

    // rotation velocity in radians per second
    public static final double STEER_RATE = 2* Math.PI;

    // acceleration when thrust is applied
    public static final double MAG_ACC = 200;

    // constant speed loss factor
    public static final double DRAG = 0.01;

    public static final Color COLOR = Color.cyan;

    // per second
    // direction in which the nose of the ship is pointing
    // this will be the direction in which thrust is applied
    // it is a unit vector representing the angle by which the ship has rotated
    public Vector2D direction;

    // controller which provides an Action object in each frame
    public Controller ctrl;

    public static int bulletCount;

    public static boolean goodbonushit = false;
    public static boolean badbonushit = false;


    public Ship(Controller ctrl) {
        super();
        this.ctrl = ctrl;
        position = new Vector2D(FRAME_WIDTH/2,FRAME_HEIGHT/2);
        velocity = new Vector2D(0,0);
        direction = new Vector2D(0,-1);
        this.radius = RADIUS;
        this.dead = false;
        this.lives = shipLives;

    }

    public static int ShieldCounter = 100;

    public static void increaseShield() {
        if(ShieldCounter < 500){
            ShieldCounter += 20;
        }

    }

    public static void extraLife(){
       shipLives++;
    }


    static int shipLives = 3;

    public Bullet bullet = null;

    public void update()  {
        Action action = ctrl.action();
        direction.rotate(action.turn*STEER_RATE*DT);
        velocity.addScaled(direction,MAG_ACC * DT * action.thrust);
        velocity.mult(1-DRAG);
        position.addScaled(velocity,DT);
        position.wrap(FRAME_WIDTH,FRAME_HEIGHT);

        if (action.shoot){
            if(bulletCount < 5){
                mkBullet();
                bulletCount++;
            }
                action.shoot = false;
        }

        if(action.shield == true){
            if (ShieldCounter > 0) {
                ShieldCounter--;
            }
            if(ShieldCounter == 0){
                action.shield = false;
            }

            System.out.println(ShieldCounter);
        }
    }

    private void mkBullet(){
        Vector2D bulletPosition = new Vector2D(position).addScaled(direction, 20);
        bullet = new Bullet(bulletPosition,new Vector2D(velocity));
        bullet.velocity.addScaled(direction,BULLET_SPEED);
        bullet.position.addScaled(direction,RADIUS+1);
        SoundManager.fire();
    }

    public void draw(Graphics2D g){
        Action action = ctrl.action();
        AffineTransform at = g.getTransform();
        g.translate(position.x,position.y);
        double rot = direction.angle() + Math.PI / 2;
        g.rotate(rot);
        g.scale(DRAWING_SCALE, DRAWING_SCALE);
        g.setColor(COLOR);
        g.fillPolygon(XP,YP,XP.length);
        if(action.thrust == 1){
            g.setColor(Color.red);
            g.fillPolygon(XPTHRUST,YPTHRUST,XPTHRUST.length);
        }
        g.setTransform(at);
        if(action.shield == true){
            g.setColor(new Color(100,0,100,125));
            g.fillOval((int) (position.x-this.radius-14),(int) (position.y-this.radius-14),(int) (6*this.radius),(int) (6*this.radius));
        }

    }

    @Override
    public void collisionHandling(GameObject other) {
        if (this.getClass() != other.getClass() && this.overlap(other)) {
            if(GoodBonuses.class.isInstance (other)){
                goodbonushit = true;
                this.hit();
                other.hit();
                goodbonushit = false;
            } else if(BadBonuses.class.isInstance(other)){
                badbonushit = true;
                this.hit();
                other.hit();
                badbonushit = false;
            } else {
                this.hit();
                other.hit();
            }
        }
    }


    public static int decrementBullet(){
        bulletCount--;
        return bulletCount;
    }


}