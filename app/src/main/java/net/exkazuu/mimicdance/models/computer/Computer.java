package net.exkazuu.mimicdance.models.computer;

/**
 * Describes Computer
 */
public interface Computer {
    interface Result {
        boolean isHalt();
    }

    Result eval();
}
