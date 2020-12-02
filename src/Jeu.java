import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.*;

public class Jeu extends BasicGame {

    private int x = 266;
    private int y = 500;
    private int j;
    private int k;
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

    public boolean moving = false;
    private boolean shooting = false;
    public int direction = 2;


    public Jeu(String title) {
        super(title);
    }

    private Laser laser;
    private int yDepart = 0;
    private Asteroide asteroide;

    private Random random = new Random();


    @Override
    public void init(GameContainer gameContainer) throws SlickException {


        this.imageVaisseau = new Image("Images/shiper_mix_03.png"); //devrait marcher pour toi aussi ce repertory
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

        if (this.moving) {
            switch (this.direction) {
                case 0:
                    //haut
                    if (y <= 0) {
                        y = 0;
                    } else
                        this.y -= 0.5 * delta;
                    break;
                case 1:
                    //gauche
                    if (x <= 0) {
                        x = 0;
                    } else
                        this.x -= 0.5 * delta;
                    break;
                case 2:
                    //bas
                    if (y >= Hauteur - imageVaisseau.getHeight()) {
                        y = Hauteur - imageVaisseau.getHeight();
                    } else
                        this.y += 0.5 * delta;
                    break;
                case 3:
                    //droite
                    if (x >= Largeur - imageVaisseau.getWidth()) {
                        x = Largeur - imageVaisseau.getWidth();
                    } else
                        this.x += 0.5 * delta;
                    break;
            }

            int direction = 0; // 0 est up, 1 est gauche, 2 est bas, 3 est droite
            // asteroide.update(delta, direction);

        }

            for (Entite entite : listeEntite) {
                if (this.shooting && entite instanceof Laser) {
                    entite.update(delta, 0);
                } else if (entite instanceof Asteroide) {
                    direction = random.nextInt(5);
                    entite.update(delta, direction);
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

        // vaisseau spatial
        graphics.drawImage(imageVaisseau, x, y);


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
                this.direction = 0;
                this.moving = true;
                break;
            case Input.KEY_A:
                this.direction = 1;
                this.moving = true;
                break;
            case Input.KEY_S:
                this.direction = 2;
                this.moving = true;
                break;
            case Input.KEY_D:
                this.direction = 3;
                this.moving = true;
                break;
        }

        if (Input.KEY_SPACE == key) {
            this.shooting = true;
            j = x + 20;
            k = y - 20;
            yDepart = k;
            laser = new Laser(j, k, spriteSheetLaser, yDepart);
            listeEntite.add(laser);

        }
    }
}
