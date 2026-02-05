//Basic Game Application
//Version 2
// Basic Object, Image, Movement
// Astronaut moves to the right.
// Threaded

//K. Chun 8/2018

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;


//*******************************************************************************
// Class Definition Section

public class BasicGameApp implements Runnable {

   //Variable Definition Section
   //Declare the variables used in the program 
   //You can set their initial values too
   
   //Sets the width and height of the program window
	final int WIDTH = 1000;
	final int HEIGHT = 700;

   //Declare the variables needed for the graphics
	public JFrame frame;
	public Canvas canvas;
   public JPanel panel;
   
	public BufferStrategy bufferStrategy;
	public Image whalePic;
    public Image whalersPic;
    public Image fishPic;
    public Image squidPic;
    public Image background;

   //Declare the objects used in the program
   //These are things that are made up of more than one variable type
	private Whale whaled;
    private Whaler whaleTeam;
    private fish goldfish;
    private squid giantSquid;
    private int whaleHealth = 3;


   // Main method definition
   // This is the code that runs first and automatically
	public static void main(String[] args) {
		BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
		new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method  
	}


   // Constructor Method
   // This has the same name as the class
   // This section is the setup portion of the program
   // Initialize your variables and construct your program objects here.
	public BasicGameApp() {
      
      setUpGraphics();
       
      //variable and objects
      //create (construct) the objects needed for the game and load up
        background=Toolkit.getDefaultToolkit().getImage("northsea.jpg");
		whalePic = Toolkit.getDefaultToolkit().getImage("whale.png"); //load the picture
		whaled = new Whale(699,100);
        whalersPic=Toolkit.getDefaultToolkit().getImage("whalers.png");
        whaleTeam=new Whaler(31,45);
        fishPic=Toolkit.getDefaultToolkit().getImage("fish.jpeg");
        goldfish=new fish(132,45);
        squidPic= Toolkit.getDefaultToolkit().getImage("squid.jpeg");
        giantSquid =new squid(0,550);


	}// BasicGameApp()

   
//*******************************************************************************
//User Method Section
//
// put your code to do things here.

   // main thread
   // this is the code that plays the game after you set things up
	public void run() {

      //for the moment we will loop things forever.
		while (true) {

         moveThings();  //move all the game objects
         render();  // paint the graphics
         pause(20); // sleep for 10 ms
		}
	}



	public void moveThings()
	{
      //calls the move( ) code in the objects


        whaled.move();
        whaleTeam.move();
        goldfish.move();
        giantSquid.move();
        interactions();

	}
    public void interactions(){
        if(whaleTeam.hitbox.intersects(whaled.hitbox2)){
            System.out.println("crash!!!!!!!!");
            whaleTeam.dx=-whaleTeam.dx;
            whaled.dx=-whaled.dx;
            whaleTeam.dy=-whaleTeam.dy;
            whaled.dy=-whaled.dy;

            // Decrease whale health instead of instant death
            whaleHealth=whaleHealth-1;
            System.out.println("Whale hit!!!!:( Health remaining: " + whaleHealth);

            // Check if whale is dead
            if(whaleHealth == 0){
                whaled.isAlive=false;
                System.out.println("Whale has died!");
            }
        }

        if(goldfish.Hitbox3.intersects(whaled.hitbox2)){
            goldfish.isAlive=false;
        }
    }


    //Pauses or sleeps the computer for the amount specified in milliseconds
   public void pause(int time ){
   		//sleep
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {

			}
   }

   //Graphics setup method
   private void setUpGraphics() {
      frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.
   
      panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
      panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
      panel.setLayout(null);   //set the layout
   
      // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
      // and trap input events (Mouse and Keyboard events)
      canvas = new Canvas();  
      canvas.setBounds(0, 0, WIDTH, HEIGHT);
      canvas.setIgnoreRepaint(true);
   
      panel.add(canvas);  // adds the canvas to the panel.
   
      // frame operations
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
      frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
      frame.setResizable(false);   //makes it so the frame cannot be resized
      frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!
      
      // sets up things so the screen displays images nicely.
      canvas.createBufferStrategy(2);
      bufferStrategy = canvas.getBufferStrategy();
      canvas.requestFocus();
      System.out.println("DONE graphic setup");
   
   }


	//paints things on the screen using bufferStrategy
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);
        g.drawImage(background, 0,0, 1000, 700,null);

        // Draw the whale (only if alive)
        if(whaled.isAlive) {
            g.drawImage(whalePic, whaled.xpos, whaled.ypos, whaled.width, whaled.height, null);
        }

        g.drawImage(whalersPic,whaleTeam.xpos,whaleTeam.ypos, whaleTeam.width,whaleTeam.height,null);

        if(goldfish.isAlive==true) {
            g.drawImage(fishPic, goldfish.xpos, goldfish.ypos, goldfish.width, goldfish.height, null);
        }

        g.drawImage(squidPic,giantSquid.xpos,giantSquid.ypos, giantSquid.width,giantSquid.height,null);

        // Display whale health on screen

        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Whale Health: " + whaleHealth, 20, 30);

        g.dispose();
        bufferStrategy.show();
    }
}