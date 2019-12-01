package uk.ac.stir.cs.yh.rj;

import uk.ac.stir.cs.yh.rj.db.ConversionDbHelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    ConversionDbHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        System.out.println(getResources().getString(R.string.cup_ml));

        Button buttonRotate = findViewById(R.id.buttonRotate);

        buttonRotate.setOnClickListener(v -> {

            if (this.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT || this.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            else
                this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.pagerAdapterConvert);
        final ConversionPagerAdapter adapter = new ConversionPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void
            onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void
            onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void
            onTabReselected(TabLayout.Tab tab) {
            }
        });

        //Checks to ensure the right layout is being used
        if (findViewById(R.id.pagerAdapterConvert) != null) {


            // If being restored from a previous state do nothing
            // TODO CHECK THIS
            if (savedInstanceState != null) {
                return;
            }

            dbHelper = new ConversionDbHelper(this);
            db = dbHelper.getWritableDatabase();

            // Creating a new fragment
            SelectionFragment selectionFragment = new SelectionFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            selectionFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the fragment frame in the main_activity layout
            getSupportFragmentManager().beginTransaction().add(R.id.pagerAdapterConvert, selectionFragment).commit();
        }
        //TODO Handle this
        else {

        }

    }

}
