package Utility;

import java.awt.image.BufferedImage;

import org.json.JSONObject;

/* Loads a buffered image to provide subimages from a key String supplied when call getImage,
 * looks in to a json provided to the constructor to find alignment variables
 */
public class SpriteHandler {
    private JSONObject metadata;
    private BufferedImage spritesheet;

    public SpriteHandler(BufferedImage spritesheet, JSONObject metadata){
        this.spritesheet = spritesheet;
        this.metadata = metadata;
    }

    public BufferedImage getImage(String key){
        JSONObject currentobj = metadata.getJSONObject(key);
        int x = currentobj.getInt("x");
        int y = currentobj.getInt("y");
        int w = currentobj.getInt("width");
        int h = currentobj.getInt("height");
        return spritesheet.getSubimage(x, y, w, h);
    }
}
