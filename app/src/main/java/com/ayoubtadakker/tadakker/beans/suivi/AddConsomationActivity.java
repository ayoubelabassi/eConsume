package com.ayoubtadakker.tadakker.beans.suivi;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.QuickContactBadge;

import com.ayoubtadakker.tadakker.R;
import com.ayoubtadakker.tadakker.checker.suivi.Consomation;
import com.ayoubtadakker.tadakker.utils.adapters.ConsomationAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by AYOUB on 26/02/2018.
 */

public class AddConsomationActivity extends Activity {
    private EditText txtName;
    private EditText txtDescription;
    private EditText txtPrice;
    private EditText txtQTE;
    private EditText txtDate;
    private GridView gridView;
    private Button btnCalendar;
    private Calendar cal;
    int year,month,day;
    static final int DIALOG_ID=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_consomation);
        gridView=(GridView)findViewById(R.id.addConsumation_grid);
        txtName=(EditText)findViewById(R.id.addConsumation_name);
        txtDate=(EditText)findViewById(R.id.addConsumation_date);
        txtDescription=(EditText)findViewById(R.id.addConsumation_description);
        txtQTE=(EditText)findViewById(R.id.addConsumation_qte);
        txtPrice=(EditText)findViewById(R.id.addConsumation_price);

        //btnCalendar=(Button)findViewById(R.id.addConsumation_btnCalendar);

        btnCalendar=(Button) findViewById(R.id.addConsumation_btnCalendar);
        cal=Calendar.getInstance();
        year=cal.get(Calendar.YEAR);
        month=cal.get(Calendar.MONTH);
        day=cal.get(Calendar.DAY_OF_MONTH);
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DIALOG_ID);
            }
        });
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

    @Override
    protected Dialog onCreateDialog(int id){
        if(id==DIALOG_ID)
            return new DatePickerDialog(this,dpickerLIstnner,year,month,day);
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerLIstnner=new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker datePicker, int y, int m, int d) {
            year=y;
            month=m;
            day=d;
            txtDate.setText(year+"-"+month+"-"+d);
        }
    };
}
