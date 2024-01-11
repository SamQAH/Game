package Utility;

import java.util.ArrayList;

public class CollisionBox{
  private static final double margin = 16; // true margin / 2
  int width, height, globalx, globaly, widthPartision, heightPartision;
  int[][] mesh;
  boolean[] collisionSides;
  
  public CollisionBox(int width, int height, int x, int y){
    this.width = width;
    this.height = height;
    globalx = x;
    globaly = y;
    setMesh();
  }

  public void setMesh(){
    collisionSides = new boolean[]{false,false,false,false};
    /*  0--->  coords around the perimeter
     *  T   |
     *  |   v
     *  <----
     */ 
    widthPartision = (int) Math.ceil(width / margin); // should be always >= 1
    heightPartision = (int) Math.ceil(height / margin);
    mesh = new int[2 * (widthPartision + heightPartision)][2];
    int[] currentCoords = new int[]{globalx, globaly};
    int counter = 0;
    for(int i = 0; i < widthPartision; i++){
      mesh[counter] = currentCoords.clone();
      currentCoords[0] += margin;
      counter ++;
    }
    currentCoords[0] = width + globalx;
    for(int i = 0; i < heightPartision; i++){
      mesh[counter] = currentCoords.clone();
      currentCoords[1] += margin;
      counter ++;
    }
    currentCoords[1] = height + globaly;
    for(int i = 0; i < widthPartision; i++){
      mesh[counter] = currentCoords.clone();
      currentCoords[0] -= margin;
      counter ++;
    }
    currentCoords[0] = globalx;
    for(int i = 0; i < heightPartision; i++){
      mesh[counter] = currentCoords.clone();
      currentCoords[1] -= margin;
      counter ++;
    }
    
  }

  public void move(int x, int y){
    globalx += x;
    globaly += y;
    for(int i = 0; i < mesh.length; i++){
      mesh[i][0] += x;
      mesh[i][1] += y;
    }
  }

  public void addCollidePlural(ArrayList<CollisionBox> cbs){
    for(CollisionBox cb : cbs){
      this.checkCollideAdd(cb);
    }
  }

  //returns [up, right, down, left]
  public void checkCollideAdd(CollisionBox cb){
    /*
     * 
     * if there is a collision, set the corresponding side to true
     * cb.checkCollide(this)
     */
    for (int i = 0; i < collisionSides.length; i++){
      collisionSides[i] = false;
    }

    for(int i = 0; i < mesh.length; i++){
      if(cb.checkCollide(mesh[i][0], mesh[i][1])){
        int perimeterOffset = i/(widthPartision+heightPartision);
        int simplifiedIndex = i%(widthPartision+heightPartision);
        if(widthPartision <= simplifiedIndex && simplifiedIndex <= widthPartision + heightPartision){
          collisionSides[1 + perimeterOffset * 2] = true;
          //optimisations
          if(perimeterOffset == 0){
            i = widthPartision + heightPartision;
          }
        }
        if(0 <= simplifiedIndex && simplifiedIndex <= widthPartision){
          collisionSides[perimeterOffset * 2] = true;
          //optimizations
          i = widthPartision + perimeterOffset * (widthPartision + heightPartision);
        }
        if(i == 0){
          collisionSides[0] = true;
          collisionSides[3] = true;
        }
      }
    }
  }

  public boolean[] checkCollideExclusive(CollisionBox cb){

    boolean[] sides = new boolean[]{false,false,false,false};
    for(int i = 0; i < mesh.length; i++){
      if(cb.checkCollide(mesh[i][0], mesh[i][1])){
        int perimeterOffset = i/(widthPartision+heightPartision);
        int simplifiedIndex = i%(widthPartision+heightPartision);
        if(widthPartision <= simplifiedIndex && simplifiedIndex <= widthPartision + heightPartision){
          sides[1 + perimeterOffset * 2] = true;
          //optimisations
          if(perimeterOffset == 0){
            i = widthPartision + heightPartision;
          }
        }
        if(0 <= simplifiedIndex && simplifiedIndex <= widthPartision){
          sides[perimeterOffset * 2] = true;
          //optimizations
          i = widthPartision + perimeterOffset * (widthPartision + heightPartision);
        }
        if(i == 0){
          sides[0] = true;
          sides[3] = true;
        }
      } 
    }
    return sides;
  
    
  }

  public void reset(){

    for(int i = 0; i < collisionSides.length; i++){
      collisionSides[i] = false;
    }
  }
  public boolean checkCollide(int x, int y){
    if(x < globalx || x > globalx + width || y < globaly || y > globaly + height){
      return false;
    }
    return true;
  }
  
  public boolean getCollideSide(int side){
    return collisionSides[side];
  }
  public boolean[] getCollideSides(){
    return collisionSides;
  }
}
