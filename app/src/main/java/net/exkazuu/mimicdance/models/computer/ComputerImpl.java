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

    public ComputerImpl(List<Program> programList) {
        this.pc = 0;
        this.programList = programList;
    }

    @Override
    public Result eval() {
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

        public Program getProgram() {
            return program;
        }
    }

}
