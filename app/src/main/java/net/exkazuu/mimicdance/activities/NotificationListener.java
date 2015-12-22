/**
 * Created by t-yokoi on 2015/10/06.
 */
package net.exkazuu.mimicdance.activities;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.Toast;

import net.exkazuu.mimicdance.models.program.Program;

import java.util.ArrayList;

public class NotificationListener extends NotificationListenerService {

    private Handler handler = new Handler();
    private String comName;

    // [1]
    @Override
    public void onNotificationPosted(final StatusBarNotification sbn) {
        final String msg = sbn.getPackageName();
        handler.post(new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                ArrayList<Program> com = new ArrayList<Program>();
                int flag = 0;
                if (msg.equals("jp.mynavi.notification.android.notificationsample")) {
                    comName = "Fcom";
                } else if (msg.equals("com.google.android.gm")) {
                    comName = "Gcom";
                } else if (msg.equals("com.google.android.calendar")) {
                    comName = "Ccom";
                } else if (msg.equals("com.twitter.android")) {
                    comName = "Tcom";
                } else if (msg.equals("com.facebook.katana")) {
                    comName = "Fcom";
                } else {
                    comName = "よくわからないやつ";
                    flag = 1;
                }

                if (flag != 1) {
//                    //データベースから読み込む
                    String str = "data/data/" + getPackageName() + "/Sample.db";
                    SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(str, null);

                    String qry1 = "CREATE TABLE " + comName + " (id INTEGER PRIMARY KEY, text STRING)";
                    String qry3 = "SELECT * FROM " + comName;

                    //テーブルの作成
                    try {
                        db.execSQL(qry1);
                    } catch (SQLException e) {
                        Log.e("ERROR", e.toString());
                    }

                    //データの検索
                    Cursor cr = db.rawQuery(qry3, null);
                    //startManagingCursor(cr);

                    int x = 0;
                    int y = 0;
                    if(!cr.moveToFirst()) {
                        cr.close();
                        return;
                    }

                    int t = cr.getColumnIndex("text");
                    do {
                        String text = cr.getString(t);
                        String program[] = text.split(",", 2);
                        Program p = new Program();
                        p.setCommands(0,program[0]);
                        p.setCommands(1,program[1]);
                        com.add(p);
                    } while(cr.moveToNext());

                    cr.close();
                    //db.close();

                    if (comName.equals("Gcom") || comName.equals("Ccom") || comName.equals("Tcom") || comName.equals("Fcom")) {
                        Toast.makeText(getApplicationContext(), comName, Toast.LENGTH_SHORT).show();
                        new MiniBearHandler(com,getApplicationContext(), new ArduinoBear());
                    }
                    System.out.println(msg);
                    Toast.makeText(getApplicationContext(), comName, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    // [2]
    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        final String msg = sbn.getPackageName();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), msg + "消えた", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
