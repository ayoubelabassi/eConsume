package com.elab.consume.checker.expence;

import android.content.Context;
import android.util.Log;

import com.elab.consume.data.local.DBHandler;
import com.elab.consume.tools.CommonCriterias;
import com.elab.consume.tools.Globals;

import java.util.Calendar;
import java.util.List;

/**
 * Created by NAWAR on 28/02/2018.
 */

public class ExpenceManageableServiceBase implements ExpenceManageableService {
    private DBHandler db;

    public ExpenceManageableServiceBase(Context context){
        db=new DBHandler(context);
    }

    @Override
    public void create(Expence entity) {
        db.createConsomation(entity);
    }

    @Override
    public void create(List<Expence> entities) {
        //db.createConsomation(entities);
    }

    @Override
    public void update(Expence entity) {
        db.updateConsommation(entity);
    }

    @Override
    public void update(List<Expence> entities) {
        //db.updateConsommation(entities);
    }

    @Override
    public Expence read(int id) {
        return db.getConsomation(id);
    }

    @Override
    public void delete(int id) {
        db.deleteConsommation(id);
    }

    @Override
    public List<Expence> readAll(int user_id) {
        return db.getConsomations(user_id);
    }

    @Override
    public List<Expence> readByCritireas(CommonCriterias criterias) {
        String req="SELECT * FROM CONSOMATION WHERE ";
        if(criterias.getDateDebut()!=null && criterias.getDateFin()!=null){
            req+="(DATE BETWEEN '"+Globals.DATE_FORMAT.format(criterias.getDateDebut())+"' AND '"+
                    Globals.DATE_FORMAT.format(criterias.getDateFin())+"')";
        }
        if(criterias.getUser()!=null){
            req+=" AND USER_FK = "+String.valueOf(criterias.getUser().getId())+" ";
        }
        return db.readConsommationByCriterias(req);
    }

    @Override
    public List<String> readExpencesNames(int user_id) {
        return db.readExpencesNames(user_id);
    }

    @Override
    public Calendar getMinDate() {
        Calendar cal=Calendar.getInstance();
        cal.setTime(db.getExpenceMinDate(Globals.CURRENT_USER.getId()));
        return cal;
    }

    @Override
    public List<MonthExpence> readMonthExpences(CommonCriterias criterias) {
        String req="SELECT DATE, SUM(PRICE*QTE) as 'AMOUNT' FROM CONSOMATION WHERE ";
        if(criterias.getDateDebut()!=null && criterias.getDateFin()!=null){
            req+="(DATE BETWEEN '"+Globals.DATE_FORMAT.format(criterias.getDateDebut())+"' AND '"+
                    Globals.DATE_FORMAT.format(criterias.getDateFin())+"')";
        }
        if(criterias.getUser()!=null){
            req+=" AND USER_FK = "+String.valueOf(criterias.getUser().getId())+" ";
        }
        req+=" GROUP BY DATE";
        return db.readMonthExpence(req);
    }

}
