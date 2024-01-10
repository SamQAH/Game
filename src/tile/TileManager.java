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

public class TileManager {
    GamePanel gp;
    Tile[] tiles;
    int[][] tileData;
    

    public TileManager(GamePanel gp, int[][] data){
        this.gp = gp;
        this.tileData = data;
        this.getTileImage();

    }

    public void getTileImage(){
        tiles = new Tile[20]; // types of tiles
        try{
            BufferedImage oceanSS = ImageIO.read(getClass().getResourceAsStream("/res/ocean/Ocean_Sand_Blend_Master.png"));
            String location = new String("src/res/ocean/Ocean_Sand_Blend.json");
            File file = new File(location);
            String content = new String(Files.readAllBytes(Paths.get(file.toURI())));
            JSONObject oceandata = new JSONObject(content);
            AnimationHandler ah = new AnimationHandler(oceanSS, oceandata);

            for(int i = 0; i < 20; i++){
                tiles[i] = new Tile();
            }
            tiles[0].image = ImageIO.read(getClass().getResourceAsStream("/res/ground_field/grassy_field.png"));
            tiles[1].animation = ah.getAnimation("open ocean");
            tiles[2].animation = ah.getAnimation("ocean blend up");
            tiles[3].animation = ah.getAnimation("ocean blend right");
            tiles[4].animation = ah.getAnimation("ocean blend down");
            tiles[5].animation = ah.getAnimation("ocean blend left");
            tiles[6].animation = ah.getAnimation("ocean blend inside upright");
            tiles[7].animation = ah.getAnimation("ocean blend inside upleft");
            tiles[8].animation = ah.getAnimation("ocean blend inside downleft");
            tiles[10].animation = ah.getAnimation("ocean blend inside downright");
            tiles[11].animation = ah.getAnimation("ocean blend outside upright");
            tiles[12].animation = ah.getAnimation("ocean blend outside upleft");
            tiles[13].animation = ah.getAnimation("ocean blend outside downleft");
            tiles[14].animation = ah.getAnimation("ocean blend outside downright");
            tiles[15].animation = ah.getAnimation("ocean blend inside");
            tiles[16].animation = ah.getAnimation("ocean blend outside");
            //tiles[11].image = ImageIO.read(getClass().getResourceAsStream("/res/ground_field/field_tile.png"));
            tiles[9].image = ImageIO.read(getClass().getResourceAsStream("/res/ocean/Sand.png"));

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public void draw(Graphics2D g2, int[] chunkid, int counter){
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


        while(col != tileData.length){


            int worldx = row*gp.TILESIZE+chunkid[0]*Chunk.chunkSize[0]*gp.TILESIZE;
            int worldy = col*gp.TILESIZE+chunkid[1]*Chunk.chunkSize[1]*gp.TILESIZE;
            int screenx = pScreenPos[0]-pWorldPos[0]+worldx;
            int screeny = pScreenPos[1]-pWorldPos[1]+worldy;

            if(screenx>-gp.TILESIZE && screenx<gp.SCREENWIDTH && screeny>-gp.TILESIZE && screeny<gp.SCREENHEIGHT ){
                Tile currenttile = tiles[tileData[col][row]];
                BufferedImage display;
                if(currenttile.animation == null){
                    display = currenttile.image;
                }else{
                    display = currenttile.animation.getCurrentFrame(counter);
                }
                g2.drawImage(display, screenx,screeny,gp.TILESIZE,gp.TILESIZE,null);

            }
            row++;
            if(row == tileData[0].length){
                row = 0;
                col++;
            }
        }
    }
}

