import java.util.ArrayList;
import org.newdawn.slick.*;

public class Jeu extends BasicGame{

    private int x = 266;
    private int y = 500;
    private int k = x;
    private int j = y;
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

    Laser laser;

    @Override
    public void init(GameContainer gameContainer) throws SlickException {


        this.imageVaisseau =new Image("Images/shiper_mix_03.png"); //devrait marcher pour toi aussi ce repertory
        this.imageBackground = new Image("Images/120_Attract.png");

        spriteSheetLaser = new SpriteSheet("Images/beams.png", 25, 25);
        laser = new Laser(k, j, spriteSheetLaser);
        listeEntite.add(laser);
        laser1 = (new SpriteSheet("Images/beams.png", 25, 25)).getSubImage(0, 0);
    }

    @Override
    public void update(GameContainer gameContainer, int delta) throws SlickException {

        if (this.moving){
            switch (this.direction){
                case 0:
                    this.y -= 0.5 * delta;
                    break;
                case 1:
                    this.x -=  0.5 * delta;
                    break;
                case 2:
                    this.y +=  0.5 * delta;
                    break;
                case 3:
                    this.x +=  0.5 * delta;
                    break;
            }
        }
        if (this.shooting) {
            this.j -= 0.7 * delta;
        }
        if ( x<=0 ){
            x = 0;
        }
        if (x >= 517){
            x = 517;
        }
        if (y <= 0){
            y = 0;
        }
        if (y >= 575){
            y = 575;
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
        graphics.drawImage(laser1, k + 20, j - 20);
    }

    @Override
    public void keyReleased(int key, char c) {
        this.moving = false;
        this.shooting = true;
        this.shooting = true;
        if (Input.KEY_ESCAPE == key) {
            this.gc.exit();
        }
    }

    @Override
    public void keyPressed(int key, char c) {
        switch (key) {
            case Input.KEY_UP:
                this.direction = 0;
                this.moving = true;
                break;
            case Input.KEY_LEFT:
                this.direction = 1;
                this.moving = true;
                break;
            case Input.KEY_DOWN:
                this.direction = 2;
                this.moving = true;
                break;
            case Input.KEY_RIGHT:
                this.direction = 3;
                this.moving = true;
                break;
        }

        if (Input.KEY_SPACE == key) {
            shooting = true;
        }
    }
}
