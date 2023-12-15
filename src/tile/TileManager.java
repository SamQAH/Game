package tile;

import java.awt.Graphics2D;


import javax.imageio.ImageIO;

import chunk.Chunk;
import main.GamePanel;

public class TileManager {
    GamePanel gp;
    Tile[] tiles;
    String data;
    

    public TileManager(GamePanel gp, String data){
        this.gp = gp;
        this.data = data.strip();
        tiles = new Tile[10]; // types of static tiles
        this.getTileImage();
    }

    public void getTileImage(){
        try{
            tiles[0] = new Tile();
            tiles[0].image = ImageIO.read(getClass().getResourceAsStream("/res/ground_field/field_tile.png"));

        }catch(Exception e){
            e.printStackTrace();
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

        String[] strKeys = data.split(" ");

        for(int i = 0; i<strKeys.length;i++){
            row = i % Chunk.chunkSize[0];
            col = i / Chunk.chunkSize[1];
            int worldx = row*gp.TILESIZE+chunkid[0]*Chunk.chunkSize[0]*gp.TILESIZE;
            int worldy = col*gp.TILESIZE+chunkid[1]*Chunk.chunkSize[1]*gp.TILESIZE;
            int screenx = pScreenPos[0]-pWorldPos[0]+worldx;
            int screeny = pScreenPos[1]-pWorldPos[1]+worldy;

            if(screenx>-gp.TILESIZE && screenx<gp.SCREENWIDTH &&
                screeny>-gp.TILESIZE && screeny<gp.SCREENHEIGHT ){
                g2.drawImage(tiles[Integer.parseInt(strKeys[i])].image, screenx,screeny,gp.TILESIZE,gp.TILESIZE,null);
            }
        }

            

        
    }
}
