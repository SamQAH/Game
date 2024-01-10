package tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public enum TileSet {
    grassyfield(0, new int[][]{{0,0,0},{0,0,0},{0,0,0},{0,0,0}}),
    openocean(1, new int[][]{{1,1,1},{1,1,1},{1,1,1},{1,1,1}}), 
    oceanblendup(2, new int[][]{{9,9,9},{9,1,1},{1,1,1},{1,1,9}}), 
    oceanblendright(3, new int[][]{{1,1,9},{9,9,9},{9,1,1},{1,1,1}}), 
    oceanblenddown(4, new int[][]{{1,1,1},{1,1,9},{9,9,9},{9,1,1}}), 
    oceanblendleft(5, new int[][]{{9,1,1},{1,1,1},{1,1,9},{9,9,9}}),
    oceanblendinsideupright(6, new int[][]{{9,9,9},{9,9,9},{9,1,1},{1,1,9}}),
    oceanblendinsideupleft(7, new int[][]{{9,9,9},{9,1,1},{1,1,9},{9,9,9}}),
    oceanblendinsidedownleft(8, new int[][]{{9,1,1},{1,1,9},{9,9,9},{9,9,9}}),
    oceanblendinsidedownright(10, new int[][]{{1,1,9},{9,9,9},{9,9,9},{9,1,1}}),
    oceanblendoutsideupright(11, new int[][]{{1,1,9},{9,1,1},{1,1,1},{1,1,1}}),
    oceanblendoutsideupleft(12, new int[][]{{9,1,1},{1,1,1},{1,1,1},{1,1,9}}),
    oceanblendoutsidedownleft(13, new int[][]{{1,1,1},{1,1,1},{1,1,9},{9,1,1}}),
    oceanblendoutsidedownright(14, new int[][]{{1,1,1},{1,1,9},{9,1,1},{1,1,1}}),
    oceanblendinside(15, new int[][]{{9,9,9},{9,9,9},{9,9,9},{9,9,9}}),
    oceanblendoutside(16, new int[][]{{1,1,1},{1,1,1},{1,1,1},{1,1,1}}),
    sand(9, new int[][]{{9,9,9},{9,9,9},{9,9,9},{9,9,9}});

    public final int index;
    public final int[][] sides;

    private TileSet(int i, int[][] sideData){
        index = i;
        sides = sideData;
    }

    public static TileSet findByIndex(int i){
        for(TileSet t : TileSet.values()){
            if(t.index == i){
                return t;
            }
        }
        return null;
    }

    public static TileSet findBySide(HashMap<Integer, Integer> adjacent){
        /* * * 0 * * *
         * * 3 & 1 <-- key of adjacent, contains tile index 
         * * * 2 * * *
         */
        if(adjacent.size() < 2){
            return null;
        }

        HashMap<Integer, int[]> importantSides = new HashMap<>();
        int first = 0;
        int[] firstSide = null;
        boolean need = true;
        for(Integer i : adjacent.keySet()){
            int touchingside = (i + 2) & 3;
            int[] sideinfo = findByIndex(adjacent.get(i)).sides[touchingside];
            
            if(need){
                first = touchingside;
                firstSide = sideinfo;
                need = false;
            }else{
                importantSides.put(touchingside, sideinfo);
            }
        }

        ArrayList<TileSet> matches = new ArrayList<>();
        for(TileSet ts : TileSet.values()){
            if(Arrays.equals(ts.sides[first],firstSide)){
                matches.add(ts);
            }
        }
        for(int i : importantSides.keySet()){
            for(int j = 0; j < matches.size(); ){
                TileSet ts = matches.get(j);
                if(!Arrays.equals(importantSides.get(i),ts.sides[i])){
                    matches.remove(ts);
                }else{
                    j++;
                }
            }
        }

        if(matches.size() != 1){
            return null;
        }else{
            return matches.get(1);
        }
    }
}