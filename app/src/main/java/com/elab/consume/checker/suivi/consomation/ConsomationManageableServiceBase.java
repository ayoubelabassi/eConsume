package com.elab.consume.checker.suivi.consomation;

import android.content.Context;

import com.elab.consume.database.localDB.DBHandler;
import com.elab.consume.utils.tools.CommonCriterias;
import com.elab.consume.utils.tools.Globals;

import java.util.List;

/**
 * Created by NAWAR on 28/02/2018.
 */

public class ConsomationManageableServiceBase implements ConsomationManageableService {
    private DBHandler db;

    public ConsomationManageableServiceBase(Context context){
        db=new DBHandler(context);
    }

    @Override
    public void create(Consomation entity) {
        db.createConsomation(entity);
    }

    @Override
    public void create(List<Consomation> entities) {
        //db.createConsomation(entities);
    }

    @Override
    public void update(Consomation entity) {
        db.updateConsommation(entity);
    }

    @Override
    public void update(List<Consomation> entities) {
        //db.updateConsommation(entities);
    }

    @Override
    public Consomation read(int id) {
        return db.getConsomation(id);
    }

    @Override
    public void delete(int id) {
        db.deleteConsommation(id);
    }

    @Override
    public List<Consomation> readAll(int user_id) {
        return db.getConsomations(user_id);
    }

    @Override
    public List<Consomation> readByCritireas(CommonCriterias criterias) {
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
}
