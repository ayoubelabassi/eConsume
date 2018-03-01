package com.ayoubtadakker.tadakker.utils.adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.ayoubtadakker.tadakker.R;
import com.ayoubtadakker.tadakker.checker.suivi.Consomation;
import com.ayoubtadakker.tadakker.utils.tools.Globals;

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
    public View getView(int i, View view, ViewGroup viewGroup) {
        View gridView=view;
        if(view==null){
            inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            gridView=inflater.inflate(R.layout.add_consomation,null);

            TextView tot=(TextView)gridView.findViewById(R.id.consomation_tot);
            TextView name=(TextView)gridView.findViewById(R.id.consomation_name);
            TextView prix=(TextView)gridView.findViewById(R.id.consomation_prix);
            TextView qte=(TextView)gridView.findViewById(R.id.consomation_qte);

            tot.setText(String.valueOf(consomationList.get(i).getPrice()*consomationList.get(i).getQte()));
            name.setText(consomationList.get(i).getName());
            prix.setText(String.valueOf(consomationList.get(i).getPrice()));
            qte.setText(String.valueOf(consomationList.get(i).getQte()));
        }
        return gridView;
    }
}
