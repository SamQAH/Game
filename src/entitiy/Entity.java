package entitiy;

import java.awt.image.BufferedImage;

import Utility.CollisionBox;

public class Entity {
    public int worldx, worldy, x, y;
    public int speed;

    public int xVelocity, yVelocity;

    public int[] currentChunk;

    public String direction;

    public BufferedImage up, down, left, right;

    public int frameCounter;
    public int frameNumber;

    public CollisionBox hitbox;

    public void updateVelocity(){
        if((hitbox.getCollideSide(0) && yVelocity < 0) || (hitbox.getCollideSide(2) && yVelocity > 0)){
            yVelocity = 0;
        }
        if((hitbox.getCollideSide(1) && xVelocity > 0) || (hitbox.getCollideSide(3) && xVelocity < 0)){
            xVelocity = 0;
        }
    }

    public void printCollide(){
        for(boolean b : hitbox.getCollideSides()){
            System.out.print(b+" ");
        }
        System.out.println();
        for(boolean b : hitbox.getAdvancedCollideSides()){
            System.out.print(b+" ");
        }
        System.out.println();
    }

    public void printPos(){
        System.out.println(worldx + ", " + worldy);
    }

    public CollisionBox getHitbox() {
        return hitbox;
    }
    public void setHitbox(CollisionBox hitbox) {
        this.hitbox = hitbox;
    }
    public int getWorldx() {
        return worldx;
    }
    public void setWorldx(int worldx) {
        this.worldx = worldx;
    }
    public int getWorldy() {
        return worldy;
    }
    public void setWorldy(int worldy) {
        this.worldy = worldy;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public int getxVelocity() {
        return xVelocity;
    }
    public void setxVelocity(int xVelocity) {
        this.xVelocity = xVelocity;
    }
    public int getyVelocity() {
        return yVelocity;
    }
    public void setyVelocity(int yVelocity) {
        this.yVelocity = yVelocity;
    }
    public int[] getCurrentChunk() {
        return currentChunk;
    }
    public String getDirection() {
        return direction;
    }
    public void setDirection(String direction) {
        this.direction = direction;
    }
}
