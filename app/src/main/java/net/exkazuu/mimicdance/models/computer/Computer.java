package net.exkazuu.mimicdance.models.computer;

import net.exkazuu.mimicdance.models.program.Program;

/**
 * Describes Computer
 */
public interface Computer {
    interface Result {
        boolean isHalt();
        Program getProgram();
    }

    Result eval();

    void stop();
}
