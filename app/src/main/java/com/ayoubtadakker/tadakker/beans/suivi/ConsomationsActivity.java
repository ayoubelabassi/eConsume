package com.ayoubtadakker.tadakker.beans.suivi;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.ayoubtadakker.tadakker.R;
import com.ayoubtadakker.tadakker.checker.suivi.consomation.Consomation;
import com.ayoubtadakker.tadakker.utils.adapters.ConsomationAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by AYOUB on 26/02/2018.
 */

public class ConsomationsActivity extends Activity{
    private EditText txtDate;
    private ListView listView;
    private Button btnCalendar;
    private Calendar cal;
    private Consomation consomation;
    List<Consomation> consomations;

    int year,month,day;
    static final int DIALOG_ID=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consomations);
        listView=(ListView)findViewById(R.id.addConsomation_listview);
        txtDate=(EditText)findViewById(R.id.addConsumation_date);

        cal=Calendar.getInstance();
        year=cal.get(Calendar.YEAR);
        month=cal.get(Calendar.MONTH);
        day=cal.get(Calendar.DAY_OF_MONTH);


        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DIALOG_ID);
                InputMethodManager imm = (InputMethodManager)getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(txtDate.getWindowToken(), 0);
            }
        });
        consomation=new Consomation();
        consomations= new ArrayList<Consomation>();
        fillConsomations();
    }

    public void fillConsomations() {

        Date date=new Date();
        consomations.add(new Consomation(1,"Danone","rah ghir danone",2,2.3,date,1));
        consomations.add(new Consomation(2,"KHOBZ","",1,1.2,date,1));
        consomations.add(new Consomation(3,"BATATA","",8,3.5,date,1));
        consomations.add(new Consomation(4,"BASTA","",5,5,date,1));
        consomations.add(new Consomation(5,"RICH","",3,10,date,1));

        listView.setDivider(null);
        listView.setAdapter(new ConsomationAdapter(this,consomations));
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
            fillConsomations();
        }
    };
}
