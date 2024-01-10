package Utility;

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
  }

  //returns [up, right, down, left]
  public void checkCollide(CollisionBox cb){
    /*
     * check four corners first?
     * 
     * TODO check for no collision
     */
    boolean[] noCollisionTally = new boolean[]{true, true, true, true};
    for(int i = 0; i < mesh.length; i++){
      if(cb.checkCollide(mesh[i][0], mesh[i][1])){
        int perimeterOffset = i/(widthPartision+heightPartision);
        int simplifiedIndex = i%(widthPartision+heightPartision);
        if(widthPartision <= simplifiedIndex && simplifiedIndex <= widthPartision + heightPartision){
          collisionSides[1 + perimeterOffset * 2] = true;
          //optimisations
          if(perimeterOffset == 1){
            return;
          }
          i = widthPartision + heightPartision;
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
      }else{
        //TODO
      }
    }
  }
  public boolean checkCollide(int x, int y){
    if(globalx <= x && x <= globalx + width && globaly <= y && y <= globaly + height){
      return true;
    }
    return false;
  }
  
  public boolean checkCollide(int side){
    return collisionSides[side];
  }
  public boolean[] getCollideSides(){
    return collisionSides;
  }
}

