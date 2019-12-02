package uk.ac.stir.cs.yh.rj;

import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import uk.ac.stir.cs.yh.rj.db.ConversionDbHelper;

/**
 * The main activity which houses the fragments
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Button buttonRotate = findViewById(R.id.buttonRotate);


        //Rotate button as the accelerometer on my phone does not work
        buttonRotate.setOnClickListener(v -> {

            v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

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

        // This lets all of the fragments be stored in memory to save them from constantly restarting
        // it is 3 as I have four fragments and as one is always on screen, only 3 will be offscreen at one time
        viewPager.setOffscreenPageLimit(3);


        final ConversionPagerAdapter adapter = new ConversionPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
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


    }

}
