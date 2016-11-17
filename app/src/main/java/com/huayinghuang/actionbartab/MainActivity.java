package com.huayinghuang.actionbartab;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FirstFragment.OnFragmentInteractionListener,
                                                               SecondFragment.OnFragmentInteractionListener,
                                                               ThirdFragment.OnFragmentInteractionListener,
                                                               NavigationView.OnNavigationItemSelectedListener {
    private Toolbar mToolbar = null;
    private TabLayout mTabLayout = null;
    private ViewPager mViewPager = null;
    private NavigationView mNavView = null;
    private DrawerLayout mDrawerLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),this);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setupDrawerLayout();

        mNavView = (NavigationView)findViewById(R.id.navView);
        mNavView.setNavigationItemSelectedListener(this);

        mViewPager = (ViewPager)findViewById(R.id.viewpager);
        setupViewPager(adapter);

        mTabLayout = (TabLayout)findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        //set custom tab view
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            tab.setCustomView(adapter.getCustomTabView(i));
        }

    }

    private void setupDrawerLayout() {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                                                                                mToolbar, R.string.openDrawer,
                                                                                R.string.closeDrawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void setupViewPager(ViewPagerAdapter viewPagerAdapter) {
        viewPagerAdapter.addFragment(FirstFragment.newInstance(1));
        viewPagerAdapter.addFragment(new SecondFragment());
        viewPagerAdapter.addFragment(new ThirdFragment());
        mViewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        item.setChecked(true);
        mDrawerLayout.closeDrawers();
        Toast.makeText(this, item.getTitle()+" pressed", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            mDrawerLayout.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<android.support.v4.app.Fragment> mFragmentList = new ArrayList<>();

        private int[] tabIconsId = {R.drawable.ic_action_good,
                R.drawable.ic_action_picture,
                R.drawable.ic_action_favorite};
        private Context mContext;
        private String[] mTabTitles = new String[] {"ONE", "TWO", "THREE"};

        public ViewPagerAdapter(android.support.v4.app.FragmentManager manager, Context context) {
            super(manager);
            this.mContext = context;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return null;
        }

        public void addFragment(android.support.v4.app.Fragment fragment) {
            mFragmentList.add(fragment);
        }

        public View getCustomTabView(int position){
            View view = LayoutInflater.from(mContext).inflate(R.layout.tab_layout, null);
            TextView tv= (TextView) view.findViewById(R.id.tabTitle);
            tv.setText(mTabTitles[position]);
            ImageView img = (ImageView) view.findViewById(R.id.tabIcon);
            img.setImageResource(tabIconsId[position]);
            return view;
        }
    }
}
