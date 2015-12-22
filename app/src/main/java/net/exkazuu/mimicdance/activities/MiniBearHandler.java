package net.exkazuu.mimicdance.activities;

import android.content.Context;

import java.util.ArrayList;

import net.exkazuu.mimicdance.models.program.Program;


/**
 * Created by t-yokoi on 2015/12/18.
 */


public class MiniBearHandler {

    private ArrayList<Program> com;
    private Bear bear;
    private Context context;

    public MiniBearHandler(ArrayList<Program> com, Context context, Bear bear) {
        this.com = com;
        this.context = context;
        this.bear = bear;
    }

    public void main() {

        for (int i = 0; i < com.size(); i++) {
            Program p = com.get(i);
            String com0 = p.getCommand(0);
            String com1 = p.getCommand(1);

            if(com0.equals("if")) {

            } else if(com0.equals("loop")) {

            } else {
                if (com.get(i).equals("right_hand_up")) {
                    bear.raiseRightHand();
                } else if (com.get(i).equals("left_hand_down")) {
                    bear.raiseLeftHand();
                } else if (com.get(i).equals("right_hand_down")) {
                    bear.downRightHand();
                } else if (com.get(i).equals("left_hand_down")) {
                    bear.downLeftHand();
                }
                bear.excecute();
                try { // 0.5秒待機
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
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
