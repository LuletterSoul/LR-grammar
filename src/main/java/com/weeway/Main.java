package com.weeway;


import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        try {
            Files.readLines(new File("F:\\GitHup\\LR-grammar\\files\\simple1.txt"),
                    Charsets.UTF_8).stream().
                    filter(line -> line.length() > 1).
                    forEach(Production::addProduction);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Files.readLines(new File("F:\\GitHup\\LR-grammar\\files\\input.txt"),
                    Charsets.UTF_8).stream().
                    filter(line -> line.length() > 1).
                    forEach(Input::addLine);
        } catch (IOException e) {
            e.printStackTrace();
        }

        println(Input.result());

        initProductionSet();
        printProductionInfo();
        extendGrammar();
        printItems();
        printActionTable();
        println("");
        printGotoTable();
        Parsering.initStacks();
        Parsering.parsering();
    }

    private static void printGotoTable() {
        println("GOTO:");
        int i = 0;
        for (HashMap<String, Integer> gt : ParserTable.GOTO_TABLE) {
            print("S" + i);
            for (String key : gt.keySet()) {
                print("  " + key + "->" + gt.get(key) + " ");
            }
            println("");
            i++;
        }
    }

    public static void extendGrammar() {
        ArrayList<String> right = new ArrayList<>();
        right.add("E");
        ArrayList<String> lookAhead = new ArrayList<>();
        lookAhead.add("$");
        Item item = new Item("E'", right, -1, lookAhead, -1);
        Utils.ACC_LEFT = item.left;
        Utils.ACC_RIGHT = item.right.get(0);
        ParserTable.initGrammar(item);
        ParserTable.createAT();
        ParserTable.createGT();
    }

    public static void printActionTable() {
        println("ACTION:");
        int i = 0;
        for (HashMap<String, ArrayList<Action>> actions : ParserTable.ACTION_TABLE) {
            print("S" + i);
            for (String key : actions.keySet()) {
                print("  " + key + "->");
                int k = 0;
                for (Action action : actions.get(key)) {
                    if (k != 0) print("/");
                    print(action.toString());
                    k++;
                }
            }
            println("");
            i++;
        }
    }

    public static void printProductionInfo() {
        print("非终结符:\n");
        Production.INTERMINAL_SET.forEach(System.out::println);
        print("\n终结符:\n");
        Production.TERMINAL_SET.forEach(System.out::println);
        print("\nMap:\n");
        Set<String> keys = Production.PRODUCTION_MAP.keySet();
        for (String key : keys) {
            print(key + ": ");
            println(Production.PRODUCTION_MAP.get(key));
        }
        println("");
        println("");
    }

    public static void initProductionSet() {
        Production.interminal();
        Production.terminal();
        Production.directory();
    }

    public static void printItems() {
        int i = 0;
        for (ItemSet itemSet : Grammar.Family) {
            print("I" + i + ":\n");
            printItemSet(itemSet);
            println(itemSet.status);
            println("");
            println("");
            i++;
        }
    }

    public static void printItemSet(ItemSet itemSet) {
        for (Item item : itemSet.container) {
            print("dot=" + item.dot + " ");
            print(item.left + " -> " + item.right + " ");
            println(item.lookAhead);
        }
    }

    public static void print(Object obj) {
        System.out.print(obj);
    }

    public static void println(Object obj) {
        System.out.println(obj);
    }
}