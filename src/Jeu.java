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
    private Image imageVaisseau;
    private Image imageBackground;
    private Image imageBackground2;
    private int deplacementImage = 0;
    private int deplacementImage2 = -15;
    private Image laser1;
    private Image AsteroidM;
    private SpriteSheet spriteSheetLaser;
    private SpriteSheet spriteSheetAstroid;
    double nouvelAsteroideReady;

    public boolean moving = false;
    private boolean shooting = false;
    public int directionVaisseau = 2;


    public Jeu(String title) throws SlickException {
        super(title);
    }

    private Laser laser;
    private int yDepart = 0;
    private Asteroide asteroide;

    private Random random = new Random();


    @Override
    public void init(GameContainer gameContainer) throws SlickException {

        this.imageVaisseau = new Image("Images/shiper_mix_03.png");//devrait marcher pour toi aussi ce repertory
        this.vaisseau = new Vaisseau(vaisseauX, vaisseauY, imageVaisseau.getWidth(), imageVaisseau.getHeight(), imageVaisseau);
        listeEntite.add(vaisseau);
        this.imageBackground = new Image("Images/120_Attract.png");
        this.imageBackground2 = new Image("Images/120_Attract.png");
        this.AsteroidM = new Image("Images/asteroids/medium/a10012.png");

        // spriteSheetAstroid = new SpriteSheet(AsteroidM, 120,120);

        //  asteroide = new Asteroide(0,0,120,120,AsteroidM);
        for (int i = 0; i <= 5; i++) {

            int x1 = random.nextInt(500);
            int y1 = random.nextInt(500);

            listeEntite.add(new Asteroide(x1, y1, 120, 120, AsteroidM));
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
            spawnAsteroide();
        }

        for (Entite entite : listeEntite) {
            if (entite instanceof Laser) {
                entite.update(delta);          //J'ai enleve la direction des laser puisque c'est tjs vers le haut anyways
            }
            if (entite instanceof Asteroide) {
                entite.update(delta);          //deplacement des asteroides est dans classe asteroide
            }
        }


    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        //background

        //tentative de loop qui marche pas
        //  deplacementImage += 1f;
        //  deplacementImage2 += 1f;

        graphics.drawImage(imageBackground, 0, deplacementImage);
        // graphics.drawImage(imageBackground2,0, deplacementImage2);


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


    }


    @Override
    public void keyReleased(int key, char c) {
        this.moving = false;
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
            listeEntite.add(laser);

        }
    }


    public boolean spawnAsteroideReady(int delta) {
        double tempsRecharge = 0.00025;
        nouvelAsteroideReady += tempsRecharge * delta;     //temps de recharge change le temps entre le spawn d'asteroide
        if (nouvelAsteroideReady >= 1) {
            nouvelAsteroideReady = 0;
            System.out.println("apparition asteroide");
            return true;
        }
        return false;
    }

    public void spawnAsteroide() {
        int spawnX = random.nextInt(MainClass.LARGEUR / 2) + MainClass.LARGEUR / 4;
        int spawnY = random.nextInt(100);
        listeEntite.add(new Asteroide(spawnX, spawnY, 120, 120, AsteroidM));
    }

}
