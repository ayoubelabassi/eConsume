package com.elab.consume.beans.expence;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elab.consume.R;
import com.elab.consume.beans.utils.MonthYearPickerDialog;
import com.elab.consume.checker.expence.Expence;
import com.elab.consume.checker.expence.ExpenceManageableServiceBase;
import com.elab.consume.checker.expence.MonthExpence;
import com.elab.consume.tools.CommonCriterias;
import com.elab.consume.tools.Globals;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by AYOUB on 11/03/2018.
 */

public class MonthlyExpences {

    //Views
    private TextView txtDate;
    private BarChart barChart;
    private View view;

    //Layouts
    private LinearLayout mainLayout;
    private RelativeLayout lineLayout;

    //Data
    private Activity _activity;
    private CommonCriterias criterias=new CommonCriterias();
    private ExpenceManageableServiceBase expenceService;
    private List<Expence> expences;
    private List<MonthExpence> monthExpences;
    private Calendar cal;
    private float x1,x2;


    public View onCreate(Activity activity) {
        _activity=activity;
        LayoutInflater li = activity.getLayoutInflater();
        view=li.inflate(R.layout.monthly_expences,null);

        //initialize views
        txtDate=(TextView)view.findViewById(R.id.me_txt_date);
        mainLayout=(LinearLayout)view.findViewById(R.id.me_main_layout);
        lineLayout=(RelativeLayout)view.findViewById(R.id.me_linechart_layout);
        barChart=(BarChart)view.findViewById(R.id.me_bar_chart);

        //Initialize datas
        cal=Calendar.getInstance();
        expenceService =new ExpenceManageableServiceBase(_activity);

        //initialize events
        txtDate.setText(Globals.DISPLAY_DATE_FORMAT.format(cal.getTime()));
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalendarDialog();
            }
        });

        mainLayout.setOnTouchListener(swipeDate);
        //fill data
        loadExpences(cal.getTime());
        return view;
    }

    public void openCalendarDialog(){
        MonthYearPickerDialog monthlyExpences=new MonthYearPickerDialog();
        monthlyExpences.setListener(dpickerListnner);
        monthlyExpences.setCal(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        monthlyExpences.MIN_YEAR=expenceService.getMinDate().get(Calendar.YEAR);
        monthlyExpences.show(_activity.getFragmentManager(),"MonthYear Picker");
    }

    private DatePickerDialog.OnDateSetListener dpickerListnner=new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker datePicker, int y, int m, int d) {
            cal.set(y,m,d);
            txtDate.setText(Globals.MONTH_DATE_FORMAT.format(cal.getTime()));
            loadExpences(cal.getTime());
        }
    };

    public void loadExpences(Date date) {
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH,1);
        txtDate.setText(Globals.MONTH_DATE_FORMAT.format(date));
        criterias.setDateDebut(cal.getTime());
        int maxDay=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH,maxDay);
        criterias.setDateFin(cal.getTime());
        criterias.setUser(Globals.CURRENT_USER);
        monthExpences = expenceService.readMonthExpences(criterias);
        fillLineCharte();
    }

    public void fillLineCharte(){
        // fill the lists
        List<BarEntry> Dentries = new ArrayList<BarEntry>();
        List<BarEntry> Aentries = new ArrayList<BarEntry>();
        List<BarEntry> Gentries = new ArrayList<BarEntry>();
        List<String> labels=new ArrayList<String>();

        Calendar calendar=Calendar.getInstance();
        Calendar current=cal;
        for (int i=1;i<=cal.getActualMaximum(Calendar.DAY_OF_MONTH);i++){
            Boolean exist=false;
            current.set(Calendar.DAY_OF_MONTH,i);
            labels.add(Globals.DAY_DATE_FORMAT.format(current.getTime()));
            for (MonthExpence data : monthExpences) {
                calendar.setTime(data.getDate());
                if(calendar.get(Calendar.DAY_OF_MONTH)==i){
                    if(data.getMontant()<=(Globals.MAX_AMOUNT/2)) {
                        Gentries.add(new BarEntry(calendar.get(Calendar.DAY_OF_MONTH), data.getMontant(),"Hello"));
                    }
                    else if(data.getMontant()<=Globals.MAX_AMOUNT)
                        Aentries.add(new BarEntry(calendar.get(Calendar.DAY_OF_MONTH), data.getMontant(),"Hello"));
                    else
                        Dentries.add(new BarEntry(calendar.get(Calendar.DAY_OF_MONTH), data.getMontant(),"Hello"));
                    exist=true;
                }
            }
            if (!exist)
                Gentries.add(new BarEntry(current.get(Calendar.DAY_OF_MONTH), 0));
        }
        BarDataSet Dset=new BarDataSet(Dentries, _activity.getString(R.string.dangerStatus));
        Dset.setColor(Color.parseColor("#ea5d5d"));
        BarDataSet Aset=new BarDataSet(Aentries, _activity.getString(R.string.averageStatus));
        Aset.setColor(Color.parseColor("#f9e800"));
        BarDataSet Gset=new BarDataSet(Gentries, _activity.getString(R.string.goodStatus));
        Gset.setColor(Color.parseColor("#69d157"));
        List<IBarDataSet> set = new ArrayList<IBarDataSet>();
        set.add(Dset);
        set.add(Aset);
        set.add(Gset);
        barChart.setData(new BarData(set));
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);
        barChart.setTouchEnabled(true);
        barChart.invalidate();
    }

    public void onSwipe(int number){
        cal.add(Calendar.MONTH,number);
        loadExpences(cal.getTime());
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
