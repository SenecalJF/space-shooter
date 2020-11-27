import org.newdawn.slick.*;

public class MainClass {

    public static final int LARGEUR = 600, HAUTEUR = 700;
    public static void main(String[] args){

        try {
            AppGameContainer app = new AppGameContainer(new Jeu("SS TEMP"));
            app.setDisplayMode(LARGEUR, HAUTEUR, false);
            app.setShowFPS(true); // true for display the numbers of FPS
            app.setVSync(true); // false for disable the FPS synchronize
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }

    }
}
