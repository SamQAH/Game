package Utility;

import java.awt.image.BufferedImage;

import org.json.JSONObject;

import java.util.HashMap;

/* stores a buffered image containing frames of animation for cyclic purposees,
 * gets the current frame of the animation from a String key to a json file
 * stores how many frames each animation key has been called with hashmap, does not reset counter
 * assumes each frame is aligned vertically and goes down by height
 */
public class AnimationHandler {
    BufferedImage spritesheet;

    HashMap<String,int[]> jsonData;
    HashMap<String,Integer> counter;

    JSONObject metadata;

    HashMap<String,Animation> ani;


    public AnimationHandler(BufferedImage ss, JSONObject jsonobj){
        spritesheet = ss;
        metadata = jsonobj;
        jsonData = new HashMap<>();
        counter = new HashMap<>();
        ani = new HashMap<>();
        for(String k : metadata.keySet()){
            counter.put(k,0);
            int[] tempinfo = parseKey(k);
            jsonData.put(k,tempinfo);

            BufferedImage sss = spritesheet.getSubimage(tempinfo[2], tempinfo[3], tempinfo[4], tempinfo[5]*tempinfo[0]);
            ani.put(k,new Animation(sss,metadata.getJSONObject(k)));
        }
    }

    public Animation geAnimation(String key){
        return ani.get(key);
    }

    public BufferedImage getCurrentFrame(String key){

        int[] currentData = jsonData.get(key);

        int counternum = counter.get(key).intValue();
        counternum++;
        counter.replace(key, counternum);
        
        BufferedImage image = spritesheet.getSubimage(currentData[2], currentData[3] + currentData[5] * (counternum/currentData[1] % currentData[0]), currentData[4], currentData[5]);
        return image;
    }

    public BufferedImage getCurrentFrame(String key, int counter){
        int[] currentData = jsonData.get(key);
        BufferedImage image = spritesheet.getSubimage(currentData[2], currentData[3] + currentData[5] * (counter/currentData[1] % currentData[0]), currentData[4], currentData[5]);
        return image;
    }

    public int[] parseKey(String key){
        int[] temp = new int[6];

        JSONObject currentdata = metadata.getJSONObject(key);
        temp[0]  = currentdata.getInt("frames");
        temp[1]  = currentdata.getInt("frame delay");
        temp[2]  = currentdata.getInt("x");
        temp[3]  = currentdata.getInt("y");
        temp[4]  = currentdata.getInt("width");
        temp[5]  = currentdata.getInt("height");
        return temp;
    }
    
}
