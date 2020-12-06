import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;


public class Laser extends Entite implements Collisionnable {

    private double vitesse = 0.5;
    private Vector2f position;
    int yDepart = 0;


    public Laser(int x, int y, SpriteSheet spriteSheet, int yDepart) {
        super(x, y, spriteSheet, 0, 0);
        position = new Vector2f(x, y);
        this.yDepart = yDepart;
    }

    public double getVitesse() {
        return vitesse;
    }

    public Image getImage() throws SlickException {
        return (new SpriteSheet("Images/beams.png", 25, 25)).getSubImage(0, 0);
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public void update(int delta) {
        y -= vitesse * delta;
    }

    @Override
    public boolean getDetruire() {
        if (y <= 0 || yDepart - y >= (MainClass.HAUTEUR / 2)) {

            detruire = true;
        }
        return detruire;
    }


}
