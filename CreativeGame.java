import java.awt.event.KeyEvent;
import java.util.Random;
import java.awt.Color;
public class CreativeGame extends AbstractGame {
    private double health = 100;
    private int score = 0;

    private static final int INTRO = 0; 
    //User image files
    private String PLAYER_IMG = "images/rocket.gif";
    private String PLAYER_IMG_UP = "images/rocket.gif";
    private String PLAYER_IMG_RIGHT = "images/rocket_right.png";
    private String PLAYER_IMG_LEFT = "images/rocket_left.png";
    private String PLAYER_IMG_DOWN = "images/rocket_down.png";
    //Images for game over screen, intro screen, game won screen and background.
    private String GAME_OVER_IMG = "images/game_over.png";
    private String INTRO_IMG = "images/intro.png";
    private String WIN_IMG = "images/win.png";
    private String BG_IMG = "images/space.jpg";
    //Image for explosion image during teleportation
    private String EXPLOSION_IMG = "images/explosion.jpg";
    // Image for different game objects the user interacts with. Names are self explanatory
    private String ASTEROID_IMG = "images/asteroid.gif";
    private String FUEL_IMG = "images/fuel.png";
    private String PORTAL_IMG = "images/portal.png";
    protected String HAMMER_IMG = "images/hammer.png";
    protected String SCREWDRIVER_IMG = "images/screwdriver.png";
    protected String WRENCH_IMG = "images/wrench.png";
    protected String DRILL_IMG = "images/drill.png";
    protected String PLIER_IMG = "images/plier.png";
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
    //Integers representing the tools in the game toolbox
    protected static int HAMMER = 0;
    protected static int SCREWDRIVER = 1;
    protected static int WRENCH = 2;
    protected static int DRILL = 3;
    protected static int PLIER = 4;
    //Integer representing the number of tools in the toolbox
    protected static int NUM_TOOLS = 5;
    //Integer representing a required tool that has been collected
    protected static int MATCH = 6;
    //Image representing a tool that has been collected and checked off the list
    protected String CHECK_IMG = "images/check.png";
    //Boolean indicating if the game has been won
    private boolean win = false;
    //The toolbox object from the Toolbox class (below)
    ToolBox box;

    //Storing the number of playable rows and columns
    int num_rows = 0;
    int num_cols = 0;

    Random rand = new Random();

    boolean Debug;
    public CreativeGame() {
        this(DEFAULT_GRID_H, DEFAULT_GRID_W);
    }
    
    public CreativeGame(int grid_h, int grid_w){
         this(grid_h, grid_w, DEFAULT_TIMER_DELAY,false);
    }
    
    public CreativeGame(int hdim, int wdim, int init_delay_ms, boolean debug) {
        super(init_delay_ms);
        //set up our "board" (i.e., game grid) 
        if (hdim<2 || wdim<2){//If either the provided width and height are less than 2 use default values
            hdim = DEFAULT_GRID_H;
            wdim = DEFAULT_GRID_W;
        }
        grid = new GameGrid(hdim, wdim);   
        this.Debug = debug;
        this.num_rows = grid.getNumRows()-1;
        this.num_cols = grid.getNumCols();
    }

    /******************** Methods **********************/
    protected void initGame(){
         // store and initialize user position
         box = new ToolBox(this.num_cols);//Initialize toolbox
         box.fillbox();//Fill it with random tools
         displayBox();//Display the contents of the box
         grid.setGameBackground(BG_IMG);
         player = new Location(DEFAULT_PLAYER_ROW, 0);
         grid.setCellImage(player, PLAYER_IMG);
         updateTitle();                           
    }
    
    // Display the intro screen
    // Notice the similarity with the while structure in play()
    // sleep is required to not consume all the CPU; going too fast freezes app 
    protected void displayIntro(){
        
       grid.setSplash(INTRO_IMG);
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
        }    
        updateTitle();
    }
    
    //This method iterates through the toolbox and displays the contents of it on the grid using the appropriate images
    private void displayBox(){
        for (int i=0; i<box.getLen(); i++){
            Location loc = new Location(num_rows,i);
            //Display the image indicating the tool in the toolbox at the current index, on the grid
            if (box.checkTool(i,HAMMER)){
                grid.setCellImage(loc,HAMMER_IMG);
            }
            else if (box.checkTool(i,WRENCH)){
                grid.setCellImage(loc,WRENCH_IMG);
            }
            else if (box.checkTool(i,SCREWDRIVER)){
                grid.setCellImage(loc,SCREWDRIVER_IMG);
            }
            else if (box.checkTool(i,PLIER)){
                grid.setCellImage(loc,PLIER_IMG);
            }
            else if (box.checkTool(i,DRILL)){
                grid.setCellImage(loc,DRILL_IMG);
            }
            else{
                //If the required tool has been collected display a checkmark
                grid.setCellImage(loc,CHECK_IMG);
            }
        }
    }
    //Randomly add objects to te right most column of the game
    private void populateRightEdge() {
        int height = this.num_rows;
        int width = this.num_cols;
        for (int i=0; i<height; i++){
            Location spot  = new Location(i,width-1);
            int rand_n = rand.nextInt(3);
            if (rand_n==1){
                grid.setCellImage(spot,ASTEROID_IMG);
            }
            else{
                int n1 = rand.nextInt(100);
                if (0<=n1 && n1<10){
                    grid.setCellImage(spot,FUEL_IMG);
                }
                else if (n1==10){
                    grid.setCellImage(spot,PORTAL_IMG);
                }
                else if (n1==12){
                    grid.setCellImage(spot,HAMMER_IMG);
                }
                else if (n1==13){
                    grid.setCellImage(spot,WRENCH_IMG);
                }
                else if (n1==14){
                    grid.setCellImage(spot,PLIER_IMG);
                }
                else if (n1==15){
                    grid.setCellImage(spot,SCREWDRIVER_IMG);
                }
                else if (n1==16){
                    grid.setCellImage(spot,DRILL_IMG);
                }
                else{
                    grid.setCellImage(spot,null);
                }
            }
        }
    }
    //Move the objects from right to left
    private void scrollLeft(){
        for (int col=0; col<this.num_cols-1;col++){
            for (int row=0; row<this.num_rows;row++){
                Location left = new Location(row,col);
                Location right = new Location(row, col+1);
                String right_img = grid.getCellImage(right);
                if (!PLAYER_IMG.equals(right_img)){
                    handleCollision();
                    grid.setCellImage(left,right_img);
                    grid.setCellImage(right,null);
                }
                if (player.equals(left)){
                    handleCollision();
                    grid.setCellImage(left,PLAYER_IMG);
                }
            }
        } 
    }
    
    
    /* handleCollision()
     * handle a collision between the user and an object in the game
     */    
    private void handleCollision() {
        String cell_img = grid.getCellImage(player);
        boolean scored = false;
        try{
            if (cell_img.equals(ASTEROID_IMG)){
                health-=5;//Hitting asteorids reduce health
            }
            else if (cell_img.equals(FUEL_IMG)){
                health += 5;//Hitting fuel tanks boost health
            }
            else if (cell_img.equals(PORTAL_IMG)){
                teleport();//Teleport when the user strikes a portal
            }
            else{
                //When the user hits a tool, try collecting it
                if (cell_img.equals(HAMMER_IMG)){
                    scored = box.collectTool(HAMMER);
                }
                else if (cell_img.equals(SCREWDRIVER_IMG)){
                    scored = box.collectTool(SCREWDRIVER);
                }
                else if (cell_img.equals(DRILL_IMG)){
                    scored = box.collectTool(DRILL);
                }
                else if (cell_img.equals(WRENCH_IMG)){
                    scored = box.collectTool(WRENCH);
                }
                else if (cell_img.equals(PLIER_IMG)){
                    scored = box.collectTool(PLIER);
                }
                if (scored){
                    //If the tool was collected increase the score and display the updated contents of the toolbox
                    score += 100;
                    displayBox();
                }
                if(this.Debug){
                    System.out.println(box);  
                }
                         
            }
        }
        catch(Exception e){
            if(this.Debug){
                System.out.println(e.toString());
            }
        }
        if(this.Debug){
            System.out.println("Health: " + health + "------Score: " + score);
        }
    }
    
    //Teleports player to a new random location
    private void teleport(){
        int r = player.getRow();
        int c = player.getCol();
        int new_r = rand.nextInt(this.num_rows);
        int new_c = rand.nextInt(this.num_cols-1);
        Location loc = new Location(new_r,new_c);
        String new_img = grid.getCellImage(loc);
        while (new_img != null){
            new_r = rand.nextInt(this.num_rows);
            new_c = rand.nextInt(this.num_cols-1);
            loc = new Location(new_r,new_c);
            new_img = grid.getCellImage(loc);
        }
        grid.setCellImage(new Location(r,c),null);
        player = new Location(new_r,new_c);
        grid.setCellImage(player,EXPLOSION_IMG);
        super.sleep(DEFAULT_TIMER_DELAY*2);
        grid.setCellImage(player,PLAYER_IMG);
        handleCollision();
    }

    //---------------------------------------------------//
    
    // handles actions upon mouse click in game
    private void handleMouseClick() {
        
        Location loc = grid.checkLastLocationClicked();
        
        if (loc != null){
            System.out.println("You clicked on a square " + loc);
            
        }  
    }
    private void move(int new_r, int new_c, String img, boolean limit){
        int r = player.getRow();
        int c = player.getCol();
        if (limit){
            grid.setCellImage(new Location(r,c),null);
            player = new Location(new_r,new_c);
        }
        handleCollision();
        PLAYER_IMG = img;
        grid.setCellImage(player,PLAYER_IMG);
    }
    // handles actions upon key press in game
    protected void handleKeyPress() {
        int key = grid.checkLastKeyPressed();
        //use Java constant names for key presses
        //http://docs.oracle.com/javase/7/docs/api/constant-values.html#java.awt.event.KeyEvent.VK_DOWN
        // Q for quit
        if (key == KeyEvent.VK_Q){
            System.exit(0);
        }
        else if (key == KeyEvent.VK_S){ //Take a screenshot
            grid.save("screenshot.png");
            System.out.println("screen saved");
        }
        else if (key == KeyEvent.VK_SPACE){
            screen += 1;
        }
        else if (key == KeyEvent.VK_DOWN){//Move user down
            int r = player.getRow();
            int c = player.getCol();
            move(r+1, c, PLAYER_IMG_DOWN, (r+1)<num_rows);
        }
        else if (key == KeyEvent.VK_UP){//Move user up
            int r = player.getRow();
            int c = player.getCol();
            move(r-1, c, PLAYER_IMG_UP, (r-1)>=0);
        }
        else if (key == KeyEvent.VK_RIGHT){//Move user right
            int r = player.getRow();
            int c = player.getCol();
            move(r, c+1, PLAYER_IMG_RIGHT, (c+1)<this.num_cols-1);
        }
        else if (key == KeyEvent.VK_LEFT){//Move user left
            int r = player.getRow();
            int c = player.getCol();
            move(r, c-1, PLAYER_IMG_LEFT, (c-1)>=0);
        }
        else if (key == KeyEvent.VK_D){//Display and hide the grid lines
            Color red = Color.RED;
            Color grid_color = grid.getLineColor();
            if (grid_color == null){
                grid.setLineColor(red);
            }
            else{
                grid.setLineColor(null);
            }
        }
        else if (key == KeyEvent.VK_P){//Pause the game
            if (factor == STARTING_FACTOR){
                factor = 10000000;
            }
            else{
                factor = STARTING_FACTOR;
            }
        }
        else if (key == KeyEvent.VK_COMMA){//Slow the game down
            factor += 1;
        }
        else if (key == KeyEvent.VK_PERIOD){//Speed the game up
            if (factor > 1){
                factor -= 1;
            }
        }
        else if (key == KeyEvent.VK_T){
            boolean interval =  (turnsElapsed % factor == 0);
            System.out.println("timerDelay " + timerDelay + " msElapsed reset " + 
                               msElapsed + " interval " + interval);
        } 
    }
    
    // update the title bar of the game window 
    private void updateTitle() {
        grid.setTitle("Save the ship:  " + "[Health: " + health + "------Score: " + score + "]");
    }
    
    // return true if the game is finished, false otherwise
    //      used by play() to terminate the main game loop 
    protected boolean isGameOver() {
        if (health==0){//If the user's health is 0 the game ends
            return true;
        }
        if (box.complete()){//If the user has collected all required tools they win the game and the game ends
            win = true;
            return true;
        }
        return false;
    }

    
    // display the game over screen
    protected void displayOutcome() {
         if (isGameOver()){
            if (win){
                grid.setSplash(WIN_IMG);
            }else{
                grid.setSplash(GAME_OVER_IMG);
            }
         }
    }
}
class ToolBox{
    //Toolbox class which abstracts a toolbox for the user to collect and store tools
    Random rand = new Random();
    //An integer array, the length of the number of columns in the game, represents the toolbox
    private int[] box;
    public ToolBox(int cols){
        this.box = new int[cols];
    }
    public int getLen(){
        return this.box.length;//Return the box length
    }
    public void fillbox(){
        for (int i=0; i<this.box.length; i++){
            this.box[i] = rand.nextInt(CreativeGame.NUM_TOOLS);//Fill the box with random tools
        }
    }
    public boolean checkTool(int index, int tool){
        return this.box[index] == tool;//Check if the tool at a given index in the box is the desired tool
    }
    public String toString(){
        //To string method to represent the box array as a string
        String str = "[";
        for (int i=0; i<this.box.length-1; i++){
            str += this.box[i] + ",";
        }
        str+= this.box[this.box.length-1] + "]";
        return str;
    }
    public boolean collectTool(int tool){
        //Collect a tool
        for (int i=0; i<this.box.length; i++){
            if (this.box[i]==tool){
                this.box[i] = CreativeGame.MATCH;//If the user collects a tool that is required to collect, the required tool is replaced by the MATCH integer (indicating that it has been collected)
                return true;
            }
        }
        return false;
    }
    public boolean complete(){
        //Check if the user has collected all requied tools
        for (int i=0; i<this.box.length; i++){
            if (this.box[i]!=CreativeGame.MATCH){
                return false;//If any of the required tools have not yet been collected return false
            }
        }
        return true;//Return true if all tools have been collected
    }
}