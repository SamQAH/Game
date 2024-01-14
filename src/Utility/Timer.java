package Utility;

public class Timer {
    long storeTime;

    public Timer(){
        storeTime = System.nanoTime();
    }

    public void print(){
        long endTime = System.nanoTime();
        System.out.println("Time Took: "+(endTime-storeTime)/10E6);
        storeTime = endTime;
    }

    public long get(){
        long endTime = System.nanoTime();
        long temp = endTime-storeTime;
        storeTime = endTime;
        return temp;
    }
}
