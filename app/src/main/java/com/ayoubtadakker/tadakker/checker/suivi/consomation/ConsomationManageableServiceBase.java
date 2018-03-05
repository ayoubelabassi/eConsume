package com.ayoubtadakker.tadakker.checker.suivi.consomation;

import android.content.Context;

import com.ayoubtadakker.tadakker.database.localDB.DBHandler;
import com.ayoubtadakker.tadakker.utils.tools.CommonCriterias;
import com.ayoubtadakker.tadakker.utils.tools.Globals;

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
        //db.crea
    }

    @Override
    public void create(List<Consomation> entities) {

    }

    @Override
    public void update(Consomation entity) {

    }

    @Override
    public void update(List<Consomation> entities) {

    }

    @Override
    public Consomation read(int id) {
        return null;
    }

    @Override
    public List<Consomation> readAll(int user_id) {
        return null;
    }

    @Override
    public List<Consomation> readByCritireas(CommonCriterias criterias) {
        String req="SELECT * FROM CONSOMATION WHERE ";
        if(criterias.getDateDebut()!=null && criterias.getDateFin()!=null){
            req+="DATE BETWEEN "+ Globals.DATE_FORMAT.format(criterias.getDateDebut())+" AND "+Globals.DATE_FORMAT.format(criterias.getDateFin());
        }
        if(criterias.getUser()!=null){
            req+=" AND USER_FK = "+criterias.getUser().getId();
        }
        return null;
    }
}
