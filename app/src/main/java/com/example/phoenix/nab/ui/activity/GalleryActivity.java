package com.example.phoenix.nab.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.phoenix.nab.R;
import com.example.phoenix.nab.ui.adapter.MainPagerAdapter;
import com.example.phoenix.nab.ui.fragment.GalleryFragment;

import java.util.HashMap;
import java.util.Map;

public class GalleryActivity extends AppCompatActivity {

    public static final String EXTRA_TAB = "EXTRA_TAB";
    public static final String EXTRA_FILE_MAP = "EXTRA_FILE_MAP";

    private MainPagerAdapter mainPagerAdapter;

    private ViewPager viewPager;
    private TabLayout tabs;

    private int countTab = 3;
    private HashMap<String, String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        initialize();
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            map = (HashMap) bundle.getSerializable(EXTRA_FILE_MAP);
        }
    }

    private void initialize() {
        initView();
        initData();
        initViewPager();
        initGUI();
        bindEvent();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        viewPager = (ViewPager) findViewById(R.id.container);

        tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    private void initViewPager() {
        if (viewPager != null) {
            setupViewPager();
        }
        viewPager.setOffscreenPageLimit(countTab);
        tabs.setupWithViewPager(viewPager);
        setupTabTitle();
    }

    private void bindEvent() {

    }

    private void showPopup(View v) {

    }

    private void setupTabTitle() {

    }

    private void setupViewPager() {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            if (key.contains("._")) {
                continue;
            }

            String value = entry.getValue();

            Bundle bundle = new Bundle();
            bundle.putString(GalleryFragment.FILE, value);
            Fragment fragment = Fragment.instantiate(this, GalleryFragment.class.getName(), bundle);
            mainPagerAdapter.addFragment(fragment);
        }
        viewPager.setAdapter(mainPagerAdapter);
    }

    private void initGUI() {

    }

}
