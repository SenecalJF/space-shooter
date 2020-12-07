import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import java.awt.*;
import java.sql.SQLOutput;

public class Entite {
    protected int x, y, width, height;
    protected Image image;
    protected boolean detruire = false;

    public Entite(int x, int y, int width, int height, Image imagepath) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = imagepath;
    }


    public Entite(int x, int y, SpriteSheet spriteSheet, int ligne, int columns) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = spriteSheet.getSubImage(ligne, columns);

    }

    public Image getImage() throws SlickException {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Rectangle getRectangle() {
        return new Rectangle(x, y, width, height);
    }

    public boolean getDetruire() {
        return detruire;
    }

    public void setDetruire() throws SlickException {
        //if (getDetruire())
        this.detruire = true;
    }

    public void update(int delta, int direction) {

    }

    public void update(int delta) {
    }
}
