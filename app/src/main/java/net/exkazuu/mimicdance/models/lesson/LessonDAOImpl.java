package net.exkazuu.mimicdance.models.lesson;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import com.activeandroid.query.Select;

import net.exkazuu.mimicdance.models.LessonClear;
import net.exkazuu.mimicdance.models.PostQuestionnaireResult;
import net.exkazuu.mimicdance.models.PreQuestionnaireResult;
import net.exkazuu.mimicdance.models.program.Command;
import net.exkazuu.mimicdance.models.program.Program;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import im.delight.android.ddp.MeteorSingleton;

/**
 * Implementation
 */
public class LessonDAOImpl implements LessonDAO {
    private final Context context;

    public LessonDAOImpl(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public void init() {
        if (!MeteorSingleton.isCreated()) {
            MeteorSingleton.createInstance(context, "ws://mimic-dance-server.herokuapp.com/websocket");
        }
    }

    @Override
    public void upload() {
        MeteorSingleton.getInstance().reconnect();
        List<LessonClear> lessonClears = new Select().from(LessonClear.class).where("Sent = ?", false).orderBy("Created_at").execute();
        List<PreQuestionnaireResult> preQuestionnaireResults = new Select().from(PreQuestionnaireResult.class).where("Sent = ?", false).orderBy("Created_at").execute();
        List<PostQuestionnaireResult> postQuestionnaireResults = new Select().from(PostQuestionnaireResult.class).where("Sent = ?", false).orderBy("Created_at").execute();
        Log.d("upload", "lessonClears: " + lessonClears.size());
        Log.d("upload", "preQuestionnaireResults: " + preQuestionnaireResults.size());
        Log.d("upload", "postQuestionnaireResults: " + postQuestionnaireResults.size());
        try {
            if (!MeteorSingleton.getInstance().isConnected()) {
                return;
            }
            for (LessonClear item : lessonClears) {
                Map<String, Object> values = new HashMap<>();
                values.put("created_at", item.created_at);
                values.put("examineeId", item.examineeId);
                values.put("lessonNumber", item.lessonNumber);
                values.put("seconds", item.milliseconds / 1000);
                values.put("moveCount", item.moveCount);
                MeteorSingleton.getInstance().insert("PlayLogs", values);
                item.sent = true;
                item.save();
            }

            String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            for (PreQuestionnaireResult item : preQuestionnaireResults) {
                Map<String, Object> values = new HashMap<>();
                values.put("created_at", item.created_at);
                values.put("examineeId", item.examineeId);
                values.put("androidId", androidId);
                values.put("type", "豪華版");
                values.put("sex", item.sex);
                values.put("age", item.age);
                values.put("knowledgeOfProgramming", item.knowledgeOfProgramming);
                values.put("knowledgeOfMimicDance", item.knowledgeOfMimicDance);
                values.put("desireToLearn", item.desireToLearn);
                values.put("fun", item.fun);
                values.put("feasibility", item.feasibility);
                values.put("usefulness", item.usefulness);
                MeteorSingleton.getInstance().insert("PreQuestionnaireResults", values);
                item.sent = true;
                item.save();
            }

            for (PostQuestionnaireResult item : postQuestionnaireResults) {
                Map<String, Object> values = new HashMap<>();
                values.put("created_at", item.created_at);
                values.put("examineeId", item.examineeId);
                values.put("gladness", item.gladness);
                values.put("vexation", item.vexation);
                values.put("desireToPlay", item.desireToPlay);
                values.put("additionalPlayTime", item.additionalPlayTime);
                values.put("desireToLearn", item.desireToLearn);
                values.put("fun", item.fun);
                values.put("feasibility", item.feasibility);
                values.put("usefulness", item.usefulness);
                values.put("opinion", item.opinion);
                MeteorSingleton.getInstance().insert("PostQuestionnaireResults", values);
                item.sent = true;
                item.save();
            }
            Log.d("upload", "uploaded");
        } catch (Exception e) {
        }
    }

    @Override
    public List<Program> getLessonProgram(int lessonId, int position) {
        List<Program> list = new ArrayList<>();
        list.add(new Program(Command.LEFT_HAND_UP, Command.NOP));
        list.add(new Program(Command.LEFT_HAND_DOWN, Command.NOP));
        list.add(new Program(Command.RIGHT_HAND_UP, Command.NOP));
        list.add(new Program(Command.RIGHT_HAND_DOWN, Command.NOP));
        return list;
    }
}
