import java.util.ArrayList;
import org.newdawn.slick.*;

public class Jeu extends BasicGame{

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
    private Image laser1;
    private SpriteSheet spriteSheetLaser;

   public boolean moving = false;
    private boolean shooting = false;
   public int direction = 2;

    public Jeu(String title) {
        super(title);
    }

    private Laser laser;
    private Asteroide


    @Override
    public void init(GameContainer gameContainer) throws SlickException {


        this.imageVaisseau =new Image("Images/shiper_mix_03.png"); //devrait marcher pour toi aussi ce repertory
        this.imageBackground = new Image("Images/120_Attract.png");

        spriteSheetLaser = new SpriteSheet("Images/beams.png", 25, 25);
        // laser = new Laser(x, y, spriteSheetLaser);

    }

    @Override
    public void update(GameContainer gameContainer, int delta) throws SlickException {

        if (this.moving){
            switch (this.direction){
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
                        this.x -=  0.5 * delta;
                    break;
                case 2:
                    //bas
                    if (y >= Hauteur - imageVaisseau.getHeight()) {
                        y = Hauteur - imageVaisseau.getHeight();
                    } else
                        this.y +=  0.5 * delta;
                    break;
                case 3:
                    //droite
                    if (x >= Largeur - imageVaisseau.getWidth()) {
                        x = Largeur - imageVaisseau.getWidth();
                    } else
                        this.x +=  0.5 * delta;
                    break;
            }
        }
        if (this.shooting) {

            System.out.println(j);

            this.k -= laser.getVitesse() * delta;
        }

    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        //background
        graphics.drawImage(imageBackground,0,0);

        // vaisseau spatial
        graphics.drawImage(imageVaisseau, x,y);

        for (Entite entite : listeEntite) {
            graphics.drawImage(entite.getImage(), entite.getX(), entite.getY());
        }
        if (shooting) {

            graphics.drawImage(laser1, j + 20, k - 20);
        }
    }
    @Override
    public void keyReleased(int key, char c) {
        this.moving = false;

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
            j = x;
            k = y;
            laser = new Laser(j, k, spriteSheetLaser);
            //listeEntite.add(laser);

            try {
                laser1 = (new SpriteSheet("Images/beams.png", 25, 25)).getSubImage(0, 0);
            } catch (SlickException e) {
                e.printStackTrace();
            }


        }
    }
}
