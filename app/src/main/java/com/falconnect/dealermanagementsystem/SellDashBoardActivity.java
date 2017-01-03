package com.falconnect.dealermanagementsystem;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.TabHost;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.falconnect.dealermanagementsystem.Adapter.PagerAdapter;
import com.falconnect.dealermanagementsystem.Fragment.SellFirstPageFragment;
import com.falconnect.dealermanagementsystem.Fragment.SellFourthPageFragment;
import com.falconnect.dealermanagementsystem.Fragment.SellSecondPageFragment;
import com.falconnect.dealermanagementsystem.Fragment.SellThirdPageFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class SellDashBoardActivity extends FragmentActivity implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {

    private boolean mVisible;

    private TabHost mTabHost;
    private ViewPager mViewPager;
    private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, SellDashBoardActivity.TabInfo>();
    private PagerAdapter mPagerAdapter;

    private class TabInfo {
        private String tag;
        private Class<?> clss;
        private Bundle args;
        private Fragment fragment;
        TabInfo(String tag, Class<?> clazz, Bundle args) {
            this.tag = tag;
            this.clss = clazz;
            this.args = args;
        }

    }

    class TabFactory implements TabHost.TabContentFactory {

        private final Context mContext;

        public TabFactory(Context context) {
            mContext = context;
        }

        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setMinimumWidth(0);
            v.setMinimumHeight(0);
            return v;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sell_dash_board);

        this.initialiseTabHost(savedInstanceState);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mVisible = true;

        /*if (mVisible) {
            android.support.v7.app.ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
            mVisible = false;
        } else {

        }*/

        if (savedInstanceState != null) {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab")); //set the tab as per the saved state
        }
        // Intialise ViewPager
        this.intialiseViewPager();

    }
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("tab", mTabHost.getCurrentTabTag()); //save the tab selected
        super.onSaveInstanceState(outState);
    }

    private void intialiseViewPager() {

        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this, SellFirstPageFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, SellSecondPageFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, SellThirdPageFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, SellFourthPageFragment.class.getName()));
        this.mPagerAdapter  = new PagerAdapter(super.getSupportFragmentManager(), fragments);
        //
        this.mViewPager = (ViewPager)super.findViewById(R.id.viewpager);
        this.mViewPager.setAdapter(this.mPagerAdapter);
        this.mViewPager.setOnPageChangeListener(this);
    }

    private void initialiseTabHost(Bundle args) {
        mTabHost = (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup();
        TabInfo tabInfo = null;
        SellDashBoardActivity.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("ALL").setIndicator("ALL"), ( tabInfo = new TabInfo("Tab1", SellFirstPageFragment.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        SellDashBoardActivity.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("PARK AND SELL").setIndicator("PARK AND SELL"), ( tabInfo = new TabInfo("Tab2", SellSecondPageFragment.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        SellDashBoardActivity.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("OWNS").setIndicator("OWNS"), ( tabInfo = new TabInfo("Tab3", SellThirdPageFragment.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        SellDashBoardActivity.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("HAS VIEWS").setIndicator("HAS VIEWS"), ( tabInfo = new TabInfo("Tab3", SellFourthPageFragment.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);

        mTabHost.setOnTabChangedListener(this);
    }
    private static void AddTab(SellDashBoardActivity activity, TabHost tabHost, TabHost.TabSpec tabSpec, TabInfo tabInfo) {
        tabSpec.setContent(activity.new TabFactory(activity));
        tabHost.addTab(tabSpec);
    }

    public void onTabChanged(String tag) {
        int pos = this.mTabHost.getCurrentTab();
        this.mViewPager.setCurrentItem(pos);
    }

    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {
        // TODO Auto-generated method stub

    }

    public void onPageSelected(int position) {
        // TODO Auto-generated method stub
        this.mTabHost.setCurrentTab(position);
    }

    public void onPageScrollStateChanged(int state) {
        // TODO Auto-generated method stub

    }
}