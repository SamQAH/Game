package Utility;

import java.util.ArrayList;
import java.util.Arrays;

public class CoordinateSet {
    public ArrayList<int[]> coordset;

    public CoordinateSet(){
        coordset = new ArrayList<>();
    }

    public boolean unorderedEquals(CoordinateSet cs){
        ArrayList<int[]> other = cs.getCoordList();
        if(other.size() != this.coordset.size()){
            return false;
        }
        for(int[] coord : other){
            if(!this.contains(coord)){
                return false;
            }
        }
        return true;
    }

    public boolean orderedEquals(CoordinateSet cs){
        ArrayList<int[]> other = cs.getCoordList();
        if(other.size() != this.coordset.size()){
            return false;
        }
        int counter = 0;

        for(int[] coord : other){
            if(!Arrays.equals(coord,coordset.get(counter++))){
                return false;
            }
        }
        return true;
    }

    public ArrayList<int[]> getCoordList(){
        return coordset;
    }

    public void setCoordList(ArrayList<int[]> cs){
        coordset = cs;
    }

    public void addCoord(int[] coord){
        if(!this.contains(coord)){
            coordset.add(coord);
        }
    }

    public boolean contains(int[] coord){
        for(int[] cord : coordset){
            if(Arrays.equals(cord, coord)){
                return true;
            }
        }
        return false;
    }

    public void remove(int[] coord){
        for(int[] cord : coordset){
            if(Arrays.equals(cord, coord)){
                coordset.remove(cord);
                return;
            }
        }
    }
}
