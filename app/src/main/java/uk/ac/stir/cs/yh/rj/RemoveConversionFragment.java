package uk.ac.stir.cs.yh.rj;

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

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;

import uk.ac.stir.cs.yh.rj.db.ConversionDatabaseContract.Conversions;
import uk.ac.stir.cs.yh.rj.db.ConversionDbMethods;


public class RemoveConversionFragment extends Fragment {

    private ConversionDbMethods dbMethods;
    private Spinner spinnerConversions;
    private int spinnerPos;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getFragmentManager().beginTransaction().add(this, "remove_conversion_fragment");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.remove_conversion, container, false);
    }


    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbMethods = new ConversionDbMethods(getContext());

        spinnerConversions = view.findViewById(R.id.spinnerRemoveConv);

        Button buttonRemove = view.findViewById(R.id.buttonRemoveConversion);

        populateSpinner();

        spinnerConversions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerPos = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonRemove.setOnClickListener(v -> {


            String selection = Conversions.COLUMN_NAME_CATEGORY + " = ? ";

            String[] selectionArgs = {
                    spinnerConversions.getAdapter().getItem(spinnerPos).toString()
            };

            if (dbMethods.deleteStatement(selection, selectionArgs) != 0) {

                populateSpinner();
                Snackbar.make(view, "Conversion Removed", 1000).show();


                SelectionFragment fragment = (SelectionFragment)
                        getFragmentManager().findFragmentByTag("selection_fragment");

                getFragmentManager().beginTransaction()
                        .detach(fragment)
                        .attach(fragment)
                        .commit();

            }


        });

    }


    private void populateSpinner() {

        String[] projection = {
                Conversions.COLUMN_NAME_CATEGORY
        };

        String selection = Conversions.COLUMN_NAME_ADDED_CONVERSION_UNIQUE + " IS NOT NULL ";


        ArrayList<String> conversions = dbMethods.selectStatement(true, projection, selection, null);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, conversions);

        spinnerConversions.setAdapter(adapter);


    }


}
