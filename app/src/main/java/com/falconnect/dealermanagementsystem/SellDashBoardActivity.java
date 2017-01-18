package com.falconnect.dealermanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.falconnect.dealermanagementsystem.Adapter.CustomList;
import com.falconnect.dealermanagementsystem.Adapter.PagerAdapter;
import com.falconnect.dealermanagementsystem.FontAdapter.RoundImageTransform;
import com.falconnect.dealermanagementsystem.Fragment.SellFirstPageFragment;
import com.falconnect.dealermanagementsystem.NavigationDrawer.BuyPageNavigation;
import com.falconnect.dealermanagementsystem.SharedPreference.SessionManager;
import com.navdrawer.SimpleSideDrawer;

import java.util.ArrayList;
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

    ImageView inventry_mnav;
    private SimpleSideDrawer mNav_inven;
    SessionManager session_myinven;
    ImageView imageView_myinven;
    TextView profile_name_myinven;
    TextView profile_address_myinven;
    String saved_name_myinven, saved_address_myinven;
    BuyPageNavigation inven_buypagenavigation;

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


        if (savedInstanceState != null) {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab")); //set the tab as per the saved state
        }
        // Intialise ViewPager
        this.intialiseViewPager();

        mNav_inven = new SimpleSideDrawer(this);
        mNav_inven.setLeftBehindContentView(R.layout.activity_behind_left_simple);

        imageView_myinven = (ImageView) mNav_inven.findViewById(R.id.profile_avatar);
        profile_name_myinven = (TextView) mNav_inven.findViewById(R.id.profile_name);
        profile_address_myinven = (TextView) mNav_inven.findViewById(R.id.profile_address);

        session_myinven = new SessionManager(SellDashBoardActivity.this);
        HashMap<String, String> user = session_myinven.getUserDetails();
        saved_name_myinven = user.get("dealer_name");
        saved_address_myinven = user.get("dealer_address");
        profile_name_myinven.setText(saved_name_myinven);
        if (user.get("dealer_img").isEmpty()) {
            Glide.with(getApplicationContext())
                    .load(R.drawable.default_avatar)
                    .transform(new RoundImageTransform(SellDashBoardActivity.this))
                    .into(imageView_myinven);
        } else {
            Glide.with(getApplicationContext())
                    .load(user.get("dealer_img"))
                    .transform(new RoundImageTransform(SellDashBoardActivity.this))
                    .into(imageView_myinven);
        }
        profile_address_myinven.setText(saved_address_myinven);
        inventry_mnav = (ImageView) findViewById(R.id.inventry_mnav);
        inventry_mnav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNav_inven.toggleLeftDrawer();
            }
        });

        //NAVIGATION DRAWER LIST VIEW
        inven_buypagenavigation = new BuyPageNavigation();
        CustomList adapter = new CustomList(SellDashBoardActivity.this, inven_buypagenavigation.web, inven_buypagenavigation.imageId);
        ListView list = (ListView) findViewById(R.id.nav_list_view);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (inven_buypagenavigation.web[position] == "Buy") {
                    Intent intent = new Intent(SellDashBoardActivity.this, DashBoard.class);
                    startActivity(intent);
                    mNav_inven.closeLeftSide();
                    SellDashBoardActivity.this.finish();
                    Toast.makeText(SellDashBoardActivity.this, inven_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (inven_buypagenavigation.web[position] == "Sell") {
                    mNav_inven.closeLeftSide();
                    Toast.makeText(SellDashBoardActivity.this, inven_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (inven_buypagenavigation.web[position] == "Manage") {
                    mNav_inven.closeLeftSide();
                    Toast.makeText(SellDashBoardActivity.this, inven_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (inven_buypagenavigation.web[position] == "Communication") {
                    mNav_inven.closeLeftSide();
                    Toast.makeText(SellDashBoardActivity.this, inven_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (inven_buypagenavigation.web[position] == "Reports") {
                    mNav_inven.closeLeftSide();
                    Toast.makeText(SellDashBoardActivity.this, inven_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (inven_buypagenavigation.web[position] == "Logout") {
                    session_myinven.logoutUser();
                    mNav_inven.closeLeftSide();
                    SellDashBoardActivity.this.finish();
                } else {
                    //Toast.makeText(DashBoard.this, web[position], Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("tab", mTabHost.getCurrentTabTag()); //save the tab selected
        super.onSaveInstanceState(outState);
    }

    private void intialiseViewPager() {

        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this, SellFirstPageFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, SellFirstPageFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, SellFirstPageFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, SellFirstPageFragment.class.getName()));
        this.mPagerAdapter = new PagerAdapter(super.getSupportFragmentManager(), fragments);
        //
        this.mViewPager = (ViewPager) super.findViewById(R.id.viewpager);
        this.mViewPager.setAdapter(this.mPagerAdapter);
        this.mViewPager.setOnPageChangeListener(this);
    }

    private void initialiseTabHost(Bundle args) {
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();
        TabInfo tabInfo = null;


        String[] tabname = {
                "ALL",
                "PARK AND SELL",
                "OWN",
                "HAS VIEWS"
        };

        for (int j = 0; j < tabname.length; j++) {
            SellDashBoardActivity.AddTab(this, this.mTabHost,
                    this.mTabHost.newTabSpec(tabname[j].toString()).setIndicator(tabname[j].toString()),
                    (tabInfo = new TabInfo("Tab", SellFirstPageFragment.class, args)));
            this.mapTabInfo.put(tabInfo.tag, tabInfo);
        }

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

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
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