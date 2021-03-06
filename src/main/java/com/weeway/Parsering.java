package com.weeway;

import java.util.ArrayList;

/**
 * Created by wythe on 2016/12/11.
 */
public class Parsering {
    //栈顶在左
    public static ArrayList<String> INPUT_STACK = new ArrayList<>();
    //栈顶在右
    public static ArrayList<Integer> STATUS_STACK = new ArrayList<>();
    //栈顶在右
    public static ArrayList<String> SYMBOL_STACK = new ArrayList<>();


    public static void initStacks(){
        for(String str : Input.inputs)
        {
            INPUT_STACK.add(str);
        }
        INPUT_STACK.add("$");
        STATUS_STACK.add(0);
    }

    public static String getLookAhead(){
        String lookAhead = INPUT_STACK.get(0);
        return lookAhead;
    }

    public static int getCurStatus(){
        return STATUS_STACK.get(STATUS_STACK.size()-1);
    }

    public static void shift(int status){
        SYMBOL_STACK.add(INPUT_STACK.remove(0));
        STATUS_STACK.add(status);
    }

    public static void reduce(String left,int status){
        ArrayList<String> rights = Utils.getRights(left).get(status);
        int len = rights.size();
        //符号栈、状态栈pop
        for(int i=0; i<len; i++){
            STATUS_STACK.remove(STATUS_STACK.size()-1);
            SYMBOL_STACK.remove(SYMBOL_STACK.size()-1);
        }

        int curStatus = getCurStatus();
        int nextStatus = ParserTable.GOTO_TABLE.get(curStatus).get(left);

        SYMBOL_STACK.add(left);
        STATUS_STACK.add(nextStatus);
    }

    public static void parsering(){
        while (true) {
            String lookAhead = getLookAhead();
            ArrayList<Action> actions;
            int curStatus = getCurStatus();
            if (ParserTable.ACTION_TABLE.get(curStatus).containsKey(lookAhead)) {
                actions = ParserTable.ACTION_TABLE.get(curStatus).get(lookAhead);
            } else {
                System.out.println("文法错误");
                return;
            }

            Action action = actions.get(0);
            if (action.type.equals("s")) {
                shift(action.status);
            } else if (action.type.equals("r")) {
                reduce(action.left, action.status);
            } else if (action.type.equals("acc")) {
                System.out.println("文法正确");
                return;
            }
        }
    }
}
