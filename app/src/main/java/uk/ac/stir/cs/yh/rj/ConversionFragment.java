package uk.ac.stir.cs.yh.rj;

import android.os.Bundle;
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

import java.util.ArrayList;

public class ConversionFragment extends Fragment {

    SharedViewModel model;

    //todo change name

    double formula;

    private EditText editTextUnit1;
    private EditText editTextUnit2;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

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

        model = new ViewModelProvider(this.getActivity(), new ViewModelProvider.NewInstanceFactory()).get(SharedViewModel.class);
        model.getUnit1().observe(this, unit1 -> {

            TextView textUnit1 = view.findViewById(R.id.textView10);
            textUnit1.setText(unit1);

        });

        model.getUnit2().observe(this, unit2 -> {
            TextView textUnit2 = view.findViewById(R.id.textView11);
            textUnit2.setText(unit2);
        });

        model.getFormula().observe(this, formula -> {
            this.formula = formula;
            convert();
        });


        editTextUnit1 = view.findViewById(R.id.editTextUnit1);



        editTextUnit2 = view.findViewById(R.id.editTextUnit2);


        //todo fix appending?
        Button buttonNo0 = view.findViewById(R.id.buttonNo0);
        buttonNo0.setOnClickListener((View v) -> {
            editTextUnit1.append("0");
            convert();
        });

        Button buttonNo1 = view.findViewById(R.id.buttonNo1);
        buttonNo1.setOnClickListener((View v) -> {
            editTextUnit1.append("1");
            convert();
        });

        Button buttonNo2 = view.findViewById(R.id.buttonNo2);
        buttonNo2.setOnClickListener((View v) -> {
            editTextUnit1.append("2");
            convert();
        });

        Button buttonNo3 = view.findViewById(R.id.buttonNo3);
        buttonNo3.setOnClickListener((View v) -> {
            editTextUnit1.append("3");
            convert();
        });

        Button buttonNo4 = view.findViewById(R.id.buttonNo4);
        buttonNo4.setOnClickListener((View v) -> {
            editTextUnit1.append("4");
            convert();
        });

        Button buttonNo5 = view.findViewById(R.id.buttonNo5);
        buttonNo5.setOnClickListener((View v) -> {
            editTextUnit1.append("5");
            convert();
        });

        Button buttonNo6 = view.findViewById(R.id.buttonNo6);
        buttonNo6.setOnClickListener((View v) -> {
            editTextUnit1.append("6");
            convert();
        });

        Button buttonNo7 = view.findViewById(R.id.buttonNo7);
        buttonNo7.setOnClickListener((View v) -> {
            editTextUnit1.append("7");
            convert();
        });

        Button buttonNo8 = view.findViewById(R.id.buttonNo8);
        buttonNo8.setOnClickListener((View v) -> {
            editTextUnit1.append("8");
            convert();
        });

        Button buttonNo9 = view.findViewById(R.id.buttonNo9);
        buttonNo9.setOnClickListener((View v) -> {
            editTextUnit1.append("9");
            convert();
        });

        Button buttonDecimal = view.findViewById(R.id.buttonDecimal);
        buttonDecimal.setOnClickListener((View v) -> {
            editTextUnit1.append(".");
            convert();
        });

        Button buttonDel = view.findViewById(R.id.buttonDel);
        buttonDel.setOnClickListener((View v) -> {

            if (editTextUnit1.length()!= 0)
            editTextUnit1.getText().delete(editTextUnit1.length()-1, editTextUnit1.length());
            convert();

        });

        Button buttonClear = view.findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener((View v) -> {

            editTextUnit1.setText("");
            editTextUnit2.setText("");

        });


    }

    private void convert(){

        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{" +model.getFormula().getValue());
        if (editTextUnit1.length() != 0)
        editTextUnit2.setText(String.valueOf(Double.parseDouble(editTextUnit1.getText().toString()) * formula));
        else
            editTextUnit2.setText("");

    }
}