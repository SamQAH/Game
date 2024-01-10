package chunk;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import entitiy.Player;
import main.GamePanel;


/*
 * drawing and animation of everything that is gurenteed to not move
 * 
 * One manager shared across all chunks in map
 */
public class ChunkManager {
    Player p;
    GamePanel gp;
    HashMap<int[],Chunk> loadedChunks;
    final int seed = 123456;

    public ChunkManager(GamePanel gp, Player player){
        this.gp = gp;
        this.p = player;
        loadedChunks = new HashMap<>();
        this.updateChunks();
    }

    public synchronized void updateChunks(){
        //find current chunk
        int[] currentChunk = new int[]{(int)Math.floor((double)p.worldx/gp.TILESIZE/Chunk.chunkSize[0]),(int)Math.floor((double)p.worldy/gp.TILESIZE/Chunk.chunkSize[1])};
        ArrayList<int[]> loadedChunksID = new ArrayList<>();
        for(int i = -1; i<2;i++){
            for(int j = -1;j<2;j++){
                loadedChunksID.add(new int[]{currentChunk[0]+i,currentChunk[1]+j});
            }
        }
        

        /*
         * remove unused chunks: if in loaded but not id
         * add new chunks: if in id but not loaded
         */
        Set<int[]> currentCoords = loadedChunks.keySet();

        ArrayList<int[]> marked = new ArrayList<>();
        for(int[] key : currentCoords){
            boolean found = false;
            for(int[] coord : loadedChunksID){
                if(Arrays.equals(key, coord)){
                    found = true;
                    break;
                }
            }
            if(!found){
                marked.add(key);
            }
        }

        for(int[] key : marked){
            currentCoords.remove(key);
            System.out.println("Removed "+key[0]+" "+key[1]);
        }

        for(int[] coord : loadedChunksID){
            boolean found = false;
            for(int[] key : currentCoords){
                if(Arrays.equals(key, coord)){
                    found = true;
                    break;
                }
            }
            if(!found){
                loadedChunks.put(coord, new Chunk(gp,coord,seed));
                System.out.println("Added "+coord[0]+" "+coord[1]);
            }
        }

    }

    public synchronized void draw(Graphics2D g2){
        for(int[] key : loadedChunks.keySet()){
            Chunk c = loadedChunks.get(key);
            c.draw(g2);
             
        }
    }
}
