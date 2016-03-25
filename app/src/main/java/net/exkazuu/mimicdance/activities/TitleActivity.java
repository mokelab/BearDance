package net.exkazuu.mimicdance.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import net.exkazuu.mimicdance.R;
import net.exkazuu.mimicdance.models.lesson.LessonDAO;
import net.exkazuu.mimicdance.models.lesson.LessonDAOImpl;
import net.exkazuu.mimicdance.pages.title.TitleFragment;

public class TitleActivity extends AppCompatActivity {

    private LessonDAO lessonDAO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArduinoManager.register(this);
        setContentView(R.layout.activity_main);

        lessonDAO = new LessonDAOImpl(this);
        lessonDAO.init();

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.add(R.id.container, TitleFragment.newInstance());

            transaction.commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        PlugManager.register(this);
        ArduinoManager.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        PlugManager.unregister(this);
        ArduinoManager.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ArduinoManager.unregister(this);
    }

    public LessonDAO getLessonDAO() {
        return lessonDAO;
    }
/*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!MeteorSingleton.isCreated()) {
            MeteorSingleton.createInstance(this, "ws://mimic-dance-server.herokuapp.com/websocket");
        }

        requestWindowFeature(Window.FEATURE_NO_TITLE); // タイトルバー非表示
        setContentView(R.layout.title);

        Button helpButton = (Button) findViewById(R.id.start_button);
        helpButton.setVisibility(View.VISIBLE);
        Button startButton = (Button) findViewById(R.id.help_button);
        startButton.setVisibility(View.VISIBLE);
        Button freeButton = (Button) findViewById(R.id.free_button);
        freeButton.setVisibility(View.GONE);
        Button preButton = (Button) findViewById(R.id.pre_ques_button);
        preButton.setVisibility(View.INVISIBLE);
        Button postButton = (Button) findViewById(R.id.post_ques_button);
        postButton.setVisibility(View.INVISIBLE);
        Button notificationButton = (Button) findViewById(R.id.notification_button);
        notificationButton.setVisibility(View.VISIBLE);

        Toast.makeText(TitleActivity.this, "test", Toast.LENGTH_SHORT).show();
        Log.v("","test");


        Button kumaBottun = (Button) findViewById(R.id.kuma_test);
        kumaBottun.setVisibility(View.VISIBLE);

        uploadData();
    }

    public void startHelpActivity(View view) {
        uploadData();
//        startHelpActivity(false);
    }

    public void startLessonListActivity(View view) {
        uploadData();
//        startLessonListActivity(true);
    }

    public void startPreQuestionnaireActivity(View view) {
        uploadData();
//        startPreQuestionnaireActivity(false);
    }

    public void startPostQuestionnaireActivity(View view) {
        uploadData();
//        startPostQuestionnaireActivity(false);
    }

    public void freePlay(View view) {
    }

    public void startNotificationActivity(View view) {
//        startNotificationActivity(false);
    }

    public void uploadData() {
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

            String androidId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
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
*/
}
