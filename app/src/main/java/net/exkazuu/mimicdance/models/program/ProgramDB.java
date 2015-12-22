package net.exkazuu.mimicdance.models.program;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * プログラムを保存するためのデータベース
 */
public class ProgramDB extends SQLiteOpenHelper {
    private static final String DB_NAME = "user_program.db";
    private static final int DB_VERSION = 1;

    public ProgramDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        ProgramDAOImpl.create(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
