package com.boss.cuncis.sqliteudemy;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseAdapter {

    DatabaseHelper helper;
    SQLiteDatabase db;

    public DatabaseAdapter(Context context) {
        helper = new DatabaseHelper(context);
        db = helper.getWritableDatabase();
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        private static final String DB_NAME = "mydb.db";
        private static final String TABLE_NAME = "contacts";
        //when you do change the stucture of the database change the version number 1 to 2
        private static final int DB_VERSION = 1;
        private static final String KEY_ROWID = "_id";
        private static final String KEY_NAME = "name";
        private static final String KEY_EMAIL = "email";
        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
                KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME + " TEXT, " +
                KEY_EMAIL + " TEXT)";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        private Context context;

        public DatabaseHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE);
                Toast.makeText(context, "onCreate called", Toast.LENGTH_SHORT).show();
            } catch (SQLException e) {
                Toast.makeText(context, "" + e, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            try {
                Toast.makeText(context, "onUpgrade called", Toast.LENGTH_SHORT).show();
                db.execSQL(DROP_TABLE);
                onCreate(db);
            } catch (SQLException e) {
                Toast.makeText(context, "" + e, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
