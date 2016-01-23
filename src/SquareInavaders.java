
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

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
    //playing music
    boolean music = false;
    //shooting
    boolean shoot = false;
    //player position varibles
    int moveX = 0;
    int moveY = 0;
    //bullet posistion variable   
    int positionX = player.x + 15;
    //number of lives you start with
    int life = 3;
    //create rectangle array list for the enemy squares
    ArrayList<Rectangle> blocks = new ArrayList<>();
    //add image for array (enemy)
    BufferedImage alien = loadImage("alien.png");
    //add image for player (ship)
    BufferedImage ship2 = loadImage("ship2.jpg");
    //create array for player bullets
    Rectangle bullet = new Rectangle(positionX, -10, 10, 10);
    //create an array for lives
    ArrayList<Rectangle> lives = new ArrayList<>();
    //create an array for enemy bullet
    Rectangle bulletE = new Rectangle(-100, 610, 10, 10);
    //add a you win image
    BufferedImage win = loadImage("youWin.PNG");
    //add backround image
    BufferedImage galaxy = loadImage("stars.jpg");
    //enemy is not shooting
    boolean enemyShoot = false;
    //enemy is not attacking
    boolean attack = false;
    //create an array list for the final boss
    ArrayList<Rectangle> finalBoss = new ArrayList<>();
    //add image for final bosses
    BufferedImage boss = loadImage("boss.PNG");
    //import number pictures
    BufferedImage n3 = loadImage("3.bmp");
    BufferedImage n2 = loadImage("2.bmp");
    BufferedImage n1 = loadImage("1.bmp");
    //method to import images
    long start = System.currentTimeMillis();

    public BufferedImage loadImage(String file) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(file));
        } catch (Exception e) {
            System.out.println("Error reading picture " + file);
        }
        return img;

    }

    //create a method to play music
    public static void play() {
        try {
            String Pump = "BGM.wav";
            InputStream in = new FileInputStream("BGM.wav");
            AudioStream audioStream = new AudioStream(in);
            AudioPlayer.player.start(audioStream);
        } catch (Exception e) {
        }

    }

    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g) {
        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);

        // GAME DRAWING GOES HERE 
        //galaxy background
        g.drawImage(galaxy, 0, 0, 800, 600, null);

        //draw numbers for count down
        //if one second has passed
        if (System.currentTimeMillis() < start) {
            if ((start - System.currentTimeMillis()) / 1000 == 2) {
                //draw the number one
                g.drawImage(n3, 300, 200, 200, 280, null);
            }
            if ((start - System.currentTimeMillis()) / 1000 == 1) {
                //draw the number one
                g.drawImage(n2, 300, 200, 200, 280, null);
            }
            if ((start - System.currentTimeMillis()) / 1000 == 0) {
                //draw the number one
                g.drawImage(n1, 300, 200, 200, 280, null);
            }
        }
        //draw life count
        for (Rectangle life : lives) {
            g.drawImage(ship2, life.x, life.y, life.width, life.height, null);
        }
        //make the blocks in the enemy array an image
        for (Rectangle block : blocks) {
            g.drawImage(alien, block.x, block.y, block.width, block.height, null);
        }
        //put player on screen (as an image)
        g.drawImage(ship2, player.x, player.y, player.width, player.height, null);

        //if shoot, player bullet is drawn
        if (shoot) {
            //set the bullet colour to yellow
            g.setColor(Color.yellow);
            //draw th player bullet
            g.fillRect(bullet.x, bullet.y, 10, 10);

        }
        //draw the enemy bullet of the screen
        //set color of enemy bullet to red
        g.setColor(Color.red);
        //draw the enemy bulllet
        g.fillRect(bulletE.x, bulletE.y, 10, 10);

        //draw the final bosses off the screen
        for (Rectangle bosses : finalBoss) {
            g.drawImage(boss, bosses.x, bosses.y, bosses.width, bosses.height, null);
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

        //add lives
        lives.add(new Rectangle(730, 570, 20, 20));
        lives.add(new Rectangle(700, 570, 20, 20));
        lives.add(new Rectangle(670, 570, 20, 20));

        //create bosses
        finalBoss.add(new Rectangle(200, -200, 100, 70));
        finalBoss.add(new Rectangle(500, -200, 100, 70));

        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime;
        long deltaTime;

        start = System.currentTimeMillis() + 3000;

        // the main game loop section
        // game will end if you set done = false;
        boolean done = false;
        while (!done) {
            // determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();

            // all your game rules and move is done in here
            // GAME LOGIC STARTS HERE 
            int numberOfAliens = blocks.size();

            if (System.currentTimeMillis() > start) {
                music = true;
                if (music = true) {

                }
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

                //animate enemy array
                //randomly select aliens to move, so it's not uniform
                //only move if the player is alive
                if (player.y < 601) {
                    int numeBlocks = blocks.size();
                    int randomInt = (int) (Math.random() * numeBlocks);
                    if (numberOfAliens > 0) {
                        Rectangle eBlock = blocks.get(randomInt);

                        //make aliens follow player
                        //match player's y coordinate
                        if (eBlock.y != player.y + 600) {
                            eBlock.y += 2;
                            //match player's x coordinate
                            if (eBlock.x > player.x) {
                                eBlock.x -= 3;
                            } else {
                                eBlock.x += 3;
                            }
                        }
                    }
                }

                //make player stop at edges of screen
                //if player reaches right side
                if (player.x + player.width > WIDTH) {
                    player.x = WIDTH - player.width;
                    moveX = 0;
                    //if player reaches left side
                } else if (player.x < 0) {
                    player.x = 0;
                    moveX = 0;
                }

                //when player shoot is true
                if (shoot) {
                    //if the bullet is not off the screen
                    //bullet starts at player
                    if (bullet.y <= -10) {
                        bullet.x = player.x + 15;
                        bullet.y = player.y;
                    }
                    //bullet keeps player x coordinate
                    //bullet moves up
                    bullet.y -= 6;

                }
                //if the bullet is off the screen
                //make shooting false so player can shoot agian
                if (bullet.y < -9) {
                    shoot = false;

                }

                //intersections
                //go through the enemy array
                Iterator<Rectangle> it = blocks.iterator();
                while (it.hasNext()) {
                    Rectangle block = it.next();
                    //is the bullet impacting an enemy
                    if (bullet.intersects(block)) {
                        //make shooting to false
                        //delete enemy
                        shoot = false;
                        bullet.y = -10;
                        it.remove();
                    }
                }

                //select a random block in the enemy array
                if (bulletE.y <= 610) {
                    //enemy bullet speed
                    bulletE.y += 6;
                    //only shoot if the player is alive
                    if (player.y < 601) {
                        if (enemyShoot == false) {
                            int numBlocks = blocks.size();
                            int randInt = (int) (Math.random() * numBlocks);
                            if (numberOfAliens > 0) {
                                Rectangle aBlock = blocks.get(randInt);
                                //when it is selected, shoot
                                enemyShoot = true;
                                //set the coordinates of the bullet to 
                                //centre of the alien
                                bulletE.y = aBlock.y;
                                bulletE.x = aBlock.x + 15;
                            }
                            //enemy bullet hitting player
                            if (bulletE.intersects(player)) {
                                //player disappears off screen
                                player.y += 100;
                                enemyShoot = true;
                            }
                            //if the bullet goes off the screen       
                        } else if (bulletE.y >= 611) {
                            //make shooting false
                            enemyShoot = false;
                            //make bullet return to orginal posistion
                            bulletE.y = 610;
                        }

                    }
                }

                //enemy bullet hitting player
                if (bulletE.intersects(player)) {
                    //take away a life
                    life--;
                    //bullet goes off screen
                    bulletE.y = -90;
                    bulletE.x = -10;
                    //if there are no more lives
                    if (life == 0) {
                        //player disapppears
                        player.y += 100;

                    }
                }

                //If player gets hit, take away lives
                //if the player loses one life
                //move life counter off screen
                if (life == 2) {
                    lives.get(2).y += 90;
                    //if the player loses another life
                    //move life counter off screen
                } else if (life == 1) {
                    lives.get(1).y += 90;
                    //if the player has no lives
                    //move last life counter off screen
                } else if (life == 0) {
                    lives.get(0).y += 90;
                }

                //if all of the aliens are dead
                if (numberOfAliens == 0) {
                    bulletE.y = 609;
                    //bring out the bosses
                    //go through boss array and move them down
                    for (Rectangle bosses : finalBoss) {
                        if (bosses.y < 150) {
                            bosses.y += 5;
                        }
                    }
                    //make boss shoot using randomizer
                    int numBosses = finalBoss.size();
                    int randomBoss = (int) (Math.random() * numBosses);
                    if (numBosses < 0) {
                        Rectangle oneBoss = finalBoss.get(randomBoss);
                        if (bulletE.y < 610) {
                            enemyShoot = true;
                            bulletE.y = oneBoss.y;
                            bulletE.x = oneBoss.x - 50;
                            bulletE.y += 6;
                        }
                    }

                }
            }

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

        }
    }
}
