import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import java.awt.*;
import java.sql.SQLOutput;

public class Entite {
    protected int x, y, width, length;
    protected Image image;
    protected boolean detruire = false;


    public Entite(int x, int y, SpriteSheet spriteSheet, int ligne, int columns) {

        this.x = x;
        this.y = y;
        this.image = spriteSheet.getSubImage(ligne, columns);

    }

    public Image getImage() {
        return image;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Rectangle getRectangle() {
        return new Rectangle(x, y, width, length);
    }
}
