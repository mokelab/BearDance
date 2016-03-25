package net.exkazuu.mimicdance.activities;

import android.util.Log;

/**
 * Created by t-yokoi on 2015/12/18.
 */
public class ArduinoBear implements Bear{
    byte[] commands = new byte[2];



    @Override
    public void raiseRightHand() {
        commands[0]=0x1;
    }

    @Override
    public void raiseLeftHand() {
        commands[1]=0x1;
    }

    @Override
    public void downRightHand() {
        commands[0]=0x0;
    }

    @Override
    public void downLeftHand() {
        commands[1]=0x0;
    }

    @Override
    public void excecute() {
        Log.v("command","right:"+commands[0]+" ,left:"+commands[1]);
        ArduinoManager.sendCommand(commands);
    }
}
