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

    public Image getImage() throws SlickException {
        return (new SpriteSheet("Images/beams.png", 25, 25)).getSubImage(0, 0);
    }


}
