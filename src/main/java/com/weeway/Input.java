package com.weeway;

import java.util.ArrayList;

/**
 * Created by wythe on 2016/12/11.
 */
public class Input {
    public static ArrayList<String> inputs
            = new ArrayList<>();

    public static void addLine(String line){
        for(String str: line.split(" ")){
            inputs.add(str);
        }
    }

    public static String result(){
        String result = "";
        for(String str:inputs){
            result+=(str+" ");
        }
        return result;
    }
}
