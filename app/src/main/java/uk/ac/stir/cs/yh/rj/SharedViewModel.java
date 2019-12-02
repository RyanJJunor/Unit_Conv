package uk.ac.stir.cs.yh.rj;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * This holds data independent of fragments, it is used to communicate between fragments and also
 * hold data to be used when recreating a fragment after configuration changes (Screen rotation)
 */
public class SharedViewModel extends ViewModel {
    // The unit to convert from and the unit to convert to
    private MutableLiveData<String> unit1;
    private MutableLiveData<String> unit2;
    // The formula for the conversion selected
    private MutableLiveData<Double> formula;
    // The positions of the units that were selected. To be used for recovering after configuration
    private int unit1Pos;
    private int unit2Pos;
    private int category;

    public SharedViewModel() {

        // This is to indicate that the selection fragment has not been created before
        unit1Pos = -1;
        unit2Pos = -1;
    }

    LiveData<String> getUnit1() {
        if (unit1 == null) {
            unit1 = new MutableLiveData<>();

        }

        return unit1;
    }

    LiveData<String> getUnit2() {
        if (unit2 == null) {
            unit2 = new MutableLiveData<>();

        }

        return unit2;
    }

    LiveData<Double> getFormula() {
        if (formula == null) {
            formula = new MutableLiveData<>();

        }

        return formula;
    }


    void setUnit1(String unit1) {

        this.unit1.setValue(unit1);

    }

    void setUnit2(String unit2) {

        this.unit2.setValue(unit2);

    }

    void setFormula(double formula) {

        this.formula.setValue(formula);

    }

    int getUnit1Pos() {

        return unit1Pos;
    }

    void setUnit1Pos(int unit1Pos) {
        this.unit1Pos = unit1Pos;
    }

    int getUnit2Pos() {
        return unit2Pos;
    }

    void setUnit2Pos(int unit2Pos) {

        this.unit2Pos = unit2Pos;
    }

    int getCategory() {
        return category;
    }

    void setCategory(int category) {
        this.category = category;
    }
}
