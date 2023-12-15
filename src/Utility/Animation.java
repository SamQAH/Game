package Utility;

import java.awt.image.BufferedImage;

import org.json.JSONObject;

public class Animation {
    BufferedImage[] bi;
    int counter;
    int delay;
    int frames;

    public Animation(BufferedImage spritesheet, JSONObject releventData){
        
        frames  = releventData.getInt("frames");
        delay  = releventData.getInt("frame delay");
        int w  = releventData.getInt("width");
        int h  = releventData.getInt("height");
        bi = new BufferedImage[frames];
        for(int i = 0; i < frames;i++){
            bi[i] = spritesheet.getSubimage(0, i*h, w, h);
        }
    }

    public BufferedImage getCurrentFrame(){
        return bi[((counter++)/delay)%frames];
    }

    public BufferedImage getCurrentFrame(int counter){
        return bi[(counter/delay)%frames];
    }
}
