package uk.ac.stir.cs.yh.rj;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import uk.ac.stir.cs.yh.rj.db.ConversionDatabaseContract.Conversions;
import uk.ac.stir.cs.yh.rj.db.ConversionDbMethods;

public class SelectionFragment extends Fragment {
    private SharedViewModel model;
    private ConversionDbMethods dbMethods;
    private int unit1Pos;
    private int unit2Pos;
    private int currentCategory;
    private int unit1Cat;
    private int unit2Cat;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbMethods = new ConversionDbMethods(getContext());
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

                if (unit2Pos == position) {

                    unit2Pos = unit1Pos;


                    spinnerUnit2.setSelection(unit2Pos);
                    setUnit2(spinnerUnit2, unit2Pos);
                }
                unit1Cat = currentCategory;
                unit1Pos = position;
                setUnit1(spinnerUnit1, position);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerUnit2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (unit1Pos == position) {

                    unit1Pos = unit2Pos;


                    spinnerUnit1.setSelection(unit1Pos);
                    setUnit1(spinnerUnit1, unit1Pos);
                }
                unit2Cat = currentCategory;
                unit2Pos = position;
                setUnit2(spinnerUnit2, position);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerUnitType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                currentCategory = position;
                populateUnitSpinner(spinnerUnitType, position, spinnerUnit1, spinnerUnit2);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        populateUnitTypeSpinner(spinnerUnitType);

    }

    //todo tidy up, fix context?
    private void populateUnitTypeSpinner(Spinner spinner) {

        String[] projection = {
                Conversions.COLUMN_NAME_CATEGORY
        };

        ArrayList<String> categories = dbMethods.selectStatement(true, projection, null, null);

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

        ArrayList<String> units = dbMethods.selectStatement(true, projection, selection, selectionArgs);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, units);


        //Ensuring that whenever the unit spinners are populated, unit2 is always position 1

        spinnerUnit1.setAdapter(adapter);


        spinnerUnit2.setAdapter(adapter);
        //Needed ass unit2Cat is only changed one the onchangelistener is called, which it isn't
        // until after this method is finished. So when changing unit type, at this point, unit1
        // changes to the new type before unit2 does, but the two category variables are still the
        // same
        unit2Cat = currentCategory;
        setUnit2(spinnerUnit2, 1);
        spinnerUnit2.setSelection(1);
        unit1Pos = 0;
        unit2Pos = 1;

    }

    private void setUnit1(Spinner unit1, int pos) {
        model.loadUnit1(unit1.getAdapter().getItem(pos).toString());
        if (model.getUnit1() != null && unit1Cat == unit2Cat && unit1Pos != unit2Pos)
            setFormula();
    }

    private void setUnit2(Spinner unit2, int pos) {
        model.loadUnit2(unit2.getAdapter().getItem(pos).toString());
        if (model.getUnit2() != null && unit1Cat == unit2Cat && unit1Pos != unit2Pos)
            setFormula();
    }

    private void setFormula() {

        String[] projection = {
                Conversions.COLUMN_NAME_FORMULA
        };

        String selection = Conversions.COLUMN_NAME_PRIMARY_UNIT + " = ? AND " + Conversions.COLUMN_NAME_SECONDARY_UNIT + " = ? ";


        String[] selectionArgs = {

                model.getUnit1().getValue(), model.getUnit2().getValue()

        };

        ArrayList<String> formula = dbMethods.selectStatement(true, projection, selection, selectionArgs);

        model.loadFormula(Double.parseDouble(formula.get(0)));


    }

}
