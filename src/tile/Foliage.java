package tile;

import java.awt.Graphics2D;

import Utility.Animation;

public class Foliage {

    int xoffset;
    int yoffset;
    Animation ah;
    String id; // what it's called in the json
    int counter = 0;

    public Foliage(Animation ah){
        this.ah = ah;
        xoffset = (int)(Math.random()*50-25);
        yoffset = (int)(Math.random()*50-25);
        //this.id = id;
    }

    public void draw(Graphics2D g2, int screenx, int screeny){
        g2.drawImage(ah.getCurrentFrame(counter), screenx+xoffset,screeny+yoffset,64,64, null);//reference by number, change later
        counter++;
    }
}
