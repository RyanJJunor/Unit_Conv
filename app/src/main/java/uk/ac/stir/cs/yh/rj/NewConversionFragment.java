package uk.ac.stir.cs.yh.rj;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import uk.ac.stir.cs.yh.rj.db.ConversionDatabaseContract.Conversions;
import uk.ac.stir.cs.yh.rj.db.ConversionDbMethods;


class NewConversionFragment extends Fragment {


    private ConversionDbMethods dbMethods;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getFragmentManager().beginTransaction().add(this, getString(R.string.tag_new_conversion));
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

        dbMethods = new ConversionDbMethods(getContext());

        EditText editTextConversionName = view.findViewById(R.id.editTextConversionName);

        EditText editTextNewUnit1 = view.findViewById(R.id.editTextNewUnitFrom);

        EditText editTextNewUnit2 = view.findViewById(R.id.editTextNewUnitTo);

        EditText editTextNewRate = view.findViewById(R.id.editTextNewRate);

        Button buttonSelectNewConversion = view.findViewById(R.id.buttonSelectNewConversion);

        buttonSelectNewConversion.setOnClickListener((v -> {

            String name = editTextConversionName.getText().toString().trim();
            String unit1 = editTextNewUnit1.getText().toString().trim();
            String unit2 = editTextNewUnit2.getText().toString().trim();
            String unitRate = editTextNewRate.getText().toString().trim();


            if (validInput(name, unit1, unit2, unitRate)) {


                String[] projection = {Conversions.COLUMN_NAME_CATEGORY};
                ArrayList<String> names = dbMethods.selectStatement(true, projection, null, null);

                projection[0] = "COUNT(" + Conversions.COLUMN_NAME_ADDED_CONVERSION_UNIQUE + ")";
                String selection = Conversions.COLUMN_NAME_ADDED_CONVERSION_UNIQUE + " IS NOT NULL";
                ArrayList<String> customConversions = dbMethods.selectStatement(true, projection, selection, null);

                if (Integer.parseInt(customConversions.get(0)) == 5) {
                    Snackbar.make(view, getString(R.string.limit_reached), 2000).show();
                    return;
                }

                if (names.contains(name)) {
                    Snackbar.make(view, getString(R.string.duplicate_conversion), 2000).show();
                    return;
                }


                if (dbMethods.insertStatement(name, unit1, unit2, unitRate) != -1) {
                    Snackbar.make(view, getString(R.string.snackConvAdded), 2000).show();

                    editTextConversionName.setText("");
                    editTextNewUnit1.setText("");
                    editTextNewUnit2.setText("");
                    editTextNewRate.setText("");

                    SelectionFragment fragmentSelect = (SelectionFragment)
                            getFragmentManager().findFragmentByTag(getString(R.string.tag_selection));

                    getFragmentManager().beginTransaction()
                            .detach(fragmentSelect)
                            .attach(fragmentSelect)
                            .commit();

                    RemoveConversionFragment fragmentRemove = (RemoveConversionFragment)
                            getFragmentManager().findFragmentByTag(getString(R.string.tag_remove));

                    getFragmentManager().beginTransaction()
                            .detach(fragmentRemove)
                            .attach(fragmentRemove)
                            .commit();

                } else {
                    Snackbar.make(view, getString(R.string.conversion_error), 2000).show();
                }

            } else {
                Snackbar.make(view, getString(R.string.invalid_input), 2000).show();
            }

        }));
    }

    private boolean validInput(String name, String unit1, String unit2, String unitRate) {

        boolean isValid = true;

        if (name.equals("") || unit1.equals("") || unit2.equals("") || unitRate.equals(""))
            isValid = false;

        if (unit1.equals(unit2))
            isValid = false;

        if (name.matches(getString(R.string.regex)) || unit1.matches(getString(R.string.regex)) || unit2.matches(getString(R.string.regex)))
            isValid = false;


        return isValid;

    }


}
