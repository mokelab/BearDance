package net.exkazuu.mimicdance.models.robot;

import net.exkazuu.mimicdance.models.program.Program;

/**
 * Describes Robot
 */
public interface Robot {
    void execCommand(Program program);

    void reset();
}
