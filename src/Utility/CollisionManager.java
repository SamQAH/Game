package Utility;

import entitiy.Entity;
import java.util.ArrayList;

public class CollisionManager{
  private static final int SUBCHUNKSIZE = 8;
  private static final int LOADEDCHUNKAREA = 3;//3 X 3 around the player or moving entity

  public static int counter = 0;
  public static final int TimeUntilWatchUpdate = 30;

  ArrayList<ArrayList<CollisionBox>> subChunk;
  ArrayList<CollisionBox> watchArea; // contains sub chunk coordinates that needs to be checked
  CollisionBox watchAreaBox;
  Entity referenceEntity;
  int[] referenceChunk;

  public CollisionManager(Entity refernce){
    //int amount = LOADEDCHUNKAREA*Chunk.chunkSize[0]/SUBCHUNKSIZE;
    referenceEntity = refernce;
    referenceChunk = referenceEntity.getCurrentChunk();
    setWatchArea();
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

  public void setWatchArea(){
    int addlength = referenceEntity.getSpeed() * TimeUntilWatchUpdate * 2;
    int w = addlength + referenceEntity.hitbox.width;
    int h = addlength + referenceEntity.hitbox.height;
    watchAreaBox = new CollisionBox(w, h, referenceEntity.getWorldx()-w/2, referenceEntity.getWorldy()-h/2);

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
    if(counter == TimeUntilWatchUpdate){
      counter = 0;
      updateWatchArea();
      //System.out.println("updated watch area");
      //referenceEntity.printCollide();
      //System.out.println("checking: "+watchArea.size());
      //System.out.println(watchAreaBox.globalx + " " + watchAreaBox.globaly);
      //referenceEntity.printPos();
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
     */
    watchArea = new ArrayList<>();
    watchAreaBox.moveTo(referenceEntity.worldx-(watchAreaBox.width-referenceEntity.hitbox.width)/2, referenceEntity.worldy-(watchAreaBox.height-referenceEntity.hitbox.height)/2);
    for(ArrayList<CollisionBox> arr : subChunk){
      for(CollisionBox cb : arr){
        if(cb.checkCollide(watchAreaBox)){
          watchArea.add(cb);
        }
      }
    }

  }

  public CollisionBox getWatchAreaBox(){
    return watchAreaBox;
  }

  public ArrayList<CollisionBox> getWatchArea(){
    return watchArea;
  }

  private int fastfloor(double x){
    return x<0 ? (int)(x-1) : (int)x;
  }
}
