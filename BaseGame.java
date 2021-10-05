import java.awt.event.KeyEvent;
import java.util.Random;
import java.awt.Color;
public class BaseGame extends AbstractGame {
    
    private static final int INTRO = 0; 

    private String PLAYER_IMG = "images/user.gif";    // specify user image file
    private String SPLASH_IMG = "images/ink.png"; 

    // ADD others for Avoid/Get items 
    private String GET_IMG = "images/get.gif";
    private String AVOID_IMG = "images/avoid.gif";
    
    // default number of vertical/horizontal cells: height/width of grid
    private static final int DEFAULT_GRID_H = 5;
    private static final int DEFAULT_GRID_W = 10;
    
    private static final int DEFAULT_TIMER_DELAY = 100;
    
    // default location of user at start
    private static final int DEFAULT_PLAYER_ROW = 0;
    
    protected static final int STARTING_FACTOR = 5;      // you might change that this when working on timing
    
    protected int factor = STARTING_FACTOR;
    
    protected Location player;
    
    protected int screen = INTRO;
    
    protected GameGrid grid;
    
    private int score = 0;
    private int hits = 0;
    
    public BaseGame() {
        this(DEFAULT_GRID_H, DEFAULT_GRID_W);
    }
    
    public BaseGame(int grid_h, int grid_w){
         this(grid_h, grid_w, DEFAULT_TIMER_DELAY);
    }

    
    
    public BaseGame(int hdim, int wdim, int init_delay_ms) {
        super(init_delay_ms);
        //set up our "board" (i.e., game grid) 
        grid = new GameGrid(hdim, wdim);   
        
    }
    
    /******************** Methods **********************/
    
    protected void initGame(){
         
         // store and initialize user position
         player = new Location(DEFAULT_PLAYER_ROW, 0);
         grid.setCellImage(player, PLAYER_IMG);
         
         updateTitle();                           
    }
    
        
    // Display the intro screen: not too interesting at the moment
    // Notice the similarity with the while structure in play()
    // sleep is required to not consume all the CPU; going too fast freezes app 
    protected void displayIntro(){
     
       grid.setSplash(SPLASH_IMG);
       while (screen == INTRO) {
          super.sleep(timerDelay);
          // Listen to keep press to break out of intro 
          // in particular here --> space bar necessary
          handleKeyPress();
       }
       grid.setSplash(null);
    }
  
    protected void updateGameLoop() {
        
        handleKeyPress();        // update state based on user key press
        handleMouseClick();      // when the game is running: 
        // click & read the console output 
        
        if (turnsElapsed % factor == 0) {  // if it's the FACTOR timer tick
            // constant 3 initially
            scrollLeft();
            populateRightEdge();
            handleCollision();
        }     
        updateTitle();
        
    }
    
    
    
    // update game state to reflect adding in new cells in the right-most column
    private void populateRightEdge() {
        int height = grid.getNumRows();
        int width = grid.getNumCols();
        Random rand = new Random();
        for (int i=0; i<height; i++){
            Location spot  = new Location(i,width-1);
            int rand_n = rand.nextInt(3);
            if (rand_n==0){
                grid.setCellImage(spot,GET_IMG);
            }
            else if (rand_n==1){
                grid.setCellImage(spot,AVOID_IMG);
            }
            else{
                grid.setCellImage(spot,"");
            }
        }
    }
    
    // updates the game state to reflect scrolling left by one column
    private void scrollLeft() {
        for (int col=0; col<grid.getNumCols()-1;col++){
            for (int row=0; row<grid.getNumRows();row++){
                Location left = new Location(row,col);
                Location right = new Location(row, col+1);
                String right_img = grid.getCellImage(right);
                if (right_img!=PLAYER_IMG){
                    grid.setCellImage(left,right_img);
                }
            }
        }
    }
    
    
    /* handleCollision()
     * handle a collision between the user and an object in the game
     */    
    private void handleCollision() {
        String cell_img = grid.getCellImage(player);
        if (cell_img!=PLAYER_IMG){
            if (cell_img == AVOID_IMG){
                hits++;
                System.out.println("Score: " + score + " Hits: " + hits);
            }
            else if (cell_img == GET_IMG){
                score+=10;
                System.out.println("Score: " + score + " Hits: " + hits);
            }
            grid.setCellImage(player, PLAYER_IMG);
        }
    }
    
    //---------------------------------------------------//
    
    // handles actions upon mouse click in game
    private void handleMouseClick() {
        
        Location loc = grid.checkLastLocationClicked();
        
        if (loc != null) 
            System.out.println("You clicked on a square " + loc);
        
    }
    
    // handles actions upon key press in game
    protected void handleKeyPress() {
        
        int key = grid.checkLastKeyPressed();
        
        //use Java constant names for key presses
        //http://docs.oracle.com/javase/7/docs/api/constant-values.html#java.awt.event.KeyEvent.VK_DOWN
        
        // Q for quit
        if (key == KeyEvent.VK_Q)
            System.exit(0);
        
        else if (key == KeyEvent.VK_S){
            grid.save("screenshot.png");
            System.out.println("screen saved");
        }
        
        else if (key == KeyEvent.VK_SPACE)
           screen += 1;
        else if (key == KeyEvent.VK_DOWN){
            int r = player.getRow();
            int c = player.getCol();
            if ((r+1)<grid.getNumRows()){
                grid.setCellImage(new Location(r,c),null);
                player = new Location(r+1,c);
            }
            handleCollision();
            grid.setCellImage(player,PLAYER_IMG);
        }
        else if (key == KeyEvent.VK_UP){
            int r = player.getRow();
            int c = player.getCol();
            if ((r-1)>=0){
                grid.setCellImage(new Location(r,c),null);
                player = new Location(r-1,c);
            }
            handleCollision();
            grid.setCellImage(player,PLAYER_IMG);
        }
        else if (key == KeyEvent.VK_RIGHT){
            int r = player.getRow();
            int c = player.getCol();
            if ((c+1)<grid.getNumCols()){
                grid.setCellImage(new Location(r,c),null);
                player = new Location(r,c+1);
            }
            handleCollision();
            grid.setCellImage(player,PLAYER_IMG);
        }
        else if (key == KeyEvent.VK_LEFT){
            int r = player.getRow();
            int c = player.getCol();
            if ((c-1)>=0){
                grid.setCellImage(new Location(r,c),null);
                player = new Location(r,c-1);
            }
            handleCollision();
            grid.setCellImage(player,PLAYER_IMG);
        }
        else if (key == KeyEvent.VK_D){
            Color red = Color.RED;
            Color black = Color.BLACK;
            Color grid_color = grid.getLineColor();
            if (grid_color == null){
                grid.setLineColor(red);
            }
            else if (grid_color.equals(black)){
                grid.setLineColor(red);
            }
            else{
                grid.setLineColor(black);
            }
        }
        else if (key == KeyEvent.VK_P){
            if (factor == STARTING_FACTOR){
                factor = 10000000;
            }
            else{
                factor = STARTING_FACTOR;
            }
        }
        else if (key == KeyEvent.VK_COMMA){
            factor += 1;
        }
        else if (key == KeyEvent.VK_PERIOD){
            if (factor > 1){
                factor -= 1;
            }
        }
        /* To help you with step 9: 
         use the 'T' key to help you with implementing speed up/slow down/pause
         this prints out a debugging message */
        else if (key == KeyEvent.VK_T)  {
            boolean interval =  (turnsElapsed % factor == 0);
            System.out.println("timerDelay " + timerDelay + " msElapsed reset " + 
                               msElapsed + " interval " + interval);
        } 
    }
    
    // return the "score" of the game 
    private int getScore() {
        return score;
    }
    
    // update the title bar of the game window 
    private void updateTitle() {
        grid.setTitle("Scrolling Game:  " + getScore());
    }
    
    // return true if the game is finished, false otherwise
    //      used by play() to terminate the main game loop 
    protected boolean isGameOver() {
        if (score==100 || hits==10){
            displayOutcome();
            return true;
        }
        return false;
    }

    
    // display the game over screen, blank for now
    protected void displayOutcome() {
        if (score == 100){
            grid.setTitle("You Win!");
        }
        else if (hits == 10){
            grid.setTitle("You Lost. Game Over!");
        }
    }
}
