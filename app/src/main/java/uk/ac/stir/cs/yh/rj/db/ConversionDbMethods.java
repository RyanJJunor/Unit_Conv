package uk.ac.stir.cs.yh.rj.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ConversionDbMethods {

    private SQLiteDatabase db;


    public ConversionDbMethods(Context context) {

        ConversionDbHelper dbHelper = new ConversionDbHelper(context);
        db = dbHelper.getWritableDatabase();

    }

    public ArrayList<String> selectStatement(boolean distinct, String[] projection, String selection, String[] selectionArgs){

        Cursor cursor = db.query(
                distinct,
                ConversionDatabaseContract.Conversions.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null,
                null);

        ArrayList<String> units = new ArrayList<>();
        while (cursor.moveToNext()) {
            String itemId = cursor.getString(
                    cursor.getColumnIndexOrThrow(projection[0]));
            units.add(itemId);
        }

        cursor.close();

        return units;
    }
}
