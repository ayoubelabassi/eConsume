package com.ayoubtadakker.tadakker.utils.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.ayoubtadakker.tadakker.R;
import com.ayoubtadakker.tadakker.beans.suivi.add_consomation;
import com.ayoubtadakker.tadakker.checker.suivi.consomation.Consomation;
import com.ayoubtadakker.tadakker.utils.tools.Globals;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AYOUB on 26/02/2018.
 */

public class ConsomationAdapter extends BaseAdapter {

    private List<Consomation> consomationList=new ArrayList<Consomation>();
    private Context context;
    private LayoutInflater inflater;

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

            txt_date.setText(Globals.DATE_FORMAT.format(consomationList.get(position).getDate()));
            txt_desc.setText(consomationList.get(position).getDescription());
            txt_tot.setText(String.valueOf(consomationList.get(position).getPrice()*consomationList.get(position).getQte()));
            txt_name.setText(consomationList.get(position).getName());
            txt_price.setText(String.valueOf(consomationList.get(position).getPrice()));
            txt_qte.setText(String.valueOf(consomationList.get(position).getQte()));

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    add_consomation add_consomation=new add_consomation();
                    add_consomation.setConsomation(consomationList.get(position));
                    add_consomation.setOperation(Globals.EDIT_OP);
                    add_consomation.setContext(context);
                    add_consomation.show(((Activity)context).getFragmentManager(),"Edit Consomation");
                }
            });
        }
        return listView;
    }
}
