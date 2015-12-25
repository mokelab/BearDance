package net.exkazuu.mimicdance.activities;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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

    public void main() {
        Log.v("command", "Handler called.");
        for (int i = 0; i < com.size(); i++) {
            Program p = com.get(i);
            String com0 = p.getCommand(0);
            String com1 = p.getCommand(1);

            if (com0.equals("if")) { //if文
                if (com1.equals("gMail")) {

                } else if (com1.equals("faceBook")) {

                } else if (com1.equals("twitter")) {

                } else if (com1.equals("calender")) {

                }
            } else if (com0.equals("else")) {
                if (com1.equals("gMail")) {

                } else if (com1.equals("faceBook")) {

                } else if (com1.equals("twitter")) {

                } else if (com1.equals("calender")) {

                }
            } else if (com0.equals("if_end")) {

            } else if (com0.equals("loop")) { //loop文
                String[] num = com1.split("_", 2);
                if (num[0].equals("number")) {
                    int loopTimes = Integer.valueOf(num[1]);
                }
            } else if (com0.equals("loop_end")) {


            } else { //その他通常コマンド
                if (com0.equals("right_hand_up")) {
                    bear.raiseRightHand();
                } else if (com0.equals("left_hand_down")) {
                    bear.raiseLeftHand();
                } else if (com0.equals("right_hand_down")) {
                    bear.downRightHand();
                } else if (com0.equals("left_hand_down")) {
                    bear.downLeftHand();
                }
                bear.excecute();
                try { // 0.5秒待機
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (!com0.equals("") || !com1.equals("")) Log.v("command", com0 + "," + com1);
        }

    }

    /*public void main() {
        SoundPool sound;
        int soundId;

        sound = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        soundId = sound.load(context, R.raw.led110110part2, 0);
        sound.play(soundId, 1.0F, 1.0F,0,0,1.0F);
    }*/
}
