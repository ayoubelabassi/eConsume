package com.elab.consume.utils.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.elab.consume.R;
import com.elab.consume.beans.main.MainActivity;
import com.elab.consume.beans.suivi.add_consomation;
import com.elab.consume.checker.suivi.consomation.Consomation;
import com.elab.consume.utils.tools.Globals;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AYOUB on 26/02/2018.
 */

public class ConsomationAdapter extends BaseAdapter {

    private List<Consomation> consomationList=new ArrayList<Consomation>();
    private Context context;

    public ConsomationAdapter(Context cntxt,List<Consomation> consomationList) {
        this.consomationList = consomationList;
        context=cntxt;
    }

    @Override
    public int getCount() {
        return consomationList.size();
    }

    @Override
    public Object getItem(int i) {
        return consomationList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return consomationList.get(i).getId();
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater= LayoutInflater.from(context);
        View listView=layoutInflater.inflate(R.layout.consomation_view,null);
        if(listView!=null){
            EditText txt_desc=(EditText)listView.findViewById(R.id.consomation_description);
            EditText txt_date=(EditText)listView.findViewById(R.id.consomation_date);
            EditText txt_tot=(EditText)listView.findViewById(R.id.consomation_tot);
            EditText txt_name=(EditText)listView.findViewById(R.id.consomation_name);
            EditText txt_price=(EditText)listView.findViewById(R.id.consomation_prix);
            EditText txt_qte=(EditText)listView.findViewById(R.id.consomation_qte);
            Button btnDelete=(Button)listView.findViewById(R.id.consomation_btn_delete);
            Button btnEdit=(Button)listView.findViewById(R.id.consomation_btn_edit);

            txt_date.setText(Globals.DISPLAY_DATE_FORMAT.format(consomationList.get(position).getDate()));
            txt_desc.setText(consomationList.get(position).getDescription());
            txt_tot.setText(String.valueOf(consomationList.get(position).getPrice()*consomationList.get(position).getQte()));
            txt_name.setText(consomationList.get(position).getName());
            txt_price.setText(String.valueOf(consomationList.get(position).getPrice()));
            txt_qte.setText(String.valueOf(consomationList.get(position).getQte()));

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    ((MainActivity)context).consomationsActivity.deleteConsomation(position);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };
                    builder.setMessage(R.string.consomation_delete_msg).setPositiveButton(R.string.yes, dialogClickListener)
                            .setNegativeButton(R.string.no, dialogClickListener).show();
                }
            });

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
                @Override
                public void onClick(View view) {
                    add_consomation add_consomation=new add_consomation();
                    add_consomation.setConsomation(consomationList.get(position));
                    add_consomation.setOperation(Globals.EDIT_OP);
                    add_consomation.setContext(context);
                    add_consomation.setUpdate_date(consomationList.get(position).getDate());
                    add_consomation.show(((Activity)context).getFragmentManager(),"Edit Consomation");
                }
            });
        }
        return listView;
    }
}
