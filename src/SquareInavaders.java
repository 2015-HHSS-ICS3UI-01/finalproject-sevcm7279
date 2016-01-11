
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author sevcm7279
 */
public class SquareInavaders extends JComponent implements KeyListener {

    // Height and Width of our game
    static final int WIDTH = 760;
    static final int HEIGHT = 600;
    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000) / desiredFPS;
    //create the user player
    //start at the centre near the bottom of the screen
    Rectangle player = new Rectangle(360, 520, 40, 40);
    //keyboard variables
    //moving player
    boolean left = false;
    boolean right = false;
    //shooting
    boolean shoot = false;
    //player position varibles
    int moveX = 0;
    int moveY = 0;
    
    //create rectangle array list for the enemy squares
    ArrayList<Rectangle> blocks = new ArrayList<>();
    //add image for array (enemy)
    BufferedImage alien = loadImage("alien.png");
    //add image for player (ship)
    BufferedImage ship2 = loadImage("ship2.jpg");
    
    
    //method to import images
    public BufferedImage loadImage(String file){
        BufferedImage img = null;
        try{
            img = ImageIO.read(new File(file));
        }catch(Exception e){
            System.out.println("Error reading picture " + file);
        }
        return img;
                
    }
    
    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g) {
        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);

        // GAME DRAWING GOES HERE 

        //black background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 800, 600);
        for (Rectangle block : blocks) {
            g.drawImage(alien, block.x, block.y, block.width, block.height, null);
        }
        //put player on screen
        //make the player blue
        g.setColor(Color.BLUE);
        g.drawImage(ship2, player.x, player.y, player.width, player.height, null);
        
        //if shoot, bullet is drawn
        if (shoot){
            g.setColor(Color.yellow);
            g.fillRect(player.x + 15, player.y, 10, 10);
            
        }
   
        // GAME DRAWING ENDS HERE
    }

    // The main game loop
    // In here is where all the logic for my game will go
    public void run() {

        //add enemy squares in three rows
        //start at the top of the screen

        //first row
        blocks.add(new Rectangle(30, 0, 40, 40));
        blocks.add(new Rectangle(140, 0, 40, 40));
        blocks.add(new Rectangle(250, 0, 40, 40));
        blocks.add(new Rectangle(360, 0, 40, 40));
        blocks.add(new Rectangle(470, 0, 40, 40));
        blocks.add(new Rectangle(580, 0, 40, 40));
        blocks.add(new Rectangle(690, 0, 40, 40));

        //second row
        blocks.add(new Rectangle(85, 50, 40, 40));
        blocks.add(new Rectangle(195, 50, 40, 40));
        blocks.add(new Rectangle(305, 50, 40, 40));
        blocks.add(new Rectangle(415, 50, 40, 40));
        blocks.add(new Rectangle(525, 50, 40, 40));
        blocks.add(new Rectangle(635, 50, 40, 40));

        //third row
        blocks.add(new Rectangle(140, 100, 40, 40));
        blocks.add(new Rectangle(250, 100, 40, 40));
        blocks.add(new Rectangle(360, 100, 40, 40));
        blocks.add(new Rectangle(470, 100, 40, 40));
        blocks.add(new Rectangle(580, 100, 40, 40));
        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime;
        long deltaTime;

        // the main game loop section
        // game will end if you set done = false;
        boolean done = false;
        while (!done) {
            // determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();

            // all your game rules and move is done in here
            // GAME LOGIC STARTS HERE 

            //move player left
            if (left) {
                moveX = -5;
            //move player right
            } else if (right) {
                moveX = 5;
            //stop player when key released  
            } else {
                moveX = 0;
            }
            //add x movements to player
            player.x = player.x + moveX;
            
            
            int numBlocks = blocks.size();
            int randInt = (int)(Math.random()*numBlocks);
            Rectangle aBlock = blocks.get(randInt);
            
            
            //make player stop at edges of screen
            //if player reaches right side
            if (player.x + player.width > WIDTH){
                player.x = WIDTH - player.width;
                moveX = 0;
            //if player reaches left side
            } else if (player.x < 0){
                player.x = 0;
                moveX = 0;
            }

            //make alien array move down
            for (Rectangle block : blocks) {
                
                
                
            }
            if (shoot){
                
                
                
            }
            
            //move 

            // GAME LOGIC ENDS HERE 

            // update the drawing (calls paintComponent)
            repaint();



            // SLOWS DOWN THE GAME BASED ON THE FRAMERATE ABOVE
            // USING SOME SIMPLE MATH
            deltaTime = System.currentTimeMillis() - startTime;
            if (deltaTime > desiredTime) {
                //took too much time, don't wait
            } else {
                try {
                    Thread.sleep(desiredTime - deltaTime);
                } catch (Exception e) {
                };
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // creates a windows to show my game
        JFrame frame = new JFrame("My Game");

        // creates an instance of my game
        SquareInavaders game = new SquareInavaders();
        // sets the size of my game
        game.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // adds the game to the window
        frame.add(game);

        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);

        //add listeners
        frame.addKeyListener(game); //keyboard

        // starts my game loop
        game.run();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            left = true;
        } else if (key == KeyEvent.VK_RIGHT) {
            right = true;
        } else if (key == KeyEvent.VK_SPACE) {
            shoot = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            left = false;
        } else if (key == KeyEvent.VK_RIGHT) {
            right = false;
        } else if (key == KeyEvent.VK_SPACE) {
            shoot = false;
        }
    }
}
