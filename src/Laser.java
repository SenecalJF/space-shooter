import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


public class Laser extends Entite {

    private double vitesse = 0.5;


    public Laser(int x, int y, SpriteSheet spriteSheet) {
        super(x, y, spriteSheet, 0, 0);

    }

    public double getVitesse() {
        return vitesse;
    }

    public int getPositionY() {
        return y;
    }

    public int getPositionX() {
        return x;
    }
}
