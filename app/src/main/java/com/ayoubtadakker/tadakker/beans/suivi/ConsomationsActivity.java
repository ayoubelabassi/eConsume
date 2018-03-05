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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.ayoubtadakker.tadakker.R;
import com.ayoubtadakker.tadakker.checker.suivi.consomation.Consomation;
import com.ayoubtadakker.tadakker.utils.adapters.ConsomationAdapter;
import com.ayoubtadakker.tadakker.utils.tools.Globals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by AYOUB on 26/02/2018.
 */

public class ConsomationsActivity extends Activity{
    private TextView txtDate;
    private ImageButton btnChooseImg;
    private ListView listView;
    private Button btnCalendar;
    private Calendar cal;
    private Consomation consomation;
    List<Consomation> consomations;

    static final int DIALOG_ID=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consomations);
        listView=(ListView)findViewById(R.id.addConsomation_listview);
        txtDate=(TextView)findViewById(R.id.addConsumation_date);
        btnChooseImg=(ImageButton)findViewById(R.id.addConsumation_Choosedate);

        cal=Calendar.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        txtDate.setText(Globals.DISPLAY_DATE_FORMAT.format(cal.getTime()));
        btnChooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DIALOG_ID);
            }
        });

        consomation=new Consomation();
        consomations= new ArrayList<Consomation>();
        Date date=new Date();
        fillConsomations(date);
    }

    @Override
    protected Dialog onCreateDialog(int id){
        if(id==DIALOG_ID)
            return new DatePickerDialog(this,dpickerListnner,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerListnner=new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker datePicker, int y, int m, int d) {
            cal.set(y,m,d);
            txtDate.setText(Globals.DISPLAY_DATE_FORMAT.format(cal.getTime()));
            fillConsomations(cal.getTime());
        }
    };

    public void fillConsomations(Date date) {
        consomations.add(new Consomation(1,"Danone","rah ghir danone",2,2.3,date,1));
        consomations.add(new Consomation(2,"KHOBZ","",1,1.2,date,1));
        consomations.add(new Consomation(3,"BATATA","",8,3.5,date,1));
        consomations.add(new Consomation(4,"BASTA","",5,5,date,1));
        consomations.add(new Consomation(5,"RICH","",3,10,date,1));

        listView.setDivider(null);
        listView.setAdapter(new ConsomationAdapter(this,consomations));
    }
}
