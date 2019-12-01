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

import uk.ac.stir.cs.yh.rj.db.ConversionDbMethods;


public class NewConversionFragment extends Fragment {


    private ConversionDbMethods dbMethods;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getFragmentManager().beginTransaction().add(this, "new_conversion_fragment");
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

            //todo don't allow unit1 and unit2 to have the same name

            //todo check conversion name is not Time, Volume etc and limit the amount of new conversions
            String name = editTextConversionName.getText().toString();
            String unit1 = editTextNewUnit1.getText().toString();
            String unit2 = editTextNewUnit2.getText().toString();
            String unitRate = editTextNewRate.getText().toString();


            //todo make more robust and move to COnversionDbMethods
            if (!unit1.equals("") || !unit2.equals("") || !unitRate.equals("")) {

                if (dbMethods.insertStatement(name, unit1, unit2, unitRate) != -1) {
                    Snackbar.make(view, getString(R.string.snackConvAdded), 1000).show();

                    SelectionFragment fragmentSelect = (SelectionFragment)
                            getFragmentManager().findFragmentByTag("selection_fragment");

                    getFragmentManager().beginTransaction()
                            .detach(fragmentSelect)
                            .attach(fragmentSelect)
                            .commit();

                    RemoveConversionFragment fragmentRemove = (RemoveConversionFragment)
                            getFragmentManager().findFragmentByTag("remove_conversion_fragment");

                    getFragmentManager().beginTransaction()
                            .detach(fragmentRemove)
                            .attach(fragmentRemove)
                            .commit();

                }
                else {
                    Snackbar.make(view, "Conversion not Added", 1000).show();
                }

            }

        }));
    }


}
