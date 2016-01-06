
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author sevcm7279
 */


public class SquareInavaders extends JComponent{

    // Height and Width of our game
    static final int WIDTH = 760;
    static final int HEIGHT = 600;
    
    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000)/desiredFPS;
    
    //create the user player
    //start at the centre near the bottom of the screen
    Rectangle player = new Rectangle(360, 520, 40, 40);
    
    
    //create rectangle array liost for the enemy squares
    ArrayList<Rectangle> blocks = new ArrayList<>();
    
    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g)
    {
        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);
        
        // GAME DRAWING GOES HERE 
        
        for(Rectangle block: blocks){
            g.fillRect(block.x, block.y, block.width, block.height);
        }
        //put player on screen
        //make the player blue
        g.setColor(Color.BLUE);
        g.fillRect(player.x, player.y, player.width, player.height);
        
        // GAME DRAWING ENDS HERE
    }
    
    
    // The main game loop
    // In here is where all the logic for my game will go
    public void run()
    {
        
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
        while(!done)
        {
            // determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();
            
            // all your game rules and move is done in here
            // GAME LOGIC STARTS HERE 
            
            

            // GAME LOGIC ENDS HERE 
            
            // update the drawing (calls paintComponent)
            repaint();
            
            
            
            // SLOWS DOWN THE GAME BASED ON THE FRAMERATE ABOVE
            // USING SOME SIMPLE MATH
            deltaTime = System.currentTimeMillis() - startTime;
            if(deltaTime > desiredTime)
            {
                //took too much time, don't wait
            }else
            {
                try
                {
                    Thread.sleep(desiredTime - deltaTime);
                }catch(Exception e){};
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
        game.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        // adds the game to the window
        frame.add(game);
         
        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);
        
        // starts my game loop
        game.run();
    }
}
