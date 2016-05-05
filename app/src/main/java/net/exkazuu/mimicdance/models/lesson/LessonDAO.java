package net.exkazuu.mimicdance.models.lesson;

import net.exkazuu.mimicdance.models.program.Program;

import java.util.List;

/**
 * レッスンに関するAPIを提供
 */
public interface LessonDAO {
    int POSITION_LEFT = 0;
    int POSITION_RIGHT = 1;

    void init();
    void upload();

    List<Program> getLessonProgram(int lessonId, int position);
}
