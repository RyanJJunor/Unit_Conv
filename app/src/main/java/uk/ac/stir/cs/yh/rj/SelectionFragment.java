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

class SelectionFragment extends Fragment {
    private SharedViewModel model;
    private ConversionDbMethods dbMethods;
    private int currentCategory;
    private int unit1Cat;
    private int unit2Cat;

    private Spinner spinnerUnitType;
    private Spinner spinnerUnit1;
    private Spinner spinnerUnit2;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getFragmentManager().beginTransaction().add(this, getString(R.string.tag_selection));

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

        spinnerUnitType = view.findViewById(R.id.spinnerUnitCategory);
        spinnerUnit1 = view.findViewById(R.id.spinnerUnitFrom);
        spinnerUnit2 = view.findViewById(R.id.spinnerUnitTo);

        spinnerUnit1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (model.getUnit2Pos() == position) {

                    model.setUnit2Pos(model.getUnit1Pos());

                    spinnerUnit2.setSelection(model.getUnit2Pos());
                    setUnit2(spinnerUnit2, model.getUnit2Pos());
                }
                unit1Cat = currentCategory;
                model.setUnit1Pos(position);
                setUnit1(spinnerUnit1, position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerUnit2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (model.getUnit1Pos() == position) {

                    model.setUnit1Pos(model.getUnit2Pos());

                    spinnerUnit1.setSelection(model.getUnit1Pos());
                    setUnit1(spinnerUnit1, model.getUnit1Pos());
                }
                unit2Cat = currentCategory;
                model.setUnit2Pos(position);
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

                if (model.getCategory() != currentCategory) {

                    model.setUnit1Pos(0);
                    model.setUnit2Pos(1);
                }

                populateUnitSpinner(spinnerUnitType, position, spinnerUnit1, spinnerUnit2);
                model.setCategory(currentCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        populateUnitTypeSpinner(spinnerUnitType);


    }

    @Override
    public void onStart() {
        super.onStart();
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

        spinnerUnit1.setAdapter(adapter);
        spinnerUnit2.setAdapter(adapter);

        //The first time the fragment is created, start with the units 0 and 1 selected
        if (model.getUnit1Pos() == -1) {
            spinnerUnit1.setSelection(0);
            spinnerUnit2.setSelection(1);
            model.setUnit1Pos(0);
            model.setUnit2Pos(1);
        } else {
            spinnerUnit1.setSelection(model.getUnit1Pos());
            spinnerUnit2.setSelection(model.getUnit2Pos());
        }


    }

    private void setUnit1(Spinner unit1, int pos) {
        model.loadUnit1(unit1.getAdapter().getItem(pos).toString());
        //Ensures setFormula isn't called unless both units are ready to be used
        if (model.getUnit1().getValue() != null && model.getUnit2().getValue() != null && unit1Cat == unit2Cat && model.getUnit1Pos() != model.getUnit2Pos()) {
            setFormula();
        }
    }

    private void setUnit2(Spinner unit2, int pos) {
        model.loadUnit2(unit2.getAdapter().getItem(pos).toString());
        //Ensures setFormula isn't called unless both units are ready to be used
        if (model.getUnit2().getValue() != null && model.getUnit1().getValue() != null && unit1Cat == unit2Cat && model.getUnit1Pos() != model.getUnit2Pos()) {
            setFormula();
        }
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
