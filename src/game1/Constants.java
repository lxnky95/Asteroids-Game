package game1;

import java.awt.*;

/**
 * Created by jlanke on 20/01/2017.
 */
public class Constants {
    public static final int FRAME_HEIGHT = 480;
    public static final int FRAME_WIDTH = 640;
    public static final int DELAY = 20;  // in milliseconds
    public static final int BULLET_SPEED = 350;
    public static final double DT = DELAY / 1000.0;  // in seconds
    public static final Dimension FRAME_SIZE = new Dimension(
            Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
    public static final int DRAWING_SCALE = 5;
    public static final int[] XP = {3,0,-3,0};
    public static final int[] YP = {3,0,3,-3};
    public static final int[] XPTHRUST = {3,0,-3};
    public static final int[] YPTHRUST = {3,0,3};
    public static final Image SPACE = Sprite.MILKYWAY1;
    public static final Image ASTEROIDIMG = Sprite.ASTEROID1;

}
