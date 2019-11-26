package uk.ac.stir.cs.yh.rj;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import uk.ac.stir.cs.yh.rj.db.ConversionDatabaseContract.Conversions;
import uk.ac.stir.cs.yh.rj.db.ConversionDbHelper;

public class SelectionFragment extends Fragment {
    //TODO read more about passing information and interfaces
    private ConversionDbHelper dbHelper;
    private SQLiteDatabase db;
    private SharedViewModel model;
    private ArrayList<String> selectedUnits = new ArrayList<>(2);
    private int unit1Pos;
    private int unit2Pos;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.unit_selection, container, false);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    //TODO DO this for the other fragment
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        model = new ViewModelProvider(this.getActivity(), new ViewModelProvider.NewInstanceFactory()).get(SharedViewModel.class);


        final Spinner spinnerUnitType = view.findViewById(R.id.spinner);
        final Spinner spinnerUnit1 = view.findViewById(R.id.spinner2);
        final Spinner spinnerUnit2 = view.findViewById(R.id.spinner3);

        spinnerUnit1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                setUnit1(spinnerUnit1, position);
                unit1Pos = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerUnit2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                setUnit2(spinnerUnit2, position);
                unit2Pos = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerUnitType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                populateUnitSpinner(spinnerUnitType, position, spinnerUnit1, spinnerUnit2);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button buttonSelect = view.findViewById(R.id.buttonSelect);

        buttonSelect.setOnClickListener(v -> {

            String[] projection = {
                Conversions.COLUMN_NAME_FORMULA
            };

            String selection = Conversions.COLUMN_NAME_PRIMARY_UNIT + " = ? AND " + Conversions.COLUMN_NAME_SECONDARY_UNIT + " = ? ";


            String[] selectionArgs = {

                    spinnerUnit1.getAdapter().getItem(unit1Pos).toString(), spinnerUnit2.getAdapter().getItem(unit2Pos).toString()

            };

            if (unit1Pos != unit2Pos) {
                //todo change these?
                Cursor cursor = db.query(
                        true,
                        Conversions.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null,
                        null);

                // todo assertion for only one result?
                ArrayList<String> formula = new ArrayList<>();
                while (cursor.moveToNext()) {
                    String itemId = cursor.getString(
                            cursor.getColumnIndexOrThrow(Conversions.COLUMN_NAME_FORMULA));
                    formula.add(itemId);
                }

                cursor.close();



                //todo change fomula.get(0)??
                model.select(selectedUnits, Double.parseDouble(formula.get(0)));

            }else
                model.select(selectedUnits, 1);


            onSelect();

        });

        populateUnitTypeSpinner(spinnerUnitType);

    }

    private void onSelect() {


        ConversionFragment frag2 = new ConversionFragment();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.frag_frame, frag2);
        transaction.addToBackStack(null);

        transaction.commit();

    }


    //todo tidy up, fix context?
    private void populateUnitTypeSpinner(Spinner spinner) {

        String[] projection = {
                Conversions.COLUMN_NAME_CATEGORY
        };

        dbHelper = new ConversionDbHelper(getContext());
        db = dbHelper.getWritableDatabase();

        Cursor cursor = db.query(true,
                Conversions.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null,
                null);

        ArrayList<String> categories = new ArrayList<>();
        while (cursor.moveToNext()) {
            String itemId = cursor.getString(
                    cursor.getColumnIndexOrThrow(Conversions.COLUMN_NAME_CATEGORY));
            categories.add(itemId);
        }

        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, categories);

        spinner.setAdapter(adapter);

    }

    private void populateUnitSpinner(Spinner spinnerUnitType, int position, Spinner spinnerUnit1, Spinner spinnerUnit2) {

        String[] projection = {
                Conversions.COLUMN_NAME_PRIMARY_UNIT
        };

        String selection =
                Conversions.COLUMN_NAME_CATEGORY + " = ?";

        String[] selectionArgs = {
                spinnerUnitType.getAdapter().getItem(position).toString()
        };

        Cursor cursor = db.query(
                true,
                Conversions.TABLE_NAME,
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
                    cursor.getColumnIndexOrThrow(Conversions.COLUMN_NAME_PRIMARY_UNIT));
            units.add(itemId);
        }

        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, units);

        spinnerUnit1.setAdapter(adapter);
        spinnerUnit2.setAdapter(adapter);

    }

    private void setUnit1(Spinner unit1, int pos){
        selectedUnits.add(0, unit1.getAdapter().getItem(pos).toString());
    }

    private void setUnit2(Spinner unit2, int pos){
        selectedUnits.add(1, unit2.getAdapter().getItem(pos).toString());
    }


}
