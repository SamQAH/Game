package Utility;

import chunk.Chunk;
import entitiy.Entity;
import java.util.ArrayList;

public class CollisionManager{
  private static final int SUBCHUNKSIZE = 8;
  private static final int LOADEDCHUNKAREA = 3;//3 X 3 around the player or moving entity

  public static int counter = 0;

  ArrayList<ArrayList<CollisionBox>> subChunk;
  ArrayList<CollisionBox> watchArea; // contains sub chunk coordinates that needs to be checked
  CollisionBox watchAreaBox;
  Entity referenceEntity;
  int[] referenceChunk;

  public CollisionManager(Entity refernce){
    //int amount = LOADEDCHUNKAREA*Chunk.chunkSize[0]/SUBCHUNKSIZE;
    referenceEntity = refernce;
    referenceChunk = referenceEntity.getCurrentChunk();
    watchAreaBox = new CollisionBox(2500, 2500, referenceEntity.getWorldx()-1250, referenceEntity.getWorldx()-1250);
    // subChunk = new ArrayList[amount][amount];
    // for(int i = 0; i < amount; i++){
    //   for(int j = 0 ; j < amount ; j++){
    //     subChunk[i][j] = new ArrayList<>();
    //   }
    // }
    
  }
  // public void addStaticCollisionBox(CollisionBox cb){
  //   //need to reference the player's current chunk TODO
  //   //the chunk of the cb subtract the reference
  //   int[] refOrigin = Chunk.toGlobalOrigin(Chunk.addToAll(referenceChunk,1));
  //   if(cb.globalx <= refOrigin[0] || cb.globalx >= refOrigin[0] + LOADEDCHUNKAREA*Chunk.chunkSize[0] || cb.globaly <= refOrigin[1] || cb.globaly >= refOrigin[1] + LOADEDCHUNKAREA*Chunk.chunkSize[1]){
  //     return;
  //   }
  //   int subChunkRow = fastfloor((cb.globalx-refOrigin[0])/(double)SUBCHUNKSIZE);
  //   int subChunkCol = fastfloor((cb.globaly-refOrigin[1])/(double)SUBCHUNKSIZE);
  //   subChunk[subChunkRow][subChunkCol].add(cb);
  // }

  public void setReferenceEntity(Entity e){
    referenceEntity = e;
    referenceChunk = referenceEntity.getCurrentChunk();
  }

  public void print(){
    // for(ArrayList<CollisionBox> arr : subChunk){
    //   for(CollisionBox cb : arr){
    //     System.out.print(cb.globalx+" "+cb.globaly+" ");
    //   }
    //   System.out.println();
    // }
    for(CollisionBox cb : watchArea){
      System.out.print(cb.globalx+" "+cb.globaly+" ");
    }
    System.out.println();
    System.out.println(referenceEntity.x + " "+ referenceEntity.y);
  }

  public void update(){
    /*
     * TODO temporay mesure
     */
    counter++;
    if(counter == 60){
      counter = 0;
      updateWatchArea();
      //print();
      
    }
    referenceEntity.hitbox.reset();
    referenceEntity.hitbox.addCollidePlural(watchArea);
    referenceEntity.updateVelocity();
  }

  public void updateSubChunks(ArrayList<ArrayList<CollisionBox>> area){
    subChunk = area;
    updateWatchArea();
  }

  public void updateWatchArea(){
    /*
     * calls every second
     */
    watchArea = new ArrayList<>();
    watchAreaBox.moveTo(referenceEntity.x-1250, referenceEntity.y-1250);
    for(ArrayList<CollisionBox> arr : subChunk){
      for(CollisionBox cb : arr){
        if(cb.checkCollide(watchAreaBox)){
          watchArea.add(cb);
        }
      }
    }

  }

  private int fastfloor(double x){
    return x<0 ? (int)(x-1) : (int)x;
  }
}
