package com.passcrypt.passwordmanager;

import android.content.Context;
import android.content.SharedPreferences;

public class SP {
    public String path(Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences("Temp_pref", Context.MODE_PRIVATE);
        String s = sharedpreferences.getString("Spath","");
        String c = sharedpreferences.getString("Cpath","");
        String name = sharedpreferences.getString("name","");
        if (!s.isEmpty()){
            return s;
        }
        if (!c.isEmpty()){
            return (c+"/"+name+".db");
        }
        return null;
    }
}
