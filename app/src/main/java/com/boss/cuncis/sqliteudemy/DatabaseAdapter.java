package com.boss.cuncis.sqliteudemy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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

    public int deleteData(String name) {
        String whereArgs[] = {name};
        int count = db.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.KEY_NAME + "=?", whereArgs);    //count row
        return count;
    }

    public int updateEmail(String name, String email) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_EMAIL, email);
        String whereArgs[] = {name};
        int count = db.update(DatabaseHelper.TABLE_NAME, values, DatabaseHelper.KEY_NAME + "=?", whereArgs);

        return count;
    }

    public String getData(String name) {
        String colums[] = {DatabaseHelper.KEY_NAME, DatabaseHelper.KEY_EMAIL};
//        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, colums, DatabaseHelper.KEY_NAME + " LIKE '%" +
//                name + "%'", null, null, null,null);
        String selectioinArgs[] = {name};
        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, colums, DatabaseHelper.KEY_NAME + "=?",
                selectioinArgs, null, null, null);
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            int index1 = cursor.getColumnIndex(DatabaseHelper.KEY_NAME);
            int index2 = cursor.getColumnIndex(DatabaseHelper.KEY_EMAIL);
            String namePerson = cursor.getString(index1);
            String emailPerson = cursor.getString(index2);
            buffer.append(namePerson + " - " + emailPerson);
        }

        return buffer.toString();
    }

    public String getAllData() {
        String[] columns = {
                DatabaseHelper.KEY_ROWID,
                DatabaseHelper.KEY_NAME,
                DatabaseHelper.KEY_EMAIL
        };

        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null, null);
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            int index1 = cursor.getColumnIndex(DatabaseHelper.KEY_ROWID);
            int rowId = cursor.getInt(index1);

            int index2 = cursor.getColumnIndex(DatabaseHelper.KEY_NAME);
            String name = cursor.getString(index2);

            int index3 = cursor.getColumnIndex(DatabaseHelper.KEY_EMAIL);
            String email = cursor.getString(index3);

            buffer.append(rowId + ". " + name + " - " + email);
        }

        return buffer.toString();
    }

    public long insertData(String name, String email) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_NAME, name);
        values.put(DatabaseHelper.KEY_EMAIL, email);
        long id = db.insert(DatabaseHelper.TABLE_NAME, null, values);

        return id;
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
