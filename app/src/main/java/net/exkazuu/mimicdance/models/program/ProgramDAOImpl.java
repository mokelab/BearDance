package net.exkazuu.mimicdance.models.program;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import net.exkazuu.mimicdance.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * 実装クラス
 */
public class ProgramDAOImpl implements ProgramDAO {
    private static final String TAG = "ProgramDAO";

    // region Table definition

    private static final String TABLE_NAME = "program";

    interface Columns extends BaseColumns {
        String COMMAND_0 = "command0";
        String COMMAND_1 = "command1";
    }

    private static final String SQL_CREATE = "CREATE TABLE " + TABLE_NAME + "(" +
        Columns._ID + " integer primary key," +
        Columns.COMMAND_0 + " text," +
        Columns.COMMAND_1 + " text)";

    // endregion

    private final SQLiteOpenHelper mHelper;

    public ProgramDAOImpl(Context context) {
        mHelper = new ProgramDB(context);
    }

    /**
     * テーブルを作成すべき時に呼ばれます
     * @param db データベースオブジェクト
     */
    public static void create(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void save(List<Program> programList) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        try {
            db.beginTransaction();
            // 一旦全部消す
            db.delete(TABLE_NAME, null, null);
            // 1行ずついれていく
            for (int i = 0 ; i < programList.size() ; ++i) {
                Program program = programList.get(i);

                ContentValues values = new ContentValues();
                values.put(Columns._ID, i);
                values.put(Columns.COMMAND_0, program.getCommand(0));
                values.put(Columns.COMMAND_1, program.getCommand(1));
                db.insert(TABLE_NAME, null, values);
            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.v(TAG, "Failed to save", e);
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    @Override
    public List<Program> load() {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        String orderBy = Columns._ID + " ASC";
        String limit = String.valueOf(Constants.NUM_PROGRAM_LINE);
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, orderBy, limit);
        if (cursor == null) {
            return createEmptyList();
        }
        if (!cursor.moveToFirst()) {
            cursor.close();
            db.close();
            return createEmptyList();
        }

        int com0Index = cursor.getColumnIndex(Columns.COMMAND_0);
        int com1Index = cursor.getColumnIndex(Columns.COMMAND_1);

        List<Program> list = new ArrayList<>(Constants.NUM_PROGRAM_LINE);
        do {
            String command0 = cursor.getString(com0Index);
            String command1 = cursor.getString(com1Index);
            list.add(new Program(command0, command1));
        } while (cursor.moveToNext());
        cursor.close();
        db.close();

        // 12個(Constants.NUM_PROGRAM_LINE個)なかったら空を追加する
        while(list.size() < Constants.NUM_PROGRAM_LINE) {
            list.add(new Program());
        }

        return list;
    }

    private List<Program> createEmptyList() {
        List<Program> list = new ArrayList<>(Constants.NUM_PROGRAM_LINE);
        for (int i = 0 ; i < Constants.NUM_PROGRAM_LINE ; ++i) {
            list.add(new Program());
        }
        return list;
    }
}
