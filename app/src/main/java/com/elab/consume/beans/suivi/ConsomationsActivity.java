package com.elab.consume.beans.suivi;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.elab.consume.R;
import com.elab.consume.checker.suivi.consomation.Consomation;
import com.elab.consume.checker.suivi.consomation.ConsomationManageableServiceBase;
import com.elab.consume.utils.adapters.ConsomationAdapter;
import com.elab.consume.utils.tools.CommonCriterias;
import com.elab.consume.utils.tools.Globals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by AYOUB on 26/02/2018.
 */

public class ConsomationsActivity{
    private Activity _activity;
    private TextView txtDate;
    private ListView listView;
    private Calendar cal;
    private Consomation consomation;
    private ConsomationAdapter consomationAdapter;
    List<Consomation> consomations;
    private ConsomationManageableServiceBase consommationService;
    private CommonCriterias criterias=new CommonCriterias();
    private View view;

    public View onCreate(Activity activity) {
        _activity=activity;
        LayoutInflater li = activity.getLayoutInflater();
        view=li.inflate(R.layout.activity_consomations,null);

        listView=(ListView)view.findViewById(R.id.addConsomation_listview);
        txtDate=(TextView)view.findViewById(R.id.addConsumation_date);
        cal=Calendar.getInstance();

        consommationService=new ConsomationManageableServiceBase(_activity);


        txtDate.setText(Globals.DISPLAY_DATE_FORMAT.format(cal.getTime()));
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalendarDialog();
            }
        });

        consomation=new Consomation();
        consomations= new ArrayList<Consomation>();
        Date date=new Date();
        fillConsomations(date);
        return view;
    }

    public void openCalendarDialog(){
        DatePickerDialog datePicker = new DatePickerDialog(_activity,dpickerListnner, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        datePicker.show();
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
        criterias.setDateDebut(date);
        criterias.setDateFin(date);
        criterias.setUser(Globals.CURRENT_USER);
        consomations=consommationService.readByCritireas(criterias);
        if(consomations==null)
            consomations=new ArrayList<Consomation>();
        consomationAdapter=new ConsomationAdapter(_activity,consomations);
        listView.setDivider(null);
        listView.setAdapter(consomationAdapter);
    }

    public void deleteConsomation(int position){
        consommationService.delete(consomations.get(position).getId());
        consomations.remove(position);
        consomationAdapter.notifyDataSetChanged();
    }
}
