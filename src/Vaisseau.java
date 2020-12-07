import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Vaisseau extends Entite {
    private float vaisseauX = 266;
    private float vaisseauY = 500;
    private int Hauteur = MainClass.HAUTEUR;
    private int Largeur = MainClass.LARGEUR;
    private Image imageVaisseau = new Image("Images/shiper_mix_03.png");


    public Vaisseau(int x, int y, int width, int height, Image imagepath) throws SlickException {
        super(x, y, width, height, imagepath);
    }


    public int deplacementVaisseauX(boolean moving, int direction, int delta) {

        if (moving) {
            switch (direction) {
                case 1:
                    //gauche
                    if (vaisseauX <= 0) {
                        vaisseauX = 0;
                    } else
                        this.vaisseauX -= 0.5 * delta;
                    break;
                case 3:
                    //droite
                    if (vaisseauX >= Largeur - imageVaisseau.getWidth()) {
                        vaisseauX = Largeur - imageVaisseau.getWidth();
                    } else
                        this.vaisseauX += 0.5 * delta;
                    break;
            }
        }
        return Math.round(vaisseauX);
    }

    public int deplacementVaisseauY(boolean moving, int direction, int delta) {

        if (moving) {
            switch (direction) {
                case 0:
                    //haut
                    if (vaisseauY <= 0) {
                        vaisseauY = 0;
                    } else
                        vaisseauY -= 0.5 * delta;
                    break;
                case 2:
                    //bas
                    if (vaisseauY >= Hauteur - imageVaisseau.getHeight()) {
                        vaisseauY = Hauteur - imageVaisseau.getHeight();
                    } else
                        vaisseauY += 0.5 * delta;
                    break;
            }
        }
        return Math.round(vaisseauY);
    }

    @Override
    public void setLocation(int x, int y) {
        super.setLocation(x, y);
    }
}

