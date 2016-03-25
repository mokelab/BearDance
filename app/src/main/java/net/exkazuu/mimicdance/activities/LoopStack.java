package net.exkazuu.mimicdance.activities;

import android.util.Log;

/**
 * Created by t-yokoi on 2016/01/08.
 */
public class LoopStack {
    private Loop[] stack;
    private final int DEFAULT_CAPACITY = 5;
    private int sp;

    public LoopStack() {
        stack = new Loop[DEFAULT_CAPACITY];
        sp=0;
    }

    public void push(Loop l) {
        stack[sp++] = l;
        Log.v("loop","pushed to sp:"+ (sp-1));
    }

    public Loop pop() {
        Log.v("loop","popped sp is"+(sp-1));
        return stack[--sp];
    }

    public Loop lookTop() {
        if(sp>0) return stack[sp-1];
        else return new Loop(-1,-1);
    }

    public void decreaseTimes(){
        if(sp>0) stack[sp-1].times--;
    }
}

