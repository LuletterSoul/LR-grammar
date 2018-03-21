package com.weeway;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wythe on 2016/12/6.
 */
public class Production {
    public static ArrayList<String> PRODUCTION_RAW
            = new ArrayList<>();
    public static ArrayList<String> INTERMINAL_SET
            = new ArrayList<>();
    public static ArrayList<String> TERMINAL_SET
            = new ArrayList<>();
    public static HashMap<String,ArrayList<ArrayList<String>>> PRODUCTION_MAP
            = new HashMap<>();

    public static void interminal(){
        for (String item : PRODUCTION_RAW) {
            String left = item.split(" -> ")[0];
//            System.out.println(left);
            if(!INTERMINAL_SET.contains(left)){
                INTERMINAL_SET.add(left);
            }
        }
    }

    public static void terminal(){
        for (String item : PRODUCTION_RAW){
            String right = item.split(" -> ")[1];

            for (String terminal:
                 right.split(" ")) {
                if(!TERMINAL_SET.contains(terminal)
                        && !INTERMINAL_SET.contains(terminal)
                        && !terminal.equals("ε")){
                    TERMINAL_SET.add(terminal);
                }
            }
        }
    }

    public static void directory(){
        for(String item : PRODUCTION_RAW){
            String[] temp = item.split(" -> ");

            ArrayList<String> right = new ArrayList<>();

            for (String i:
                 temp[1].split(" ")) {
                right.add(i);
            }

            if(PRODUCTION_MAP.containsKey(temp[0])){
                ArrayList<ArrayList<String>> set = PRODUCTION_MAP.get(temp[0]);
                set.add(right);
                PRODUCTION_MAP.put(temp[0],set);
            }else {
                ArrayList<ArrayList<String>> set = new ArrayList<>();
                set.add(right);
                PRODUCTION_MAP.put(temp[0],set);
            }
        }
    }

    public static void productions(){

    }

    public static void addProduction(String production){
        PRODUCTION_RAW.add(production);
    }

    public static ArrayList<ArrayList<String>> getRights(String key){
        ArrayList<ArrayList<String>> rights = new ArrayList<>();
        rights.addAll(PRODUCTION_MAP.get(key));
        return rights;
    }
}
