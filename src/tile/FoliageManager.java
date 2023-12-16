package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.json.JSONObject;

import Utility.AnimationHandler;
import chunk.Chunk;
import main.GamePanel;

public class FoliageManager {
    AnimationHandler ah;
    GamePanel gp;
    Foliage[][] foli;

    public FoliageManager(GamePanel gp, String data){
        this.getAnimation();
        this.setFoliage(data.strip());
        this.gp = gp;
    }

    public void getAnimation(){
        try{
            BufferedImage ss = ImageIO.read(getClass().getResourceAsStream("/res/ground_field/field_overlay_animation.png"));
            String location = new String("src/res/ground_field/field_foliage.json");
            File file = new File(location);
            String content = new String(Files.readAllBytes(Paths.get(file.toURI())));
            JSONObject playerdata = new JSONObject(content);
            ah = new AnimationHandler(ss, playerdata);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setFoliage(String data){
        foli = new Foliage[Chunk.chunkSize[0]][Chunk.chunkSize[1]];
        String[] strKeys = data.split(" ");
        int row;
        int col;
        for(int i = 0; i<strKeys.length;i++){
            row = i % Chunk.chunkSize[0];
            col = i / Chunk.chunkSize[1];

            String name;
            switch(strKeys[i]){
                case "0":
                name = "grass 1";
                break;
                case "1":
                name = "grass 2";
                break;
                case "2":
                name = "grass 3";
                break;
                case "3":
                name = "grass 4";
                break;
                case "4":
                name = "flower 1";
                break;
                case "5":
                name = "flower 2";
                break;
                case "6":
                name = "flower 3";
                break;
                case "7":
                name = "flower 4";
                break;
                default:
                name = null;;
                break;
            }
            if(name != null){
                foli[row][col] = new Foliage(ah.getAnimation(name));

            }

        }

    }

    public void draw(Graphics2D g2, int[] chunkid){
        /*
         * loop through all loaded chunks
         * calculate the world coordinate of the tiles
         * calculate the screen coordinate of the tiles
         * draw tiles if between -TILESIZE and MAXSCREENWIDTH etc
         */
        int row = 0;
        int col = 0;
        int[] pWorldPos = gp.getPlayerWorldPos();
        int[] pScreenPos = gp.getPlayerScreenPos();

        while(col < Chunk.chunkSize[1]){

            int worldx = row*gp.TILESIZE+chunkid[0]*Chunk.chunkSize[0]*gp.TILESIZE;
            int worldy = col*gp.TILESIZE+chunkid[1]*Chunk.chunkSize[1]*gp.TILESIZE;
            int screenx = pScreenPos[0]-pWorldPos[0]+worldx;
            int screeny = pScreenPos[1]-pWorldPos[1]+worldy;

            if(foli[row][col] != null &&
                screenx>-gp.TILESIZE && screenx<gp.SCREENWIDTH &&
                screeny>-gp.TILESIZE && screeny<gp.SCREENHEIGHT ){
                foli[row][col].draw(g2,screenx,screeny);
            }

            row++;
            if(row >= Chunk.chunkSize[0]){
                row = 0;
                col++;
            }
        }
    }


}
