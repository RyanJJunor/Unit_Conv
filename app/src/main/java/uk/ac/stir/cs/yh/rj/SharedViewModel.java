package uk.ac.stir.cs.yh.rj;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class SharedViewModel extends ViewModel {
    private ArrayList<String> selectedUnits = new ArrayList<>();
    private double formula;

    void select(ArrayList<String> selectedUnits, double formula) {
        //todo this or unique variable names?
        this.selectedUnits = selectedUnits;
        this.formula = formula;
    }

    ArrayList<String> getSelected() {
        return selectedUnits;
    }

    public double getFormula() {
        return formula;
    }
}
