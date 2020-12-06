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
    //private int j = y;
    private GameContainer gc;
    Vaisseau vaisseau;
    private ArrayList<Entite> listeEntite = new ArrayList<>();
    private ArrayList<Destructible> listeDestructible = new ArrayList<>();
    private ArrayList<Collisionnable> listeCollisionnable = new ArrayList<>();
    private Image imageVaisseau;
    private Image imageBackground;
    private Image imageBackground2;
    private int deplacementImage = 0;
    private int imgBackHeight = 0;
    private int deplacementImage2 = 0;
    private boolean descendre = false;
    private Image laser1;
    private Image AsteroidL;
    private Image AsteroidM;
    private Image AsteroidS;
    private SpriteSheet spriteSheetLaser;
    private SpriteSheet spriteSheetAstroid;
    double nouvelAsteroideReady;

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

        this.imageVaisseau = new Image("Images/shiper_mix_03.png"); //devrait marcher pour toi aussi ce repertory
        this.vaisseau = new Vaisseau(vaisseauX, vaisseauY, imageVaisseau.getWidth(), imageVaisseau.getHeight(), imageVaisseau);
        listeEntite.add(vaisseau);
        listeCollisionnable.add(vaisseau);
        this.imageBackground = new Image("Images/120_Attract.png");
        imgBackHeight = imageBackground.getHeight();
        this.imageBackground2 = new Image("Images/120_Attract.png");
        this.AsteroidL = new Image("Images/asteroids/large/Grand0.png");
        this.AsteroidM = new Image("Images/asteroids/medium/Moyen0.png");
        this.AsteroidS = new Image("Images/asteroids/small/Small0.png");
        System.out.println(AsteroidL.getHeight());



        for (int i = 0; i <= 5; i++) {
            spawnAsteroideRandom();
        }


        spriteSheetLaser = new SpriteSheet("Images/beams.png", 25, 25);


    }

    @Override
    public void update(GameContainer gameContainer, int delta) throws SlickException {

        this.vaisseauX = this.vaisseau.deplacementVaisseauX(this.moving, directionVaisseau, delta);
        this.vaisseauY = this.vaisseau.deplacementVaisseauY(this.moving, directionVaisseau, delta);
        this.vaisseau.setLocation(vaisseauX, vaisseauY);
        // J'ai change pour que les deplacements soient traites individuellement
        // Parce que sinon les deplacements d'asteroides et du vaisseau se derangeaient

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
        /*deplacementImage += 1f;
        if (deplacementImage >= 0){
            deplacementImage2 += 1f;
            descendre = true;

        }*/

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


        for (int i = 0; i < listeEntite.size(); i++) {
            Entite entite = listeEntite.get(i);
            graphics.drawImage(entite.getImage(), entite.getX(), entite.getY());
            if (entite instanceof Laser) {
                if (entite.getDetruire()) {
                    listeEntite.remove(i);
                }
            }

           /* if (entite.getRectangle().intersects(asteroide.getRectangle())){
                graphics.drawString("collision", 50,50);
            }*/
        }

        collision(graphics);
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
            laserX = (int) vaisseauX + 20;
            laserY = (int) vaisseauY - 20;
            yDepart = laserY;
            laser = new Laser(laserX, laserY, spriteSheetLaser, yDepart);
            laser.setHeight(25);
            laser.setWidth(25);
            listeEntite.add(laser);
            listeCollisionnable.add(laser);

        }
    }


    public boolean spawnAsteroideReady(int delta) {
        double tempsRecharge = 0.00025;
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
        listeEntite.add(new Asteroide(spawnX, spawnY, AsteroidL.getWidth(), AsteroidL.getHeight(), AsteroidL));
        listeCollisionnable.add(new Asteroide(spawnX, spawnY, AsteroidL.getWidth(), AsteroidL.getHeight(), AsteroidL));
    }

    public void spawnAsteroideMedium() {
        int spawnX = random.nextInt(MainClass.LARGEUR / 2) + MainClass.LARGEUR / 4;
        int spawnY = random.nextInt(100);
        listeEntite.add(new Asteroide(spawnX, spawnY, AsteroidM.getWidth(), AsteroidM.getHeight(), AsteroidM));
        listeCollisionnable.add(new Asteroide(spawnX, spawnY, AsteroidM.getWidth(), AsteroidM.getHeight(), AsteroidM));
    }

    public void spawnAsteroideSmall() {
        int spawnX = random.nextInt(MainClass.LARGEUR / 2) + MainClass.LARGEUR / 4;
        int spawnY = random.nextInt(100);
        listeEntite.add(new Asteroide(spawnX, spawnY, AsteroidS.getWidth(), AsteroidS.getHeight(), AsteroidS));
        listeCollisionnable.add(new Asteroide(spawnX, spawnY, AsteroidS.getWidth(), AsteroidS.getHeight(), AsteroidS));
    }


    public void collision(Graphics graphics) {

        Entite laser = null;
        Entite asteroid = null;
        for (int i = 0; i < listeEntite.size(); i++) {
            for (int j = 0; j < listeEntite.size(); j++) {
                if (i != j) {
                    if (listeEntite.get(i) instanceof Laser && listeEntite.get(j) instanceof Asteroide) {
                        laser = listeEntite.get(i);
                        asteroid = listeEntite.get(j);
                        // System.out.println(listeEntite.get(j).getRectangle());
                        if (laser.getRectangle().intersects(asteroid.getRectangle())) {
                            graphics.drawString("collision", 50, 50);
                        }
                    }


                }
            }
        }
    }

}
