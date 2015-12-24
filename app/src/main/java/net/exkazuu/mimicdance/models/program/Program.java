package net.exkazuu.mimicdance.models.program;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by t-yokoi on 2015/12/15.
 */
public class Program implements Parcelable {
    private String[] commands = new String[2];

    public Program() {
        commands[0] = "";
        commands[1] = "";
    }

    public Program(String command0, String command1) {
        commands[0] = command0;
        commands[1] = command1;
    }

    Program(Parcel source) {
        commands[0] = source.readString();
        commands[1] = source.readString();
    }

    public void setCommands(int index, String value) {
        commands[index] = value;
    }
    public String getCommand(int index) { return commands[index];}

    // region Parcelable

    public static Creator<Program> CREATOR = new Creator<Program>() {
        @Override
        public Program createFromParcel(Parcel source) {
            return new Program(source);
        }

        @Override
        public Program[] newArray(int size) {
            return new Program[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(commands[0]);
        dest.writeString(commands[1]);
    }

    // endregion
}
