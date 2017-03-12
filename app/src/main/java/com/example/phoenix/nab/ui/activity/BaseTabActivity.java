package com.example.phoenix.nab.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.example.phoenix.nab.R;

public class BaseTabActivity extends BaseActivity {

    ViewPager viewPager;
    TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        bindView();
        init();
    }

    private void bindView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabs = (TabLayout) findViewById(R.id.tabs);
    }

    private void init() {
        initViewPager();
    }

    private void initViewPager() {
        if (viewPager != null) {
            setupViewPager();
        }
        viewPager.setOffscreenPageLimit(3);
        tabs.setupWithViewPager(viewPager);
        setupTabTitle();

    }

    private void setupTabTitle() {
//        View tab0 = View.inflate(getBaseContext(), R.layout.tab_other_title, null);
//        ((TextView) tab0.findViewById(R.id.txt_tab_title)).setText(getResources().getString(R.string.quark_P04_02_register_quark));
//        View tab1 = View.inflate(getBaseContext(), R.layout.tab_other_title, null);
//        ((TextView) tab1.findViewById(R.id.txt_tab_title)).setText(getResources().getString(R.string.quark_P04_02_joined_brand));
//        View tab2 = View.inflate(getBaseContext(), R.layout.tab_other_title, null);
//        ((TextView) tab2.findViewById(R.id.txt_tab_title)).setText(getResources().getString(R.string.quark_P04_02_my_register_quark));
//        tabs.getTabAt(0).setCustomView(tab0);
//        tabs.getTabAt(1).setCustomView(tab1);
//        tabs.getTabAt(2).setCustomView(tab2);

    }

    private void setupViewPager() {

    }
}
