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

/**
 * The fragment that handles adding new conversions
 */
public class NewConversionFragment extends Fragment {

    private ConversionDbMethods dbMethods;
    private final int NUMBER_OF_CONVERSIONS_ALLOWED = 5;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Assign a tag to the fragment so I can retrieve it
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


            // Checks to see if the input is valid
            if (validInput(name, unit1, unit2, unitRate)) {

                //Gets the names/categories of all conversions
                String[] projection = {Conversions.COLUMN_NAME_CATEGORY};
                ArrayList<String> names = dbMethods.selectStatement(true, projection, null, null);

                //Gets the number of custom conversions added
                projection[0] = "COUNT(" + Conversions.COLUMN_NAME_ADDED_CONVERSION_UNIQUE + ")";
                String selection = Conversions.COLUMN_NAME_ADDED_CONVERSION_UNIQUE + " IS NOT NULL";
                ArrayList<String> customConversions = dbMethods.selectStatement(true, projection, selection, null);

                // Only allow the user to add 5 conversions as it seems like a reasonable amount and
                // by using a constant it would be very easy to change if needed
                if (Integer.parseInt(customConversions.get(0)) == NUMBER_OF_CONVERSIONS_ALLOWED) {
                    Snackbar.make(view, getString(R.string.limit_reached), 2000).show();
                    return;
                }

                if (names.contains(name)) {
                    Snackbar.make(view, getString(R.string.duplicate_conversion), 2000).show();
                    return;
                }


                //If the statement is successful
                if (dbMethods.insertStatement(name, unit1, unit2, unitRate) != -1) {
                    Snackbar.make(view, getString(R.string.snackConvAdded), 2000).show();

                    //clears the input fields
                    editTextConversionName.setText("");
                    editTextNewUnit1.setText("");
                    editTextNewUnit2.setText("");
                    editTextNewRate.setText("");

                    //gets the fragment by its tag and refreshes it to repopulate based on the updated database
                    SelectionFragment fragmentSelect = (SelectionFragment)
                            getFragmentManager().findFragmentByTag(getString(R.string.tag_selection));

                    getFragmentManager().beginTransaction()
                            .detach(fragmentSelect)
                            .attach(fragmentSelect)
                            .commit();

                    //gets the fragment by its tag and refreshes it to repopulate based on the updated database
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

    /**
     * Checks to see if the inputs given are valid
     * @param name the given name
     * @param unit1 the first unit given
     * @param unit2 the second unit given
     * @param unitRate the given rate
     * @return a boolean stating whether the input is valid (passed all of the checks)
     */
    private boolean validInput(String name, String unit1, String unit2, String unitRate) {

        boolean isValid = true;

        //all fields contain something
        if (name.equals("") || unit1.equals("") || unit2.equals("") || unitRate.equals(""))
            isValid = false;

        // units do not have the same name
        if (unit1.equals(unit2))
            isValid = false;

        // the names only contain alphanumeric characters
        if (name.matches(getString(R.string.regex)) || unit1.matches(getString(R.string.regex)) || unit2.matches(getString(R.string.regex)))
            isValid = false;


        return isValid;

    }


}
