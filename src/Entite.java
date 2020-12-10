import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import java.awt.*;
import java.sql.SQLOutput;

public class Entite {
    protected int x, y, width, height;
    protected Image image;
    protected boolean detruire = false;

    /**
     * @param x         position en x de l'iamge
     * @param y         position en y de l'image
     * @param width     largeur de l'image
     * @param height    hauteur de l'image
     * @param imagepath Image
     */
    public Entite(int x, int y, int width, int height, Image imagepath) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = imagepath;
    }


    /**
     * @param x position en x de l'image
     * @param y position en y de l'image
     * @param spriteSheet
     * @param ligne # de la ligne dans le spritesheet
     * @param columns # de la colonne dans le spritesheet
     */
    public Entite(int x, int y, SpriteSheet spriteSheet, int ligne, int columns) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = spriteSheet.getSubImage(ligne, columns);

    }

    /**
     * @return l'image
     * @throws SlickException
     */
    public Image getImage() throws SlickException {
        return image;
    }

    /**
     * @return la position en x
     */
    public int getX() {
        return x;
    }

    /**
     * @return la position en y
     */
    public int getY() {
        return y;
    }

    /**
     * @return la largeur
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return la hauteur
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param x position en x update
     * @param y position en y update
     */
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return le rectangle que forme l'image
     */
    public Rectangle getRectangle() {
        return new Rectangle(x, y, width, height);
    }

    /**
     * @return si il faut le detruire
     */
    public boolean getDetruire() {
        return detruire;
    }

    /**
     * definir la destruction
     * @throws SlickException
     */
    public void setDetruire() throws SlickException {
        //if (getDetruire())
        this.detruire = true;
    }


    /**
     * @param delta sert comme valeur qui change
     */
    public void update(int delta) {
    }
}
