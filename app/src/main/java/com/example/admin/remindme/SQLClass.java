package com.example.admin.remindme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by admin on 19-01-2017.
 */

public class SQLClass extends SQLiteOpenHelper{

    SQLiteDatabase db;
    public static final String DATABASE_NAME="Remindme.db";
    public static final String TABLE_NAME="info_table";
    public static final String CAT_NAME="cat_table";
    public static final String EXT_TABLE="extra_table";
    public static final String CAT_COL1="CATEGORY_NAME";
    public static final String COL1="ID";
    public static final String COL2="NAME";
    public static final String COL3="MOBILE_NO";
    public static final String COL4="EMAIL";
    public static final String COL5="ADDRESS";
    public static final String COL6="GENDER";
    public static final String COL7="DATE_BIRTH";
    public static final String COL8="START_DATE";
    public static final String COL9="END_DATE";
    public static final String COL10="INSTALL_MONTH";
    public static final String COL11="REMIND_TIME";
    public static final String COL12="SNOOZE_TIME";
    public static final String COL13="REMIND_DAY";
    public static final String COL14="DESC";
    public static final String COL15="TYPE";

    public SQLClass(Context context) {
        super(context, DATABASE_NAME, null, 1);
        //db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + CAT_NAME + "(CATEGORY_NAME TEXT)");
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,MOBILE_NO TEXT,EMAIL TEXT,ADDRESS TEXT,GENDER TEXT,DATE_BIRTH TEXT,START_DATE TEXT,END_DATE TEXT,INSTALL_MONTH TEXT,REMIND_TIME TEXT,SNOOZE_TIME TEXT,REMIND_DAY TEXT,DESC TEXT,TYPE TEXT)");
        db.execSQL("create table " + EXT_TABLE + "(ID INTEGER PRIMARY KEY,COL1 TEXT,COL2 TEXT,COL3 TEXT,COL4 TEXT,COL5 TEXT,COL6 TEXT,COL7 TEXT,COL8 TEXT,COL9 TEXT,COL10 TEXT,)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE "+TABLE_NAME);
        db.execSQL("DROP TABLE "+CAT_NAME);
        db.execSQL("DROP TABLE "+EXT_TABLE);
        onCreate(db);
    }

    public boolean insertcategory(String cat_name)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(CAT_COL1,cat_name);
        long result=db.insert(CAT_NAME,null,contentValues);
        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean updatecategory(String cat_name)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(CAT_COL1,cat_name);
        db.update(CAT_NAME,contentValues,"CATEGORY_NAME = ?",new String[]{cat_name});
        return true;
    }

    public Integer DeleteCategoty(String name){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(CAT_NAME,"ID = ?",new String[]{name});
    }

    public Cursor viewAllcategory(){
        SQLiteDatabase db=this.getWritableDatabase();
        String[] projections={CAT_COL1};
        Cursor res=db.query(CAT_NAME,projections,null,null,null,null,null);
        return res;
    }

    public boolean insertdata(String address, String birth_Date, String email_Id, String end_Date, String gender, String mobile_No, String name, String start_Date, String remindme, String snooze_time, String Remindday, String install_month, String desc,String type)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL2,name);
        contentValues.put(COL3,mobile_No);
        contentValues.put(COL4, email_Id);
        contentValues.put(COL5, address);
        contentValues.put(COL6, gender);
        contentValues.put(COL7,birth_Date);
        contentValues.put(COL8,start_Date);
        contentValues.put(COL9,end_Date);
        contentValues.put(COL10,install_month);
        contentValues.put(COL11,remindme);
        contentValues.put(COL12,snooze_time);
        contentValues.put(COL13,Remindday);
        contentValues.put(COL14,desc);
        contentValues.put(COL15,type);
        long result=db.insert(TABLE_NAME,null,contentValues);
        if(result==-1)
        {
            return false;
        }
        else {
            return true;
        }
    }

    public boolean updatedata(String id,String address, String birth_Date, String email_Id, String end_Date, String gender, String mobile_No, String name, String start_Date, String remindme, String snooze_time, String Remindday, String install_month,String desc ,String type)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL2,name);
        contentValues.put(COL3,mobile_No);
        contentValues.put(COL4, email_Id);
        contentValues.put(COL5, address);
        contentValues.put(COL6, gender);
        contentValues.put(COL7,birth_Date);
        contentValues.put(COL8,start_Date);
        contentValues.put(COL9,end_Date);
        contentValues.put(COL10,install_month);
        contentValues.put(COL11,remindme);
        contentValues.put(COL12,snooze_time);
        contentValues.put(COL13,Remindday);
        contentValues.put(COL14,desc);
        contentValues.put(COL15,type);
        db.update(TABLE_NAME,contentValues,"ID = ?",new String[]{id});
        return true;
    }

    public Cursor viewAllData(){
        SQLiteDatabase db=this.getWritableDatabase();
        String[] projections={COL1,COL2,COL3,COL4,COL5,COL6,COL7,COL8,COL9,COL10,COL11,COL12,COL13,COL14,COL15};
        Cursor res=db.query(TABLE_NAME,projections,null,null,null,null,null);
        return res;
    }

    public Integer DeleteData(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_NAME,"ID = ?",new String[]{id});
    }
}
