package net.exkazuu.mimicdance.pages.lesson.preview;

import android.os.Handler;

import net.exkazuu.mimicdance.models.computer.Computer;
import net.exkazuu.mimicdance.models.program.Program;
import net.exkazuu.mimicdance.models.robot.Robot;

/**
 * Runnable for Preview
 */
public class MoveRobotRunnable implements Runnable {
    private static final long FRAME_INTERVAL = 500; // 500ms
    private final Program program;
    private final Computer computer;
    private final Robot robot;
    private final Handler handler;

    private int loopCount;

    public MoveRobotRunnable(Program program, Computer computer, Robot robot, Handler handler) {
        this.program = program;
        this.computer = computer;
        this.robot = robot;
        this.handler = handler;
        this.loopCount = 0;
    }

    @Override
    public void run() {
        if (this.loopCount >= 2) {
            this.handler.post(new ComputeRunnable(this.computer, this.robot, this.handler));
            return;
        }
        this.robot.execCommand(this.program);
        ++this.loopCount;
        this.handler.postDelayed(this, FRAME_INTERVAL);
    }
}
