package com.ayoubtadakker.tadakker.beans.suivi;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.GridView;

import com.ayoubtadakker.tadakker.R;
import com.ayoubtadakker.tadakker.checker.suivi.Consomation;
import com.ayoubtadakker.tadakker.utils.adapters.ConsomationAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by AYOUB on 26/02/2018.
 */

public class AddConsomationActivity extends Activity {
    private GridView gridView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_consomation);
        gridView=(GridView)findViewById(R.id.addConsumation_grid);
    }

    public void addConsomationEvent(View view) {
        List<Consomation> list= new ArrayList<Consomation>();
        Date date=new Date();
        list.add(new Consomation(1,"Danone","rah ghir danone",2,2.3,date,1));
        list.add(new Consomation(2,"KHOBZ","",1,1.2,date,1));
        list.add(new Consomation(3,"BATATA","",8,3.5,date,1));
        list.add(new Consomation(4,"BASTA","",5,5,date,1));
        list.add(new Consomation(5,"RICH","",3,10,date,1));

        gridView.setAdapter(new ConsomationAdapter(this,list));


    }
}
