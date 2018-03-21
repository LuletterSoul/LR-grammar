package com.weeway;

import java.util.ArrayList;

/**
 * Created by wythe on 2016/12/7.
 */
public class Item{
        public String left;
        public ArrayList<String> right;
        public ArrayList<String> lookAhead;
        public int dot;
        public int index;


        public Item(String left, ArrayList<String> right, int dot, ArrayList<String> lookAhead, int index){
            this.right = right;
            this.left = left;
            this.dot = dot;
            this.lookAhead = lookAhead;
            this.index = index;
        }


        public void addLookAhead(ArrayList<String> lookAhead){
            this.lookAhead.addAll(lookAhead);
        }


        public boolean NextIsInterminal(){
            boolean status = false;
            int indexOfNext = dot + 1;
            if( !dotAtEnd()
                    && Production.INTERMINAL_SET.
                    contains(right.get(indexOfNext)) ){
                status = true;
            }
            return status;
        }

        public ArrayList<String> getLookAhead(){
            ArrayList<String> lookAhead = new ArrayList<>();
            lookAhead.addAll(this.lookAhead);
            return lookAhead;
        }

        public ArrayList<String> getBeta(){
            ArrayList<String> arr = new ArrayList<>();
            if(hasBeta()){
                arr.addAll(right.subList(this.dot+2,this.right.size()));
                return arr;
            }else {
                return null;
            }
        }

        private boolean hasBeta(){
            if(this.dot+1 == this.right.size()-1){
                return false;
            }
            return true;
        }

        public String getDotRight(){
            String left = this.right.get(this.dot+1);
            return left;
        }

        public boolean dotAtEnd(){
            if(dot >= right.size()-1){
                return true;
            }
            return false;
        }
}
