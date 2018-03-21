package com.weeway;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wythe on 2016/12/11.
 */
public class ParserTable {
    public static ArrayList<HashMap<String,ArrayList<Action>>> ACTION_TABLE
            = new ArrayList<>();
    public static ArrayList<HashMap<String, Integer>> GOTO_TABLE
            = new ArrayList<>();

    public static void initGrammar(Item augmentedGrammar){
        Grammar.items(augmentedGrammar);
    }

    public static void createAT(){
        for(int i = 0; i < Grammar.Family.size(); i++){
            HashMap<String, ArrayList<Action>> table = new HashMap<>();
            ItemSet itemSet = Grammar.Family.get(i);

            //移入
            for(String symbol : itemSet.shiftSymbol()){
                ArrayList<Action> actList = new ArrayList<>();
                Action action = new Action(itemSet.getStatus(symbol),"s");
                actList.add(action);
                table.put(symbol, actList);
            }

            //归约
            for(Item item : itemSet.reduceItems()){
                Action action;
                if(item.left.equals(Utils.ACC_LEFT)
                        && item.right.size()==1
                        && item.right.get(0).equals(Utils.ACC_RIGHT)
                        && item.lookAhead.contains("$")){
                    action = new Action(null,"acc");
                }
                else {
                    action = new Action(item.index,"r",item.left);
                }

                for(String symbol:item.getLookAhead()){
                    if(table.keySet().contains(symbol))
                    {
                        ArrayList<Action> actList = table.get(symbol);
                        actList.add(action);
                        table.put(symbol , actList);
                    }else {
                        ArrayList<Action> actList = new ArrayList<>();
                        actList.add(action);
                        table.put(symbol , actList);
                    }
                }
            }
            ACTION_TABLE.add(table);
        }
    }

    public static void createGT(){
        for(int i = 0; i < Grammar.Family.size(); ++i){
            HashMap<String,Integer> gt = new HashMap<>();
            ItemSet itemSet = Grammar.Family.get(i);
            for(String symbol : itemSet.gotoSymbol()){
                gt.put(symbol,itemSet.getStatus(symbol));
            }

            GOTO_TABLE.add(gt);
        }
    }
}
