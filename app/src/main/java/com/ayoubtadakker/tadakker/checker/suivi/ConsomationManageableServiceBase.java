package com.ayoubtadakker.tadakker.checker.suivi;

import android.content.Context;

import com.ayoubtadakker.tadakker.database.localDB.DBHandler;

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
}
