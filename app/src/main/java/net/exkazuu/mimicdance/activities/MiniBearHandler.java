package net.exkazuu.mimicdance.activities;

import android.content.Context;
import android.util.Log;

import java.util.List;

import net.exkazuu.mimicdance.models.program.Command;
import net.exkazuu.mimicdance.models.program.Program;


/**
 * Created by t-yokoi on 2015/12/18.
 */


public class MiniBearHandler {

    private List<Program> com;
    private Bear bear;
    private Context context;

    public MiniBearHandler(List<Program> com, Context context, Bear bear) {
        this.com = com;
        this.context = context;
        this.bear = bear;
    }

    public void main(String noteType) {
        boolean ifFlag = false;
        boolean noteFlag = true;
//        boolean loopFlag = false;
        LoopStack s = new LoopStack();
        Log.v("command", "Notification of " + noteType);
        for (int i = 0; i < com.size(); i++) {
            Program p = com.get(i);
            String com0 = p.getCommand(0);
            String com1 = p.getCommand(1);

            if (com0.equals(Command.IF)) { //if文
                ifFlag = true;
                Log.v("command", "if");
                if (com1.equals(noteType)) {
                    Log.v("command", "noteFlag is true.");
                    noteFlag = true;
                } else {
                    noteFlag = false;
                }
            } else if (com0.equals(Command.ELSE)) {
                Log.v("command", "else");
                if (com1.equals(noteType)) {
                    Log.v("command", "noteFlag is true.");
                    noteFlag = true;
                } else {
                    noteFlag = false;
                }
            } else if (com0.equals(Command.END_IF)) {
                Log.v("command", "if end");
                ifFlag = false;
                noteFlag = true;

            } else if (com0.equals(Command.LOOP)) { //loop文
//                loopFlag = true;
//                int loopTimes = Integer.parseInt(com1);
                //loop内容を保存開始
                if (s.lookTop().index != i) {
                    int t = Integer.parseInt(com1);
                    Loop l = new Loop(i, t);
                    s.push(l);
                    Log.v("loop","loop started, index:"+i+",times:"+t);
                }

            } else if (com0.equals(Command.END_LOOP)) {
                //loop内容をloopTimes-1回繰り返す
//                loopFlag = false;
                s.decreaseTimes();
                Log.v("loop", "times is " + s.lookTop().times);
                if (s.lookTop().times > 0) {
                    i = s.lookTop().index;
                    continue;
                }
                s.pop();
                Log.v("loop","loop finished.");

            } else if (!com0.equals("")) { //その他通常コマンド
                if (ifFlag && !noteFlag) {
                    Log.v("command", "false branch.");
                    continue;
                }
                handleHands(com0);
                if (!com1.equals("")) handleHands(com1);
                bear.excecute();
                try { // 0.5秒待機
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void handleHands(String command) {
        switch (command) {
            case Command.RIGHT_HAND_UP:
                bear.raiseRightHand();
                break;
            case Command.LEFT_HAND_UP:
                bear.raiseLeftHand();
                break;
            case Command.RIGHT_HAND_DOWN:
                bear.downRightHand();
                break;
            case Command.LEFT_HAND_DOWN:
                bear.downLeftHand();
                break;
            default:
                break;
        }
    }
}
