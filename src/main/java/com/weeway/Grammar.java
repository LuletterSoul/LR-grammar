package com.weeway;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wythe on 2016/12/7.
 */
public class Grammar {
    public static ArrayList<ItemSet> Family
            = new ArrayList<>();

    public static void items(Item augmentedGrammar){
        ArrayList<Item> arr = new ArrayList<>();
        arr.add(augmentedGrammar);
        ItemSet I0 = closure(arr);
        Family.add(I0);

        for(int i = 0; i < Family.size(); ++i){
            ItemSet I = Family.get(i);
            HashMap<String,ArrayList<Integer>> ids = I.getProIds();
            for(String key:ids.keySet()){
                Goto(I, ids.get(key), key);
            }
        }
    }

    private static ItemSet closure(ArrayList<Item> startItem){
        //状态初始项
        ItemSet I = new ItemSet(startItem);

        for(int i = 0; i < I.container.size(); ++i){
            Item item = I.container.get(i);

            if(!item.dotAtEnd()
                && item.NextIsInterminal()){

                ArrayList<String> lookAhead = new ArrayList<>();
                ArrayList<String> beta = item.getBeta();
                if(beta!=null) lookAhead.addAll(first(beta));
                if(lookAhead.isEmpty()) lookAhead.addAll(item.getLookAhead());

                String left = item.getDotRight();
                ArrayList<ArrayList<String>> rights = Utils.getRights(left);

                int index = 0;
                for(ArrayList<String> right:
                        rights){
                    Item extendItem = new Item(left, right, -1, lookAhead, index);
                    if(!inI(I, extendItem)) {
                        I.addItem(extendItem);
                    }
                    index++;
                }
            }
        }
        return I;
    }

    private static void Goto (ItemSet I, ArrayList<Integer> ids, String key){
        ArrayList<Item> initItems = I.statusInitialize(ids);
        int flag = IExist(initItems);
        if(flag != -1){
            I.setStatus(key,flag);
        }else {
            Family.add(closure(initItems));
            I.setStatus(key,Family.size()-1);
        }
    }

    private static int IExist(ArrayList<Item> init){
        for(int index=0; index < Family.size(); index++){
            int[] flag = new int[init.size()];
            ItemSet itemSet = Family.get(index);
            for(Item item:itemSet.container){
                for(int j=0; j<init.size(); j++)
                {
                    if(myEqual(item,init.get(j))){
                        flag[j] = 1;
                    }
                }
            }
            int sum = 0;
            for(int i=0; i<init.size(); ++i){
                sum+=flag[i];
            }
            if(sum==flag.length) return index;
        }

        return -1;
    }

    private static boolean myEqual(Item i0, Item i1){
        if(i0.left.equals(i1.left)
                && i0.dot == i1.dot
                && i0.right.equals(i1.right)
                && i0.lookAhead.equals(i1.lookAhead)){
            return true;
        }
        return false;
    }

    private static ArrayList<String> first(ArrayList<String> bate){
        ArrayList<String> firstSet = new ArrayList<>();

        first(bate, firstSet, 0);

        return firstSet;
    }

    private static String first(ArrayList<String> beta, ArrayList<String> firstSet,int index) {
        String finalSymbol;
        ArrayList<String> record = new ArrayList<>();
        String left = beta.get(index);

        if(!Utils.isTerminal(beta.get(index)) &&
                !record.contains(left)){
            record.add(left);
            ArrayList<ArrayList<String>> rights = Utils.getRights(left);
            for(int i=0; i<rights.size(); ++i){
                if(Utils.isRecursion(rights.get(i), left)){
                    if(Utils.hasEmpty(rights)){
                        rights.get(i).remove(0);
                    }else {
                        rights.remove(i);
                    }
                }
            }
            for (ArrayList<String> arr:
                    rights) {
                finalSymbol = first(arr,firstSet,0);
                if(finalSymbol.equals("ε") && index < beta.size()-1){
                    first(beta, firstSet, index+1);
                }
            }
            return "success";
        }else{
            if(!left.equals("ε") && Utils.isTerminal(left)){
                firstSet.add(left);
            }
            return left;
        }
    }

    private static boolean inI(ItemSet I, Item extendItem) {
        for (Item item : I.container){
            if(item.left.equals(extendItem.left)
                    && item.right.equals(extendItem.right)
                    && item.dot == extendItem.dot
                    && item.lookAhead.equals(extendItem.lookAhead)){
                return true;
            }
        }
        return false;
    }

    private static boolean prefixSame(ItemSet I, Item extendItem){
        for (Item item : I.container){
            if(item.left.equals(extendItem.left)
                    && item.right.equals(extendItem.right)
                    && item.dot == extendItem.dot
                    ){
                return true;
            }
        }
        return false;
    }
}
