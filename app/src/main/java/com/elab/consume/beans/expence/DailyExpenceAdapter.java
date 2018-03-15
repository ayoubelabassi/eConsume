package com.elab.consume.beans.expence;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.elab.consume.R;
import com.elab.consume.beans.main.MainActivity;
import com.elab.consume.checker.expence.Expence;
import com.elab.consume.tools.Globals;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AYOUB on 26/02/2018.
 */

public class DailyExpenceAdapter extends BaseAdapter {

    private List<Expence> expenceList =new ArrayList<Expence>();
    private Context context;

    public DailyExpenceAdapter(Context cntxt, List<Expence> expenceList) {
        this.expenceList = expenceList;
        context=cntxt;
    }

    @Override
    public int getCount() {
        return expenceList.size();
    }

    @Override
    public Object getItem(int i) {
        return expenceList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return expenceList.get(i).getId();
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater= LayoutInflater.from(context);
        View listView=layoutInflater.inflate(R.layout.expence_view,null);
        if(listView!=null){
            TextView txt_desc=(TextView)listView.findViewById(R.id.expence_desc);
            //TextView txt_date=(TextView)listView.findViewById(R.id.expence_date);
            TextView txt_tot=(TextView)listView.findViewById(R.id.expence_tot);
            TextView txt_name=(TextView)listView.findViewById(R.id.expence_name);
            TextView txt_price=(TextView)listView.findViewById(R.id.expence_price);
            TextView txt_qte=(TextView)listView.findViewById(R.id.expence_qte);
            Button btnDelete=(Button)listView.findViewById(R.id.expence_btn_delete);
            Button btnEdit=(Button)listView.findViewById(R.id.expence_btn_edit);

            //txt_date.setText(Globals.DISPLAY_DATE_FORMAT.format(expenceList.get(position).getDate()));
            txt_desc.setText(" "+expenceList.get(position).getDescription());
            txt_tot.setText(String.valueOf(expenceList.get(position).getPrice()* expenceList.get(position).getQte()));
            txt_name.setText(expenceList.get(position).getName().toUpperCase());
            txt_price.setText(String.valueOf(expenceList.get(position).getPrice()));
            txt_qte.setText(String.valueOf(expenceList.get(position).getQte()));

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    ((MainActivity)context).dailyExpences.deleteConsomation(position);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };
                    builder.setMessage(R.string.expence_delete_msg).setPositiveButton(R.string.yes, dialogClickListener)
                            .setNegativeButton(R.string.no, dialogClickListener).show();
                }
            });

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ExpenceEditor add_consomation=new ExpenceEditor();
                    add_consomation.setExpence(expenceList.get(position));
                    add_consomation.setOperation(Globals.EDIT_OP);
                    add_consomation.setContext(context);
                    add_consomation.setUpdate_date(expenceList.get(position).getDate());
                    add_consomation.show(((Activity)context).getFragmentManager(),"Edit Expence");
                }
            });
        }
        return listView;
    }
}
