package com.example.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ContactDataBase extends SQLiteOpenHelper {
    static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "contacts_db";
    static final String TABLE_USER = "user";
    public ContactDataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_USER+"(name Text unique,phone Text unique)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TABLE_USER);
        onCreate(db);
    }

    public boolean insertUser(User user){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",user.getName());
        values.put("phone",user.getPhone());
        long res=db.insert(TABLE_USER,null,values);

        return res!=-1;
    }
    public boolean updateUser(User user,String oldPhone){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",user.getName());
        values.put("phone",user.getPhone());
        long res=db.update(TABLE_USER,values,"phone=?",new String[]{oldPhone});

        return res!=-1;
    }
    public boolean deleteData(String phone){
        SQLiteDatabase db=getWritableDatabase();
        int delete=db.delete(TABLE_USER,"phone = ?",new String[]{phone});
        return delete!=-1;
    }
    public ArrayList<User> retrieveAllData(){
        ArrayList<User>users=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor c=db.rawQuery("select * from "+TABLE_USER,null);

        if(c!=null&&c.moveToFirst()){
            do{
                String name=c.getString(0);
                String phone=c.getString(1);
                users.add(new User(name,phone));
            }while(c.moveToNext());
        }
        c.close();
        return users;
    }
    public boolean checkUser(String name, String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE email =? AND password =?";
        Cursor cursor = db.rawQuery(query, new String[]{name, phone});

        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists; // Returns true if user exists
    }
}
