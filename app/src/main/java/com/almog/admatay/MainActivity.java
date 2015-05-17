package com.almog.admatay;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.almog.admatay.fragments.DataFragment;
import com.almog.admatay.fragments.DayListFragment;
import com.almog.admatay.fragments.NightListFragment;
import com.almog.admatay.tabs.SlidingTabLayout;
import com.almog.admatay.tabs.ViewPagerAdapter;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private ViewPager pager;
    private ViewPagerAdapter adapter;
    private SlidingTabLayout tabs;
    private String[] mTitles = null;
    private int mNumbOfTabs = 3;
    private Button makeBtn;

    public static EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        mTitles = getResources().getStringArray(R.array.titles);
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), mTitles, mNumbOfTabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assigning the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

        makeBtn = (Button) findViewById(R.id.makeBtn);
        makeBtn.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*switch (id){
            case R.id.action_bar:

                break;
            case R.id.about:
                AlertDialog.Builder about = new AlertDialog.Builder(this);
                TextView tv = new TextView(this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                tv.setLayoutParams(lp);
                break;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.makeBtn){
            if (DayListFragment.dayList.size() == 0 && NightListFragment.nightList.size() == 0){
                Toast.makeText(this, R.string.empty_lists, Toast.LENGTH_SHORT).show();
            }else {
                /*DataFragment df = new DataFragment();
                df.CalculateList(this, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataFragment df = new DataFragment();
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        df.sb.replace(0, df.sb.length(), input.getText().toString());
                        intent.putExtra(Intent.EXTRA_TEXT, (CharSequence) df.sb);
                        startActivity(Intent.createChooser(intent, getString(R.string.share_via))
                        );
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });*/
                Intent listActivity = new Intent(this, ListActivity.class);
                this.startActivity(listActivity);
            }
        }
    }
}
