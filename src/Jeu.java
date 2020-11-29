import java.util.ArrayList;

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

    public boolean moving = false;
    private boolean shooting = false;
    public int direction = 2;


    public Jeu(String title) {
        super(title);
    }

    private Laser laser;
    private int yDepart = 0;
    private Asteroide asteroide;


    @Override
    public void init(GameContainer gameContainer) throws SlickException {


        this.imageVaisseau = new Image("Images/shiper_mix_03.png"); //devrait marcher pour toi aussi ce repertory
        this.imageBackground = new Image("Images/120_Attract.png");
        this.imageBackground2 = new Image("Images/120_Attract.png");

        this.AsteroidM = new Image("Images/asteroids/medium/a10012.png");

        //  asteroide = new Asteroide(0,0,120,120,AsteroidM);
        //    for (int i = 0; i <= 5 ; i++){
        //      listeEntite.add(new Asteroide(30 * i, 49 * i, 120,120,AsteroidM ));
        //   }


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

        }
        if (this.shooting) {
            for (Entite entite : listeEntite) {
                entite.update(delta);
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


        for (Entite entite : listeEntite) {
            System.out.println(entite);
            graphics.drawImage(entite.getImage(), entite.getX(), entite.getY());
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
