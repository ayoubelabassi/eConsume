package com.elab.consume.beans.suivi;

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

public class ConsomationsActivity extends Activity{
    private TextView txtDate;
    private ImageButton btnChooseImg;
    private ListView listView;
    private Button btnCalendar;
    private Calendar cal;
    private Consomation consomation;
    private ConsomationAdapter consomationAdapter;
    List<Consomation> consomations;
    private ConsomationManageableServiceBase consommationService;
    private CommonCriterias criterias=new CommonCriterias();
    static final int DIALOG_ID=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consomations);
        listView=(ListView)findViewById(R.id.addConsomation_listview);
        txtDate=(TextView)findViewById(R.id.addConsumation_date);
        btnChooseImg=(ImageButton)findViewById(R.id.addConsumation_Choosedate);
        cal=Calendar.getInstance();
        consommationService=new ConsomationManageableServiceBase(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        txtDate.setText(Globals.DISPLAY_DATE_FORMAT.format(cal.getTime()));
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DIALOG_ID);
            }
        });
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
        criterias.setDateDebut(date);
        criterias.setDateFin(date);
        criterias.setUser(Globals.CURRENT_USER);
        //DBHandler db=new DBHandler(this);
        consomations=consommationService.readByCritireas(criterias);
        if(consomations==null)
            consomations=new ArrayList<Consomation>();
        consomationAdapter=new ConsomationAdapter(this,consomations);
        listView.setDivider(null);
        listView.setAdapter(consomationAdapter);
    }

    public void deleteConsomation(int position){
        consommationService.delete(consomations.get(position).getId());
        consomations.remove(position);
        consomationAdapter.notifyDataSetChanged();
    }
}
