package com.ayoubtadakker.tadakker.beans.suivi;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.ayoubtadakker.tadakker.R;
import com.ayoubtadakker.tadakker.checker.suivi.consomation.Consomation;
import com.ayoubtadakker.tadakker.utils.tools.Globals;

import java.util.Calendar;

public class add_consomation extends DialogFragment {

    private TextView txt_date;
    private EditText txt_desc;
    private EditText txt_name;
    private EditText txt_tot;
    private EditText txt_price;
    private EditText txt_qte;
    private Button btnValide;

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    private Consomation consomation;
    private String operation;
    private Context context;
    private Calendar cal;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dialog_add_consomation, container, false);
        txt_date=(TextView)view.findViewById(R.id.txt_cns_date);
        txt_desc=(EditText)view.findViewById(R.id.txt_cns_desc);
        txt_name=(EditText)view.findViewById(R.id.txt_cns_name);
        txt_tot=(EditText)view.findViewById(R.id.txt_cns_tot);
        txt_price=(EditText)view.findViewById(R.id.txt_cns_price);
        txt_qte=(EditText)view.findViewById(R.id.txt_cns_qte);
        btnValide=(Button)view.findViewById(R.id.btn_cns_edit);
        cal=Calendar.getInstance();
        if(consomation!=null){
            cal.setTime(consomation.getDate());
            txt_date.setText(Globals.DISPLAY_DATE_FORMAT.format(consomation.getDate()));
            txt_desc.setText(consomation.getDescription());
            txt_tot.setText(String.valueOf(consomation.getPrice()*consomation.getQte()));
            txt_name.setText(consomation.getName());
            txt_price.setText(String.valueOf(consomation.getPrice()));
            txt_qte.setText(String.valueOf(consomation.getQte()));
        }else{
            txt_date.setText(Globals.DISPLAY_DATE_FORMAT.format(cal.getTime()));
        }

        txt_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                txt_tot.setText(String.valueOf(calculateTotal(txt_price.getText().toString().trim(),txt_qte.getText().toString().trim())));
            }
        });

        txt_qte.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                txt_tot.setText(String.valueOf(calculateTotal(txt_price.getText().toString().trim(),txt_qte.getText().toString().trim())));
            }
        });

        txt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalendarDialog();
            }
        });
        return view;
    }

    private Double calculateTotal(String price, String Qte){
        Double result=0.0;
        if(price.length() !=0 &&  price.length() !=0){
            try {
                Double p=Double.valueOf(price);
                int c=Integer.valueOf(Qte);
                result=p*c;
            }catch (Exception e){
                result=0.0;
            }
        }

        return result;
    }

    //Open Calendar
    public void openCalendarDialog(){
        DatePickerDialog datePicker = new DatePickerDialog(context,datePickerListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        datePicker.show();
    }
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int year,
                              int month, int day) {
            cal.set(year,month,day);
            txt_date.setText(Globals.DISPLAY_DATE_FORMAT.format(cal.getTime()));

        }
    };
    /**
     * getters and setters
     * @return
     */

    public void setConsomation(Consomation consomation) {
        this.consomation = consomation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
