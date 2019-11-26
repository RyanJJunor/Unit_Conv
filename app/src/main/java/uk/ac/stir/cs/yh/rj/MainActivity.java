package uk.ac.stir.cs.yh.rj;

import uk.ac.stir.cs.yh.rj.db.ConversionDatabaseContract.Conversions;
import uk.ac.stir.cs.yh.rj.db.ConversionDbHelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    ConversionDbHelper dbHelper;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);



        //Checks to ensure the right layout is being used
        if (findViewById(R.id.frag_frame) != null) {


            // If being restored from a previous state do nothing
            // TODO CHECK THIS
            if (savedInstanceState != null) {
                return;
            }

            dbHelper = new ConversionDbHelper(this);
            db = dbHelper.getWritableDatabase();

            // Creating a new fragment
            SelectionFragment selectionFragment = new SelectionFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            selectionFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the fragment frame in the main_activity layout
            getSupportFragmentManager().beginTransaction().add(R.id.frag_frame, selectionFragment).commit();
        }
        //TODO Handle this
        else {

        }

    }

    //TODO fix this
    private double addConversion(String name, String formula, String category){

        ConversionDbHelper dbHelper = new ConversionDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Conversions.COLUMN_NAME_NAME, name);
        values.put(Conversions.COLUMN_NAME_FORMULA, formula);
        values.put(Conversions.COLUMN_NAME_CATEGORY, category);

        long newRowId = db.insert(Conversions.TABLE_NAME, null, values);
        System.out.println("NEW ROW ID: " + newRowId);

        return newRowId;
    }


    public void onUnitsSelected() {

        ConversionFragment frag2 = new ConversionFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.frag_frame, frag2);
        transaction.addToBackStack(null);

        transaction.commit();

    }

    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof SelectionFragment) {
            SelectionFragment frag1 = (SelectionFragment) fragment;

        }
    }

}