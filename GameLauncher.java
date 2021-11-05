public class GameLauncher{ 
   /**
   * Only CHANGE constant values as appropriate
   *
   * 
   * THERE SHOULD NOT BE ANYTHING SUBSTANTIAL TO **ADD** TO THIS CLASS
   * 
   * The Scrolling Game is described the Project Handout. 
   *  
   * @author Elodie Fourquet 
   * @date October, 2019
   */
   
   
   
   private static final int DEMO = 0;
   private static final int BASE = 1;
   private static final int CREATIVE = 2;
   
   private static final int RUNNING = CREATIVE;
   
   private static AbstractGame game;
   
   
   public static void main(String[] args) {
      // Construct sized version of the creative game you've written
      game = new CreativeGame(5, 10);
      break;
      // launch the instantiated game version: trace from parent,
      // i.e. ScrollingGame
      game.play();
   }
   
}
