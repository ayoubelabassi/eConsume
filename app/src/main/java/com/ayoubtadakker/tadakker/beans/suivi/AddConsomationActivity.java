package com.ayoubtadakker.tadakker.beans.suivi;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.ayoubtadakker.tadakker.R;
import com.ayoubtadakker.tadakker.checker.suivi.Consomation;
import com.ayoubtadakker.tadakker.utils.adapters.ConsomationAdapter;
import com.ayoubtadakker.tadakker.utils.tools.Globals;

import java.text.DecimalFormat;
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
        setContentView(R.layout.add_consomation);
        listView=(ListView)findViewById(R.id.addConsomation_listview);
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
        consomation=new Consomation();
        consomations= new ArrayList<Consomation>();
    }

    public void addConsomationEvent(View view) {

        Date date=new Date();
        consomations.add(new Consomation(1,"Danone","rah ghir danone",2,2.3,date,1));
        consomations.add(new Consomation(2,"KHOBZ","",1,1.2,date,1));
        consomations.add(new Consomation(3,"BATATA","",8,3.5,date,1));
        consomations.add(new Consomation(4,"BASTA","",5,5,date,1));
        consomations.add(new Consomation(5,"RICH","",3,10,date,1));

        listView.setAdapter(new ConsomationAdapter(this,consomations));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                consomation=consomations.get(position);
                updateForm();
            }
        });
    }

    private void updateForm(){
        if(consomation!=null){
            txtDate.setText(Globals.DATE_FORMAT.format(consomation.getDate()));
            txtDescription.setText(consomation.getDescription());
            txtName.setText(consomation.getName());
            txtPrice.setText(new DecimalFormat("$##.##").format(consomation.getPrice()));
            txtQTE.setText(consomation.getQte());
        }
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
