public class GameLauncher{ 
   public static void main(String[] args) {
      // Construct sized version of the creative game you've written
      AbstractGame game;
      game = new CreativeGame(5, 10);
      // launch the instantiated game version: trace from parent,
      // i.e. ScrollingGame
      game.play();
   }
}
