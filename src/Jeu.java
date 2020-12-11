import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.*;

import javax.swing.*;

import static java.lang.System.*;

public class Jeu extends BasicGame {

    private int vaisseauX = 266;
    private int vaisseauY = 500;
    private int laserX;
    private int laserY;
    private int Hauteur = MainClass.HAUTEUR;
    private int Largeur = MainClass.LARGEUR;

    private GameContainer gc;
    private Vaisseau vaisseau;
    public boolean moving = false;
    public int directionVaisseau = 2;
    private Laser laser;
    private int yDepart = 0;
    private boolean shooting = false;
    
    //arrayliste
    private ArrayList<Entite> listeEntite = new ArrayList<>();
    private ArrayList<Collisionnable> listeCollisionnable = new ArrayList<>();
    private ArrayList<Entite> aSuprimer = new ArrayList<>();
    private ArrayList<Collisionnable> aAjouter = new ArrayList<>();
    private ArrayList<Image> listeRecolte = new ArrayList<>();
    //image
    private Image imageVaisseau;
    private Image imageBackground;
    private Image asteroidL, asteroidm, asteroidS, asteroidXS;
    private SpriteSheet spriteSheetLaser;


    private int deplacementImage = 0; //background


    private double nouvelAsteroideReady;

    Sound snd;

    /**
     * @param title titre de la page du jeu
     * @throws SlickException
     */
    public Jeu(String title) throws SlickException {
        super(title);
    }


    //private Asteroide asteroide;

    private Random random = new Random();


    @Override
    public void init(GameContainer gameContainer) throws SlickException {

        snd = new Sound("Sound/SpaceSound.wav");
        snd.loop();


        this.imageVaisseau = new Image("Images/vaisseau.png"); //devrait marcher pour toi aussi ce repertory
        this.vaisseau = new Vaisseau(vaisseauX, vaisseauY, imageVaisseau.getWidth(), imageVaisseau.getHeight(), imageVaisseau);

        listeEntite.add(vaisseau);
        listeCollisionnable.add(vaisseau);
        this.imageBackground = new Image("Images/120_Attract.png");
        this.asteroidL = new Image("Images/asteroidS/large/Grand0.png");
        this.asteroidm = new Image("Images/asteroidS/medium/Moyen0.png");
        this.asteroidS = new Image("Images/asteroidS/small/Small0.png");
        this.asteroidXS = new Image("Images/asteroidS/Xsmall/Xsmall0.png");

        out.println("largeur L : " + asteroidL.getWidth());
        out.println("hauteur L: " + asteroidL.getHeight());

        out.println("largeur M : " + asteroidm.getWidth());
        out.println("hauteur M: " + asteroidm.getHeight());

        out.println("largeur S : " + asteroidS.getWidth());
        out.println("hauteur S: " + asteroidS.getHeight());


        //J'ai mit la vie et la recolte dans la classe vaisseau, les methodes getHealth et getRecolte vont retourner les images


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

        deplacementImage += 0.1f * delta;
        this.vaisseau.perteInvincibilite(delta);

        if (spawnAsteroideReady(delta)) {
            spawnAsteroideRandom();
        }


        for (Entite entite : listeEntite) {
            if (entite instanceof Laser) {
                entite.update(delta);
            }

            if (entite instanceof Asteroide) {
                entite.update(delta);

            }
        }

        collision();
        listeCollisionnable.removeAll(aSuprimer);
        aSuprimer.clear();
        listeCollisionnable.addAll(aAjouter);
        aAjouter.clear();


        System.gc();

    }


    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        //background
        defilerBackground(graphics);

        // vaisseau spatial
        graphics.drawImage(imageVaisseau, vaisseauX, vaisseauY);

        //bar de vie
        if (this.vaisseau.getHealthBar() == null) {
        } else {
            graphics.drawImage(this.vaisseau.getHealthBar(), 30, 650);
        }

        //bar de points
        if (this.vaisseau.getRecolteBar() == null) {
        } else {
            graphics.drawImage(this.vaisseau.getRecolteBar(), 450, 650);
        }
        graphics.drawString("Capacite : " + Integer.toString(this.vaisseau.getNbRecolte()), 450, 625);
        graphics.drawString("Score : " + Integer.toString(this.vaisseau.getScore()), 0, 25);

        for (int i = 0; i < listeEntite.size(); i++) {
            Entite entite = listeEntite.get(i);
            graphics.drawImage(entite.getImage(), entite.getX(), entite.getY());
            if (entite.getDetruire()) {
                aSuprimer.add(listeEntite.get(i));
                listeEntite.remove(i);
            }


            if (vaisseau.getNbVie() == 0) {
                if (JOptionPane.showConfirmDialog(null, "Vous n'avez plus de vie malheureusement.\n Voulez-vous recommencer?", "Nouveau Départ", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    listeEntite.removeAll(listeEntite);
                    listeCollisionnable.removeAll(listeCollisionnable);
                    listeRecolte.removeAll(listeRecolte);
                    init(gameContainer);
                } else {
                    exit(0);
                }
            }

        }

    }


    /**
     * @param delta donné changeante
     * @return
     */
    public boolean spawnAsteroideReady(int delta) {
        double tempsRecharge = 0.00030;
        nouvelAsteroideReady += tempsRecharge * delta;     //temps de recharge change le temps entre le spawn d'asteroide
        if (nouvelAsteroideReady >= 1) {
            nouvelAsteroideReady = 0;
            return true;
        }
        return false;
    }

    /**
     * Faire apparaitre des asteroid
     */
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

    /**
     * Faire apparaitre des asteroid large
     */
    public void spawnAsteroideLarge() {
        int spawnX = random.nextInt(MainClass.LARGEUR / 2) + MainClass.LARGEUR / 4;
        int spawnY = random.nextInt(100);
        listeEntite.add(new Asteroide(spawnX, spawnY, asteroidL.getWidth(), asteroidL.getHeight(), asteroidL));
        listeCollisionnable.add(new Asteroide(spawnX, spawnY, asteroidL.getWidth(), asteroidL.getHeight(), asteroidL));
    }

    /**
     * Faire apparaitre des asteroide medium
     */
    public void spawnAsteroideMedium() {
        int spawnX = random.nextInt(MainClass.LARGEUR / 2) + MainClass.LARGEUR / 4;
        int spawnY = random.nextInt(100);
        listeEntite.add(new Asteroide(spawnX, spawnY, 128, 128, asteroidm));
        listeCollisionnable.add(new Asteroide(spawnX, spawnY, 128, 128, asteroidm));
    }

    /**
     * Faire apparaitre des asteroid small
     */
    public void spawnAsteroideSmall() {
        int spawnX = random.nextInt(MainClass.LARGEUR / 2) + MainClass.LARGEUR / 4;
        int spawnY = random.nextInt(100);
        listeEntite.add(new Asteroide(spawnX, spawnY, asteroidS.getWidth(), asteroidS.getHeight(), asteroidS));
        listeCollisionnable.add(new Asteroide(spawnX, spawnY, asteroidS.getWidth(), asteroidS.getHeight(), asteroidS));
    }


    /**
     * S'occupe du traitement des collisions : Asteroide - Vaisseau && Asteroide - Laser
     *
     * @throws SlickException
     */
    private void collision() throws SlickException {

        Entite laser;
        Entite asteroid;
        Entite vaisseau;
        int aireAsteroid;
        int aireVaisseau;
        for (int i = 0; i < listeEntite.size(); i++) {
            for (int j = 0; j < listeEntite.size(); j++) {
                if (i != j) {
                    if (listeEntite.get(i) instanceof Laser && listeEntite.get(j) instanceof Asteroide) {
                        laser = listeEntite.get(i);
                        asteroid = listeEntite.get(j);
                        if (laser.getRectangle().intersects(asteroid.getRectangle())) {
                            if (asteroid.getWidth() > 32)
                                listeEntite.get(i).setDetruire();
                            separatioAsteroid(j);
                        }
                    }
                    if (listeEntite.get(i) instanceof Vaisseau && listeEntite.get(j) instanceof Asteroide) {
                        vaisseau = listeEntite.get(i);
                        asteroid = listeEntite.get(j);
                        if (vaisseau.getRectangle().intersects(asteroid.getRectangle())) {
                            aireAsteroid = (asteroid.getWidth()) * (asteroid.getHeight());
                            aireVaisseau = (vaisseau.getHeight()) * (vaisseau.getWidth());

                            if (aireAsteroid < aireVaisseau) {
                                listeEntite.get(j).setDetruire();
                                this.vaisseau.gainRecolte(aireAsteroid / 2);
                            } else {
                                listeEntite.get(j).setDetruire();
                                this.vaisseau.perteVie();

                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * @param j position dans l'array list
     * @throws SlickException
     */
    public void separatioAsteroid(int j) throws SlickException {
        if (listeEntite.get(j).getImage() == asteroidL) {
            listeEntite.add(new Asteroide(listeEntite.get(j).getX() + 50, listeEntite.get(j).getY(), 128, 128, asteroidm));
            listeEntite.add(new Asteroide(listeEntite.get(j).getX() - 50, listeEntite.get(j).getY(), 128, 128, asteroidm));
            listeEntite.remove(j);
            aSuprimer.add(listeEntite.get(j));
            aAjouter.add((Collisionnable) listeEntite.get(listeEntite.size() - 1));
            aAjouter.add((Collisionnable) listeEntite.get(listeEntite.size() - 2));

        } else if (listeEntite.get(j).getImage() == asteroidm) {
            listeEntite.add(new Asteroide(listeEntite.get(j).getX() + 50, listeEntite.get(j).getY(), asteroidS.getWidth(), asteroidS.getHeight(), asteroidS));
            listeEntite.add(new Asteroide(listeEntite.get(j).getX() - 50, listeEntite.get(j).getY(), asteroidS.getWidth(), asteroidS.getHeight(), asteroidS));
            listeEntite.remove(j);
            aSuprimer.add(listeEntite.get(j));
            aAjouter.add((Collisionnable) listeEntite.get(listeEntite.size() - 1));
            aAjouter.add((Collisionnable) listeEntite.get(listeEntite.size() - 2));

        } else if (listeEntite.get(j).getImage() == asteroidS) {
            listeEntite.add(new Asteroide(listeEntite.get(j).getX() + 50, listeEntite.get(j).getY(), asteroidXS.getWidth(), asteroidXS.getHeight(), asteroidXS));
            listeEntite.add(new Asteroide(listeEntite.get(j).getX() - 50, listeEntite.get(j).getY(), asteroidXS.getWidth(), asteroidXS.getHeight(), asteroidXS));
            listeEntite.remove(j);
            aSuprimer.add(listeEntite.get(j));
            aAjouter.add((Collisionnable) listeEntite.get(listeEntite.size() - 1));
            aAjouter.add((Collisionnable) listeEntite.get(listeEntite.size() - 2));

        }
    }

    /**
     * @param graphics afficher le deroulement du background
     */
    private void defilerBackground(Graphics graphics) {
        for (int i = 0; i < Largeur; i = i + 600) {
            for (long j = deplacementImage % 700 - 700; j < Hauteur; j = j + 700) {
                graphics.drawImage(imageBackground, i, j);
            }
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


    // Confirme la compilation du garbage collector
    @Override
    protected void finalize()
    {
        System.out.println("Jetage de dechets");

    }


}