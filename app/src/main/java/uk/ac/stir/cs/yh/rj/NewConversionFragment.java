package uk.ac.stir.cs.yh.rj;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import uk.ac.stir.cs.yh.rj.db.ConversionDatabaseContract.Conversions;
import uk.ac.stir.cs.yh.rj.db.ConversionDbHelper;


public class NewConversionFragment extends Fragment {

    private ConversionDbHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_conversion, container, false);
    }


    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText editTextConversionName = view.findViewById(R.id.editTextConversionName);

        EditText editTextNewUnit1 = view.findViewById(R.id.editTextNewUnit1);

        EditText editTextNewUnit2 = view.findViewById(R.id.editTextNewUnit2);

        EditText editTextNewRate = view.findViewById(R.id.editTextNewRate);

        Button buttonSelectNewConversion = view.findViewById(R.id.buttonSelectNewConversion);

        buttonSelectNewConversion.setOnClickListener((v -> {

            dbHelper = new ConversionDbHelper(getContext());
            db = dbHelper.getWritableDatabase();

            //todo don't allow unit1 and unit2 to have the same name
            String name = editTextConversionName.getText().toString();
            String unit1 = editTextNewUnit1.getText().toString();
            String unit2 = editTextNewUnit2.getText().toString();
            String unitRate = editTextNewRate.getText().toString();


            //todo make more robust
            if (!unit1.equals("") || !unit2.equals("") || !unitRate.equals("")) {

                String query =
                        "INSERT INTO " + Conversions.TABLE_NAME + " (" +
                                Conversions.COLUMN_NAME_PRIMARY_UNIT + "," +
                                Conversions.COLUMN_NAME_SECONDARY_UNIT + "," +
                                Conversions.COLUMN_NAME_FORMULA + ", " +
                                Conversions.COLUMN_NAME_CATEGORY + ", " +
                                Conversions.COLUMN_NAME_ADDED_CONVERSION_UNIQUE + ") " +
                                "VALUES " +
                                "('" + unit1 + "'," +
                                "'" + unit2 + "'," +
                                "'" + unitRate + "'," +
                                "'" + name + "'), " +
                                "('" + unit2 + "'," +
                                "'" + unit1 + "'," +
                                "'" + (1 / (Double.parseDouble(unitRate))) + "'," +
                                "'" + name + "'," +
                                "'" + name + "')";

                //db.execSQL(query);

                ContentValues cv = new ContentValues();

                cv.put(Conversions.COLUMN_NAME_PRIMARY_UNIT, unit1);
                cv.put(Conversions.COLUMN_NAME_SECONDARY_UNIT, unit2);
                cv.put(Conversions.COLUMN_NAME_FORMULA, unitRate);
                cv.put(Conversions.COLUMN_NAME_CATEGORY, name);
                cv.put(Conversions.COLUMN_NAME_ADDED_CONVERSION_UNIQUE, name);

                long result = db.insertWithOnConflict(Conversions.TABLE_NAME, null, cv, SQLiteDatabase.CONFLICT_IGNORE);

                System.out.println(result);



                cv.put(Conversions.COLUMN_NAME_PRIMARY_UNIT, unit2);
                cv.put(Conversions.COLUMN_NAME_SECONDARY_UNIT, unit1);
                cv.put(Conversions.COLUMN_NAME_FORMULA, (1 / (Double.parseDouble(unitRate))));
                cv.put(Conversions.COLUMN_NAME_CATEGORY, name);
                cv.remove(Conversions.COLUMN_NAME_ADDED_CONVERSION_UNIQUE);

                db.insert(Conversions.TABLE_NAME, null, cv);

            }

        }));
    }


}
