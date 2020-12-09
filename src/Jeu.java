import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.*;

public class Jeu extends BasicGame {

    private int vaisseauX = 266;
    private int vaisseauY = 500;
    private int laserX;
    private int laserY;
    private int Hauteur = MainClass.HAUTEUR;
    private int Largeur = MainClass.LARGEUR;
    private GameContainer gc;
    Vaisseau vaisseau;
    private ArrayList<Entite> listeEntite = new ArrayList<>();
    private ArrayList<Destructible> listeDestructible = new ArrayList<>();
    private ArrayList<Collisionnable> listeCollisionnable = new ArrayList<>();
    private ArrayList<Image> listeVie = new ArrayList<>();
    private ArrayList<Image> listeRecolte = new ArrayList<>();
    private Image imageVaisseau;
    private Image imageBackground;
    private Image imageBackground2;
    private Image healthBar3, healthBar2, healthBar1;
    private Image recolteBar1, recolteBar2, recolteBar3, recolteBar4, recolteBar5;

    private int deplacementImage = 0;
    private int imgBackHeight = 0;
    private int deplacementImage2 = 0;
    private boolean descendre = false;
    private Image laser1;
    private Image asteroidL, asteroidm, asteroidS, asteroidXS;
    private SpriteSheet spriteSheetLaser;
    private SpriteSheet spriteSheetAstroid;
    private double nouvelAsteroideReady;

    public boolean moving = false;
    private boolean shooting = false;
    public int directionVaisseau = 2;
    private int directionAsteroid = 1;


    public Jeu(String title) throws SlickException {
        super(title);
    }

    private Laser laser;
    private int yDepart = 0;
    private Asteroide asteroide;

    private Random random = new Random();


    @Override
    public void init(GameContainer gameContainer) throws SlickException {


        this.imageVaisseau = new Image("Images/vaisseau.png"); //devrait marcher pour toi aussi ce repertory
        this.vaisseau = new Vaisseau(vaisseauX, vaisseauY, imageVaisseau.getWidth(), imageVaisseau.getHeight(), imageVaisseau);

        listeEntite.add(vaisseau);
        //listeCollisionnable.add(vaisseau);
        this.imageBackground = new Image("Images/120_Attract.png");
        imgBackHeight = imageBackground.getHeight();
        this.imageBackground2 = new Image("Images/120_Attract.png");
        this.asteroidL = new Image("Images/asteroidS/large/Grand0.png");
        this.asteroidm = new Image("Images/asteroidS/medium/Moyen0.png");
        this.asteroidS = new Image("Images/asteroidS/small/Small0.png");
        this.asteroidXS = new Image("Images/asteroidS/Xsmall/Xsmall0.png");

        System.out.println("largeur L : " + asteroidL.getWidth());
        System.out.println("hauteur L: " + asteroidL.getHeight());

        System.out.println("largeur M : " + asteroidm.getWidth());
        System.out.println("hauteur M: " + asteroidm.getHeight());

        System.out.println("largeur S : " + asteroidS.getWidth());
        System.out.println("hauteur S: " + asteroidS.getHeight());

        /*
        listeVie.add(this.healthBar3 = new Image("Images/healthBar/BAR3.png"));
        listeVie.add(this.healthBar2 = new Image("Images/healthBar/BAR2.png"));
        listeVie.add(this.healthBar1 = new Image("Images/healthBar/BAR1.png"));
        listeRecolte.add(this.recolteBar1 = new Image("Images/recolteBar/BAR1.png"));
        listeRecolte.add(this.recolteBar2 = new Image("Images/recolteBar/BAR2.png"));
        listeRecolte.add(this.recolteBar3 = new Image("Images/recolteBar/BAR3.png"));
        listeRecolte.add(this.recolteBar4 = new Image("Images/recolteBar/BAR4.png"));
        listeRecolte.add(this.recolteBar5 = new Image("Images/recolteBar/BAR5.png"));
        */

        //J'ai mit la vie et la recolte dans la classe vaisseau, les methodes getHealth et getRecolte vont retourner les images

        /*for (int i = 0; i <= 3; i++) {
            spawnAsteroideRandom();
        }
*/
        spawnAsteroideLarge();
        spawnAsteroideLarge();
        spawnAsteroideLarge();

        spriteSheetLaser = new SpriteSheet("Images/beams.png", 25, 25);


    }

    @Override
    public void update(GameContainer gameContainer, int delta) throws SlickException {

        this.vaisseauX = this.vaisseau.deplacementVaisseauX(this.moving, directionVaisseau, delta);
        this.vaisseauY = this.vaisseau.deplacementVaisseauY(this.moving, directionVaisseau, delta);
        this.vaisseau.setLocation(vaisseauX, vaisseauY);
        // J'ai change pour que les deplacements soient traites individuellement
        // Parce que sinon les deplacements d'asteroides et du vaisseau se derangeaient

        this.vaisseau.perteInvincibilite(delta);

        if (spawnAsteroideReady(delta)) {
            spawnAsteroideRandom();
        }

        for (Entite entite : listeEntite) {
            if (entite instanceof Laser) {
                entite.update(delta);          //J'ai enleve la direction des laser puisque c'est tjs vers le haut anyways
            }

            if (entite instanceof Asteroide) {
                entite.update(delta); //deplacement des asteroides est dans classe asteroide

            }
        }


    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        //background
        //tentative de loop qui marche pas


        graphics.drawImage(imageBackground, 0, deplacementImage);
        /*if (descendre) {
            graphics.drawImage(imageBackground, 0, deplacementImage2);

        }*/


        // vaisseau spatial
        graphics.drawImage(imageVaisseau, vaisseauX, vaisseauY);

        if (this.vaisseau.getHealthBar() == null) {
        } else {
            graphics.drawImage(this.vaisseau.getHealthBar(), 30, 650);
        }

        if (this.vaisseau.getRecolteBar() == null) {
        } else {
            graphics.drawImage(this.vaisseau.getRecolteBar(), 450, 650);
        }
        graphics.drawString("Capacite : " + Integer.toString(this.vaisseau.getNbRecolte()), 450, 625);
        graphics.drawString("Score : " + Integer.toString(this.vaisseau.getScore()), 0, 25);

        for (int i = 0; i < listeEntite.size(); i++) {
            Entite entite = listeEntite.get(i);
            graphics.drawImage(entite.getImage(), entite.getX(), entite.getY());
            // if (entite instanceof Laser) {
            if (entite.getDetruire()) {
                listeEntite.remove(i);
            }
            // }
            collision(graphics);

        }
    }


    public boolean spawnAsteroideReady(int delta) {
        double tempsRecharge = 0.00015;
        nouvelAsteroideReady += tempsRecharge * delta;     //temps de recharge change le temps entre le spawn d'asteroide
        if (nouvelAsteroideReady >= 1) {
            nouvelAsteroideReady = 0;
            return true;
        }
        return false;
    }

    public void spawnAsteroideRandom() {
        int tailleRandom = random.nextInt(3);
        switch (tailleRandom) {
            case 0:
                spawnAsteroideLarge();
                break;
            case 1:
                spawnAsteroideMedium();
                break;
            case 2:
                spawnAsteroideSmall();
                break;
        }

    }

    public void spawnAsteroideLarge() {
        int spawnX = random.nextInt(MainClass.LARGEUR / 2) + MainClass.LARGEUR / 4;
        int spawnY = random.nextInt(100);
        listeEntite.add(new Asteroide(spawnX, spawnY, asteroidL.getWidth(), asteroidL.getHeight(), asteroidL));
        listeCollisionnable.add(new Asteroide(spawnX, spawnY, asteroidL.getWidth(), asteroidL.getHeight(), asteroidL));
    }

    public void spawnAsteroideMedium() {
        int spawnX = random.nextInt(MainClass.LARGEUR / 2) + MainClass.LARGEUR / 4;
        int spawnY = random.nextInt(100);
        listeEntite.add(new Asteroide(spawnX, spawnY, 128, 128, asteroidm));
        listeCollisionnable.add(new Asteroide(spawnX, spawnY, 128, 128, asteroidm));
    }

    public void spawnAsteroideSmall() {
        int spawnX = random.nextInt(MainClass.LARGEUR / 2) + MainClass.LARGEUR / 4;
        int spawnY = random.nextInt(100);
        listeEntite.add(new Asteroide(spawnX, spawnY, asteroidS.getWidth(), asteroidS.getHeight(), asteroidS));
        listeCollisionnable.add(new Asteroide(spawnX, spawnY, asteroidS.getWidth(), asteroidS.getHeight(), asteroidS));
    }


    public void collision(Graphics graphics) throws SlickException {

        Entite laser = null;
        Entite asteroid = null;
        Entite vaisseau = null;
        for (int i = 0; i < listeEntite.size(); i++) {
            for (int j = 0; j < listeEntite.size(); j++) {
                if (i != j) {
                    if (listeEntite.get(i) instanceof Laser && listeEntite.get(j) instanceof Asteroide) {
                        laser = listeEntite.get(i);
                        asteroid = listeEntite.get(j);
                        if (laser.getRectangle().intersects(asteroid.getRectangle())) {
                            if (asteroid.getWidth() > 32)
                                listeEntite.get(i).setDetruire();
                            separatioAsteroid(j, i);
                        }
                    }
                    if (listeEntite.get(i) instanceof Vaisseau && listeEntite.get(j) instanceof Asteroide) {
                        vaisseau = listeEntite.get(i);
                        asteroid = listeEntite.get(j);
                        if (vaisseau.getRectangle().intersects(asteroid.getRectangle())) {
                            int aireAsteroid = (asteroid.getWidth()) * (asteroid.getHeight());
                            int aireVaisseau = (vaisseau.getHeight()) * (vaisseau.getWidth());
                            if (aireAsteroid < aireVaisseau) {
                                listeEntite.get(j).setDetruire();
                                this.vaisseau.gainRecolte(aireAsteroid / 2);

                            } else if (aireAsteroid >= aireVaisseau) {
                                listeEntite.get(j).setDetruire();
                                this.vaisseau.perteVie();

                            }
                        }
                    }
                }
            }

        }
    }

    public void separatioAsteroid(int j, int i) throws SlickException {
        if (listeEntite.get(j).getImage() == asteroidL) {
            listeEntite.add(new Asteroide(listeEntite.get(j).getX() + 50, listeEntite.get(j).getY(), 128, 128, asteroidm));
            listeEntite.add(new Asteroide(listeEntite.get(j).getX() - 50, listeEntite.get(j).getY(), 128, 128, asteroidm));
            listeEntite.remove(j);

        } else if (listeEntite.get(j).getImage() == asteroidm) {
            listeEntite.add(new Asteroide(listeEntite.get(j).getX() + 50, listeEntite.get(j).getY(), asteroidS.getWidth(), asteroidS.getHeight(), asteroidS));
            listeEntite.add(new Asteroide(listeEntite.get(j).getX() - 50, listeEntite.get(j).getY(), asteroidS.getWidth(), asteroidS.getHeight(), asteroidS));
            listeEntite.remove(j);

        } else if (listeEntite.get(j).getImage() == asteroidS) {
            listeEntite.add(new Asteroide(listeEntite.get(j).getX() + 50, listeEntite.get(j).getY(), asteroidXS.getWidth(), asteroidXS.getHeight(), asteroidXS));
            listeEntite.add(new Asteroide(listeEntite.get(j).getX() - 50, listeEntite.get(j).getY(), asteroidXS.getWidth(), asteroidXS.getHeight(), asteroidXS));
            listeEntite.remove(j);

        }


    }

    @Override
    public void keyReleased(int key, char c) {

        switch (key) {
            case Input.KEY_W:
            case Input.KEY_UP:
            case Input.KEY_D:
            case Input.KEY_RIGHT:
            case Input.KEY_A:
            case Input.KEY_LEFT:
            case Input.KEY_S:
            case Input.KEY_DOWN:
                this.moving = false;
                break;
        }

        if (Input.KEY_SPACE == key) {
            this.shooting = true;
        }

        if (Input.KEY_ESCAPE == key) {
            this.gc.exit();
        }
    }

    @Override
    public void keyPressed(int key, char c) {
        switch (key) {
            case Input.KEY_W:
            case Input.KEY_UP:
                this.directionVaisseau = 0;
                this.moving = true;
                break;
            case Input.KEY_A:
            case Input.KEY_LEFT:
                this.directionVaisseau = 1;
                this.moving = true;
                break;
            case Input.KEY_S:
            case Input.KEY_DOWN:
                this.directionVaisseau = 2;
                this.moving = true;
                break;
            case Input.KEY_D:
            case Input.KEY_RIGHT:
                this.directionVaisseau = 3;
                this.moving = true;
                break;
        }

        if (Input.KEY_SPACE == key) {
            this.shooting = true;
            laserX = (int) vaisseauX + 47;
            laserY = (int) vaisseauY - 20;
            yDepart = laserY;
            laser = new Laser(laserX, laserY, spriteSheetLaser, yDepart);
            laser.setHeight(25);
            laser.setWidth(25);
            listeEntite.add(laser);
            listeCollisionnable.add(laser);

        }

        if (Input.KEY_E == key) {
            this.vaisseau.vidageRecolte();
        }
    }


    private void modificationFood() {

    }
}


//modification de l'health bar, je vais l'arranger plus tard
/*if (listeEntite.get(i) instanceof Asteroide && listeEntite.get(i).getImage() == asteroidm || listeEntite.get(i).getImage() == asteroidL) {

            if (vaisseau.getRectangle().intersects(listeEntite.get(i).getRectangle())){
                healthBar3.destroy();
                graphics.drawImage(healthBar2,50,650);
            }
        }*/