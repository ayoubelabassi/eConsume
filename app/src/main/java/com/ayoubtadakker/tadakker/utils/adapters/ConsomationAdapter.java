package com.ayoubtadakker.tadakker.utils.adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ayoubtadakker.tadakker.R;
import com.ayoubtadakker.tadakker.checker.suivi.Consomation;
import com.ayoubtadakker.tadakker.utils.tools.Globals;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.ayoubtadakker.tadakker.R.layout.consomation_view;

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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater= LayoutInflater.from(context);
        View listView=layoutInflater.inflate(R.layout.consomation_view,null);
        if(listView!=null){
            TextView tot=(TextView)listView.findViewById(R.id.consomation_tot);
            TextView name=(TextView)listView.findViewById(R.id.consomation_name);
            TextView prix=(TextView)listView.findViewById(R.id.consomation_prix);
            TextView qte=(TextView)listView.findViewById(R.id.consomation_qte);
            ImageButton btnDelete=(ImageButton)listView.findViewById(R.id.consomation_btnDelete);

            tot.setText(String.valueOf(consomationList.get(i).getPrice()*consomationList.get(i).getQte()));
            name.setText(consomationList.get(i).getName());
            prix.setText(String.valueOf(consomationList.get(i).getPrice()));
            qte.setText(String.valueOf(consomationList.get(i).getQte()));


            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    updateForm(consomationList.get(i));
                }
            });
        }
        return listView;
    }

    private void updateForm(Consomation consomation){
        if(consomation!=null){
            LayoutInflater layoutInflater= LayoutInflater.from(context);
            View view=layoutInflater.inflate(R.layout.add_consomation,null);
            EditText txtDate=(EditText)view.findViewById(R.id.addConsumation_date);
            EditText txtPrice=(EditText)view.findViewById(R.id.addConsumation_price);
            EditText txtName=(EditText)view.findViewById(R.id.addConsumation_name);
            EditText txtDescription=(EditText)view.findViewById(R.id.addConsumation_description);
            EditText txtQTE=(EditText)view.findViewById(R.id.addConsumation_qte);


            txtDate.setText(Globals.DATE_FORMAT.format(consomation.getDate()));
            txtDescription.setText(consomation.getDescription());
            txtName.setText(consomation.getName());
            txtPrice.setText(new DecimalFormat("$##.##").format(consomation.getPrice()));
            //txtQTE.setText(consomation.getQte());
        }
    }
}
