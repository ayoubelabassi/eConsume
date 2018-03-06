package com.ayoubtadakker.tadakker.database.localDB;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ayoubtadakker.tadakker.checker.compagne.User;
import com.ayoubtadakker.tadakker.checker.suivi.consomation.Consomation;
import com.ayoubtadakker.tadakker.utils.tools.Globals;
import com.ayoubtadakker.tadakker.utils.tools.Logger;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.jar.Attributes;

/**
 * Created by AYOUB on 20/01/2018.
 */

public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "consomationDB";

    public DBHandler(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE `USER` ( `ID` INTEGER NOT NULL, `USERNAME` TEXT NOT NULL UNIQUE, `PASSWORD` TEXT NOT NULL UNIQUE, `FIRST_NAME` TEXT, `LAST_NAME` TEXT, `PHOTO` BLOB, PRIMARY KEY(`ID`) )");
        db.execSQL("CREATE TABLE `CONSOMATION` ( `ID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `PRODUCT_NAME` TEXT, `PRODUCT_DESCRIPTION` TEXT, `PRICE` REAL NOT NULL, `QTE` INTEGER NOT NULL, `DATE` TEXT, `USER_FK` INTEGER, FOREIGN KEY(`USER_FK`) REFERENCES USER(ID) )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("Drop table IF EXISTS USER");
        db.execSQL("Drop table IF EXISTS CONSOMATION");
    }

    /**
     * User Methods
     */
    public Long createUser(User user)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("USERNAME",user.getUsername());
        contentValues.put("PASSWORD",user.getPassword());
        contentValues.put("PHOTO", String.valueOf(user.getPhoto()));
        contentValues.put("FIRST_NAME",user.getFirst_name());
        contentValues.put("LAST_NAME",user.getLast_name());
        Long res=db.insert("USER",null,contentValues);
        return res;
    }

    public void deleteUsers(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE USER FROM USER");
    }

    public int deleteUser(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        int res=db.delete("USER","id="+id,null);
        return res;
    }

    public List<User> getUsers()
    {
        List<User> users=new ArrayList<User>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("select * from USER",null);
        res.moveToFirst();
        while(res.isAfterLast()==false)
        {
            int ID=res.getInt(res.getColumnIndex("ID"));
            String USERNAME=res.getString(res.getColumnIndex("USERNAME"));
            String PASSWORD=res.getString(res.getColumnIndex("PASSWORD"));
            String FIRST_NAME=res.getString(res.getColumnIndex("FIRST_NAME"));
            String LAST_NAME=res.getString(res.getColumnIndex("LAST_NAME"));
            byte[] PHOTO=res.getBlob(res.getColumnIndex("PHOTO"));
            users.add(new User(ID,USERNAME,PASSWORD,FIRST_NAME,LAST_NAME,PHOTO));
            res.moveToNext();
        };
        return users;
    }

    public User getUser(String username,String password)
    {
        User user=null;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("SELECT * FROM USER WHERE USERNAME=? AND PASSWORD=? ",new String [] {username,password});
        res.moveToFirst();
        while(res.isAfterLast()==false)
        {
            int ID=res.getInt(res.getColumnIndex("ID"));
            String USERNAME=res.getString(res.getColumnIndex("USERNAME"));
            String PASSWORD=res.getString(res.getColumnIndex("PASSWORD"));
            String FIRST_NAME=res.getString(res.getColumnIndex("FIRST_NAME"));
            String LAST_NAME=res.getString(res.getColumnIndex("LAST_NAME"));
            byte[] PHOTO=res.getBlob(res.getColumnIndex("PHOTO"));
            user=new User(ID,USERNAME,PASSWORD,FIRST_NAME,LAST_NAME,PHOTO);
            res.moveToNext();
        };
        return user;
    }

    /**
     * Consommation Methods
     */
    //Create Consomation
    public Long createConsomation(Consomation entity){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("PRODUCT_NAME",entity.getName());
        contentValues.put("PRODUCT_DESCRIPTION",entity.getDescription());
        contentValues.put("PRICE", String.valueOf(entity.getPrice()));
        contentValues.put("QTE",entity.getQte());
        contentValues.put("DATE", Globals.DATE_FORMAT.format(entity.getDate()));
        contentValues.put("USER_FK",entity.getUser_fk());
        Long res=db.insert("CONSOMATION",null,contentValues);
        return res;
    }

    //Update Consomation
    public int updateConsommation(Consomation entity){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("PRODUCT_NAME",entity.getName());
        contentValues.put("PRODUCT_DESCRIPTION",entity.getDescription());
        contentValues.put("PRICE", String.valueOf(entity.getPrice()));
        contentValues.put("QTE",entity.getQte());
        contentValues.put("DATE", Globals.DATE_FORMAT.format(entity.getDate()));
        contentValues.put("USER_FK",entity.getUser_fk());
        int res=db.update("CONSOMATION", contentValues,"ID="+entity.getId(),null);
        return res;
    }

    //delete Consommation
    public int deleteConsommation(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        int res=db.delete("CONSOMATION","ID="+id,null);
        return res;
    }

    public List<Consomation> getConsomations(int user_fk){
        List<Consomation> entity=null;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("SELECT * FROM CONSOMATION WHERE USER_FK=?",new String [] {String.valueOf(user_fk)});
        res.moveToFirst();
        while(res.isAfterLast()==false)
        {
            int ID=res.getInt(res.getColumnIndex("ID"));
            String PRODUCT_NAME=res.getString(res.getColumnIndex("PRODUCT_NAME"));
            String PRODUCT_DESCRIPTION=res.getString(res.getColumnIndex("PRODUCT_DESCRIPTION"));
            Double PRICE=res.getDouble(res.getColumnIndex("PRICE"));
            int QTE=res.getInt(res.getColumnIndex("QTE"));
            String DATE=res.getString(res.getColumnIndex("DATE"));
            int USER_FK=res.getInt(res.getColumnIndex("USER_FK"));
            java.util.Date dt=new java.util.Date();
            try {
                dt=Globals.DATE_FORMAT.parse(DATE);
            }catch (ParseException e){
                Logger.ERROR(e);
            }
            entity.add(new Consomation(ID,PRODUCT_NAME,PRODUCT_DESCRIPTION,QTE,PRICE,dt,USER_FK));
            res.moveToNext();
        };
        return entity;
    }

    public Consomation getConsomation(int id){
        Consomation entity=null;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("SELECT * FROM CONSOMATION WHERE ID=?",new String [] {String.valueOf(id)});
        res.moveToFirst();
        while(res.isAfterLast()==false)
        {
            int ID=res.getInt(res.getColumnIndex("ID"));
            String PRODUCT_NAME=res.getString(res.getColumnIndex("PRODUCT_NAME"));
            String PRODUCT_DESCRIPTION=res.getString(res.getColumnIndex("PRODUCT_DESCRIPTION"));
            Double PRICE=res.getDouble(res.getColumnIndex("PRICE"));
            int QTE=res.getInt(res.getColumnIndex("QTE"));
            String DATE=res.getString(res.getColumnIndex("DATE"));
            int USER_FK=res.getInt(res.getColumnIndex("USER_FK"));
            java.util.Date dt=new java.util.Date();
            try {
                dt=Globals.DATE_FORMAT.parse(DATE);
            }catch (ParseException e){
                Logger.ERROR(e);
            }
            entity=new Consomation(ID,PRODUCT_NAME,PRODUCT_DESCRIPTION,QTE,PRICE,dt,USER_FK);
            res.moveToNext();
        };
        return entity;
    }

    public List<Consomation> readAllConsommation(){
        List<Consomation> consomations=new ArrayList<Consomation>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("SELECT * FROM CONSOMATION",null);
        res.moveToFirst();
        while(res.isAfterLast()==false)
        {
            int ID=res.getInt(res.getColumnIndex("ID"));
            String NAME=res.getString(res.getColumnIndex("PRODUCT_NAME"));
            String DESCRIPTION=res.getString(res.getColumnIndex("PRODUCT_DESCRIPTION"));
            Double PRICE=res.getDouble(res.getColumnIndex("PRICE"));
            Date DATE=new Date();
            try {
                DATE=Globals.DATE_FORMAT.parse(res.getString(res.getColumnIndex("DATE")));
            } catch (ParseException e) {
                Logger.ERROR(e);
            }
            int QTE=res.getInt(res.getColumnIndex("QTE"));
            int USER_FK=res.getInt(res.getColumnIndex("USER_FK"));
            consomations.add(new Consomation(ID, NAME,DESCRIPTION,QTE,PRICE,DATE,USER_FK));
            res.moveToNext();
        }
        return consomations;
    }

    //read Consomation by spesic criteria
    public List<Consomation> readConsommationByCriterias(String req,String [] params){
        List<Consomation> consomations=new ArrayList<Consomation>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery(req,params);
        res.moveToFirst();
        while(res.isAfterLast()==false)
        {
            int ID=res.getInt(res.getColumnIndex("ID"));
            String NAME=res.getString(res.getColumnIndex("PRODUCT_NAME"));
            String DESCRIPTION=res.getString(res.getColumnIndex("PRODUCT_DESCRIPTION"));
            Double PRICE=res.getDouble(res.getColumnIndex("PRICE"));
            Date DATE=new Date();
            try {
                DATE=Globals.DATE_FORMAT.parse(res.getString(res.getColumnIndex("DATE")));
            } catch (ParseException e) {
                Logger.ERROR(e);
            }
            int QTE=res.getInt(res.getColumnIndex("QTE"));
            int USER_FK=res.getInt(res.getColumnIndex("USER_FK"));
            consomations.add(new Consomation(ID, NAME,DESCRIPTION,QTE,PRICE,DATE,USER_FK));
            res.moveToNext();
        }
        return consomations;
    }
}
