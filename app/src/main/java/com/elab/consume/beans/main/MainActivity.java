package com.elab.consume.beans.main;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.elab.consume.R;
import com.elab.consume.beans.expence.DailyExpences;
import com.elab.consume.beans.expence.ExpenceEditor;
import com.elab.consume.beans.expence.MonthlyExpences;
import com.elab.consume.tools.Globals;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * Layout components
     */
    private FloatingActionButton fab;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private FrameLayout mainFrame;

    private TextView txtProfileName;
    private CircleImageView imgProfile;

    /**
     * Data classes
     */
    public DailyExpences dailyExpences;
    public MonthlyExpences monthlyExpences;

    private float x1,x2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         fab= (FloatingActionButton) findViewById(R.id.fab);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        mainFrame=(FrameLayout) findViewById(R.id.mainFrameLayout);

        View navView= LayoutInflater.from(this).inflate(R.layout.nav_header_main,null);
        txtProfileName=(TextView)navView.findViewById(R.id.profile_name);
        imgProfile=(CircleImageView)navView.findViewById(R.id.profile_image);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //fab.set
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExpenceEditor add_consomation=new ExpenceEditor();
                add_consomation.setExpence(null);
                add_consomation.setOperation(Globals.ADD_OP);
                add_consomation.setContext(MainActivity.this);
                add_consomation.show(getFragmentManager(),"Add Expence");
            }
        });
        navigationView.setNavigationItemSelectedListener(this);
        txtProfileName.setText(Globals.CURRENT_USER.getFirst_name()+" "+Globals.CURRENT_USER.getLast_name());
        LoadDailyExpences();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_daily_expence) {
            LoadDailyExpences();
        } else if (id == R.id.nav_monthly_expence) {
            loadMonthlyExpences();
        } else if (id == R.id.nav_daily_income) {

        } else if (id == R.id.nav_monthly_income) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void LoadDailyExpences(){
        monthlyExpences=null;
        mainFrame.removeAllViews();
        dailyExpences = new DailyExpences();
        mainFrame.addView(dailyExpences.onCreate(this));

    }
    public void loadMonthlyExpences(){
        dailyExpences=null;
        mainFrame.removeAllViews();
        monthlyExpences = new MonthlyExpences();
        mainFrame.addView(monthlyExpences.onCreate(this));
    }
}
