package game1;

import utilities.JEasyFrame;
import utilities.SoundManager;

import java.util.ArrayList;
import java.util.List;

import static game1.Constants.DELAY;

/**
 * Created by jlanke on 20/01/2017.
 */
public class Game {
    public static int N_INITIAL_ASTEROIDS = 2;
    public List<GameObject> objects;
    public Ship ship;
    public Keys ctrl;
    public static int score;
    public int tempscore;
    public int asteroidsCount;
    public SoundManager sounds;
    static int safety = 150;



    public Game() {
        objects = new ArrayList<>();
        for (int i = 0; i < N_INITIAL_ASTEROIDS; i++) {
            objects.add(Asteroid.makeRandomAsteroid());
        }
        asteroidsCount = N_INITIAL_ASTEROIDS*13;
        ctrl = new Keys();
        ship = new Ship(ctrl);
        objects.add(ship);
        score = 0;
        this.sounds = new SoundManager();
        tempscore = 0;
    }

    public int getShipLives(){
        return ship.lives;
    }

    public int gainLife(){
        return ship.lives++;
    }

    public int safetyTimer(){
        if (safety > 0) {
            safety--;
        }
        return safety;
    }

    public int incScore(){
        score+= 100;
        return score;
    }



    public static void main(String[] args) throws Exception {
        Game game = new Game();
        View view = new View(game);
        new JEasyFrame(view, "Asteroids").addKeyListener(game.ctrl);
        while (true) {
            game.update();
            view.repaint();
            Thread.sleep(DELAY);
        }
    }

    private void update() {
        safetyTimer();
        List<GameObject> alive = new ArrayList<>();

        for (GameObject object: objects){
            object.update();
            if(!object.dead){
                alive.add(object);
            }
            else{
                if(object instanceof Asteroid){
                    incScore();
                    asteroidsCount--;
                    Ship.increaseShield();
                    //TODO remove debug print statement
                    System.out.println("Before: " + Integer.valueOf(asteroidsCount+1) + " After: " + asteroidsCount);
                }
                if(object instanceof Bullet){
                    Ship.decrementBullet();
                }

            }
        }

        if(asteroidsCount < 1){
            N_INITIAL_ASTEROIDS++;
            gainLife();
            asteroidsCount = N_INITIAL_ASTEROIDS*13;
            for (int i = 0; i < N_INITIAL_ASTEROIDS; i++) {
                alive.add(Asteroid.makeRandomAsteroid());
            }

        }

        if(Asteroid.ast1 != null || Asteroid.ast2 != null || Asteroid.ast3 != null){
            alive.add(Asteroid.ast1);
            alive.add(Asteroid.ast2);
            alive.add(Asteroid.ast3);
            Asteroid.ast1 = null;
            Asteroid.ast2 = null;
            Asteroid.ast3 = null;
        }
        if(ship.bullet!= null){
            alive.add(ship.bullet);
            ship.bullet = null;
        }
        synchronized (Game.class) {
            objects.clear();
            objects.addAll(alive);
        }

        if(score % 1000 == 0 && score != 0 && score != tempscore){
            tempscore = score;
            BadBonuses badNews = BadBonuses.makeRandomBadBonus();
            objects.add(badNews);
        }

        if(score % 500 == 0 && score != 0 && score != tempscore){
            tempscore = score;
            GoodBonuses goodNews = GoodBonuses.makeRandomGoodBonus();
            objects.add(goodNews);
        }


        for (int i = 0; i < objects.size(); i++){
            GameObject temp1 = objects.get(i);
            for (int j = 0; j < i; j++){
                GameObject temp2 = objects.get(j);
                if(ship.ctrl.action().shield  == false && safety == 0){
                    temp1.collisionHandling(temp2);
                }


            }
        }

    }
}