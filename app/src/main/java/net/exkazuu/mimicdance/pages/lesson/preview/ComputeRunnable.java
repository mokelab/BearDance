package net.exkazuu.mimicdance.pages.lesson.preview;

import android.os.Handler;

import net.exkazuu.mimicdance.models.computer.Computer;
import net.exkazuu.mimicdance.models.robot.Robot;

/**
 * Runnable for Preview
 */
public class ComputeRunnable implements Runnable {
    private final Computer computer;
    private final Robot robot;
    private final Handler handler;

    public ComputeRunnable(Computer computer, Robot robot, Handler handler) {
        this.computer = computer;
        this.robot = robot;
        this.handler = handler;
    }

    @Override
    public void run() {
        Computer.Result result = this.computer.eval();
        if (result.isHalt()) { return; }

        this.handler.post(new MoveRobotRunnable(result.getProgram(), computer, robot, handler));
    }
}
