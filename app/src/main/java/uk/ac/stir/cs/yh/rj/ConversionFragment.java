package uk.ac.stir.cs.yh.rj;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

public class ConversionFragment extends Fragment {

    //todo change name
    private ArrayList<String> units;
    private double formula;
    private EditText editTextUnit1;
    private EditText editTextUnit2;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        SharedViewModel model = new ViewModelProvider(this.getActivity(), new ViewModelProvider.NewInstanceFactory()).get(SharedViewModel.class);
        units = model.getSelected();
        formula = model.getFormula();

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
    public void onStart() {
        super.onStart();

        TextView textUnit1 = getView().findViewById(R.id.textView10);
        textUnit1.setText(units.get(0));
        editTextUnit1 = getView().findViewById(R.id.editTextUnit1);


        TextView textUnit2 = getView().findViewById(R.id.textView11);
        textUnit2.setText(units.get(1));
        editTextUnit2 = getView().findViewById(R.id.editTextUnit2);

        Button buttonBack = getView().findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener((View v) -> getFragmentManager().popBackStackImmediate());

        Button buttonNo0 = getView().findViewById(R.id.buttonNo0);
        buttonNo0.setOnClickListener((View v) -> {
            editTextUnit1.append("0");
            convert();
        });

        Button buttonNo1 = getView().findViewById(R.id.buttonNo1);
        buttonNo1.setOnClickListener((View v) -> {
            editTextUnit1.append("1");
            convert();
        });

        Button buttonNo2 = getView().findViewById(R.id.buttonNo2);
        buttonNo2.setOnClickListener((View v) -> {
            editTextUnit1.append("2");
            convert();
        });

        Button buttonNo3 = getView().findViewById(R.id.buttonNo3);
        buttonNo3.setOnClickListener((View v) -> {
            editTextUnit1.append("3");
            convert();
        });

        Button buttonNo4 = getView().findViewById(R.id.buttonNo4);
        buttonNo4.setOnClickListener((View v) -> {
            editTextUnit1.append("4");
            convert();
        });

        Button buttonNo5 = getView().findViewById(R.id.buttonNo5);
        buttonNo5.setOnClickListener((View v) -> {
            editTextUnit1.append("5");
            convert();
        });

        Button buttonNo6 = getView().findViewById(R.id.buttonNo6);
        buttonNo6.setOnClickListener((View v) -> {
            editTextUnit1.append("6");
            convert();
        });

        Button buttonNo7 = getView().findViewById(R.id.buttonNo7);
        buttonNo7.setOnClickListener((View v) -> {
            editTextUnit1.append("7");
            convert();
        });

        Button buttonNo8 = getView().findViewById(R.id.buttonNo8);
        buttonNo8.setOnClickListener((View v) -> {
            editTextUnit1.append("8");
            convert();
        });

        Button buttonNo9 = getView().findViewById(R.id.buttonNo9);
        buttonNo9.setOnClickListener((View v) -> {
            editTextUnit1.append("9");
            convert();
        });

        Button buttonDecimal = getView().findViewById(R.id.buttonDecimal);
        buttonDecimal.setOnClickListener((View v) -> {
            editTextUnit1.append(".");
            convert();
        });

        Button buttonDel = getView().findViewById(R.id.buttonDel);
        buttonDel.setOnClickListener((View v) -> {

            if (editTextUnit1.length()!= 0)
            editTextUnit1.getText().delete(editTextUnit1.length()-1, editTextUnit1.length());
            convert();

        });


    }


    private void convert(){

        editTextUnit2.setText(String.valueOf(Double.parseDouble(editTextUnit1.getText().toString()) * formula));
    }
}