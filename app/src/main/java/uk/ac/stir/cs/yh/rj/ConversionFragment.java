package uk.ac.stir.cs.yh.rj;

import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;

/**
 * Fragment that handles the conversions
 */
public class ConversionFragment extends Fragment {

    private double formula;
    // to format the output for human consumption
    private DecimalFormat df = new DecimalFormat("###.#########");
    private Snackbar snackCharLimit;
    private EditText editTextUnit1;
    private EditText editTextUnit2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Assign a tag to the fragment so I can retrieve it
        this.getFragmentManager().beginTransaction().add(this, getString(R.string.tag_conversion));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.unit_conversion, container, false);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onStart();

        //If more snackbars were added this would allow easy altering of time shown
        int LENGTH_OF_SNACK = 2000;
        snackCharLimit = Snackbar.make(view, getString(R.string.snackCharLimit), LENGTH_OF_SNACK);


        //listens for changes to the unit to convert from
        SharedViewModel model = new ViewModelProvider(this.getActivity(), new ViewModelProvider.NewInstanceFactory()).get(SharedViewModel.class);
        model.getUnit1().observe(this, unit1 -> {

            TextView textUnit1 = view.findViewById(R.id.textViewUnitToConvertFrom);
            textUnit1.setText(unit1);

        });

        //listens for changes to the unit to convert to
        model.getUnit2().observe(this, unit2 -> {
            TextView textUnit2 = view.findViewById(R.id.textViewUnitToConvertTo);
            textUnit2.setText(unit2);
        });

        //listens for changes to the conversion rate
        model.getFormula().observe(this, formula -> {
            this.formula = formula;
            convert(true);
        });

        editTextUnit1 = view.findViewById(R.id.editTextUnit1);
        editTextUnit2 = view.findViewById(R.id.editTextUnit2);

        // Each button appends a character(Except the del and clear button) to the input field,
        // performs haptic feedback and recalculates the output

        Button buttonNo0 = view.findViewById(R.id.buttonNo0);
        buttonNo0.setOnClickListener((View v) -> {
            editTextUnit1.append("0");
            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            convert(false);
        });

        Button buttonNo1 = view.findViewById(R.id.buttonNo1);
        buttonNo1.setOnClickListener((View v) -> {
            editTextUnit1.append("1");
            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            convert(false);
        });

        Button buttonNo2 = view.findViewById(R.id.buttonNo2);
        buttonNo2.setOnClickListener((View v) -> {
            editTextUnit1.append("2");
            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            convert(false);
        });

        Button buttonNo3 = view.findViewById(R.id.buttonNo3);
        buttonNo3.setOnClickListener((View v) -> {
            editTextUnit1.append("3");
            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            convert(false);
        });

        Button buttonNo4 = view.findViewById(R.id.buttonNo4);
        buttonNo4.setOnClickListener((View v) -> {
            editTextUnit1.append("4");
            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            convert(false);
        });

        Button buttonNo5 = view.findViewById(R.id.buttonNo5);
        buttonNo5.setOnClickListener((View v) -> {
            editTextUnit1.append("5");
            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            convert(false);
        });

        Button buttonNo6 = view.findViewById(R.id.buttonNo6);
        buttonNo6.setOnClickListener((View v) -> {
            editTextUnit1.append("6");
            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            convert(false);
        });

        Button buttonNo7 = view.findViewById(R.id.buttonNo7);
        buttonNo7.setOnClickListener((View v) -> {
            editTextUnit1.append("7");
            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            convert(false);
        });

        Button buttonNo8 = view.findViewById(R.id.buttonNo8);
        buttonNo8.setOnClickListener((View v) -> {
            editTextUnit1.append("8");
            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            convert(false);
        });

        Button buttonNo9 = view.findViewById(R.id.buttonNo9);
        buttonNo9.setOnClickListener((View v) -> {
            editTextUnit1.append("9");
            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            convert(false);
        });

        Button buttonDecimal = view.findViewById(R.id.buttonDecimal);
        buttonDecimal.setOnClickListener((View v) -> {
            editTextUnit1.append(".");
            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            convert(false);
        });

        Button buttonDel = view.findViewById(R.id.buttonDel);
        buttonDel.setOnClickListener((View v) -> {
            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            if (editTextUnit1.length() != 0)
                editTextUnit1.getText().delete(editTextUnit1.length() - 1, editTextUnit1.length());
            convert(false);

        });

        Button buttonClear = view.findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener((View v) -> {
            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            editTextUnit1.setText("");
            editTextUnit2.setText("");

        });

    }

    /**
     * Does the conversion and populates the output editText with the result
     * @param gettingFormula this is used to stop the snackbar from showing up when the input is not being changed
     */
    private void convert(boolean gettingFormula) {

        //todo is this needed if the edittext only allows 10 charasters
        if (editTextUnit1.length() == 10 && !gettingFormula && !snackCharLimit.isShown()) {
            snackCharLimit.show();
        }

        if (editTextUnit1.length() != 0) {
            editTextUnit2.setText(df.format(Double.parseDouble(editTextUnit1.getText().toString()) * formula));
        } else
            editTextUnit2.setText("");

    }
}