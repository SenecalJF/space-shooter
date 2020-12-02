import org.newdawn.slick.Image;

import java.util.ArrayList;

public class Asteroide extends Entite {

    public Asteroide(int x, int y, int width, int height, Image imagepath) {
        super(x, y, width, height, imagepath);

    }

    @Override
    public void update(int delta, int direction) {
        if (x > MainClass.LARGEUR - 120) {
            direction = 1;
        }

        if (x < 50) {
            direction = 3;
        }


        if (y > MainClass.HAUTEUR - 100) {
            direction = 0;
        }

        if (y < 50) {
            direction = 2;
        }

        if (direction == 2) {
            y -= 0.1 * delta;
        } else if (direction == 0) {
            y += 0.1 * delta;
        }

        if (direction == 1) {
            x -= 0.1 * delta;
        } else if (direction == 3) {
            x += 0.1 * delta;
        }


    }
}
