package uk.ac.stir.cs.yh.rj;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<String> unit1;
    private MutableLiveData<String> unit2;
    private MutableLiveData<Double> formula;

    public LiveData<String> getUnit1() {
        if (unit1 == null){
            unit1 = new MutableLiveData<>();

        }

        return unit1;
    }

    public LiveData<String> getUnit2() {
        if (unit2 == null){
            unit2 = new MutableLiveData<>();

        }

        return unit2;
    }

    public LiveData<Double> getFormula() {
        if (formula == null){
            formula = new MutableLiveData<>();

        }

        return formula;
    }


    public void loadUnit1(String unit1){

        this.unit1.setValue(unit1);

    }
    public void loadUnit2(String unit2){

        this.unit2.setValue(unit2);

    }

    public void loadFormula(double formula){

        this.formula.setValue(formula);

    }

}
