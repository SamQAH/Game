package main;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JPanel;

import Utility.Timer;
import chunk.ChunkManager;

import entitiy.Entity;
import entitiy.Player;


public class GamePanel extends JPanel implements Runnable{
    
    // screen settings
    public final int ORIGINALTILESIZE = 32;
    final int SCALE = 2;

    public final int TILESIZE = ORIGINALTILESIZE * SCALE;

    final int MAXSCREENCOL = 24;
    final int MAXSCREENROW = 16;

    public final int SCREENWIDTH = MAXSCREENCOL * TILESIZE;
    public final int SCREENHEIGHT = MAXSCREENROW * TILESIZE;

    int FPS = 60;

    public int frameCounter;

    KeyHandler kh = new KeyHandler();

    Thread gameThread;

    Player player = new Player(this, kh);

    ChunkManager cm = new ChunkManager(this, player);


    public GamePanel(){
        this.setPreferredSize(new Dimension(SCREENWIDTH, SCREENHEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(kh);
        this.setFocusable(true);

        this.getSave();
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();


    }

    @Override
    public void run() {

        double frameInterval = 1E9/FPS; // in nanpseconds
        double frameFraction = 0; // fraction until next frame
        double chunkcounter = 0;
        long lasttime = System.nanoTime();
        long currenttime;
        long deltaTime = 1; // can be used for true speed calculations
        frameCounter = 0;
        long[] timeTracking = new long[FPS];
        long averageFrameTime = 0;

        while(gameThread != null){
            currenttime = System.nanoTime();
            frameFraction += (deltaTime = (currenttime - lasttime)) / frameInterval;
            chunkcounter += (currenttime - lasttime) / frameInterval / (60);
            lasttime = currenttime;

            if(frameFraction >= 1){
                Timer timer = new Timer();
                update();
                repaint();
                frameFraction--;
                timeTracking[frameCounter] = timer.get();
                if(frameCounter == FPS-1){
                    frameCounter = -1;
                    long sum = 0;
                    for(long l : timeTracking){
                        sum += l;
                    }
                    averageFrameTime = sum/FPS;
                }
                frameCounter++;
            }

            if(chunkcounter >= 5 && frameFraction < 0.2){
                Timer timer = new Timer();
                cm.updateChunks();
                System.out.print("Update chunk ");
                System.out.println(timer.get()/frameInterval);
                System.out.println("Averae frame "+averageFrameTime/frameInterval);

                //System.out.println(player.worldx+" "+player.worldy);
                chunkcounter = 0;
            }
            




        }
        
    }

    // calls appopriate methods per frame
    public void update(){
        player.update();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(5));
        cm.draw(g2);
        player.draw(g2);
        g2.dispose();
    }

    public void getSave(){

        try{
            File file = new File("file.txt");

            if(file.exists()){
                System.out.println("found save");
            }else{
                System.out.println("did not find save");
            }


            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
 
            String line;
            int counter = -1;
            while ((line = bufferedReader.readLine()) != null) {
                counter = Integer.parseInt(line);
                System.out.println(line);
            }
            bufferedReader.close();

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

            bufferedWriter.write(""+(++counter));

            bufferedWriter.close();


        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("got save");
    }

    public int[] getPlayerWorldPos(){
        return new int[]{player.worldx,player.worldy};
    }

    public int[] getPlayerScreenPos(){
        return new int[]{player.x,player.y};
    }

    public Entity getPlayer(){
        return player;
    }
}
