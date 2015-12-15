package net.exkazuu.mimicdance.models;

/**
 * Created by t-yokoi on 2015/12/15.
 */
public class Program {
    private String[] commands = new String[2];

    public Program() {
        commands[0] = "";
        commands[1] = "";
    }

    public void setCommands(int index, String value) {
        commands[index] = value;
    }
}
