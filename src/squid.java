import java.awt.*;

public class squid {
    public String name;                //holds the name of the hero
    public int xpos;                //the x position
    public int ypos;                //the y position
    public int dx;                    //the speed of the hero in the x direction
    public int dy;                    //the speed of the hero in the y direction
    public int width;
    public int height;
    public boolean isAlive;
    public Rectangle hitbox;
    //a boolean to denote if the hero is alive or dead.


    // METHOD DEFINITION SECTION

    // Constructor Definition
    // A constructor builds the object when called and sets variable values.


    //This is a SECOND constructor that takes 3 parameters.  This allows us to specify the hero's name and position when we build it.
    // if you put in a String, an int and an int the program will use this constructor instead of the one above.
    public squid(int pXpos, int pYpos) {
        xpos = pXpos;
        ypos = pYpos;
        dx =1;
        dy =1/9;
        width = 150;
        height = 100;
        isAlive = true;
        hitbox=new Rectangle(xpos,ypos,width,height);

    } // constructor

    //The move method.  Everytime this is run (or "called") the hero's x position and y position change by dx and dy
    public void move() {

        dy += dy + Math.sin(dx * 0.05) * 0.3;
        xpos=xpos+dx;
        hitbox= new Rectangle(xpos,ypos,width,height);
        ypos = ypos + dy+ (int)(Math.sin(xpos * -0.05) * 5);
        ;
        hitbox= new Rectangle(xpos,ypos,width,height);


    }

}
