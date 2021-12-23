import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;


public class Laser extends Entite implements Collisionnable {

    private double vitesse = 0.2;
    private Vector2f position;
    int yDepart = 0;


    /**
     * @param x           position en x du laser
     * @param y           position en y du laser
     * @param spriteSheet
     * @param yDepart     la position en y du départ de laser
     */
    public Laser(int x, int y, SpriteSheet spriteSheet, int yDepart) {
        super(x, y, spriteSheet, 0, 0);
        position = new Vector2f(x, y);
        this.yDepart = yDepart;
    }

    /**
     * @return la vitesse du laser
     */
    public double getVitesse() {
        return vitesse;
    }

    /**
     * @return sprite sheet du laser
     * @throws SlickException
     */
    public Image getImage() throws SlickException {
        return (new SpriteSheet("Images/beams.png", 25, 25)).getSubImage(0, 0);
    }

    /**
     * @param width largeur du laser
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @param height hauteur du laser
     */
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
