import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class Vaisseau extends Entite implements Collisionnable {
    private float vaisseauX = 266;
    private float vaisseauY = 500;
    private int Hauteur = MainClass.HAUTEUR;
    private int Largeur = MainClass.LARGEUR;
    private Image imageVaisseau = new Image("Images/shiper_mix_03.png");
    private int nbVie = 3;
    private int nbRecolte = 0;
    private int score = 0;
    private boolean invincible = false;
    private double invincibleTimeout;
    Image healthBar3 = new Image("Images/healthBar/BAR3.png");
    Image healthBar2 = new Image("Images/healthBar/BAR2.png");
    Image healthBar1 = new Image("Images/healthBar/BAR1.png");
    Image recolteBar1 = new Image("Images/recolteBar/BAR1.png");
    Image recolteBar2 = new Image("Images/recolteBar/BAR2.png");
    Image recolteBar3 = new Image("Images/recolteBar/BAR3.png");
    Image recolteBar4 = new Image("Images/recolteBar/BAR4.png");
    Image recolteBar5 = new Image("Images/recolteBar/BAR5.png");

    /**
     * @param x         position en x du vaisseau
     * @param y         position en y du vaisseau
     * @param width     largeur du vaisseau
     * @param height    largeur du vaisseau
     * @param imagepath image du vaisseau
     * @throws SlickException
     */
    public Vaisseau(int x, int y, int width, int height, Image imagepath) throws SlickException {
        super(x, y, width, height, imagepath);
    }


    /**
     * @param moving true si le vaisseau bouge
     * @param direction du vaisseau; 1 = gauche, 3 = droite
     * @param delta valeur pour le déplacement
     * @return la potision en x du vaisseau
     */
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


    /**
     * @param moving true si le vaisseau bouge
     * @param direction du vaisseau; 0 = haut, 2 = bas
     * @param delta valeur du déplacement
     * @return la position em y du vaisseau
     */
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


    /**
     * @return image de la barre de vie adequate
     */
    public Image getHealthBar() {
        if (this.nbVie == 3) {
            return healthBar3;
        } else if (this.nbVie == 2) {
            return healthBar2;
        } else if (this.nbVie == 1) {
            return healthBar1;
        } else if (this.nbVie <= 0) {

            return null;
        }

        return healthBar1;
    }

    /**
     *  Diminue le nombre de vie
     */
    public void perteVie() {
        if (!invincible) {
            this.nbVie--;
            invincible = true;

        }

    }

    /**
     * @param delta valeur de deplacement
     */
    public void perteInvincibilite(int delta) {
        if (invincible) {
            invincibleTimeout += 0.002 * delta;
        }
        if (invincibleTimeout > 1) {
            invincibleTimeout = 0;
            invincible = false;
            System.out.println("invincible over");
        }
    }

    /**
     * @return Image correspondant à la récolte actuelle
     */
    public Image getRecolteBar() {
        if (this.nbRecolte == 0) {
            return null;
        } else if (this.nbRecolte <= 3250) {
            return recolteBar1;
        } else if (this.nbRecolte <= 6500) {
            return recolteBar2;
        } else if (this.nbRecolte <= 9750) {
            return recolteBar3;
        } else if (this.nbRecolte <= 13000) {
            return recolteBar4;
        } else if (this.nbRecolte > 13000) {
            return recolteBar5;
        }
        return recolteBar1;
    }


    /**
     * @param mineraiCollecte le nombre de minerai collecté en pixel carre
     */
    public void gainRecolte(int mineraiCollecte) {
        this.nbRecolte += mineraiCollecte;
        if (this.nbRecolte >= 16384) {
            this.nbRecolte = 16384;
        }
    }


    /**
     * Vide les ressources et ajoute au score
     */
    public void vidageRecolte() {
        int recoltesVidees = this.nbRecolte;
        this.nbRecolte = 0;
        this.score += recoltesVidees;
    }


    /**
     * @return score actuelle
     */
    public int getScore() {
        return score;
    }

    /**
     * @return le nombre recolte
     */
    public int getNbRecolte() {
        return nbRecolte;
    }

    /**
     * @return le nombre de vie
     */
    public int getNbVie() {
        return nbVie;
    }

    @Override
    public void setLocation(int x, int y) {
        super.setLocation(x, y);
    }
}

