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
import com.ayoubtadakker.tadakker.checker.suivi.consomation.ConsomationManageableServiceBase;
import com.ayoubtadakker.tadakker.utils.tools.Globals;
import com.ayoubtadakker.tadakker.utils.tools.Logger;

import java.text.ParseException;
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
    private ConsomationManageableServiceBase consommationService;



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

        consommationService=new ConsomationManageableServiceBase(context);

        cal=Calendar.getInstance();

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

        btnValide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateForm()){
                    try {
                        if(consomation==null)
                            consomation=new Consomation();
                        String s=txt_date.getText().toString().trim();
                        consomation.setDate(Globals.DISPLAY_DATE_FORMAT.parse(s));
                        consomation.setDescription(txt_desc.getText().toString().trim());
                        consomation.setName(txt_name.getText().toString().trim());
                        consomation.setQte(Integer.parseInt(txt_qte.getText().toString().trim()));
                        consomation.setPrice(Double.parseDouble(txt_price.getText().toString().trim()));
                    } catch (ParseException e) {
                        Logger.ERROR(e);
                    }
                    if(operation.equals(Globals.EDIT_OP)){
                        consommationService.update(consomation);
                    }else{
                        consommationService.create(consomation);
                        consomation=null;
                    }
                    initFragment();
                }
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
    //validate form
    public boolean validateForm(){
        txt_name.setError(null);
        txt_price.setError(null);
        txt_qte.setError(null);

        Boolean valide=true;
        if(txt_name.getText().length()==0){
            txt_name.setError(getString(R.string.error_field_required));
            valide=false;
        }
        if(txt_price.getText().length()==0){
            txt_price.setError(getString(R.string.error_field_required));
            valide=false;
        }
        else{
            if(Double.parseDouble(txt_price.getText().toString())<=0){
                txt_price.setError(getString(R.string.consomation_price_error));
                valide=false;
            }
        }

        if(txt_qte.getText().length()==0){
            txt_qte.setError(getString(R.string.error_field_required));
            valide=false;
        }else{
            if(Integer.parseInt(txt_qte.getText().toString())<=0){
                txt_qte.setError(getString(R.string.consomation_qte_error));
                valide=false;
            }
        }
        return valide;
    }

    public void initFragment(){
        if(consomation!=null){
            cal.setTime(consomation.getDate());
            txt_date.setText(Globals.DISPLAY_DATE_FORMAT.format(consomation.getDate()));
            txt_desc.setText(consomation.getDescription());
            txt_tot.setText(String.valueOf(consomation.getPrice()*consomation.getQte()));
            txt_name.setText(consomation.getName());
            txt_price.setText(String.valueOf(consomation.getPrice()));
            txt_qte.setText(String.valueOf(consomation.getQte()));
        }else{
            consomation=new Consomation();
            txt_date.setText(Globals.DISPLAY_DATE_FORMAT.format(cal.getTime()));
            txt_desc.setText("");
            txt_tot.setText("0");
            txt_name.setText("");
            txt_price.setText("0");
            txt_qte.setText("0");
        }
    }
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
