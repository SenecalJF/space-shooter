import org.newdawn.slick.*;

public class MainClass {

    public static final int LARGEUR = 600, HAUTEUR = 700;
    public static void main(String[] args){
        System.out.println("LANCEMENT");
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

/*Ce qu'il reste à faire
  FAIT Spritesheet d'une image de coeur et afficher les coeurs en fonction de la vie restante
  FAIT spritesheet d'une image de nourriture minecraft et les afficher graduellement jusqu'à temps que le vaisseau est plein
  FAIT Defiller l'écran continnuellement
  FAIT Patron Observateur + MVC pour afficher la cargaison envoyé sur mars
  FAIT Gestion de la mémoire
  FAIT Faire recommencer le JEU lorsque ça fini
  FAIT Java docs
  FAIT Word et image d'essai
  FAIT Mettre de la musique
*/

