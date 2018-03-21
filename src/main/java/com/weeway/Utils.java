package com.weeway;

import java.util.ArrayList;

/**
 * Created by wythe on 2016/12/10.
 */
public class Utils {
    public static String ACC_LEFT;
    public static String ACC_RIGHT;

    public static boolean isTerminal(String str){
        if(Production.INTERMINAL_SET.contains(str)){
            return false;
        }else {
            return true;
        }
    }

    public static ArrayList<ArrayList<String>> getRights(String symbol){
        return Production.PRODUCTION_MAP.get(symbol);
    }

    public static boolean hasEmpty(ArrayList<ArrayList<String>> rights) {
        for(ArrayList<String> symbols:rights){
            if(symbols.get(0).equals("ε")){
                return true;
            }
        }
        return false;
    }

    public static boolean isRecursion(ArrayList<String> rights, String left) {
        return rights.get(0).equals(left);
    }
}
