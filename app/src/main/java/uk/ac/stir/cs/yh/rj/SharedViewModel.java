package uk.ac.stir.cs.yh.rj;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

class SharedViewModel extends ViewModel {
    private MutableLiveData<String> unit1;
    private MutableLiveData<String> unit2;
    private MutableLiveData<Double> formula;
    private int unit1Pos;
    private int unit2Pos;
    private int category;

    public SharedViewModel() {

        unit1Pos = -1;
        unit2Pos = -1;
    }

    public LiveData<String> getUnit1() {
        if (unit1 == null) {
            unit1 = new MutableLiveData<>();

        }

        return unit1;
    }

    public LiveData<String> getUnit2() {
        if (unit2 == null) {
            unit2 = new MutableLiveData<>();

        }

        return unit2;
    }

    public LiveData<Double> getFormula() {
        if (formula == null) {
            formula = new MutableLiveData<>();

        }

        return formula;
    }


    public void loadUnit1(String unit1) {

        this.unit1.setValue(unit1);

    }

    public void loadUnit2(String unit2) {

        this.unit2.setValue(unit2);

    }

    public void loadFormula(double formula) {

        this.formula.setValue(formula);

    }

    public int getUnit1Pos() {

        return unit1Pos;
    }

    public void setUnit1Pos(int unit1Pos) {
        this.unit1Pos = unit1Pos;
    }

    public int getUnit2Pos() {
        return unit2Pos;
    }

    public void setUnit2Pos(int unit2Pos) {

        this.unit2Pos = unit2Pos;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}
