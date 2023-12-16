package chunk;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;

import main.GamePanel;
import tile.FoliageManager;
import tile.TileManager;


/*
 * Each chunk is responsible for:
 * Generating itself from a seed value
 * Storing its information to a file

 */
public class Chunk{
    GamePanel gp;
    int[] id; // [x,y] cooridinate with starting screen being 0 , 0 and each subsequent chunk id incrementing by 1, corresponding to chunkSize number of chunks
    public static final int[] chunkSize = new int[] {32,32}; // number of tiles horizontaly and verticaly
    String fileName; // stored in /src/chunk/chunk_data/??.txt
    String tileData;
    TileManager tm;
    String foliageData;
    FoliageManager fm;
    File file;
    String chunkType;

    public Chunk(GamePanel gp, int[] coord, int seed){
        this.gp = gp;
        id = coord;
        fileName = "src/chunk/chunk_data/chunk_"+id[0]+"_"+id[1]+".txt";
        file = new File(fileName);

        // if(!file.exists()){
        //     this.generateChunk(seed);
        // } TODO
        this.getChunkType();
        this.generateChunk(seed);
        this.parseData();
        

        fm = new FoliageManager(gp,this.getFoliageData());
        tm = new TileManager(gp, this.getTileData());

    }

    public void getChunkType(){
        if(id[0] < -1){
            chunkType = "ocean";
        }else if(id[0] == -1){
            chunkType = "beach";
        }else{
            chunkType = "plains";
        }
    }
    
    public void generateChunk(int seed){
        /*
         * calculate the chunk type:
         * plains
         * ocean
         * beach
         * ocean beach vertical blend
         * 
         * write tiles
         * write foliage
         */
        
        Random random = new Random(seed);
        int[] currentCoord = new int[]{0,0};
        try{
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

            this.calculateTileString(random, bufferedWriter);


            bufferedWriter.write("section break\n");
            currentCoord[0] = 0;
            currentCoord[1] = 0;

            while(currentCoord[1]<chunkSize[1]){
                int num;
                switch(chunkType){
                    case "plains":
                    num = random.nextInt(20);
                    break;
                    case "ocean":
                    num = -1;
                    break;
                    case "beach":
                    num = random.nextInt(100);
                    break;
                    default:
                    num = -1;
                    break;
                }
                bufferedWriter.write(num+" ");
                currentCoord[0]++;
                if(currentCoord[0] == chunkSize[0]){
                    currentCoord[0] = 0;
                    currentCoord[1]++;
                    bufferedWriter.newLine();
                }
            }

            bufferedWriter.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private void calculateTileString(Random random, BufferedWriter bufferedWriter){

        try{
            int[] currentCoord = new int[]{0,0};
            switch(chunkType){
                case "plains":
                    while(currentCoord[1]<chunkSize[1]){
                        bufferedWriter.write(0+" ");
                        currentCoord[0]++;
                        if(currentCoord[0] == chunkSize[0]){
                            currentCoord[0] = 0;
                            currentCoord[1]++;
                            bufferedWriter.newLine();
                        }
                    }
                    break;
                case "ocean":
                    while(currentCoord[1]<chunkSize[1]){
                        double val = random.nextDouble();
                        if(currentCoord[0] == chunkSize[0]-1){
                            bufferedWriter.write(4+" ");
                        }else if(currentCoord[0] > 20 && val > 0.9){
                            bufferedWriter.write(2+" ");
                        }else{
                            bufferedWriter.write(1+" ");
                        }
                        
                        currentCoord[0]++;
                        if(currentCoord[0] == chunkSize[0]){
                            currentCoord[0] = 0;
                            currentCoord[1]++;
                            bufferedWriter.newLine();
                        }
                    }
                    break;
                case "beach":
                    while(currentCoord[1]<chunkSize[1]){
                        double val = random.nextDouble();
                        if(currentCoord[0] > 29 && val > 0.5){
                            bufferedWriter.write(0+" ");
                        }else{
                            bufferedWriter.write(9+" ");
                        }
                        currentCoord[0]++;
                        if(currentCoord[0] == chunkSize[0]){
                            currentCoord[0] = 0;
                            currentCoord[1]++;
                            bufferedWriter.newLine();
                        }
                    }
                    break;
                default:
                    currentCoord = new int[]{0,0};
                    while(currentCoord[1]<chunkSize[1]){
                        bufferedWriter.write(0+" ");
                        currentCoord[0]++;
                        if(currentCoord[0] == chunkSize[0]){
                            currentCoord[0] = 0;
                            currentCoord[1]++;
                            bufferedWriter.newLine();
                        }
                    }
                    break;
            }
        }catch(Exception e){
            e.printStackTrace();
        }


    }

    public void parseData(){
        tileData = "";
        foliageData = "";
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            int counter = 0;
            while ((line = bufferedReader.readLine()) != null) {
                if(line.equals("section break")){
                    counter++;
                    continue;
                }
                switch(counter){
                    case 0:
                    tileData += line;
                    break;
                    case 1:
                    foliageData += line;
                    break;
                    default:
                    break;
                }
                

            }
            bufferedReader.close();

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public String getTileData(){
        return tileData;
    }

    public String getFoliageData(){
        return foliageData;
    }

    public void draw(Graphics2D g2){
        tm.draw(g2, id, gp.frameCounter);
        fm.draw(g2, id);
    }




}