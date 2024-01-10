package entitiy;

import java.awt.image.BufferedImage;

public class Entity {
    public int worldx, worldy, x, y;
    public int speed;

    public int xVelocity, yVelocity;

    public int[] currentChunk;

    public String direction;

    public BufferedImage up, down, left, right;

    public int frameCounter;
    public int frameNumber;
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
