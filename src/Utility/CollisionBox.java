package Utility;

import java.awt.Graphics2D;
import java.util.ArrayList;

import chunk.Chunk;
import entitiy.Entity;

public class CollisionBox{
  private static final double margin = 8; // true margin / 2
  int width, height, globalx, globaly, widthPartision, heightPartision;
  int[][] mesh;
  boolean[] collisionSides;
  boolean[] advancedCollisionSides;
  
  public CollisionBox(int width, int height, int x, int y){
    this.width = width;
    this.height = height;
    globalx = x;
    globaly = y;
    setMesh();
  }

  public void setMesh(){
    advancedCollisionSides = new boolean[8];
    collisionSides = new boolean[4];
    for(int i = 0; i < 8; i++){
      advancedCollisionSides[i] = false;
    }
    for(int i = 0; i < 4; i++){
      collisionSides[i] = false;
    }
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



    for(int i = 0; i < mesh.length; i++){
      if(cb.checkCollide(mesh[i][0], mesh[i][1])){
        int perimeterOffset = i/(widthPartision+heightPartision);
        int simplifiedIndex = i%(widthPartision+heightPartision);
        if(simplifiedIndex == 0){
          advancedCollisionSides[perimeterOffset * 4] = true;
        }else if(0 < simplifiedIndex && simplifiedIndex < widthPartision){
          advancedCollisionSides[1 + perimeterOffset * 4] = true;
          collisionSides[perimeterOffset * 2] = true;
          i = widthPartision + perimeterOffset * (widthPartision + heightPartision);
        }else if(simplifiedIndex == widthPartision){
          advancedCollisionSides[2 + perimeterOffset * 4] = true;
        }else if(widthPartision < simplifiedIndex && simplifiedIndex < widthPartision + heightPartision){
          advancedCollisionSides[3 + perimeterOffset * 4] = true;
          collisionSides[1 + perimeterOffset * 2] = true;
          i = (widthPartision + heightPartision)*(perimeterOffset+1);
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

  public void moveTo(int x, int y){
    globalx = x;
    globaly = y;
    setMesh();
  }

  public void reset(){
    for(int i = 0; i < advancedCollisionSides.length; i++){
      advancedCollisionSides[i] = false;
    }
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

  public boolean checkCollide(CollisionBox cb){
    // check manhattan distance
    for(int i = 0; i < mesh.length; i++){
      if(cb.checkCollide(mesh[i][0], mesh[i][1])){
        return true;
      }
    }
    return false;
  }
  
  public boolean getCollideSide(int side){
    return collisionSides[side];
  }
  public boolean[] getAdvancedCollideSides(){
    return advancedCollisionSides;
  }
  public boolean[] getCollideSides(){
    return collisionSides;
  }

  public void draw(Graphics2D g2, Entity referenceEntity){
    int[] pointsX = new int[5];
    int[] pointsY = new int[5];

    int pWorldX = referenceEntity.worldx;
    int pWorldY = referenceEntity.worldy;
    int pScreenX = referenceEntity.x;
    int pScreenY = referenceEntity.y;
    int screenx = pScreenX-pWorldX+globalx;
    int screeny = pScreenY-pWorldY+globaly;

    pointsX[0] = screenx;
    pointsY[0] = screeny;
    pointsX[1] = screenx += width;
    pointsY[1] = screeny;
    pointsX[2] = screenx;
    pointsY[2] = screeny += height;
    pointsX[3] = screenx -= width;
    pointsY[3] = screeny;
    pointsX[4] = screenx;
    pointsY[4] = screeny -= height;



    g2.drawPolyline(pointsX, pointsY, 5);
  }

  public static int manhattanDistance(CollisionBox one, CollisionBox two){
    return Math.abs(one.globalx-two.globalx) + Math.abs(one.globaly-two.globaly);
  }
}
