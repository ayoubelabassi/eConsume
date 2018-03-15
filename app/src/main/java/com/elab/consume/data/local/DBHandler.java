package com.elab.consume.data.local;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.elab.consume.checker.compagne.User;
import com.elab.consume.checker.expence.Expence;
import com.elab.consume.checker.expence.MonthExpence;
import com.elab.consume.tools.Globals;
import com.elab.consume.tools.Logger;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        onCreate(db);
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
    //Create Expence
    public Long createConsomation(Expence entity){
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

    //Update Expence
    public int updateConsommation(Expence entity){
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

    public List<Expence> getConsomations(int user_fk){
        List<Expence> entity=null;
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
            entity.add(new Expence(ID,PRODUCT_NAME,PRODUCT_DESCRIPTION,QTE,PRICE,dt,USER_FK));
            res.moveToNext();
        };
        return entity;
    }

    public Expence getConsomation(int id){
        Expence entity=null;
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
            entity=new Expence(ID,PRODUCT_NAME,PRODUCT_DESCRIPTION,QTE,PRICE,dt,USER_FK);
            res.moveToNext();
        };
        return entity;
    }

    public List<Expence> readAllConsommation(){
        List<Expence> expences =new ArrayList<Expence>();
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
            expences.add(new Expence(ID, NAME,DESCRIPTION,QTE,PRICE,DATE,USER_FK));
            res.moveToNext();
        }
        return expences;
    }

    //read Expence by spesic criteria
    public List<Expence> readConsommationByCriterias(String req){
        List<Expence> expences =new ArrayList<Expence>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery(req,null);
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
            expences.add(new Expence(ID, NAME,DESCRIPTION,QTE,PRICE,DATE,USER_FK));
            res.moveToNext();
        }
        return expences;
    }

    public List<String> readExpencesNames(int user_fk){
        List<String> expences =new ArrayList<String>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("SELECT DISTINCT PRODUCT_NAME FROM CONSOMATION WHERE USER_FK=?",new String[]{String.valueOf(user_fk)});
        res.moveToFirst();
        while(res.isAfterLast()==false)
        {
            String NAME=res.getString(res.getColumnIndex("PRODUCT_NAME"));
            expences.add(NAME);
            res.moveToNext();
        }
        return expences;
    }

    public Date getExpenceMinDate(int USER_ID){
        Date DATE=new Date();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("SELECT MIN(DATE) as 'DATE' FROM CONSOMATION WHERE USER_FK=?",new String[]{String.valueOf(USER_ID)});
        res.moveToFirst();
        while(res.isAfterLast()==false)
        {
            try {
                DATE=Globals.DATE_FORMAT.parse(res.getString(res.getColumnIndex("DATE")));
            } catch (ParseException e) {
                Logger.ERROR(e);
            }
            res.moveToNext();
        }
        return DATE;
    }

    public List<MonthExpence> readMonthExpence(String req){
        List<MonthExpence> expences =new ArrayList<MonthExpence>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery(req,null);
        res.moveToFirst();
        while(res.isAfterLast()==false)
        {
            float PRICE=res.getFloat(res.getColumnIndex("AMOUNT"));
            Date DATE=new Date();
            try {
                DATE=Globals.DATE_FORMAT.parse(res.getString(res.getColumnIndex("DATE")));
            } catch (ParseException e) {
                Logger.ERROR(e);
            }
            expences.add(new MonthExpence(DATE,PRICE));
            res.moveToNext();
        }
        return expences;
    }

    public Double readTotaleExpences(String req){
        Double tot =0.0;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery(req,null);
        res.moveToFirst();
        while(res.isAfterLast()==false)
        {
            tot=res.getDouble(res.getColumnIndex("TOTALE"));
            res.moveToNext();
        }
        return tot;
    }
}
