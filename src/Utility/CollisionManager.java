package Utility;

import chunk.Chunk;
import entitiy.Entity;
import java.util.ArrayList;

public class CollisionManager{
  private static final int SUBCHUNKSIZE = 8;
  private static final int LOADEDCHUNKAREA = 3;//3 X 3 around the player or moving entity

  ArrayList<CollisionBox>[][] subChunk;
  ArrayList<int[]> watchArea; // contains sub chunk coordinates that needs to be checked
  Entity referenceEntity;
  int[] referenceChunk;

  public CollisionManager(Entity refernce){
    int amount = LOADEDCHUNKAREA*Chunk.chunkSize[0]/SUBCHUNKSIZE;
    referenceEntity = refernce;
    referenceChunk = referenceEntity.getCurrentChunk();
    subChunk = new ArrayList[amount][amount];
    for(int i = 0; i < amount; i++){
      for(int j = 0 ; j < amount ; j++){
        subChunk[i][j] = new ArrayList<>();
      }
    }
    
  }
  public void addStaticCollisionBox(CollisionBox cb){
    //need to reference the player's current chunk
    //the chunk of the cb subtract the reference
    int[] refOrigin = Chunk.toGlobalOrigin(Chunk.addToAll(referenceChunk,1));
    if(cb.globalx <= refOrigin[0] || cb.globalx >= refOrigin[0] + LOADEDCHUNKAREA*Chunk.chunkSize[0] || cb.globaly <= refOrigin[1] || cb.globaly >= refOrigin[1] + LOADEDCHUNKAREA*Chunk.chunkSize[1]){
      return;
    }
    int subChunkRow = fastfloor((cb.globalx-refOrigin[0])/(double)SUBCHUNKSIZE);
    int subChunkCol = fastfloor((cb.globaly-refOrigin[1])/(double)SUBCHUNKSIZE);
    subChunk[subChunkRow][subChunkCol].add(cb);
  }

  public void setReferenceEntity(Entity e){
    referenceEntity = e;
    referenceChunk = referenceEntity.getCurrentChunk();
  }

  public void update(){
    /*
     * 
     */
    referenceEntity.updateVelocity();
  }

  public void updateSubChunks(){

  }

  public void updateWatchArea(){

  }

  private int fastfloor(double x){
    return x<0 ? (int)(x-1) : (int)x;
  }
}
