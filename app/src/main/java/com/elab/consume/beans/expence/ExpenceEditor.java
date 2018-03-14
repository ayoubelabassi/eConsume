package com.elab.consume.beans.expence;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.elab.consume.R;
import com.elab.consume.beans.main.MainActivity;
import com.elab.consume.checker.expence.Expence;
import com.elab.consume.checker.expence.ExpenceManageableServiceBase;
import com.elab.consume.tools.Globals;
import com.elab.consume.tools.Logger;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ExpenceEditor extends DialogFragment {

    private TextView txt_date;
    private EditText txt_desc;
    private AutoCompleteTextView txt_name;
    private EditText txt_tot;
    private EditText txt_price;
    private EditText txt_qte;
    private TextView txt_unit;
    private Button btnValide;
    private ImageView img_txt_name;

    /**
     *
     */
    private Expence expence;
    private String operation;
    private Context context;
    private Calendar cal;

    private Date update_date;
    private ExpenceManageableServiceBase expenceService;
    private List<String> productsNames;
    private Boolean isDown=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.expence_editor, container, false);
        txt_date=(TextView)view.findViewById(R.id.txt_cns_date);
        txt_desc=(EditText)view.findViewById(R.id.txt_cns_desc);
        txt_name=(AutoCompleteTextView)view.findViewById(R.id.txt_cns_name);
        txt_tot=(EditText)view.findViewById(R.id.txt_cns_tot);
        txt_price=(EditText)view.findViewById(R.id.txt_cns_price);
        txt_qte=(EditText)view.findViewById(R.id.txt_cns_qte);
        btnValide=(Button)view.findViewById(R.id.btn_cns_edit);
        txt_unit=(TextView)view.findViewById(R.id.txt_consommation_unit);
        img_txt_name=(ImageView)view.findViewById(R.id.img_txt_name);


        txt_name.setThreshold(0);

        expenceService =new ExpenceManageableServiceBase(context);

        cal=Calendar.getInstance();

        initFragment();

        txt_price.addTextChangedListener(totaleCalculator);

        txt_qte.addTextChangedListener(totaleCalculator);

        txt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalendarDialog();
            }
        });

        btnValide.setOnClickListener(validerClick);

        img_txt_name.setOnClickListener(autoCompleteViewer);
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
                txt_price.setError(getString(R.string.expence_price_error));
                valide=false;
            }
        }

        if(txt_qte.getText().length()==0){
            txt_qte.setError(getString(R.string.error_field_required));
            valide=false;
        }else{
            if(Integer.parseInt(txt_qte.getText().toString())<=0){
                txt_qte.setError(getString(R.string.expence_qte_error));
                valide=false;
            }
        }
        return valide;
    }

    public void initFragment(){
        txt_unit.setText(Globals.UNIT_MONEY);
        setNamesAdapter();
        if(expence !=null){
            cal.setTime(expence.getDate());
            txt_date.setText(Globals.DISPLAY_DATE_FORMAT.format(expence.getDate()));
            txt_desc.setText(expence.getDescription());
            txt_tot.setText(String.valueOf(expence.getPrice()* expence.getQte()));
            txt_name.setText(expence.getName());
            txt_price.setText(String.valueOf(expence.getPrice()));
            txt_qte.setText(String.valueOf(expence.getQte()));
        }else{
            expence =new Expence();
            txt_date.setText(Globals.DISPLAY_DATE_FORMAT.format(cal.getTime()));
            txt_desc.setText("");
            txt_tot.setText("0");
            txt_name.setText("");
            txt_price.setText("0");
            txt_qte.setText("0");
        }
    }

    public void setNamesAdapter(){
        productsNames=expenceService.readExpencesNames(Globals.CURRENT_USER.getId());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_dropdown_item_1line, productsNames);
        txt_name.setAdapter(adapter);
    }
    /**
     * Events
     */
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int year,
                              int month, int day) {
            cal.set(year,month,day);
            txt_date.setText(Globals.DISPLAY_DATE_FORMAT.format(cal.getTime()));

        }
    };

    private View.OnClickListener validerClick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(validateForm()){
                try {
                    if(expence ==null)
                        expence =new Expence();
                    String s=txt_date.getText().toString().trim();
                    expence.setDate(Globals.DISPLAY_DATE_FORMAT.parse(s));
                    expence.setDescription(txt_desc.getText().toString().trim());
                    expence.setName(txt_name.getText().toString().trim());
                    expence.setQte(Integer.parseInt(txt_qte.getText().toString().trim()));
                    expence.setPrice(Double.parseDouble(txt_price.getText().toString().trim()));
                    expence.setUser_fk(Globals.CURRENT_USER.getId());
                } catch (ParseException e) {
                    Logger.ERROR(e);
                }
                if(operation.equals(Globals.EDIT_OP)){
                    expenceService.update(expence);
                    ((MainActivity)context).dailyExpences.fillConsomations(update_date);
                    getFragmentManager().beginTransaction().remove(ExpenceEditor.this).commit();
                }else{
                    expenceService.create(expence);
                    expence =null;
                }
                initFragment();
            }
        }
    };

    private TextWatcher totaleCalculator=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if(txt_qte.getText().equals("0")){
                txt_qte.setText("");
            }
            if(txt_price.getText().equals("0")){
                txt_price.setText("");
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            txt_tot.setText(String.valueOf(calculateTotal(txt_price.getText().toString().trim(),txt_qte.getText().toString().trim())));
        }
    };

    private View.OnClickListener autoCompleteViewer=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(isDown){
                img_txt_name.setImageResource(R.drawable.ic_arrow_drop_down);
                txt_name.dismissDropDown();
                isDown=false;
            }else {
                img_txt_name.setImageResource(R.drawable.ic_arrow_drop_up);
                txt_name.showDropDown();
                isDown=true;
            }
        }
    };
    /**
     * getters and setters
     * @return
     */

    public void setExpence(Expence expence) {
        this.expence = expence;
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

    public void setUpdate_date(Date update_date) {
        this.update_date = update_date;
    }
}
