package net.exkazuu.mimicdance.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import net.exkazuu.mimicdance.R;
import net.exkazuu.mimicdance.models.program.Program;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by t-yokoi on 2015/10/13.
 */
public class NotificationActivity extends BaseActivity {
    Program[] mCommands = new Program[12];
    String[] commands = new String[4];
    ArrayList<String> Gcom = new ArrayList<String>();
    ArrayList<String> Ccom = new ArrayList<String>();
    ArrayList<String> Tcom = new ArrayList<String>();
    ArrayList<String> Fcom = new ArrayList<String>();
    String name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);

        //コードの初期化
        for (int i = 0; i < 12; i++) {
            mCommands[i] = new Program();
        }

//        //nameは通知プログラム名
//        Intent intent = getIntent();
//        name = intent.getStringExtra("name");
//        System.out.println(name);
//
//        //データベースからプログラムを読み込む
//        String str = "data/data/" + getPackageName() + "/Sample.db";
//        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(str, null);
//
//        String qry1 = "CREATE TABLE " + name + " (id INTEGER PRIMARY KEY, text STRING)";
//        String qry3 = "SELECT * FROM " + name;
//
//        //テーブルの作成
//        try {
//            db.execSQL(qry1);
//        } catch (SQLException e) {
//            Log.e("ERROR", e.toString());
//        }
//
//        //データの検索
//        Cursor cr = db.rawQuery(qry3, null);
//        //startManagingCursor(cr);
//
//        int x = 0;
//        int y = 0;
//        while (cr.moveToNext()) {
//            int t = cr.getColumnIndex("text");
//            String text = cr.getString(t);
//            if (text.equals("")) {
//                program[x][y] = "";
//            } else {
//                program[x][y] = text;
//            }
//            x++;
//            y++;
//            if (x == 2) {
//                x = 0;
//            } else {
//                y--;
//            }
//        }
//
        //db.close();

        //textviewに表示
//        TextView text = (TextView) findViewById(R.id.programs);
//        text.setText(program[0][0] + program[1][0] + "\n"
//            + program[0][1] + program[1][1] + "\n"
//            + program[0][2] + program[1][2] + "\n"
//            + program[0][3] + program[1][3] + "\n"
//            + program[0][4] + program[1][4] + "\n"
//            + program[0][5] + program[1][5] + "\n"
//            + program[0][6] + program[1][6] + "\n"
//            + program[0][7] + program[1][7] + "\n"
//            + program[0][8] + program[1][8] + "\n"
//            + program[0][9] + program[1][9] + "\n"
//            + program[0][10] + program[1][10] + "\n"
//            + program[0][11] + program[1][11]);

        //記述可能部分
        initializeCanWrite();
        initializeProgramIcons();


        //ここから

/*
        // 背景たち
        int[][] cellsId = new int[2][12];
        ImageView[][] cells = new ImageView[2][12];
        DragViewListener[][] backgroundlistener = new DragViewListener[2][12];
        int[][] flag = new int[2][12];

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 12; j++) {
                flag[i][j] = 0;
                cellsId[i][j] = this.getResources().getIdentifier("image" + i + "_" + j, "id", this.getPackageName());
                cells[i][j] = (ImageView) findViewById(cellsId[i][j]);
                backgroundlistener[i][j] = new DragViewListener(this, cells, program);
                cells[i][j].setOnTouchListener(backgroundlistener[i][j]);
            }
        }
*/

//        //保存されていたものをアイコンに戻す用
//        for (int i = 0; i < 2; i++) {
//            for (int j = 0; j < 12; j++) {
//                if (program[i][j].equals("Gmail")) {
//                    cells[i][j].setImageResource(R.drawable.icon_num0);
//                } else if (program[i][j].equals("Calendar")) {
//                    cells[i][j].setImageResource(R.drawable.icon_num0);
//                } else if (program[i][j].equals("Twitter")) {
//                    cells[i][j].setImageResource(R.drawable.icon_num0);
//                } else if (program[i][j].equals("Facebook")) {
//                    cells[i][j].setImageResource(R.drawable.icon_num0);
//                } else if (program[i][j].equals("for")) {
//                    cells[i][j].setImageResource(R.drawable.icon_loop);
//                } else if (program[i][j].equals("forend")) {
//                    cells[i][j].setImageResource(R.drawable.icon_end_loop);
//                } else if (program[i][j].equals("ON")) {
//                    cells[i][j].setImageResource(R.drawable.icon_num1);
//                } else if (program[i][j].equals("OFF")) {
//                    cells[i][j].setImageResource(R.drawable.icon_num1);
//                } else if (program[i][j].equals("FadeIn")) {
//                    cells[i][j].setImageResource(R.drawable.icon_num1);
//                } else if (program[i][j].equals("FadeOut")) {
//                    cells[i][j].setImageResource(R.drawable.icon_num1);
//                } else if (program[i][j].equals("if")) {
//                    cells[i][j].setImageResource(R.drawable.icon_if);
//                } else if (program[i][j].equals("else")) {
//                    cells[i][j].setImageResource(R.drawable.icon_else);
//                } else if (program[i][j].equals("elseif")) {
//                    cells[i][j].setImageResource(R.drawable.icon_num2);
//                } else if (program[i][j].equals("ifend")) {
//                    cells[i][j].setImageResource(R.drawable.icon_end_if);
//                } else if (program[i][j].equals("1")) {
//                    cells[i][j].setImageResource(R.drawable.icon_num1);
//                } else if (program[i][j].equals("2")) {
//                    cells[i][j].setImageResource(R.drawable.icon_num2);
//                } else if (program[i][j].equals("3")) {
//                    cells[i][j].setImageResource(R.drawable.icon_num3);
//                } else if (program[i][j].equals("4")) {
//                    cells[i][j].setImageResource(R.drawable.icon_num4);
//                } else if (program[i][j].equals("5")) {
//                    cells[i][j].setImageResource(R.drawable.icon_num5);
//                } else if (program[i][j].equals("6")) {
//                    cells[i][j].setImageResource(R.drawable.icon_num6);
//                } else if (program[i][j].equals("7")) {
//                    cells[i][j].setImageResource(R.drawable.icon_num7);
//                } else if (program[i][j].equals("8")) {
//                    cells[i][j].setImageResource(R.drawable.icon_num8);
//                } else if (program[i][j].equals("9")) {
//                    cells[i][j].setImageResource(R.drawable.icon_num9);
//                } else {
//                    cells[i][j].setImageResource(R.drawable.back_image);
//                }
//            }
//        }

//        //次の入力場所の表示
//        for (int j = 0; j < 12; j++) {
//            int f = 0;
//            for (int i = 0; i < 1; i++) {
//                if (program[i][j] == "") {
//                    if (f == 0) {
//                        canWrite[i][j].setImageResource(R.drawable.icon_writable);
//                        f = 1;
//                    } else {
//                        canWrite[i][j].setImageResource(R.drawable.icon_none);
//                    }
//                } else {
//                    canWrite[i][j].setImageResource(R.drawable.icon_none);
//                }
//            }
//        }

//        //2列目以降入力可能にするかどうかの変更
//        for (int j = 0; j < 12; j++) {
//            if (!program[0][j].equals("") && !program[0][j].equals("if") && !program[0][j].equals("elseif") && !program[0][j].equals("for")) {
//                canWrite[1][j].setImageResource(R.drawable.icon_none);
//            }
//        }


//        // ドラッグ対象Viewとイベント処理クラスを紐付ける
//        // アイコンたち
//        int[][] iconsId = new int[2][14];
//        ImageView[][] dragView = new ImageView[2][14];
//        DragViewListener[][] listener = new DragViewListener[2][14];
//        for (int i = 0; i < 14; i++) {
//            iconsId[0][i] = this.getResources().getIdentifier("imageView" + (i + 1), "id", this.getPackageName());
//            dragView[0][i] = (ImageView) findViewById(iconsId[0][i]);
//            listener[0][i] = new DragViewListener(this, cells, program);
//            dragView[0][i].setOnTouchListener(listener[0][i]);
//        }
//        for (int i = 1; i < 10; i++) {
//            iconsId[1][i] = this.getResources().getIdentifier("imageView0" + i, "id", this.getPackageName());
//            dragView[1][i] = (ImageView) findViewById(iconsId[1][i]);
//            listener[1][i] = new DragViewListener(this, cells, program);
//            dragView[1][i].setOnTouchListener(listener[1][i]);
//        }
//    }

//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.helpInMenu:
//                Intent intent = new Intent(this, HelpActivity.class);
//                startActivity(intent);
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    //保存ボタン
//    public void onClickSaveButton(View v) {
//        ArrayList<String> oldcommands = new ArrayList<String>();
//        for (int j = 0; j < 12; j++) {
//            for (int i = 0; i < 2; i++) {
//                if (program[i][j] != "") {
//                    oldcommands.add(program[i][j]);
//                }
//            }
//        }
//        Parser parser = new Parser(oldcommands, commands, Gcom, Ccom, Tcom, Fcom);
//        parser.main();
//        if (!parser.isErr()) {
//
//            //データベースにプログラムを保存
//            String str = "data/data/" + getPackageName() + "/Sample.db";
//            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(str, null);
//            String delete = "DELETE FROM " + name;
//            db.execSQL(delete);
//
//            for (int j = 0; j < 12; j++) {
//                for (int i = 0; i < 2; i++) {
//                    String qry2 = "INSERT INTO " + name + "(text) VALUES('" + program[i][j] + "')";
//                    db.execSQL(qry2);
//                }
//            }
//            //db.close();
//
//            Toast.makeText(this, "保存しました。", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "文法がどこか違うよ", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public void onClickReturnButton(View view) {
//        startTitleActivity(true);
//        finish();
//    }
//
//    public void onClickGmailButton(View v) {
//        LayoutInflater inflater = getLayoutInflater();
//        View layout = inflater.inflate(R.layout.toast_gmail, null);
//
//        Toast gtoast = new Toast(getApplicationContext());
//        gtoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//        gtoast.setView(layout);
//        gtoast.show();
//
//        TextView text = (TextView) findViewById(R.id.programs);
//        text.setText(commands[0]);
//        ShineLED LED = new ShineLED(Gcom, this);
//        LED.main();
//    }
//
//    public void onClickCalendarButton(View v) {
//        LayoutInflater inflater = getLayoutInflater();
//        View layout = inflater.inflate(R.layout.toast_calendar, null);
//
//        Toast gtoast = new Toast(getApplicationContext());
//        gtoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//        gtoast.setView(layout);
//        gtoast.show();
//
//        TextView text = (TextView) findViewById(R.id.programs);
//        text.setText(commands[1]);
//        ShineLED LED = new ShineLED(Ccom, this);
//        LED.main();
//    }
//
//    public void onClickTwitterButton(View v) {
//        LayoutInflater inflater = getLayoutInflater();
//        View layout = inflater.inflate(R.layout.toast_twitter, null);
//
//        Toast gtoast = new Toast(getApplicationContext());
//        gtoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//        gtoast.setView(layout);
//        gtoast.show();
//
//        TextView text = (TextView) findViewById(R.id.programs);
//        text.setText(commands[2]);
//        ShineLED LED = new ShineLED(Tcom, this);
//        LED.main();
//    }
//
//    public void onClickFacebookButton(View v) {
//        LayoutInflater inflater = getLayoutInflater();
//        View layout = inflater.inflate(R.layout.toast_facebook, null);
//
//        Toast gtoast = new Toast(getApplicationContext());
//        gtoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//        gtoast.setView(layout);
//        gtoast.show();
//
//        TextView text = (TextView) findViewById(R.id.programs);
//        text.setText(commands[3]);
//        ShineLED LED = new ShineLED(Fcom, this);
//        LED.main();
//    }


    }

    private void initializeCanWrite() {
        WritableView wv[][] = new WritableView[12][2];
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 2; j++) {
                int id = this.getResources().getIdentifier("canwrite" + j + "_" + i, "id", this.getPackageName());
                Log.v("id", "i=" + i + " j=" + j + " id=" + id);
                wv[i][j] = (WritableView) findViewById(id);
                wv[i][j].setLocate(i, j);
                wv[i][j].setCommand("");

            }
        }
    }

    private void initializeProgramIcons() {
        Map<Integer, String> map = new HashMap<>();
        map.put(R.id.action_0, "right_hand_up");
        map.put(R.id.action_1, "right_hand_down");
        map.put(R.id.action_2, "left_hand_up");
        map.put(R.id.action_3, "left_hand_down");
        map.put(R.id.number_0,"num_1");
        map.put(R.id.number_1,"num_2");
        map.put(R.id.number_2,"num_3");
        map.put(R.id.number_3,"num_4");
        map.put(R.id.number_4,"num_5");
        map.put(R.id.number_5,"num_6");
        map.put(R.id.number_6,"num_7");
        map.put(R.id.number_7,"num_8");
        map.put(R.id.number_8,"num_9");
        map.put(R.id.loop_0,"loop");
        map.put(R.id.loop_1,"loop_end");
        map.put(R.id.branch_0,"if");
        map.put(R.id.branch_1,"if");

        Set<Map.Entry<Integer, String>> entries = map.entrySet();
        for (Map.Entry<Integer, String> entry : entries) {
            int id = entry.getKey();
            ProgramIconView piv = (ProgramIconView) findViewById(id);
            piv.setVisibility(View.VISIBLE);
            piv.setCommand(entry.getValue());

        }
    }

    public void onClickReturnButton(View view) {
        finish();
    }

    public void onClickSaveButton(View view) {
        Toast.makeText(this, "Saved.", Toast.LENGTH_SHORT).show();
        finish();
    }
}

