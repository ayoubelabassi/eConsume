package com.elab.consume.beans.expence;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.elab.consume.beans.utils.MonthYearPickerDialog;
import com.elab.consume.checker.expence.Expence;
import com.elab.consume.checker.expence.ExpenceManageableServiceBase;
import com.elab.consume.tools.CommonCriterias;
import com.elab.consume.tools.Globals;

import com.elab.consume.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by AYOUB on 26/02/2018.
 */

public class DailyExpences {

    private Activity _activity;
    private TextView txtDate;
    private ListView listView;
    private Calendar cal;
    private Expence expence;
    private TextView txtEmptyView;
    private DailyExpenceAdapter dailyExpenceAdapter;
    List<Expence> expences;
    private ExpenceManageableServiceBase consommationService;
    private CommonCriterias criterias=new CommonCriterias();
    private View view;
    private float x1,x2;

    public View onCreate(Activity activity) {
        _activity=activity;
        LayoutInflater li = activity.getLayoutInflater();
        view=li.inflate(R.layout.daily_expences,null);

        listView=(ListView)view.findViewById(R.id.addConsomation_listview);
        txtDate=(TextView)view.findViewById(R.id.addConsumation_date);
        txtEmptyView=(TextView)view.findViewById(R.id.expence_empty_view);
        cal=Calendar.getInstance();

        consommationService=new ExpenceManageableServiceBase(_activity);


        txtDate.setText(Globals.DISPLAY_DATE_FORMAT.format(cal.getTime()));
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalendarDialog();
            }
        });

        expence =new Expence();
        expences = new ArrayList<Expence>();
        Date date=new Date();
        fillConsomations(date);

        listView.setOnTouchListener(swipeDate);
        txtEmptyView.setOnTouchListener(swipeDate);
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
        cal.setTime(date);
        txtDate.setText(Globals.DISPLAY_DATE_FORMAT.format(date));
        criterias.setDateDebut(date);
        criterias.setDateFin(date);
        criterias.setUser(Globals.CURRENT_USER);
        expences =consommationService.readByCritireas(criterias);
        if(expences ==null || expences.size()==0){
            expences =new ArrayList<Expence>();
            listView.setAdapter(null);
            txtEmptyView.setVisibility(View.VISIBLE);
        }
        else{

            txtEmptyView.setVisibility(View.INVISIBLE);
            dailyExpenceAdapter =new DailyExpenceAdapter(_activity, expences);
            listView.setDivider(null);
            listView.setAdapter(dailyExpenceAdapter);
        }
    }

    public void deleteConsomation(int position){
        consommationService.delete(expences.get(position).getId());
        expences.remove(position);
        dailyExpenceAdapter.notifyDataSetChanged();
    }


    public void onSwipe(int number){
        cal.add(Calendar.DAY_OF_MONTH,number);
        fillConsomations(cal.getTime());
    }

    private View.OnTouchListener swipeDate = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            boolean result = true;

            try {
                switch(event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();
                        float deltaX = x2 - x1;
                        if (Math.abs(deltaX) > Globals.MIN_DISTANCE)
                        {
                            if (x2 > x1)
                            {
                                onSwipe(-1);
                                result=true;
                            }
                            else
                            {
                                onSwipe(1);
                                result=true;
                            }
                        }
                        break;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    };
}
