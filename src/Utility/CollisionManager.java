package Utility;

import chunk.Chunk;
import java.util.ArrayList;

public class CollisionManager{
  private static final int SUBCHUNKSIZE = 8;
  private static final int LOADEDCHUNKAREA = 3;//3 X 3 around the player or moving entity

  ArrayList<CollisionBox>[][] subChunk;
  Entity referenceEntity;

  public CollisionManager(){
    int amount = LOADEDCHUNKAREA*Chunk.CHUNKSIZE[0]/SUBCHUNKSIZE;
    subChunk = new ArrayList()[amount][amount];
  }
  public void addStaticCollisionBox(CollisionBox cb){
    //need to reference the player's current chunk
    //the chunk of the cb subtract the reference
    int subChunkRow = fastfloor(cb.globalx/(double)SUBCHUNKSIZE);
    int subChunkCol = fastfloor(cb.globaly/(double)SUBCHUNKSIZE);
  }

  public void asetReferenceEntity(Entity e){
    referenceEntity = e;
  }

  private int fastfloor(double x){
    return x<0 ? (int)(x-1) : (int)x;
  }
}
