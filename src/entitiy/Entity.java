package entitiy;

import java.awt.image.BufferedImage;

public class Entity {
    public int worldx, worldy, x, y;
    public int speed;

    public int[] currentChunk;

    public String direction;

    public BufferedImage up, down, left, right;

    public int frameCounter;
    public int frameNumber;
}
