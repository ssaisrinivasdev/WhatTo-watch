package com.appplication.firebase_imagdrawer.sharedprefarences;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    Context context;


    public PrefManager(Context context) {
        this.context = context;
    }


    public boolean check(String name) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("titles", Context.MODE_PRIVATE);
        boolean flag = false;
        SharedPreferences sh = context.getSharedPreferences("count",Context.MODE_PRIVATE);
        int count= Integer.parseInt(sh.getString("cou",""));
        for(int i=1;i<=count+1;i++){
            if((sharedPreferences.getString("title"+String.valueOf(i),"")).equals(name)){
                flag =true;
                break;
            }
        }
        return flag;
    }
    public int getcountexists(String name) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("titles", Context.MODE_PRIVATE);
        boolean flag = false;
        SharedPreferences sh = context.getSharedPreferences("count",Context.MODE_PRIVATE);
        int count= Integer.parseInt(sh.getString("cou",""));
        int c=0;
        for(int i=1;i<=count+1;i++){
            if(sharedPreferences.getString("title"+String.valueOf(i),"").equals(name)){
                c=i;
                break;
            }
        }
        return c;
    }
    public boolean firstcheck() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("titles", Context.MODE_PRIVATE);
        int n = sharedPreferences.getAll().size();
        if(n==0)
            return false;
        else
            return  true;
    }
    public void savecount(int count){
        SharedPreferences sharedPreferences = context.getSharedPreferences("count",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("cou", ""+String.valueOf(count));
        editor.apply();
    }
    public int getcount(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("count",Context.MODE_PRIVATE);
        return Integer.parseInt(sharedPreferences.getString("cou", ""));
    }
    public void createtitle(int count,String title){
        String str = "title"+String.valueOf(count);
        SharedPreferences sharedPreferences = context.getSharedPreferences("titles",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(""+str,""+title);
        editor.commit();
    }
    public void create(int count){
        String str = "title"+String.valueOf(count);
        SharedPreferences sharedPreferences = context.getSharedPreferences(str,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("favstatus","0");
        editor.commit();
    }


    public String returnstate(int count){
        String str = "title"+String.valueOf(count);
        SharedPreferences sharedPreferences = context.getSharedPreferences(str,Context.MODE_PRIVATE);
        return sharedPreferences.getString("favstatus","");
    }

    public boolean removestate() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        boolean sav = sharedPreferences.getString("favstatus", "").isEmpty();
        return sav;
    }
}
