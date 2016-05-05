package net.exkazuu.mimicdance.models.computer;

import net.exkazuu.mimicdance.models.program.Program;

import java.util.List;

/**
 * Implementation
 */
public class ComputerImpl implements Computer {
    /* Program counter */
    private int pc;

    private final List<Program> programList;

    private boolean stopped;

    public ComputerImpl(List<Program> programList) {
        this.pc = 0;
        this.programList = programList;
        this.stopped = false;
    }

    @Override
    public Result eval() {
        if (this.stopped) {
            return new ResultImpl(null, true);
        }
        while (true) {
            if (programList.size() <= pc) {
                return new ResultImpl(null, true);
            }
            Program program = programList.get(pc);
            ++pc;
            // If program has condition statement, evaluate here and modify the program counter.
            return new ResultImpl(program, false);
        }
    }

    @Override
    public void stop() {
        this.stopped = true;
    }

    private static class ResultImpl implements Computer.Result {

        private final boolean halt;
        private final Program program;

        private ResultImpl(Program program, boolean halt) {
            this.program = program;
            this.halt = halt;
        }

        @Override
        public boolean isHalt() {
            return halt;
        }

        @Override
        public Program getProgram() {
            return program;
        }
    }

}
