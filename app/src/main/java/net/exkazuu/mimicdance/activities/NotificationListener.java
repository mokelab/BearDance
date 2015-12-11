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
                ArrayList<String> com = new ArrayList<String>();
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
//                    String str = "data/data/" + getPackageName() + "/Sample.db";
//                    SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(str, null);
//
//                    String qry1 = "CREATE TABLE " + comName + " (id INTEGER PRIMARY KEY, text STRING)";
//                    String qry3 = "SELECT * FROM " + comName;
//
//                    //テーブルの作成
//                    try {
//                        db.execSQL(qry1);
//                    } catch (SQLException e) {
//                        Log.e("ERROR", e.toString());
//                    }
//
//                    //データの検索
//                    Cursor cr = db.rawQuery(qry3, null);
//                    //startManagingCursor(cr);
//
//                    int x = 0;
//                    int y = 0;
//                    while (cr.moveToNext()) {
//                        int t = cr.getColumnIndex("text");
//                        String text = cr.getString(t);
//                        com.add(text);
//                    }
//                    //db.close();
//
//                    if (comName.equals("Gcom") || comName.equals("Ccom") || comName.equals("Tcom") || comName.equals("Fcom")) {
//                        Toast.makeText(getApplicationContext(), comName, Toast.LENGTH_SHORT).show();
//                    }
//                    System.out.println(msg);
                    Toast.makeText(getApplicationContext(), comName + "やで", Toast.LENGTH_SHORT).show();

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
                Toast.makeText(getApplicationContext(), msg + "消えたよん", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
