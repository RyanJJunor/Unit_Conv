package uk.ac.stir.cs.yh.rj;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ConversionPagerAdapter extends FragmentStatePagerAdapter {

    private final int numOfTabs;

    public ConversionPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.numOfTabs = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                System.out.println(position);
                return new SelectionFragment();
            case 1:
                System.out.println(position);
                return new ConversionFragment();
            case 2:
                System.out.println(position);
                return new NewConversionFragment();
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
