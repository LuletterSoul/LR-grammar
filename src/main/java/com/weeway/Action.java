package com.weeway;

/**
 * Created by wythe on 2016/12/11.
 */
public class Action {
    public Integer status;
    public String type;
    public String left;
    public Action(Integer status, String type){
        this.status = status;
        this.type = type;
    }

    public Action(Integer index, String type, String left){
        this.status = index;
        this.type = type;
        this.left = left;
    }

    public String toString(){
        if(type=="s") return "s"+status;
        else{
            if(status == null) return type;
            else return type+status+"("+left+")";
        }
    }

}
