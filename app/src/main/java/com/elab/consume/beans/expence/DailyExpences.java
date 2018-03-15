package com.elab.consume.beans.expence;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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


    private TextView txtEmptyView;
    private TextView txtDate;
    private TextView txtTotExp;
    private TextView txtTotIncom;
    private TextView txtBalance;

    private ListView listView;
    private ImageButton btnNext;
    private ImageButton btnPrevious;
    private ImageView imgUp;
    private ImageView imgDown;


    private View view;
    private Activity _activity;

    private Calendar cal;
    private Expence expence;
    private DailyExpenceAdapter dailyExpenceAdapter;
    private List<Expence> expences;
    private ExpenceManageableServiceBase expenceService;
    private CommonCriterias criterias=new CommonCriterias();
    private float x1,x2;


    public View onCreate(Activity activity) {
        _activity=activity;
        LayoutInflater li = activity.getLayoutInflater();
        view=li.inflate(R.layout.daily_expences,null);

        listView=(ListView)view.findViewById(R.id.de_list_view);
        txtDate=(TextView)view.findViewById(R.id.de_date);
        txtEmptyView=(TextView)view.findViewById(R.id.de_empty_view);
        btnNext=(ImageButton) view.findViewById(R.id.de_next_expence);
        btnPrevious=(ImageButton)view.findViewById(R.id.de_previous_date);
        imgDown=(ImageView)view.findViewById(R.id.de_img_down);
        imgUp=(ImageView)view.findViewById(R.id.de_img_up);

        txtTotIncom=(TextView)view.findViewById(R.id.de_txt_tot_incomes);
        txtBalance=(TextView)view.findViewById(R.id.de_txt_balance);
        txtTotExp=(TextView)view.findViewById(R.id.de_txt_tot_exp);

        cal=Calendar.getInstance();

        expenceService =new ExpenceManageableServiceBase(_activity);
        txtDate.setText(Globals.DISPLAY_DATE_FORMAT.format(cal.getTime()));

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalendarDialog();
            }
        });
        txtEmptyView.setOnTouchListener(swipeDate);
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSwipe(-1);
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSwipe(1);
            }
        });



        expence =new Expence();
        expences = new ArrayList<Expence>();
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
        cal.setTime(date);
        txtDate.setText(Globals.DISPLAY_DATE_FORMAT.format(date));
        criterias.setDateDebut(date);
        criterias.setDateFin(date);
        criterias.setUser(Globals.CURRENT_USER);
        expences = expenceService.readByCritireas(criterias);
        if(expences ==null || expences.size()==0){
            expences =new ArrayList<Expence>();
            listView.setAdapter(null);
            txtEmptyView.setVisibility(View.VISIBLE);
            listView.setVisibility(View.INVISIBLE);
        }
        else{

            txtEmptyView.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.VISIBLE);
            dailyExpenceAdapter =new DailyExpenceAdapter(_activity, expences);
            listView.setDivider(null);
            listView.setAdapter(dailyExpenceAdapter);
        }
        Double Expencetotale=expenceService.readTotaleExpence(criterias);
        Double IncomesTotale=20.0;
        Double balence=IncomesTotale-Expencetotale;

        txtTotExp.setText(String.format(Globals.MONEY_FORMAT, Expencetotale)+" "+Globals.UNIT_MONEY);
        txtTotIncom.setText(String.format(Globals.MONEY_FORMAT, IncomesTotale)+" "+Globals.UNIT_MONEY);
        txtBalance.setText(String.format(Globals.MONEY_FORMAT, balence)+" "+Globals.UNIT_MONEY);

        if(balence>0){
            imgUp.setVisibility(View.VISIBLE);
            imgDown.setVisibility(View.INVISIBLE);
        }else{
            imgUp.setVisibility(View.INVISIBLE);
            imgDown.setVisibility(View.VISIBLE);
        }
    }

    public void deleteConsomation(int position){
        expenceService.delete(expences.get(position).getId());
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
