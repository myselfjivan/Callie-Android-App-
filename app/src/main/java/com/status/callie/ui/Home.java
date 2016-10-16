package com.status.callie.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.status.callie.Model.Status;
import com.status.callie.R;
import com.status.callie.accounts.AccountConstants;
import com.status.callie.ui.Fragments.StatusContacts;
import com.status.callie.ui.Fragments.StatusSearch;
import com.status.callie.ui.Fragments.StatusShow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jivan.ghadage on 9/6/2016.
 */
public class Home extends AppCompatActivity {
    Button setStatusButton;
    Button getSetStatusButton;
    EditText setStatusEditText;
    String textStatus;
    Status status;
    private SharedPreferences shared_pref_login;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        status = new Status(Home.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        shared_pref_login = this.getSharedPreferences(AccountConstants.SHARED_PREF_LOGIN, Context.MODE_PRIVATE);

//        setStatusButton = (Button) findViewById(R.id.set_status_button);
//        getSetStatusButton = (Button) findViewById(R.id.status_get);
//        setStatusEditText = (EditText) findViewById(R.id.set_status_edit_text);
//        setStatusButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                textStatus = setStatusEditText.getText().toString();
//                status.statusStore(textStatus, shared_pref_login.getString(AccountConstants.TOKEN, ""));
//
//            }
//        });
//
//        getSetStatusButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                status.getStatus(shared_pref_login.getString(AccountConstants.TOKEN, ""));
//            }
//        });


    }

    private Boolean exit = false;

    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new StatusShow(), "ONE");
        adapter.addFragment(new StatusSearch(), "TWO");
        adapter.addFragment(new StatusContacts(), "THREE");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
