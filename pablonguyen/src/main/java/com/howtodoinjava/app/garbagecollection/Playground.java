package com.howtodoinjava.app.garbagecollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Playground {
    public Playground(){}

    public List<Integer> LastNItems(int n, List<Integer> list){
        int size = list.size();
        return list.subList((size - n), size);
    }


    public static void main (String[] args){
            List<Integer> intlist = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,0));
            Playground p = new Playground();

            List<Integer> last3 = p.LastNItems(3, intlist);
    }

}
