package com.elab.consume.beans.expence;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.elab.consume.R;
import com.elab.consume.beans.main.MainActivity;
import com.elab.consume.checker.expence.Expence;
import com.elab.consume.tools.Globals;
import com.tubb.smrv.SwipeHorizontalMenuLayout;
import com.tubb.smrv.listener.SimpleSwipeFractionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AYOUB on 26/02/2018.
 */

public class DailyExpenceAdapter extends BaseAdapter {

    static final int VIEW_TYPE_ENABLE = 0;
    static final int VIEW_TYPE_DISABLE = 1;
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
    public View getView(final int position,View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        //LayoutInflater layoutInflater= LayoutInflater.from(context);
        convertView=LayoutInflater.from(context).inflate(R.layout.expence_details,null);
        if(convertView!=null){
            viewHolder = new ViewHolder(convertView,position);
            convertView.setTag(viewHolder);


            final ViewHolder finalViewHolder = viewHolder;
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finalViewHolder.sml.smoothCloseMenu();
                }
            });
            viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalViewHolder.sml.smoothCloseMenu();
                    // must close normal
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

                    expenceList.remove(position);
                    notifyDataSetChanged();
                }
            });

            viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ExpenceEditor add_consomation=new ExpenceEditor();
                    add_consomation.setExpence(expenceList.get(position));
                    add_consomation.setOperation(Globals.EDIT_OP);
                    add_consomation.setContext(context);
                    add_consomation.setUpdate_date(expenceList.get(position).getDate());
                    add_consomation.show(((Activity)context).getFragmentManager(),"Edit Expence");
                }
            });
            viewHolder.txt_Unit.setText(Globals.UNIT_MONEY);
            viewHolder.txt_desc.setText(" "+expenceList.get(position).getDescription());
            viewHolder.txt_tot.setText(String.valueOf(expenceList.get(position).getPrice()* expenceList.get(position).getQte()));
            viewHolder.txt_name.setText(expenceList.get(position).getName().toUpperCase());
            viewHolder.txt_price.setText(String.valueOf(expenceList.get(position).getPrice()));
            viewHolder.txt_qte.setText(String.valueOf(expenceList.get(position).getQte()));

            boolean swipeEnable = swipeEnableByViewType(getItemViewType(position));
            viewHolder.sml.setSwipeEnable(swipeEnable);
        }
        return convertView;
    }

    boolean swipeEnableByViewType(int viewType) {
        /*
        if(viewType == VIEW_TYPE_ENABLE)
            return true;
        else
            return viewType != VIEW_TYPE_DISABLE;
            */
        return true;
    }

    class ViewHolder{
        int position;
        TextView txt_desc;
        TextView txt_Unit;
        TextView txt_tot;
        TextView txt_name;
        TextView txt_price;
        TextView txt_qte;
        Button btnDelete;
        Button btnEdit;
        SwipeHorizontalMenuLayout sml;
        ViewHolder(View itemView,int position) {
            txt_desc=(TextView)itemView.findViewById(R.id.expence_desc);
            txt_Unit=(TextView)itemView.findViewById(R.id.ev_txt_unit);
            txt_tot=(TextView)itemView.findViewById(R.id.expence_tot);
            txt_name=(TextView)itemView.findViewById(R.id.expence_name);
            txt_price=(TextView)itemView.findViewById(R.id.expence_price);
            txt_qte=(TextView)itemView.findViewById(R.id.expence_qte);
            btnDelete=(Button)itemView.findViewById(R.id.btnDeleteExpence);
            btnEdit=(Button)itemView.findViewById(R.id.btnEditExpence);
            sml = (SwipeHorizontalMenuLayout) itemView.findViewById(R.id.sml);
        }
    }
}
