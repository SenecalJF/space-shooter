import org.newdawn.slick.Image;

import java.util.Random;
import java.util.ArrayList;

public class Asteroide extends Entite implements Collisionnable {

    int directionAsteroide;
    double vitesseDescente;
    double deplacementY;
    Random random = new Random();


    public Asteroide(int x, int y, int width, int height, Image imagepath) {
        super(x, y, width, height, imagepath);
        int possVit = random.nextInt(4);
        switch (possVit) {
            case 0:
                this.vitesseDescente = 0.045;
                break;
            case 1:
                this.vitesseDescente = 0.050;        // 4 vitesses possibles
                break;
            case 2:
                this.vitesseDescente = 0.025;
                break;
            case 3:
                this.vitesseDescente = 0.010;
                break;
        }
        this.directionAsteroide = random.nextInt(2);
    }

    @Override
    public void update(int delta) {

        this.deplacementY += this.vitesseDescente * delta;
        if (deplacementY >= 1) {                            //ca permet des vitesses plus petites que 1 pixel/update
            this.y++;
            this.deplacementY = 0;
        }


        if (this.directionAsteroide == 0) {
            if (this.x >= MainClass.LARGEUR - this.width) {         // la hitbox des asteroides est tellement large que
                this.directionAsteroide = 1;                        // les collisions de bord d'ecran sont nazes
            } else {
                this.x += Math.round(delta * 0.08);
            }

        } else if (this.directionAsteroide == 1) {
            if (this.x <= 1) {
                this.directionAsteroide = 0;
            } else {
                this.x -= Math.round(delta * 0.08);
            }
        }
    }


}
