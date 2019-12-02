package uk.ac.stir.cs.yh.rj;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

class ConversionPagerAdapter extends FragmentStatePagerAdapter {

    private final int numOfTabs;

    ConversionPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.numOfTabs = behavior;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                return new SelectionFragment();
            case 1:
                return new ConversionFragment();
            case 2:
                return new NewConversionFragment();
            case 3:
                return new RemoveConversionFragment();
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
